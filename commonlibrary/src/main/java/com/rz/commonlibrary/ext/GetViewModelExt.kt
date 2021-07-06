package com.rz.commonlibrary.ext

import java.lang.reflect.ParameterizedType

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 12:10 下午
 * @Description:
 */

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}