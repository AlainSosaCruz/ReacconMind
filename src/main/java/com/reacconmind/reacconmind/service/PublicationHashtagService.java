package com.reacconmind.reacconmind.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.PublicationHashtag;
import com.reacconmind.reacconmind.model.PublicationHashtagPK;
import com.reacconmind.reacconmind.repository.PublicationHashtagRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PublicationHashtagService {
    @Autowired
    private PublicationHashtagRepository publicationHashtagRepository;

    public List <PublicationHashtag> getAll(int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<PublicationHashtag> publicationHashtag= publicationHashtagRepository.findAll(pageRequest);
        return publicationHashtag.getContent();
    }

    public List<PublicationHashtag> getAllPublicationHashtags() {
        return publicationHashtagRepository.findAll();
    }

    public void savePublicationHashtag(PublicationHashtag publicationHashtag) {
        publicationHashtagRepository.save(publicationHashtag);
    }

    public PublicationHashtag getPublicationHashtagById(PublicationHashtagPK id) {
        return publicationHashtagRepository.findById(id).orElse(null);
    }

    public void deletePublicationHashtagById(PublicationHashtagPK id) {
        publicationHashtagRepository.deleteById(id);
    }
}
