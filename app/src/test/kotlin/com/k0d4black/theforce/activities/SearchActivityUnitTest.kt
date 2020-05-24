package com.k0d4black.theforce.activities

import android.os.Build
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.k0d4black.theforce.R
import com.k0d4black.theforce.features.character_search.SearchActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SearchActivityUnitTest : AutoCloseKoinTest(){

    private lateinit var searchActivity: SearchActivity

    @Before
    fun setup() {
        searchActivity =
            Robolectric.buildActivity(SearchActivity::class.java)
                .create()
                .visible()
                .get()
    }

    @Test
    fun `given activity is visible when app launches then validate default info text shown`() {
        val infoTextView = searchActivity.findViewById<TextView>(R.id.search_tip_text_view)
        assertThat(infoTextView).isNotNull()
        assertThat(infoTextView.text.toString()).matches(searchActivity.resources.getString(
            R.string.info_search_tip
        ))
    }

    @Test
    fun `given activity is visible when app launched then validate search menu is displayed`() {
        val menu = Shadows.shadowOf(searchActivity).optionsMenu
        val menuItem = menu.findItem(R.id.action_search)
        assertThat(menuItem.isVisible)
    }
}