package com.example.mtgdeckbox.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class is the ViewModel which allows the application to access the
 * repository.
 * @author: Tom Barker
 */
public class CardViewModel extends AndroidViewModel {
    private CardRepository repository;

    /**
     * This is the non-default constructor.
     * @param application an Application object representing the Application,
     */
    public CardViewModel (Application application) {
        super(application);
        repository = new CardRepository(application);
    }

    /**
     * This method adds a new Card into the database.
     * @param card a Card to be inserted into the database.
     */
    public void addCard (Card card) {
        repository.addCard(card);
    }

    /**
     * This method cleans out all DeckCards with zero quantity from the database.
     */
    public void cleanDeckCards() {
        repository.cleanDeckCards();
    }

    /**
     * This method deletes all Cards from the database.
     */
    public void deleteAllCards() {
        repository.deleteAllCards();
    }

    /**
     * This method returns a list of all Cards in the database,
     * as a CompletableFuture.
     * @return a CompletableFuture containing a List of all Cards from the database.
     */
    public CompletableFuture<List<Card>> getAllCardsByFuture() {
        return repository.getAllCardsByFuture();
    }

    /**
     * This method returns a list of all Cards eligible to be commanders from
     * the database, as a CompletableFuture.
     * @return a CompletableFuture containing a List of all Cards eligible
     * to be commanders from the database.
     */
    public CompletableFuture<List<Card>> getAllCommanders() {
        return repository.getAllCommandersByFuture();
    }

    /**
     * This method returns a list of all Decks from the database.
     * @return a CompletableFuture containing a List of all Decks
     * from the database.
     */
    public CompletableFuture<List<Deck>> getAllDecksByFuture() {
        return repository.getAllDecksByFuture();
    }

    /**
     * This method returns a list of all Cards categorised as 'Board Wipes'
     * from the database, as a CompletableFuture.
     * @return a CompletableFuture containing a List of all 'Board Wipe' Cards
     * from the database.
     */
    public CompletableFuture<List<Card>> getBoardWipesByFuture() {
        return repository.getBoardWipesByFuture();
    }

    /**
     * This method returns a specified Card from the database, as a CompletableFuture.
     * @param cardID an Integer containing the ID number of the requested Card.
     * @return a CompletableFuture containing the Card requested.
     */
    public CompletableFuture<Card> getCardByID(int cardID) {return repository.getCardByID(cardID);}

    /**
     * This method returns a specified Deck from the database, as a CompletableFuture.
     * @param deckID an Integer containing the ID number of the requested Deck.
     * @return a CompletableFuture containing the Deck requested.
     */
    public CompletableFuture<Deck> getDeck(int deckID) {return repository.getDeck(deckID);}

    /**
     * This method returns a list of DeckCards for a specified Deck, as a
     * CompletableFuture.
     * @param deckID an Integer containing the ID number of the Deck to be
     *               returned.
     * @return a CompletableFuture containing a List of DeckCards for the
     * specified Deck.
     */
    public CompletableFuture<List<DeckCards>> getDeckCards(int deckID) {
        return repository.getDeckCards(deckID);
    }

    /**
     * This method returns a list of all 'Draw' Cards from the database, as a
     * CompletableFuture.
     * @return a CompletableFuture containing a list of all 'Draw' Cards.
     */
    public CompletableFuture<List<Card>> getDrawCardsByFuture() {
        return repository.getDrawCardsByFuture();
    }

    /**
     * This method returns a list of all Cards of a specified colour identity from
     * the database, as a CompletableFuture.
     * @param identity a String containing the colour identity to be returned.
     * @return a CompletableFuture containing a list of Cards of the specified
     * colour identity.
     */
    public CompletableFuture<List<Card>> getFutureCardsByColourIdentity(String identity) {
        return repository.getFutureCardsByColourIdentity(identity);
    }

