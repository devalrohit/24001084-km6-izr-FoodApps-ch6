package com.catnip.appfood_rohit.presentation.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catnip.appfood_rohit.data.model.PriceItem
import com.catnip.appfood_rohit.databinding.ItemPriceBinding
import com.catnip.appfood_rohit.utils.toRupiahFormat

class PriceListAdapter(private val itemClick: (PriceItem) -> Unit) :
    RecyclerView.Adapter<PriceListAdapter.PriceItemViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<PriceItem>() {
                override fun areItemsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem,
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<PriceItem>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PriceItemViewHolder {
        val binding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: PriceItemViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class PriceItemViewHolder(
        private val binding: ItemPriceBinding,
        val itemClick: (PriceItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: PriceItem) {
            with(item) {
                binding.tvItemPrice.text = item.total.toRupiahFormat()
                binding.tvItemTitle.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
