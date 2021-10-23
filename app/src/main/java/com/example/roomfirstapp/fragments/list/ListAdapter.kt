package com.example.roomfirstapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomfirstapp.R
import com.example.roomfirstapp.model.User
import kotlinx.android.synthetic.main.item_rc.view.*

class ListAdapter:RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var userList = emptyList<User>()
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rc, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.itemView.authorFirstName.text = currentItem.firstName
        holder.itemView.ID.text = currentItem.id.toString()
        holder.itemView.authorSecondName.text = currentItem.lastName
        holder.itemView.authAge.text = currentItem.age

        holder.itemView.rowLayout.setOnClickListener {//open UpdateFragment with SaveArgs
            val action = ListFragmentDirections.actionListFragmentToFragmentUpdate(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}