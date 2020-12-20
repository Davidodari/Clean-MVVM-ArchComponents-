package com.k0d4black.theforce.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.k0d4black.theforce.R
import com.k0d4black.theforce.adapters.createFilmsAdapter
import com.k0d4black.theforce.adapters.createSpeciesAdapter
import com.k0d4black.theforce.base.BaseFavoritesFragment
import com.k0d4black.theforce.commons.remove
import com.k0d4black.theforce.commons.show
import com.k0d4black.theforce.databinding.FragmentFavoritesBinding
import com.k0d4black.theforce.models.*
import kotlinx.android.synthetic.main.fragment_favorites.*

internal class FavoriteDetailsFragment : BaseFavoritesFragment(R.layout.fragment_favorites),
    ICharacterDetailsBinder {

    // region Members

    private lateinit var binding: FragmentFavoritesBinding

    private val filmsAdapter = createFilmsAdapter()

    private val speciesAdapter = createSpeciesAdapter()

    // endregion

    // region Android API

    override fun onCreate(savedInstanceState: Bundle?) {
        setupToolbar()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    // endregion

    // region BaseFavoritesFragment

    override fun bindFavorite(favoritePresentation: FavoritePresentation) {
        bindCharacterBasicInfo(favoritePresentation.characterPresentation)
        bindPlanet(favoritePresentation.planetPresentation)
        bindSpecies(favoritePresentation.speciePresentation)
        bindFilms(favoritePresentation.films)
    }

    override val rootViewGroup: ViewGroup
        get() = favorites_layout

    // endregion

    // region Private API

    private fun setupToolbar() {
        (requireActivity() as DashboardActivity).setSupportActionBar(favorites_toolbar)
        (requireActivity() as DashboardActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // endregion

    // region ICharacterDetailsBinder

    override fun bindCharacterBasicInfo(character: CharacterPresentation?) {
        (requireActivity() as DashboardActivity).supportActionBar?.title = character?.name ?: ""
        binding.infoLayout.character = character
    }

    override fun bindFilms(films: List<FilmPresentation>?) {
        films?.let {
            with(binding.filmsLayout) {
                filmsProgressBar.remove()
                characterDetailsFilmsRecyclerView.apply {
                    adapter = filmsAdapter.apply { submitList(films) }
                }
            }
        }
    }

    override fun bindSpecies(species: List<SpeciePresentation>?) {
        species?.let {
            with(binding.specieLayout) {
                speciesProgressBar.remove()
                if (species.isNotEmpty()) {
                    characterDetailsSpeciesRecyclerView.apply {
                        adapter = speciesAdapter.apply { submitList(species) }
                    }
                } else noSpeciesTextView.show()
            }
        }
    }

    override fun bindPlanet(planet: PlanetPresentation?) {
        planet?.let {
            with(binding.planetLayout) {
                planetProgressBar.remove()
                this.planet = planet
                characterDetailsPlanetNameTextView.show()
                characterDetailsPlanetPopulationTextView.show()
            }
        }
    }

    // endregion
}
