package com.gaurav.kafkaconsumerimplemenation.device.repository;


import com.gaurav.kafkaconsumerimplemenation.device.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceInfoRepo extends JpaRepository<DeviceInfo, UUID> {
  boolean existsByDeviceId(String deviceId);;
}
