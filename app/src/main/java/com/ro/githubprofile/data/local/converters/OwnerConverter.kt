package com.ro.githubprofile.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ro.githubprofile.data.remote.model.GHRepo

/**
 * OwnerConverter is a Room TypeConverter for converting complex `GHRepo.Owner` objects
 * to and from a JSON string format.
 *
 * Room doesn't support custom objects directly as column types in entities.
 * Therefore, this converter helps Room persist and retrieve the `Owner` object
 * by serializing it to a JSON string and deserializing it back when needed.
 *
 * Functions:
 * - [fromOwner]: Converts a `GHRepo.Owner` object to a JSON string using Gson.
 * - [toOwner]: Converts a JSON string back to a `GHRepo.Owner` object using Gson and TypeToken.
 *
 * This converter is typically registered in the Room database class using the `@TypeConverters` annotation.
 */
class OwnerConverter {

    /**
     * Converts a GHRepo.Owner object into its JSON string representation.
     *
     * @param owner The Owner object to convert.
     * @return A JSON string representation of the Owner.
     */
    @TypeConverter
    fun fromOwner(owner: GHRepo.Owner): String {
        return Gson().toJson(owner)
    }

    /**
     * Converts a JSON string back into a GHRepo.Owner object.
     *
     * @param ownerString The JSON string to deserialize.
     * @return The deserialized GHRepo.Owner object.
     */
    @TypeConverter
    fun toOwner(ownerString: String): GHRepo.Owner {
        return Gson().fromJson(ownerString, object : TypeToken<GHRepo.Owner>() {}.type)
    }
}