package nl.wehkamp.mvvmpoc

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import nl.wehkamp.mvvmpoc.model.Result
import nl.wehkamp.mvvmpoc.model.BaseAction
import nl.wehkamp.mvvmpoc.model.BaseEvent
import nl.wehkamp.mvvmpoc.model.BaseState
import java.lang.Exception

abstract class BaseActivity<S : BaseState, E : BaseEvent, A : BaseAction> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<S, E, A>

    abstract fun onResult(result: Result<S>)
    abstract fun onEvent(event: E)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToState()
        subscribeToEvents()
    }

    protected fun startLoading() {
        // Trigger general loading state
        // or
        // Could also be made abstract to defer to views for handling a loading state
    }

    protected fun stopLoading() {
        // Cancel Loading state
    }

    protected fun showError(throwable: Throwable) {
        // Trigger error state based on exception
    }

    private fun subscribeToState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewSharedFlow.collect { result ->
                onResult(result)
            }
        }
        viewModel.stateLiveData.observe(this) { result ->
            onResult(result)
        }
    }

    private fun subscribeToEvents() {
        viewModel.eventLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }
}
