package com.devsancabo.www.publicationsread.service;

import com.devsancabo.www.publicationsread.dto.GetPopulatorResponseDTO;
import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.exception.PublicationApiException;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicationService {
    List<PublicationDTO> searchPublications(String userName,
                                            LocalDateTime dateTime,
                                            Integer pageSize,
                                            Integer pageNumber) throws PublicationApiException;

    PublicationDTO createPublication(PublicationDTO publication);

    void createPublicationFromEvent(PublicationDTO publication) throws PublicationApiException;

    GetPopulatorResponseDTO startPopulator(Integer intensity, Boolean runForever);

    void stopPopulators();

    GetPopulatorResponseDTO gerPopulator();
}
