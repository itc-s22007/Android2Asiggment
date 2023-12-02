package jp.ac.it_college.std.s22007.weather_forecast.api

import io.ktor.client.call.body

object WeatherGroup {
    suspend fun getGeneration(id: Int){
        return Client.get("/forecast?id=$id&appid=5efafcccbd7c2cecdbc6b629ae7d2528").body()

    }
}