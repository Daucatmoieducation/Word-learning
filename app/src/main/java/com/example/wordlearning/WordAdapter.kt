package com.example.wordlearning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordlearning.model.WordData
import com.example.wordlearning.model.WordLevels

class WordAdapter(
    private val words: List<WordData>,
    private val levelClicked: (Int) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    inner class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val word: TextView = view.findViewById(R.id.word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.word.text = word.word

        val container = holder.view.findViewById<LinearLayout>(R.id.container_level)
        val template = holder.view.findViewById<ImageView>(R.id.level_template)
        container.removeViews(2, container.childCount - 2)

        val iconList = mutableListOf<ImageView>()

        for ((index, iconRes) in WordLevels.wordLevelIcons.withIndex()) {
            val level = index + 1
            val icon = ImageView(holder.view.context).apply {
                layoutParams = template.layoutParams
                setImageResource(iconRes)
                visibility = View.VISIBLE
                alpha = if (word.levelSelected == index + 1) 1f else 0.1f

                setOnClickListener {
                    word.levelSelected = level
                    levelClicked(index + 1)

                    //update all icons
                    iconList.forEachIndexed { i, img ->
                        img.alpha = if (i == index) 1f else 0.1f
                    }
                }
            }
            iconList.add(icon)
            container.addView(icon)
        }
    }

}