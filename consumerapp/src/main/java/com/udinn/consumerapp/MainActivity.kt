package com.udinn.consumerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udinn.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AccountAdapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        adapter = AccountAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            revUser.setHasFixedSize(true)
            revUser.adapter = adapter
            revUser.layoutManager = LinearLayoutManager(this@MainActivity)

        }
        viewModel.setFavoriteAccount(this)
        viewModel.getFavAccount()?.observe(this) {
            if (it != null) {
                adapter.setList(it)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.Loading.visibility = View.VISIBLE
        } else {
            binding.Loading.visibility = View.GONE

        }
    }
}