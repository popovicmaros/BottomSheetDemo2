package cz.cvut.popovma1.bottomsheetdemo2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.BottomSheetContent
import cz.cvut.popovma1.bottomsheetdemo2.ui.theme.BottomSheetDemo2Theme
import cz.cvut.popovma1.bottomsheetdemo2.ui.theme.BottomSheetShape
import cz.cvut.popovma1.bottomsheetdemo2.ui.theme.SheetPeekHidden
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomSheetDemo2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainLayout() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var currentBottomSheet: BottomSheetScreen? by remember {
        mutableStateOf(null)
    }
    BackHandler(scaffoldState.bottomSheetState.isExpanded) {
        currentBottomSheet = null
        scope.launch { scaffoldState.bottomSheetState.collapse() }
    }

    val closeSheet: () -> Unit = {
        scope.launch {
            currentBottomSheet = null
            scaffoldState.bottomSheetState.collapse()
        }
    }

    val openSheet: (BottomSheetScreen) -> Unit = {
        scope.launch {
            currentBottomSheet = it
            scaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        sheetPeekHeight = SheetPeekHidden, // todo do this differently?
        scaffoldState = scaffoldState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            // "called during expand()"
            currentBottomSheet?.let { currentSheet ->
                SheetLayout(currentSheet,closeSheet)
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)){
            MainContent(openSheet)
        }
    }

}

@Composable
fun SheetLayout(currentScreen: BottomSheetScreen, onCloseBottomSheet: ()->Unit) {
    BottomSheetContent(onCloseBottomSheet){
        when(currentScreen){
            // "enum class type" -> "composable fun"
            BottomSheetScreen.ScreenNoArg -> ScreenNoArg()
            is BottomSheetScreen.ScreenArg -> ScreenArg()
            is BottomSheetScreen.ScreenImageInfo -> ImageInfoScreen(currentScreen.imageInfo)
            else -> {}
        }
    }
}

@Composable
fun ScreenNoArg(){
    Text("ScreenNoArg")
}

@Composable
fun ScreenArg(){
    Text("ScreenArg")
}

@Composable
fun MainContent(openSheet: (BottomSheetScreen) -> Unit) {
    val imageInfoList: List<ImageInfo> = DummyData.getImageInfoList()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is Main Content")

        Button(onClick = { openSheet(BottomSheetScreen.ScreenNoArg) }) {
            Text(text = "Open bottom sheet ScreenNoArg")
        }

        Button(onClick = { openSheet(BottomSheetScreen.ScreenArg("hello")) }) {
            Text(text = "Open bottom sheet ScreenArg")
        }

        imageInfoList.forEach{ imageInfo ->
            Button(onClick = { openSheet(BottomSheetScreen.ScreenImageInfo(imageInfo)) }) {
                Text("Image ${imageInfo.id}")
            }
        }
    }
}

sealed class BottomSheetScreen {
    object ScreenNoArg: BottomSheetScreen()
    class ScreenArg(val argument: String): BottomSheetScreen()
    class ScreenImageInfo(val imageInfo: ImageInfo): BottomSheetScreen()
}