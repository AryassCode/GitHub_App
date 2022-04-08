package com.udinn.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udinn.consumerapp.databinding.ItemUserBinding
import java.util.ArrayList

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    private val list = ArrayList<Account>()


    fun setList(account: ArrayList<Account>) {
        list.clear()
        list.addAll(account)
        notifyDataSetChanged()
    }

    inner class AccountViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {

            binding.apply {
                Glide.with(itemView)
                    .load(account.avatar_url)
                    .centerCrop()
                    .into(tvAvatar)
                tvUsername.text = account.login
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

}