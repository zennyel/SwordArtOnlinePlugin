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
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por jogadores.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUso correto: /" + label + " <jogador> <quantidade>");
            return true;
        }

        String playerName = args[0];
        Player target = Bukkit.getPlayer(playerName);


        if (target == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        Character player = SAO.getPlugin(SAO.class).getPlayer(target.getUniqueId());
        int quantity = Integer.parseInt(args[1]);

        if(quantity < 1000){
            sender.sendMessage("§aO número máximo de niveís peritido é 1000");
        }

                player.setLevel(player.getLevel() + quantity);
                player.setPoints(player.getPoints() + quantity);
                sender.sendMessage("§aVocê adicionou " + quantity + " levels para o jogador " + playerName);
        return true;
    }
}
