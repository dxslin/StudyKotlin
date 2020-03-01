package com.slin.study.kotlin.samples.`class`

import android.content.Context
import java.util.jar.Attributes


/**
 * author: slin
 * date: 2020-01-26
 * description: 类的构造函数
 */

fun main() {
    val user1 = User1("user1")
    println("user1: ${user1.nickname}")
    val user2 = User2("user2")
    println("user2: ${user2.nickname}")
    val user3 = User3("user3")
    println("user3: ${user3.nickname}")
    val user40 = User4("user4_0")
    println("user4_0: ${user40.nickname} isSubscribed: ${user40.isSubscribed}")
    val user41 = User4("user4_1", false)
    println("user4_1: ${user41.nickname} isSubscribed: ${user41.isSubscribed}")
    val user42 = User4("user4_2", isSubscribed = false)
    println("user4_2: ${user42.nickname} isSubscribed: ${user42.isSubscribed}")

    val subscribingUser = SubscribingUser("slin@qq.com")
    val subscribingUser2 = SubscribingUser2("slin@qq.com")
    println("subscribingUser nickname: ${subscribingUser.nickname}")
    println("subscribingUser2 nickname: ${subscribingUser2.nickname}")

    val people = People("slin")
    println("people.addressLength: ${people.addressLength}")
    people.address = "ChongQing, China"
    println("people.addressLength: ${people.addressLength}")
    people.age = 18
    println("people.age ${people.age}")


}

/*******************************三种类定义方式*********************************************/
/**
 * 定义User1类，带一个参数，这是完整的
 * 1. constructor 表示构造函数的关键字，可省略
 * 2. init 表示初始化语句块，调用构造函数时会调用，如果只是初始化属性，
 *      可以省略init块，通过参数来初始化属性，见User2
 */
@Suppress("JoinDeclarationAndAssignment")
class User1 constructor(_nickname: String) {
    val nickname: String

    init {
        nickname = _nickname
    }
}

/**
 * 定义User2类，带一个参数，简化版，省略 constructor 和 init 关键字
 * 1. 直接使用参数来初始化属性
 */
class User2(_nickname: String) {
    //属性初始化
    val nickname = _nickname

}

/**
 * 定义User类，带一个参数，最简版，实际情况是User1的定义形式
 * 1. “val”直接将属性和构造函数合为一体，构造函数中的参数即为类的属性
 *
 */
class User3(val nickname: String)


/*******************************三种类定义方式 结束*******************************************/

/*******************************start: 引用构造函数*******************************************/
/**
 * 为构造函数提供默认值
 * 注：如果所有参数都有默认值，那么编译器会定义一个无参的构造方法，使实例化变得更为简单
 */
class User4(val nickname: String, val isSubscribed: Boolean = true)

open class User(nickname: String)

/**
 * 继承User类，并且引用父类的构造方法
 */
class TwitterUser(nickname: String) : User(nickname)

/**
 * 1. 不写构造方法，会生成一个默认的无参构造方法
 * 2. 继承时需要显示的调用无参构造方法
 */
open class Dog

class BigDog : Dog()

/**
 * 创建多个构造函数的类
 */
open class View {
    constructor(context: Context)

    constructor(context: Context, attributes: Attributes)
}

/**
 * 继承View，并调用父类构造函数
 * 1. 调用父类构造函数通过“: super(参数..)”去调用
 * 2. 委托给自己的其他构造方法通过“: this(参数..)”调用
 */
class Button : View {

    constructor(context: Context, name: String) : this(context, Attributes()) {
        //do something
    }

    constructor(context: Context) : super(context) {
        //do something
    }

    constructor(context: Context, attributes: Attributes) : super(context, attributes) {
        //do something
    }

}

/******************************* end: 构造函数 *******************************************/


/***************************** start: 抽象属性 *******************************************/
/**
 * 在接口中声明抽象属性
 * 接口中的属性并不具有任何状态，需要实现类去存储
 */
interface IUser {
    val nickname: String
}

/**
 * 实现接口并重写接口属性
 */
class PrivateUser(override val nickname: String) : IUser

class SubscribingUser(val email: String) : IUser {
    //自定义getter
    override val nickname: String
        get() = email.substringBefore('@')
}

class SubscribingUser2(val email: String) : IUser {
    //初始化属性
    override val nickname: String = email.substringBefore('@')
}

/**
 * 带默认实现的抽象属性
 */
interface IUser2 {
    // email属性必须被重写
    val email: String
    // nickname拥有默认的getter，可以不用重写
    val nickname: String
        get() = email.substringBefore('@')
}

class PrivateUser2(override val email: String) : IUser2


/******************************** end: 抽象属性 ******************************************/


/***************************** start: setter/getter **************************************/

class People(val name: String) {

    /**
     * 重写setter函数
     * 1. 通过set关键字重写setter函数
     * 2. field关键字代表类的该属性，可以直接读取或者赋值
     */
    var address: String = "unspecified"
        set(value) {
            println(
                """
            Address was changed for $name:
                "$field" -> "$value". """.trimIndent()
            )
            field = value
            addressLength = field.length
        }

    /**
     * 设置setter的访问权限，不能在外部设置addressLength的值
     */
    var addressLength: Int = address.length
        private set

    /**
     * 重写setter和getter
     * 1. 通过get关键字重写getter函数，需要返回值
     */
    var age: Int = -1
        set(value) {
            println("set age: $value")
            field = value
        }
        get() {
            println("get age: $field")
            return field
        }
}

/******************************* end: setter/getter **************************************/

