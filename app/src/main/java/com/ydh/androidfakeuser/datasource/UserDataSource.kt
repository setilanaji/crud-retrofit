package com.ydh.androidfakeuser.datasource

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.ydh.androidfakeuser.*
import com.ydh.androidfakeuser.model.UserModel
import com.ydh.androidfakeuser.Util.isInternetAvailable
import com.ydh.androidfakeuser.Util.showProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserDataSource(private val context: Context) : PageKeyedDataSource<Int, UserModel>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserModel>
    ) {
        if (context.isInternetAvailable()) {
            getUsers(callback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserModel>) {
        if (context.isInternetAvailable()) {
            val nextPageNo = params.key + 1
            getMoreUsers(params.key, nextPageNo, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserModel>) {
        if (context.isInternetAvailable()) {
            val previousPageNo = if (params.key > 1) params.key - 1 else 0
            getMoreUsers(params.key, previousPageNo, callback)
        }
    }

    private fun getUsers(callback: LoadInitialCallback<Int, UserModel>) {

        context.showProgressBar()

        NetUtil.userApiService.getAllUser(1).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Util.hideProgressBar()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Util.hideProgressBar()
                val userResponse = response.body()
                val listUsers = userResponse?.listUser
                listUsers?.let { callback.onResult(it, null, 2) }
            }

        })

    }

    private fun getMoreUsers(
        pageNo: Int,
        previousOrNextPageNo: Int,
        callback: LoadCallback<Int, UserModel>
    ) {

        NetUtil.userApiService.getAllUser(pageNo).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val userResponse = response.body()
                val listUsers = userResponse?.listUser
                listUsers?.let { callback.onResult(it, previousOrNextPageNo) }
            }

        })

    }



}