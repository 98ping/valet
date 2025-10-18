package me.max.valet.config

import color_console_log.ColorConsoleContext.Companion.colorConsole
import color_console_log.Colors
import com.google.gson.reflect.TypeToken
import me.max.valet.Valet
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Files

/**
 * Class created on 10/17/2025
 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
object ValetConfigurationService {
    val file = File(
        File(
            Valet::class.java
                .protectionDomain
                .codeSource
                .location
                .toURI().path
        ).parentFile,
        "configuration.json"
    )
    val type: Type = object : TypeToken<ValetConfiguration>()
    {}.type

    var cached: ValetConfiguration = ValetConfiguration("changeme")

    fun loadConfiguration()
    {
        // Ensure parent directory exists
        file.parentFile?.mkdirs()

        if (file.exists())
        {
            val reader = Files.newBufferedReader(file.toPath(), Charsets.UTF_8)

            if (reader != null)
            {
                cached = Valet.instance.gson.fromJson(
                    reader,
                    type
                )

                colorConsole {
                    printLine {
                        span(Colors.Green, "Cached valet configuration file")
                    }
                }

                colorConsole {
                    printLine {
                        span(Colors.Blue, "Found your authorization token (${cached.token.substring(0, 12)})")
                    }
                }
            }
        } else
        {
            file.createNewFile()

            Files.writeString(
                file.toPath(),
                Valet.instance.gson.toJson(cached),
                Charsets.UTF_8
            )

            colorConsole {
                printLine {
                    span(Colors.Green, "Created valet configuration file")
                }
            }

            colorConsole {
                printLine {
                    span(Colors.Red, "Make sure you change your GitHub API Token before proceeding!")
                }
            }
        }
    }
}