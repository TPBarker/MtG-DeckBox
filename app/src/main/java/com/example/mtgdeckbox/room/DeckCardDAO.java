package com.example.mtgdeckbox.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * This class is the DAO for accessing the DeckCards Entity.
 */
@Dao
public interface DeckCardDAO {

    /**
     * This method returns the quantity of a specified Card in a
     * specified Deck.
     * @param deckID an Integer containing the ID number of the Deck to check.
     * @param cardID an Integer containing the ID number of the Card to check.
     * @return a List of Integers as a LiveData, containing the quantity of the
     * specified Card in the specified Deck.
     */
    @Query("SELECT quantity FROM deckcards" +
            " WHERE deck_ID = :deckID AND card_ID = :cardID")
    LiveData<List<Integer>> getQuantities(int deckID, int cardID);

    /**
     * This method deletes a DeckCards from the database for a specific Deck.
     * @param deckID an Integer containing the ID number of the Deck for which
     *               the DeckCards should be removed.
     */
    @Query("DELETE FROM deckcards WHERE deck_ID = :deckID")
    void deleteDeck(int deckID);

    /**
     * This method returns the quantities for a specified Deck.
     * @param deckID an Integer containing the ID number of the Deck to check.
     * @return a List of DeckCards containing all the quantities in the
     * specified Deck.
     */
    @Query("SELECT * FROM deckcards WHERE deck_ID = :deckID")
    List<DeckCards> getDeckCardQuantities(int deckID);

    /**
     * This method inserts a new DeckCards into the database.
     * @param deckCards a new DeckCards to be inserted.
     */
    @Insert
    void insertDeckCards(DeckCards deckCards);

    /**
     * This method deletes any DeckCards where the quantity has been changed
     * to zero.
     */
    @Query("DELETE FROM deckcards WHERE quantity = 0")
    void cleanDeckCards();

    /**
     * This method returns a DeckCards for a specified Card and Deck.
     * @param deckID an Integer containing the ID number of the Deck.
     * @param cardID an Integer containing the ID number of the Card.
     * @return a DeckCards containing the quantity of the specified Card
     * in the specified Deck.
     */
    @Query("SELECT * FROM deckcards WHERE deck_ID = :deckID AND card_ID = :cardID")
    DeckCards getSpecificDeckCards(int deckID, int cardID);

    /**
     * This method returns a List of DeckCards for a specified Deck, as a LiveData.
     * @param deckID an Integer containing the ID number of the Deck to be checked.
     * @return a List of all DeckCards for the specified Deck, as a LiveData.
     */
    @Query("SELECT * FROM deckcards WHERE deck_ID = :deckID")
    LiveData<List<DeckCards>> getLiveDeckCards(int deckID);

    /**
     * This method deletes a specific DeckCards from the database.
     * @param deckID an Integer containing the ID number of the Deck for which the
     *               DeckCards should be removed.
     * @param cardID an Integer containing the ID number of the Card for which the
     *               DeckCards should be removed.
     */
    @Query("DELETE FROM deckcards WHERE deck_ID = :deckID AND card_ID = :cardID")
    void removeSpecificDeckCards(int deckID, int cardID);

    /**
     * This method clears all data from the database. Used mostly for
     * debugging and testing.
     */
    @Query("DELETE FROM deckcards")
    void deleteAllDeckCards();

}
