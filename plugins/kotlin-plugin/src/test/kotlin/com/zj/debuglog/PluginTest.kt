/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zj.debuglog

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import junit.framework.Assert.assertEquals
import org.junit.Test


class PluginTest {
    private val main = SourceFile.kotlin(
        "main.kt", """
import com.zj.kcp_start.DebugLog
fun main() {
    doSomething()
}
@DebugLog
fun doSomething() {
    Thread.sleep(15)
}
"""
    )

    @Test
    fun simpleTest() {
        val result = compile(
            sourceFile = main,
            DebugLogComponentRegistrar(),
            DebugLogCommandLineProcessor(), listOf(
                PluginOption(BuildConfig.KOTLIN_PLUGIN_ID, "enabled", "true"),
                PluginOption(
                    BuildConfig.KOTLIN_PLUGIN_ID, "debugLogAnnotation", "com.zj.kcp_start.DebugLog"
                )
            )
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
        val out = invokeMain(result, "MainKt").trim().split("""\r?\n+""".toRegex())
        assert(out.size == 2)
        assert(out[0] == "doSomething 方法开始执行")
        assert(out[1] == "doSomething 方法执行结束")
    }
}