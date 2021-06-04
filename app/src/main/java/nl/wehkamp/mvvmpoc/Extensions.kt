package nl.wehkamp.mvvmpoc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nl.wehkamp.mvvmpoc.model.BaseEvent
import nl.wehkamp.mvvmpoc.model.Event

fun <T : BaseEvent> MutableLiveData<Event<T>>.postEvent(event: T) {
    postValue(Event(event))
}
