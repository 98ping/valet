package me.max.valet.storage.mechanism

import com.google.gson.reflect.TypeToken
import me.max.valet.Valet
import me.max.valet.storage.ArtifactStorageMechanism
import me.max.valet.storage.StorableArtifact
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Files
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

/**
 * Class created on 10/19/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
class JsonStorageMechanism: ArtifactStorageMechanism
{
    val file = File(
        File(
            Valet::class.java
                .protectionDomain
                .codeSource
                .location
                .toURI().path
        ).parentFile, "storable-artifacts.json"
    )
    val type: Type = object : TypeToken<MutableList<StorableArtifact>>()
    {}.type
    private val localCache: ConcurrentHashMap<String, StorableArtifact> = ConcurrentHashMap()

    override fun load()
    {
        if (file.exists())
        {
            val reader = Files.newBufferedReader(file.toPath(), Charsets.UTF_8)

            if (reader != null)
            {
                Valet.instance.gson.fromJson<MutableList<StorableArtifact>>(
                    reader,
                    type
                ).forEach {
                    localCache[it.id] = it
                }
            }
        } else
        {
            file.createNewFile()

            Files.writeString(
                file.toPath(),
                Valet.instance.gson.toJson(
                    mutableListOf<StorableArtifact>()
                ),
                Charsets.UTF_8
            )
        }
    }

    override fun save(artifact: StorableArtifact): CompletableFuture<Void>
    {
        localCache[artifact.id] = artifact

        Files.writeString(
            file.toPath(),
            Valet.instance.gson.toJson(
                localCache.values
            ),
            Charsets.UTF_8
        )

        return CompletableFuture.completedFuture(null)
    }

    override fun getById(artifactID: String): StorableArtifact? = localCache[artifactID]
}