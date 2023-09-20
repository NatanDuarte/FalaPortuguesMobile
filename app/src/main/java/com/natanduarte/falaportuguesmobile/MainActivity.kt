package com.natanduarte.falaportuguesmobile

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.natanduarte.falaportuguesmobile.databinding.ActivityMainBinding
import com.natanduarte.falaportuguesmobile.repository.MainRepository
import com.natanduarte.falaportuguesmobile.rest.RetrofitService
import com.natanduarte.falaportuguesmobile.util.JsonLoader
import com.natanduarte.falaportuguesmobile.viewmodel.main.MainViewModel
import com.natanduarte.falaportuguesmobile.viewmodel.main.MainViewModelFactory
import java.util.*

class MainActivity(
    private var randomNoun: String = "",
    private var adjective: String = ""
) : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(retrofitService))
        )[MainViewModel::class.java]

        loadInitialData()

        binding.mainTerm.setOnClickListener {
            copyTextToClipboard(binding.mainTerm.text.toString())
        }

        binding.newTrend.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            randomNoun = getRandomNoun()
            viewModel.getAdjective()
        }

        binding.newAdjective.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getAdjective()
        }

        binding.newNoun.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            randomNoun = getRandomNoun()
            updateMainTerm()
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun loadInitialData() {
        randomNoun = getRandomNoun()

        viewModel.adjectives.observe(this) { adjectives ->
            adjective = adjectives[0]
            binding.progressBar.visibility = View.GONE
            updateMainTerm()
        }

        viewModel.errorMessage.observe(this) { message ->
            binding.progressBar.visibility = View.GONE
            Toast
                .makeText(this, message, Toast.LENGTH_SHORT)
                .show()
        }

        viewModel.getAdjective()
    }

    @SuppressLint("SetTextI18n")
    private fun updateMainTerm() {
        binding.mainTerm.text = "$adjective $randomNoun"
    }

    private fun copyTextToClipboard(textToCopy: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipData = ClipData.newPlainText("Texto copiado", textToCopy)
        clipboardManager.setPrimaryClip(clipData)

        Toast
            .makeText(this, "Texto copiado para a área de transferência", Toast.LENGTH_SHORT)
            .show()
    }

    private fun getRandomNoun(): String {
        val jsonLoader = JsonLoader(this)
        val jobNouns = jsonLoader.loadJsonFileToArray("job-nouns.json")

        val random = Random()
        val randomIndex = random.nextInt(jobNouns!!.length())
        return jobNouns[randomIndex].toString()
    }
}