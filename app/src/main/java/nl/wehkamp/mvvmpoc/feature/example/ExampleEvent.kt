package nl.wehkamp.mvvmpoc.feature.example

import nl.wehkamp.mvvmpoc.model.BaseEvent

sealed class ExampleEvent : BaseEvent() {
    object ShowDetail : ExampleEvent()
}
