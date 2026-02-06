package com.gaurav.kafkaconsumerimplemenation.device.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRegisterDto {
  private  String deviceId;
}
