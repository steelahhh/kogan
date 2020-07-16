package dev.steelahhh.kogan.data

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching as ktRunCatching
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KoganAPI(
    private val httpClient: HttpClient,
    private val baseUrl: String
) {

    suspend fun getInitial(): Result<ApiResponse, Throwable> = get(INITIAL_PATH)

    suspend fun get(path: String) = ktRunCatching {
        httpClient.get<ApiResponse>("$baseUrl$path")
    }

    companion object {
        private const val INITIAL_PATH = "/api/products/1"
    }
}
