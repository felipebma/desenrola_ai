package com.example.desenrolaai

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desenrolaai.databinding.BorrowViewBinding
import com.example.desenrolaai.model.Borrow

class BorrowAdapter(val clickListener: BorrowListener) :
    ListAdapter<Borrow, BorrowAdapter.ViewHolder>(BorrowDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: BorrowViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(borrow: Borrow, clickListener: BorrowListener) {
            binding.borrow = borrow
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BorrowViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BorrowDiffCallback : DiffUtil.ItemCallback<Borrow>() {
    override fun areItemsTheSame(oldItem: Borrow, newItem: Borrow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Borrow, newItem: Borrow): Boolean {
        return oldItem == newItem
    }
}

class BorrowListener(val clickListener: (borrow: Borrow) -> Unit) {
    fun onClick(borrow: Borrow) = clickListener(borrow)
}