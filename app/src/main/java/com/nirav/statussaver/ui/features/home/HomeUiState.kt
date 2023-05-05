package com.nirav.statussaver.ui.features.home

import com.nirav.statussaver.data.model.FileMedia

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val imageList: List<FileMedia>) : HomeUiState()
    object Permission : HomeUiState()
}
