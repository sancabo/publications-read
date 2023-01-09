package com.devsancabo.www.publicationsread.event.producer;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;

public interface PublicationProducer {
    void send(PublicationDTO publicationDTO);
}
