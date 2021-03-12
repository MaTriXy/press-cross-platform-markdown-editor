package press.widgets

import android.graphics.drawable.PaintDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import press.theme.themeAware

@Suppress("FunctionName")
fun View.DividerItemDecoration(): RecyclerView.ItemDecoration {
  return DividerItemDecoration(context, VERTICAL).apply {
    themeAware {
      setDrawable(DividerDrawable(it.separator))
    }
  }
}

class DividerDrawable(@ColorInt color: Int) : PaintDrawable(color) {
  override fun getIntrinsicWidth() = 1
  override fun getIntrinsicHeight() = 1
}
