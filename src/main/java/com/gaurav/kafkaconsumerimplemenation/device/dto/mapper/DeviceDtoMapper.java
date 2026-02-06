package com.gaurav.kafkaconsumerimplemenation.device.dto.mapper;


import com.gaurav.kafkaconsumerimplemenation.device.dto.DeviceRegisterDto;
import com.gaurav.kafkaconsumerimplemenation.device.entity.DeviceInfo;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.DeviceDataDTO;
import org.springframework.stereotype.Service;

@Service
public class DeviceDtoMapper {

  public DeviceInfo DeviceRegisterDtoToEntity(DeviceRegisterDto deviceRegisterDto)
  {
    DeviceInfo device = new DeviceInfo();
    device.setDeviceId(deviceRegisterDto.getDeviceId());
    return  device;
  }
}
