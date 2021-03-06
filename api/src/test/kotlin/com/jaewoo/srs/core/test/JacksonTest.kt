package com.jaewoo.srs.core.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class JacksonTest {
    val mapper = jacksonObjectMapper()
    //val mapper = ObjectMapper().registerModule(KotlinModule())


    @Test
    fun whenSerializeMovie_thenSuccess() {
        val movie = Movie("Endgame", "Marvel", 9.2f)
        val serialized = mapper.writeValueAsString(movie)

        val json = """{"name":"Endgame","studio":"Marvel","rating":9.2}"""
        assertEquals(serialized, json)
    }

    @Test
    fun whenDeserializeMovie_thenSuccess() {
        val json = """{"name":"Endgame","studio":"Marvel","rating":9.2}"""
        // val movie: Movie =  mapper.readValue(json)
        val movie = mapper.readValue<Movie>(json)

        assertEquals(movie.name, "Endgame")
        assertEquals(movie.studio, "Marvel")
        assertEquals(movie.rating, 9.2f)
    }

    @Test
    fun whenDeserializeMovieWithMissingValue_thenUseDefaultValue() {
        val json = """{"name":"Endgame","studio":"Marvel"}"""
        val movie: Movie = mapper.readValue(json)

        assertEquals(movie.name, "Endgame")
        assertEquals(movie.studio, "Marvel")
        assertEquals(movie.rating, 1f)
    }

    @Test
    fun whenSerializeMap_thenSuccess() {
        val map = mapOf(1 to "one", 2 to "two")
        val serialized = mapper.writeValueAsString(map)

        val json = """{"1":"one","2":"two"}"""
        assertEquals(serialized, json)
    }

    @Test
    fun whenDeserializeMap_thenSuccess() {
        val json = """{"1":"one","2":"two"}"""
        val aMap: Map<Int, String> = mapper.readValue(json)

        assertEquals(aMap[1], "one")
        assertEquals(aMap[2], "two")

        val sameMap = mapper.readValue<Map<Int, String>>(json)
        assertEquals(sameMap[1], "one")
        assertEquals(sameMap[2], "two")
    }

    @Test
    fun whenSerializeList_thenSuccess() {
        val movie1 = Movie("Endgame", "Marvel", 9.2f)
        val movie2 = Movie("Shazam", "Warner Bros", 7.6f)
        val movieList = listOf(movie1, movie2)
        val serialized = mapper.writeValueAsString(movieList)

        val json =
            """[{"name":"Endgame","studio":"Marvel","rating":9.2},{"name":"Shazam","studio":"Warner Bros","rating":7.6}]"""
        assertEquals(serialized, json)
    }

    @Test
    fun whenDeserializeList_thenSuccess() {
        val json =
            """[{"name":"Endgame","studio":"Marvel","rating":9.2},{"name":"Shazam","studio":"Warner Bros","rating":7.6}]"""
        val movieList: List<Movie> = mapper.readValue(json)

        val movie1 = Movie("Endgame", "Marvel", 9.2f)
        val movie2 = Movie("Shazam", "Warner Bros", 7.6f)
        assertTrue(movieList.contains(movie1))
        assertTrue(movieList.contains(movie2))

        val sameList = mapper.readValue<List<Movie>>(json)
        assertTrue(sameList.contains(movie1))
        assertTrue(sameList.contains(movie2))
    }
}

data class Movie(var name: String, var studio: String, var rating: Float? = 1f)