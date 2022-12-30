package com.dev_marinov.osmdroidtest.domain

import com.dev_marinov.osmdroidtest.domain.model.Data

interface IRepository {

   // suspend fun getCategories() : List<String>
    suspend fun getData() : Data?


}