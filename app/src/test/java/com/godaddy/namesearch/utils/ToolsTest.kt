package com.godaddy.namesearch.utils

import org.junit.Test

import org.junit.Assert.*

class ToolsTest {

    @Test
    fun validUserNameAndPassword() {
        var userName="AliSalman"
        var password="123456"
        assertTrue(Tools.validateLogin(userName,password).equals(Tools.SuccessKey))
    }

    @Test
    fun invalidUserName() {
        var userName="A"
        var password="123456"
        assertTrue(!Tools.validateLogin(userName,password).equals(Tools.SuccessKey))
    }

    @Test
    fun invalidPassword() {
        var userName="AliSalman"
        var password=""
        assertTrue(!Tools.validateLogin(userName,password).equals(Tools.SuccessKey))
    }

    @Test
    fun emptyUserNameAndPassword() {
        var userName=""
        var password=""
        assertTrue(!Tools.validateLogin(userName,password).equals(Tools.SuccessKey))
    }

}