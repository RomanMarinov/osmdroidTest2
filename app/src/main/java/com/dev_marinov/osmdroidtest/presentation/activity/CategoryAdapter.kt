package com.dev_marinov.osmdroidtest.presentation.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.osmdroidtest.databinding.ItemCategoriesBinding

class CategoryAdapter(private val onClick: (id: String) -> Unit) :
    ListAdapter<String, CategoryAdapter.ViewHolder>(DetailDiffUtilCallback()) {

    private var categories: List<String> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemCategoriesBinding.inflate(inflater, parent, false)
        return ViewHolder(listItemBinding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun submitList(list: List<String>?) {
        super.submitList(list)
        list?.let { this.categories = it.toList() }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(
        private val binding: ItemCategoriesBinding,
        onClick: (id: String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.tvone.text = category

            binding.constraintlayout.setOnClickListener {
                onClick(category)
            }

        }
    }

}

class DetailDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}
