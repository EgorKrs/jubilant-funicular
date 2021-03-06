package com.loneliness.service.game;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.command.GameEnd;
import com.loneliness.command.ReceiveDeckOfCardsCommand;
import com.loneliness.dao.sql_dao_impl.ProfileDAO;
import com.loneliness.entity.Card;
import com.loneliness.service.GameEndService;
import com.loneliness.service.ReceiveDeckOfCardsService;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Game {
    private final Logger logger = LogManager.getLogger();
    private final GameData gameData;
    private final Random random;
    private final String gameID;
    private Map<Integer, Card> cardDeck;
    private Card mainCard;
    private Deque<Card> forehead;//лоб карты ии
    private Deque<Card> sonic;// соник карты игрока
    private Stage stage;

    public Game(String gameID, int decksOfCardsID, int gamerId, BigDecimal jackpot, Card mainCard) throws ServiceException {
        this.stage = Stage.SELECTION_START_ATTRIBUTE;
        this.gameID=gameID;
        this.mainCard = mainCard;
        this.gameData = new GameData(gamerId, jackpot);
        this.random = new Random();
        forehead = new ConcurrentLinkedDeque<>();
        sonic = new ConcurrentLinkedDeque<>();
        try {
            this.cardDeck = new ReceiveDeckOfCardsCommand(new ReceiveDeckOfCardsService()).execute(decksOfCardsID);
        } catch (CommandException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public Queue<Card> getForehead() {

        return forehead;
    }

    public Queue<Card> getSonic() {

        return sonic;
    }


    public Deque[] playGame() throws CommandException {
        stage = Stage.GAME;
        distributeCard();
        return new Deque[]{forehead, sonic};
    }


    private Card getOneRandomCard(){
        Card card;
        int pos = random.nextInt(cardDeck.size());
        Iterator<Integer> cardIterator = cardDeck.keySet().iterator();
        int i=0;
        while (i<pos) {
            cardIterator.next();
            i++;
        }
        int key=cardIterator.next();
        card = cardDeck.get(key);
        cardDeck.remove(key);
        return card;
    }

    private void distributeCard() throws CommandException {
        if (stage.equals(Stage.GAME)) {
            int size = cardDeck.size();
            for (int i = 0; i < size; i++) {
                Card card = getOneRandomCard();
                if (i % 2 == 0) {
                    forehead.add(card);
                } else {
                    sonic.add(card);
                }
                if (getMainCard().lightEquals(card)) {
                    endGame(card);
                    return;
                }
            }
            endGame(new Card.Builder().build());
        }
    }


    public boolean finishGame() {
        cardDeck.clear();
        cardDeck = null;
        mainCard = null;
        forehead.clear();
        forehead = null;
        sonic.clear();
        sonic = null;
        stage = Stage.END_OF_THE_GAME;
        return true;
    }

    private void endGame(Card card) throws CommandException {
        if (forehead.contains(card)) {
            gameData.setGamerWon(false);
        } else if (sonic.contains(card)) {
            gameData.setGamerWon(true);
        } else {
            gameData.setGamerWon(false);
        }
        Command command = null;
        command = new GameEnd(new GameEndService(new ProfileDAO()));
        command.execute(gameData);
    }


    public Map<Integer, Card> getCardDeck() {
        return cardDeck;
    }


    public Card getMainCard() {
        return mainCard;
    }

    public void setMainCard(Card mainCard) {
        this.mainCard = mainCard;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getGameId() {
        return gameID;
    }

    public GameData getGameData() {
        return gameData;
    }

    public int getGamerId() {
        return gameData.getGamerId();
    }

}
