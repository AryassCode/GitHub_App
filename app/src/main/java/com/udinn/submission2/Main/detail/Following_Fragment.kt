package com.udinn.submission2.Main.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udinn.submission2.Main.main.AccountAdapter
import com.udinn.submission2.Main.model.Account
import com.udinn.submission2.R
import com.udinn.submission2.databinding.FragmentFollowBinding

class Following_Fragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingAccountModel
    private lateinit var adapter: AccountAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailAccountActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = AccountAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object: AccountAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Account) {
                val intent = Intent(this@Following_Fragment.requireContext(), DetailAccountActivity::class.java)
                intent.putExtra(DetailAccountActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailAccountActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailAccountActivity.EXTRA_URL, data.avatar_url)
                startActivity(intent)
            }

        })

        binding.apply {
            revAccount.setHasFixedSize(true)
            revAccount.layoutManager = LinearLayoutManager(activity)
            revAccount.adapter = adapter
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingAccountModel::class.java
        )
        viewModel.setListFollowing(username)

        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                if (it.isEmpty()) {
                    binding.error2.visibility = View.VISIBLE
                } else {
                    binding.error2.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}