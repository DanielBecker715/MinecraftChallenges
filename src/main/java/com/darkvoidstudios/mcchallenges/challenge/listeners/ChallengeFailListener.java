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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChallengeFailListener implements Listener {
    final Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (challenge.isChallengeActive()) {
            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                challenge.setChallengeActive(false);
                challenge.abortChallenge(true);
                challenge.resetAllChallenges();
                dropAllPlayerItems(event.getPlayer());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.getInventory().clear();
                    player.showTitle(Title.title(Component.text("§c§lGAME OVER"), Component.text("")));
                    if (player != event.getPlayer()) {
                        player.teleport(event.getPlayer());
                    }
                }
                event.setCancelled(true);
            }
        }
    }

    public void dropAllPlayerItems(Player player) {
        List<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null) {
                items.add(player.getInventory().getItem(i));
            }
        }
        player.getInventory().clear();

        for(ItemStack item : items) {
            player.getWorld().dropItem(player.getLocation(), item).setPickupDelay(20);
        }
    }
}
