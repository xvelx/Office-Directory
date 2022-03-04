package com.vm.office.directory

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class with MultiDexApplication support and Hilt Injection enabled.
 *
 * This must be used as application class in Manifest in order to
 * enable the aforementioned supports.
 */
@HiltAndroidApp
class OfficeDirectoryApp : MultiDexApplication()