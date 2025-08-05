package com.example.wordlearning

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.wordlearning.model.WordLevels


object DialogUtils {
    @SuppressLint("MissingInflatedId")
    fun showInputDialog(context: Context, onWordEntered: (String, Int) -> Unit) {
        var level: Int = 0
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_input_word, null)
        val editText = view.findViewById<EditText>(R.id.etWord)
        val btnEnter = view.findViewById<TextView>(R.id.enter_button)
        val btnCancel = view.findViewById<TextView>(R.id.cancel_button)

        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .create()
        val iconList = mutableListOf<ImageView>()

        val container = view.findViewById<LinearLayout>(R.id.icon_container)
        val template = view.findViewById<ImageView>(R.id.icon_template)

        for ((index, iconRes) in WordLevels.wordLevelIcons.withIndex()) {
            val icon = ImageView(context).apply {
                layoutParams = template.layoutParams
                setImageResource(iconRes)
                alpha = 0.1f
                setOnClickListener {
                    level = index + 1
                    iconList.forEachIndexed { i, img ->
                        img.alpha = if (i == index) 1f else 0.1f
                    }
                }
            }
            iconList.add(icon)
            container.addView(icon)
        }

        btnEnter.setOnClickListener {
            val word = editText.text.toString().trim()
            if (word.isNotEmpty()) {
                onWordEntered(word,level)
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter a word", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}