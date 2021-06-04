package nl.wehkamp.mvvmpoc.feature.example

import nl.wehkamp.mvvmpoc.model.BaseAction

sealed class ExampleAction : BaseAction() {
    object Start : ExampleAction()
    object ChangeText : ExampleAction()
    object ChangeTextLiveData : ExampleAction()
    object ShowDetail : ExampleAction()
}
