package com.rawahacoder.worddefinition.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels

import com.rawahacoder.worddefinition.databinding.ActivityMainBinding
import com.rawahacoder.worddefinition.repository.DictionaryRepo
import com.rawahacoder.worddefinition.service.DictionaryService
import com.rawahacoder.worddefinition.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.searchButton.setOnClickListener{
            val word = binding.searchWord.text.toString()
            if (word.isEmpty()){
                return@setOnClickListener
            }
            performSearch(word)
            it.hideKeyboard()
        }
        setupViewModels()
    }

    private fun setupViewModels() {
        val service = DictionaryService.instance
        searchViewModel.dictionaryRepo = DictionaryRepo(service)
    }

    private fun performSearch(word: String) {
        showProgressBar()
        GlobalScope.launch {
            val result = searchViewModel.searchWord(word)
            withContext(Dispatchers.Main) {
                hideProgressBar()
                "Definition: ${result[0].definition}".also { binding.definitionView.text = it }
                "Example: ${result[0].example}".also { binding.exampleView.text = it }
                "Synonyms: ${result[0].synonyms}".also { binding.synonymsView.text = it }
            }

        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}