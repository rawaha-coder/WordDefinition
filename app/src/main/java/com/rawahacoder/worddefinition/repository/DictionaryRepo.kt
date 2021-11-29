package com.rawahacoder.worddefinition.repository

import com.rawahacoder.worddefinition.service.DictionaryService

class DictionaryRepo(private val dictionaryService: DictionaryService) {
    suspend fun searchWord(word: String) = dictionaryService.searchWordDefinition(word)

}