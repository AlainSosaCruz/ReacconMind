package com.reacconmind.reacconmind.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.Hashtag;
import com.reacconmind.reacconmind.model.TendencyHashtag;
import com.reacconmind.reacconmind.model.TendencyHashtagPK;
import com.reacconmind.reacconmind.repository.TendencyHashtagRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TendencyHashtagService {
    @Autowired
    private TendencyHashtagRepository tendencyHashtagRepository;

    public List<TendencyHashtag> getAll(int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<TendencyHashtag> tendencyhashtags = tendencyHashtagRepository.findAll(pageRequest);
        return tendencyhashtags.getContent();
    }

    public List<TendencyHashtag> getAllTendencyHashtags() {
        return tendencyHashtagRepository.findAll();
    }

    public void saveTendencyHashtag(TendencyHashtag tendencyHashtag) {
        tendencyHashtagRepository.save(tendencyHashtag);
    }

    public TendencyHashtag getTendencyHashtagById(TendencyHashtagPK id) {
        return tendencyHashtagRepository.findById(id).orElse(null);
    }

    public void deleteTendencyHashtagById(TendencyHashtagPK id) {
        tendencyHashtagRepository.deleteById(id);
    }
}
