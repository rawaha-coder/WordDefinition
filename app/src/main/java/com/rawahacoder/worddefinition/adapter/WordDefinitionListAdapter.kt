package com.rawahacoder.worddefinition.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rawahacoder.worddefinition.databinding.SearchWordBinding
import com.rawahacoder.worddefinition.viewmodel.WordDefinitionsViewData

class WordDefinitionListAdapter(
    private var wordDefinitionsViewList: List<WordDefinitionsViewData>?,
    private val wordDefinitionListAdapterListener: WordDefinitionListAdapterListener,
    private val parentActivity: Activity
) : RecyclerView.Adapter<WordDefinitionListAdapter.ViewHolder>(){
    interface WordDefinitionListAdapterListener {
        fun onShowDetails(wordDefinitionsViewData: WordDefinitionsViewData)
    }

    inner class ViewHolder(
        databinding: SearchWordBinding,
        private val wordDefinitionListAdapterListener:
        WordDefinitionListAdapterListener
    ) : RecyclerView.ViewHolder(databinding.root) {
        var wordDefinitionsViewData: WordDefinitionsViewData? = null
        val definitionTextView: TextView = databinding.definitionTextView
        val exampleTextView: TextView = databinding.exampleTextView
        val synonymsTextView: TextView = databinding.synonymsTextView
        init {
            databinding.searchWord.setOnClickListener {
                wordDefinitionsViewData?.let {
                    wordDefinitionListAdapterListener.onShowDetails(it)
                }
            }
        }
    }

    fun setSearchData(wordDefinitionsViewData: List<WordDefinitionsViewData>) {
        wordDefinitionsViewList = wordDefinitionsViewData
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordDefinitionListAdapter.ViewHolder {
        return ViewHolder(
            SearchWordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false),
            wordDefinitionListAdapterListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position:
    Int) {
        val searchWordViewList = wordDefinitionsViewList ?: return
        val searchWordView = searchWordViewList[position]
        holder.wordDefinitionsViewData = searchWordView
        holder.definitionTextView.text = searchWordView.definition
        holder.exampleTextView.text = searchWordView.example
        holder.synonymsTextView.text = searchWordView.synonyms.toString()
    }

    override fun getItemCount(): Int {
        return wordDefinitionsViewList?.size ?: 0
    }


}