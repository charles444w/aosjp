package jcp.apps

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.safeObserve(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>
) {
    // Using LifecycleEventObserver instead of @OnLifecycleEvent
    val observerWrapper = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            this.observe(lifecycleOwner, observer)
        } else if (event == Lifecycle.Event.ON_STOP) {
            this.removeObserver(observer)
        }
    }

    // Add the observer to lifecycleOwner's lifecycle
    lifecycleOwner.lifecycle.addObserver(observerWrapper)
}