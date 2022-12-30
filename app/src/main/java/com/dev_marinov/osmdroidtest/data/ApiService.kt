package com.dev_marinov.osmdroidtest.data

import com.dev_marinov.osmdroidtest.data.dto.ResponseMapDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

//    https://api.weatherapi.com/v1/forecast.json?key=12cdbf51592a4e5a914112046221402&q=moscow
    // https://api.baza.net/public/info

    @GET("public/info")
    suspend fun getData(): Response<ResponseMapDTO>

}