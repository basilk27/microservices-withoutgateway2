package com.mbsystems.gamification.domain;

import java.util.stream.Stream;

public enum WeekDay {
    SUNDAY("off"),
    MONDAY("working"),
    TUESDAY("working"),
    WEDNESDAY("working"),
    THURSDAY("working"),
    FRIDAY("working"),
    SATURDAY("off");

    private String typeOfDay;

    WeekDay(String typeOfDay) {
        this.typeOfDay = typeOfDay;
    }

    public static Stream<WeekDay> stream() {
        return Stream.of( WeekDay.values() );
    }
}
