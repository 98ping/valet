package me.max.valet.github

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
data class GithubCommit(
    val sha: String,
    val commit: CommitInfo,
    val files: List<FileChange>?
)