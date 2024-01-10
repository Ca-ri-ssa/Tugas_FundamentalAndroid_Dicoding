package com.carissa.fundamental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carissa.fundamental.data.response.GithubResponse
import com.carissa.fundamental.data.response.ItemsItem
import com.carissa.fundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItem>?>()
    val userList: LiveData<List<ItemsItem>?> = _userList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUsers(username: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.searchUsers(username).enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            )
            {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    _userList.postValue(users as List<ItemsItem>?)
                } else {
                    // Handle API error
                    _error.postValue("API Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                // Handle API failure
                _error.postValue("API Failure: ${t.message}")
            }
        })
    }
}