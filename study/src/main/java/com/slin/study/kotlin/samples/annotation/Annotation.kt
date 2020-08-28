package com.slin.study.kotlin.samples.annotation

import ru.yole.jkid.*
import ru.yole.jkid.deserialization.deserialize
import ru.yole.jkid.serialization.serialize
import java.text.SimpleDateFormat
import java.util.*

/**
 * author: slin
 * date: 2020-03-18
 * description:  注解
 * 1. 同Java一样，使用`@`作为前缀引用注解
 * 2. 注解参数，指向一个类，需要在类名后面加上`::class`，如@MyAnnotation(MyClass::class)；
 *      把另一个注解指定给实参时，不需要@；
 *      指定数组为实参时，使用arrayOf，如@RequestMapping(path=arrayOf("/foo", "/bar")
 *      只有常量(const修饰)才可以作为参数，
 * 3. kotlin支持的点目标
 *      property —— Java的注解不用应用到这种使用点目标
 *      field —— 为属性生成的字段
 *      get —— 属性的getter
 *      set —— 属性的setter
 *      receiver —— 扩展函数或者扩展属性的接收参数
 *      param —— 构造方法的参数
 *      setparam —— 属性setter的参数
 *      delegate —— 为委托属性存储委托实例的字段
 *      file —— 包含在文件中声明的顶层函数或属性的类
 */

fun main() {

    val person = Person("slin", 24)
    val personJson = """{"age": 24, "name": "slin"}"""
    println("serialize(person): ${serialize(person)}")
    println("deserialize<Person>(personJson): ${deserialize<Person>(personJson)}")

    val personAnnotationSlin = PersonAnnotation("slin", 24)
    val personAnnotationTom = PersonAnnotation("tom")
    println("serialize(personAnnotationSlin): ${serialize(personAnnotationSlin)}")
    println("serialize(personAnnotationTom): ${serialize(personAnnotationTom)}")

    val company: Company = CompanyImpl("JetBrains")
    val personCompany = PersonCompany("Jon", company)
    val personCompanyJson = """{"company": {"name": "JetBrains"}, "name": "Jon"}"""
    println("serialize(personCompany): ${serialize(personCompany)}")
    println(
        "deserialize<PersonCompany>(personCompanyJson): ${deserialize<PersonCompany>(
            personCompanyJson
        )}"
    )

    val personDate = PersonDate("Gong", Date())
    val personDateJson = """{"birthDate": "2020-56-22", "name": "Gong"}"""
    println("serialize(personDate): ${serialize(personDate)}")
    println("deserialize<PersonDate>(personDateJson): ${deserialize<PersonDate>(personDateJson)}")

}

data class Person(val name: String, val age: Int)

data class PersonAnnotation(
    @JsonName("alias") val firstName: String,
    @JsonExclude val age: Int? = null
)

/**
 * 声明一个注解
 */
annotation class MyAnnotation

/**
 * 声明一个元注解，仅可用于注解注解类
 * Target注解表示注解的作用域
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

/**
 * 使用注解标注注解
 */
@BindingAnnotation
annotation class MyBinding

interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

/**
 * 使用注解是标注反序列化时创建的对象
 */
data class PersonCompany(
    val name: String,
    @DeserializeInterface(CompanyImpl::class) val company: Company
)

class DateSerializer : ValueSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
    override fun toJsonValue(value: Date): Any? {
        return dateFormat.format(value)
    }

    override fun fromJsonValue(jsonValue: Any?): Date {
        return dateFormat.parse(jsonValue as String)
    }
}

data class PersonDate(
    val name: String,
    @CustomSerializer(DateSerializer::class) val birthDate: Date
)