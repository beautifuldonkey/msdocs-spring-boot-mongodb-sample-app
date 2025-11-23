package com.microsoft.azure.appservice.examples.springbootmongodb.controller;

import com.microsoft.azure.appservice.examples.springbootmongodb.model.BdpExportRequest;
import com.microsoft.azure.appservice.examples.springbootmongodb.model.BdpResp;
import com.microsoft.azure.appservice.examples.springbootmongodb.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BdpEmailController {

    private static Logger logger = LoggerFactory.getLogger(BdpEmailController.class);

    @Autowired
    private EmailService emailService;

    public BdpEmailController() {
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(path = "/api/email/exportGames", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BdpResp exportGames(@RequestBody BdpExportRequest item) {
        logger.info("POST request access '/api/email/exportGames' path with item: {}", item);
        BdpResp resp = new BdpResp();
        try {


            emailService.sendEmail(item.getEmail(), "Game export","Your exported games will be available soon.");
//            emailService.sendEmailAttachment(item.getEmail(), "Game export","Exported games attached.", item.getAttachmentData(), "exported_games.txt");

            resp.setStatus("success");
            resp.setMessage("Exported games sent.");
            return resp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Sending exported games failed");
        }
    }

}
