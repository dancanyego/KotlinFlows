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

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        collectFlow()
    }
    fun incrementCounter(){
        _stateFlow.value +=1
    }

    private fun collectFlow(){
        val flow1 = flow {
            delay(250L)
            emit("Appetizers")
            delay(1000L)
            emit("Main Dish")
            delay(1000L)
            emit("Desert")
        }

        viewModelScope.launch {
//            flow1.flatMapConcat { value ->
//                flow {
//                    emit(value +1)
//                    delay(500L)
//                    emit(value +2)
//                }
//            }.collect { value ->
//                println("The Value is $value")
//            }
//            countDownFlow.onEach {
//                print(it)
//            }.launchIn(viewModelScope)
            val count = countDownFlow
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
                .reduce { accumulator, value ->
                    accumulator + value
                }
//                .count {
//                    it %2 == 0
//                }
            print("The count is $count")
//                .collect { time ->
//                print("The current time is $time")
//            }

        }
    }
}