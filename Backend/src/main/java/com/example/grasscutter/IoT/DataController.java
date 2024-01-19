package com.example.grasscutter.IoT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    private final MQTTConfig mqttConfig;

    @Autowired
    public DataController(MQTTConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    @GetMapping("/stopAdding")
    public ResponseEntity<String> stopAddingData() {
        try {
            mqttConfig.stopAddingData();
            return ResponseEntity.ok("Stopped adding data and saved to MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error stopping data addition: " + e.getMessage());
        }
    }

//    @GetMapping("/getAllData")
//    public ResponseEntity<List<AngleDistancePair>> getAllData() {
//        try {
//            List<AngleDistancePair> data = mqttConfig.getAndClearTemporaryData();
//            return ResponseEntity.ok(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body(null);
//        }
//    }
}

