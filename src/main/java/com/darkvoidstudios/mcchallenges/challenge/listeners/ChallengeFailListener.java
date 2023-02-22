package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ChallengeFailListener implements Listener {
    Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (challenge.isChallengeActive()) {
            challenge.setChallengeActive(false);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != event.getPlayer()) {
                    player.setGameMode(GameMode.SPECTATOR);
                    if (event.getPlayer() != player) {
                        player.teleport(event.getPlayer());
                    }
                }
            }
            challenge.challengeCancel(true);
        }
    }
}
