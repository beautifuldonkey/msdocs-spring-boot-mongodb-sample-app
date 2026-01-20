package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.appservice.examples.springbootmongodb.dao.GameLogsRepository;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.BdpResp;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.GameLogsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class GameLogsController {

    private static Logger logger = LoggerFactory.getLogger(GameLogsController.class);

    @Autowired
    private GameLogsRepository gameLogsRepository;

    public GameLogsController() {
    }

    /**
     * HTTP GET
     */
    @GetMapping(path = "/api/gamelogs/{index}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public BdpResp getGameLogsItem(@PathVariable("index") String index) {
        logger.info("GET request access '/api/gamelogs/{}' path.", index);
        BdpResp resp = new BdpResp();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            GameLogsEntry gameLogsEntry = gameLogsRepository.findById(index).get();
            resp.setData(objectMapper.writeValueAsString(gameLogsEntry));
            resp.setStatus("success");
            resp.setMessage("game long item found");
        } catch (Exception e) {
            logger.error("Get event errors: ", e);
            resp.setStatus("error");
            resp.setMessage("game log item not found");
        }
        return resp;
    }

    /**
     * HTTP GET ALL
     */
    @GetMapping(path = "/api/gamelogs", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<GameLogsEntry> getAllEventItems() {
        logger.info("GET request access '/api/gamelogs' path.");

        // limit number of returned items to 100
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "id"));
        return gameLogsRepository.findAll(pageable).getContent();
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(path = "/api/gamelogs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp addGamelogs(@RequestBody List<GameLogsEntry> items) {
        logger.info("POST request access '/api/gamelogs' path with item: {}", items);
        BdpResp resp = new BdpResp();
        try {
            for(GameLogsEntry item : items) {
                if(item.getId() == null) {
                    item.setId(UUID.randomUUID().toString());
                } else {
                    gameLogsRepository.deleteById(item.getId());
                }
                gameLogsRepository.save(item);
            }
            resp.setStatus("success");
            resp.setMessage("Game logs saved");
            return resp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "game log item save failed");
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @PutMapping(path = "/api/gamelogs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateEventItem(@RequestBody GameLogsEntry item) {
        logger.info("PUT request access '/api/gamelogs' path with item {}", item);
        try {
            gameLogsRepository.deleteById(item.getId());
            gameLogsRepository.save(item);
            return "game log item updated";
        } catch (Exception e) {
            logger.error("Update errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game log item not found");
        }
    }

    /**
     * HTTP DELETE
     */
    @DeleteMapping("/api/gamelogs/{id}")
    public BdpResp deleteEventItem(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/gamelogs/{}' path.", id);
        BdpResp resp = new BdpResp();
        try {
            gameLogsRepository.deleteById(id);
            resp.setStatus("success");
            resp.setMessage("game log deleted");
            return resp;
        } catch (Exception e) {
            logger.error("Delete errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game log delete failed");
        }

    }
}
