package com.example.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue

        emit(startingValue)

        while (startingValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow(){
        viewModelScope.launch {
            countDownFlow.onEach {
                print(it)
            }.launchIn(viewModelScope)
            countDownFlow
                .filter { time ->
                    time %2 ==0 // get even values in the flow

                    // this is a common filter operator used to filter flows

                }
                .map { time ->
                    time*time
                }
                .onEach { time ->
                    print(time)
                }
                .collect { time ->
                print("The current time is $time")
            }
        }
    }
}