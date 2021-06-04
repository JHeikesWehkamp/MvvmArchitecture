package nl.wehkamp.mvvmpoc.repositories

import kotlinx.coroutines.flow.*
import nl.wehkamp.mvvmpoc.model.data.MockData
import javax.inject.Inject

class MockRepository @Inject constructor() {

    fun getData(): Flow<MockData> = flow {
        emitAll(mockData.asFlow())
    }

    private val mockData = listOf(
        MockData(
            "Some title",
        "Some subtitle"
        ),
        MockData(
          "Hello world",
          "Goodbye world"
        ),
        MockData(
            "How are you gentlemen?",
            "All your base are belong to us"
        )
    )
}
