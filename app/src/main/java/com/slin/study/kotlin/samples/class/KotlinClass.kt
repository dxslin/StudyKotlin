package com.slin.study.kotlin.samples.`class`

import java.io.Serializable

/**
 * author: slin
 * date: 2020-01-23
 * description: kotlin 类测试
 * 1. 类定义和接口定义相关
 * 2. 抽象类和类的继承
 * 3. 内部类和嵌套类
 * 4. 密封类，受限的类继承结构
 */

fun main() {
    //类和接口定义测试
    println("类和接口定义测试")
    val tv = TextView()
    tv.showoff()
    tv.setFocus(true)
    tv.click()

    //抽象类和类的继承
    println("\r\n抽象类和类的继承")
    val rb = RadioButton()
    rb.showoff()
    rb.selected(true)
    rb.animate()
    rb.stopAnimating()
    rb.disable()

    //内部类和嵌套类测试
    println("\r\n内部类和嵌套类测试")
    val cat = Cat()
    println("cat type: ${cat.getCurrentType().typeName()}")
    val type = Cat.CatType("cat")
    cat.restoreType(type)
    val smallCat = cat.SmallCat()
    smallCat.getType()

    //密封类测试
    println("\r\n密封类")
    println(
        eval(
            Expr.Sum(
                Expr.Num(
                    eval(
                        Expr.Sum(
                            Expr.Num(1), Expr.Num(1)
                        )
                    )
                ),
                Expr.Num(eval(Expr.Num(3)))
            )
        )
    )

}


/**
 * 定义一个接口
 */
interface Clickable {
    //定义一个普通的方法
    fun click()

    /**
     * 定义一个带默认实现的方法
     * 1. 与Java不同的是，不需要default关键字
     */
    fun showoff() = println("I'm Clickable")

}

interface Focusable {

    fun setFocus(focus: Boolean) {
        println("I'm ${if (focus) "get" else "lost"} focus")
    }

    fun showoff() = println("I'm Focusable")

}

/**
 * 定义一个类
 * 1. 使用冒号“:”代替了Java的extends和implements关键字
 * 2.
 */
class TextView : Clickable, Focusable {

    /**
     * 1. 重写的方法使用override关键字代替@Override注解，且override是必须的
     * 2.
     */
    override fun click() = println("TextView click")


    /**
     * 1. 如果两个接口均有同一个默认方法，那么继承的时候必须显示的重写该默认方法
     * 2. 使用super调用指定接口的父方法时，需要使用“<类名>”显示指出
     */
    override fun showoff() {
        super<Clickable>.showoff()
        super<Focusable>.showoff()
        println("I'm TextView")
    }
}

/**
 * 定义一个抽象类
 * 1. 与Java一样，使用abstract标注抽象类
 * 2. 抽象类默认是open的，可以被继承
 */
abstract class Animated {

    /**
     * 1. 抽象函数默认是open的，可以被重写
     */
    abstract fun animate()

    /**
     * 1. 非抽象函数默认是final的，需要使用open标注，才能重写
     */
    open fun stopAnimating() {
        println("stop animating")
    }

    /**
     * 1. 这个函数没有使用open标注，不能被重写
     */
    fun animateTwice() {}

}

/**
 * 1. 定义一个可以被继承的类
 * 2. Kotlin的类默认是final的，如果想要被继承需要使用open显示声明
 */
open class RichButton : Animated(), Clickable {

    /**
     * 1. 函数默认也是final的，是不能被重写的
     */
    fun disable() {
        println("RichButton is disable")
    }

    /**
     * 1. open声明的函数才能被重写
     */
    open fun selected(select: Boolean) {
        println("RichButton is ${if (select) "selected" else "unselected"}")
    }


    /**
     * 1. 重写的方法默认就是open的
     * 2. 如果不想子类重写，可以使用final显示声明
     */
    override fun click() {}

    final override fun showoff() {
        super.showoff()
        println("I'm RichButton")
    }

    override fun animate() = println("RichButton animate")


    override fun stopAnimating() = println("RichButton stop animating")

}

/**
 * 定义继承测试类
 */
class RadioButton : RichButton() {

    override fun selected(select: Boolean) {
        println("RadioButton is ${if (select) "selected" else "unselected"}")
    }

}


/************************* 小结：类中访问修饰符的意义 **********************************
 *  修饰符         相关成员                    评注
 *  final           不能被重写               类中成员默认使用
 *  open            可以被重写               需要明确声明
 *  abstract        必须被重写               只能在抽象类中使用，抽象成员不能有默认实现
 *  override       重写父类或接口成员        如果没有使用final声明，重写的方法默认是open的
 *
 ***********************************小结结束******************************************/

/**
 * 1. kotlin中类的基础属性和类型参数列表中用到的所有类或者函数的签名都有与这个类或者函数相同的可见性
 * 2. protected成员只能在类和它的子类中可见，同一个包中无法访问
 * 3. kotlin的外部内无法查看其内部或者嵌套类的private成员
 */

/************************* Kotlin中的可见修饰符 **************************************
 *  修饰符         类成员                    顶层声明
 *  public（默认）      所有地方可见             所有地方可见
 *  internal        模块中可见               模块中可见
 *  protected       子类中可见               ——
 *  private         类中可见                 文件中可见
 *
 ***********************************小结结束******************************************/


interface Type : Serializable {
    fun typeName(): String
}

interface Animal {

    fun getCurrentType(): Type

    fun restoreType(type: Type) {}

}

/**
 * 嵌套类和内部类
 *
 * 类B在另一个类A中声明      Java                Kotlin
 * 嵌套类                  static class B      class B
 * 内部类                  class B             inner class B
 */
class Cat : Animal {

    override fun getCurrentType(): Type =
        CatType("cat")

    override fun restoreType(type: Type) {
        super.restoreType(type)
        if (type is CatType) {
            println("Restore cat type: " + type.type)
        }
    }

    /**
     * 1. 定义嵌套类，其不持有外部类引用，相当于Java的static class
     */
    class CatType(val type: String) : Type {

        override fun typeName(): String = type
    }


    /**
     * 1. 定义内部类，内部类需要使用inner显示的声明，会持有外部类的引用
     */
    inner class SmallCat {

        /**
         * 1. 使用“this@外部类名”访问外部引用
         */
        fun getType() {
            val type = this@Cat.getCurrentType()
            println("small ${type.typeName()}")
        }

    }

}

/**
 * 定义密封类
 * 1. 使用关键字sealed声明密封类
 * 2. 在类中列出所有嵌套类
 */
sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(e: Expr): Int =
    //使用when判断不再需要else分支，如果列出密封类里面的所有可能性
    when (e) {
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.left) + eval(e.right)
        //如果写其他类型会报错
//        is TextView -> print("dd")
    }

