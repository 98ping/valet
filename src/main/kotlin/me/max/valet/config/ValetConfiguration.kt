package me.max.valet.config

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
data class ValetConfiguration(
    val token: String,
    val mongoDbEnabled: Boolean = false,
    val mongoDbConnectionString: String = "mongodb://localhost:27017",
)