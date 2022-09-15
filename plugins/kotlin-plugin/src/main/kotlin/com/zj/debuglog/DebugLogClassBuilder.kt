package com.zj.debuglog

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.js.descriptorUtils.nameIfStandardType
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.Type
import org.jetbrains.org.objectweb.asm.Type.LONG_TYPE
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter

internal class DebugLogClassBuilder(
    private val debugLogAnnotations: List<String>,
    delegateBuilder: ClassBuilder
) : DelegatingClassBuilder(delegateBuilder) {
    override fun newMethod(
        origin: JvmDeclarationOrigin,
        /* not used: */
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val original = super.newMethod(origin, access, name, desc, signature, exceptions)

        val function = origin.descriptor as? FunctionDescriptor ?: return original
        if (debugLogAnnotations.none { function.annotations.hasAnnotation(FqName(it)) }) {
            // none of the debugLogAnnotations were on this function; return the original behavior
            return original
        }

        return object : MethodVisitor(Opcodes.ASM5, original) {
            override fun visitCode() {
                super.visitCode()
                InstructionAdapter(this).onEnterFunction(function)
            }

            override fun visitInsn(opcode: Int) {
                when (opcode) {
                    // all of the opcodes that result in a return
                    Opcodes.RETURN, // void return
                    Opcodes.ARETURN, // object return
                    Opcodes.IRETURN, Opcodes.FRETURN, Opcodes.LRETURN, Opcodes.DRETURN // int, float, long, double return
                    -> {
                        InstructionAdapter(this).onExitFunction(function)
                    }
                }
                super.visitInsn(opcode)
            }
        }
    }
}

private fun InstructionAdapter.onEnterFunction(function: FunctionDescriptor) {
    visitLdcInsn("${function.name}")
    invokestatic("com/zj/kcp_start/DebugLogHelper", "startMethod", "(Ljava/lang/String;)V", false)
}

private fun InstructionAdapter.onExitFunction(function: FunctionDescriptor) {
    visitLdcInsn("${function.name}")
    invokestatic("com/zj/kcp_start/DebugLogHelper", "stopMethod", "(Ljava/lang/String;)V", false)
}