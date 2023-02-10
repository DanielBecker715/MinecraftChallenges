package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventListener implements Listener {

    Challenge challenge;

    public BlockEventListener(Challenge challenge) {
        this.challenge = challenge;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!challenge.isChallengeActive()) {
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!challenge.isChallengeActive()) {
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

}
