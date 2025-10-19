package me.max.valet.storage

import color_console_log.ColorConsoleContext.Companion.colorConsole
import color_console_log.Colors
import com.mongodb.client.MongoCollection
import me.max.valet.config.ValetConfigurationService
import me.max.valet.storage.mechanism.JsonStorageMechanism
import me.max.valet.storage.mechanism.MongoDatabaseStorageMechanism
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

/**
 * Class created on 10/19/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
object ArtifactStorageService
{
    private lateinit var mechanism: ArtifactStorageMechanism

    fun loadMechanisms()
    {
        val config = ValetConfigurationService.cached

        mechanism = if (config.mongoDbEnabled)
        {
            MongoDatabaseStorageMechanism()
        } else
        {
            JsonStorageMechanism()
        }

        colorConsole {
            printLine {
                span(Colors.Purple, "Found a mechanism for data storage: ${mechanism::class.qualifiedName}")
            }
        }

        mechanism.load()
    }

    fun getMechanism(): ArtifactStorageMechanism = mechanism
}