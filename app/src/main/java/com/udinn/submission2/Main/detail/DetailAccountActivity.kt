package com.udinn.submission2.Main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.udinn.submission2.databinding.ActivityDetailAccountBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailAccountActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL="extra_url"
    }

    private lateinit var binding: ActivityDetailAccountBinding
    private lateinit var viewModel: DetailAccountModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this).get(DetailAccountModel::class.java)

        if (username != null) {
            viewModel.setAccountDetail(username)
            showLoading(true)
        }
        viewModel.getAccountDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailAccountActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(tvProfile)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = it.followers
                    tvFollowing.text = it.following
                    Company.text = it.company
                    Location.text = it.location
                    tvRepo.text = it.public_repos
                    binding.coordinator3.visibility = View.VISIBLE
                    showLoading(false)
                }
            }
        }
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count!=null){
                    if (count>0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
        binding.toggleFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                    viewModel.addToFavorite(username!!, id, avatarUrl!!)
            }else{
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

        val detaiAdapter = DetaiAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = detaiAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.coordinator4.visibility = View.VISIBLE
        }else{
            binding.coordinator4.visibility = View.GONE
        }
    }

}
