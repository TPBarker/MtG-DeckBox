package com.example.mtgdeckbox.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * This class defines a Card from Magic: The Gathering, defined as an
 * SQL Entity.
 * @author: Tom Barker
 */
@Entity
public class Card {
    @PrimaryKey (autoGenerate = true)
    private int cardID;

    @ColumnInfo (name = "card_name")
    @NonNull
    private String name;

    @ColumnInfo (name = "superTypes")
    @NonNull
    private String superTypes;

    @ColumnInfo (name = "types")
    @NonNull
    private String types;

    @ColumnInfo (name = "subtypes")
    @NonNull
    private String subtypes;

    @ColumnInfo (name = "colourIdentity")
    @NonNull
    private String colourIdentity;

    @ColumnInfo (name = "manaCost")
    @NonNull
    private String manaCost;

    @ColumnInfo (name = "manaValue")
    @NonNull
    private int manaValue;

    @ColumnInfo (name = "rank")
    @NonNull
    private int rank;

    @ColumnInfo (name = "alternateLimit")
    @NonNull
    private boolean alternateLimit;

    @ColumnInfo (name = "canBeCommander")
    @NonNull
    private boolean canBeCommander;

    @ColumnInfo (name = "multiverseID")
    @NonNull
    private int multiverseID;

    @ColumnInfo (name = "scryfallID")
    @NonNull
    private String scryfallID;

    @ColumnInfo (name = "commanderLegal")
    @NonNull
    private boolean commanderLegal;

    @ColumnInfo (name = "categories")
    @NonNull
    private String categories;

    /**
     * This is the default constructor.
     */
    public Card() {
        name = "";
        superTypes = "";
        types = "";
        subtypes = "";
        colourIdentity = "";
        manaCost = "";
        manaValue = -1;
        rank = -1;
        alternateLimit = false;
        canBeCommander = false;
        multiverseID = -1;
        scryfallID = "";
        commanderLegal = false;
        categories = "";
    }

    /**
     * This is the non-default constructor.
     */
    public Card(ArrayList<String> cardData) {
        // Strip quotation marks from the name.
        name = cardData.get(0).replace("\"", "");

        superTypes = cardData.get(1).replace("\"", "");
        types = cardData.get(2).replace("\"", "");
        subtypes = cardData.get(3).replace("\"", "");
        colourIdentity = cardData.get(4);
        manaCost = cardData.get(5);
        manaValue = Integer.parseInt(cardData.get(6));

        // Handle a blank ranking in the dataset.
        if (cardData.get(7).isEmpty()) {
            rank = -1;
        } else {
            rank = Integer.parseInt(cardData.get(7));
        }

        // Handle a blank alternate quantity limit in dataset.
        if (cardData.get(8).isEmpty()) {
            alternateLimit = false;
        } else {
            if (Integer.parseInt(cardData.get(8)) > 0) {
                alternateLimit = true;
            } else {
                alternateLimit = false;
            }
        }

        // Handle a blank can be commander in dataset.
        if (cardData.get(9).isEmpty()) {
            canBeCommander = false;
        } else {
            if (Integer.parseInt(cardData.get(9)) > 0) {
                canBeCommander = true;
            } else {
                canBeCommander = false;
            }
        }

        // Handle a blank multiverse ID in dataset.
        if (cardData.get(10).isEmpty()) {
            multiverseID = -1;
        } else {
            multiverseID = Integer.parseInt(cardData.get(10));
        }

        scryfallID = cardData.get(11);

        // Handle a blank commander legality in dataset.
        if (cardData.get(12).isEmpty()) {
            commanderLegal = false;
        } else {
            if (Integer.parseInt(cardData.get(12)) > 0) {
                commanderLegal = true;
            } else {
                commanderLegal = false;
            }
        }

        // Strip quotation marks from the categories.
        categories = cardData.get(13).replace("\"", "");
    }

    /**
     * This is the Accessor method for the alternateLimit field.
     * @return a Boolean describing if the card has an alternate Qty limit or not.
     */
    public boolean getAlternateLimit() {
        return alternateLimit;
    }

    /**
     * This is the Accessor method for the canBeCommander field.
     * @return a Boolean describing if the card is legal to be the deck Commander or not.
     */
    public boolean getCanBeCommander() {
        return canBeCommander;
    }

    /**
     * This is the Accessor method for the cardID field.
     * @return an Integer containing the card's ID number for the DB.
     */
    public int getCardID() {return cardID;}

    /**
     * This is the Accessor method for the categories field.
     * @return a String containing all of this Card's categories.
     */
    public String getCategories() {
        return categories;
    }

    /**
     * This is the Accessor method for the colourIdentity field.
     * @return a String containing this Card's colour identity.
     */
    public String getColourIdentity() {
        return colourIdentity;
    }

    /**
     * This is the Accessor method for the commanderLegal field.
     * @return a Boolean describing if this Card is legal in Commander or not.
     */
    public boolean getCommanderLegal() {
        return commanderLegal;
    }

