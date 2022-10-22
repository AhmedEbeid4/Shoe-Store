package com.example.udacityfirstproject.views.shoedetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udacityfirstproject.R
import com.example.udacityfirstproject.databinding.FragmentShoeDetailBinding
import com.example.udacityfirstproject.model.Shoe
import com.example.udacityfirstproject.model.Texts
import com.example.udacityfirstproject.viewmodel.ShoesViewModel
import kotlinx.android.synthetic.main.fragment_shoe_detail.*


class ShoeDetailFragment : Fragment() {
    private lateinit var binding : FragmentShoeDetailBinding
    private lateinit var viewModel: ShoesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_shoe_detail,container,false)
        viewModel = ViewModelProvider(requireActivity())[ShoesViewModel::class.java]
        val args by navArgs<ShoeDetailFragmentArgs>()

        if(args.statue == Texts.add){
            binding.actionBtn.setOnClickListener {
                addShoe()
            }
        }else if(args.statue == Texts.update){
            binding.actionBtn.text= "Edit"
            val index:Int=args.index
            assignProp(args.shoe!!)
            binding.actionBtn.setOnClickListener {
                if(index != -1){
                    updateShoe(args.shoe!!,index)
                }else{
                    Toast.makeText(activity?.applicationContext,"Something Went Wrong !",Toast.LENGTH_SHORT).show()
                    back()
                }
            }
        }else if(args.statue == Texts.view){
            binding.actionBtn.visibility=View.GONE
            binding.cancelButton.visibility=View.GONE
            val s:Shoe= args.shoe!!
            viewShoe(s)
        }
        binding.cancelButton.setOnClickListener {
            back()
        }

        return binding.root
    }

    private fun viewShoe(s:Shoe){
        assignProp(s)
        binding.shoeNameField.isEnabled=false
        binding.shoeSizeField.isEnabled=false
        binding.shoeCompanyField.isEnabled=false
        binding.shoeDescField.isEnabled=false
    }
    private fun assignProp(s:Shoe){
        binding.shoeNameField.setText(s.name)
        binding.shoeSizeField.setText(s.size.toString())
        binding.shoeCompanyField.setText(s.company)
        binding.shoeDescField.setText(s.description)
    }
    private fun updateShoe(s:Shoe,index:Int){
        var newShoe=Shoe(
            binding.shoeNameField.text.toString(),
            binding.shoeSizeField.text.toString().toDouble(),
            binding.shoeCompanyField.text.toString(),
            binding.shoeDescField.text.toString()
        )
        if (newShoe.company == s.company && newShoe.name == s.name && newShoe.size == s.size && newShoe.description==s.description){
            Toast.makeText(activity?.applicationContext,"Nothing has changed !",Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.updateShoe(newShoe,index)
        Toast.makeText(activity?.applicationContext,"Updated Successfully",Toast.LENGTH_SHORT).show()
        back()
    }
    private fun addShoe(){

        var bool=true
        if(binding.shoeDescField.text.isEmpty()){
            binding.shoeDescField.error = "Fill The Blank Please"
            binding.shoeDescField.requestFocus()
            bool=false
        }
        if(binding.shoeCompanyField.text.isEmpty()){
            binding.shoeCompanyField.error = "Fill The Blank Please"
            binding.shoeCompanyField.requestFocus()
            bool=false
        }

        if(binding.shoeSizeField.text.isEmpty()){
            binding.shoeSizeField.error = "Fill The Blank Please"
            binding.shoeSizeField.requestFocus()
            bool=false
        }
        if(binding.shoeNameField.text.isEmpty()){
            binding.shoeNameField.error = "Fill The Blank Please"
            binding.shoeNameField.requestFocus()
            bool=false
        }

        if(bool){
            var s=Shoe(
                binding.shoeNameField.text.toString(),
                binding.shoeSizeField.text.toString().toDouble(),
                binding.shoeCompanyField.text.toString(),
                binding.shoeDescField.text.toString()
                )
            viewModel.addShoe(s)
            Toast.makeText(activity?.applicationContext,"Added",Toast.LENGTH_SHORT).show()
            binding.shoeNameField.setText("")
            binding.shoeSizeField.setText("")
            binding.shoeCompanyField.setText("")
            binding.shoeDescField.setText("")
            binding.shoeNameField.requestFocus()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                back()
            }
        })
    }
    private fun back(){
        val action=ShoeDetailFragmentDirections.actionShoeDetailFragmentToShowListFragment()
        findNavController().navigate(action)
    }


}