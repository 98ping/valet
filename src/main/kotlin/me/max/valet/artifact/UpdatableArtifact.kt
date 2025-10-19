package me.max.valet.artifact

import color_console_log.ColorConsoleContext.Companion.colorConsole
import color_console_log.Colors
import me.max.valet.interaction.GithubInteractionHandler
import java.io.File
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

        val directory = File(location)

        if (!directory.exists())
        {
            colorConsole {
                printLine {
                    span(Colors.Red, "Unable to find this directory! Because of this, we are stopping the file scan.")
                }
            }

            colorConsole {
                printLine {
                    span(Colors.Red, "To resolve this error, please ensure the directory exists and your project is loaded there.")
                }
            }

            return
        }

        val changedFiles = GithubInteractionHandler.fetchChangedFiles(owner, repo, branch, changes)
        val historicStashDirectory = File(location, "stash")

        // make a stash to ensure that no problems occur
        if (!historicStashDirectory.exists())
        {
            historicStashDirectory.mkdirs()

            colorConsole {
                printLine {
                    span(Colors.Green, "Creating stash directory to save changes")
                }
            }
        }

        changedFiles.forEach { (path, content) ->
            if (content != null)
            {
                println("$path: ${content.size} bytes")

                val whereToWrite = File(location, path)

            } else
            {
                println("$path: marked for deletion")
                // Delete from disk here
            }
        }
    }
}