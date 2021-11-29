package com.rawahacoder.worddefinition.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.rawahacoder.worddefinition.databinding.ActivityMainBinding
import com.rawahacoder.worddefinition.repository.DictionaryRepo
import com.rawahacoder.worddefinition.service.Definitions
import com.rawahacoder.worddefinition.service.DictionaryService
import com.rawahacoder.worddefinition.service.ResultResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
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
    }

    private fun performSearch(word: String) {
        val dictionaryService = DictionaryService.instance
        val dictionaryRepo = DictionaryRepo(dictionaryService)
        GlobalScope.launch {
            val results = dictionaryRepo.searchWord(word)

            val dictionaryResult : ResultResponse? = results.body()?.get(0)

            val definitions: List<Definitions> =
                dictionaryResult?.meanings?.get(0)?.definitions ?: listOf()


            Log.i(TAG, "Results: definition = ${definitions.get(0).definition}"  )
            Log.i(TAG, "Results: example = ${definitions.get(0).example}"  )
            Log.i(TAG, "Results: synonyms = ${definitions.get(0).synonyms}"  )

        }
    }
}