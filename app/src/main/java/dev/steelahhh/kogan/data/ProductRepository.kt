package dev.steelahhh.kogan.data

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import dev.steelahhh.kogan.data.ApiResponse.Product

/*
 * Author: steelahhh
 * 16/7/20
 */

class ProductRepository(private val api: KoganAPI) {
    suspend fun getAirConditioners() = getAllProducts().fold({ products ->
        Ok(products
            // get only air conditioners from the list
            .filter { it.category.equals("Air Conditioners", true) }
            // filter out all the invalid products
            .filter { it.size.width != null && it.size.height != null && it.size.length != null }
        )
    }, { Err(it) })

    private suspend fun getAllProducts(): Result<List<Product>, Throwable> {
        val objects = mutableListOf<Product>()
        var nextEndPoint: String? = null

        /**
         * Handle the result of request:
         *  – if it was successful, add the objects, and set nextEndPoint
         *  - if error occurred, set nextEndPoint to null and return the accumulated objects
         */
        fun Result<ApiResponse, Throwable>.handle() = fold(
            {
                objects.addAll(it.objects)
                nextEndPoint = it.next
            },
            {
                nextEndPoint = null
            }
        )

        api.getInitial().handle()

        while (nextEndPoint != null) {
            api.get(nextEndPoint!!).handle()
        }

        return if (objects.isNotEmpty()) {
            Ok(objects)
        } else {
            Err(NoProductsException)
        }
    }
}
