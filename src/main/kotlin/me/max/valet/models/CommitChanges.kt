package me.max.valet.github

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
data class CommitChanges(
    val added: List<String>,
    val modified: List<String>,
    val removed: List<String>,
    val renamed: Map<String, String>
)