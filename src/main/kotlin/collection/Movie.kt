package collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import java.util.*

object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: Date) {
        encoder.encodeLong(value.time)
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeLong())
    }
}

@Serializable
data class Movie(private var id: Int,
                 private var name: String,
                 private var coordinates: Coordinates,
                 @Serializable(DateSerializer::class)
                 private var creationDate: Date,
                 private var oscarsCount: Int,
                 private var genre: MovieGenre,
                 private var mpaaRating: MpaaRating,
                 private var director: Person
                 ): Comparable<Movie> {


    fun getId(): Int{
        return id
    }

    fun getName(): String{
        return name
    }

    fun getCoordinates(): Coordinates{
        return coordinates
    }

    fun getCreationDate(): Date{
        return creationDate
    }

    fun getOscarsCount(): Int {
        return oscarsCount
    }

    fun getGenre(): MovieGenre{
        return genre
    }

    fun getMpaaRating(): MpaaRating{
        return mpaaRating
    }

    fun getDirector(): Person{
        return director
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (name != other.name) return false
        if (coordinates != other.coordinates) return false
        if (creationDate != other.creationDate) return false
        if (oscarsCount != other.oscarsCount) return false
        if (genre != other.genre) return false
        if (mpaaRating != other.mpaaRating) return false
        if (director != other.director) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + coordinates.hashCode()
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + oscarsCount.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + mpaaRating.hashCode()
        result = 31 * result + director.hashCode()
        return result
    }

    override fun toString(): String {
        return "Movie(id=$id, name='$name', coordinates=$coordinates, creationDate=$creationDate, oscarsCount=$oscarsCount, genre=$genre, mpaaRating=$mpaaRating, director=$director)"
    }

    override fun compareTo(other: Movie): Int {
        return this.id.compareTo(other.getId())
    }
}