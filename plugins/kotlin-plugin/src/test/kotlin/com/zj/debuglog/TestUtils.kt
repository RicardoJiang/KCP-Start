package com.zj.debuglog

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.lang.reflect.InvocationTargetException

fun compile(
    sourceFiles: List<SourceFile>,
    plugin: ComponentRegistrar,
    commandLineProcessor: CommandLineProcessor,
    pluginOptionList: List<PluginOption>
): KotlinCompilation.Result {
    return KotlinCompilation().apply {
        sources = sourceFiles
        useIR = true
        compilerPlugins = listOf(plugin)
        commandLineProcessors = listOf(commandLineProcessor)
        pluginOptions = pluginOptionList
        inheritClassPath = true
        verbose = false
    }.compile()
}

fun compile(
    sourceFile: SourceFile,
    plugin: ComponentRegistrar,
    commandLineProcessor: CommandLineProcessor,
    pluginOptionList: List<PluginOption>
): KotlinCompilation.Result {
    return compile(listOf(sourceFile), plugin, commandLineProcessor, pluginOptionList)
}


fun invokeMain(result: KotlinCompilation.Result, className: String): String {
    val oldOut = System.out
    try {
        val buffer = ByteArrayOutputStream()
        System.setOut(PrintStream(buffer, false, "UTF-8"))

        try {
            val kClazz = result.classLoader.loadClass(className)
            val main = kClazz.declaredMethods.single { it.name == "main" && it.parameterCount == 0 }
            main.invoke(null)
        } catch (e: InvocationTargetException) {
            throw e.targetException
        }

        return buffer.toString("UTF-8")
    } finally {
        System.setOut(oldOut)
    }
}

