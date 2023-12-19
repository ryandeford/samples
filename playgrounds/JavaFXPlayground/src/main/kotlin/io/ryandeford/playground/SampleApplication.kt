package io.ryandeford.playground

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class SampleApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(SampleApplication::class.java.getResource("sample-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 500.0, 500.0)
        stage.title = "Sample App | JavaFX"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(SampleApplication::class.java)
}