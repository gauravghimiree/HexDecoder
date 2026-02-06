package com.gaurav.kafkaconsumerimplemenation.device.Exception;

public class DeviceAlreadyExist extends RuntimeException {
  public DeviceAlreadyExist(String message) {
    super(message);
  }
}
