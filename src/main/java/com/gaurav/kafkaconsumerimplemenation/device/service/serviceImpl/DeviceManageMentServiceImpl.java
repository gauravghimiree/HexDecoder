package com.gaurav.kafkaconsumerimplemenation.device.service.serviceImpl;

import com.gaurav.kafkaconsumerimplemenation.device.Exception.DeviceAlreadyExist;
import com.gaurav.kafkaconsumerimplemenation.device.dto.DeviceRegisterDto;
import com.gaurav.kafkaconsumerimplemenation.device.dto.mapper.DeviceDtoMapper;
import com.gaurav.kafkaconsumerimplemenation.device.entity.DeviceInfo;
import com.gaurav.kafkaconsumerimplemenation.device.repository.DeviceInfoRepo;
import com.gaurav.kafkaconsumerimplemenation.device.service.DeviceMangeMentService;
import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.DeviceDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceManageMentServiceImpl implements DeviceMangeMentService {

  @Autowired
  DeviceInfoRepo deviceRepo;

  @Autowired
  DeviceDtoMapper deviceDtoMapper;


  @Override
  public DeviceRegisterDto registerDevice(DeviceRegisterDto deviceRegisterDto) {

    if (!deviceRepo.existsByDeviceId(deviceRegisterDto.getDeviceId())) {
      DeviceInfo deviceInfos = deviceDtoMapper.DeviceRegisterDtoToEntity(deviceRegisterDto);
      deviceRepo.save(deviceInfos);
      return deviceRegisterDto;
    } else {
      throw new DeviceAlreadyExist("device with this id has been already registered in the system");
    }
  }
}
