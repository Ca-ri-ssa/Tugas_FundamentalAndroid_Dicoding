package com.carissa.fundamental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carissa.fundamental.data.response.DetailUserResponse
import com.carissa.fundamental.data.response.ItemsItem
import com.carissa.fundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserModel: ViewModel() {
    private val _itemsItem = MutableLiveData<List<ItemsItem?>?>()
    val itemsItem: LiveData<List<ItemsItem?>?> = _itemsItem

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUserDetail(username: String?){
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getDetailUser(username.toString()).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            )
            {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    _error.postValue("API Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _error.postValue("API Failure: ${t.message}")
            }
        })
    }
}