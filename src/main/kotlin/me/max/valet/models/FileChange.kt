package me.max.valet.github

import com.google.gson.annotations.SerializedName

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
data class FileChange(
    val filename: String,
    val status: String,
    val additions: Int,
    val deletions: Int,
    @SerializedName("previous_filename") val previousFilename: String?,
    val sha: String,
    @SerializedName("blob_url") val blobUrl: String
)