package ru.tomindapps.testideasworld

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.tomindapps.testideasworld.workers.PhotoLoader

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var pl: PhotoLoader

    @Before
    fun prepare(){
    pl = PhotoLoader()
    }

    @Test
    fun getStringFromApi() {
        val s= pl.parseJson().get(1).id
        println(s)
    }
}
