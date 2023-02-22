package com.darkvoidstudios.mcchallenges;

import com.darkvoidstudios.mcchallenges.challenge.commands.ChallengeCommand;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeFailListener;
import com.darkvoidstudios.mcchallenges.pdare.listeners.PDAREListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeListener;
import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.ActionbarTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    Challenge challenge = Challenge.getInstance();
    ActionbarTimer actionbarTimer = ActionbarTimer.getInstance();

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();
    }

    private void commandRegistration() {
        this.getCommand("challenge").setExecutor(new ChallengeCommand());
    }

    private void listenerRegistration() {
        getServer().getPluginManager().registerEvents(new ChallengeListener(), this);
        getServer().getPluginManager().registerEvents(new ChallengeFailListener(), this);
        getServer().getPluginManager().registerEvents(new PDAREListener(), this);
    }
}
