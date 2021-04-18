package io.dev.zveno.constant;

public enum ValidSubmitButton {
    VALID("Корректные значения"), INVALID("Некорректные значения");

    private final String attribute;

    ValidSubmitButton(String attribute) {
        this.attribute = attribute;
    }
}
