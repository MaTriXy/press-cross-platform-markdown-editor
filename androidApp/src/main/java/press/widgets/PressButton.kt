package press.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color.BLACK
import android.graphics.Color.GRAY
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import me.saket.press.shared.theme.UiStyles
import me.saket.press.shared.theme.applyStyle
import press.theme.themeAware
import press.extensions.updatePadding

open class PressButton(context: Context, style: UiStyles.Text) : AppCompatButton(context) {
  init {
    minHeight = 0
    minimumHeight = 0
    isAllCaps = false
    applyStyle(style)
    updatePadding(horizontal = dp(16), vertical = dp(8))
    themeAware {
      background = pressButtonDrawable(it.buttonNormal, pressedColor = it.buttonPressed)
    }
  }

  override fun setTextColor(color: Int) {
    super.setTextColor(
      colorStateListOf(
        intArrayOf(-android.R.attr.state_enabled) to GRAY,
        intArrayOf() to color
      )
    )
  }
}

class PressBorderlessButton(context: Context, style: UiStyles.Text) : PressButton(context, style) {
  init {
    elevation = 0f
    stateListAnimator = null
    themeAware {
      background = pressButtonDrawable(TRANSPARENT, pressedColor = it.buttonPressed, rounded = false)
    }
  }
}

@Suppress("FunctionName")
fun View.pressButtonDrawable(color: Int, pressedColor: Int, rounded: Boolean = true): Drawable {
  val rippleColor = ColorStateList.valueOf(pressedColor)
  val shape = PaintDrawable(color).apply {
    if (rounded) {
      setCornerRadius(dp(20f))
    }
  }
  val mask = PaintDrawable(BLACK).apply {
    if (rounded) {
      setCornerRadius(dp(20f))
    }
  }
  return RippleDrawable(rippleColor, shape, mask)
}
