package org.pointyware.commonsense.shared.home

import org.pointyware.commonsense.core.common.Mapper

/**
 */
object HomeUiStateMapper: Mapper<HomeUiState, HomeScreenState> {
    override fun map(input: HomeUiState): HomeScreenState {
        return HomeScreenState(
            false,
            null,
        )
    }
}
