package ru.mosit.pahotest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Data-class для хранения информации в JSON
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {

    private Float temperature;
    private Float sound;
    private Integer CO2;
    private Integer VOC;
    private String dateTime;
    private Integer number;


    public JsonData(JsonData another) {
        this.temperature = another.temperature;
        this.sound = another.sound;
        this.CO2 = another.CO2;
        this.VOC = another.VOC;
        this.dateTime = another.dateTime;
        this.number = another.number;
    }
}
