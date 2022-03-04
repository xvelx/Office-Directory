package com.vm.office.directory.ui.base.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.vm.office.common_ui.activities.ProgressActivity
import com.vm.office.directory.R
import com.vm.office.directory.databinding.ActivityBaseBinding
import com.vm.office.directory.databinding.ViewToolbarBinding

/**
 * BaseActivity - The other Activities should extend this to reuse the setup of components
 * like toolbar, drawer etc.
 *
 * UI logics that is common for all the activities should be placed here.
 */
abstract class BaseActivity : AppCompatActivity(), ProgressActivity {

    private lateinit var toolBarBinding: ViewToolbarBinding
    private lateinit var baseActivityBinding: ActivityBaseBinding

    /**
     * Provides the Navigation Graph that should be used with
     * the Navigation host of this Activity.
     */
    @get:NavigationRes
    abstract val navigationGraphId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivityBinding = ActivityBaseBinding.inflate(LayoutInflater.from(this))
        toolBarBinding = ViewToolbarBinding.bind(baseActivityBinding.root)

        setContentView(baseActivityBinding.root)

        configureNavigation()
    }

    private fun configureNavigation() {
        (supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)
                as? NavHostFragment)?.navController?.let { navController ->
            navController.setGraph(navigationGraphId)

            setSupportActionBar(toolBarBinding.appToolBar)
            toolBarBinding.appToolBar.setupWithNavController(
                navController,
                AppBarConfiguration(
                    setOf(R.id.employeeListFragment, R.id.roomListFragment),
                    baseActivityBinding.drawerLayout
                )
            )

            baseActivityBinding.navigationDrawer.setupWithNavController(navController)
        }
    }

    /**
     * Shows global progress indicator.
     */
    override fun showProgressIndicator() {
        baseActivityBinding.appProgressBar.isVisible = true
    }

    /**
     * Hides global progress indicator.
     */
    override fun hideProgressIndicator() {
        baseActivityBinding.appProgressBar.isVisible = false
    }

    /**
     * Shows the error (by extracting the message from throwable)
     * in global error view.
     *
     * @param throwable Throwable - error that occurred and that should be notified.
     */
    override fun showError(throwable: Throwable) {
        Snackbar.make(
            baseActivityBinding.mainFragmentContainer,
            throwable.message.toString(),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}