package com.example.wordlearning

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordlearning.databinding.ActivityHomeBinding
import com.example.wordlearning.model.WordData

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showWordList()

        binding.addWord.setOnClickListener {
            DialogUtils.showInputDialog(this) { word,level->
                Words.words.add(WordData(word,"",level))

                wordAdapter.notifyItemInserted(Words.words.size - 1)
            }
        }

    }

    private fun showWordList() {
        val recyclerView = binding.wordList
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordAdapter = WordAdapter(Words.words) {
            Toast.makeText(this, "Level $it", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = wordAdapter
    }

}