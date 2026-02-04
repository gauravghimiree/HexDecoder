package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.DeviceData;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.DeviceDataDTO;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.ReceiveDTO;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.repository.DeviceDataRepo;
import jakarta.persistence.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeviceDataKafkaConsumer {


  @Autowired
  private final DeviceDataRepo repository;


  @Autowired
  ObjectMapper objectMapper;

  public DeviceDataKafkaConsumer(DeviceDataRepo repository) {
    this.repository = repository;
  }


  @KafkaListener(topics = "messagefromsatelite", groupId = "abc")
  public void consumeMessage(String mqttMessage) {
    try {
      String hexPayload = this.extractHex(mqttMessage).getValue();
      String deviceId = this.extractHex(mqttMessage).getSendId();
      DeviceDataDTO decoded = HexDecoderUtil.decodeHex(hexPayload, deviceId);

      DeviceData entity = mapToEntity(decoded);
      repository.save(entity);

      System.out.println("Saved: " + decoded.getLatitude() + ", " + decoded.getLongitude());
    }
    catch (Exception e) {
      System.err.println("Error processing message: " + e.getMessage());
    }
  }

  private ReceiveDTO extractHex(String message) throws JsonProcessingException {
    ReceiveDTO receiveDTO = objectMapper.readValue(message, ReceiveDTO.class);
    System.out.println(  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(receiveDTO));
    return receiveDTO;
  }



  private DeviceData mapToEntity(DeviceDataDTO dto) {
    return DeviceData.builder()

            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .altitude(dto.getAltitude())
            .battery(dto.getBattery())
            .sosCount(dto.getSosCount())
            .spo2(dto.getSpo2())
            .direction(dto.getDirection())
            .temperature(dto.getTemperature())
            .createdTime(dto.getCreatedTime())
            .heartRate(dto.getHeartRate())
            .deviceId(dto.getDeviceId())
            .deviceCode(dto.getDeviceCode())
            .messageType(dto.getMessageType())
            .locationType(dto.getLocationType())
            .locationValid(dto.getLocationValid())
            .positioningFormat(dto.getPositioningFormat())
            .build();
  }
}
