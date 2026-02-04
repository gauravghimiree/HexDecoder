package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto;
import lombok.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class DeviceDataDTO {
  private Float latitude;
  private Float longitude;
  private Integer altitude;
  private Integer battery;            // 0-100 (%)
  private Integer sosCount;           // SOS button press count
  private Integer spo2;               // Blood oxygen 0-100 (%)
  private Integer direction;          // Degrees (0-360)
  private Float temperature;          // Celsius (0.1°C unit)
  private Instant createdTime;        // UTC timestamp from device
  private Integer heartRate;          // BPM
  private String deviceId;            // SendId from message
  private String deviceCode;          // Model/code from device
  private String messageType;         // "09" or "0C"
  private String locationType;        // "0"=regular, "1"=SOS
  private Boolean locationValid;      // Protocol validation
  private String positioningFormat;  // parsed from SendId when possible


}

