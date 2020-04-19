package com.agrebovod.motivator.interfaces

import com.agrebovod.motivator.model.FileInfo
import com.agrebovod.motivator.model.MotivatorData
import com.agrebovod.motivator.model.Phrase

interface IFileWorker {
    fun readData(): MotivatorData

    fun saveFile(fileInfo: FileInfo, phrases: List<Phrase>)
    fun saveFile(data: MotivatorData)
}