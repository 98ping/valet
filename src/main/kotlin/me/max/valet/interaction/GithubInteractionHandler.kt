package me.max.valet.interaction

import me.max.valet.github.CommitChanges
import me.max.valet.github.GithubCommit
import me.max.valet.github.GithubContent
import me.max.valet.Valet
import me.max.valet.baseUrl
import me.max.valet.client
import me.max.valet.config.ValetConfigurationService
import okhttp3.Request
import java.util.*

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
object GithubInteractionHandler
{
    fun getLatestCommit(owner: String, repo: String, branch: String): GithubCommit
    {
        val url = "${baseUrl}/repos/$owner/$repo/commits/$branch"
        val request = buildRequest(url)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
            {
                throw Exception("Failed to fetch commit: ${response.code} - ${response.message}")
            }

            return Valet.Companion.instance.gson.fromJson(response.body?.string(), GithubCommit::class.java)
        }
    }

    fun getCommitsSince(owner: String, repo: String, branch: String, sha: String, limit: Int = 10): List<GithubCommit>
    {
        val url = "${baseUrl}/repos/$owner/$repo/commits?sha=$branch&since=$sha&per_page=$limit"
        val request = buildRequest(url)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
            {
                throw Exception("Failed to fetch commits: ${response.code} - ${response.message}")
            }

            return Valet.Companion.instance.gson.fromJson(response.body?.string(), Array<GithubCommit>::class.java).toList()
        }
    }

    fun getCommitChanges(owner: String, repo: String, commitSha: String): CommitChanges
    {
        val url = "${baseUrl}/repos/$owner/$repo/commits/$commitSha"
        val request = buildRequest(url)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
            {
                throw Exception("Failed to fetch commit details: ${response.code}")
            }

            val commit = Valet.Companion.instance.gson.fromJson(response.body?.string(), GithubCommit::class.java)
            val files = commit.files ?: emptyList()

            val added = mutableListOf<String>()
            val modified = mutableListOf<String>()
            val removed = mutableListOf<String>()
            val renamed = mutableMapOf<String, String>()

            files.forEach { file ->
                when (file.status)
                {
                    "added" -> added.add(file.filename)
                    "modified" -> modified.add(file.filename)
                    "removed" -> removed.add(file.filename)
                    "renamed" ->
                    {
                        file.previousFilename?.let { old ->
                            renamed[old] = file.filename
                        }
                        modified.add(file.filename)
                    }
                }
            }

            return CommitChanges(added, modified, removed, renamed)
        }
    }

    fun getChangesBetweenCommits(owner: String, repo: String, baseCommit: String, headCommit: String): CommitChanges
    {
        val url = "${baseUrl}/repos/$owner/$repo/compare/$baseCommit...$headCommit"
        val request = buildRequest(url)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
            {
                throw Exception("Failed to compare commits: ${response.code}")
            }

            val comparison = Valet.Companion.instance.gson.fromJson(response.body?.string(), GithubCommit::class.java)
            val files = comparison.files ?: emptyList()

            val added = mutableListOf<String>()
            val modified = mutableListOf<String>()
            val removed = mutableListOf<String>()
            val renamed = mutableMapOf<String, String>()

            files.forEach { file ->
                when (file.status)
                {
                    "added" -> added.add(file.filename)
                    "modified" -> modified.add(file.filename)
                    "removed" -> removed.add(file.filename)
                    "renamed" ->
                    {
                        file.previousFilename?.let { old ->
                            renamed[old] = file.filename
                        }
                        modified.add(file.filename)
                    }
                }
            }

            return CommitChanges(added, modified, removed, renamed)
        }
    }

    fun fetchChangedFiles(owner: String, repo: String, branch: String, changes: CommitChanges): Map<String, ByteArray?>
    {
        val files = mutableMapOf<String, ByteArray?>()

        // Fetch added files
        changes.added.forEach { path ->
            try
            {
                files[path] = fetchFile(owner, repo, branch, path)
                println("Fetched added file: $path")
            } catch (e: Exception)
            {
                println("Error fetching $path: ${e.message}")
            }
        }

        // Fetch modified files
        changes.modified.forEach { path ->
            try
            {
                files[path] = fetchFile(owner, repo, branch, path)
                println("Fetched modified file: $path")
            } catch (e: Exception)
            {
                println("Error fetching $path: ${e.message}")
            }
        }

        // Mark removed files with null
        changes.removed.forEach { path ->
            files[path] = null
            println("Marked for removal: $path")
        }

        return files
    }

    fun fetchFile(owner: String, repo: String, branch: String, path: String): ByteArray
    {
        val url = "${baseUrl}/repos/$owner/$repo/contents/$path?ref=$branch"
        val request = buildRequest(url)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
            {
                throw Exception("Failed to fetch file: ${response.code} - ${response.message}")
            }

            val content = Valet.Companion.instance.gson.fromJson(response.body?.string(), GithubContent::class.java)

            return when
            {
                content.content != null && content.encoding == "base64" ->
                {
                    Base64.getDecoder().decode(content.content.replace("\n", ""))
                }

                content.downloadUrl != null ->
                {
                    downloadFromUrl(content.downloadUrl)
                }

                else -> throw Exception("Unable to retrieve file content")
            }
        }
    }

    private fun downloadFromUrl(url: String): ByteArray
    {
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
            {
                throw Exception("Failed to download file: ${response.code}")
            }
            return response.body?.bytes() ?: throw Exception("Empty response body")
        }
    }

    private fun buildRequest(url: String): Request
    {
        val builder = Request.Builder().url(url)

        ValetConfigurationService.cached.token.let {
            builder.addHeader("Authorization", "Bearer $it")
        }

        builder.addHeader("Accept", "application/vnd.github.v3+json")

        return builder.build()
    }
}