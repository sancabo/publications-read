package com.devsancabo.www.publicationsread.service;

import com.devsancabo.www.LoremIpsum;
import com.devsancabo.www.publicationsread.event.producer.PublicationProducer;
import com.devsancabo.www.publicationsread.dto.AuthorDTO;
import com.devsancabo.www.publicationsread.dto.GetPopulatorResponseDTO;
import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.entity.Publication;
import com.devsancabo.www.publicationsread.populator.MongoPublicationPopulator;
import com.devsancabo.www.publicationsread.populator.Populator;
import com.devsancabo.www.publicationsread.exception.PublicationApiException;
import com.devsancabo.www.publicationsread.repository.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class PublicationServiceImpl implements PublicationService {
    private final Logger logger = LoggerFactory.getLogger(PublicationServiceImpl.class);
    private final PublicationRepository publicationRepository;
    private final Populator<PublicationDTO> populator;

    private final PublicationProducer publicationProducer;

    @Autowired
    PublicationServiceImpl(final PublicationRepository publicationRepository,
                           final PublicationProducer publicationProducer) {
        this.publicationRepository = publicationRepository;
        this.publicationProducer = publicationProducer;
        this.populator = new MongoPublicationPopulator(() ->
                PublicationDTO.builder()
                        .author(AuthorDTO.newAuthor().withUsername(UUID.randomUUID().toString()).
                                withId(UUID.randomUUID().getLeastSignificantBits()))
                        .content(LoremIpsum.getRandomSentence(25))
                        .datetime(LocalDateTime.now())
                        .build()
                , publicationDTO -> {
            try {
                createPublicationFromEvent(publicationDTO);
            } catch (PublicationApiException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public List<PublicationDTO> searchPublications(String userName,
                                                   LocalDateTime dateTime,
                                                   Integer pageSize,
                                                   Integer pageNumber) throws PublicationApiException {
        logger.info("Search publication[username={},dateTime={}]", userName, dateTime);
        var realDatetime = dateTime == null ? LocalDateTime.ofInstant(Instant.MIN, ZoneId.of("-03:00")) : dateTime;
        List<Publication> publications;
        try {
            if (pageSize != null && pageNumber != null) {
                publications = publicationRepository.findByAuthorNameAndDateTimeGreaterThan
                        (userName, realDatetime, PageRequest.of(pageNumber - 1, pageSize));
            } else {
                publications = publicationRepository.findByAuthorNameAndDateTimeGreaterThan(userName, realDatetime);
            }
            logger.info("Found publication[username={},dateTime={}]", userName, dateTime);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new PublicationApiException(
                    "Error finding publication[username=" + userName + ",dateTime=" + dateTime + "] : " + e.getLocalizedMessage());
        }
        return publications.stream().map(PublicationServiceImpl::convertToDto).toList();
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

    @Override
    public PublicationDTO createPublication(PublicationDTO publication) {
        logger.info("Create Publication {}", publication);
        publicationProducer.send(publication);
        return publication;
    }

    @Override
    public void createPublicationFromEvent(PublicationDTO publication) throws PublicationApiException {
        logger.info("Process event - create publication - [{}]", publication);
        Publication createdPublication;
        try {
            createdPublication = publicationRepository.insert(convertToEntity(publication));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new PublicationApiException("Error inserting new publication: " + e.getLocalizedMessage());
        }
        logger.info("Publication Created[username={}, dateTime={}, id={}]",
                createdPublication.getAuthorName(), createdPublication.getDateTime(), createdPublication.getAuthorId());
    }


    @Override
    public GetPopulatorResponseDTO startPopulator(Integer intensity, Boolean runForever) {

        return populator.startPopulator(intensity, runForever);
    }

    @Override
    public void stopPopulators() {
        populator.stopPopulator();
    }

    @Override
    public GetPopulatorResponseDTO gerPopulator() {
        return populator.getPopulatorDTO();
    }
}
