package com.godaddy.namesearch.models.loginModels

import com.godaddy.namesearch.models.Auth
import com.godaddy.namesearch.models.User

data class LoginResponse(
    val auth: Auth,
    val user: User
)
