package com.kelompok12.backend.service;

import com.kelompok12.backend.model.Score;
import com.kelompok12.backend.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    private static final int DEFAULT_LEADERBOARD_LIMIT = 10;
    private static final int FINPRO_LEADERBOARD_LIMIT = 5;
    private static final int MAX_GAME_DURATION_SECONDS = 300;

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public int calculateScore(int enemiesAwakened, float completionTimeSeconds) {
        int clampedTime = Math.max(0, Math.min(MAX_GAME_DURATION_SECONDS, Math.round(completionTimeSeconds)));
        int timeBonus = Math.max(0, MAX_GAME_DURATION_SECONDS - clampedTime) * 10;
        return (enemiesAwakened * 100) + timeBonus;
    }

    public Score createScore(Score score) {
        if (score.getPlayerName() == null || score.getPlayerName().isBlank()) {
            score.setPlayerName("Bagas");
        }
        if (score.getCreatedAt() == null) {
            score.setCreatedAt(Instant.now());
        }
        return scoreRepository.save(score);
    }

    public Optional<Score> getScoreById(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public List<Score> getRecentScores() {
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Score> getScoreAboveValue(Integer minValue) {
        return scoreRepository.findPointGreaterThan(minValue);
    }

    public List<Score> getLeaderboard(Integer limit) {
        int resolvedLimit = (limit == null || limit <= 0) ? DEFAULT_LEADERBOARD_LIMIT : limit;
        return scoreRepository.findTopScores(resolvedLimit);
    }

    public List<Score> getTop5Leaderboard() {
        return scoreRepository.findTopScores(FINPRO_LEADERBOARD_LIMIT);
    }

    public void deleteScore(UUID scoreId) {
        Score target = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score dengan ID " + scoreId + " tidak ditemukan"));
        scoreRepository.delete(target);
    }
}