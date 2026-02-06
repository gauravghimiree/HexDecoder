package com.gaurav.kafkaconsumerimplemenation.device.controller;


import com.gaurav.kafkaconsumerimplemenation.device.dto.DeviceRegisterDto;
import com.gaurav.kafkaconsumerimplemenation.device.service.serviceImpl.DeviceManageMentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/device")
public class DeviceController {

  @Autowired
  DeviceManageMentServiceImpl deviceManageMentService;

  @PostMapping("/addDevice")
  public ResponseEntity<DeviceRegisterDto> addDevice( @RequestBody DeviceRegisterDto deviceRegisterDto)
  {
    deviceManageMentService.registerDevice(deviceRegisterDto);
    return new ResponseEntity<>(deviceRegisterDto, HttpStatus.OK);
  }
}
