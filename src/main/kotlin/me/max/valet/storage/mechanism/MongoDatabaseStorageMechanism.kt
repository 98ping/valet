package me.max.valet.storage.mechanism

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import me.max.valet.config.ValetConfigurationService
import me.max.valet.storage.ArtifactStorageMechanism
import me.max.valet.storage.StorableArtifact
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOne
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/**
 * Class created on 10/19/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
class MongoDatabaseStorageMechanism : ArtifactStorageMechanism
{
    lateinit var collection: MongoCollection<StorableArtifact>
    private val localCache: ConcurrentHashMap<String, StorableArtifact> = ConcurrentHashMap()

    override fun load()
    {
        val client = KMongo.createClient(ValetConfigurationService.cached.mongoDbConnectionString)
        val database = client.getDatabase("Valet")

        collection = database.getCollection<StorableArtifact>("artifacts")
    }

    override fun save(artifact: StorableArtifact): CompletableFuture<Void> = CompletableFuture.runAsync {
        collection.updateOne(
            Filters.eq("_id", artifact.stub),
            artifact,
            UpdateOptions().upsert(true)
        )

        localCache[artifact.stub] = artifact
    }

    override fun getById(artifactID: String): StorableArtifact? =
        localCache.values.firstOrNull { it.id == artifactID }
            ?: collection.findOne(Filters.eq("id", artifactID))
}