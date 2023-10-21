package ru.mosit.pahotest;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import ru.mosit.pahotest.data.JsonData;
import ru.mosit.pahotest.data.Topic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class PahoTest {
    // Параметры подключения к брокеру
    static String host = "192.168.1.12";
    static Integer port = 1883;
    static JsonData jsonDataList = new JsonData();

    static XmlMapper xmlMapper = new XmlMapper();

    static boolean isFirst = true;

    public static void main(String[] args) throws MqttException, InterruptedException {
        // Создание MQTT клиента для подключения
        MqttClient client = new MqttClient(
                String.format("tcp://%s:%d", host, port),
                MqttClient.generateClientId());

        // Создание класса для формирования JSON
        var g = new Gson();
        // Объект для хранения данных
        var jsonData = new JsonData();

        // Создание колбэков для обработки событий, возникающих на клиенте
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("client lost connection " + cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                var payload = new String(message.getPayload());
                System.out.println(topic + ": " + payload);

                var param = Topic.fromValue(topic);

                if (param != null)
                    switch (param) {
                        case TEMPERATURE -> jsonData.setTemperature(Float.valueOf(payload));
                        case SOUND -> jsonData.setSound(Float.valueOf(payload));
                        case CO2 -> jsonData.setCO2(Integer.valueOf(payload));
                        case VOC -> jsonData.setVOC(Integer.valueOf(payload));
                    }
                else
                    System.out.println("Not known topic");

                jsonData.setDateTime(LocalDateTime.now().toString());
                jsonData.setNumber(11);

                jsonDataList = new JsonData(jsonData);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("delivery complete " + token);
            }
        });

        client.connect();

        // Подписывание на топики
        for (var topic : Topic.values())
            client.subscribe(topic.getValue(), 1);

        var timer = new Thread(new Timer5sec());
        timer.start();
    }

    private static class Timer5sec implements Runnable {

        private final Gson g = new Gson();

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                writeToJson(g.toJson(jsonDataList));
                writeToXml(jsonDataList);
            }
        }
    }

    private static void writeToJson(String data) {
        try {
            if (isFirst) {
                Files.write(Paths.get("data.json"), "[".getBytes(), StandardOpenOption.APPEND);
            }
            Files.write(Paths.get("data.json"), data.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.json"), ",".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToXml(JsonData data) {
        try {
            if (isFirst) {
                Files.write(Paths.get("data.xml"), "<data>".getBytes(), StandardOpenOption.APPEND);
                isFirst = false;
            }
            Files.write(Paths.get("data.xml"), "<record>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "<temperature>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), data.getTemperature().toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</temperature>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "<sound>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), data.getSound().toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</sound>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "<co2>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), data.getCO2().toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</co2>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "<voc>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), data.getVOC().toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</voc>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "<dateTime>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), data.getDateTime().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</dateTime>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "<number>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), data.getNumber().toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</number>".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("data.xml"), "</record>".getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
