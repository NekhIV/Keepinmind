package com.nehaev.keepinmind.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nehaev.keepinmind.R
import com.nehaev.keepinmind.models.Theme
import com.nehaev.keepinmind.util.ThemeListResource
import kotlinx.android.synthetic.main.list_item_category.view.*
import kotlinx.android.synthetic.main.list_item_theme.view.*

class ThemesAdapter(
    val list_item_theme_layout: Int? = null,
    val list_item_category_layout: Int? = null,
    val onBindViewHolderAction: ((ThemeListResource, ThemesViewHolder, Int) -> Unit?)? = null
    ) : RecyclerView.Adapter<ThemesAdapter.ThemesViewHolder>() {

    companion object {
        const val THEME_ITEM_TYPE = 1
        const val CATEGORY_ITEM_TYPE = 2
    }
    private val differCallback = object : DiffUtil.ItemCallback<ThemeListResource>() {
        override fun areItemsTheSame(
            oldItem: ThemeListResource,
            newItem: ThemeListResource
        ): Boolean {
            when(newItem) {
                is ThemeListResource.ThemeItem -> {
                    return oldItem.theme?.id == newItem.theme?.id
                }
                is ThemeListResource.CategoryItem -> {
                    return oldItem.categoryName == newItem.categoryName
                }
            }
        }

        override fun areContentsTheSame(
            oldItem: ThemeListResource,
            newItem: ThemeListResource
        ): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemViewType(position: Int): Int {
        val itemList = differ.currentList
        return when(itemList[position]) {
            is ThemeListResource.ThemeItem -> {
                THEME_ITEM_TYPE
            }
            is ThemeListResource.CategoryItem -> {
                CATEGORY_ITEM_TYPE
            }
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesViewHolder {

        val resource = when(viewType) {
            THEME_ITEM_TYPE -> {
                list_item_theme_layout ?: R.layout.list_item_theme
            }
            CATEGORY_ITEM_TYPE -> {
                list_item_category_layout ?: R.layout.list_item_category
            }
            else -> R.layout.list_item_theme
        }

        return ThemesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                resource,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) {
        val itemList = differ.currentList[position]

        // условие для совместимости с экраном тем
        if (onBindViewHolderAction != null) {
            onBindViewHolderAction?.apply {
                this(itemList, holder, position)
            }
        } else {
            when(itemList) {
                is ThemeListResource.ThemeItem -> {
                    holder.itemView.apply {
                        tvThemeName.text = itemList.theme?.name
                        tvQuestionCount.text = itemList.theme?.questionCnt.toString()
                        setOnClickListener {
                            onItemClickListener?.let { onItemClick ->
                                itemList.theme?.let { theme ->
                                    onItemClick(theme)
                                }
                            }
                        }
                    }
                }
                is ThemeListResource.CategoryItem -> {
                    holder.itemView.apply {
                        rvCategoryName.text = itemList.categoryName
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Theme) -> Unit)? = null

    fun setOnItemClickListener(listener: (Theme) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = differ.currentList.size

    inner class ThemesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}























