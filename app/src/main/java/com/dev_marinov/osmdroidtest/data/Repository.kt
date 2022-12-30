package com.dev_marinov.osmdroidtest.data

import com.dev_marinov.osmdroidtest.domain.IRepository
import com.dev_marinov.osmdroidtest.domain.model.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService
) : IRepository {

    override suspend fun getData(): Data? {
        val response = apiService.getData()
        return response.body()?.data?.mapToDomain()
    }
}