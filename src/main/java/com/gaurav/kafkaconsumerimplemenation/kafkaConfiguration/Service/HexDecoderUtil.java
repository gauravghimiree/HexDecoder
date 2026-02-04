package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Service;


import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.DeviceDataDTO;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;

public class HexDecoderUtil {

    private static final String MESSAGE_TYPE_LOCATION = "09";
    private static final String MESSAGE_TYPE_SOS = "0C";


    public static DeviceDataDTO decodeHex(String hexRaw, String deviceId) {
      if (hexRaw == null) {
        throw new IllegalArgumentException("Hex data cannot be null");
      }

      String dataStr = hexRaw.replace(" ", "");

      // Validate length
      if (dataStr.length() != 84 && dataStr.length() != 70) {
        throw new IllegalArgumentException("Invalid data length: " + dataStr.length());
      }


      String messageType = dataStr.substring(6, 8);

      if (MESSAGE_TYPE_LOCATION.equals(messageType)) {
        return decodeLocationMessage(dataStr, deviceId);
      } else if (MESSAGE_TYPE_SOS.equals(messageType)) {
        return decodeSosMessage(dataStr, deviceId);
      } else {
        throw new IllegalArgumentException("Unknown message type: " + messageType);
      }
    }

    /**
     * Decode regular location message (0x09)
     */
    private static DeviceDataDTO decodeLocationMessage(String dataStr, String deviceId) {
      ValidationResult v = isLocationValid(dataStr, 56);
      if (!v.isValid) {
        throw new IllegalStateException("Location invalid");
      }

      LocationInfo info = parseLocationInfo(dataStr, v.binaryResult, false);
      return buildDto(info, 0, deviceId, MESSAGE_TYPE_LOCATION);
    }

    /**
     * Decode SOS message (0x0C)
     */
    private static DeviceDataDTO decodeSosMessage(String dataStr, String deviceId) {
      ValidationResult v = isLocationValid(dataStr, 54);
      if (!v.isValid) {
        throw new IllegalStateException("Location invalid");
      }

      LocationInfo info = parseLocationInfo(dataStr, v.binaryResult, true);
      parseHealthData(dataStr, info);

      return buildDto(info, info.sosNum, deviceId, MESSAGE_TYPE_SOS);
    }

    /**
     * Build DeviceDataResponseDto from LocationInfo
     */
    private static DeviceDataDTO buildDto(LocationInfo info, int sosNum, String deviceId, String messageType) {
      return DeviceDataDTO.builder()

              .latitude(info.latitude != null ? info.latitude.floatValue() : null)
              .longitude(info.longitude != null ? info.longitude.floatValue() : null)
              .altitude(info.altitude)
              .battery(info.electricity)
              .sosCount(sosNum)
              .spo2(info.spo2)
              .direction(info.direction)
              .temperature(info.temperature)
              .heartRate(info.heartRate)
              .createdTime(Instant.ofEpochSecond(info.utcTimeEpochSeconds))
              .deviceId(deviceId)
              .deviceCode(parseDeviceCode(deviceId))
              .messageType(messageType)
              .locationType(info.locationType)
              .locationValid(true)
              .positioningFormat(info.highBits)
              .build();
    }

    /**
     * Parse device code from deviceId
     */
    private static String parseDeviceCode(String deviceId) {
      if (deviceId == null) return null;
      try {
        Long.parseLong(deviceId);
        return deviceId;
      } catch (NumberFormatException ex) {
        return String.valueOf(deviceId.hashCode());
      }
    }



    private static class ValidationResult {
      boolean isValid;
      String binaryResult;

      ValidationResult(boolean valid, String binary) {
        this.isValid = valid;
        this.binaryResult = binary;
      }
    }

    private static class LocationInfo {
      long utcTimeEpochSeconds;
      String highBits;
      String lonIndicator;
      String lonHex;
      String latIndicator;
      String latHex;
      Double longitude;
      Double latitude;
      Integer direction;
      Integer altitude;
      Integer electricity;
      Integer heartRate;
      Integer spo2;
      Float temperature;
      Integer sosNum;
      String locationType;
    }

    // ==================== Parsing Methods ====================

    /**
     * Check if location is valid (bit 0 of positioning type)
     */
    private static ValidationResult isLocationValid(String dataStr, int position) {
      String lastTwoChars = dataStr.substring(position, position + 2);
      int val = Integer.parseInt(lastTwoChars, 16);
      String binaryResult = String.format("%8s", Integer.toBinaryString(val)).replace(' ', '0');
      boolean isValid = binaryResult.charAt(7) == '1';
      return new ValidationResult(isValid, binaryResult);
    }

