package com.rawahacoder.worddefinition.service

data class ResultResponse (
    val word : String,
    val phonetics : List<Phonetics>,
    val meanings : List<Meanings>
    )
data class Phonetics (
    val text : String,
    val audio : String
)

data class Meanings (
    val partOfSpeech : String,
    val definitions : List<Definitions>
)

data class Definitions (
    val definition : String,
    val example : String,
    val synonyms : List<String>
)



