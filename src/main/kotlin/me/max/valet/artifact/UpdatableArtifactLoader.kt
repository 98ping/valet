package me.max.valet.artifact

import color_console_log.ColorConsoleContext.Companion.colorConsole
import color_console_log.Colors
import com.google.gson.reflect.TypeToken
import me.max.valet.Valet
import me.max.valet.artifactTemplate
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Files

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
object UpdatableArtifactLoader
{
    val file = File(
        File(
            Valet::class.java
                .protectionDomain
                .codeSource
                .location
                .toURI().path
        ).parentFile, "artifact-list.json"
    )
    val type: Type = object : TypeToken<MutableList<UpdatableArtifact>>()
    {}.type
    val cache = hashMapOf<String, UpdatableArtifact>()

    fun loadEntries()
    {
        if (file.exists())
        {
            val reader = Files.newBufferedReader(file.toPath(), Charsets.UTF_8)

            if (reader != null)
            {
                Valet.instance.gson.fromJson<MutableList<UpdatableArtifact>>(
                    reader,
                    type
                ).forEach {
                    cache[it.id] = it

                    colorConsole {
                        printLine {
                            span(Colors.Cyan, "Registered artifact: ${it.id} (${it.owner}/${it.repo})...")
                        }
                    }
                }
            }
        } else
        {
            file.createNewFile()

            Files.writeString(
                file.toPath(),
                Valet.instance.gson.toJson(
                    mutableListOf(artifactTemplate)
                ),
                Charsets.UTF_8
            )

            colorConsole {
                printLine {
                    span(Colors.Green, "Artifact configuration file has been created")
                }
            }
            colorConsole {
                printLine {
                    span(Colors.Cyan, "(Also added test artifact)")
                }
            }
        }
    }

    fun pollUpdates()
    {
        cache.values.forEach {
            it.scan()
        }
    }
}