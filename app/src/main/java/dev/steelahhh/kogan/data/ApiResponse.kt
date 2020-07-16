package dev.steelahhh.kogan.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val objects: List<Product>,
    val next: String?
) {
    @JsonClass(generateAdapter = true)
    data class Product(
        val category: String,
        val title: String,
        val weight: Double?, // Weight in grams
        val size: Size // Size in centimeters
    )

    @JsonClass(generateAdapter = true)
    data class Size(
        val width: Double?,
        val length: Double?,
        val height: Double?
    )
}
