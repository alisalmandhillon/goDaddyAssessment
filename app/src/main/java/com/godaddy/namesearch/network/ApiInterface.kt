package com.godaddy.namesearch.network

import com.godaddy.namesearch.models.loginModels.LoginRequest
import com.godaddy.namesearch.models.paymentModels.PaymentRequest
 import com.godaddy.namesearch.models.loginModels.LoginResponse
import com.godaddy.namesearch.models.paymentModels.PaymentMethod
 import com.godaddy.namesearch.models.domainModels.DomainSearchExactMatchResponse
import com.godaddy.namesearch.models.domainModels.DomainSearchRecommendedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST(EndPoints.LOGIN_API)
    suspend fun hitLoginApi(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @GET(EndPoints.EXACT_DOMAIN_API)
    suspend fun hitExactDomainApi(@Query(value="q") domainName:String) : Response<DomainSearchExactMatchResponse>

    @GET(EndPoints.SUGGESTED_DOMAIN_API)
    suspend fun hitSuggestedDomainApi(@Query(value="q") domainName:String) : Response<DomainSearchRecommendedResponse>

    @POST(EndPoints.PAYMENT_PROCESS_API)
    suspend fun hitPaymentApi(@Body paymentRequest: PaymentRequest) : Response<Void>

    @GET(EndPoints.PAYMENT_METHODS_API)
    suspend fun getPaymentMethods() : List<PaymentMethod>

}