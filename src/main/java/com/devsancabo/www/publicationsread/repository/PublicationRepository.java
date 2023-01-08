package com.devsancabo.www.publicationsread.repository;

import com.devsancabo.www.publicationsread.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PublicationRepository extends MongoRepository<Publication, Long> {
    List<Publication> findByAuthorNameAndDateTimeGreaterThan(String username, LocalDateTime dateTime);

    List<Publication> findByAuthorNameAndDateTimeGreaterThan(String username, LocalDateTime dateTime, Pageable pageable);
}
