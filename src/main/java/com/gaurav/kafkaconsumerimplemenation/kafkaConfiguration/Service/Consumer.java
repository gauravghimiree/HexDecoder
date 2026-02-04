//package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Service;
//
//
//import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.ReceiveDTO;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Service
//public class Consumer {
//
//  @Autowired
// ObjectMapper objectMapper;
//
//
//  ReceiveDTO receivedMessage;
//
//
//
//
//  @Transactional
//  @KafkaListener(topics = "messagefromsatelite", groupId = "abc")
//  public void listenGroupabc(String message) {
//    try {
//      ReceiveDTO receiveDTO = objectMapper.readValue(message, ReceiveDTO.class);
//      System.out.println(  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(receiveDTO));
//      receivedMessage = receiveDTO;
////      this.saveData(receiveDTO);
//      System.out.println("data saved in repo successfully");
//    }
//    catch (Exception e)
//    {
//      System.out.println( e.getMessage());
//    }
//  }
//
//  public  ReceiveDTO returnMessage()
//  {
//    return receivedMessage;
//  }
//
//
//}
