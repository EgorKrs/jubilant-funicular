package com.loneliness.service.game;

import com.loneliness.entity.Card;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Game {
    private final Random random;
    private final int gameID;
    private Map<Integer,Card> shtose;
    private BigDecimal jackpot;
    private Card mainCard;
    private Card forehead;
    private Card sonic;

    public Game(int gameID, int decksOfCardsID, Command<Integer,Map<Integer, Card>,Integer> command) throws ServiceException {
        this.gameID=gameID;
        random=new Random();
        shtose=command.execute(decksOfCardsID);
    }

    public Card setMainCard(){
        mainCard=getOneRandomCard();
        return mainCard;
    }

    public Card setForehead(){
        forehead=getOneRandomCard();
        return forehead;
    }

    public Card setSonic(){
        sonic=getOneRandomCard();
        return sonic;
    }

    private Card getOneRandomCard(){
        Card card;
        int pos=random.nextInt(shtose.size());
        Iterator<Integer> cardIterator=shtose.keySet().iterator();
        int i=0;
        while (i<pos) {
            cardIterator.next();
            i++;
        }
        int key=cardIterator.next();
        card=shtose.get(key);
        shtose.remove(key);
        return card;
    }

    public int getGameID() {
        return gameID;
    }


    public Map<Integer, Card> getShtose() {
        return shtose;
    }

    public void setShtose(Map<Integer, Card> shtose) {
        this.shtose = shtose;
    }

    public BigDecimal getJackpot() {
        return jackpot;
    }

    public void setJackpot(BigDecimal jackpot) {
        this.jackpot = jackpot;
    }

    public Card getMainCard() {
        return mainCard;
    }

    public void setMainCard(Card mainCard) {
        this.mainCard = mainCard;
    }

    public Card getForehead() {
        return forehead;
    }

    public void setForehead(Card forehead) {
        this.forehead = forehead;
    }

    public Card getSonic() {
        return sonic;
    }

    public void setSonic(Card sonic) {
        this.sonic = sonic;
    }
}
