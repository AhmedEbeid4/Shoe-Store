package com.example.udacityfirstproject.views.login


import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _password=MutableLiveData<String>()
    val password:LiveData<String>
        get() = _password

    private val _email=MutableLiveData<String>()
    val email:LiveData<String>
        get() = _email

    private val _isPasswordValid=MutableLiveData<Boolean>()
    val isPasswordValid:LiveData<Boolean>
        get() = _isPasswordValid

    private val _isEmailValid=MutableLiveData<Boolean>()
    val isEmailValid:LiveData<Boolean>
        get() = _isEmailValid

    fun updatePasswordValue(password:String){
        this._password.value=password
        this._isPasswordValid.value = passwordProblem() == "OK"
    }

    fun updateEmailValue(email:String){
        this._email.value=email
        this._isEmailValid.value = emailProblem() == "OK"
    }

    fun emailProblem():String{
        if(_email.value?.isEmpty()!!){
            return "Fill The Blank , Please"
        }
        return "OK"
    }
    fun passwordProblem():String{
        return when {
            _password.value!!.isEmpty() -> {
                "Fill The Blank , Please"
            }
            else -> "OK"
        }
    }

    init {
        _password.value=""
        _email.value=""
        _isPasswordValid.value=false
        _isEmailValid.value=false
    }
}