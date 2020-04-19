package com.agrebovod.motivator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.agrebovod.motivator.R
import com.agrebovod.motivator.util.FileWorker
import com.agrebovod.motivator.util.PhraseStorage

class MainActivity : AppCompatActivity() {

    private lateinit var phraseStorage: PhraseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.phraseStorage = PhraseStorage(FileWorker(this.applicationContext))
    }

    fun nextPhrase(view: View) {
        val phrase = phraseStorage.getRandPhase()
        Toast.makeText(this, phrase, Toast.LENGTH_LONG).show()
    }
}
