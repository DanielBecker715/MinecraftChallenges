package com.darkvoidstudios.mcchallenges.challenge.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Setter
@Getter
public class ActionbarTimer {

    private static final ActionbarTimer instance = new ActionbarTimer();

    public static ActionbarTimer getInstance() {
        return instance;
    }
    private Challenge challenge = Challenge.getInstance();

    Instant timerStartTimestamp;
    String currentTimer = "§e§l";
    long currentDays;
    int currentHours;
    int currentMinutes;
    int currentSeconds;

    public String getTimer() {
        if (challenge.isChallengeActive()) {
            Instant currentTime = Instant.now();
            Duration diff = Duration.between(timerStartTimestamp, currentTime);

            if (diff.toDays() != 0) {
                currentTimer = currentTimer + diff.toDays() + "d ";
                currentDays = diff.toDays();
            }
            if (diff.toHoursPart() != 0) {
                currentTimer = currentTimer + diff.toHoursPart() + "h ";
                currentHours = diff.toHoursPart();
            }
            if (diff.toMinutesPart() != 0) {
                currentTimer = currentTimer + diff.toMinutesPart() + "m ";
                currentMinutes = diff.toMinutesPart();
            }
            currentTimer = currentTimer + diff.toSecondsPart() + "s ";
            currentSeconds = diff.toSecondsPart();
            return currentTimer;
        }
        return null;
    }

    public void setupTimer() {
        timerStartTimestamp = Instant.now();
    }
}