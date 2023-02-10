package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    Challenge challenge;
    private static final Server server = Bukkit.getServer();

    public DeathListener(Challenge challenge) {
        this.challenge = challenge;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (challenge.isChallengeActive()) {
            challenge.setChallengeActive(false);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != event.getPlayer()) {
                    player.setHealth(0);
                }
            }
            server.broadcast(Component.text("§8----------------------"));
            server.broadcast(Component.text(Messages.challengeFailed));
            server.broadcast(Component.text(Messages.prefix + "§7Player §e" + event.getPlayer().getName() + " §cdied"));
            server.broadcast(Component.text(Messages.prefix + "§7Timer: §e§l" + "Coming Soon"));
            server.broadcast(Component.text("§8----------------------"));
        }
    }
}
