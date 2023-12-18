package io.ryandeford.samples.interfaces;

import io.ryandeford.samples.data.SampleResultData;

public interface SampleService {
    public SampleResultData executeSampleAction();

    public SampleResultData executeSampleActionWithMessage(String message);
}
