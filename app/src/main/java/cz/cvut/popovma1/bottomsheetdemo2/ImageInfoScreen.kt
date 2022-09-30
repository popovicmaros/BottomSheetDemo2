package cz.cvut.popovma1.bottomsheetdemo2

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ImageInfoScreen(imageInfo: ImageInfo){
    Column {
        with(imageInfo) {
            Text(text = "$id")
            Text(text = url)
            Text(text = detail)
        }
    }
}