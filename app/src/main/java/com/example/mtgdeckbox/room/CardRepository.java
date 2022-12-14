package com.example.mtgdeckbox.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * This class is the Repository used by the ViewModel to access the database.
 * @author: Tom Barker
 */
public class CardRepository {
    private CardDAO cardDAO;
    private DeckDAO deckDAO;
    private DeckCardDAO deckCardDAO;

    /**
     * This is the default constructor.
     */
    public CardRepository() {}

    /**
     * This is the non-default constructor.
     * @param application The Application which is creating this Repository.
     */
    public CardRepository(Application application) {
        CardDatabase db = CardDatabase.getInstance(application);
        cardDAO = db.cardDAO();
        deckDAO = db.deckDAO();
        deckCardDAO = db.quantities();
    }

    /**
     * This method adds new Cards to the database.
     * @param card a Card to be added to the database.
     */
    public void addCard(final Card card) {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardDAO.insertCard(card);
            }
        });
    }

    /**
     * This method cleans up any DeckCards with quantity zero from the database.
     */
    public void cleanDeckCards() {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {deckCardDAO.cleanDeckCards();}
        });
    }

    /**
     * This method deletes all Cards from the database.
     */
    public void deleteAllCards() {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardDAO.deleteAllCards();
            }
        });
    }

    /**
     * This method deletes a Deck from the database.
     * @param deck a Deck to be deleted from the database.
     */
    public void deleteDeck(Deck deck) {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                deckDAO.deleteDeck(deck);
                deckCardDAO.deleteDeck(deck.getDeckID());
            }
        });
    }

    /**
     * This method returns a list of all Cards in the database.
     * @return a LiveData List of all Cards from the database.
     */
    public LiveData<List<Card>> getAllCards() {
        return cardDAO.getAll();
    }

    /**
     * This method returns a list of all Cards in the database, as a
     * CompletableFuture.
     * @return a CompletableFuture containing a List of all Cards in the database.
     */
    public CompletableFuture<List<Card>> getAllCardsByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {
                return cardDAO.getAllCards();
            }
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a list of all Cards eligible to be a commander, as
     * a CompletableFuture.
     * @return a CompletableFuture containing a List of all Cards eligible
     * to be a commander.
     */
    public CompletableFuture<List<Card>> getAllCommandersByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {return cardDAO.getCommanders();}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a list of all Decks in the database, as a
     * CompletableFuture.
     * @return a CompletableFuture containing a list of all Decks in the database.
     */
    public CompletableFuture<List<Deck>> getAllDecksByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Deck>>() {
            @Override
            public List<Deck> get() {return deckDAO.getAllDecks();}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a list of all Cards in the database categorised as
     * Board Wipes, as a CompletableFuture.
     * @return a CompletableFuture containing a list of all board-wipe Cards
     * in the database.
     */
    public CompletableFuture<List<Card>> getBoardWipesByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {
                return cardDAO.getBoardWipes();
            }
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a specific Card as a CompletableFuture.
     * @param cardID an Integer containing the card ID to be returned.
     * @return a CompletableFuture containing the specified Card.
     */
    public CompletableFuture<Card> getCardByID(int cardID) {
        return CompletableFuture.supplyAsync(new Supplier<Card>() {
            @Override
            public Card get() {return cardDAO.getCardByID(cardID);}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This is the Accessor method for the cardDAO field.
     * @return a CardDAO object used to access the Card Entity.
     */
    public CardDAO getCardDAO() {
        return cardDAO;
    }

    /**
     * This method returns a specific Deck as a CompletableFuture.
     * @param deckID an Integer containing the deck ID number to be returned.
     * @return a CompletableFuture containing the requested Deck.
     */
    public CompletableFuture<Deck> getDeck(int deckID) {
        return CompletableFuture.supplyAsync(new Supplier<Deck>() {
            @Override
            public Deck get() {return deckDAO.getDeck(deckID);}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a List of DeckCards for a specific Deck, as a
     * CompletableFuture.
     * @param deckID an Integer containing the deck ID number to be returned.
     * @return a CompletableFuture containing a List of DeckCards for the
     * specified Deck.
     */
    public CompletableFuture<List<DeckCards>> getDeckCards(int deckID) {
        return CompletableFuture.supplyAsync(new Supplier<List<DeckCards>>() {
            @Override
            public List<DeckCards> get() {return deckCardDAO.getDeckCardQuantities(deckID);}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This is the Accessor method for the deckCardDAO field.
     * @return a DeckCardDAO object used to access the DeckCards Entity.
     */
    public DeckCardDAO getDeckCardDAO() {
        return deckCardDAO;
    }

    /**
     * This is the Accessor method for the deckDAO field.
     * @return a DeckDAO object used to access the Deck Entity.
     */
    public DeckDAO getDeckDAO() {
        return deckDAO;
    }

    /**
     * This method returns a list of Cards categorised as 'Draw' cards from
     * the database, as a CompletableFuture.
     * @return a CompletableFuture containing a List of 'Draw' Cards from
     * the database.
     */
    public CompletableFuture<List<Card>> getDrawCardsByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {
                return cardDAO.getDrawCards();
            }
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a list of Cards from the database matching a
     * specific colour identity, as a CompletableFuture.
     * @param colourIdentity a String containing the desired colour identity.
     * @return a CompletableFuture containing a List of Cards matching the
     * specified colour identity.
     */
    public CompletableFuture<List<Card>> getFutureCardsByColourIdentity (String colourIdentity) {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {return cardDAO.getFutureCardsByColourIdentity(colourIdentity);}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a list of all colourless cards in the database, as
     * a CompletableFuture.
     * @return a CompletableFuture containing a List of Cards which are colourless
     * colour identity.
     */
    public CompletableFuture<List<Card>> getFutureColourlessCards() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {
                return cardDAO.getFutureColourlessCards();
            }
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns the latest Deck to be inserted into the database, as
     * a CompletableFuture.
     * @return a CompletableFuture containing the latest Deck inserted into the
     * database.
     */
    public CompletableFuture<Deck> getLatestDeck() {
        return CompletableFuture.supplyAsync(new Supplier<Deck>() {
            @Override
            public Deck get() {return deckDAO.getLatestDeck();}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a specific Deck from the database, as a LiveData.
     * @param deckID an Integer containing the deck ID number to be requested.
     * @return a LiveData Deck which is the requested Deck.
     */
    public LiveData<Deck> getLiveDeck(int deckID) {
        return deckDAO.getLiveDeck(deckID);
    }

    /**
     * This method returns a List of DeckCards for a specific Deck from the
     * database, as a LiveData.
     * @param deckID an Integer containing the deck ID number to be requested.
     * @return a LiveData List of DeckCards for the specified Deck.
     */
    public LiveData<List<DeckCards>> getLiveDeckCards(int deckID) {
        return deckCardDAO.getLiveDeckCards(deckID);
    }

    /**
     * This method returns a list of Cards categorised as 'Ramp' from
     * the database.
     * @return a CompletableFuture containing a List of Cards categorised
     * as 'Ramp'.
     */
    public CompletableFuture<List<Card>> getRampCardsByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {
                return cardDAO.getRampCards();
            }
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a list of Cards categorised as 'Removal' from
     * the database.
     * @return a CompletableFuture containing a List of Cards categorised
     * as 'Removal'.
     */
    public CompletableFuture<List<Card>> getRemovalCardsByFuture() {
        return CompletableFuture.supplyAsync(new Supplier<List<Card>>() {
            @Override
            public List<Card> get() {
                return cardDAO.getRemovalCards();
            }
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method returns a specific DeckCards from the database, as a
     * CompletableFuture.
     * @param deckID an Integer containing the deck ID number to be returned.
     * @param cardID an Integer containing the card ID number to be returned.
     * @return a CompletableFuture containing the requested DeckCards.
     */
    public CompletableFuture<DeckCards> getSpecificDeckCards(int deckID, int cardID) {
        return CompletableFuture.supplyAsync(new Supplier<DeckCards>() {
            @Override
            public DeckCards get() {return deckCardDAO.getSpecificDeckCards(deckID, cardID);}
        }, CardDatabase.databaseWriteExecutor);
    }

    /**
     * This method inserts a Deck into the database.
     * @param deck a Deck to be inserted.
     */
    public void insertDeck(Deck deck) {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { deckDAO.insertDeck(deck);}
        });
    }

    /**
     * This method inserts a new DeckCards into the database.
     * @param deckCards a DeckCards to be inserted.
     */
    public void insertDeckCards(DeckCards deckCards) {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {deckCardDAO.insertDeckCards(deckCards);}
        });
    }

    /**
     * This is the Mutator method for the cardDAO field.
     * @param cardDAO a CardDAO object used to access the Card Entity.
     */
    public void setCardDAO(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    /**
     * This is the Mutator method for the deckDAO field.
     * @param deckDAO a DeckDAO object used to access the Deck Entity.
     */
    public void setDeckDAO(DeckDAO deckDAO) {
        this.deckDAO = deckDAO;
    }

    /**
     * This is the Mutator method for the deckCardDAO field.
     * @param deckCardDAO a DeckCardDAO object used to access the DeckCards Entity.
     */
    public void setDeckCardDAO(DeckCardDAO deckCardDAO) {
        this.deckCardDAO = deckCardDAO;
    }

    /**
     * This method removes specific DeckCards from the database.
     * @param deckID an Integer containing the deckID of the DeckCards to
     *               be removed.
     * @param cardID an Integer containing the cardID of the DeckCards to
     *               be removed.
     */
    public void removeSpecificDeckCards(int deckID, int cardID) {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {deckCardDAO.removeSpecificDeckCards(deckID, cardID);}
        });
    }

    /**
     * This method updates a particular Deck.
     * @param deck a Deck to be updated.
     */
    public void updateDeck(Deck deck) {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { deckDAO.updateDeck(deck);}
        });
    }

    /**
     * This method wipes the entire database - used mostly for debugging.
     */
    public void wipeDatabase() {
        CardDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardDAO.deleteAllCards();
                deckDAO.deleteAllDecks();
                deckCardDAO.deleteAllDeckCards();
            }
        });
    }
}