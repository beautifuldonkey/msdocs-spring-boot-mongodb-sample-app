package com.microsoft.azure.appservice.examples.springbootmongodb.dao;

import com.microsoft.azure.appservice.examples.springbootmongodb.model.GameLogsEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLogsRepository extends MongoRepository<GameLogsEntry, String> {
}
