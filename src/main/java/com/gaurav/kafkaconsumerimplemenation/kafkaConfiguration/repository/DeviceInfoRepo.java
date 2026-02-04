package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.repository;


import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceInfoRepo extends JpaRepository<DeviceInfo, UUID> {
}
