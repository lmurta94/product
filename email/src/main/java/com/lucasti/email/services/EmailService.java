package com.lucasti.email.services;

import com.lucasti.email.dtos.ProductReceiverDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    JavaMailSender javaMailSender;


    @RetryableTopic(attempts = "1", backoff = @Backoff(delay = 3000, multiplier = 2, maxDelay = 12000))
    @KafkaListener(topics = "topic_product",
//            containerFactory = "kafkaListenerContainerFactory",
            groupId = "EmaiL-Application"
    )
    public void sendEmail(ProductReceiverDTO productReceiverDTO
//                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                          @Header(KafkaHeaders.OFFSET) Long offset
    ) {

            log.error("Sending message to DLQ {}", productReceiverDTO);


//        sendEmail();
    }


//    @DltHandler
//    public void listenDLT(
//            ConsumerRecord<String, ProductReceiverDTO> record,
//            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//            @Header(KafkaHeaders.OFFSET) Long offset
//    ) {
//        log.warn("Message: {}, topic: {}, offset: {}", record, topic, offset);
//
//    }

    private void sendEmail() {
        var message = new SimpleMailMessage();
        message.setFrom("teste@gmail.com");
        message.setTo("lmurta94@gmail.com");
        message.setTo("marjoriecorreia1996@gmail.com");
        message.setCc("maahcristiine@gmail.com");
        message.setSubject("Dinner invitation");
        message.setText(" Passou no teste moranguinho");


        javaMailSender.send(message);
    }

}