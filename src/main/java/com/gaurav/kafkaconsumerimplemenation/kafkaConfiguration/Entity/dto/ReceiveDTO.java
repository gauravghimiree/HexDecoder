package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceiveDTO {
  @JsonProperty("Id")                 // JSON "Id" → message_id
  private Long message_id;

  @JsonProperty("Data")               // JSON "Data" → value
  private String value;

  @JsonProperty("DataLength")         // JSON "DataLength" → data_length
  private Integer data_length;

  @JsonProperty("MqMsgType")          // JSON "MqMsgType" → mqMsgType
  private Integer mqMsgType;

  @JsonProperty("SendId")             // JSON "SendId" → sendId
  private String sendId;

  @JsonProperty("SendTime")           // JSON "SendTime" → send_time
  private Long sendTime;

}
