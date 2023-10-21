package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Parser {

    public static void main(String[] args) throws ParseException, IOException {

        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("data_.json"));
        System.out.println("[");
        for (Object o : a)
        {
            System.out.println("\t{");
            JSONObject data = (JSONObject) o;

            Double temperature = (Double) data.get("temperature");
            System.out.println("\t\t" + "\"temperature\": " + temperature);

            Double sound = (Double) data.get("sound");
            System.out.println("\t\t" + "\"sound\": " + sound);

            Long CO2 = (Long) data.get("CO2");
            System.out.println("\t\t" + "\"CO2\": " + CO2);

            Long VOC = (Long) data.get("VOC");
            System.out.println("\t\t" + "\"VOC\": " + VOC);

            String dateTime = (String) data.get("dateTime");
            System.out.println("\t\t" + "\"dateTime\": " + dateTime);

            Long number = (Long) data.get("number");
            System.out.println("\t\t" + "\"number\": " + number);

            System.out.println("\t}");
        }
        System.out.println("]");
    }
}