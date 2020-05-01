package com.k0d4black.theforce.features.character_search;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.k0d4black.theforce.databinding.SearchResultLayoutItemBinding
import com.k0d4black.theforce.models.StarWarsCharacterUiModel
import kotlinx.android.synthetic.main.search_result_layout_item.view.*


class SearchResultAdapter(val onClick: (StarWarsCharacterUiModel) -> Unit) :
    ListAdapter<StarWarsCharacterUiModel, SearchResultAdapter.SearchedCharacterViewHolder>(
        SearchedCharacterDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedCharacterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return SearchedCharacterViewHolder(SearchResultLayoutItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: SearchedCharacterViewHolder, position: Int): Unit =
        getItem(position).let { holder.bind(it) }

    inner class SearchedCharacterViewHolder(private val binding: SearchResultLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(modelSearchStarWars: StarWarsCharacterUiModel) {
            binding.searchedCharacter = modelSearchStarWars
            binding.executePendingBindings()

            binding.root.more_info_arrow_image_button.setOnClickListener {
                onClick(modelSearchStarWars)
            }
        }
    }

    companion object {
        val SearchedCharacterDiffUtil =
            object : DiffUtil.ItemCallback<StarWarsCharacterUiModel>() {
                override fun areItemsTheSame(
                    oldItem: StarWarsCharacterUiModel,
                    newItem: StarWarsCharacterUiModel
                ): Boolean = oldItem.url == newItem.url


                override fun areContentsTheSame(
                    oldItem: StarWarsCharacterUiModel,
                    newItem: StarWarsCharacterUiModel
                ): Boolean = oldItem == newItem

            }
    }
}