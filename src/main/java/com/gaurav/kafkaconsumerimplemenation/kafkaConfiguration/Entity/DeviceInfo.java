package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
  private List<DeviceData> deviceData;

}
