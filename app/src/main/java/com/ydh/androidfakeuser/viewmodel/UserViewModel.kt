package com.ydh.androidfakeuser.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ydh.androidfakeuser.*
import com.ydh.androidfakeuser.datasource.UserDataSource
import com.ydh.androidfakeuser.datasource.UserDataSourceFactory
import com.ydh.androidfakeuser.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel(private val context: Context) : ViewModel() {

    private var listUsers: LiveData<PagedList<UserModel>> = MutableLiveData<PagedList<UserModel>>()
    private var mutableLiveData = MutableLiveData<UserDataSource>()

    init {
        val factory: UserDataSourceFactory by lazy {
            UserDataSourceFactory(context)
        }
        mutableLiveData = factory.mutableLiveData

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(6)
            .build()

        listUsers = LivePagedListBuilder(factory, config)
            .build()

    }

    fun getData(): LiveData<PagedList<UserModel>> {
        return listUsers
    }

    private fun createUser(createUserBody: CreateUserBody){
        NetUtil.userApiService.createUser(createUserBody = createUserBody).enqueue(object : Callback<CreateUserResponse> {
            override fun onResponse(call: Call<CreateUserResponse>, response: Response<CreateUserResponse>) {
                if (response.code() == 200 ){

                }
            }

            override fun onFailure(call: Call<CreateUserResponse>, t: Throwable) {

            }

        })
    }

    private fun updateUser(updateUserBody: UpdateUserBody, id: Int){
        NetUtil.userApiService.updateUser(updateUserBody = updateUserBody, id = id).enqueue(object : Callback<UpdateUserResponse> {
            override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun deleteUser(id: Int){
        NetUtil.userApiService.deleteUser(id = id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}