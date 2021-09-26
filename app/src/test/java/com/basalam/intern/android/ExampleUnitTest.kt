package com.basalam.intern.android

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun isSimilar() {

        val set1: MutableSet<Char> = HashSet()
        val set2: MutableSet<Char> = HashSet()

        for (c in "گرراز".toCharArray()) {
            set1.add(c)
        }
        for (c in "رز".toCharArray()) {
            set2.add(c)
        }

        // Stores the intersection of set1 and set2 inside set1
        set1.retainAll(set2)

        set1.forEach {
            println(it)
        }
    }


}