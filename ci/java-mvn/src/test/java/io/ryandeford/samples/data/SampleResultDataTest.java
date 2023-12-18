package io.ryandeford.samples.data;

import io.ryandeford.samples.test.SampleTestConstants;
import io.ryandeford.samples.utils.SampleConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleResultDataTest {

    @Test
    void createSampleResultDataDefaultConstructor() {
        SampleResultData data = new SampleResultData();
        assertEquals(SampleConstants.DEFAULT_SAMPLE_RESULT_MESSAGE, data.getMessage());
    }

    @Test
    void createSampleResultDataMessageConstructor() {
        String message = "Custom Sample Message";
        SampleResultData data = new SampleResultData(message);
        assertEquals(message, data.getMessage());
    }

    @Test
    void createSampleResultDataBuilderDefault() {
        SampleResultData data = SampleResultData.builder().build();
        assertEquals(SampleConstants.DEFAULT_SAMPLE_RESULT_MESSAGE, data.getMessage());
    }

    @Test
    void createSampleResultDataBuilderCustomMessage() {
        SampleResultData data = SampleResultData.builder()
                .message(SampleTestConstants.CUSTOM_SAMPLE_RESULT_MESSAGE)
                .build();
        assertEquals(SampleTestConstants.CUSTOM_SAMPLE_RESULT_MESSAGE, data.getMessage());
    }

    // Note: There are all kinds of tests we could create here, but this is just a sample project
}