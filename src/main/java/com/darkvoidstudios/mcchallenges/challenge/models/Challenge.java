package com.darkvoidstudios.mcchallenges.challenge.models;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Instant;

@Getter
@Setter
public class Challenge {
    private static final Server server = Bukkit.getServer();


    boolean isChallengeActive = false;
    Instant timerStartTimestamp;
    double maxHealth = 20;

    //RandomItemChallenge
    private boolean isRandomItemChallengeActive = false;
    private boolean is5HeartChallengeActive = false;

    //PDARE -> Player Damage And Random Effect
    private boolean isPDAREChallengeActive = false;

    public void disableAllChallenges() {
        setChallengeActive(false);
        setRandomItemChallengeActive(false);
        set5HeartChallengeActive(false);
        setPDAREChallengeActive(false);
        setMaxHealth(20);
    }

    public void challengeFailed() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setMaxHealth(getMaxHealth());
            player.setHealth(getMaxHealth());
            player.playSound(player.getLocation(), "block.anvil.place", 40f, 0f);
        }
        server.broadcast(Component.text(Messages.challengeFailed));
    }
}
