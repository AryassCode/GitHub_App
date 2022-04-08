package com.udinn.submission2.Main.favorite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.udinn.submission2.Main.detail.DetailAccountActivity
import com.udinn.submission2.Main.local.FavAccount
import com.udinn.submission2.Main.main.AccountAdapter
import com.udinn.submission2.Main.model.Account
import com.udinn.submission2.databinding.ActivityFavoriteBinding
import com.udinn.submission2.databinding.ActivityMainBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: AccountAdapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AccountAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : AccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Account) {
                Intent(this@FavoriteActivity, DetailAccountActivity::class.java).also {
                    it.putExtra(DetailAccountActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailAccountActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailAccountActivity.EXTRA_URL,data.avatar_url)
                    startActivity(it)
                }
            }

        })
        binding.apply {
            revUser.setHasFixedSize(true)
            revUser.adapter = adapter
            revUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)

        }
        viewModel.getFavAccount()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
                binding.revUser.visibility = View.VISIBLE
                showLoading(false)

                if (it.isEmpty()) {
                    binding.errorPage.visibility = View.VISIBLE
                } else {
                    binding.errorPage.visibility = View.GONE
                }
            }
        }
    }

    private fun mapList(accounts: List<FavAccount>): ArrayList<Account>{
        val listAccount = ArrayList<Account>()
        for (account in accounts){
            val accaountMap = Account(
                account.login,
                account.id,
                account.avatar_url
            )
            listAccount.add(accaountMap)
        }
        return listAccount
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.Loading.visibility = View.VISIBLE
        } else {
            binding.Loading.visibility = View.GONE

        }
    }
}