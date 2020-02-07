package com.arainko.nawts.fragments

import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.Navigation
import com.arainko.nawts.R

object NavMenuClickHandler : Toolbar.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.navCustomization ->
                Navigation.findNavController()
        }
    }
}