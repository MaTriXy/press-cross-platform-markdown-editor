package me.saket.press.shared.di

import com.russhwolf.settings.AppleSettings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import me.saket.press.PressDatabase
import me.saket.press.shared.sync.git.DeviceInfo
import me.saket.press.shared.sync.git.File
import platform.Foundation.NSUserDefaults

actual object SharedComponent : BaseSharedComponent() {
  fun initialize() {
    setupGraph(
      PlatformDependencies(
        sqlDriver = { sqliteDriver(it) },
        settings = { settings() },
        deviceInfo = { deviceInfo() }
      )
    )
  }

  private fun sqliteDriver(schema: SqlDriver.Schema) =
    NativeSqliteDriver(schema, "press.db")

  private fun settings() =
    AppleSettings(NSUserDefaults.standardUserDefaults())

  private fun deviceInfo() = object : DeviceInfo {
    override val appStorage get() = File("todo")
    override fun deviceName() = TODO()
  }
}
