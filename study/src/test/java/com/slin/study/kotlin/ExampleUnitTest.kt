package com.slin.study.kotlin

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Volatile
    private var isOver = false

    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)

        println(test())
        println(number)


//        val thread = Thread(Runnable {
//            while (!isOver) {
//                println(System.nanoTime())
//            };
//        })
//        thread.start()
//        try {
//            Thread.sleep(500)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//        println("main ${System.nanoTime()}")
//        isOver = true
//        println("main ${System.nanoTime()}")
    }

    @Volatile
    var number = 0

    fun test(): Int {
        try {
            println("try...")
            number = 1
            return number
        } catch (e: Exception) {
            println("catch...")
        } finally {
            println("finally")
            number = 2
        }
        return 0
    }

    fun testReturn(): Int {
        println("testReturn")
//        throw ArithmeticException()
        return 111
    }

    @Test
    fun arrayTest() {
        val list = mutableListOf("1", "2", "3", "4", "5");
        var index = 0;
        for (value in list) {
            println(value)
            index++;
            if (index == 1) {
                list.remove(value)
            }
        }
    }

    data class Person(var name: String) : Cloneable {
        public override fun clone(): Any {
            return super.clone()
        }
    }

    @Test
    fun cloneTest() {
        val person = Person("slin")
        println(person)
        val clonePerson = person.clone() as Person
        println(clonePerson)

        clonePerson.name = "tom"
        println(clonePerson)
        println(person)


    }

}
