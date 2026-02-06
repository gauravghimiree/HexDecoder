package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity;


import com.gaurav.kafkaconsumerimplemenation.device.entity.DeviceInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class DeviceData {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "latitude", nullable = false)
  private Float latitude;

  @Column(name = "longitude", nullable = false)
  private Float longitude;

  @Column(name = "altitude", nullable = true)
  private Integer altitude;

  @Column(name = "battery", nullable = true)
  private Integer battery;

  @Column(name = "sos_count", nullable = true)
  private Integer sosCount;

  @Column(name = "spo2", nullable = true)
  private Integer spo2;

  @Column(name = "direction", nullable = true)
  private Integer direction;

  @Column(name = "temperature", nullable = true)
  private Float temperature;

  @Column(name = "created_time", nullable = false)
  private Instant createdTime;

  @Column(name = "heart_rate", nullable = true)
  private Integer heartRate;

  @Column(name = "device_id", nullable = false)
  private String deviceId;

  @Column(name = "device_code", nullable = true)
  private String deviceCode;

  @Column(name = "message_type", nullable = false, length = 2)
  private String messageType;

  @Column(name = "location_type", nullable = true, length = 1)
  private String locationType;

  @Column(name = "location_valid", nullable = false)
  private Boolean locationValid;

  @Column(name = "positioning_format", nullable = true, length = 4)
  private String positioningFormat;

  @CreationTimestamp
  @Column(name = "saved_at", nullable = false, updatable = false)
  private Instant savedAt;

  @Version
  @Column(name = "version")
  private Long version;


  @ManyToOne
  @JoinColumn(name = "device_ids")
  private DeviceInfo device;
}
