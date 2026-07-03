package com.jugomo.kmp_example

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1"
private const val CAT_API_KEY = "DEMO-API-KEY"

@Serializable
data class CatPhoto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
)

private val catApiClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}

suspend fun getRandomCatPhotos(): List<CatPhoto> {
    return catApiClient.get("$CAT_API_BASE_URL/images/search") {
        header("x-api-key", CAT_API_KEY)
        parameter("limit", 10)
        parameter("order", "RAND")
    }.body()
}
