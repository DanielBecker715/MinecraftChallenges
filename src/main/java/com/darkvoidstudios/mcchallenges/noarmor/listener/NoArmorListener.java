package com.darkvoidstudios.mcchallenges.noarmor.listener;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import com.darkvoidstudios.mcchallenges.noarmor.NoArmorChallenge;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoArmorListener implements Listener {

    Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (challenge.isChallengeActive() && challenge.isNoArmorChallengeActive()) {
            Player player = event.getPlayer();
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                NoArmorChallenge.replaceArmor(player, Material.BARRIER);
            }
        }
    }

    @EventHandler
    public void onArmorChange(InventoryClickEvent event) {
        if (challenge.isChallengeActive() && challenge.isNoArmorChallengeActive()) {
            Player player = (Player) event.getWhoClicked();
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                    event.setCancelled(true);
                    player.sendMessage(Messages.prefix+"§cThe current challenge prohibits the use of armor");
                    player.playSound(player.getLocation(), "entity.enderman.teleport", 40f, 1f);
                }
            }
        }
    }
}
