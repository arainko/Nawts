package com.arainko.nawts

import org.apache.commons.validator.GenericValidator
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun dateRegexTest() {
        val testString = "jazda tirowcy 13/12/1999 ognia"
        val yearLastRegex = "[0-9]{2}[/][0-9]{2}[/][0-9]{4}".toRegex()
        val yearFirstRegex = "[0-9]{4}[/][0-9]{2}[/][0-9]{2}".toRegex()
        assertNull(yearFirstRegex.find(testString))
        assertNotNull(yearLastRegex.find(testString))
        assertTrue(GenericValidator.isDate(yearLastRegex.find(testString)!!.value, "dd/MM/yyyy", true))

    }
}
