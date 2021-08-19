package com.shortly.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shortly.R
import com.shortly.databinding.ItemHistoryBinding
import com.shortly.model.datamodel.HistoryModel
import com.shortly.view.listener.HistoryDeleteListener
import com.shortly.model.usecase.CopyToClipboardUseCase

class HistoryAdapter(
    private val historyDeleteListener: HistoryDeleteListener,
    private val copyToClipboardUseCase: CopyToClipboardUseCase
) : ListAdapter<HistoryModel, HistoryViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<HistoryModel>() {
            override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.imageDelete.setOnClickListener {
            val tag = it.tag as HistoryModel
            historyDeleteListener.onDelete(tag)
        }
        binding.buttonCopy.setOnClickListener {
            val btn = it as Button
            val tag = it.tag as HistoryModel
            if(tag.isCopied) {
                return@setOnClickListener
            }
            val isCopied = copyToClipboardUseCase.copy(tag.fullShortLink)
            if(isCopied.not()) {
                return@setOnClickListener
            }
            if(tag.isCopied.not()) {
                tag.isCopied = true
                it.setBackgroundColor(it.context.getColor(R.color.violet_dark))
                btn.text = it.context.getString(R.string.text_copied)
            }
            else {
                it.setBackgroundColor(it.context.getColor(R.color.cyan))
                btn.text = it.context.getString(R.string.text_copy)
            }
        }
        return HistoryViewHolder(binding)
    }
}

class HistoryViewHolder(
    private val binding: ItemHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryModel) {
        binding.imageDelete.tag = item
        binding.buttonCopy.tag = item

        binding.shortLink.text = item.originalLink
        binding.link.text = item.fullShortLink
    }
}