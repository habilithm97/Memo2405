package com.example.memo2405.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memo2405.R
import com.example.memo2405.databinding.ItemMemoBinding
import com.example.memo2405.room.Memo

/**
 * ListAdapter : RecyclerView.Adapter를 확장한 클래스
 -> 데이터의 변경 사항을 자동으로 처리하여 효율적으로 목록을 업데이트할 수 있도록 도와줌
 */
class MemoAdapter : ListAdapter<Memo, MemoAdapter.MemoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = DataBindingUtil.inflate<ItemMemoBinding>(LayoutInflater.from(parent.context), R.layout.item_memo, parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MemoViewHolder(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {
            binding.memo = memo
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Memo>() {
        override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem.id == newItem.id // 두 아이템이 같은지
        }

        override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem == newItem // 두 아이템의 데이터가 같은지
        }
    }
}