package io.ryandeford.playground;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractItem implements Item {
    public abstract String doSomethingElse(String message);

    public String createMessage() {
        Instant instant = Instant.now();
        String dateInSystemTime = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(
                ZonedDateTime.ofInstant(
                        instant,
                        ZoneId.systemDefault()
                )
        );
        return "Created Message From Abstract Item: " + instant + ": " + dateInSystemTime;
    }
}