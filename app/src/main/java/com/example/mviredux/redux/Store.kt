package com.example.mviredux.redux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Store<T>(initialState: T) {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<T> = _stateFlow.asStateFlow()

    private val mutex = Mutex() //lock the store when reading and writing

    suspend fun update(updateBlock: (T) -> T) = mutex.withLock {
        //updateBlock:(T) -> T is lambda
        //(T) is argument and the return type -> is gonna be T

        val newState = updateBlock(_stateFlow.value) //The idea here is we can update block with current state
        _stateFlow.value = newState
    }

    suspend fun <S> read(readBlock:(T) -> S) = mutex.withLock {
        readBlock(_stateFlow.value)
    }
}