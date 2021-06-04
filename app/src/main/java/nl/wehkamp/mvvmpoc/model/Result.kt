package nl.wehkamp.mvvmpoc.model

sealed class Result<T : BaseState> {
    class Success<T : BaseState>(
        val state: T
    ) : Result<T>()

    class Error<T : BaseState>(
        val throwable: Throwable
    ) : Result<T>()

    class Loading<T : BaseState> : Result<T>()
}
