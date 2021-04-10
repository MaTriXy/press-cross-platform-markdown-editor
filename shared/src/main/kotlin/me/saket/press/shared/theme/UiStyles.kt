package me.saket.press.shared.theme

import android.content.Context
import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.P
import android.text.TextUtils.TruncateAt.END
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import me.saket.press.shared.BuildConfig
import me.saket.press.shared.R
import me.saket.press.shared.theme.UiStyles.FontFamily.Regular
import me.saket.press.shared.theme.UiStyles.FontVariant.Bold
import me.saket.press.shared.theme.UiStyles.FontVariant.Italic
import me.saket.press.shared.theme.UiStyles.FontVariant.Normal

fun TextView.applyStyle(style: UiStyles.Text) {
  textSize = style.textSize
  typeface = style.font.asTypeface(context)
  setLineSpacing(0f, style.lineSpacingMultiplier)

  if (style.maxLines != null) {
    maxLines = style.maxLines
    ellipsize = END
  }
}

fun UiStyles.Font.asTypeface(context: Context): Typeface {
  val fontResId = when (family) {
    Regular -> R.font.work_sans
  }
  val fontFamily = ResourcesCompat.getFont(context, fontResId)

  return if (SDK_INT >= P) {
    val isItalic = variant.isItalic
    Typeface.create(fontFamily, variant.weight, isItalic)
  } else {
    if (BuildConfig.DEBUG && variant.weight > 400 && variant == Italic) {
      throw TODO("Find a way backward-compatible way to render composite styles (i.e., italic + bold)")
    }

    val styleInt = when (variant) {
      Normal -> Typeface.NORMAL
      Italic -> Typeface.ITALIC
      Bold -> Typeface.BOLD
    }
    Typeface.create(fontFamily, styleInt)
  }
}

@Suppress("FunctionName")
fun TextView(context: Context, style: UiStyles.Text): TextView {
  return TextView(context).apply { applyStyle(style) }
}
