package com.agrebovod.motivator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.agrebovod.motivator.R
import com.agrebovod.motivator.observers.PhraseStorageObserver
import com.agrebovod.motivator.util.FileWorker
import com.agrebovod.motivator.util.PhraseStorage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var phraseStorage: PhraseStorage
    private var duration: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initImages()

        this.phraseStorage = PhraseStorage(FileWorker(this.applicationContext))
        phraseText.text = this.phraseStorage.getRandPhase()

        lifecycle.addObserver(PhraseStorageObserver(this.phraseStorage))

        duration = resources.getInteger(R.integer.animateDuration).toLong()
    }

    fun nextPhrase(view: View) {
        val text = findViewById<TextView>(R.id.phraseText)
        fadeIn(text)
    }

    private fun fadeIn(text: View) {
        text.animate()
            .setDuration(this.duration)
            .alpha(0f)
            .withEndAction {
                text.alpha = 0.0f
                textChange(text as TextView)
                fadeOut(text)
            }
    }

    private fun textChange(text: TextView) {
        text.text = phraseStorage.getRandPhase()
        val layout = findViewById<ConstraintLayout>(R.id.mainLayout)

        text.x = (0..layout.width - text.width).random().toFloat()
        text.y = (0..layout.height - text.height).random().toFloat()
    }

    private fun fadeOut(text: View) {
        val layout = findViewById<ConstraintLayout>(R.id.mainLayout)

        text.animate()
            .setDuration(this.duration)
            .alpha(1f)
            .withEndAction {
                text.alpha = 1f

                if (text.x + text.width > layout.width || text.y + text.height > layout.height || text.x < 0 || text.y < 0) {
                    var newX = 0f
                    if (text.x + text.width > layout.width) newX =
                        (layout.width.toFloat() - text.width)
                    else if (text.x < 0) newX = 10f
                    else newX = text.x

                    var newY = 0f
                    if (text.y + text.height > layout.height) newY =
                        layout.height.toFloat() - text.height
                    else if (text.y < 0) newY = 10f
                    else newY = text.y

                    text.animate()
                        .translationXBy(newX - text.x)
                        .translationYBy(newY - text.y)
                        .setDuration(1000)
                        .withEndAction {
                            text.x = newX
                            text.y = newY
                        }
                }
            }
    }

    private fun initImages() {

        // Возможные картинки
        val imageRes: Array<Int> = arrayOf(
            R.drawable.h1_1,
            R.drawable.h2_1,
            R.drawable.h3,
            R.drawable.h4,
            R.drawable.h5,
            R.drawable.h6,
            R.drawable.h7,
            R.drawable.h8
        )

        // TODO: Можно попробовать создавать программно
        val imageViews: Array<Int> = arrayOf(
            R.id.imageView,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5
        )

        for (i in imageViews) {
            val size = (25..100).random()
            val img = findViewById<ImageView>(i)
            img.alpha = 0.5f
            img.setImageResource(imageRes[(imageRes.indices).random()])

            val animation = AnimationUtils.loadAnimation(this, R.anim.scale)
            animation.duration = (1000..4000).random().toLong()
            img.startAnimation(animation)
        }
    }
}