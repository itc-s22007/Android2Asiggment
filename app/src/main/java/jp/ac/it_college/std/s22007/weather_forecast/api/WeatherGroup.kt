package jp.ac.it_college.std.s22007.weather_forecast.api

import io.ktor.client.call.body

object WeatherGroup {
    suspend fun getGeneration(gen: Int){
        return Client.get("/gemeration/$gen/").body()
    }
}