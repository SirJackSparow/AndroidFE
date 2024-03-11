package com.example.androidtestfe.ui.listscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtestfe.data.network.model.ListModelItem
import com.example.androidtestfe.usecases.GetListDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getListDataUseCase: GetListDataUseCase) :
    ViewModel() {

    private val _uiState = MutableLiveData<ListState>()
    val uiState get() = _uiState

    private var page = 1

    init {
        getDataList()
    }

     fun getDataList() {
        viewModelScope.launch {
            val result = getListDataUseCase.getListData(page, 10)
            if (result.isNotEmpty()) {
                _uiState.value = ListState.Success(result)
            } else {
                _uiState.value = ListState.Failed
            }
        }
    }

}

sealed class ListState {
    data class Success(val data: List<ListModelItem>) : ListState()
    object Failed : ListState()
}