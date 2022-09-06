package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.controller.Observer;

/**
 * This class specifies a generic javaFX controller
 */
public abstract class Controller {

    private Observer networkHandler;

    /**
     * This method sets a networkHandler to the controller
     * @param networkHandler specifies the object used as an Observer to communicate with the Server
     */
    public void setNetworkHandler(Observer networkHandler) {
        this.networkHandler = networkHandler;
    }

    /**
     * @return the controller's networkHandler
     */
    public Observer getNetworkHandler() {
        return networkHandler;
    }
}
