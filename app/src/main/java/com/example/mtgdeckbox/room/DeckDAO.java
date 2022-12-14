package com.example.mtgdeckbox.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This class is the DAO class for the Deck Entity.
 * @author: Tom Barker
 */
@Dao
public interface DeckDAO {

    /**
     * This method returns a list of all the Decks in the database,
     * as a LiveData.
     * @return a LiveData List of all Decks in the database.
     */
    @Query("SELECT * FROM deck")
    LiveData<List<Deck>> getAll();

    /**
     * This method returns a list of all the Decks in the database.
     * @return a List of all Decks in the database.
     */
    @Query("SELECT * FROM deck")
    List<Deck> getAllDecks();

    /**
     * This method inserts a new Deck into the database.
     * @param deck the Deck to be inserted.
     */
    @Insert
    void insertDeck(Deck deck);

    /**
     * This method updates a Deck in the database.
     * @param deck the Deck to be updated.
     */
    @Update
    void updateDeck(Deck deck);

    /**
     * This method deletes a Deck from the database.
     * @param deck the Deck to be deleted.
     */
    @Delete
    void deleteDeck(Deck deck);

    /**
     * This method returns a specific Deck from the database.
     * @param deckID an Integer containing the ID number of the Deck to be fetched.
     * @return the requested Deck.
     */
    @Query("SELECT * FROM deck WHERE deckID = :deckID")
    Deck getDeck(int deckID);

    /**
     * This method returns a specific Deck from the database as a LiveData.
     * @param deckID an Integer containing the ID number of the Deck to be fetched.
     * @return a LiveData containing the Deck.
     */
    @Query("SELECT * FROM deck WHERE deckID = :deckID")
    LiveData<Deck> getLiveDeck(int deckID);

    /**
     * This method returns the Deck from the database which was most
     * recently inserted.
     * @return the Deck which was most recently inserted into the database.
     */
    @Query("SELECT * FROM deck WHERE deckID = (SELECT MAX(deckID) FROM deck)")
    Deck getLatestDeck();

    /**
     * This method deletes all data from the database. Used mostly for debugging
     * and testing.
     */
    @Query("DELETE FROM deck")
    void deleteAllDecks();
}
