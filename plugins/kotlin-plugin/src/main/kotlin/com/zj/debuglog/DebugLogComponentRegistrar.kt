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

import com.google.auto.service.AutoService
import com.zj.debuglog.DebugLogCommandLineProcessor.Companion.ARG_ANNOTATION
import com.zj.debuglog.DebugLogCommandLineProcessor.Companion.ARG_ENABLE
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(ComponentRegistrar::class)
class DebugLogComponentRegistrar : ComponentRegistrar {

  override fun registerProjectComponents(
    project: MockProject,
    configuration: CompilerConfiguration
  ) {
    if (!configuration.getBoolean(ARG_ENABLE)) {
      return
    }
    ClassBuilderInterceptorExtension.registerExtension(
      project,
      DebugLogClassGenerationInterceptor(
        debugLogAnnotations = configuration[ARG_ANNOTATION]
          ?: error("debuglog plugin requires at least one annotation class option passed to it")
      )
    )
  }
}
