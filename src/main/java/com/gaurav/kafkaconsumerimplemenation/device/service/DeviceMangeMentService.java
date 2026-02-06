package com.gaurav.kafkaconsumerimplemenation.device.service;

import com.gaurav.kafkaconsumerimplemenation.device.dto.DeviceRegisterDto;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.DeviceDataDTO;

public interface DeviceMangeMentService {
  public DeviceRegisterDto registerDevice(DeviceRegisterDto deviceRegisterDto);
}
