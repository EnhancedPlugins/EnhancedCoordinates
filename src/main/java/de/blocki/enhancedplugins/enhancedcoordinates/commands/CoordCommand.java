package de.blocki.enhancedplugins.enhancedcoordinates.commands;

import de.blocki.enhancedplugins.enhancedcoordinates.utils.ConfigManager;
import de.blocki.enhancedplugins.enhancedcoordinates.utils.CoordData;
import de.blocki.enhancedplugins.enhancedcoordinates.utils.Permission;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CoordCommand implements CommandExecutor, TabCompleter {

    public static HashMap<Player, Player> playerTeleportHM = new HashMap<>();
    private final String prefix;
    private CoordData data = null;

    public CoordCommand(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pSender = (Player) sender;

            data = new CoordData(pSender.getLocation());
            data.setRounded(ConfigManager.getBool("round-location"));

            if(pSender.hasPermission(Permission.DEFAULT.toString()) || pSender.hasPermission(Permission.STAR.toString()) || pSender.hasPermission(Permission.PLUGIN_STAR.toString()) || pSender.isOp()){
                if(args.length >= 1){

                    if(args[0].equalsIgnoreCase("broadcast")){
                        broadcastCoords(pSender, args);
                    }else if(args.length >= 2) {

                        if(args[0].equalsIgnoreCase("teleport")){
                            teleportToCoords(pSender, args);
                        }else if(args[0].equalsIgnoreCase("send")){
                            sendCoords(pSender, args);
                        }else {
                            pSender.sendMessage(prefix + "Der Befehl wurde nicht gefunden.");
                        }

                    }else {
                        pSender.sendMessage(prefix + "Der Befehl wurde nicht gefunden.");
                    }

                }else {
                    viewCoordsSelf(pSender, args);
                }
            }else {
                pSender.sendMessage(prefix + "Du hast keine Berechtigungen diesen Befehl auszufürhen.");
                return false;
            }

            return true;
        }
        return false;
    }

    private void viewCoordsSelf(Player pSender, String[] args){
        if(pSender.hasPermission(Permission.SELF.toString()) || pSender.hasPermission(Permission.STAR.toString()) || pSender.hasPermission(Permission.PLUGIN_STAR.toString()) || pSender.isOp()){
            pSender.sendMessage(prefix + "Die Koordinated von dir sind: " + data.getX() + "/" + data.getY() + "/" + data.getZ() + ".");
        }
    }


    private void sendCoords(Player pSender, String[] args){
        if(pSender.hasPermission(Permission.SEND.toString()) || pSender.hasPermission(Permission.STAR.toString()) || pSender.hasPermission(Permission.PLUGIN_STAR.toString()) || pSender.isOp()){
            Player playtosend = Bukkit.getPlayer(args[1]);
            if (playtosend != null) {
                if (!(playtosend == pSender)) {
                    //player is nich null

                    playtosend.sendMessage(prefix + "Die Koordinated des Spielers §a" + pSender.getName() + " §7sind: " + data.getX() + "/" + data.getY() + "/" + data.getZ() + ".");

                    playerTeleportHM.put(pSender, playtosend);

                    TextComponent message = new TextComponent(prefix + "§9Teleportieren");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/coords teleport " + pSender.getName()));
                    playtosend.spigot().sendMessage(message);

                    pSender.sendMessage(prefix + "Deine Koordinaten wurden an den Spieler " + playtosend.getName() + " gesendet!");
                } else {
                    pSender.sendMessage(prefix + "Du kannst dir selbst deine Koordinaten nicht schicken!");
                }
            } else {
                //player is null
                pSender.sendMessage(prefix + "Der Spieler wurde nicht gefunden!");
            }
        }else {
            pSender.sendMessage(prefix + "Du hast keine Berechtigungen diesen Befehl auszufürhen.");
        }
    }

    private void broadcastCoords(Player pSender, String[] args){
        if(pSender.hasPermission(Permission.BROADCAST.toString()) || pSender.hasPermission(Permission.STAR.toString()) || pSender.hasPermission(Permission.PLUGIN_STAR.toString()) || pSender.isOp()){
            Bukkit.broadcastMessage(prefix + "Die Koordinated des Spielers " + pSender.getName() + " sind: " + data.getX() + "/" + data.getY() + "/" + data.getZ() + ".");

            TextComponent message = new TextComponent(prefix + "§9Teleportieren");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/coords teleport " + pSender.getName()));
            pSender.spigot().sendMessage(message);
        }else {
            pSender.sendMessage(prefix + "Du hast keine Berechtigungen diesen Befehl auszufürhen.");
        }
    }

    private void teleportToCoords(Player pSender, String[] args){
        if(pSender.hasPermission(Permission.TELEPORT.toString()) || pSender.hasPermission(Permission.STAR.toString()) || pSender.hasPermission(Permission.PLUGIN_STAR.toString()) || pSender.isOp()) {
            Player playerExact = Bukkit.getPlayerExact(args[1]);

            if (playerExact != null) {
                if (playerTeleportHM.containsKey(playerExact)) {

                    pSender.teleport(playerExact.getLocation());
                    pSender.sendMessage(prefix + "Du wurdest zu " + playerExact.getName() + " teleportiert.");
                    playerTeleportHM.remove(playerExact);
                } else {
                    pSender.sendMessage(prefix + "Du kannst dich nicht zu diesem Spieler teleportieren ohne das er dir eine Anfrage gesendet hat.");
                }
            } else {
                pSender.sendMessage(prefix + "Der Spieler wurde nicht gefunden!");
            }
        }else {
            pSender.sendMessage(prefix + "Du hast keine Berechtigungen diesen Befehl auszufürhen.");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
