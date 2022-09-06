package it.polimi.ingsw.protocol;

/**
 * This message is sent from the client to the server to indicate the chosen card.
 */
public class SelectedCardMessage extends Message{

    private final int card;

    public SelectedCardMessage(int card) {
        super(MessageLabel.SELECTED_CARD);
        this.card = card;
    }

    /**
     * @return the card that has been chosen
     */
    public int getCard() {
        return card;
    }
}
