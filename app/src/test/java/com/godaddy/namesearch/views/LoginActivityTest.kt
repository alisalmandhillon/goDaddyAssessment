package com.godaddy.namesearch.views

import com.godaddy.namesearch.managers.AuthManager
import com.godaddy.namesearch.models.loginModels.LoginResponse
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

class LoginActivityTest{

    var parsedPayload:LoginResponse?=null

    @Before
    fun setup() {
        val mockPayLoad="""{"auth":{"token":"09114d19-cf75-498c-b16c-84aa19cc1afa","expiration":"2099-12-31T11:59:59.999Z"},"user":{"first":"GD","last":"Test"}}"""
        parsedPayload =  Gson().fromJson(mockPayLoad,LoginResponse::class.java)

    }

    @Test
    fun loginResponseIsNotNull() {
        assertThat(parsedPayload!=null)
        parsedPayload?.run {
            assertThat(user).isNotNull()
            assertThat(auth).isNotNull()
        }
    }

    @Test
    fun userModelHasFirstNameAndLastName() {
        parsedPayload?.run {
            assertThat(user.first).isNotNull()
            assertThat(user.last).isNotNull()
        }
    }

    @Test
    fun authHasToken() {
        parsedPayload?.run {
            assertThat(auth.token).isNotNull()
        }
    }

    @Test
    fun testAuthManagerObject() {
        parsedPayload?.run {
            AuthManager.token=auth.token!!
            AuthManager.user=user!!
        }

        assertThat(AuthManager.token).isNotEmpty()
        assertThat(AuthManager.user).isNotNull()
    }
}