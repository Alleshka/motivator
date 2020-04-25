package com.agrebovod.motivator.util

import android.content.Context
import com.agrebovod.motivator.interfaces.IFileWorker
import com.agrebovod.motivator.model.FileInfo
import com.agrebovod.motivator.model.MotivatorData
import com.agrebovod.motivator.model.Phrase
import java.io.File
import java.io.FileWriter

class FileWorker(val context: Context) : IFileWorker {
    private val assetsVersionFile = "assetsVersion.txt"
    private val assetsPhrasesFile = "assetsPhrases.txt"

    private val metaInfoFileName = "metaInfo.txt"
    private val phrasesFileName = "phraseList.txt"

    override fun saveFile(fileInfo: FileInfo, phrases: List<Phrase>) {
        context.openFileOutput(this.phrasesFileName, Context.MODE_PRIVATE).bufferedWriter().use {
            for (phrase in phrases) {
                it.write(phrase.toString())
                it.newLine()
            }
        }

        context.openFileOutput(this.metaInfoFileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(fileInfo.toString())
        }
    }

    override fun saveFile(data: MotivatorData) {
        saveFile(data.fileInfo, data.phrases)
    }

    override fun readData(): MotivatorData {
        val assetsVersion = context.assets.open(this.assetsVersionFile).bufferedReader().use {
            return@use it.readLine().toInt()
        }

        val file = File(context.filesDir, metaInfoFileName)
        return if (file.exists()) {
            val fileInfo = FileInfo(file.bufferedReader().use {
                return@use it.readLine()
            })
            return if (fileInfo.curVersion >= assetsVersion) {
                MotivatorData(fileInfo, readFromFile(fileInfo))
            } else readFromAssets(assetsVersion)
        } else {
            readFromAssets(assetsVersion)
        }
    }

    // Считывает информацию из ассетов
    private fun readFromAssets(assetsVersion: Int): MotivatorData {
        val phraseList =
            context.assets.open(this.assetsPhrasesFile).bufferedReader().use phraseList@{
                context.openFileOutput(this.phrasesFileName, Context.MODE_PRIVATE).bufferedWriter()
                    .use { writer ->
                        val phraseList: MutableList<Phrase> = mutableListOf()
                        var phraseMeta: String?
                        while (true) {
                            phraseMeta = it.readLine()
                            if (phraseMeta == null) break
                            else {
                                val phrase = Phrase(phraseMeta, 0)
                                phraseList.add(phrase)
                                writer.write(phrase.toString() + "\n")
                            }
                        }
                        return@phraseList phraseList
                    }
            }

        val fileInfo = FileInfo(assetsVersion, 0)
        context.openFileOutput(this.metaInfoFileName, Context.MODE_PRIVATE).bufferedWriter()
            .use { writer ->
                writer.write(fileInfo.toString())
            }

        return MotivatorData(fileInfo, phraseList)
    }

    private fun readFromFile(fileInfo: FileInfo): List<Phrase> {
        return context.openFileInput(phrasesFileName).bufferedReader()
            .use { reader ->
                val phraseList: MutableList<Phrase> = mutableListOf()
                var str: String?
                while (true) {
                    str = reader.readLine()
                    if (str == null) break
                    else {
                        phraseList.add(Phrase(str, fileInfo.maxShowCount))
                    }
                }
                return@use phraseList
            }
    }
}