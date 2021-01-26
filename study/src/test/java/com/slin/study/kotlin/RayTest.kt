package com.slin.study.kotlin

import com.slin.study.kotlin.math.Ray2
import com.slin.study.kotlin.math.Vector3
import org.junit.Test


/**
 * author: slin
 * date: 2021/1/12
 * description:
 *
 */
class RayTest {

    @Test
    fun test() {
//        val ray = Ray(Vector3(1.0f, 1.0f, 1.0f), Vector3(1.0f, 1.0f, 1.0f))
//        println(ray.origin)
//        ray.origin = Vector3(1.0f, 1.0f, 1.0f)
//        println(ray.origin)
//
//        val ray2 = ray.makeCopy()
//        println(ray2.origin)

        val ray2 = Ray2()
        ray2.origin = Vector3.up()

        println(ray2)

    }


}