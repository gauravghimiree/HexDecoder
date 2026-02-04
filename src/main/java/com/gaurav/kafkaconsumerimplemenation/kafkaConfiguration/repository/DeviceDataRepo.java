package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.repository;

import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceDataRepo extends JpaRepository<DeviceData, UUID> {
}
