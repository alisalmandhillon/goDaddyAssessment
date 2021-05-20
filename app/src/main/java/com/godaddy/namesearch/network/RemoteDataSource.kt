package com.godaddy.namesearch.network

import com.godaddy.namesearch.models.loginModels.LoginRequest
import com.godaddy.namesearch.models.paymentModels.PaymentRequest
import com.godaddy.namesearch.models.loginModels.LoginResponse
import com.godaddy.namesearch.models.paymentModels.PaymentMethod
import com.godaddy.namesearch.models.domainModels.DomainSearchExactMatchResponse
import com.godaddy.namesearch.models.domainModels.DomainSearchRecommendedResponse

class RemoteDataSource(private val apiService: ApiInterface): BaseDataSource() {
    suspend fun getLoginResult(loginRequest: LoginRequest): Resource<LoginResponse> {
    return getResult {apiService.hitLoginApi(loginRequest)}
    }
    suspend fun getExactDomain(domainName: String): Resource<DomainSearchExactMatchResponse> {
        return getResult {apiService.hitExactDomainApi(domainName)}
    }
    suspend fun getSuggestedDomain(domainName: String): Resource<DomainSearchRecommendedResponse> {
        return getResult {apiService.hitSuggestedDomainApi(domainName)}
    }
    suspend fun callPaymentProcessApi(paymentRequest: PaymentRequest): Resource<Void> {
        return getResult {apiService.hitPaymentApi(paymentRequest)}
    }
    suspend fun getPaymentMethods(): List<PaymentMethod> {
        return apiService.getPaymentMethods()
    }
 }