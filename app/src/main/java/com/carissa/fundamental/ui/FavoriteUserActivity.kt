package com.carissa.fundamental.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.carissa.fundamental.data.response.ItemsItem
import com.carissa.fundamental.databinding.ActivityFavoriteUserBinding
import com.carissa.fundamental.viewmodel.FavoriteUserViewModel
import com.carissa.fundamental.viewmodel.FavoriteUserViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favRv.layoutManager = LinearLayoutManager(this)

        favoriteUserViewModel = getFavoriteViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.isLoading.observe(this) {
            loading(it)
        }
        favoriteUserViewModel.getAllFavoriteUser().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            val adapter = UserAdapter(items)
            binding.favRv.adapter = adapter
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem?) {
                    val intent = Intent(applicationContext, DetailUser::class.java)
                    intent.putExtra(DetailUser.DETAIL_USER, data)
                    startActivity(intent)
                }
            })
            loading(false)
        }
    }

    private fun getFavoriteViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = FavoriteUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    private fun loading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}