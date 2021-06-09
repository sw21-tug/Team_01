package at.tu.graz.coffee.model

import org.junit.Test

import org.junit.Assert.*

class ReviewTest {

    @Test
    fun tasteTest() {
       val r = Review(10,10,10, "Comment",0);
        assertEquals(10, r.taste);
    }

    @Test
    fun costTest() {
        val r = Review(10,10,10, "Comment",0);
        assertEquals(10, r.cost);
    }

    @Test
    fun availabilityTest() {
        val r = Review(10,10,10, "Comment",0);
        assertEquals(10, r.availability);
    }

    @Test
    fun commentTest() {
        val r = Review(10,10,10, "Comment",0);
        assertEquals("Comment", r.comment);
    }
}