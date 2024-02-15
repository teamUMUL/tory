package inu.thebite.tory.screens.teachingboard.recentlto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inu.thebite.tory.screens.teachingboard.recentlto.components.RecentLTOTopBar
import inu.thebite.tory.screens.teachingboard.recentlto.components.STOsInRecentLTO


@Composable
fun RecentLTOScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        RecentLTOTopBar(
            modifier = Modifier,
            ltoName = "같은 사진 매칭"
        )
        val dummyList = listOf(
            "STO_01",
            "STO_02",
            "STO_03",
            "STO_04",

        )
        STOsInRecentLTO(
            modifier = Modifier.weight(1f),
            dummyList = dummyList
        )
    }
}



