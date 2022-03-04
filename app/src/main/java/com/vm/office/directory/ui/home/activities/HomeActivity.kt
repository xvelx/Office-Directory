package com.vm.office.directory.ui.home.activities

import com.vm.office.directory.R
import com.vm.office.directory.ui.base.activities.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * HomeActivity - Entry point to the application.
 *
 * Configures the navigation with required graph.
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override val navigationGraphId: Int
        get() = R.navigation.navigation_app
}