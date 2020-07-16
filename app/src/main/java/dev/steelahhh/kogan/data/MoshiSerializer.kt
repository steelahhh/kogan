package dev.steelahhh.kogan.data

import com.squareup.moshi.Moshi
import io.ktor.client.call.TypeInfo
import io.ktor.client.features.json.JsonSerializer
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.readText

/*
 * Author: steelahhh
 * 15/7/20
 */

class MoshiSerializer(
    private val moshi: Moshi = Moshi.Builder().build()
) : JsonSerializer {
    override fun read(type: TypeInfo, body: Input): Any {
        val text = body.readText()
        return moshi.adapter<Any>(type.reifiedType).fromJson(text) ?: Any()
    }

    override fun write(data: Any, contentType: ContentType): OutgoingContent {
        return TextContent(moshi.adapter(data.javaClass).toJson(data), contentType)
    }
}
