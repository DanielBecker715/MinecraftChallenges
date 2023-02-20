package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.potion.PotionEffectType.*;

public class PlayerDamageRandomEffectListener implements Listener {

    Challenge challenge;

    public PlayerDamageRandomEffectListener(Challenge challenge) {
        this.challenge = challenge;
    }

    int duration;

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(((EntityDamageByEntityEvent)event).getDamager() instanceof Player)) {
            if (event.getEntity() instanceof Player) {
                if (challenge.isChallengeActive() && challenge.isPDAREChallengeActive()) {
                    Player player = (Player) event.getEntity();
                    boolean triggerEvent = true;

                    switch(event.getCause()) {
                        case WITHER:
                        case POISON:
                        case FIRE:
                        case FIRE_TICK:
                            triggerEvent = false;
                            break;
                    }

                    if (triggerEvent) {
                        PotionEffect potionEffect = generatePotionEffect();
                        player.addPotionEffect(potionEffect);
                        player.sendMessage(Messages.prefix + "You got the effect §e" + potionEffect.getType().getName() + "§7 for §e" + duration/20 + " seconds");
                    }
                }
            }
        }
    }

    private PotionEffect generatePotionEffect() {
        int rndPotion = ThreadLocalRandom.current().nextInt(PotionEffectType.values().length);
        PotionEffectType potionEffectType = PotionEffectType.values()[rndPotion];

        if (potionEffectType.equals(HARM)) {
            return generatePotionEffect();
        }

        if (potionEffectType.equals(WITHER) || potionEffectType.equals(POISON)) {
            duration = new Random().nextInt(10 - 5) + 5;
            return new PotionEffect(potionEffectType, duration * 20, 1);
        }

        duration = new Random().nextInt(30 - 10) + 10;
        return new PotionEffect(potionEffectType, duration * 20, 1);
    }
}
