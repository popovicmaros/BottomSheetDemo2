package cz.cvut.popovma1.bottomsheetdemo2

object DummyData {
    fun getImageInfoList(): List<ImageInfo> = (1..3).map { i ->
        ImageInfo(
            id = i,
            url = "imageUrl$i",
            detail = "this is detail of image $i"
        )
    }
}