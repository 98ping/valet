package me.max.valet.storage

import java.util.concurrent.CompletableFuture

/**
 * Class created on 10/19/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
interface ArtifactStorageMechanism
{
    fun load()

    fun save(artifact: StorableArtifact): CompletableFuture<Void>

    fun getById(artifactID: String): StorableArtifact?
}