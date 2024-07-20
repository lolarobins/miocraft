package sh.lola.miocraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PingHUD {

    Scoreboard sb;
    Objective ping;

    PingHUD() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
		ping = sb.registerNewObjective("ping", Criteria.DUMMY, "ping", RenderType.INTEGER);
		ping.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(TheGays.globalHomo, new Runnable() {

            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
			        Score pingScore = ping.getScore(p.getName());
			        pingScore.setScore(p.getPing());
			        p.setScoreboard(sb);
                }
            }
        }, 0, 50);
    }
}
