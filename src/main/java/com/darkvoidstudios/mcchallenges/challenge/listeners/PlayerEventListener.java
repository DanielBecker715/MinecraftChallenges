package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {

    Challenge challenge;

    public PlayerEventListener(Challenge challenge) {
        this.challenge = challenge;
    }

    @EventHandler
    public void onPlayerInteract(PlayerJoinEvent event) {
        event.getPlayer().setMaxHealth((challenge.getMaxHealth()));
        event.getPlayer().setHealth(challenge.getMaxHealth());
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!challenge.isChallengeActive()) {
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!challenge.isChallengeActive()) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!challenge.isChallengeActive()) {
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(EntityDamageEvent event) {
        if (!challenge.isChallengeActive()) {
            event.setCancelled(true);
        }
    }
}
