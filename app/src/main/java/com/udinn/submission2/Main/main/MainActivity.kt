package com.udinn.submission2.Main.main


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.udinn.submission2.Main.*
import com.udinn.submission2.Main.detail.DetailAccountActivity
import com.udinn.submission2.Main.model.Account
import com.udinn.submission2.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AccountAdapter
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewMosel::class.java
        )
        adapter = AccountAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : AccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Account) {
                Intent(this@MainActivity, DetailAccountActivity::class.java).also {
                    it.putExtra(DetailAccountActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailAccountActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailAccountActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.apply {
            revUser.setHasFixedSize(true)
            revUser.adapter = adapter
            revUser.showContextMenu()
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                revUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                revUser.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            btnSearch.setOnClickListener {
                searchUser()
            }
            fab.setOnClickListener {
                Intent(this@MainActivity, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
            edQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)

                if (it.isEmpty()) {
                    binding.errorPage.visibility = View.VISIBLE
                } else {
                    binding.errorPage.visibility = View.GONE
                }
            }
        }
        viewModel.getSearchUsers()
    }

    private fun searchUser() {
        binding.apply {
            val query = edQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchAccount(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.coordinator1.visibility = View.VISIBLE
        } else {
            binding.coordinator1.visibility = View.GONE

        }
    }
}
