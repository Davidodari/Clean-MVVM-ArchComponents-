package com.k0d4black.theforce.features.character_search

import androidx.lifecycle.viewModelScope
import com.k0d4black.theforce.commons.Loading
import com.k0d4black.theforce.commons.Success
import com.k0d4black.theforce.commons.UiStateViewModel
import com.k0d4black.theforce.domain.usecases.SearchStarWarsCharacterUseCase
import com.k0d4black.theforce.mappers.toPresentation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterSearchViewModel @Inject constructor(
    private val searchStarWarsCharacterUseCase: SearchStarWarsCharacterUseCase
) : UiStateViewModel() {

//    val searchResultsStarWars: LiveData<List<StarWarsCharacterUiModel>>
//        get() = _searchResultsStarWars
//
//    private var _searchResultsStarWars: MutableLiveData<List<StarWarsCharacterUiModel>> =
//        MutableLiveData()

    fun executeCharacterSearch(params: String) {
        _uiState.value = Loading
        viewModelScope.launch(handler) {
            searchStarWarsCharacterUseCase(params).collect { results ->
                _uiState.value = Success(results.map { it.toPresentation() })
            }
        }

    }
}

