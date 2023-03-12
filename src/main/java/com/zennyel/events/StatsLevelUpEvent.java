package com.zennyel.events;

import com.zennyel.player.Character;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatsLevelUpEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Character character;
    private Player player;
    private boolean cancelled;
    public StatsLevelUpEvent(Character character, Player player) {
        this.character = character;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
