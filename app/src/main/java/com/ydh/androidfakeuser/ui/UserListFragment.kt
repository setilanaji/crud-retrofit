package com.ydh.androidfakeuser.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ydh.androidfakeuser.*
import com.ydh.androidfakeuser.databinding.FragmentUserListBinding
import com.ydh.androidfakeuser.model.UserModel
import com.ydh.androidfakeuser.viewmodel.UserViewModel
import com.ydh.androidfakeuser.viewmodel.UserViewModelFactory


class UserListFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentUserListBinding
    private var isOpen = false
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

    private fun setViewModel() {
        userViewModel = ViewModelProviders.of(this, UserViewModelFactory(this.context)).get(
                UserViewModel::class.java)
    }

    private fun setData() {
    }

    private fun setView() {
        binding.root.run {
            val fabDelete = this.findViewById<FloatingActionButton>(R.id.fab1)
            val fabMain = this.findViewById<FloatingActionButton>(R.id.fab)
            val fabEdit = this.findViewById<FloatingActionButton>(R.id.fab2)
            val fabAdd = this.findViewById<FloatingActionButton>(R.id.fab3)
            val edit = this.findViewById<TextView>(R.id.textview_edit)
            val add = this.findViewById<TextView>(R.id.textview_add)
            val delete = this.findViewById<TextView>(R.id.textview_delete)
            val fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close);
            val fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open);
            val fabClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock);
            val fabAntiClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock);

            findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                if (isOpen) {
                    delete.visibility = View.INVISIBLE;
                    add.visibility = View.INVISIBLE;
                    edit.visibility = View.INVISIBLE;
                    fabDelete.startAnimation(fabClose);
                    fabEdit.startAnimation(fabClose);
                    fabAdd.startAnimation(fabClose);
                    fabMain.startAnimation(fabAntiClock);
                    fabDelete.isClickable = false;
                    fabEdit.isClickable = false;
                    fabAdd.isClickable = false;
                    isOpen = false;
                } else {
                    delete.visibility = View.VISIBLE;
                    add.visibility = View.VISIBLE;
                    edit.visibility = View.VISIBLE;
                    fabDelete.startAnimation(fabOpen);
                    fabEdit.startAnimation(fabOpen);
                    fabAdd.startAnimation(fabOpen);
                    fabMain.startAnimation(fabClock);
                    fabEdit.isClickable = true;
                    fabAdd.isClickable = true;
                    fabDelete.isClickable = true;
                    isOpen = true;
                }
            }
            fabAdd.setOnClickListener {
                dialogAdd()
            }
            fabEdit.setOnClickListener {
                dialogEdit()
            }
            fabDelete.setOnClickListener {
                dialogDelete()
            }
        }
        binding.rvUserList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }

    }

    private fun setObserver() {
        userViewModel.getData().observe(viewLifecycleOwner, userDataObserver)
    }

    private val userDataObserver = Observer<PagedList<UserModel>> { t -> myAdapter?.submitList(t) }

    private fun dialogAdd() {
        val dialog = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val x: View = inflater.inflate(R.layout.add_user, null)
        dialog.setView(x)
        val buttonAdd = x.findViewById<Button>(R.id.button_add)
        buttonAdd.setOnClickListener {
            val name = x.findViewById<EditText>(R.id.et_add_user_name)
            val job = x.findViewById<EditText>(R.id.et_add_user_job)
            userViewModel.createUser(CreateUserBody(name = name.text.toString(), job = job.text.toString()))
        }
        dialog.create()
        dialog.show()
    }

    private fun dialogDelete(){
        val dialog = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val x: View = inflater.inflate(R.layout.delete_user, null)
        dialog.setView(x)
        val buttonDelete = x.findViewById<Button>(R.id.button_delete)
        buttonDelete.setOnClickListener {
            val id = x.findViewById<EditText>(R.id.et_delete_user_id)
            userViewModel.deleteUser(id.text.toString().toInt())
        }
        dialog.create()
        dialog.show()
    }

    private fun dialogEdit(){
        val dialog = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val x: View = inflater.inflate(R.layout.update_user, null)
        dialog.setView(x)
        val buttonUpdate = x.findViewById<Button>(R.id.button_update)
        buttonUpdate.setOnClickListener {
            val name = x.findViewById<EditText>(R.id.et_update_user_name)
            val id = x.findViewById<EditText>(R.id.et_update_user_id)
            val job = x.findViewById<EditText>(R.id.et_update_user_job)
            userViewModel.updateUser(UpdateUserBody(name = name.text.toString(), job =  job.text.toString()), id = id.text.toString().toInt())
        }
        dialog.create()
        dialog.show()
    }

}