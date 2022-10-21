package com.example.udacityfirstproject.views.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.udacityfirstproject.R
import com.example.udacityfirstproject.databinding.FragmentLoginBinding
import com.example.udacityfirstproject.model.User
import com.example.udacityfirstproject.viewmodel.ShoesViewModel

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding:FragmentLoginBinding
    private val shoesViewModel:ShoesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        try {
            if(shoesViewModel.hasSomeoneLoggedInBefore.value!! && shoesViewModel.user.value!! != null  ){
                val action=LoginFragmentDirections.actionLoginFragmentToShowListFragment()
                findNavController().navigate(action)
            }
        }catch (e:Exception){

        }

        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_login,container,false)
        binding.loginBtn.isEnabled=false
        binding.emailField.doOnTextChanged { text, start, before, count ->
            viewModel.updateEmailValue(text.toString())
        }
        binding.passwordField.doOnTextChanged { text, start, before, count ->
            viewModel.updatePasswordValue(text.toString())
        }
        viewModel.isEmailValid.observe(viewLifecycleOwner, Observer { value->
            if(value){
                makeBtnEnabled()
            }else{
                binding.emailField.error=viewModel.emailProblem()
                binding.emailField.requestFocus()
                binding.loginBtn.isEnabled=false
            }
        })
        viewModel.isPasswordValid.observe(viewLifecycleOwner, Observer { value->
            if(value){
                makeBtnEnabled()
            }else{
                binding.loginBtn.isEnabled=false
            }
        })
        binding.loginBtn.setOnClickListener {
            nextScreen()
        }
        return binding.root
    }
    private fun nextScreen(){
        val user=User(formatName(viewModel.email.value!!), viewModel.email.value!!,viewModel.password.value!!)
        if(!shoesViewModel.hasSomeoneLoggedInBefore.value!!){
            shoesViewModel.login(user)
            val action=LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
            findNavController().navigate(action)
        }else{
            shoesViewModel.login(user)
            val action=LoginFragmentDirections.actionLoginFragmentToShowListFragment()
            findNavController().navigate(action)
        }
    }
    private fun makeBtnEnabled(){
        binding.loginBtn.isEnabled = viewModel.emailProblem() == "OK" && viewModel.passwordProblem() == "OK"
    }
    private fun formatName(s:String):String{
        var username=""
        for (i in s.indices){
            if(s[i] == '@'){
                break
            }
            username="$username${s[i]}"
        }
        return username
    }

}