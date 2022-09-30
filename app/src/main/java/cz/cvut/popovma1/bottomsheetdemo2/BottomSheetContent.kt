package cz.cvut.popovma1.bottomsheetdemo2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetContent(
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier,
    closeButtonColor: Color = Color.Gray,
    content: @Composable() () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().fillMaxHeight()
    ) {
        IconButton(
            onClick = onClosePressed,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(29.dp)

        ) {
            Icon(Icons.Filled.Close, tint = closeButtonColor, contentDescription = null)
        }

        content()
    }
}
