package me.max.valet.storage

import kotlinx.serialization.Serializable
import me.max.valet.artifact.UpdatableArtifact
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

/**
 * Class created on 10/19/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
@Serializable
data class StorableArtifact(
    val stub: String,
    val id: String,
    var lastCommitHash: String
)
{
    @BsonId
    val _id: String = stub

    companion object
    {
        fun from(
            updatableArtifact: UpdatableArtifact,
            lastCommitHash: String
        ): StorableArtifact = StorableArtifact(
            UUID.randomUUID()
                .toString()
                .substring(0, 8),
            updatableArtifact.id,
            lastCommitHash
        )
    }
}
