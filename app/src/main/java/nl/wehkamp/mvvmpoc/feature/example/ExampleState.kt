package nl.wehkamp.mvvmpoc.feature.example

import nl.wehkamp.mvvmpoc.model.BaseState
import nl.wehkamp.mvvmpoc.model.data.MockData

sealed class ExampleState : BaseState() {

    class Initial(
        val data: MockData
    ) : ExampleState()

    class NewText(
        val data: MockData
    ) : ExampleState()

    class SomeState (
        val title: String,
        val list: List<String>
    )
}
