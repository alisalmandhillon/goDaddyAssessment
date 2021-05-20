package com.godaddy.namesearch.views

import com.godaddy.namesearch.models.paymentModels.PaymentMethod
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test


class PaymentMethodActivityTest{
    lateinit var paymentMothods:List<PaymentMethod>

    @Before
    fun setup() {
        var mockPaymentMethod="""[{"name":"Visa","lastFour":"0123","token":"035db0ce-52b4-4ac5-9fb6-4cfec4446a28"},{"name":"MasterCard","lastFour":"7890","token":"0d67518f-1133-4e1e-9478-b21f3689733c"},{"name":"PayPal","displayFormattedEmail":"gd***st@g*.com","token":"46c89981-b3de-498c-b50b-2eeb3e00dee0"}]"""

        paymentMothods = Gson().fromJson(mockPaymentMethod,
            object : TypeToken<List<PaymentMethod?>?>() {}.getType()
        )
    }

    @Test
    fun paymentMethodSizeGreaterThanZero() {
        Truth.assertThat(paymentMothods).isNotNull()
        Truth.assertThat(paymentMothods.size).isGreaterThan(0)
    }

    @Test
    fun paymentObjectIsNotNull() {
        Truth.assertThat(paymentMothods.get(0)).isNotNull()
        paymentMothods.get(0)?.run {
            Truth.assertThat(name).isNotEmpty()
            Truth.assertThat(token).isNotEmpty()
            Truth.assertThat(lastFour).isNotEmpty()
         }
    }
}