package com.godaddy.namesearch.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.godaddy.namesearch.network.RemoteDataSource
import com.godaddy.namesearch.network.RetrofitClient
import com.godaddy.namesearch.repository.MainRepository
import com.godaddy.namesearch.models.paymentModels.PaymentMethod
import com.godaddy.namesearch.R
import com.godaddy.namesearch.adapters.PaymentMethodAdapter
import com.godaddy.namesearch.managers.PaymentsManager
import com.godaddy.namesearch.viewModels.PaymentViewModel
import com.godaddy.namesearch.viewModels.ViewModelFactory

class PaymentMethodActivity : AppCompatActivity() {
    private var paymentMethods: List<PaymentMethod> = emptyList()
    private lateinit var viewModel: PaymentViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var paymentMethodAdapter : PaymentMethodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)
        setUpViewModel()
        progressBar=findViewById(R.id.progress_bar)
        initializePaymentAdapter()
        callPaymentMethodApi()
    }
    private fun initializePaymentAdapter(){
        val paymentMethodList = findViewById<ListView>(R.id.payment_method_list)
        paymentMethodAdapter = PaymentMethodAdapter(this)
        paymentMethodList.adapter = paymentMethodAdapter
        paymentMethodList.setOnItemClickListener { _, _, position, _ ->
            PaymentsManager.selectedPaymentMethod = paymentMethods[position]
            finish()
        }
    }
    private fun setUpViewModel() {
        val viewModelFactory= ViewModelFactory(
            MainRepository(RemoteDataSource(RetrofitClient.apiInterface))
        )
        viewModel= ViewModelProvider(this,viewModelFactory).get(
            PaymentViewModel::class.java)
    }
    private fun callPaymentMethodApi() {
        progressBar.visibility=View.VISIBLE
        viewModel.getPaymentsMethods().observe(this, {
            progressBar.visibility=View.GONE
            if(it!=null && it.isNotEmpty()){
              paymentMethods=it
              paymentMethodAdapter.addAll(paymentMethods)
             } else {
                Toast.makeText(this@PaymentMethodActivity,"No Payment Method Found",Toast.LENGTH_SHORT).show()
            }
        })
    }

}
