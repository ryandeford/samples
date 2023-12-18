package io.ryandeford.samples.implementations;

import io.ryandeford.samples.data.SampleResultData;
import io.ryandeford.samples.interfaces.SampleService;
import io.ryandeford.samples.utils.SampleConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleServiceDefaultImplTest {

    @Test
    void executeSample() {
        SampleService service = new SampleServiceDefaultImpl();
        SampleResultData result = service.executeSampleAction();
        assertInstanceOf(SampleResultData.class, result);
        assertEquals(SampleConstants.DEFAULT_SAMPLE_RESULT_MESSAGE, result.getMessage());
    }

    @Test
    void executeSampleMessage() {
        String message = "Custom Sample Message";

        SampleService service = new SampleServiceDefaultImpl();
        SampleResultData result = service.executeSampleActionWithMessage(message);
        assertInstanceOf(SampleResultData.class, result);
        assertEquals(message, result.getMessage());
    }
}