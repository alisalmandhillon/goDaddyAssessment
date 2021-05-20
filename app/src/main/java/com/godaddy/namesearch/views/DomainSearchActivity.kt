package com.godaddy.namesearch.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.godaddy.namesearch.network.RemoteDataSource
import com.godaddy.namesearch.network.Resource
import com.godaddy.namesearch.network.RetrofitClient
import com.godaddy.namesearch.repository.MainRepository
 import com.godaddy.namesearch.R
import com.godaddy.namesearch.adapters.SearchResultAdapter
import com.godaddy.namesearch.models.domainModels.Domain
import com.godaddy.namesearch.models.domainModels.DomainSearchExactMatchResponse import com.godaddy.namesearch.utils.ShoppingCart
import com.godaddy.namesearch.viewModels.DomainSearchViewModel
import com.godaddy.namesearch.viewModels.ViewModelFactory

class DomainSearchActivity : AppCompatActivity() {
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var viewModel: DomainSearchViewModel
    private lateinit var progressBar:ProgressBar
    private lateinit var cartButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domain_search)
        setUpViewModel()
        searchResultAdapter = SearchResultAdapter(this)
        progressBar=findViewById(R.id.progress_bar)
        cartButton=findViewById(R.id.view_cart_button)
        initializeSearchButton()
        configureCartButton()
        initializeCartButton()
        initializeListViewListener()
    }
    private fun initializeSearchButton(){
        findViewById<Button>(R.id.search_button).setOnClickListener {
            loadData()
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }
    private fun initializeCartButton(){
        findViewById<ListView>(R.id.results_list_view).also { listView ->
            listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
            listView.adapter = searchResultAdapter
            listView.setOnItemClickListener { _, view, position, _ ->
                val item = searchResultAdapter.getItem(position)
                ShoppingCart.domains = ShoppingCart.domains.toMutableList().also {
                    if (ShoppingCart.domains.contains(item)) {
                        it.remove(item)
                    } else {
                        item?.apply { it.add(item) }
                    }
                }
                item?.apply {
                    item.selected = !item.selected
                    view.setBackgroundColor(when (item.selected) {
                        true -> Color.LTGRAY
                        false -> Color.TRANSPARENT
                    })
                }
                configureCartButton()
            }
        }
    }
    private fun initializeListViewListener() {
        findViewById<ListView>(R.id.results_list_view).also { listView ->
            listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
            listView.adapter = searchResultAdapter
            listView.setOnItemClickListener { _, view, position, _ ->
                val item = searchResultAdapter.getItem(position)
                ShoppingCart.domains = ShoppingCart.domains.toMutableList().also {
                    if (ShoppingCart.domains.contains(item)) {
                        it.remove(item)
                    } else {
                        item?.apply { it.add(item) }
                    }
                }
                item?.apply {
                    item.selected = !item.selected
                    view.setBackgroundColor(when (item.selected) {
                        true -> Color.LTGRAY
                        false -> Color.TRANSPARENT
                    })
                }
                configureCartButton()
            }
        }

    }
    private fun setUpViewModel() {
        val viewModelFactory= ViewModelFactory(
            MainRepository(RemoteDataSource(RetrofitClient.apiInterface))
        )
        viewModel= ViewModelProvider(this,viewModelFactory).get(
            DomainSearchViewModel::class.java
        )
    }
    private fun configureCartButton() {
        cartButton.isEnabled = ShoppingCart.domains.isNotEmpty()
        cartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
    private fun loadData() {
    val searchQuery = findViewById<EditText>(R.id.search_edit_text).text.toString()
    if(searchQuery.isEmpty()) {
        Toast.makeText(applicationContext, "Please enter domain name", Toast.LENGTH_SHORT).show()
    } else {
        callExactDomainApi(searchQuery)
    }
    }
    private fun callExactDomainApi(domainName: String) {
        viewModel.getExactDomain(domainName).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                     if (it.data!=null){
                         callSuggestedDomainApi(domainName,it?.data)
                    }
                }
                Resource.Status.ERROR -> {
                    progressBar.visibility=View.GONE
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()

                }

                Resource.Status.LOADING ->{
                   progressBar.visibility=View.VISIBLE
                }
            }
        })
    }
    private fun callSuggestedDomainApi(domainName: String,exactMatchResponse: DomainSearchExactMatchResponse?) {
        viewModel.getSuggestedDomain(domainName).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                   progressBar.visibility=View.GONE
                    if (it.data!=null){
                        var exactDomain: Domain?=null
                        if(exactMatchResponse!=null) {
                            val exactDomainPriceInfo =
                                exactMatchResponse.products.first { product ->
                                    product.productId == exactMatchResponse.domain.productId
                                }.priceInfo
                            exactDomain = Domain(
                                exactMatchResponse.domain.fqdn,
                                exactDomainPriceInfo.currentPriceDisplay,
                                exactMatchResponse.domain.productId
                            )
                        }
                        val suggestionDomains = it.data.domains.map { domain ->
                            val priceInfo = it.data.products
                                .first { price -> price.productId == domain.productId }
                                .priceInfo

                            Domain(domain.fqdn, priceInfo.currentPriceDisplay, domain.productId)
                        }
                        if(exactDomain!=null){
                        setDomainResult(listOf(exactDomain) + suggestionDomains)}
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    progressBar.visibility=View.GONE
                }

                Resource.Status.LOADING ->{
                    //progressBar.visibility=View.VISIBLE
                }
            }
        })
    }
    private fun setDomainResult(results:List<Domain>) {
        searchResultAdapter.clear()
        searchResultAdapter.addAll(results)
    }

}