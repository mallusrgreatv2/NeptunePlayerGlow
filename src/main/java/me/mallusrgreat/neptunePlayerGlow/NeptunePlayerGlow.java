package me.mallusrgreat.neptunePlayerGlow;

import dev.lrxh.api.events.MatchEndEvent;
import dev.lrxh.api.events.MatchStartEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class NeptunePlayerGlow extends JavaPlugin implements Listener {
    private Team red, blue;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        red = scoreboard.getTeam("Red") != null ? scoreboard.getTeam("Red") : scoreboard.registerNewTeam("Red");
        blue = scoreboard.getTeam("Blue") != null ? scoreboard.getTeam("Blue") : scoreboard.registerNewTeam("Blue");

        red.color(NamedTextColor.RED);
        blue.color(NamedTextColor.BLUE);
    }

    @EventHandler
    public void onMatchStart(MatchStartEvent e) {
        e.getMatch().getParticipants().forEach(participant -> {
            Player player = participant.getProfile().getPlayer();
            boolean isRed = participant.getColor().getContentColor().equals(Color.RED);

            (isRed ? red : blue).addPlayer(player);
            player.setGlowing(true);
        });
    }

    @EventHandler
    public void onMatchEnd(MatchEndEvent e) {
        e.getMatch().getParticipants().forEach(participant -> {
            Player player = Bukkit.getPlayer(participant.getPlayerUUID());
            if (player == null) return;
            player.setGlowing(false);
            red.removePlayer(player);
            blue.removePlayer(player);
        });
    }
}