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
    var word: String? = "",
    var phonetic: String? = "",
    var exclamation: String? = "",
    var noun: String? = "",
    var verb: String? = "",
)

private fun resultResponseToWordDefinitionsView(resultResponse: ResultResponse): WordDefinitionsViewData {
    return when(resultResponse.meanings.size){
        3 -> WordDefinitionsViewData(
            "Word: ${resultResponse.word}",
            "Phonetic: ${resultResponse.phonetics[0].text}",
            "Exclamation: ${resultResponse.meanings[0].definitions[0].definition}",
            "Noun: ${resultResponse.meanings[0].definitions[0].definition}",
            "Verb: ${resultResponse.meanings[0].definitions[0].definition}")
        2 -> WordDefinitionsViewData(
            "Word: ${resultResponse.word}",
            "Phonetic: ${resultResponse.phonetics[0].text}",
            "Exclamation: ${resultResponse.meanings[0].definitions[0].definition}",
            "Noun: ${resultResponse.meanings[0].definitions[0].definition}")
        1 -> WordDefinitionsViewData(
            "Word: ${resultResponse.word}",
            "Phonetic: ${resultResponse.phonetics[0].text}",
            "Exclamation: ${resultResponse.meanings[0].definitions[0].definition}")

        else -> WordDefinitionsViewData()
    }
}

