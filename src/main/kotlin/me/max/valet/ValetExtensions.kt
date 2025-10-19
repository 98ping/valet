package me.max.valet

import me.max.valet.artifact.UpdatableArtifact
import okhttp3.OkHttpClient

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */

val client = OkHttpClient()
val baseUrl = "https://api.github.com"

val artifactTemplate = UpdatableArtifact(
    "template",
    "/home/Template",
    "max",
    "valet",
    "master",
    mutableMapOf(
        "include" to listOf("*"),
        "exclude" to listOf(".idea/")
    ),
    "./start.sh"
)