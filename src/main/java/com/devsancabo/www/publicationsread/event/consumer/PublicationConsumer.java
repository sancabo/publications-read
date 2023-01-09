package com.devsancabo.www.publicationsread.event.consumer;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.exception.PublicationApiException;
import com.devsancabo.www.publicationsread.service.PublicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PublicationConsumer {
    private final Logger logger = LoggerFactory.getLogger(PublicationConsumer.class);
    private final PublicationService service;

    @Autowired
    public PublicationConsumer(final PublicationService service){
        this.service = service;
    }
    @KafkaListener(topics = "publication-events")
    public void processMessage(PublicationDTO content) throws PublicationApiException {
        logger.info("received event = {} - {} ", content, content.hashCode());
        this.service.createPublicationFromEvent(content);
        logger.info("processed event[{}] successfully", content.hashCode());
    }
}
