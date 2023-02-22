package com.darkvoidstudios.mcchallenges.challenge.commands;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import com.darkvoidstudios.mcchallenges.challenge.models.ActionbarTimer;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import com.darkvoidstudios.mcchallenges.randomitem.RandomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChallengeCommand implements CommandExecutor {
    private static final Server server = Bukkit.getServer();

    Challenge challenge = Challenge.getInstance();
    ChallengeTimer challengeTimer = ChallengeTimer.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("challenge.use")) {
            if (args.length == 0) {
                sender.sendMessage(Messages.commandParameterError);
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!challenge.isChallengeActive()) {
                        challenge.setChallengeActive(true);
                        challengeTimer.run();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.setMaxHealth(challenge.getMaxHealth());
                            player.setHealth(challenge.getMaxHealth());
                            player.setSaturation(20);
                            player.getInventory().clear();
                            player.playSound(player.getLocation(), "entity.wolf.howl", 40f, 1f);
                        }
                        server.broadcast(Component.text(Messages.challengeStarted));
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    // Challenges
                    switch(args[1].toLowerCase()) {
                        case "randomitems":
                            if (!challenge.isRandomItemChallengeActive()) {
                                challenge.setRandomItemChallengeActive(true);
                                server.broadcast(Component.text(Messages.prefix + "Added challenge §aRandom-Items"));
                                break;
                            } else {
                                sender.sendMessage(Messages.challengeAlreadyAdded);
                                break;
                            }
                        case "nopickup":
                            if (!challenge.isNoPickupChallengeActive()) {
                                challenge.setNoPickupChallengeActive(true);
                            } else {
                                sender.sendMessage(Messages.challengeAlreadyAdded);
                                break;
                            }
                        case "5hearts":
                            if (!challenge.is5HeartChallengeActive()) {
                                challenge.set5HeartChallengeActive(true);
                                challenge.setMaxHealth(10);
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.setMaxHealth(challenge.getMaxHealth());
                                    player.setHealth(challenge.getMaxHealth());
                                }
                                server.broadcast(Component.text(Messages.prefix + "Added challenge §a5-Hearts"));
                                break;
                            } else {
                                sender.sendMessage(Messages.challengeAlreadyAdded);
                                break;
                            }
                        case "pdare":
                            if (!challenge.isPDAREChallengeActive()) {
                                challenge.setPDAREChallengeActive(true);
                                server.broadcast(Component.text(Messages.prefix + "Added challenge §aPlayer Damage And Random Effects"));
                                break;
                            } else {
                                sender.sendMessage(Messages.challengeAlreadyAdded);
                                break;
                            }
                        default:
                            sender.sendMessage(Messages.prefix + "§cWrong syntax! §7/challenge add §e<challenge>");
                            break;

                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (challenge.isChallengeActive()) {
                        challenge.disableAllChallenges();
                        challenge.challengeCancel(false);
                    } else {
                        sender.sendMessage(Messages.noChallengeRunning);
                    }
                } else {
                    sender.sendMessage(Messages.commandParameterError);
                }
            }
        } else {
            sender.sendMessage(Messages.invalidPermissions);
        }
        return true;
    }
}
