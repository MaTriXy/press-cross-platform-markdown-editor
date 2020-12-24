package me.saket.press.shared.sync.git

import co.touchlab.stately.collections.IsoMutableList
import com.soywiz.klock.DateTime

internal interface SyncLogger {
  fun log(message: String)
  fun onSyncStart(fromDevice: String) = Unit
  fun onSyncComplete()
}

internal class SyncLoggers(private vararg val loggers: SyncLogger) : SyncLogger {
  override fun log(message: String) = loggers.forEach { it.log(message) }
  override fun onSyncStart(fromDevice: String) = loggers.forEach { it.onSyncStart(fromDevice) }
  override fun onSyncComplete() = loggers.forEach { it.onSyncComplete() }
}

object PrintLnSyncLogger : SyncLogger {
  override fun log(message: String) = println(message)
  override fun onSyncStart(fromDevice: String) = println("======================================")
  override fun onSyncComplete() = println("\n")
}

/**
 * Writes logs to a file so that they can optionally be shared by users to debug sync issues.
 * Git maintains its revision history so the logs are reset on every sync and Press does not
 * have to evict stale logs.
 */
internal class FileBasedSyncLogger(private val notesDirectory: File) : SyncLogger {
  private val buffer = IsoMutableList<String>()
  val file get() = File(notesDirectory, ".press/sync_log.txt")

  override fun log(message: String) {
    buffer.add(message)
  }

  override fun onSyncStart(fromDevice: String) {
    buffer.clear()
    log("Syncing notes with '$fromDevice' on ${DateTime.now()}")
  }

  override fun onSyncComplete() {
    log("Wrapping up sync (before push) on ${DateTime.now()}\n")
    file.write(buffer.joinToString(separator = "\n"))
    buffer.clear()
  }
}
