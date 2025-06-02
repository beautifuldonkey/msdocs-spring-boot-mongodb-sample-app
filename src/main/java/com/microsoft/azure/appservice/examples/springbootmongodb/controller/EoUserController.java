package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.appservice.examples.springbootmongodb.dao.EoUserRepository;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.*;
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
public class EoUserController {

    private static Logger logger = LoggerFactory.getLogger(EoUserController.class);

    @Autowired
    private EoUserRepository eoUserRepository;

    public EoUserController() {
    }

    /**
     * HTTP GET
     */
    @GetMapping(path = "/api/eouser/{index}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public BdpResp getEoUser(@PathVariable("index") String index) {
        logger.info("GET request access '/api/eouser/{}' path.", index);
        BdpResp resp = new BdpResp();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            EoUser user = eoUserRepository.findById(index).get();
            resp.setData(objectMapper.writeValueAsString(user));
            resp.setStatus("success");
            resp.setMessage("EO user saved");
        } catch (Exception e) {
            logger.error("Get EO user errors: ", e);
            resp.setStatus("error");
            resp.setMessage("EO user not found");
        }
        return resp;
    }

    /**
     * HTTP GET ALL
     */
    @GetMapping(path = "/api/eouser", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EoUser> getAllEoUsers() {
        logger.info("GET request access '/api/eouser' path.");
        return eoUserRepository.findAll();
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(path = "/api/eouser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp addNewEoUser(@RequestBody EoUser item) {
        logger.info("POST request access '/api/eouser' path with item: {}", item);
        BdpResp resp = new BdpResp();
        try {
            if(item.getId() == null) {
                item.setId(UUID.randomUUID().toString());
            } else {
                eoUserRepository.deleteById(item.getId());
            }
            EoUser createdItem = eoUserRepository.save(item);
            resp.setData(createdItem.getId());
            resp.setStatus("success");
            resp.setMessage("EO user saved");
            return resp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EO user save failed");
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @PutMapping(path = "/api/eouser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateEoUser(@RequestBody EoUser item) {
        logger.info("PUT request access '/api/eouser' path with item {}", item);
        try {
            eoUserRepository.deleteById(item.getId());
            eoUserRepository.save(item);
            return "EO user updated";
        } catch (Exception e) {
            logger.error("Update errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EO user not found");
        }
    }

    /**
     * HTTP DELETE
     */
    @DeleteMapping("/api/eouser/{id}")
    public BdpResp deleteEoUser(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/eouser/{}' path.", id);
        BdpResp resp = new BdpResp();
        try {
            eoUserRepository.deleteById(id);
            resp.setStatus("success");
            resp.setMessage("EO user deleted");
            return resp;
        } catch (Exception e) {
            logger.error("Delete errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EO user delete failed");
        }
    }
}
