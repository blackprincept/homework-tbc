package com.perullheim.homework.presentation.screen.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.perullheim.homework.R
import com.perullheim.homework.databinding.AccountViewHolderBinding
import com.perullheim.homework.presentation.model.UiAccount

class AccountsAdapter(
    private val onEvent: (AccountsUiEvent) -> Unit
) : ListAdapter<UiAccount, AccountsAdapter.AccountViewHolder>(ITEM_COMPARATOR) {

    inner class AccountViewHolder(private val binding: AccountViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: UiAccount) = with(binding) {
            tvAccountName.text = account.accountName
            tvCardType.text = account.cardType.displayName
            tvBalance.text = root.context
                .getString(R.string.balance, account.balance, account.currency.name)

            root.setOnClickListener {
                onEvent(AccountsUiEvent.OnAccountSelected(account))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = AccountViewHolderBinding.inflate(layoutInflater, parent, false)

        return AccountViewHolder(binding = viewBinding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiAccount>() {

    override fun areItemsTheSame(oldItem: UiAccount, newItem: UiAccount): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiAccount, newItem: UiAccount): Boolean {
        return oldItem == newItem
    }
}