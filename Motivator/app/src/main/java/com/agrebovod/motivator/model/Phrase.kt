package com.agrebovod.motivator.model

class Phrase {

    val phrase: String
    var probability: Double
    var showCount: Int

    var weight: Double
    var curProbability: Double

    var minRage: Double
    var maxRange: Double


    // Восстановление настроек из ассетов
    constructor(str: String, maxShowCount: Int) {
        val items = str.split(";")
        phrase = items[0]
        probability = items[1].toDouble()
        showCount = if (items.count() >= 3) items[2].toInt() else 0
        weight = calculateWeight(maxShowCount)
        curProbability = 0.0
        minRage = 0.0
        maxRange = 0.0
    }

    fun calculateRealProbability(coef: Double, curRange: Double): Double {
        this.minRage = curRange
        this.curProbability = weight * coef
        this.maxRange = this.minRage + curProbability
        return this.maxRange
    }

    fun isCurRage(curRange: Double): Boolean {
        return curRange >= this.minRage && curRange < this.maxRange
    }

    override fun toString(): String {
        return "$phrase;$probability;$showCount"
    }

    fun calculateWeight(maxShowCount: Int): Double {
        this.weight = probability * (maxShowCount - showCount + 1)
        return this.weight
    }
}