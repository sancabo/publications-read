package com.devsancabo.www.publicationsread.service;

import com.devsancabo.www.publicationsread.dto.AuthorDTO;
import com.devsancabo.www.publicationsread.dto.GetPopulatorResponseDTO;
import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.entity.Publication;
import com.devsancabo.www.publicationsread.populator.Populator;
import com.devsancabo.www.publicationsread.populator.PublicationApiException;
import com.devsancabo.www.publicationsread.repository.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PublicationService {
    private final Logger logger = LoggerFactory.getLogger(PublicationService.class);
    private final PublicationRepository publicationRepository;
    private final Populator<PublicationDTO> populator;

    @Autowired
    PublicationService(final PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
        this.populator = new MongoPublicationPopulator();
    }

    public List<PublicationDTO> searchPublications(String userName, LocalDateTime dateTime) throws PublicationApiException {
        logger.info("Search publication[username={},dateTime={}]", userName, dateTime);
        var realDatetime = dateTime == null ? LocalDateTime.ofInstant(Instant.MIN, ZoneId.of("-03:00")) : dateTime;
        List<Publication> publications;
        try {
            publications = publicationRepository.findByAuthorNameAndDateTimeGreaterThan(userName, realDatetime);
            logger.info("Found publication[username={},dateTime={}]", userName, dateTime);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new PublicationApiException(
                    "Error finding publication[username=" + userName + ",dateTime=" + dateTime + "] : " + e.getLocalizedMessage());
        }
        return publications.stream().map(PublicationService::convertToDto).toList();
    }

    public static PublicationDTO convertToDto(final Publication publication) {
        return PublicationDTO.builder()
                .id(publication.getId())
                .author(new AuthorDTO(publication.getAuthorId(), publication.getAuthorName()))
                .content(publication.getContent())
                .datetime(publication.getDateTime()).build();
    }

    public static Publication convertToEntity(final PublicationDTO publication) {
        return new Publication(publication.getAuthor().getId(),
                publication.getAuthor().getUsername(), LocalDateTime.now(), publication.getContent());
    }

    public PublicationDTO createPublication(PublicationDTO publication) throws PublicationApiException {
        logger.info("Create Publication[username={}]", publication.getAuthor().getUsername());
        Publication createdPublication;
        try {
            createdPublication = publicationRepository.insert(convertToEntity(publication));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new PublicationApiException("Error inserting new publication: " + e.getLocalizedMessage());
        }
        logger.info("Publication Created[username={}, dateTime={}, id={}]",
                createdPublication.getAuthorName(), createdPublication.getDateTime(), createdPublication.getAuthorId());
        return convertToDto(createdPublication);
    }


    public GetPopulatorResponseDTO startPopulator(Integer intensity, Boolean runForever) {

        return populator.startPopulator(intensity, runForever);
    }

    public void stopPopulators() {
        populator.stopPopulator();
    }

    public GetPopulatorResponseDTO gerPopulator() {
        return populator.getPopulatorDTO();
    }
}
