package com.slin.study.kotlin.math

import kotlin.math.max
import kotlin.math.min

/** A Vector with 3 floats.  */ // TODO: Evaluate consolidating internal math. Additional bugs: b/69935335
class Vector3 {
    var x = 0f
    var y = 0f
    var z = 0f

    /** Construct a Vector3 and assign zero to all values  */
    constructor() {
        x = 0f
        y = 0f
        z = 0f
    }

    /** Construct a Vector3 and assign each value  */
    // Suppress @UnderInitialization warning.
    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    /** Construct a Vector3 and copy the values  */
    // Suppress @UnderInitialization warning.
    constructor(v: Vector3) {
        set(v)
    }

    /** Copy the values from another Vector3 to this Vector3  */
    fun set(v: Vector3) {
        x = v.x
        y = v.y
        z = v.z
    }

    /** Set each value  */
    operator fun set(vx: Float, vy: Float, vz: Float) {
        x = vx
        y = vy
        z = vz
    }

    /** Set each value to zero  */
    fun setZero() {
        set(0f, 0f, 0f)
    }

    /** Set each value to one  */
    fun setOne() {
        set(1f, 1f, 1f)
    }

    /** Forward into the screen is the negative Z direction  */
    fun setForward() {
        set(0f, 0f, -1f)
    }

    /** Back out of the screen is the positive Z direction  */
    fun setBack() {
        set(0f, 0f, 1f)
    }

    /** Up is the positive Y direction  */
    fun setUp() {
        set(0f, 1f, 0f)
    }

    /** Down is the negative Y direction  */
    fun setDown() {
        set(0f, -1f, 0f)
    }

    /** Right is the positive X direction  */
    fun setRight() {
        set(1f, 0f, 0f)
    }

    /** Left is the negative X direction  */
    fun setLeft() {
        set(-1f, 0f, 0f)
    }

    fun lengthSquared(): Float {
        return x * x + y * y + z * z
    }

    fun length(): Float {
        return Math.sqrt(lengthSquared().toDouble()).toFloat()
    }

    override fun toString(): String {
        return "${this.hashCode()} [x=$x, y=$y, z=$z]"
    }

    /**
     * Uniformly scales a Vector3
     *
     * @return a Vector3 multiplied by a scalar amount
     */
    fun scaled(a: Float): Vector3 {
        return Vector3(x * a, y * a, z * a)
    }


    /** @hide
     */
//    override fun hashCode(): Int {
//        val prime = 31
//        var result = 1
//        result = prime * result + java.lang.Float.floatToIntBits(x)
//        result = prime * result + java.lang.Float.floatToIntBits(y)
//        result = prime * result + java.lang.Float.floatToIntBits(z)
//        return result
//    }

    companion object {
        /**
         * Adds two Vector3's
         *
         * @return The combined Vector3
         */
        fun add(lhs: Vector3, rhs: Vector3): Vector3 {
            return Vector3(lhs!!.x + rhs!!.x, lhs.y + rhs.y, lhs.z + rhs.z)
        }

        /**
         * Subtract two Vector3
         *
         * @return The combined Vector3
         */
        fun subtract(lhs: Vector3, rhs: Vector3): Vector3 {
            return Vector3(lhs.x - rhs.x, lhs.y - rhs.y, lhs.z - rhs.z)
        }

        /**
         * Get dot product of two Vector3's
         *
         * @return The scalar product of the Vector3's
         */
        fun dot(lhs: Vector3, rhs: Vector3): Float {
            return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z
        }

        /**
         * Get cross product of two Vector3's
         *
         * @return A Vector3 perpendicular to Vector3's
         */
        fun cross(lhs: Vector3, rhs: Vector3): Vector3 {
            val lhsX = lhs.x
            val lhsY = lhs.y
            val lhsZ = lhs.z
            val rhsX = rhs.x
            val rhsY = rhs.y
            val rhsZ = rhs.z
            return Vector3(
                lhsY * rhsZ - lhsZ * rhsY, lhsZ * rhsX - lhsX * rhsZ, lhsX * rhsY - lhsY * rhsX
            )
        }

        /** Get a Vector3 with each value set to the element wise minimum of two Vector3's values  */
        fun min(lhs: Vector3, rhs: Vector3): Vector3 {
            return Vector3(min(lhs.x, rhs.x), min(lhs.y, rhs.y), min(lhs.z, rhs.z))
        }

        /** Get a Vector3 with each value set to the element wise maximum of two Vector3's values  */
        fun max(lhs: Vector3, rhs: Vector3): Vector3 {
            return Vector3(max(lhs.x, rhs.x), max(lhs.y, rhs.y), max(lhs.z, rhs.z))
        }

        /** Get the maximum value in a single Vector3  */
        fun componentMax(a: Vector3): Float {
            return max(max(a.x, a.y), a.z)
        }

        /** Get the minimum value in a single Vector3  */
        fun componentMin(a: Vector3): Float {
            return min(min(a.x, a.y), a.z)
        }


        /** Gets a Vector3 with all values set to zero  */
        fun zero(): Vector3 {
            return Vector3()
        }

        /** Gets a Vector3 with all values set to one  */
        fun one(): Vector3 {
            val result = Vector3()
            result.setOne()
            return result
        }

        /** Gets a Vector3 set to (0, 0, -1)  */
        fun forward(): Vector3 {
            val result = Vector3()
            result.setForward()
            return result
        }

        /** Gets a Vector3 set to (0, 0, 1)  */
        fun back(): Vector3 {
            val result = Vector3()
            result.setBack()
            return result
        }

        /** Gets a Vector3 set to (0, 1, 0)  */
        fun up(): Vector3 {
            val result = Vector3()
            result.setUp()
            return result
        }

        /** Gets a Vector3 set to (0, -1, 0)  */
        fun down(): Vector3 {
            val result = Vector3()
            result.setDown()
            return result
        }

        /** Gets a Vector3 set to (1, 0, 0)  */
        fun right(): Vector3 {
            val result = Vector3()
            result.setRight()
            return result
        }

        /** Gets a Vector3 set to (-1, 0, 0)  */
        fun left(): Vector3 {
            val result = Vector3()
            result.setLeft()
            return result
        }
    }

}