package com.godaddy.namesearch.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godaddy.namesearch.models.paymentModels.PaymentRequest
import com.godaddy.namesearch.network.RemoteDataSource
import com.godaddy.namesearch.network.Resource
import com.godaddy.namesearch.network.RetrofitClient
import com.godaddy.namesearch.repository.MainRepository
import com.godaddy.namesearch.*
import com.godaddy.namesearch.adapters.CartAdapter
import com.godaddy.namesearch.managers.AuthManager
import com.godaddy.namesearch.managers.PaymentsManager
import com.godaddy.namesearch.utils.ShoppingCart
import com.godaddy.namesearch.viewModels.CartViewModel
import com.godaddy.namesearch.viewModels.ViewModelFactory
import java.text.NumberFormat


class CartActivity : AppCompatActivity() {
    private lateinit var viewModel: CartViewModel
    private lateinit var progressBar:ProgressBar
    private lateinit var paymentButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initializeAdapter()
        initializePaymentButton()
        setUpViewModel()
        progressBar=findViewById(R.id.progress_bar)
    }
    private fun setUpViewModel() {
        val viewModelFactory= ViewModelFactory(
            MainRepository(RemoteDataSource(RetrofitClient.apiInterface)))

        viewModel= ViewModelProvider(this,viewModelFactory).get(
            CartViewModel::class.java)
    }
    private fun initializePaymentButton(){
        paymentButton=findViewById<Button>(R.id.pay_now_button).also{
            it.setOnClickListener { payButtonTapped() }}
     }
    private fun initializeAdapter(){
        val cartRecyclerView = findViewById<RecyclerView>(R.id.cart_item_recyclerview)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            cartRecyclerView.context,
            LinearLayout.VERTICAL
        )
        cartRecyclerView.addItemDecoration(dividerItemDecoration)
        cartRecyclerView.adapter = CartAdapter { updatePayButton() }
    }
    override fun onResume() {
        super.onResume()
        updatePayButton()
    }
    private fun payButtonTapped() {
        if (PaymentsManager.selectedPaymentMethod == null) {
            startActivity(Intent(this, PaymentMethodActivity::class.java))
        } else {
            performPayment()
        }
    }
    private fun updatePayButton() {
        if (PaymentsManager.selectedPaymentMethod == null) {
            paymentButton.text = resources.getString(R.string.selectpaymentmethod)
        } else {
            var totalPayment = 0.00

            ShoppingCart.domains.forEach {
                val priceDouble = it.price.replace("$","").toDouble()
                totalPayment += priceDouble
            }

            val currencyFormatter = NumberFormat.getCurrencyInstance()

            paymentButton.text = "Pay ${currencyFormatter.format(totalPayment)} Now"
        }
    }
    private fun performPayment() {
        paymentButton.isEnabled = false
        postPaymentApi()
    }
    private fun postPaymentApi() {
        if(AuthManager.token!=null && PaymentsManager.selectedPaymentMethod!=null &&
            PaymentsManager.selectedPaymentMethod?.token!=null) {
        val paymentRequest= PaymentRequest(AuthManager.token,PaymentsManager.selectedPaymentMethod?.token!!)
        viewModel.callPaymentProcess(paymentRequest).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.visibility=View.GONE
                    showSuccessMessage()
                }
                Resource.Status.ERROR -> {
                     progressBar.visibility=View.GONE
                    if(it.message!!.contains("200")){
                        showSuccessMessage()
                    } else {
                        showErrorMessage()
                    }
                }

                Resource.Status.LOADING ->{
                    progressBar.visibility=View.VISIBLE
                }
            }
        })}
    }
    private fun showErrorMessage(){
        AlertDialog.Builder(this@CartActivity)
            .setTitle("All done!")
            .setMessage("There was an issue with your purchase")
            .show()
        paymentButton.isEnabled = true
    }
    private fun showSuccessMessage(){
        AlertDialog.Builder(this@CartActivity)
            .setTitle("All done!")
            .setMessage("Your purchase is complete!")
            .show()
        paymentButton.isEnabled = true

    }
}

