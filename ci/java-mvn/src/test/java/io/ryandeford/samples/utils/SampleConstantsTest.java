package io.ryandeford.samples.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleConstantsTest {

    @Test
    void verifyDefaultSampleResultMessage() {
        assertEquals("Default Sample Message", SampleConstants.DEFAULT_SAMPLE_RESULT_MESSAGE);
    }
}