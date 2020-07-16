package dev.steelahhh.kogan.ui

import dev.steelahhh.kogan.data.ApiResponse

/*
 * Author: steelahhh
 * 16/7/20
 */

data class ProductUi(
    val title: String,
    val category: String,
    val dimensions: String,
    val weight: String
)

fun ApiResponse.Product.toUi() = ProductUi(
    title = title,
    category = category,
    dimensions = size.toUi(),
    weight = weight?.let { it / 100.0 }?.toString() ?: ""
)

fun ApiResponse.Size.toUi(): String {
    return toMeters()?.let { (height, width, length) ->
        "H: $height m, W: $width m, L: $length m"
    } ?: ""
}

fun ApiResponse.Size.toMeters(): Triple<Double, Double, Double>? {
    if (width != null && height != null && length != null) {
        return Triple(height / 100.0, width / 100.0, length / 100.0)
    }
    return null
}
