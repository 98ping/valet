package me.max.valet.github

import com.google.gson.annotations.SerializedName

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
data class GithubContent(
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val type: String,
    @SerializedName("download_url") val downloadUrl: String?,
    val content: String?,
    val encoding: String?
)