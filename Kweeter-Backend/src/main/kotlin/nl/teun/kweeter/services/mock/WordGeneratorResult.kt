package nl.teun.kweeter.services.mock

class WordGeneratorResult {
    var data: List<WordGeneratorSentence> = mutableListOf()
}

class WordGeneratorSentence {
    var sentence: String = ""
}