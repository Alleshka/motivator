package com.agrebovod.motivator.observers

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.agrebovod.motivator.util.PhraseStorage


class PhraseStorageObserver(private val phraseStorage: PhraseStorage) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopped(source: LifecycleOwner) {
        phraseStorage.save()
        Log.i("saved", "data saved");
    }
}