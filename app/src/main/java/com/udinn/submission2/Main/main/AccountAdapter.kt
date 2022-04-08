package com.udinn.submission2.Main.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udinn.submission2.Main.detail.DetailAccountActivity
import com.udinn.submission2.Main.model.Account
import com.udinn.submission2.databinding.ItemUserBinding
import java.util.ArrayList

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    private val list = ArrayList<Account>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(account: ArrayList<Account>) {
        list.clear()
        list.addAll(account)
        notifyDataSetChanged()
    }

    inner class AccountViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(account)
                binding.apply {
                    Glide.with(itemView)
                        .load(account.avatar_url)
                        .centerCrop()
                        .into(tvAvatar)
                    tvUsername.text = account.login
                }
            }
        }
    }


    override fun onCreateViewHolder(account: ViewGroup, viewType: Int): AccountViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(account.context), account, false)
        return AccountViewHolder((view))
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Account)
    }
}

