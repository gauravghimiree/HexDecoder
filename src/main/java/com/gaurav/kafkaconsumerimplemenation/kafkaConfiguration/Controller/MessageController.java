package com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Controller;


import com.gaurav.kafkaconsumerimplemenation.kafkaConfiguration.Entity.dto.ReceiveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {




//  @GetMapping("/latest-message")
//  public ResponseEntity<ReceiveDTO> getLatestMessage() {
//    ReceiveDTO message = consumer.returnMessage();
//    if (message != null) {
//      return ResponseEntity.ok(message);
//    } else {
//      return ResponseEntity.noContent().build();
//    }
//  }


}
