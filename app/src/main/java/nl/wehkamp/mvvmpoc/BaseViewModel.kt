package nl.wehkamp.mvvmpoc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import nl.wehkamp.mvvmpoc.model.*

abstract class BaseViewModel<S : BaseState, E : BaseEvent, A : BaseAction> : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _stateLiveData.value = Result.Error(throwable)
    }

    protected val _viewSharedFlow = MutableSharedFlow<Result<S>>()
    val viewSharedFlow: SharedFlow<Result<S>>
        get() = _viewSharedFlow.asSharedFlow()

    protected val _stateLiveData = MutableLiveData<Result<S>>()
    val stateLiveData: LiveData<Result<S>>
        get() = _stateLiveData

    protected val _eventLiveData = MutableLiveData<Event<E>>()
    val eventLiveData: LiveData<Event<E>>
        get() = _eventLiveData

    abstract fun executeAction(action: A)
}
