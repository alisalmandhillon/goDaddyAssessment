package com.godaddy.namesearch.viewModels
 import androidx.lifecycle.*
 import com.godaddy.namesearch.models.loginModels.LoginRequest
 import com.godaddy.namesearch.network.Resource
 import com.godaddy.namesearch.repository.MainRepository
 import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: MainRepository) : ViewModel(){
    fun getLoginData(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(repository.getLogin(loginRequest))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
