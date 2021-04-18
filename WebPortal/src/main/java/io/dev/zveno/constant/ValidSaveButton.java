package io.dev.zveno.constant;

public enum ValidSaveButton {
    VALID("Корректные значения"), INVALID("Некорректные значения");

    private final String attribute;


    ValidSaveButton(String attribute) {
        this.attribute = attribute;
    }
}
