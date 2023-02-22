package com.darkvoidstudios.mcchallenges.challenge.models;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Getter
@Setter
public class Challenge {
    private static final Server server = Bukkit.getServer();
    private static final Challenge challengeInstance = new Challenge();

    private Challenge(){}

    public static Challenge getInstance(){
        return challengeInstance;
    }

    boolean isChallengeActive = false;
    double maxHealth = 20;

    private boolean isRandomItemChallengeActive = false;
    private boolean isNoPickupChallengeActive = false;
    private boolean is5HeartChallengeActive = false;
    //PDARE = Player Damage And Random Effects
    private boolean isPDAREChallengeActive = false;

    ActionbarTimer actionbarTimer = ActionbarTimer.getInstance();

    /**
     * Disables all challenges
     */
    public void disableAllChallenges() {
        setChallengeActive(false);
        setRandomItemChallengeActive(false);
        set5HeartChallengeActive(false);
        setPDAREChallengeActive(false);
        setMaxHealth(20);
    }

    /**
     * Cancels the challenge
     */
    public void challengeCancel(boolean sendLoseMessage) {
        if (sendLoseMessage) {
            server.broadcast(Component.text("§8----------------------"));
            server.broadcast(Component.text(Messages.challengeCancel));
            server.broadcast(Component.text(Messages.prefix + "§7Timer: §e§l" + actionbarTimer.getCurrentTimer()));
            server.broadcast(Component.text("§8----------------------"));
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setMaxHealth(getMaxHealth());
                player.setHealth(getMaxHealth());
                player.playSound(player.getLocation(), "block.anvil.place", 40f, 0f);
            }
            server.broadcast(Component.text(Messages.challengeCancel));
        }
    }
}
