package com.darkvoidstudios.mcchallenges.challenge.commands;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.ChallengeEnum;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class ChallengeCommand implements CommandExecutor {
    private static final Server server = Bukkit.getServer();

    final Challenge challenge = Challenge.getInstance();
    final ChallengeTimer challengeTimer = ChallengeTimer.getInstance();

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
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(challenge.getMaxHealth());
                            player.setHealth(challenge.getMaxHealth());
                            player.setSaturation(20);
                            player.getInventory().clear();
                            player.playSound(player.getLocation(), "entity.wolf.howl", 40f, 1f);
                        }
                        server.broadcast(Component.text(Messages.challengeStarted));
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    //User input
                    ChallengeEnum inputChallenge = ChallengeEnum.forNameIgnoreCase(args[1]);
                    if (inputChallenge != null) {
                        // Challenges
                        switch(inputChallenge) {
                            case RANDOMITEMS:
                                if (!challenge.isRandomItemChallengeActive()) {
                                    challenge.setRandomItemChallengeActive(true);
                                    server.broadcast(Component.text(Messages.prefix + "Added challenge §aRandom-Items"));
                                } else {
                                    sender.sendMessage(Messages.challengeAlreadyAdded);
                                }
                                break;
                            case NOPICKUP:
                                if (!challenge.isNoPickupChallengeActive()) {
                                    challenge.setNoPickupChallengeActive(true);
                                } else {
                                    sender.sendMessage(Messages.challengeAlreadyAdded);
                                }
                                break;
                            case FIVEHEARTS:
                                if (!challenge.is5HeartChallengeActive()) {
                                    challenge.set5HeartChallengeActive(true);
                                    challenge.setMaxHealth(10);
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(challenge.getMaxHealth());
                                        player.setHealth(challenge.getMaxHealth());
                                    }
                                    server.broadcast(Component.text(Messages.prefix + "Added challenge §a5-Hearts"));
                                } else {
                                    sender.sendMessage(Messages.challengeAlreadyAdded);
                                }
                                break;
                            case PDARE:
                                if (!challenge.isPDAREChallengeActive()) {
                                    challenge.setPDAREChallengeActive(true);
                                    server.broadcast(Component.text(Messages.prefix + "Added challenge §aPlayer Damage And Random Effects"));
                                } else {
                                    sender.sendMessage(Messages.challengeAlreadyAdded);
                                }
                                break;
                            default:
                                sender.sendMessage(Messages.prefix + "§cWrong syntax! §7/challenge add §e<challenge>");
                                break;
                        }
                    } else {
                        sender.sendMessage(Messages.prefix + "§cThe challenge §e"+args[0]+" §cdoesn't exists");
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    String allChallenges = ChallengeEnum.getListOfAllChallenges().stream()
                            .map( Object::toString )
                            .collect(Collectors.joining(","));

                    sender.sendMessage(Messages.prefix+"A list of all challenges:");
                    sender.sendMessage(allChallenges.toLowerCase());
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (challenge.isChallengeActive()) {
                        challenge.resetAllChallenges();
                        challenge.abortChallenge(false);
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
