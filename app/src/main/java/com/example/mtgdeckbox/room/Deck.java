package com.example.mtgdeckbox.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * This class represents a Deck Entity object.
 * @author: Tom Barker
 */
@Entity
public class Deck {
    @PrimaryKey (autoGenerate = true)
    private int deckID;

    @ColumnInfo (name = "deckName")
    @NonNull
    private String deckName;

    @ColumnInfo (name = "commanderID")
    private int commanderID;

    /**
     * This is the default constructor.
     */
    public Deck() {
        deckName = "";
        commanderID = -1;
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer containing the ID number for this deck.
     */
    public int getDeckID() {
        return deckID;
    }

    /**
     * This is the Accessor method for the deckName field.
     * @return a String containing the name of this deck.
     */
    @NonNull
    public String getDeckName() {
        return deckName;
    }

    /**
     * This is the Accessor method for the commanderID field.
     * @return an Integer containing the ID number of the Card which is the
     *         commander of this deck.
     */
    public int getCommanderID() {
        return commanderID;
    }

    /**
     * This is the Mutator method for the deckID field.
     * @param deckID an Integer containing the new ID number for this deck.
     */
    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }

    /**
     * This is the Mutator method for the deckName field.
     * @param deckName a String containing the name of this deck.
     */
    public void setDeckName(@NonNull String deckName) {
        this.deckName = deckName;
    }

    /**
     * This is the Mutator method for the commander field.
     * @param commanderID an Integer containing the ID number of the new Card
     *                    which is to be the commander of this deck.
     */
    public void setCommanderID(int commanderID) {
        this.commanderID = commanderID;
    }

}
