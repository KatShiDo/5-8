package ru.mosit.pahotest.data;

import lombok.Getter;

// Enum для хранения топиков и удобной подпиской к ним
@Getter
public enum Topic {
    TEMPERATURE("/devices/wb-m1w2_14/controls/External Sensor 1"),
    SOUND("/devices/wb-msw-v3_21/controls/Sound Level"),
    CO2("/devices/wb-msw-v3_21/controls/CO2"),
    VOC("/devices/wb-msw-v3_21/controls/Air Quality (VOC)");

    private final String value;
    Topic(String value) {
        this.value = value;
    }

    public static Topic fromValue(String value) {
        for (final Topic dayOfWeek : values()) {
            if (dayOfWeek.value.equalsIgnoreCase(value)) {
                return dayOfWeek;
            }
        }
        return null;
    }
}
