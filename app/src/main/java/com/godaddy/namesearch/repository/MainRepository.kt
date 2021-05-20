package com.godaddy.namesearch.repository

import com.godaddy.namesearch.models.loginModels.LoginRequest
import com.godaddy.namesearch.models.paymentModels.PaymentRequest
import com.godaddy.namesearch.network.BaseDataSource
import com.godaddy.namesearch.network.RemoteDataSource

class MainRepository(private val remoteDataSource: RemoteDataSource): BaseDataSource() {
    suspend fun getLogin(loginRequest: LoginRequest) = remoteDataSource.getLoginResult(loginRequest)
    suspend fun getExactDomain(domainName: String) = remoteDataSource.getExactDomain(domainName)
    suspend fun getSuggestedDomain(domainName: String) = remoteDataSource.getSuggestedDomain(domainName)
    suspend fun callPaymentProcess(paymentRequest: PaymentRequest) = remoteDataSource.callPaymentProcessApi(paymentRequest)
    suspend fun getPaymentMethods() = remoteDataSource.getPaymentMethods()
}