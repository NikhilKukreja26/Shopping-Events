package dev.nikhilkukreja.shoppingevents.customcomposables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EmptyListUI(
    modifier: Modifier = Modifier,
    message: String,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
        )
    }
}