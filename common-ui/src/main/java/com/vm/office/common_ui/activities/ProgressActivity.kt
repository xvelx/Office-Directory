package com.vm.office.common_ui.activities

interface ProgressActivity {

    fun showProgressIndicator()

    fun hideProgressIndicator()

    fun showError(throwable: Throwable)
}