package dev.steelahhh.kogan.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import dev.steelahhh.kogan.data.ApiResponse
import dev.steelahhh.kogan.data.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class MainViewModel(app: Application) : AndroidViewModel(app), DIAware {
    override val di: DI by di()

    private val repository: ProductRepository by di.instance()

    private val stateFlow: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val state: StateFlow<State> get() = stateFlow

    init {
        load()
    }

    fun load() {
        stateFlow.value = State.Loading
        viewModelScope.launch {
            repository.getAirConditioners().fold({ products ->
                stateFlow.value = State.Content(
                    average = products.calculateCubicAverage(),
                    products = products.map { it.toUi() }
                )
            }, {
                stateFlow.value = State.Failure
            })
        }
    }

    private fun List<ApiResponse.Product>.calculateCubicAverage(): String {
        val sum = sumByDouble {
            val (height, width, length) = it.size.toMeters() ?: Triple(0.0, 0.0, 0.0)
            height * width * length
        }
        val avg = sum / size
        val result = avg * CONVERSION_FACTOR
        return String.format("%.2f", result)
    }

    sealed class State {
        object Loading : State()
        data class Content(val average: String, val products: List<ProductUi>) : State()
        object Failure : State()
    }

    companion object {
        private const val CONVERSION_FACTOR = 250.0
    }
}
