package com.ydh.androidfakeuser.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.ydh.androidfakeuser.*
import com.ydh.androidfakeuser.databinding.FragmentUserListBinding
import com.ydh.androidfakeuser.model.UserModel
import com.ydh.androidfakeuser.viewmodel.UserViewModel
import com.ydh.androidfakeuser.viewmodel.UserViewModelFactory


class UserListFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentUserListBinding
    private val myAdapter by lazy {
       context?.let { UserAdapter(it) }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentUserListBinding>(inflater,
                R.layout.fragment_user_list, container, false)

        setViewModel()
        setData()
        setView()
        setObserver()
        return binding.root
    }

    private fun setViewModel(){
        userViewModel = ViewModelProviders.of(this, UserViewModelFactory(this.context)).get(
            UserViewModel::class.java)
    }

    private fun setData(){
    }

    private fun setView(){
        binding.rvUserList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }
        binding.floatingActionButton.setOnClickListener {
            dialog()
        }

    }

    private fun setObserver(){
        userViewModel.getData().observe(viewLifecycleOwner, userDataObserver)
    }

    private val userDataObserver = Observer<PagedList<UserModel>> { t -> myAdapter?.submitList(t) }

    private fun dialog(){
        val dialog = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val x: View = inflater.inflate(R.layout.add_user, null)
        dialog.setView(x)
        val buttonAdd = x.findViewById<Button>(R.id.button_add)
        buttonAdd.setOnClickListener{
            val name = x.findViewById<EditText>(R.id.et_add_user_name)
            val job = x.findViewById<EditText>(R.id.et_add_user_job)
            userViewModel.createUser(CreateUserBody(name = name.text.toString(), job = job.text.toString()))
        }
        dialog.create()
        dialog.show()
    }

}