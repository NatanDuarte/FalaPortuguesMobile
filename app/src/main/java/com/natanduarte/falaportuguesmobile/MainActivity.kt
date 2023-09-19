package com.natanduarte.falaportuguesmobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.natanduarte.falaportuguesmobile.databinding.ActivityMainBinding
import com.natanduarte.falaportuguesmobile.repository.MainRepository
import com.natanduarte.falaportuguesmobile.rest.RetrofitService
import com.natanduarte.falaportuguesmobile.util.JsonLoader
import com.natanduarte.falaportuguesmobile.viewmodel.main.MainViewModel
import com.natanduarte.falaportuguesmobile.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

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
        ).get(MainViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        viewModel.adjectives.observe(this) { adjectives ->
            Log.i("DEV", "onStart: $adjectives")

            val jsonLoader = JsonLoader(this)
            val jsonObject = jsonLoader.loadJsonFileToArray("job-nouns.json")
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAdjective()
    }
}