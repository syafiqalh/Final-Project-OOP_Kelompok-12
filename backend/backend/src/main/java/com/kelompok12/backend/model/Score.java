package com.kelompok12.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private Integer point;

    @Column
    private Integer enemiesAwakened;

    @Column
    private Float completionTimeSeconds;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Score() {
    }

    public Score(String playerName, Integer point) {
        this.playerName = playerName;
        this.point = point;
    }

    public Score(String playerName, Integer point, Integer enemiesAwakened, Float completionTimeSeconds) {
        this.playerName = playerName;
        this.point = point;
        this.enemiesAwakened = enemiesAwakened;
        this.completionTimeSeconds = completionTimeSeconds;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
        if (this.point == null) {
            this.point = 0;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getEnemiesAwakened() {
        return enemiesAwakened;
    }

    public void setEnemiesAwakened(Integer enemiesAwakened) {
        this.enemiesAwakened = enemiesAwakened;
    }

    public Float getCompletionTimeSeconds() {
        return completionTimeSeconds;
    }

    public void setCompletionTimeSeconds(Float completionTimeSeconds) {
        this.completionTimeSeconds = completionTimeSeconds;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}