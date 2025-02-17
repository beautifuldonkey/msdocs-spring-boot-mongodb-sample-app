package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.microsoft.azure.appservice.examples.springbootmongodb.dao.EventRepository;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.BdpResp;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.EventItem;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.EventParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public BdpResp addNewEventItem(@RequestBody EventItem item) {
        logger.info("POST request access '/api/event' path with item: {}", item);
        BdpResp resp = new BdpResp();
        try {
            if(item.getId() == null) {
                item.setId(UUID.randomUUID().toString());
            } else {
                eventRepository.deleteById(item.getId());
            }
            eventRepository.save(item);
            resp.setStatus("success");
            resp.setMessage("Event item saved");
            return resp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event item save failed");
        }
    }

    /**
     * HTTP POST PARTICIPANT TO EVENT
     */
    @PostMapping(path = "/api/event/participant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp addEventParticipant(@RequestBody EventParticipant item) {
        logger.info("POST request access '/api/event/participant' path with item: {}", item);
        BdpResp resp = new BdpResp();
        try {
            EventItem event = eventRepository.findById(item.getId()).get();
            if(event.getApplicants() != null) {
                event.getApplicants().add(item.getUser());
            } else {
                event.setApplicants(new ArrayList());
                event.getApplicants().add(item.getUser());
            }

            eventRepository.save(event);
            resp.setStatus("success");
            resp.setMessage("Event participant added");
            return resp;
        } catch (Exception e) {
            logger.error("Participant add errors: ", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event participant add failed");
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
    public BdpResp deleteEventItem(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/event/{}' path.", id);
        BdpResp resp = new BdpResp();
        try {
            eventRepository.deleteById(id);
            resp.setStatus("success");
            resp.setMessage("Event deleted");
            return resp;
        } catch (Exception e) {
            logger.error("Delete errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event delete failed");
        }

    }
}
