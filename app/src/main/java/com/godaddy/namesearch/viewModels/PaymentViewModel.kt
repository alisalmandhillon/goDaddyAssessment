package com.godaddy.namesearch.viewModels
 import androidx.lifecycle.*
 import com.godaddy.namesearch.repository.MainRepository
 import kotlinx.coroutines.Dispatchers

class PaymentViewModel(private val repository: MainRepository) : ViewModel(){
    fun getPaymentsMethods() = liveData(Dispatchers.IO) {
        try {
            emit(repository.getPaymentMethods())
        } catch (exception: Exception) {
            emit(null)
        }
    }
}
