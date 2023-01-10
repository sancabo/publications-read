package com.devsancabo.www.publicationsread.event.producer;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class PublicationProducerImpl implements PublicationProducer {
    private final Logger logger = LoggerFactory.getLogger(PublicationProducerImpl.class);
    private final KafkaTemplate<String, PublicationDTO> kafkaTemplate;

    @Autowired
    public PublicationProducerImpl(final KafkaTemplate<String, PublicationDTO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void send(PublicationDTO publicationDTO){
        logger.info("Producing Event: {}", publicationDTO);
        var future = kafkaTemplate.send("publication-events",publicationDTO);
        logger.info("Produced event successfully");
    }
}
