package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reacconmind.reacconmind.model.PublicationHashtag;
import com.reacconmind.reacconmind.model.PublicationHashtagPK;

@Repository
public interface PublicationHashtagRepository extends JpaRepository<PublicationHashtag, PublicationHashtagPK> {
    
}
