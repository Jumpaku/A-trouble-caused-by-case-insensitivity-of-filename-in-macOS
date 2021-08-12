package jumpaku

import org.junit.Test
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat

class ConcatenateTest {

    @Serializable
    data class TestData(val input: List<String>, val result: String)

    val testDataJson = this::class.java.getResource("/jumpaku/testData.json").readText()
    val testData = Json.decodeFromString<TestData>(testDataJson)

    @Test
    fun testConcatenate() {
        println("concatenate")
        val actual = concatenate(testData.input)
        val expected = testData.result
        assertThat(actual,`is`(equalTo(expected)))
    }
}