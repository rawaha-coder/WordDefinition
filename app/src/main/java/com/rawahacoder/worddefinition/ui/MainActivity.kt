package com.rawahacoder.worddefinition.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.rawahacoder.worddefinition.R

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
                if (result.isEmpty()) {
                    binding.wordView.text = getString(R.string.result_not_found)
                    binding.phoneticView.text = ""
                    binding.exclamationView.text = ""
                    binding.nounView.text = ""
                    binding.verbView.text= ""
                }else{
                    binding.wordView.text = result[0].word
                    binding.phoneticView.text = result[0].phonetic
                    binding.exclamationView.text = result[0].exclamation
                    binding.nounView.text = result[0].noun
                    binding.verbView.text= result[0].verb
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}