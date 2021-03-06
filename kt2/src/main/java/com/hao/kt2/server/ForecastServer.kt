package com.hao.kt2.server

import com.hao.kt2.db.ForecastDb
import com.hao.kt2.domain.datasource.ForecastDataSource
import com.hao.kt2.domain.model.ForecastList

/**
 * @author Yang Shihao
 */
class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, days: Int): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode,days).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, days)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}