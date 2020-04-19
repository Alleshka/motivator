package com.agrebovod.motivator.model

import kotlin.math.max

class FileInfo {

    var maxShowCount: Int
    val curVersion: Int

    constructor(curVersion: Int, maxShowCount: Int = 0) {
        this.maxShowCount = maxShowCount
        this.curVersion = curVersion
    }

    constructor(string: String) {
        val items = string.split(";")

        maxShowCount = items[0].toInt()
        curVersion = if (items.count() >= 2) {
            items[1].toInt()
        } else 0
    }

    override fun toString(): String {
        return "$maxShowCount;$curVersion"
    }
}