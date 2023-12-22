package io.ryandeford.playground

class Nulls {

    data class Data(val metadata: Metadata?)
    data class Metadata(val description: String?)

    fun isNull(obj: Any?): String {
        return when {
            obj == null -> "The object is null"
            else -> "The object is not null: $obj"
        }
    }

    fun getDescription(data: Data?): String? {
        return data?.metadata?.description
    }

    fun getDescriptionOrDefault(data: Data?): String? {
        return data?.metadata?.description ?: "This is a default metadata description"
    }
}