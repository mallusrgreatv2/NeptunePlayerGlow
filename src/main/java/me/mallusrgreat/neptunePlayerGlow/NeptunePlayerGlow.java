package me.mallusrgreat.neptunePlayerGlow;

import dev.lrxh.api.events.MatchEndEvent;
import dev.lrxh.api.events.MatchStartEvent;
import dev.lrxh.api.match.IFffaFightMatch;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public final class NeptunePlayerGlow extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled NeptunePlayerGlow");
    }
    @EventHandler
    public void onMatchStart(MatchStartEvent e) {
        var match = e.getMatch();
        var scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team red = scoreboard.registerNewTeam("Red");
        red.color(NamedTextColor.RED);
        Team blue = scoreboard.registerNewTeam("Blue");
        blue.color(NamedTextColor.BLUE);
        if (match instanceof IFffaFightMatch) return;
        match.getParticipants().forEach(participant -> {
            Player player = participant.getProfile().getPlayer();
            if (participant.getColor().getContentColor().equals(Color.RED)) red.addPlayer(player);
            else blue.addPlayer(player);
            player.setGlowing(true);
        });
    }
    @EventHandler
    public void onMatchEnd(MatchEndEvent e) {
        e.getMatch().getParticipants().forEach(participant ->
            participant.getProfile().getPlayer().setGlowing(false)
        );
    }
}
