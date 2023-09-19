package com.natanduarte.falaportuguesmobile.repository

import com.natanduarte.falaportuguesmobile.rest.RetrofitService

class MainRepository constructor(
    private val retrofitService: RetrofitService
) {
    fun getAdjective() = retrofitService.getAdjective()
}
