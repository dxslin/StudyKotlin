package com.slin.viewbinding

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * author: slin
 * date: 2021/1/27
 * description:
 *
 */


/**
 * # Activity ViewBinding 委托
 *
 * [classTransformer] 将 [Class<VB>] 转化为对应的对象 VB，其默认值为 [GenericClassTransformer]，它会查找
 * 您的[Activity]类的泛型参数，如果具有`XXViewBinding.bindView(View)`方法的话，调用其生成对应的[ViewBinding]
 *
 *
 * `constructor(clazz: Class<VB>)` 通过传入的`clazz`通过反射调用其`bindView(View)`方法创建对应的[ViewBinding]，
 * 而不是查找[Fragment]类的泛型参数
 *
 *
 */
class ActivityBindingDelegate<VB : ViewBinding>(
    private val classTransformer: ClassTransformer<Activity, VB> = GenericClassTransformer(
        activityViewBinding()
    ),
) : ReadOnlyProperty<Activity, VB> {

    constructor(clazz: Class<VB>) : this(
        DefaultClassTransformer<Activity, VB>(
            clazz,
            activityViewBinding()
        )
    )

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Activity, property: KProperty<*>): VB {
        if (!isInitialized) {
            _binding = classTransformer.transformer(thisRef)
            isInitialized = true
        }
        return binding
    }

}


/**
 * # Fragment ViewBinding 委托
 *
 * `XXViewBinding.inflate` 方法还要传 parent 对象就不好处理，
 * 因此我们用另一个生成的方法 `XXViewBinding.bind`]`，只需传个 [View]，在 [Fragment] 很好拿。
 * 另外还需要释放 binding 对象，不能用延时委托改用属性委托
 *
 * ### 注意：需要在Fragment的构造函数中传入布局文件id，否则会拿不到view
 *
 *
 * [classTransformer] 将 [Class<VB>] 转化为对应的对象 VB，其默认值为 [GenericClassTransformer]，它会查找
 * [Fragment]中的泛型参数，如果具有`XXViewBinding.bindView(View)`]`方法的话，调用其生成对应的[ViewBinding]
 *
 *
 * `constructor(clazz: Class<VB>)` 通过传入的`clazz`通过反射调用其`bindView(View)`方法创建对应的[ViewBinding]，
 * 而不是查找[Fragment]的泛型参数
 *
 * 在这个代理类中，会通过[Lifecycle]监听[Fragment.onDestroyView]方法，并销毁[ViewBinding]
 *
 */
class FragmentBindingDelegate<VB : ViewBinding>(
    private val classTransformer: ClassTransformer<Fragment, VB> = GenericClassTransformer(
        fragmentViewBinding()
    ),
) : ReadOnlyProperty<Fragment, VB> {

    constructor(clazz: Class<VB>) : this(
        DefaultClassTransformer<Fragment, VB>(
            clazz,
            fragmentViewBinding()
        )
    )

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    _binding = null
                }
            })
            _binding = classTransformer.transformer(thisRef)
            isInitialized = true

        }
        return binding
    }
}

/**
 * 创建一个转化器接口，在Activity或者Fragment中通过不同的实现类来创建ViewBinding
 */
interface ClassTransformer<in T : Any, VB : ViewBinding> {
    fun transformer(thisRef: T): VB
}

/**
 * 默认转换器
 *
 * [clazz] 传入生成的`XXViewBinding`的类型。
 *
 * [block] VB的生成代码
 *
 * 这里明明传入了`VB:ViewBinding`，为啥还要传入[clazz]，不能通过`VB`来获取类型吗？
 *
 * 答：不能，因为java/kotlin的泛型会被类型擦除，是获取不到`VB`具体的类型的；kotlin中通过`reified`可以保留泛型类型，
 * 但是它只能作用于内联方法上
 *
 */
class DefaultClassTransformer<in T : Any, VB : ViewBinding>(
    private val clazz: Class<VB>,
    private val block: (thisRef: T, Class<VB>) -> VB,
) :
    ClassTransformer<T, VB> {
    override fun transformer(thisRef: T): VB {
        return block(thisRef, clazz)
    }
}


/**
 * 泛型转换器
 *
 * 通过查找`thisRef:T`类上面的泛型参数来获取`Class<VB>`，因此使用这种转换器需要`T`类上面含有能够顺利通过[block]
 * 创建`ViewBinding`泛型参数
 *
 * 这里每次都去查找`class<VB>`，为啥不用缓存？
 *
 * 经测试如果map缓存时，当超过一定数量后，速度反而不如直接查找
 *
 */
class GenericClassTransformer<in T : Any, VB : ViewBinding>(private val block: (thisRef: T, Class<VB>) -> VB) :
    ClassTransformer<T, VB> {
    @Suppress("UNCHECKED_CAST")
    override fun transformer(thisRef: T): VB {
        return withGenericBindingClass(thisRef) { clazz ->
            block(thisRef, clazz)
        }
    }
}

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    var index = 0
    while (true) {
        try {
            return block.invoke(any.findGenericBindingClass(index))
        } catch (e: NoSuchMethodException) {
            index++
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <VB : ViewBinding> Any.findGenericBindingClass(index: Int): Class<VB> {
    val type = javaClass.genericSuperclass
    if (type is ParameterizedType && index < type.actualTypeArguments.size) {
        return type.actualTypeArguments[index] as Class<VB>
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}


private fun <VB : ViewBinding> activityViewBinding(): (Activity, Class<VB>) -> VB =
    { activity, clz ->
        clz.inflateViewBinding(activity.layoutInflater).apply {
            activity.setContentView(root)
        }
    }

private fun <VB : ViewBinding> fragmentViewBinding(): (Fragment, Class<VB>) -> VB =
    { fragment, clz ->
        clz.bindViewBinding(fragment.requireView())
    }
