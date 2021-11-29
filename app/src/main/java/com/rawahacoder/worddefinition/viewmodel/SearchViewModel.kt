package com.rawahacoder.worddefinition.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rawahacoder.worddefinition.repository.DictionaryRepo

class SearchViewModel(application: Application) :
    AndroidViewModel(application) {
    var dictionaryRepo: DictionaryRepo? = null
}