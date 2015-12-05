package de.qaware.qahappy.backend.web;

public enum HappinessType {

    UNHAPPY,
    OK,
    HAPPY;


    public static HappinessType fromInt(int fromInt) {
        HappinessType[] values = HappinessType.values();
        if (fromInt < 0 || fromInt >= values.length) {
            return null;
        }
        return values[fromInt];
    }
}
