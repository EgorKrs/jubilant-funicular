package com.loneliness.command;

import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * класс для увеличения рейтингамигрока с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class AddScoreCommand implements Command<Integer, Integer, Object[]> {
    private final Service<Integer, Integer, Object[], Object[]> service;
    private Logger logger = LogManager.getLogger();
    private Object[] note;

    public AddScoreCommand(Service<Integer, Integer, Object[], Object[]> service) {
        this.service = service;
    }

    /**
     * @param note данные по которым происходит изменение счета [0] id пользователя [1] сумма данег
     * @return @return
     * 1-ok
     * -3-invalid note
     */
    @Override
    public Integer execute(Object[] note) throws CommandException {
        this.note = note;
        try {
            return service.execute(note);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return 1-ok
     * -3-invalid note
     */
    @Override
    public Integer undo() throws CommandException {
        try {
            return service.undo(note);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
