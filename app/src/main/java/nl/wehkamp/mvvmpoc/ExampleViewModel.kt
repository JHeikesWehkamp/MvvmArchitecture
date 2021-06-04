package nl.wehkamp.mvvmpoc

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.wehkamp.mvvmpoc.feature.example.ExampleAction
import nl.wehkamp.mvvmpoc.feature.example.ExampleEvent
import nl.wehkamp.mvvmpoc.feature.example.ExampleState
import nl.wehkamp.mvvmpoc.repositories.MockRepository
import nl.wehkamp.mvvmpoc.model.Result
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val mockRepository: MockRepository
) : BaseViewModel<ExampleState, ExampleEvent, ExampleAction>() {

    override fun executeAction(action: ExampleAction) {
        when (action) {
            ExampleAction.Start -> generateText()
            ExampleAction.ChangeText -> generateText()
            ExampleAction.ChangeTextLiveData -> generateTextWithLiveData()
            ExampleAction.ShowDetail -> _eventLiveData.postEvent(ExampleEvent.ShowDetail)
        }
    }

    private fun generateTextWithLiveData() = viewModelScope.launch {
        mockRepository.getData().collect { data ->
            Log.d("SharedFlowTest", "Emitting state $data with LiveData")
            _stateLiveData.postValue(Result.Success(ExampleState.NewText(data)))
        }
    }

    private fun generateText() = viewModelScope.launch {
        mockRepository.getData().collect { data ->
            Log.d("SharedFlowTest", "Emitting state $data with SharedFlow")
            _viewSharedFlow.emit(Result.Success(ExampleState.NewText(data)))
        }
    }
}
