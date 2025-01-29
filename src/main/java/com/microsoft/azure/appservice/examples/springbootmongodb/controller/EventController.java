package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.microsoft.azure.appservice.examples.springbootmongodb.dao.EventRepository;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.EventItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class EventController {

    private static Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventRepository eventRepository;

    public EventController() {
    }

    /**
     * HTTP GET
     */
    @GetMapping(path = "/api/event/{index}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public EventItem getEventItem(@PathVariable("index") String index) {
        logger.info("GET request access '/api/event/{}' path.", index);
        return eventRepository.findById(index).get();
    }

    /**
     * HTTP GET ALL
     */
    @GetMapping(path = "/api/event", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EventItem> getAllEventItems() {
        logger.info("GET request access '/api/event' path.");

        List<EventItem> items = eventRepository.findAll();
        return eventRepository.findAll();
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(path = "/api/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addNewEventItem(@RequestBody EventItem item) {
        logger.info("POST request access '/api/event' path with item: {}", item);
        try {
            item.setId(UUID.randomUUID().toString());
            eventRepository.save(item);
            return "Event item created";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event item creation failed");
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @PutMapping(path = "/api/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateEventItem(@RequestBody EventItem item) {
        logger.info("PUT request access '/api/event' path with item {}", item);
        try {
            eventRepository.deleteById(item.getId());
            eventRepository.save(item);
            return "Event item updated";
        } catch (Exception e) {
            logger.error("Update errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event item not found");
        }
    }

    /**
     * HTTP DELETE
     */
    @DeleteMapping("/api/event/{id}")
    public String deleteEventItem(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/event/{}' path.", id);
        try {
            eventRepository.deleteById(id);
            return "Todo item deleted";
        } catch (Exception e) {
            logger.error("Delete errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo item not found");
        }

    }
}
