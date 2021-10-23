package com.example.roomfirstapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomfirstapp.R
import com.example.roomfirstapp.model.User
import com.example.roomfirstapp.viewModel.UserViewModel
import com.example.roomfirstapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btAdd.setOnClickListener {
            insertDataToDatabase()
        }

        return root
    }

    private fun insertDataToDatabase(){
        val firstName = binding.edFirstName.text.toString()
        val lastName = binding.edLastName.text.toString()
        val age = binding.edAge.text

        if (inputCheck(firstName, lastName, age)){
            //Create User Object
            val user = User(0, firstName, lastName, age.toString())
            //add data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else
            Toast.makeText(requireContext(), "Fill All fields", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(firstName: String, secondName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) or TextUtils.isEmpty(secondName) or age.isEmpty())
    }
}