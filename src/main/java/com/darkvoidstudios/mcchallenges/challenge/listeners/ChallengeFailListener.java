package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ChallengeFailListener implements Listener {
    final Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (challenge.isChallengeActive()) {
            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                challenge.setChallengeActive(false);
                challenge.abortChallenge(true);
                challenge.resetAllChallenges();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.showTitle(Title.title(Component.text("§c§lGAME OVER"), Component.text("")));
                    if (player != event.getPlayer()) {
                        player.teleport(event.getPlayer());
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}
