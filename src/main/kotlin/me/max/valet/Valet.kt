package me.max.valet

import color_console_log.ColorConsoleContext.Companion.colorConsole
import color_console_log.Colors
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.max.valet.artifact.UpdatableArtifactLoader
import me.max.valet.config.ValetConfigurationService
import me.max.valet.storage.ArtifactStorageService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Valet {
    companion object
    {
        lateinit var instance: Valet

        @JvmStatic
        fun main(args: Array<String>)
        {
            instance = Valet()

            instance.loadConfiguration()
            instance.keepAlive()
        }
    }

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    fun loadConfiguration()
    {
        colorConsole {
            printLine {
                span(Colors.Yellow, "Loading all configuration files")
            }
        }

        ValetConfigurationService.loadConfiguration()
        UpdatableArtifactLoader.loadEntries()
        ArtifactStorageService.loadMechanisms()
    }

    private fun keepAlive()
    {
        val scheduler = Executors.newScheduledThreadPool(1)

        scheduler.scheduleAtFixedRate({
            try {
                colorConsole {
                    printLine {
                        span(Colors.Yellow, "Checking for updates...")
                    }
                }
                UpdatableArtifactLoader.pollUpdates()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 0, 3, TimeUnit.MINUTES)

        colorConsole {
            printLine {
                span(Colors.Green, "Valet is now running. Press Ctrl+C to stop.")
            }
        }

        Thread.currentThread().join()
    }
}