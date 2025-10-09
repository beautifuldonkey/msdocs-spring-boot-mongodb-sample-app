package com.microsoft.azure.appservice.examples.springbootmongodb.model;

import org.springframework.data.annotation.Id;

public class GameLogsEntry {

    @Id
    private String id;
    private Object game;
    private Object player;
    private Object stats;
    private Object team;

    public GameLogsEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getGame() {
        return game;
    }

    public void setGame(Object game) {
        this.game = game;
    }

    public Object getPlayer() {
        return player;
    }

    public void setPlayer(Object player) {
        this.player = player;
    }

    public Object getStats() {
        return stats;
    }

    public void setStats(Object stats) {
        this.stats = stats;
    }

    public Object getTeam() {
        return team;
    }

    public void setTeam(Object team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "GameLogsEntry{" +
                "game=" + game +
                ", player=" + player +
                ", stats=" + stats +
                ", team=" + team +
                '}';
    }
}
