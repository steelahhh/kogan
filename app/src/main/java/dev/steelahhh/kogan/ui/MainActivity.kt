package dev.steelahhh.kogan.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dev.steelahhh.kogan.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenResumed {
            vm.state.collect {
                when (it) {
                    is MainViewModel.State.Loading -> showLoader()
                    is MainViewModel.State.Content -> showContent(it)
                    is MainViewModel.State.Failure -> showFailure()
                }
            }
        }
    }

    private fun showLoader() {
        progressBar.isVisible = true
        averageTv.isGone = true
        failureContainer.isGone = true
    }

    private fun showFailure() {
        progressBar.isGone = true
        averageTv.isGone = true
        failureContainer.isVisible = true
        retryButton.setOnClickListener { vm.load() }
    }

    private fun showContent(state: MainViewModel.State.Content) {
        progressBar.isGone = true
        failureContainer.isGone = true
        averageTv.isVisible = true
        averageTv.text = getString(R.string.average_cubic_weight_pattern, state.average)
    }
}
