package com.reacconmind.reacconmind.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.TendencyDTO;
import com.reacconmind.reacconmind.model.Tendency;
import com.reacconmind.reacconmind.repository.TendencyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TendencyService {
    @Autowired
    private TendencyRepository tendencyRepository;

    public  List<Tendency> getAll(int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Tendency> tendencies = tendencyRepository.findAll(pageRequest);
        return tendencies.getContent();
    }

    public List<Tendency> getAll(){
        return tendencyRepository.findAll();
    }


    public Tendency save(Tendency tendency){
        return tendencyRepository.save(tendency);
    }

    public Tendency getByIdTendency(Integer idTendency){
        return tendencyRepository.findById(idTendency).get();
    }

    public Tendency findByName(String tendencyName){
        return tendencyRepository.findByName(tendencyName);
    }

    public Optional<Tendency> updateHashtag(Integer idTendency, TendencyDTO tendencyDTO){
        return tendencyRepository.findById(idTendency).map(existingTendency -> {
            existingTendency.setName(tendencyDTO.getName()); 
            existingTendency.setDateBegin(tendencyDTO.getDateBegin()); 
            existingTendency.setDateEnd(tendencyDTO.getDateEnd());
            return tendencyRepository.save(existingTendency);
        });
    }
}
