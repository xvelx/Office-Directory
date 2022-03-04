package com.vm.office.common_ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.viewbinding.ViewBinding
import com.vm.office.common_ui.R

abstract class SearchFragment<T : ViewBinding> : BaseFragment<T>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_option_menu, menu)

        (menu.findItem(R.id.appBarSearchView).actionView as? SearchView)
            ?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        onItemSearch(newText)
                        return true
                    }

                    return false
                }
            })
        super.onCreateOptionsMenu(menu, inflater)
    }

    abstract fun onItemSearch(query: String)
}