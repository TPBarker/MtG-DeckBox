package com.example.mtgdeckbox.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class is the DeckCards Entity - an intermediate Entity which links
 * Card and Deck in a many-to-many relationship.
 * @author: Tom Barker
 */
@Entity
public class DeckCards {
    @PrimaryKey (autoGenerate = true)
    private int deckCardID;

    @ColumnInfo (name = "deck_ID")
    @NonNull
    private int deckID;

    @ColumnInfo (name = "card_ID")
    @NonNull
    private int cardID;

    @ColumnInfo (name = "quantity")
    @NonNull
    private int quantity;

    /**
     * This is the default constructor.
     */
    public DeckCards() {}

    /**
     * This is the non-default constructor.
     * @param deckID    an Integer containing the ID number of the deck.
     * @param cardID    an Integer containing the ID number of the card.
     * @param quantity  an Integer containing the quantity of this card in this deck.
     */
    public DeckCards(int deckID, int cardID, int quantity) {
        this.deckID = deckID;
        this.cardID = cardID;
        this.quantity = quantity;
    }

    /**
     * This is the Accessor method for the deckCardID field.
     * @return an Integer containing the ID number of this deckCard entry.
     */
    public int getDeckCardID() {
        return deckCardID;
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer containing the deck ID number.
     */
    public int getDeckID() {
        return deckID;
    }

    /**
     * This is the Accessor method for the cardID field.
     * @return an Integer containing the card ID number.
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * This is the Accessor method for the quantity field.
     * @return an Integer containing the quantity for this card, in this deck.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This is the Mutator method for the deckCardID field.
     * @param deckCardID an Integer containing the new ID number for this
     *                   deckCard combination.
     */
    public void setDeckCardID(int deckCardID) {
        this.deckCardID = deckCardID;
    }

    /**
     * This is the Mutator method for the deckID field.
     * @param deckID an Integer containing the ID number of this deck.
     */
    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }

    /**
     * This is the Mutator method for the cardID field.
     * @param cardID an Integer containing the ID number of this card.
     */
    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    /**
     * This is the Mutator method for the quantity field.
     * @param quantity an Integer containing the quantity for this card
     *                 in this deck.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}