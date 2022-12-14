package com.example.mtgdeckbox.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class is the DAO for the Card Entity.
 * @author: Tom Barker
 */
@Dao
public interface CardDAO {

    /**
     * This query returns all of the cards in the database.
     * @return a LiveData object containing a List of all Cards in the database.
     */
    @Query ("SELECT * FROM card ORDER BY card_name ASC")
    LiveData<List<Card>> getAll();

    /**
     * This query returns all of the cards in the database, for a Supplier.
     * @return a List of all Cards in the database.
     */
    @Query ("SELECT * FROM card ORDER BY card_name ASC")
    List<Card> getAllCards();

    /**
     * This query inserts a new Card into the database.
     * @param card a Card to be inserted.
     */
    @Insert
    void insertCard(Card card);

    /**
     * This query updates an existing Card in the database.
     * @param card a Card to be updated.
     */
    @Update
    void updateCard(Card card);

    /**
     * This query deletes a Card from the database.
     * @param card a Card to be deleted.
     */
    @Delete
    void deleteCard(Card card);

    /**
     * This query deletes all data from the Card entity.
     */
    @Query ("DELETE FROM card")
    void deleteAllCards();

    /**
     * This query returns all Cards from the database which are eligible to be
     * chosen as commanders.
     * @return a List of Cards which are eligible to be chosen as commanders.
     */
    @Query ("SELECT * FROM card WHERE canBeCommander = 1")
    List<Card> getCommanders();

    /**
     * This query returns a specific requested Card.
     * @param cardID an Integer containing the ID number of the Card being queried.
     * @return the Card requested.
     */
    @Query ("SELECT * FROM card WHERE cardID = :cardID")
    Card getCardByID(int cardID);

    /**
     * This query returns Cards of a specific colour identity.
     * @param colourIdentity a String describing the colour identity being
     *                       requested.
     * @return a List of Cards matching the requested colour identity.
     */
    @Query ("SELECT * FROM card WHERE colourIdentity LIKE :colourIdentity")
    List<Card> getFutureCardsByColourIdentity(String colourIdentity);

    /**
     * This query returns all colourless Cards from the database.
     * @return a List of Cards which have no colour identity.
     */
    @Query ("SELECT * FROM card WHERE colourIdentity IS null")
    List<Card> getFutureColourlessCards();

    /**
     * This query returns all Ramp Cards from the database.
     * @return a List of Cards which have the matching category.
     */
    @Query ("SELECT * FROM card WHERE categories LIKE '%ramp%' OR " +
            "categories LIKE '%mana%' ORDER BY rank ASC")
    List<Card> getRampCards();

    /**
     * This query returns all Card-draw Cards from the database.
     * @return a List of Cards which have the matching category.
     */
    @Query ("SELECT * FROM card WHERE categories LIKE '%cardraw%' " +
            "ORDER BY rank ASC")
    List<Card> getDrawCards();

    /**
     * This query returns all Removal Cards from the database.
     * @return a List of Cards which have the matching category.
     */
    @Query ("SELECT * FROM card WHERE categories LIKE '%removal%' " +
            "ORDER BY card_name ASC")
    List<Card> getRemovalCards();

    /**
     * This query returns all Board-wipes from the database.
     * @return a List of Cards which have the matching category.
     */
    @Query ("SELECT * FROM card WHERE categories LIKE '%wrath%' " +
            "ORDER BY card_name ASC")
    List<Card> getBoardWipes();
}
