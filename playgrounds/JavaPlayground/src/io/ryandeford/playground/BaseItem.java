package io.ryandeford.playground;

public class BaseItem extends AbstractItem implements Item {
    @Override
    public String doSomething(String message) {
        return "Doing Something With Message: " + message;
    }

    @Override
    public String doSomethingElse(String message) {
        return "Doing Something Else With Message: " + message;
    }
}
