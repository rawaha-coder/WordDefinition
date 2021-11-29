package com.rawahacoder.worddefinition.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rawahacoder.worddefinition.adapter.WordDefinitionListAdapter

import com.rawahacoder.worddefinition.databinding.ActivityMainBinding
import com.rawahacoder.worddefinition.repository.DictionaryRepo
import com.rawahacoder.worddefinition.service.Definitions
import com.rawahacoder.worddefinition.service.DictionaryService
import com.rawahacoder.worddefinition.service.ResultResponse
import com.rawahacoder.worddefinition.viewmodel.SearchViewModel
import com.rawahacoder.worddefinition.viewmodel.WordDefinitionsViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), WordDefinitionListAdapter.WordDefinitionListAdapterListener {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var wordDefinitionListAdapter: WordDefinitionListAdapter

    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.searchButton.setOnClickListener{
            val word = binding.searchWord.text.toString()
            performSearch(word)
        }
        setupViewModels()
        updateControls()
    }

    private fun setupViewModels() {
        val service = DictionaryService.instance
        searchViewModel.dictionaryRepo = DictionaryRepo(service)
    }

    private fun updateControls() {
        binding.wordDefinitionRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding.wordDefinitionRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            binding.wordDefinitionRecyclerView.context,
            layoutManager.orientation)
        binding.wordDefinitionRecyclerView.addItemDecoration(dividerItemDecoration)
        wordDefinitionListAdapter = WordDefinitionListAdapter(null, this, this)
        binding.wordDefinitionRecyclerView.adapter = wordDefinitionListAdapter
    }

    private fun performSearch(word: String) {
        showProgressBar()
        GlobalScope.launch {
            val result = searchViewModel.searchWord(word)
            withContext(Dispatchers.Main) {
                hideProgressBar()
                wordDefinitionListAdapter.setSearchData(result)
            }

        }
    }

    override fun onShowDetails(wordDefinitionsViewData: WordDefinitionsViewData) {
        TODO("Not yet implemented")
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}