    /**
     * This method returns a list of all colourless Cards from the database, as
     * a CompletableFuture.
     * @return a CompletableFuture containing a list of all colourless Cards
     * from the database.
     */
    public CompletableFuture<List<Card>> getFutureColourlessCards() {
        return repository.getFutureColourlessCards();
    }

    /**
     * This method returns the latest Deck to be inserted into the database,
     * as a CompletableFuture.
     * @return a CompletableFuture containing the latest Deck.
     */
    public CompletableFuture<Deck> getLatestDeck() {return repository.getLatestDeck();}

    /**
     * This method returns a particular Deck from the database, as LiveData.
     * @param liveDeckID an Integer containing the ID number of the Deck to be
     *                   retrieved.
     * @return the specified Deck, as LiveData.
     */
    public LiveData<Deck> getLiveDeck(int liveDeckID) {return repository.getLiveDeck(liveDeckID);}

    /**
     * This method returns a list of DeckCards from the database for a specified
     * Deck, as LiveData.
     * @param deckID an Integer containing the deck ID number for the DeckCards
     *               to be retrieved.
     * @return a LiveData List of DeckCards for the specified Deck.
     */
    public LiveData<List<DeckCards>> getLiveDeckCards (int deckID) {
        return repository.getLiveDeckCards(deckID);
    }

    /**
     * This method returns a list of all 'Ramp' Cards from the database,
     * as a CompletableFuture.
     * @return a CompletableFuture containing a list of all 'Ramp' Cards.
     */
    public CompletableFuture<List<Card>> getRampCardsByFuture() {
        return repository.getRampCardsByFuture();
    }

    /**
     * This method returns a list of all 'Removal' Cards from the database,
     * as a CompletableFuture.
     * @return a CompletableFuture containing a List of all 'Removal' Cards.
     */
    public CompletableFuture<List<Card>> getRemovalCardsByFuture() {
        return repository.getRemovalCardsByFuture();
    }

    /**
     * This is the Accessor method for the repository field.
     * @return a CardRepository object which allows access to the DAOs.
     */
    public CardRepository getRepository() {
        return repository;
    }

    /**
     * This method returns a specific DeckCards from the database, as a
     * CompletableFuture.
     * @param deckID an Integer containing the deck ID number of the DeckCards
     *               to be returned.
     * @param cardID an Integer containing the card ID number of the DeckCards
     *               to be returned.
     * @return a CompletableFuture containing the specified DeckCards.
     */
    public CompletableFuture<DeckCards> getSpecificDeckCards(int deckID, int cardID) {
        return repository.getSpecificDeckCards(deckID, cardID);
    }

    /**
     * This method inserts a Deck into the database.
     * @param deck a Deck to be inserted into the database.
     */
    public void insertDeck(Deck deck) {repository.insertDeck(deck);}

    /**
     * This method inserts a DeckCards into the database.
     * @param deckCards a DeckCards to be inserted into the database.
     */
    public void insertDeckCards(DeckCards deckCards) {
        repository.insertDeckCards(deckCards);
    }

    /**
     * This method removes a specific Deck from the database.
     * @param deck a Deck to be removed from the database.
     */
    public void removeDeck(Deck deck) {repository.deleteDeck(deck);}

    /**
     * This method removes a specific DeckCards from the database.
     * @param deckID an Integer containing the deck ID number of the DeckCards
     *               to be removed.
     * @param cardID an Integer containing the card ID number of the DeckCards
     *               to be removed.
     */
    public void removeSpecificDeckCards (int deckID, int cardID) {
        repository.removeSpecificDeckCards(deckID, cardID);
    }

    /**
     * This is the Mutator method for the repository field.
     * @param repository a CardRepository object which provides access to the DAOs.
     */
    public void setRepository(CardRepository repository) {
        this.repository = repository;
    }

    /**
     * This method updates a Deck in the database.
     * @param deck a Deck to be updated.
     */
    public void updateDeck(Deck deck) {repository.updateDeck(deck);}

    /**
     * This method wipes the entire database.
     */
    public void wipeDatabase() {
        repository.wipeDatabase();
    }
}
