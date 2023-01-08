package com.devsancabo.www.publicationsread.consumer;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class PublicationProducer {
    private final Logger logger = LoggerFactory.getLogger(PublicationProducer.class);
    private final KafkaTemplate<String, PublicationDTO> kafkaTemplate;

    @Autowired
    public PublicationProducer(final KafkaTemplate<String, PublicationDTO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(PublicationDTO publicationDTO){
        logger.info("Producing Event: {}", publicationDTO);
        kafkaTemplate.send("publication-events",publicationDTO);
        logger.info("Produced event successfully");
    }
}
