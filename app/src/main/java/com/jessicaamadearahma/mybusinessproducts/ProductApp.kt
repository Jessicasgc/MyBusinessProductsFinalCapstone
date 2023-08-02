package com.jessicaamadearahma.mybusinessproducts

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.jessicaamadearahma.mybusinessproducts.core.di.databaseModule
import com.jessicaamadearahma.mybusinessproducts.core.di.networkModule
import com.jessicaamadearahma.mybusinessproducts.core.di.repositoryModule
import com.jessicaamadearahma.mybusinessproducts.di.useCaseModule
import com.jessicaamadearahma.mybusinessproducts.di.viewModelModule
import com.jessicaamadearahma.mybusinessproducts.ui.settings.ModeSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import java.util.Locale

class ProductApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val choosenMode = preferences.getString("selected_mode", getString(R.string.pref_auto))
        AppCompatDelegate.setDefaultNightMode(ModeSettings.valueOf(choosenMode?.uppercase(Locale.US) ?: ModeSettings.AUTO.name).value )

        GlobalContext.startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ProductApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}