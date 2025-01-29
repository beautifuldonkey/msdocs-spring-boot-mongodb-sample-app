package com.microsoft.azure.appservice.examples.springbootmongodb.dao;

import com.microsoft.azure.appservice.examples.springbootmongodb.model.EventItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<EventItem, String> {
}
