package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.appservice.examples.springbootmongodb.dao.EoUserRepository;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.*;
import com.microsoft.azure.appservice.examples.springbootmongodb.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
public class EoUserController {

    private static Logger logger = LoggerFactory.getLogger(EoUserController.class);

    @Autowired
    private EoUserRepository eoUserRepository;

    @Autowired
    private EmailService emailService;

    public EoUserController() {
    }

    /**
     * HTTP GET
     */
    @GetMapping(path = "/api/BdpMeUser/{index}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public BdpResp getEoUser(@PathVariable("index") String index) {
        logger.info("GET request access '/api/BdpMeUser/{}' path.", index);
        BdpResp resp = new BdpResp();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            BdpMeUser user = eoUserRepository.findById(index).get();
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
    @GetMapping(path = "/api/BdpMeUser/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BdpMeUser> getAllEoUsers() {
        logger.info("GET request access '/api/BdpMeUser/list' path.");
        return eoUserRepository.findAll();
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(path = "/api/BdpMeUser/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp addNewEoUser(@RequestBody BdpMeUser item) {
        logger.info("POST request access '/api/BdpMeUser/register' path with item: {}", item);
        BdpResp resp = new BdpResp();
        try {

            // Search for existing BdpMeUser records by email to ensure no duplicates
            List<BdpMeUser> existingUsers = eoUserRepository.findAll();
            for (BdpMeUser user : existingUsers) {
                if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(item.getEmail()) && !user.getId().equals(item.getId())) {
                    resp.setStatus("failure");
                    resp.setMessage("User not saved, email already registered to another user.");
                    return resp;
                }
            }
            if(item.getId() == null) {
                item.setId(UUID.randomUUID().toString());
            } else {
                eoUserRepository.deleteById(item.getId());
            }
            BdpMeUser createdItem = eoUserRepository.save(item);
            LocalDateTime expirationTime = sendUserAuth(createdItem);
            resp.setData(createdItem.getId());
            resp.setStatus("success");
            resp.setMessage("New user registered. Authentication code sent to your email.");
            return resp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "EO user save failed");
        }
    }

    private LocalDateTime sendUserAuth(BdpMeUser user) {
        // Generate 6-digit auth code
        int authCode = new Random().nextInt(900000) + 100000;

        // Set auth code expiration time (10 minutes from now)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        // Send auth code to user email
        String emailBody = String.format("Hello %s,\n\nYour authentication code is: %d.\nThis code will expire at: %s.",
                user.getUsername(), authCode, expirationTime);
        user.setAuthCode(String.valueOf(authCode));
        user.setAuthCodeExpires(expirationTime.toString());
        eoUserRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Your Authentication Code", emailBody);
        return expirationTime;
    }

    @PostMapping(path = "/api/BdpMeUser/loginRequest", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp loginRequest(@RequestBody BdpMeUser userReq) {
        logger.info("POST request access '/api/BdpMeUser/loginRequest' path with item: {}", userReq);
        BdpResp resp = new BdpResp();

        try {
            BdpMeUser user = eoUserRepository.findByEmail(userReq.getEmail());

            if (user == null) {
                resp.setStatus("error");
                resp.setMessage("User not found");
                return resp;
            }

            LocalDateTime expirationTime = sendUserAuth(user);

            // Send success response with auth code expiration time
            resp.setStatus("success");
            resp.setMessage("Authentication code sent to your email");
            resp.setData(expirationTime.toString());
            return resp;
        } catch (Exception e) {
            logger.error("Login errors: ", e);
            resp.setStatus("error");
            resp.setMessage("Login failed");
        }
        return resp;
    }

    @PostMapping(path = "/api/BdpMeUser/loginUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp loginUser(@RequestBody BdpMeUser item) {
        logger.info("POST request access '/api/BdpMeUser/login' path with item: {}", item);
        BdpResp resp = new BdpResp();

        try {
            BdpMeUser user = eoUserRepository.findByEmail(item.getEmail());

            if (user == null || !user.getAuthCode().equals(item.getAuthCode())) {
                resp.setStatus("error");
                resp.setMessage("Invalid email or authentication code");
                return resp;
            }

            // Check if auth code is expired
            LocalDateTime expirationTime = LocalDateTime.parse(user.getAuthCodeExpires());
            if (LocalDateTime.now().isAfter(expirationTime)) {
                resp.setStatus("error");
                resp.setMessage("Authentication code has expired");
                return resp;
            }

            // Successful login
            ObjectMapper objectMapper = new ObjectMapper();
            resp.setData(objectMapper.writeValueAsString(user));
            resp.setStatus("success");
            resp.setMessage("Login successful");
            return resp;

        } catch (Exception e) {
            logger.error("Login errors: ", e);
            resp.setStatus("error");
            resp.setMessage("Login failed");
        }
        return resp;
    }

    /**
     * HTTP PUT UPDATE
     */
    @PutMapping(path = "/api/BdpMeUser/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp updateEoUser(@RequestBody BdpMeUser item) {
        logger.info("PUT request access '/api/BdpMeUser/update' path with item {}", item);
        BdpResp resp = new BdpResp();
        try {
            eoUserRepository.deleteById(item.getId());
            eoUserRepository.save(item);
            resp.setStatus("success");
            resp.setMessage("EO user updated");
            return resp;
        } catch (Exception e) {
            logger.error("Update errors: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EO user not found");
        }
    }

    /**
     * HTTP DELETE
     */
    @DeleteMapping("/api/BdpMeUser/remove/{id}")
    public BdpResp deleteEoUser(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/BdpMeUser/remove/{}' path.", id);
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
