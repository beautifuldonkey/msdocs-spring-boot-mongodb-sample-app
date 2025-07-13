package com.microsoft.azure.appservice.examples.springbootmongodb.dao;

import com.microsoft.azure.appservice.examples.springbootmongodb.model.BdpMeUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EoUserRepository extends MongoRepository<BdpMeUser, String> {
    BdpMeUser findByEmail(String email);
}
