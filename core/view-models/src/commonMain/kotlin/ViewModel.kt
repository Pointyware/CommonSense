package org.pointyware.commonsense.core.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Base class for all view models.
 */
abstract class ViewModel() {

    protected val viewModelScope: CoroutineScope by lazy {
        createViewModelScope()
    }

    protected fun onDestroy() {
        viewModelScope.cancel()
    }
}

private fun createViewModelScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.Main + SupervisorJob())
}
