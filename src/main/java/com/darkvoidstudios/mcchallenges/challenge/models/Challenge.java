package com.darkvoidstudios.mcchallenges.challenge.models;

import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.time.Instant;

@Getter
@Setter
public class Challenge {
    private static final Challenge challengeInstance = new Challenge();

    private Challenge(){}

    public static Challenge getInstance(){
        return challengeInstance;
    }

    private static final Server server = Bukkit.getServer();
    ChallengeTimer challengeTimer = ChallengeTimer.getInstance();


    //Challenge Settings
    Instant challengeStartTimestamp;
    boolean isChallengeActive = false;
    double maxHealth = 20;
    private boolean isRandomItemChallengeActive = false;
    private boolean isNoPickupChallengeActive = false;
    private boolean is5HeartChallengeActive = false;
    //PDARE = Player Damage And Random Effects
    private boolean isPDAREChallengeActive = false;


    /**
     * Resets all challenges. Resets it to the initial state
     */
    public void resetAllChallenges() {
        setChallengeActive(false);
        setRandomItemChallengeActive(false);
        set5HeartChallengeActive(false);
        setPDAREChallengeActive(false);
        challengeStartTimestamp = null;
        setMaxHealth(20);
    }

    public void startChallenge() {
        setChallengeActive(true);
        challengeTimer.run();
        challengeStartTimestamp = Instant.now();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHealth());
            player.setHealth(getMaxHealth());
            player.setSaturation(20);
            player.getInventory().clear();
            player.playSound(player.getLocation(), "entity.wolf.howl", 40f, 1f);
        }
        server.broadcast(Component.text(Messages.challengeStarted));
    }

    /**
     * Cancels the challenge
     */
    public void abortChallenge(boolean sendLoseMessage) {
        if (sendLoseMessage) {
            server.broadcast(Component.text("§8----------------------"));
            server.broadcast(Component.text(Messages.challengeCancel));
            server.broadcast(Component.text(Messages.prefix + "§7Timer: §e§l" + challengeTimer.getTimer()));
            server.broadcast(Component.text("§8----------------------"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), "block.anvil.place", 40f, 0f);
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHealth());
                player.setHealth(getMaxHealth());
                player.playSound(player.getLocation(), "block.anvil.place", 40f, 0f);
            }
            server.broadcast(Component.text(Messages.challengeCancel));
        }
    }

    /**
     * Challenge completed. Players have beaten the challenge and a firework will spawn.
     */
    public void challengeCompleted() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Firework fw = (Firework) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            fwm.setPower(2);
            fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
            for (int i = 0; i < 5; i++) {
                fw.detonate();
            }
        }
        //TODO call stop the timer method
    }
}