    /**
     * Parse location information
     */
    private static LocationInfo parseLocationInfo(String dataStr, String binaryResult, boolean isSos) {
      LocationInfo info = new LocationInfo();
      info.utcTimeEpochSeconds = Long.parseLong(dataStr.substring(10, 18), 16);
      info.highBits = binaryResult.substring(0, 4);

      int lonIndIdx = isSos ? 22 : 24;
      info.lonIndicator = dataStr.substring(lonIndIdx, lonIndIdx + 2);

      int lonHexIdx = isSos ? 24 : 26;
      info.lonHex = dataStr.substring(lonHexIdx, lonHexIdx + 8);

      int latIndIdx = isSos ? 32 : 34;
      info.latIndicator = dataStr.substring(latIndIdx, latIndIdx + 2);

      int latHexIdx = isSos ? 34 : 36;
      info.latHex = dataStr.substring(latHexIdx, latHexIdx + 8);

      int dirIdx = isSos ? 46 : 48;
      info.direction = Integer.parseInt(dataStr.substring(dirIdx, dirIdx + 4), 16);

      int altIdx = isSos ? 50 : 52;
      info.altitude = parseAltitude(dataStr.substring(altIdx, altIdx + 4));

      int elecIdx = isSos ? 56 : 66;
      info.electricity = Integer.parseInt(dataStr.substring(elecIdx, elecIdx + 2), 16);

      if (isSos) {
        info.sosNum = Integer.parseInt(dataStr.substring(80, 82), 16);
        info.locationType = "1";
      } else {
        info.sosNum = 0;
        info.locationType = "0";
      }

      double[] coords = parseCoordinates(info.highBits, info.lonHex, info.latHex,
              info.lonIndicator, info.latIndicator);
      info.longitude = coords[0];
      info.latitude = coords[1];

      return info;
    }

    /**
     * Parse health data (heart rate, SpO2, temperature)
     */
    private static void parseHealthData(String dataStr, LocationInfo info) {
      String heartRatePrefix = dataStr.substring(66, 68);
      String spo2Prefix = dataStr.substring(70, 72);
      String temperaturePrefix = dataStr.substring(74, 76);

      if ("52".equals(heartRatePrefix)) {
        info.heartRate = Integer.parseInt(dataStr.substring(68, 70), 16);
      }
      if ("53".equals(spo2Prefix)) {
        info.spo2 = Integer.parseInt(dataStr.substring(72, 74), 16);
      }
      if ("54".equals(temperaturePrefix)) {
        int tempRaw = Integer.parseInt(dataStr.substring(76, 80), 16);
        info.temperature = (float) (tempRaw * 0.1);
      }
    }

    /**
     * Parse coordinates based on format (4 formats supported)
     */
    private static double[] parseCoordinates(String highBits, String lonHex, String latHex,
                                             String lonIndicator, String latIndicator) {
      boolean isEast = "45".equals(lonIndicator);
      boolean isNorth = "4E".equals(latIndicator);

      double lon, lat;
      switch (highBits) {
        case "0000": // DMS format
          lon = parseDMS(lonHex, isEast);
          lat = parseDMS(latHex, isNorth);
          break;
        case "0001": // Float format
          lon = parseFloatDegree(lonHex, isEast);
          lat = parseFloatDegree(latHex, isNorth);
          break;
        case "0010": // High precision format
          lon = parseHighPrecisionDegree(lonHex, isEast);
          lat = parseHighPrecisionDegree(latHex, isNorth);
          break;
        case "0011": // Degree-minute float format
          lon = parseDegreeMinuteFloat(lonHex, isEast);
          lat = parseDegreeMinuteFloat(latHex, isNorth);
          break;
        default:
          throw new IllegalArgumentException("Unsupported format: " + highBits);
      }

      return new double[]{Math.round(lon * 1e7) / 1e7, Math.round(lat * 1e7) / 1e7};
    }

    /**
     * Parse DMS format (degrees, minutes, seconds)
     */
    private static double parseDMS(String hexData, boolean isPositive) {
      int degrees = Integer.parseInt(hexData.substring(0, 2), 16);
      int minutes = Integer.parseInt(hexData.substring(2, 4), 16);
      int seconds = Integer.parseInt(hexData.substring(4, 6), 16);
      double fraction = Integer.parseInt(hexData.substring(6, 8), 16) / 10.0;

      double coordinate = degrees + minutes / 60.0 + (seconds + fraction) / 3600.0;
      return isPositive ? coordinate : -coordinate;
    }

    /**
     * Parse float format (decimal degrees)
     */
    private static double parseFloatDegree(String hexData, boolean isPositive) {
      byte[] bytes = new byte[4];
      for (int i = 0; i < 4; i++) {
        bytes[i] = (byte) Integer.parseInt(hexData.substring(i * 2, i * 2 + 2), 16);
      }
      float value = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
      return isPositive ? value : -value;
    }

    /**
     * Parse high precision format (0.000000001 degrees)
     */
    private static double parseHighPrecisionDegree(String hexData, boolean isPositive) {
      int value = (int) Long.parseLong(hexData, 16);
      double coordinate = value * 0.00000001;
      return isPositive ? coordinate : -coordinate;
    }

    /**
     * Parse degree-minute float format (0.000001)
     */
    private static double parseDegreeMinuteFloat(String hexData, boolean isPositive) {
      int degrees = Integer.parseInt(hexData.substring(0, 2), 16);
      double minutes = Integer.parseInt(hexData.substring(2, 4), 16) / 100000.0;
      double coordinate = degrees + minutes / 60.0;
      return isPositive ? coordinate : -coordinate;
    }

    /**
     * Parse altitude (signed 16-bit)
     */
    private static Integer parseAltitude(String altitudeHex) {
      int altitudeRaw = Integer.parseInt(altitudeHex, 16);
      boolean isNegative = (altitudeRaw & 0x8000) != 0;
      return isNegative ? -(altitudeRaw & 0x7FFF) : altitudeRaw;
    }
  }


