package io.ryandeford.samples.implementations;

import io.ryandeford.samples.data.SampleResultData;
import io.ryandeford.samples.interfaces.SampleService;

public class SampleServiceDefaultImpl implements SampleService {
    @Override
    public SampleResultData executeSampleAction() {
        return SampleResultData.builder().build();
    }

    @Override
    public SampleResultData executeSampleActionWithMessage(String message) {
        return SampleResultData.builder().message(message).build();
    }
}
