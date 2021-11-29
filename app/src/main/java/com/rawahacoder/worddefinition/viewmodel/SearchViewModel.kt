package com.rawahacoder.worddefinition.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rawahacoder.worddefinition.repository.DictionaryRepo
import com.rawahacoder.worddefinition.service.ResultResponse

class SearchViewModel(application: Application) :
    AndroidViewModel(application) {
    var dictionaryRepo: DictionaryRepo? = null

    suspend fun searchWord(word: String): List<WordDefinitionsViewData>{
        val results = dictionaryRepo?.searchWord(word)

        if (results != null && results.isSuccessful) {

            val response = results.body()

            if (!response.isNullOrEmpty()) {

                return response.map { result ->
                    resultResponseToWordDefinitionsView(result)
                }
            }
        }
        return emptyList()
    }

}

data class WordDefinitionsViewData(
    var definition: String? = "",
    var example: String? = "",
    var synonyms: List<String> = listOf()
)

private fun resultResponseToWordDefinitionsView(
    resultResponse: ResultResponse): WordDefinitionsViewData {
    return WordDefinitionsViewData(
        resultResponse.meanings[0].definitions[0].definition,
        resultResponse.meanings[0].definitions[0].example,
        resultResponse.meanings[0].definitions[0].synonyms)
}

