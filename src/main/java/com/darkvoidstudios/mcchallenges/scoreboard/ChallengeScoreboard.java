package com.darkvoidstudios.mcchallenges.scoreboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeScoreboard {

    static final ChallengeScoreboard challengeScoreboard = new ChallengeScoreboard();

    public static ChallengeScoreboard getInstance() {
        return challengeScoreboard;
    }

    public void updateScoreboard() {

    }

}
