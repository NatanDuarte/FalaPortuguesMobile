package com.natanduarte.falaportuguesmobile.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natanduarte.falaportuguesmobile.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(
    private val repository: MainRepository
) : ViewModel() {

    val adjectives = MutableLiveData<List<String>>()
    val errorMessage = MutableLiveData<String>()

    fun getAdjective() {
        val request = repository.getAdjective()

        request.enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                adjectives.postValue(response.body())
            }

            override fun onFailure(
                call: Call<List<String>>,
                t: Throwable
            ) {
                errorMessage.postValue(t.message)
            }
        })
    }
}