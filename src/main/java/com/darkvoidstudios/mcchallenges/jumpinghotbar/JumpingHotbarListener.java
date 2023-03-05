package com.darkvoidstudios.mcchallenges.jumpinghotbar;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.randomitem.RandomItem;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class JumpingHotbarListener implements Listener {

    Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if (challenge.isChallengeActive() && challenge.isJumpingHotbarActive()) {
            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                Player player = event.getPlayer();
                player.getInventory().clear();
                player.playSound(player.getLocation(), "ui.button.click", 1f, 2f);

                List<ItemStack> randomItemList = RandomItem.generateRandomItemStacks(9);

                //Sets random items to the hotbar
                for (int i=0; i<randomItemList.size(); i++) {
                    event.getPlayer().getInventory().setItem(i, randomItemList.get(i));
                }

                //Replace the slots from 9-35 with barrier blocks
                for (int i=9; i<=35; i++) {
                    ItemStack itemStack = new ItemStack(Material.BARRIER);

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("§c§lNOT AVAILABLE"));
                    itemStack.setItemMeta(itemMeta);

                    event.getPlayer().getInventory().setItem(i, itemStack);
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (challenge.isChallengeActive() && challenge.isJumpingHotbarActive()) {
            if (!event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                int latestHotbarSlot = 8;
                if (event.getSlot() > latestHotbarSlot) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getInventory().getHolder() instanceof Player)) {
            if (challenge.isChallengeActive() && challenge.isJumpingHotbarActive()) {
                event.setCancelled(true);
            }
        }
    }
}
