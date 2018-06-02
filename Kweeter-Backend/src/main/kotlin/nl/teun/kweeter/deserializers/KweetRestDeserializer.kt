package nl.teun.kweeter.deserializers

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.services.ProfileService
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class KweetRestDeserializer(private val profileService: ProfileService) : JsonDeserializer<Kweet> {

    private val dateTimeFormatter = DateTimeFormatter.ISO_INSTANT.withResolverStyle(ResolverStyle.LENIENT)

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Kweet {
        val p = profileService.findAll(Int.MAX_VALUE)
        return Kweet().apply {
            internalId = json["id"].asLong
            publicId = json["id"].asString
            textContent = json["textContent"].asString
            date = LocalDateTime.now()
            author = p.find { it.id == json["author"].asLong }
        }
    }
}