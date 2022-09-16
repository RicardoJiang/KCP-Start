package com.zj.kcp_start

object DebugLogHelper {
    @JvmStatic
    fun startMethod(methodName: String) {
        println("$methodName 方法开始执行")
    }

    @JvmStatic
    fun stopMethod(methodName: String){
        println("$methodName 方法执行结束")
    }
}