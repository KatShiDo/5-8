package org.example;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Record> record = new ArrayList<>();
}
