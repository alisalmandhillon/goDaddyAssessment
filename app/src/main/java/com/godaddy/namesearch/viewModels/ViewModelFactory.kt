package com.godaddy.namesearch.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godaddy.namesearch.repository.MainRepository


class ViewModelFactory(private val repo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when(modelClass){
            LoginViewModel::class.java -> return LoginViewModel(repo) as T
            DomainSearchViewModel::class.java -> return DomainSearchViewModel(repo) as T
            CartViewModel::class.java -> return CartViewModel(repo) as T
            PaymentViewModel::class.java -> return PaymentViewModel(repo) as T
        }
        return null as T
    }
}