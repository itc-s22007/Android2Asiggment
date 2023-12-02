package jp.ac.it_college.std.s22007.weather_forecast.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Client {
        private const val BASE_URL = "https://weather/api/org/2.5/"
    private val ktor = HttpClient(CIO){
        engine {
            endpoint {
                connectTimeout = 5000
                requestTimeout = 5000
                socketTimeout = 5000
            }
        }
        install(ContentNegotiation){
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }
    suspend fun get(endpoint: String) = ktor.get{ url("$BASE_URL$endpoint")}
}