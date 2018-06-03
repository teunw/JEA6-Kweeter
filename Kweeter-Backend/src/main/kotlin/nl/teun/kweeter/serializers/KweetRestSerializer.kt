package nl.teun.kweeter.serializers

import com.github.salomonbrys.kotson.get
import com.google.gson.*
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.services.ProfileService
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class KweetRestSerializer(private val profileService: ProfileService) : JsonDeserializer<Kweet>, JsonSerializer<Kweet> {

    private val dateTimeFormatter = DateTimeFormatter.ISO_INSTANT.withResolverStyle(ResolverStyle.LENIENT)

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Kweet {
        val p = profileService.findById(json["author"].asLong)
        return Kweet().apply {
            internalId = json["id"].asLong
            publicId = json["id"].asString
            textContent = json["textContent"].asString
            date = LocalDateTime.now()
            author = p
        }
    }

    override fun serialize(src: Kweet, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonObject().apply {
            addProperty("textContent", src.textContent)
            addProperty("id", src.internalId)
            addProperty("publicId", src.publicId)
            addProperty("author", src.author.id)
        }
    }
}