package nl.teun.kweeter.serializers

import com.github.salomonbrys.kotson.get
import com.google.gson.*
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.KweetService
import java.lang.reflect.Type

class ProfileRestSerializer(
        val kweetService: KweetService
) : JsonSerializer<Profile>, JsonDeserializer<Profile> {
    override fun serialize(src: Profile, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonObject().apply {
            addProperty("id", src.id)
            addProperty("email", src.email)
            addProperty("username", src.username)
            addProperty("contactLink", src.contactLink)
            addProperty("bio", src.bio)
            addProperty("location", src.location)
        }
    }

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Profile {
        return Profile().apply {
            id = json["id"].asLong
            email = if (json["email"].isJsonNull) null else json["email"].asString
            username = if (json["username"].isJsonNull) null else json["username"].asString
            location = if (json["location"].isJsonNull) null else json["location"].asString
            bio = if (json["bio"].isJsonNull) null else json["bio"].asString
        }
    }
}