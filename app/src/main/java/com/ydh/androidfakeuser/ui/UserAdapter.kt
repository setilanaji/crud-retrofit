package com.ydh.androidfakeuser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ydh.androidfakeuser.R
import com.ydh.androidfakeuser.databinding.UserItemBinding
import com.ydh.androidfakeuser.model.UserModel


class UserAdapter(private val context: Context) : PagedListAdapter<UserModel, UserAdapter.MyViewHolder>(
    USER_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding: UserItemBinding = DataBindingUtil.inflate(inflater,
                R.layout.user_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemBinding.user = getItem(position)
    }

    class MyViewHolder(val itemBinding: UserItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

        private var binding : UserItemBinding? = null

        init {
            this.binding = itemBinding
        }

    }
    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                newItem == oldItem
        }

        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(view: ImageView, profileImage: String) {
            Glide.with(view.context)
                .load(profileImage)
                .circleCrop()
                .into(view)
        }
    }

}