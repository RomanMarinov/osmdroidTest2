package com.dev_marinov.osmdroidtest.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.osmdroidtest.databinding.ItemCategoriesBinding
import com.dev_marinov.osmdroidtest.domain.model.CityCam

class MapCategoriesAdapter(private val onClickCategory: (id: Int) -> Unit) :
    ListAdapter<CityCam, MapCategoriesAdapter.ViewHolder>(DetailDiffUtilCallback()) {

    private var mapCategories: List<CityCam> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemCategoriesBinding.inflate(inflater, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mapCategories[position])
    }

    override fun submitList(list: List<CityCam>?) {
        super.submitList(list)
        list?.let { this.mapCategories = it.toList() }
    }

    override fun getItemCount(): Int {
        return mapCategories.size
    }

    inner class ViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mapCategories: CityCam) {

            binding.tvItem.text = mapCategories.title
            binding.tvValue.text = mapCategories.count.toString()

            binding.clMain.setOnClickListener {
                onClickCategory(bindingAdapterPosition)
            }
        }
    }
}

class DetailDiffUtilCallback : DiffUtil.ItemCallback<CityCam>() {
    override fun areItemsTheSame(oldItem: CityCam, newItem: CityCam): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CityCam,
        newItem: CityCam
    ): Boolean {
        return oldItem == newItem
    }

}