package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Float temperature;
    private Float sound;
    private Integer CO2;
    private Integer VOC;
    private LocalDateTime dateTime;
    private Integer number;
}
