package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.ProfileDAO;
import com.loneliness.entity.Profile;
import com.loneliness.service.game.GameData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameEndService implements Service<Integer, Boolean, GameData, GameData> {
    private final ProfileDAO dao;
    private Logger logger = LogManager.getLogger();
    private Profile profile;

    public GameEndService(ProfileDAO dao) {
        this.dao = dao;
    }

    @Override
    public Boolean execute(GameData note) throws ServiceException {
        try {
            profile = dao.getProfileByUserId(note.getGamerId());
            dao.update(changeProfileData(profile, note));

        } catch (DAOException e) {
            e.printStackTrace();
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
        return null;
    }

    @Override
    public Integer undo(GameData note) throws ServiceException {
        try {
            return dao.update(profile);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private Profile changeProfileData(Profile profile, GameData gameData) {
        Profile.Builder builder = new Profile.Builder(profile);
        int deltaRating = -50;
        if (gameData.isGamerWon()) {
            deltaRating *= -1;
            builder.addScore(gameData.getJackpot())
                    .addOneVictory();
        } else {
            builder.minusScore(gameData.getJackpot())
                    .addOneDefeat();
        }
        builder.addRating(deltaRating);
        return builder.build();
    }
}
