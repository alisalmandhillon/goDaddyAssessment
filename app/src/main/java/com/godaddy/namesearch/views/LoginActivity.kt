package com.godaddy.namesearch.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.godaddy.namesearch.models.loginModels.LoginRequest
import com.godaddy.namesearch.network.RemoteDataSource
import com.godaddy.namesearch.network.Resource
import com.godaddy.namesearch.network.RetrofitClient
import com.godaddy.namesearch.repository.MainRepository
 import com.godaddy.namesearch.managers.AuthManager
import com.godaddy.namesearch.R
import com.godaddy.namesearch.managers.DataManager
import com.godaddy.namesearch.utils.Tools
import com.godaddy.namesearch.viewModels.LoginViewModel
import com.godaddy.namesearch.viewModels.ViewModelFactory
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginStatus()
        setContentView(R.layout.activity_login)
        setUpViewModel()
        initializeLoginButton()
        progressBar=findViewById(R.id.progress_bar)

    }
    private fun initializeLoginButton(){
        findViewById<Button>(R.id.login).setOnClickListener(this)

    }
    private fun checkLoginStatus(){
        //Check User is already logged in or not
        val loginResponse=DataManager(this@LoginActivity).getLoginResponse()
        if(loginResponse?.user!=null && loginResponse.auth.token!=null){
            AuthManager.user = loginResponse.user
            AuthManager.token = loginResponse.auth.token
            startActivity(Intent(this@LoginActivity, DomainSearchActivity::class.java))
            finish()
        }
    }
    private fun setUpViewModel() {
        val viewModelFactory= ViewModelFactory(
            MainRepository(RemoteDataSource(RetrofitClient.apiInterface))
        )
        viewModel= ViewModelProvider(this,viewModelFactory).get(
            LoginViewModel::class.java
        )
    }
    override fun onClick(v: View?) {
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        var validateResult=Tools.validateLogin(username,password)
        if(validateResult.equals(Tools.SuccessKey)) {
            callLoginApi(LoginRequest(username, password))
        } else {
            Toast.makeText(applicationContext,validateResult,Toast.LENGTH_SHORT).show()
        }
    }
    private fun callLoginApi(loginRequest: LoginRequest) {
            viewModel.getLoginData(loginRequest).observe(this, {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        progressBar.visibility=View.GONE
                        if (it.data?.user!=null && it.data.auth.token!=null){
                            AuthManager.user = it.data.user
                            AuthManager.token = it.data.auth.token
                            DataManager(this@LoginActivity).saveLoginResponse(it.data)
                            startActivity(Intent(this@LoginActivity, DomainSearchActivity::class.java))
                            finish()
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        progressBar.visibility=View.GONE

                    }

                    Resource.Status.LOADING ->{
                        progressBar.visibility=View.VISIBLE
                    }
                }
            })
    }

}