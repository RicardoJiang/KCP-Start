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

import com.bnorm.template.BuildConfig
import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@AutoService(CommandLineProcessor::class)
class DebugLogCommandLineProcessor : CommandLineProcessor {
  companion object {
    private const val OPTION_ENABLE = "enabled"
    private const val OPTION_ANNOTATION = "debugLogAnnotation"

    val ARG_ENABLE = CompilerConfigurationKey<Boolean>(OPTION_ENABLE)
    val ARG_ANNOTATION = CompilerConfigurationKey<List<String>>(OPTION_ANNOTATION)
  }

  override val pluginId: String = BuildConfig.KOTLIN_PLUGIN_ID

  override val pluginOptions: Collection<CliOption> = listOf(
    CliOption(
      optionName = OPTION_ENABLE, valueDescription = "<true|false>",
      description = "whether to enable the debuglog plugin or not"
    ),
    CliOption(
      optionName = OPTION_ANNOTATION, valueDescription = "<fqname>",
      description = "fully qualified name of the annotation(s) to use as debug-log",
      required = true, allowMultipleOccurrences = true
    )
  )

  override fun processOption(
    option: AbstractCliOption,
    value: String,
    configuration: CompilerConfiguration
  ) {
    return when (option.optionName) {
      OPTION_ENABLE -> configuration.put(ARG_ENABLE, value.toBoolean())
      OPTION_ANNOTATION -> configuration.appendList(ARG_ANNOTATION, value)
      else -> throw IllegalArgumentException("Unexpected config option ${option.optionName}")
    }
  }
}
