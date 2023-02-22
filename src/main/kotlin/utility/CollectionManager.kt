package utility

import collection.Movie
import collection.MovieGenre
import java.time.LocalDateTime
import java.util.*
import kotlin.math.pow


class CollectionManager(private var fileManager: FileManager) {

    private var moviesCollection: TreeSet<Movie> = TreeSet<Movie>()
    private var lastInitTime: LocalDateTime? = null
    private var lastSaveTime: LocalDateTime? = null

    init {
        readCollection()
    }

    fun getCollection(): TreeSet<Movie> {
        return moviesCollection
    }

    fun getLastInitTime(): LocalDateTime? {
        return lastInitTime
    }

    fun getLastSaveTime(): LocalDateTime? {
        return lastSaveTime
    }

    fun getCollectionType(): String {
        return moviesCollection.javaClass.getName().substring(10..16)
    }

    fun getCollectionSize(): Int {
        return moviesCollection.size
    }

    fun getFirst(): Movie? {
        if (moviesCollection.isEmpty()) return null
        return moviesCollection.first()
    }

    fun getLast(): Movie? {
        if (moviesCollection.isEmpty()) return null
        return moviesCollection.last()
    }

    fun getById(id: Int): Movie? {
        var movie: Movie
        for (movie in moviesCollection) {
            if(movie.getId().equals(id)) return movie
        }
        return null
    }

    fun generateId(): Int {
        if (moviesCollection.isEmpty()) return 1
        return moviesCollection.last().getId() + 1
    }

    fun addObjectToCollection(movie: Movie) {
        moviesCollection.add(movie)
    }

    fun removeFromCollection(movie: Movie) {
        moviesCollection.remove(movie)
    }

    fun clearCollection() {
        moviesCollection.clear()
    }

    fun removeGreaterByOscars(oscars: Int): Int {
        var count = 0
        val iterator = moviesCollection.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.getOscarsCount() > oscars) {
                iterator.remove()
                count += 1
            }
        }
        return count
    }

    fun removeLowerByOscars(oscars: Int): Int {
        var count = 0
        val iterator = moviesCollection.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.getOscarsCount() < oscars) {
                iterator.remove()
                count += 1
            }
        }
        return count
    }

    fun compareOscarsWithMin(int: Int): Boolean {
        val oscars = int
        var minOscars = (2.0.pow(31 ) - 1).toInt()
        for (movie in moviesCollection) {
            minOscars = minOf(minOscars, movie.getOscarsCount())
        }
        return oscars < minOscars
    }

    fun getMinCountOfOscars(): Int {
        var minOscars = (2.0.pow(31) - 1).toInt()
        for (movie in moviesCollection) {
            minOscars = minOf(minOscars, movie.getOscarsCount())
        }
        return minOscars
    }

    fun getAverageOfOscars(): Double {
        val list = mutableListOf<Int>()
        for (movie in moviesCollection) {
            list.add(movie.getOscarsCount())
        }
        return list.average()
    }

    fun getCountOfGenreGreater(genreIn: MovieGenre): Int {
        val genre = genreIn
        var count = 0
        for (movie in moviesCollection) {
            if (genre.toString().length < movie.getGenre().toString().length) {
                count += 1
            }
        }
        return count
    }

    fun getOscarsCountsInDescending(): String {
        val list = mutableListOf<Int>()
        for (movie in moviesCollection) {
            list.add(movie.getOscarsCount())
        }
        list.sortDescending()
        return list.joinToString()
    }


    fun saveCollection() {
        fileManager.writeCollection(moviesCollection)
        lastSaveTime = LocalDateTime.now()
    }

    fun readCollection() {
        moviesCollection = fileManager.readCollection()
        lastInitTime = LocalDateTime.now()
    }

    override fun toString(): String {
        if (moviesCollection.isEmpty()) return "Коллекция пустая"

        var collection = ""
        for(movie in moviesCollection) {
            collection += movie
            if(movie != moviesCollection.last()) collection += "\n"
        }
        return collection
    }
}