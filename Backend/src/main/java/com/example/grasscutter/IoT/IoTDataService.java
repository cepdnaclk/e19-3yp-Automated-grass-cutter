package com.example.grasscutter.IoT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IoTDataService {

    private final IoTDataRepository ioTDataRepository;

    @Autowired
    public IoTDataService(IoTDataRepository ioTDataRepository) {
        this.ioTDataRepository = ioTDataRepository;
    }

    public void saveIoTData(IoTData ioTData) {
        ioTDataRepository.save(ioTData);
    }

    public IoTData getIoTDataById(String id) {
        return ioTDataRepository.findById(id).orElse(null);
    }

    // Add more methods as needed for your use case
}
