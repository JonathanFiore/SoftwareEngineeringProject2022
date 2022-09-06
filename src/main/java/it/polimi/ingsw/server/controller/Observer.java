package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.protocol.Message;

/**
 *The classes which implement this interface are observers
 */
public interface Observer
{
    /**
     *This method is used when an object wants to notify its observer
     */
    void update(Message message);
}
