package me.max.valet.artifact

import color_console_log.ColorConsoleContext.Companion.colorConsole
import color_console_log.Colors
import me.max.valet.interaction.GithubInteractionHandler
import java.util.concurrent.ScheduledExecutorService

/**
 * Class created on 10/17/2025

 * @author Max C.
 * @project valet
 * @website https://solo.to/redis
 */
class UpdatableArtifact(
    val id: String,
    val location: String,
    val owner: String,
    val repo: String,
    val branch: String
)
{
    fun scan()
    {
        val latestCommitHash = GithubInteractionHandler.getLatestCommit(owner, repo, branch)

        colorConsole {
            printLine {
                span(Colors.Green, "Scanning artifact: $id (${owner}/${repo})...")
            }
        }

        val changes = GithubInteractionHandler.getCommitChanges(owner, repo, latestCommitHash.sha)

        colorConsole {
            printLine {
                span(Colors.Green, "Added: ${changes.added}")
            }
        }

        colorConsole {
            printLine {
                span(Colors.Red, "Removed: ${changes.removed}")
            }
        }
        colorConsole {
            printLine {
                span(Colors.Yellow, "Modified: ${changes.modified}")
            }
        }

        colorConsole {
            printLine {
                span(Colors.Purple, "Renamed: ${changes.renamed}")
            }
        }

        val changedFiles = GithubInteractionHandler.fetchChangedFiles(owner, repo, branch, changes)
        changedFiles.forEach { (path, content) ->
            if (content != null)
            {
                println("$path: ${content.size} bytes")
                // Write to disk here
            } else
            {
                println("$path: marked for deletion")
                // Delete from disk here
            }
        }
    }
}