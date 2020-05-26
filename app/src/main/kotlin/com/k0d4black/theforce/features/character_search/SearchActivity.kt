package com.k0d4black.theforce.features.character_search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.k0d4black.theforce.R
import com.k0d4black.theforce.commons.*
import com.k0d4black.theforce.databinding.ActivitySearchBinding
import com.k0d4black.theforce.features.character_details.CharacterDetailActivity
import com.k0d4black.theforce.features.settings.SettingsActivity
import com.k0d4black.theforce.models.CharacterPresentation
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val characterSearchViewModel by viewModel<CharacterSearchViewModel>()

    lateinit var binding: ActivitySearchBinding

    private val searchResultAdapter: SearchResultAdapter by lazy {
        SearchResultAdapter { character ->
            Intent(this, CharacterDetailActivity::class.java).apply {
                putExtra(CHARACTER_PARCEL_KEY, character)
            }.also { startActivity(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        observeSearchViewState()
    }

    private fun observeSearchViewState() {
        characterSearchViewModel.searchViewState.observe(this, Observer {
            when (it) {
                is SearchResultLoaded -> {
                    showSnackbar(
                        binding.searchResultsRecyclerView,
                        getString(R.string.info_search_done)
                    )
                    displaySearchResults(it.searchResults)
                }
                is Error -> displayErrorState(it.message)
                is Loading -> displayLoadingState()
            }
        })
    }

    private fun displayLoadingState() {
        binding.searchTipTextView
            .animate()
            .alpha(0f)
            .setListener(AnimatorListener(onEnd = {
                binding.searchTipTextView.hide()
                binding.loadingSearchResultsProgressBar.show()
            }))
    }

    private fun displaySearchResults(searchResultStarWars: List<CharacterPresentation>) {
        val progressBar = binding.loadingSearchResultsProgressBar
        val searchTipTextView = binding.searchTipTextView
        progressBar.animate()
            .alpha(0f)
            .setListener(AnimatorListener(onEnd = { progressBar.hide() }))

        if (searchResultStarWars.isNotEmpty()) {

            if (searchTipTextView.isVisible) searchTipTextView.hide()

            binding.searchResultsRecyclerView.apply {
                adapter = ScaleInAnimationAdapter(searchResultAdapter.apply {
                    submitList(searchResultStarWars)
                })
                initRecyclerViewWithLineDecoration(this@SearchActivity)
            }

            binding.searchResultsRecyclerView.show()

        } else displayNoSearchResults()
    }

    private fun displayNoSearchResults() {
        showSearchTip()
        binding.searchResultsRecyclerView.hide()
        showSnackbar(
            binding.searchResultsRecyclerView,
            getString(R.string.info_no_results)
        )
    }

    private fun displayErrorState(message: String) {
        binding.searchResultsRecyclerView.hide()
        binding.loadingSearchResultsProgressBar
            .animate()
            .alpha(0f)
            .setListener(AnimatorListener(onEnd = { binding.loadingSearchResultsProgressBar.hide() }))
        showSearchTip()
        showSnackbar(binding.searchResultsRecyclerView, message)
    }

    private fun showSearchTip() {
        binding.searchTipTextView
            .animate()
            .alpha(1f)
            .setListener(AnimatorListener(onEnd = { binding.searchTipTextView.show() }))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(
            SearchQueryListener(characterSearchViewModel)
        )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            true
        } else super.onOptionsItemSelected(item)
    }

}
