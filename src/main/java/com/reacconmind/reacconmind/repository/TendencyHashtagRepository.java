package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reacconmind.reacconmind.model.TendencyHashtag;
import com.reacconmind.reacconmind.model.TendencyHashtagPK;

@Repository
public interface TendencyHashtagRepository extends JpaRepository<TendencyHashtag, TendencyHashtagPK> {
    
}