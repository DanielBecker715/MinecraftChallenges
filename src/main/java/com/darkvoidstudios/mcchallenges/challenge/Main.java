package com.darkvoidstudios.mcchallenges.challenge;

import com.darkvoidstudios.mcchallenges.challenge.listeners.BlockEventListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.DeathListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.PlayerDamageRandomEffectListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.PlayerEventListener;
import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ActionbarTimer;
import com.darkvoidstudios.mcchallenges.randomitem.schedulers.RandomItemTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    Challenge challenge = new Challenge();

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();
        schedulerRegistration();

    }

    private void commandRegistration() {
        this.getCommand("challenge").setExecutor(new ChallengeCommand(challenge));
    }

    private void listenerRegistration() {
        getServer().getPluginManager().registerEvents(new DeathListener(challenge), this);
        getServer().getPluginManager().registerEvents(new BlockEventListener(challenge), this);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(challenge), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageRandomEffectListener(challenge), this);
    }

    private void schedulerRegistration() {
        new ActionbarTimer(this, challenge).run();
        new RandomItemTimer(this, challenge).run();
    }
}
