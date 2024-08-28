package jcp.apps

data class VideoModel(
    val topic: String = "",
    val videoDetails: MutableList<Details> = mutableListOf()
) {
    data class Details(
        val title: String = "",
        val url: String = "https://firebasestorage.googleapis.com/v0/b/aosfbai.appspot.com/o/Product%20Demo%20Video%20_%20SaaS%20Explainer%20Video%20_%20Infinity.mp4?alt=media&token=93a94173-0e3b-43fa-8bf6-d65567636146",
        val bigUrlImage: String = "https://firebasestorage.googleapis.com/v0/b/aosfbai.appspot.com/o/pngtree-fresh-apple-fruit-red-png-image_10203073.png?alt=media&token=2b7623fd-110c-4ba7-aef8-018ceb356d62",
        val smallUrlImage: String = "https://firebasestorage.googleapis.com/v0/b/aosfbai.appspot.com/o/pngtree-fresh-apple-fruit-red-png-image_10203073.png?alt=media&token=2b7623fd-110c-4ba7-aef8-018ceb356d62",
        val desc: String = "",
        val createData: String = ""
    ) {

    }
}