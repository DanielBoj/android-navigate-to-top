package org.dboj.totopbutton

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory

class App: Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            .components {

            }
            .build()

    }
}