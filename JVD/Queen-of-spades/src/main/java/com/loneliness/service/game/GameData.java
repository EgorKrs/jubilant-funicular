package com.loneliness.service.game;

import java.math.BigDecimal;

public class GameData {
    private int gamerId;
    private BigDecimal jackpot;
    private boolean gamerWon;

    public GameData(int gamerId, BigDecimal jackpot) {
        this.gamerId = gamerId;
        this.jackpot = jackpot;
    }

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
    }

    public BigDecimal getJackpot() {
        return jackpot;
    }

    public void setJackpot(BigDecimal jackpot) {
        this.jackpot = jackpot;
    }

    public boolean isGamerWon() {
        return gamerWon;
    }

    public void setGamerWon(boolean gamerWon) {
        this.gamerWon = gamerWon;
    }
}
