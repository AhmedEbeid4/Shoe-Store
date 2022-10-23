package com.example.udacityfirstproject.views.showlist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.udacityfirstproject.R
import com.example.udacityfirstproject.databinding.FragmentShowListBinding
import com.example.udacityfirstproject.databinding.ItemLayoutBinding
import com.example.udacityfirstproject.model.Shoe
import com.example.udacityfirstproject.model.Texts
import com.example.udacityfirstproject.viewmodel.ShoesViewModel


class ShowListFragment : Fragment() {
    private lateinit var binding:FragmentShowListBinding
    private lateinit var viewModel: ShoesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.setHasOptionsMenu(true)
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_show_list,container,false)
        viewModel = ViewModelProvider(requireActivity())[ShoesViewModel::class.java]
        addItems()
        binding.floatingBtn.setOnClickListener{
            addShoe()
        }
        return binding.root
    }
    private fun addItems(){
        for (i in viewModel.shoesList.value?.indices!!){
            var v:View = assignShoe(viewModel.shoesList.value!![i])
            binding.itemsContainer.addView(v)
        }
    }
    private fun assignShoe(shoe: Shoe) :View{
//      data binding for the custom layout
        val itemLayoutBinding = ItemLayoutBinding.inflate(LayoutInflater.from(requireContext()),null,false)
        itemLayoutBinding.shoe=shoe
        itemLayoutBinding.deleteBtn.setOnClickListener {
            binding.itemsContainer.removeView(itemLayoutBinding.root)
            viewModel.shoesList.value?.remove(shoe)
        }
        itemLayoutBinding.editBtn.setOnClickListener {
            editShoe(shoe)
        }

        itemLayoutBinding.container.setOnClickListener {
            viewShoe(shoe)
        }
        return itemLayoutBinding.root
    }

    private fun editShoe(shoe:Shoe){
        val action=ShowListFragmentDirections.actionShowListFragmentToShoeDetailFragment(Texts.update)
        action.shoe=shoe
        action.index= viewModel.indexOfElement(shoe)
        findNavController().navigate(action)
    }

    private fun addShoe(){
        val action=ShowListFragmentDirections.actionShowListFragmentToShoeDetailFragment(Texts.add)
        action.shoe=null
        findNavController().navigate(action)
    }
    private fun viewShoe(s:Shoe){
        val action=ShowListFragmentDirections.actionShowListFragmentToShoeDetailFragment(Texts.view)
        action.shoe=s
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.loginFragment){
            viewModel.logout()
            val action=ShowListFragmentDirections.actionShowListFragmentToLoginFragment()
            findNavController().navigate(action)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}