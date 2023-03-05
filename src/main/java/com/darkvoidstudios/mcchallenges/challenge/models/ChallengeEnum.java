package com.darkvoidstudios.mcchallenges.challenge.models;


import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ChallengeEnum {
    RANDOMITEMS,
    NOPICKUP,
    FIVEHEARTS,
    PDARE,
    JumpingHotbar,
    IceWalk;

    public static List<ChallengeEnum> getListOfAllChallenges() {
        return Arrays.asList(ChallengeEnum.values());
    }

    static public ChallengeEnum forNameIgnoreCase(String value) {
        for (ChallengeEnum challenge : ChallengeEnum.values()) {
            if ( challenge.name().equalsIgnoreCase(value) ) return challenge;
        }
        return null;
    }
}