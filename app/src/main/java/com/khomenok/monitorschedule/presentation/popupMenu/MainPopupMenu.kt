package com.khomenok.monitorschedule.presentation.popupMenu

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.khomenok.monitorschedule.R

class MainPopupMenu(
    private val context: Context,
    private val onSettingsClick: () -> Unit,
    private val onAboutClick: () -> Unit
) {

    fun initPopupMenu(targetView: View): PopupMenu {
        val popupMenu = PopupMenu(context, targetView)

        popupMenu.inflate(R.menu.main_menu)

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.settings -> {
                    onSettingsClick()
                    true
                }
                R.id.about -> {
                    onAboutClick()
                    true
                }
                else -> {
                    true
                }
            }
        }

        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)

        return popupMenu
    }

}