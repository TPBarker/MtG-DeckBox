package com.example.mtgdeckbox.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class contains the Android Room SQL database for the application.
 * @author: Tom Barker
 */
@Database(entities = {Card.class, Deck.class, DeckCards.class},
            version = 1, exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {

    public abstract CardDAO cardDAO();
    public abstract DeckDAO deckDAO();
    public abstract DeckCardDAO quantities();

    private static CardDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized CardDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    CardDatabase.class, "CardDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
