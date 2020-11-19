package me.saket.press.shared.sync

import me.saket.press.shared.AndroidParcelize
import me.saket.press.shared.sync.git.GitHost
import me.saket.press.shared.ui.ScreenKey

@AndroidParcelize
object SyncPreferencesScreenKey : ScreenKey

interface SyncPreferencesEvent {
  data class SetupHostClicked(val host: GitHost) : SyncPreferencesEvent
  object DisableSyncClicked : SyncPreferencesEvent
}

sealed class SyncPreferencesUiModel {
  data class SyncDisabled(
    val availableGitHosts: List<GitHost>
  ) : SyncPreferencesUiModel()

  data class SyncEnabled(
    val gitHost: GitHost,
    val remoteName: String,
    val remoteUrl: String,
    val status: String
  ) : SyncPreferencesUiModel()
}

sealed class SyncPreferencesUiEffect {
  data class OpenUrl(val url: String) : SyncPreferencesUiEffect()
}
