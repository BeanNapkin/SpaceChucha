package pro.fateeva.spacechucha.utils

import android.view.View
import coil.request.ImageRequest

fun ImageRequest.Builder.fadeInOnLoad(view: View) {
    listener(onSuccess = { _, _ ->
        view.alpha = 0f
        view.animate()
            .alpha(1f)
            .setDuration(1000)
    })
}