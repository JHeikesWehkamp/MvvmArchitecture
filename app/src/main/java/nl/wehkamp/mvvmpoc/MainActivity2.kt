package nl.wehkamp.mvvmpoc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import nl.wehkamp.mvvmpoc.databinding.ActivityMainBinding
import nl.wehkamp.mvvmpoc.feature.example.ExampleAction
import nl.wehkamp.mvvmpoc.feature.example.ExampleEvent
import nl.wehkamp.mvvmpoc.feature.example.ExampleState
import nl.wehkamp.mvvmpoc.model.Result
import nl.wehkamp.mvvmpoc.model.data.MockData

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {
    private val vmSubscriber =
        object : ViewModelSubscriber<ExampleState, ExampleEvent, ExampleAction>(this) {
            override val viewModel: ExampleViewModel by viewModels()

            override fun onResult(result: Result<ExampleState>) = when (result) {
                is Result.Success -> onState(result.state)
                is Result.Loading -> startLoading()
                is Result.Error -> showError(result.throwable)
            }

            override fun onEvent(event: ExampleEvent) = when (event) {
                ExampleEvent.ShowDetail -> showDetailScreen()
            }

        }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        this.lifecycle.addObserver(vmSubscriber)

        setContentView(binding.root)

        with(binding) {
            changeTextLivedataButton.setOnClickListener {
                vmSubscriber.viewModel.executeAction(ExampleAction.ChangeTextLiveData)
            }
            changeTextFlowButton.setOnClickListener {
                vmSubscriber.viewModel.executeAction(ExampleAction.ChangeText)
            }
            showDetailButton.setOnClickListener {
                vmSubscriber.viewModel.executeAction(ExampleAction.ShowDetail)
            }
        }

        vmSubscriber.viewModel.executeAction(ExampleAction.Start)
    }


    override fun onDestroy() {
        super.onDestroy()
        this.lifecycle.removeObserver(vmSubscriber)
    }

    private fun onState(state: ExampleState) {
        when (state) {
            is ExampleState.Initial -> showInitialState(state.data)
            is ExampleState.NewText -> showChangedText(state.data)
        }
    }

    private fun showInitialState(data: MockData) {
        binding.titleTextview.text = data.title
        binding.subtitleTextview.text = data.content
    }

    private fun showChangedText(data: MockData) {
        Log.d("SharedFlowTest", "Received new state $data")
        binding.titleTextview.text = data.title
        binding.subtitleTextview.text = data.content
    }

    private fun showDetailScreen() {
        startActivity(Intent(this, DetailActivity::class.java))
    }
}
