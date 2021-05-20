package com.godaddy.namesearch.utils

object Tools {
     val SuccessKey="Success"

    fun validateLogin(userName:String,password:String):String{
        if(userName.isEmpty()){
            return "Enter username"
        }
        if(password.isEmpty()){
            return "Enter password"
        }
        if(userName.length<3){
            return "Invalid username"
        }
        if(password.length<3){
            return "Invalid password"
        }
        return SuccessKey
    }
}