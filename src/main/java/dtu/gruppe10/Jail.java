package dtu.gruppe10;


import java.util.HashMap;

public class Jail {
    protected HashMap<Player, Integer> inmates;
    protected int maxTurnsInPrison;

    public Jail(int maxTurnsInPrison) {
        this.maxTurnsInPrison = maxTurnsInPrison;
        this.inmates = new HashMap<>();
    }

    public void addPlayer(Player player) {
        inmates.put(player, 0);
    }

    public void releasePlayer(Player inmate) {
        inmates.remove(inmate);
    }

    public boolean playerIsJailed(Player player) {
        return inmates.containsKey(player);
    }

    public int turnsServed(Player player) {
        if (!playerIsJailed(player)) {
            return 0;
        }
        return inmates.get(player);
    }

    public boolean playerHasToGetOut(Player player) {
        if (!playerIsJailed(player)) {
            return false;
        }
        return turnsServed(player) >= maxTurnsInPrison;
    }

    public void playerServedTurn(Player player) {
        if (playerIsJailed(player)) {
            int turnsServed = turnsServed(player);
            turnsServed++;
            inmates.put(player, turnsServed);
        }
    }
}
