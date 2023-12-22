package io.ryandeford.playground

import javafx.fxml.FXML
import javafx.scene.control.Label
import java.time.Instant
import java.util.*

class SampleController {
    @FXML
    private lateinit var sampleText: Label

    @FXML
    private fun onSampleButtonClick() {
        sampleText.text += "\nSample Message from JavaFX: ${Date.from(Instant.now())}"
    }
}