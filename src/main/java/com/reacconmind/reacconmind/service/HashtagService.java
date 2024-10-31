package com.reacconmind.reacconmind.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.HashtagDTO;
import com.reacconmind.reacconmind.model.Hashtag;
import com.reacconmind.reacconmind.repository.HashtagRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HashtagService {
    @Autowired
    private HashtagRepository hashtagRepository;

    public List<Hashtag> getAll(int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Hashtag> hashtags = hashtagRepository.findAll(pageRequest);
        return hashtags.getContent();
    }


    public List<Hashtag> getAll() {
        return hashtagRepository.findAll();
    }

    public Hashtag save(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }

    public Hashtag getByIdHashtag(Integer idHashtag){
        return hashtagRepository.findById(idHashtag).get();
    }

    public Hashtag findByName(String name){
        return hashtagRepository.findByName(name);
    }

    public Optional<Hashtag> updateHashtag(Integer idHashtag, HashtagDTO hashtagDTO){
        return hashtagRepository.findById(idHashtag).map(existingHashtag -> {
            existingHashtag.setName(hashtagDTO.getName());
            return hashtagRepository.save(existingHashtag);
        });
    }
}
