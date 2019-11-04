package com.slin.study.kotlin.test.grammer

interface Expr
class Num(val value: Int): Expr
class Sum(val left: Int, val right:Int):Expr

fun eval(e: Expr): Int{
    if (e is Num){
        return e.value
    } else if(e is Sum) {
        return e.left + e.right
    } else{
        throw IllegalArgumentException("Unknown expression")
    }
}

fun main() {
//    println(eval(eval(Num(1), Num(2)))
}