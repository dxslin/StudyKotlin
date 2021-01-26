package com.slin.study.kotlin.math

interface R {
    val origin: Vector3
}

class Ray2 : Ray() {

    override var origin: Vector3 = Vector3.zero()
        set(value) {
            super.origin = value
            field.set(value)
        }

}

/** Mathematical representation of a ray. Used to perform intersection and collision tests.  */
open class Ray : R {
    override var origin = Vector3()
        set(value) {
            field.set(value)
            println("set $field")
        }
        get() = Vector3(field)

    var direction: Vector3 = Vector3.forward()
        set(value) = field.set(value)
        get() = Vector3(field)


    /** Create a ray with an origin of (0,0,0) and a direction of Vector3.forward().  */
    constructor() {}

    /**
     * Create a ray with a specified origin and direction. The direction will automatically be
     * normalized.
     *
     * @param origin the ray's origin
     * @param direction the ray's direction
     */
    // Suppress @UnderInitialization warning.
    constructor(origin: Vector3, direction: Vector3) {
        println("constructor $origin $direction")
        this.origin = origin
        this.direction = direction
    }


    fun makeCopy(): Ray {
        return Ray(origin, direction)
    }

    /**
     * Set the origin of the ray in world coordinates.
     *
     * @param origin the new origin of the ray.
     */
//    fun setOrigin(origin: Vector3) {
//        this.origin.set(origin)
//    }

    /**
     * Get the origin of the ray.
     *
     * @return a new vector that represents the ray's origin
     */
//    fun getOrigin(): Vector3 {
//        return Vector3(origin)
//    }

    /**
     * Set the direction of the ray. The direction will automatically be normalized.
     *
     * @param direction the new direction of the ray
     */
//    fun setDirection(direction: Vector3) {
//        this.direction.set(direction.normalized())
//    }

    /**
     * Get the direction of the ray.
     *
     * @return a new vector that represents the ray's direction
     */
//    fun getDirection(): Vector3 {
//        return Vector3(direction)
//    }

    /**
     * Get a point at a distance along the ray.
     *
     * @param distance distance along the ray of the point
     * @return a new vector that represents a point at a distance along the ray.
     */
    fun getPoint(distance: Float): Vector3 {
        return Vector3.add(origin, direction.scaled(distance))
    }

    override fun toString(): String {
        return "${this.hashCode()} [Origin:$origin, Direction:$direction]"
    }
}