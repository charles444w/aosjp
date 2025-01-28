package jcp.apps

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
       val list = listOf("","")
        val r = "111,222,333".filter { it != ',' }
        println("resutl:${list.size}")
       // assertEquals(4, 2 + 2)
    }
}