package com.darkvoidstudios.mcchallenges.icewalk.listener;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class IceWalkListener implements Listener {

    Challenge challenge = Challenge.getInstance();

    boolean isIceActive = false;

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {

        if (challenge.isChallengeActive() && challenge.isIceWalkActive()) {
            Player player = event.getPlayer();
            if (isIceActive && !player.getGameMode().equals(GameMode.CREATIVE)) {
                Location blockUnderPlayer = new Location(player.getWorld(), player.getLocation().x(), player.getLocation().y() - 1, player.getLocation().z());

                int radius = 1;
                for (int i=-radius; i <= radius; i++) {
                    for (int k = -radius; k <= radius; k++) {
                        if (blockUnderPlayer.getBlock().getRelative(i, 0, k).getType() == Material.AIR) {
                            setFloor(blockUnderPlayer, radius, Material.PACKED_ICE);
                        };
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && !player.getGameMode().equals(GameMode.CREATIVE)) {
            if (challenge.isChallengeActive() && challenge.isIceWalkActive()) {
                if (isIceActive) {
                    isIceActive = false;
                    player.sendMessage(Messages.prefix + "IceWalk is now §coff");
                } else {
                    isIceActive = true;
                    player.sendMessage(Messages.prefix + "IceWalk is now §aon");
                }
            }
        }
    }

    public void setFloor(Location location, int radius, Material material) {
        for (int i=-radius; i <= radius; i++) {
            for (int k =-radius; k <= radius; k++) {
                Block theBlock = location.getBlock().getRelative(i, 0, k);
                if (location.getBlock().getRelative(i, 0, k).getType() == Material.AIR) {
                    theBlock.setType(material);
                } else {
                    switch (theBlock.getType()) {
                        case BROWN_MUSHROOM:
                        case RED_MUSHROOM:
                        case CRIMSON_FUNGUS:
                        case WARPED_FUNGUS:
                        case GRASS:
                        case VINE:
                        case FERN:
                        case DANDELION:
                        case POPPY:
                        case BLUE_ORCHID:
                        case AZURE_BLUET:
                        case RED_TULIP:
                        case ORANGE_TULIP:
                        case WHITE_TULIP:
                        case PINK_TULIP:
                        case OXEYE_DAISY:
                        case CORNFLOWER:
                        case LILY_OF_THE_VALLEY:
                        case SPORE_BLOSSOM:
                        case WITHER_ROSE:
                        case CRIMSON_ROOTS:
                        case WARPED_ROOTS:
                        case NETHER_SPROUTS:
                        case WEEPING_VINES:
                        case TWISTING_VINES:
                        case TALL_GRASS:
                        case GLOW_LICHEN:
                        case HANGING_ROOTS:
                        case FROGSPAWN:
                        case SNOW:
                            theBlock.setType(material);
                    }
                }
            }
        }
    }
}
