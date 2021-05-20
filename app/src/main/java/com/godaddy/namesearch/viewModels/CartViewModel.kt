package com.godaddy.namesearch.viewModels
 import androidx.lifecycle.*
  import com.godaddy.namesearch.models.paymentModels.PaymentRequest
 import com.godaddy.namesearch.network.Resource
 import com.godaddy.namesearch.repository.MainRepository
 import kotlinx.coroutines.Dispatchers

class CartViewModel(private val repository: MainRepository) : ViewModel(){
    fun callPaymentProcess(paymentRequest: PaymentRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(repository.callPaymentProcess(paymentRequest))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
