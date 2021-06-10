package nl.wehkamp.mvvmpoc

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import nl.wehkamp.mvvmpoc.model.BaseAction
import nl.wehkamp.mvvmpoc.model.BaseEvent
import nl.wehkamp.mvvmpoc.model.BaseState
import nl.wehkamp.mvvmpoc.model.Result

abstract class ViewModelSubscriber<S : BaseState, E : BaseEvent, A : BaseAction>(private val lifecycle: LifecycleOwner) :
    LifecycleObserver {
    abstract val viewModel: BaseViewModel<S, E, A>
    abstract fun onResult(result: Result<S>)
    abstract fun onEvent(event: E)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        subscribeToState()
        subscribeToEvents()
    }

    fun startLoading() {
        // Trigger general loading state
        // or
        // Could also be made abstract to defer to views for handling a loading state
    }

    fun stopLoading() {
        // Cancel Loading state
    }

    fun showError(throwable: Throwable) {
        // Trigger error state based on exception
    }

    private fun subscribeToState() {
        lifecycle.lifecycleScope.launchWhenStarted {
            viewModel.viewSharedFlow.collect { result ->
                onResult(result)
            }
        }
        viewModel.stateLiveData.observe(lifecycle) { result ->
            onResult(result)
        }
    }

    private fun subscribeToEvents() {
        viewModel.eventLiveData.observe(lifecycle) { event ->
            event.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }
}