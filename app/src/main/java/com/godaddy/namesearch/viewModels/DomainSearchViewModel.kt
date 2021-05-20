package com.godaddy.namesearch.viewModels
 import androidx.lifecycle.*
 import com.godaddy.namesearch.network.Resource
 import com.godaddy.namesearch.repository.MainRepository
 import kotlinx.coroutines.Dispatchers

class DomainSearchViewModel(private val repository: MainRepository) : ViewModel(){
    fun getExactDomain(domainName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(repository.getExactDomain(domainName))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun getSuggestedDomain(domainName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(repository.getSuggestedDomain(domainName))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}
