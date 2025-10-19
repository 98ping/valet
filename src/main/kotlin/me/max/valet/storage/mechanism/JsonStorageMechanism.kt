package me.max.valet.storage.mechanism

import me.max.valet.storage.ArtifactStorageMechanism
import me.max.valet.storage.StorableArtifact
import java.util.concurrent.CompletableFuture

/**
 * Class created on 10/19/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
class JsonStorageMechanism: ArtifactStorageMechanism
{
    override fun load()
    {
        TODO("Not yet implemented")
    }

    override fun save(artifact: StorableArtifact): CompletableFuture<Void>
    {
        TODO("Not yet implemented")
    }

    override fun getById(artifactID: String): StorableArtifact?
    {
        TODO("Not yet implemented")
    }
}