    /**
     * This is the Accessor method for the manaCost field.
     * @return a String containing the mana cost for this Card.
     */
    public String getManaCost() {
        return manaCost;
    }

    /**
     * This is the Accessor method for the manaValue field.
     * @return an Integer describing the converted mana value for the Card.
     */
    public int getManaValue() {
        return manaValue;
    }

    /**
     * This is the Accessor method for the multiverseID field.
     * @return an Integer containing the multiverseID for the Card.
     */
    public int getMultiverseID() {
        return multiverseID;
    }

    /**
     * This is the Accessor method for the name field.
     * @return a String containing the name of the Card.
     */
    public String getName() {
        return name;
    }

    /**
     * This is the Accessor method for the rank field.
     * @return an Integer containing the EDHREC Rank for this Card.
     */
    public int getRank() {
        return rank;
    }

    /**
     * This is the Accessor method for the scryfallID field.
     * @return a String containing the Scryfall UID for this Card.
     */
    public String getScryfallID() {
        return scryfallID;
    }

    /**
     * This is the Accessor method for the subtypes field.
     * @return a String containing all of the subtypes for this Card.
     */
    public String getSubtypes() {
        return subtypes;
    }

    /**
     * This is the Accessor method for the supertypes field.
     * @return a String containing all of the supertypes for this Card.
     */
    public String getSuperTypes() {
        return superTypes;
    }

    /**
     * This is the Accessor method for the types field.
     * @return a String containing the types for this Card.
     */
    public String getTypes() {
        return types;
    }

    /**
     * This is the Mutator method for the alternateLimit field.
     * @param alternateLimit a Boolean describing if the Card has an alternate
     *                       quantity limit or not.
     */
    public void setAlternateLimit(boolean alternateLimit) {
        this.alternateLimit = alternateLimit;
    }

    /**
     * This is the Mutator method for the canBeCommander field.
     * @param canBeCommander a Boolean describing if this Card can be the Commander
     *                       of a deck or not.
     */
    public void setCanBeCommander(boolean canBeCommander) {
        this.canBeCommander = canBeCommander;
    }

    /**
     * This is the Mutator method for the categories field.
     * @param categories a comma separated String containing the new categories
     *                   for this Card.
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     * This is the Mutator method for the cardID field.
     * @param newCardID an Integer containing the new ID number for the Card.
     */
    public void setCardID(int newCardID) {this.cardID = newCardID;}

    /**
     * This is the Mutator method for the colourIdentity field.
     * @param colourIdentity a String containing the colour identity of this Card.
     */
    public void setColourIdentity(String colourIdentity) {
        this.colourIdentity = colourIdentity;
    }

    /**
     * This is the Mutator method for the commanderLegal field.
     * @param commanderLegal a Boolean describing if this Card is legal to be played
     *                       in Commander or not.
     */
    public void setCommanderLegal(boolean commanderLegal) {
        this.commanderLegal = commanderLegal;
    }

    /**
     * This is the Mutator method for the manaCost field.
     * @param manaCost a String containing the mana cost information for this Card.
     */
    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    /**
     * This is the Mutator method for the manaValue field.
     * @param manaValue an Integer containing the new converted mana value for
     *                  this Card.
     */
    public void setManaValue(int manaValue) {
        this.manaValue = manaValue;
    }

    /**
     * This is the Mutator method for the multiverseID field.
     * @param multiverseID an Integer containing the new multiverse ID for this Card.
     */
    public void setMultiverseID(int multiverseID) {
        this.multiverseID = multiverseID;
    }

    /**
     * This is the Mutator method for the name field.
     * @param name a String containing the new name for this Card.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is the Mutator method for the rank field.
     * @param rank an Integer containing the new EDHREC Rank for this Card.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * This is the Mutator method for the scryfall ID field.
     * @param scryfallID a String containing the new Scryfall UID for this Card.
     */
    public void setScryfallID(String scryfallID) {
        this.scryfallID = scryfallID;
    }

    /**
     * This is the Mutator method for the subTypes field.
     * @param subtypes a String containing the new subTypes for this Card.
     */
    public void setSubtypes(String subtypes) {
        this.subtypes = subtypes;
    }

    /**
     * This is the Mutator method for the superTypes field.
     * @param superTypes a String containing the new superTypes for this Card.
     */
    public void setSuperTypes(String superTypes) {
        this.superTypes = superTypes;
    }

    /**
     * This is the Mutator method for the types field.
     * @param types a String containing the new types for this Card.
     */
    public void setTypes(String types) {
        this.types = types;
    }

    /**
     * This method returns all of the Card information in a String format.
     * @return a String containing all of the fields for this Card.
     */
    public String toString() {
        String thisCard = name + " " + superTypes + " " + types + " " + subtypes + " " +
                colourIdentity + " " + manaCost + " " + manaValue + " " + rank +
                " " + alternateLimit + " " +canBeCommander + " " + multiverseID +
                " " + scryfallID + " " + commanderLegal + " " + categories;
        return thisCard;
    }
}