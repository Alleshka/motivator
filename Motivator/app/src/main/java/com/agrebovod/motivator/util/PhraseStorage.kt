package com.agrebovod.motivator.util

import com.agrebovod.motivator.interfaces.IFileStorage
import com.agrebovod.motivator.interfaces.IFileWorker
import com.agrebovod.motivator.model.FileInfo
import com.agrebovod.motivator.model.Phrase
import kotlin.random.Random

class PhraseStorage(private val fileWorker: IFileWorker) : IFileStorage {

    override fun save() {
        fileWorker.saveFile(fileInfo, phraseList)
    }

    private val phraseList: MutableList<Phrase>
    private val fileInfo: FileInfo

    init {
        val data = fileWorker.readData()

        this.phraseList = data.phrases.toMutableList()
        this.fileInfo = data.fileInfo

        this.calculateProbability(this.phraseList, fileInfo.maxShowCount)
    }

    override fun getRandPhase(): String? {
        val randNumber = Random.nextDouble(0.0, 100.0);
        val phrase: Phrase? = this.phraseList.find { it.isCurRage(randNumber.toDouble()) }

        if (phrase != null) {
            phrase.showCount++
            if (phrase.showCount > fileInfo.maxShowCount) {
                this.fileInfo.maxShowCount = phrase.showCount
                this.calculateProbability(this.phraseList, this.fileInfo.maxShowCount)
            }
            return phrase.phrase
        } else
            return "Попробуй ещё раз"
    }

    private fun calculateProbability(storage: List<Phrase>, maxShowCount: Int) {
        val weighSumms = storage.sumByDouble { it.calculateWeight(maxShowCount) }
        val showCoef = 100 / weighSumms

        var curRange = 0.0
        for (item in storage) {
            curRange = item.calculateRealProbability(showCoef, curRange)
        }
    }

}