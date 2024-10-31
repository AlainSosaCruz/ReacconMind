package com.reacconmind.reacconmind.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.BotDTO;
import com.reacconmind.reacconmind.model.Bot;
import com.reacconmind.reacconmind.repository.BotRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BotService {

    @Autowired
    private BotRepository repository;


    public List<Bot> getAllBots() {
        return repository.findAll();
    }

    public Optional<Bot> getBotById(int idBot) {
        return repository.findByIdBot(idBot);
    }

    public Bot createBot(Bot bot) {
        return repository.save(bot);
    }

    public Bot updateBot(int idBot, Bot updatedBot) {
        Optional<Bot> existingBot = repository.findByIdBot(idBot);
        if (existingBot.isPresent()) {
            Bot bot = existingBot.get();
            bot.setName(updatedBot.getName());
            bot.setTheme(updatedBot.getTheme());
            bot.setMultimedia(updatedBot.getMultimedia());
            return repository.save(bot);
        }
        return null;
    }

    public void deleteBot(int idBot) {
        repository.deleteById(idBot);
    }

    public BotDTO convertEntityToDTO(Bot bot) {
        BotDTO dto = new BotDTO();
        dto.setIdBot(bot.getIdBot());
        dto.setName(bot.getName());
        dto.setTheme(bot.getTheme());
        dto.setShippingDate(Timestamp.valueOf(LocalDateTime.now())); 
        dto.setMultimedia(bot.getMultimedia());
        return dto;
    }

    public Bot convertDTOToEntity(BotDTO dto) {
        Bot bot = new Bot();
        bot.setIdBot(dto.getIdBot());
        bot.setName(dto.getName());
        bot.setTheme(dto.getTheme());
        bot.setMultimedia(dto.getMultimedia());
        return bot;
    }
}
