package com.devsancabo.www.publicationsread.controller;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.populator.PublicationApiException;
import com.devsancabo.www.publicationsread.service.PublicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PublicationController {
    private final PublicationService publicationService;

    @Autowired
    public PublicationController(final PublicationService publicationService) {
        this.publicationService = publicationService;

    }

    @PostMapping("/publicacion")
    @Validated
    public ResponseEntity<PublicationDTO> search(@RequestBody @Valid PublicationDTO publication)
            throws PublicationApiException {
        return ResponseEntity.ok(publicationService.createPublication(publication));
    }

    @GetMapping("/publicacion")
    public ResponseEntity<List<PublicationDTO>> search(@RequestParam(name = "userName") String userName,
                                                       @RequestParam(required = false, name = "date") LocalDateTime date,
                                                       @RequestParam(name = "pageSize")
                                                           @Min(value = 1, message = "pageSize has to be greater than 0")
                                                           Integer pageSize,
                                                       @RequestParam(name = "pageNumber")
                                                           @Min(value = 1, message = "pageNumber has to be greater than 0")
                                                           Integer pageNumber)
            throws PublicationApiException {
        return ResponseEntity.ok(publicationService.searchPublications(userName, date, pageSize, pageNumber));

    }
}