// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.appservice.examples.springbootmongodb.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Objects;

public class EventItem {

    @Id
    private String id;
    private String name;
    private String owner;
    private String description;
    private String ptsLimit;
    private String restrictions;
    private String playerLimit;
    private String gameCount;
    private ArrayList applicants;
    private ArrayList games;
    private String startDate;
    private String endDate;

    public EventItem() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getGameCount() {
        return gameCount;
    }

    public void setGameCount(String gameCount) {
        this.gameCount = gameCount;
    }

    public String getPlayerLimit() {
        return playerLimit;
    }

    public void setPlayerLimit(String playerLimit) {
        this.playerLimit = playerLimit;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getPtsLimit() {
        return ptsLimit;
    }

    public void setPtsLimit(String ptsLimit) {
        this.ptsLimit = ptsLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getApplicants() {
        return applicants;
    }

    public void setApplicants(ArrayList applicants) {
        this.applicants = applicants;
    }

    public ArrayList getGames() {
        return games;
    }

    public void setGames(ArrayList games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

