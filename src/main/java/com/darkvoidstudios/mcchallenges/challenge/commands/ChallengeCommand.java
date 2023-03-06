package com.darkvoidstudios.mcchallenges.challenge.commands;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.ChallengeEnum;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import com.darkvoidstudios.mcchallenges.noarmor.NoArmorChallenge;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("challenge.use")) {
            if (args.length == 0) {
                sender.sendMessage(Messages.commandParameterError);
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!challenge.isChallengeActive()) {
                        challenge.startChallenge();
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (!args[1].isEmpty()) {
                        //User input
                        ChallengeEnum inputChallenge = ChallengeEnum.forNameIgnoreCase(args[1]);
                        if (inputChallenge != null) {
                            // Challenges
                            switch (inputChallenge) {
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
                                        server.broadcast(Component.text(Messages.prefix + "Added challenge §aNo-Pickup"));
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
                                case DAMAGEEFFECTS:
                                    if (!challenge.isDamageEffectsChallengeActive()) {
                                        challenge.setDamageEffectsChallengeActive(true);
                                        server.broadcast(Component.text(Messages.prefix + "Added challenge §aPlayer Damage And Random Effects"));
                                    } else {
                                        sender.sendMessage(Messages.challengeAlreadyAdded);
                                    }
                                    break;
                                case JUMPINGHOTBAR:
                                    if (!challenge.isJumpingHotbarActive()) {
                                        challenge.setJumpingHotbarActive(true);
                                        server.broadcast(Component.text(Messages.prefix + "Added challenge §aJumping Hotbar"));
                                    } else {
                                        sender.sendMessage(Messages.challengeAlreadyAdded);
                                    }
                                    break;
                                case ICEWALK:
                                    if (!challenge.isIceWalkActive()) {
                                        challenge.setIceWalkActive(true);
                                        server.broadcast(Component.text(Messages.prefix + "Added challenge §aIceWalk"));
                                    } else {
                                        sender.sendMessage(Messages.challengeAlreadyAdded);
                                    }
                                    break;
                                case DELAYEDDAMAGE:
                                    if (!challenge.isDelayedDamageActive()) {
                                        challenge.setDelayedDamageActive(true);
                                        server.broadcast(Component.text(Messages.prefix + "Added challenge §aDelayed-Damage"));
                                    } else {
                                        sender.sendMessage(Messages.challengeAlreadyAdded);
                                    }
                                    break;
                                case NOARMOR:
                                    if (!challenge.isNoArmorChallengeActive()) {
                                        challenge.setNoArmorChallengeActive(true);
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                                                NoArmorChallenge.replaceArmor(player, Material.BARRIER);
                                            }
                                        }
                                        server.broadcast(Component.text(Messages.prefix + "Added challenge §aNo-Armor"));
                                    } else {
                                        sender.sendMessage(Messages.challengeAlreadyAdded);
                                    }
                                    break;
                                default:
                                    sender.sendMessage(Messages.prefix + "§cWrong syntax! §7/challenge add §e<challenge>");
                                    break;
                            }
                        } else {
                            sender.sendMessage(Messages.prefix + "§cThe challenge §e" + args[1] + " §cdoesn't exists");
                        }
                    } else {
                        sender.sendMessage(Messages.prefix + "§cYou have to specify a challenge");
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    String allChallenges = "§a"+ChallengeEnum.getListOfAllChallenges().stream()
                            .map( Object::toString )
                            .collect(Collectors.joining("§7,§a "));

                    sender.sendMessage(Messages.prefix+"A list of all challenges §e("+ChallengeEnum.values().length+")§7: "+allChallenges.toLowerCase());
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
