package com.example.udacityfirstproject.views.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udacityfirstproject.R
import com.example.udacityfirstproject.databinding.FragmentInstructionBinding


class InstructionFragment : Fragment() {
    private lateinit var binding: FragmentInstructionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=
            DataBindingUtil.inflate(layoutInflater,R.layout.fragment_instruction,container,false)
        binding.nextBtn.setOnClickListener {
            val action= InstructionFragmentDirections.actionInstructionFragmentToShowListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }

        })
    }
}