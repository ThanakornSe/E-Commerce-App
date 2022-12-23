package com.example.mviredux.redux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Store<T>(initialState: T) {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<T> = _stateFlow.asStateFlow()

    private val mutex = Mutex()

    suspend fun update(updateBlock: (T) -> T) = mutex.withLock {
        //updateBlock:(T) -> T
        //this mean (T) only argument is T and the return type -> is gonna be T

        val newState = updateBlock(_stateFlow.value) //The idea here is we can update block with current state
        _stateFlow.value = newState
    }

    suspend fun read(readBlock:(T) -> Unit) = mutex.withLock {
        readBlock(_stateFlow.value)
    }
}