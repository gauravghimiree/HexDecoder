package com.gaurav.kafkaconsumerimplemenation.device.entity;


import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.DeviceData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class DeviceInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name="device_id")
  private String deviceId;


  @Column(name="registered_Time")
  private Instant registerdDate;


  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
  private List<DeviceData> deviceData;

}
