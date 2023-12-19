package io.ryandeford.playground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseItemTest {

    @Test
    public void doSomething() {
        Item item = new BaseItem();
        String message = "Test Message";

        String result = item.doSomething(message);
        assertEquals("Doing Something With Message: " + message, result);
    }

    @Test
    public void doSomethingElse() {
        AbstractItem item = new BaseItem();
        String message = "Test Message";

        String result = item.doSomethingElse(message);
        assertEquals("Doing Something Else With Message: " + message, result);
    }

    @Test
    public void createMessage() {
        AbstractItem item = new BaseItem();

        String result = item.createMessage();
        assertTrue(result.startsWith("Created Message From Abstract Item: "));
    }
}