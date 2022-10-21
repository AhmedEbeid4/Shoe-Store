package com.example.udacityfirstproject.views.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udacityfirstproject.R
import com.example.udacityfirstproject.databinding.FragmentWelcomeBinding
import com.example.udacityfirstproject.viewmodel.ShoesViewModel

class WelcomeFragment : Fragment() {

    private lateinit var binding:FragmentWelcomeBinding
    private lateinit var viewModel: ShoesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_welcome,container,false)
        viewModel = ViewModelProvider(requireActivity())[ShoesViewModel::class.java]
        binding.welcomeMessage.text="Hi ${viewModel.user.value?.name}"
        binding.nextBtn.setOnClickListener {
            val action=WelcomeFragmentDirections.actionWelcomeFragmentToInstructionFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }

        })
    }

}