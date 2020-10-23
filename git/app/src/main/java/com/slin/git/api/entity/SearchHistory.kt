package com.slin.git.api.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*


/**
 * author: slin
 * date: 2020/10/23
 * description:
 *
 */
@Entity(tableName = "search_history")
@TypeConverters(SearchHistory.Converter::class)
data class SearchHistory(

    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,

    val type: SearchType = SearchType.REPOS,

    @ColumnInfo(name = "keyword", defaultValue = "")
    var keyword: String = "",

    ) {


    enum class SearchType {
        REPOS, USER, ISSUE
    }

    class Converter {
        @TypeConverter
        fun searchTypeToInt(type: SearchType): Int = type.ordinal

        @TypeConverter
        fun intToSearchType(ordinal: Int): SearchType =
            SearchType.values().first { ordinal == it.ordinal }
    }

}

object SearchHistoryDiff : DiffUtil.ItemCallback<SearchHistory>() {
    override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
        return oldItem == newItem
    }

}