package com.example.udacityfirstproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.udacityfirstproject.model.Shoe
import com.example.udacityfirstproject.model.User

class ShoesViewModel :ViewModel(){
    private val _hasLoggedInBefore = MutableLiveData<Boolean>()
    val hasSomeoneLoggedInBefore : LiveData<Boolean>
    get() = _hasLoggedInBefore

    private val _shoesList = MutableLiveData<ArrayList<Shoe>>()
    val shoesList : LiveData<ArrayList<Shoe>>
        get() = _shoesList

    private var _user= MutableLiveData<User>()
    val user: LiveData<User>
    get() = _user


    fun login(user: User){
        this._user.value=user
        _hasLoggedInBefore.value=true
    }
    fun logout(){
        this._user.value=null
    }

    init {
        _hasLoggedInBefore.value=false
        _user.value = null
        _shoesList.value= ArrayList()
    }
    fun addShoe(shoe:Shoe){
        _shoesList.value?.add(shoe)
    }
    fun updateShoe(shoe:Shoe,i:Int){
        _shoesList.value?.set(i , shoe)
    }
    fun indexOfElement(shoe: Shoe):Int{
        return _shoesList.value?.indexOf(shoe)!!
    }
}