package com.darkvoidstudios.mcchallenges.challenge.models;

import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
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
    private boolean isDamageEffectsChallengeActive = false;
    private boolean isJumpingHotbarActive = false;
    private boolean isIceWalkActive = false;
    private boolean isDelayedDamageActive = false;
    private boolean isNoArmorChallengeActive = false;


    /**
     * Resets all challenges. Resets it to the initial state
     */
    public void resetAllChallenges() {
        setChallengeActive(false);
        setRandomItemChallengeActive(false);
        setNoPickupChallengeActive(false);
        set5HeartChallengeActive(false);
        setDamageEffectsChallengeActive(false);
        setJumpingHotbarActive(false);
        setIceWalkActive(false);
        setDelayedDamageActive(false);
        setNoArmorChallengeActive(false);
        challengeStartTimestamp = null;
        setMaxHealth(20);
    }

    public void startChallenge() {
        setChallengeActive(true);
        setChallengeStartTimestamp(Instant.now());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHealth());
            player.setHealth(getMaxHealth());
            player.setSaturation(20L);

            player.getInventory().clear();
            player.playSound(player.getLocation(), "entity.wolf.howl", 40f, 1f);
            if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
                player.setAllowFlight(false);
            }
        }
        server.broadcast(Component.text(Messages.challengeStarted));
    }

    /**
     * Cancels the challenge
     */
    public void abortChallenge(boolean sendLoseMessage) {
        if (sendLoseMessage) {
            server.broadcast(Component.text(Messages.challengeCancel));
            server.broadcast(Component.text(Messages.prefix + "§7Timer: §e§l" + challengeTimer.getTimer()));
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
            player.playSound(player.getLocation(), "ui.toast.challenge_complete", 40f, 1f);

            for (int i = 0; i < 5; i++) {
                fw.detonate();
            }
        }
        //TODO call stop the timer method
    }
}