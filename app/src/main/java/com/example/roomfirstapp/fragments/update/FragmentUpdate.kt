package com.example.roomfirstapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomfirstapp.R
import com.example.roomfirstapp.databinding.FragmentUpdateBinding
import com.example.roomfirstapp.model.User
import com.example.roomfirstapp.viewModel.UserViewModel

class FragmentUpdate : Fragment() {
    private val args by navArgs<FragmentUpdateArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view: View = binding.root
        // Inflate the layout for this fragment
        binding.edFirstName.setText(args.currentUser.firstName)
        binding.edLastName.setText(args.currentUser.lastName)
        binding.edAge.setText(args.currentUser.age)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btAdd.setOnClickListener {
            updateItem()
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
        }


        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){
        val firstName = binding.edFirstName.text.toString()
        val lastName = binding.edLastName.text.toString()
        val age = binding.edAge.text

        if(inputCheck(firstName, lastName, age)){
            val updateUser = User(args.currentUser.id, firstName, lastName, age.toString())
            //update current user
            mUserViewModel.updateUser(updateUser)
            findNavController().navigate(R.id.action_fragmentUpdate_to_listFragment)
        }
        else
            Toast.makeText(requireContext(), "Fill all fields!", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(firstName: String, secondName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) or TextUtils.isEmpty(secondName) or age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "User has been deleted successfully", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_fragmentUpdate_to_listFragment)
        }

        builder.setNegativeButton("No"){_, _->

        }

        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete  ${args.currentUser.firstName}?")
        builder.create().show()
    }

}

