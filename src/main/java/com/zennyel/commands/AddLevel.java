package com.zennyel.commands;

import com.zennyel.SAO;
import com.zennyel.player.Character;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddLevel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUso correto: /" + label + " <jogador> <quantidade> (addlevel/addpoints)");
            return true;
        }

        String playerName = args[0];
        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        if (!(target instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por jogadores.");
            return true;
        }

        Character player = SAO.getPlugin(SAO.class).getPlayer(target.getUniqueId());

        String operation = args[2];
        int quantity = Integer.parseInt(args[1]);

        switch (operation.toLowerCase()) {
            case "addlevel":
                player.setLevel(player.getLevel() + quantity);
                player.setPoints(player.getPoints() + quantity);
                sender.sendMessage("§aVocê adicionou " + quantity + " levels para o jogador " + playerName);
                break;
            case "addpoints":
                player.setPoints(player.getPoints() + quantity);
                sender.sendMessage("§aVocê adicionou " + quantity + " pontos para o jogador " + playerName);
                break;
            default:
                sender.sendMessage("§cUso correto: /" + label + " <jogador> <quantidade> (addlevel/addpoints)");
                break;
        }

        return true;
    }
}
