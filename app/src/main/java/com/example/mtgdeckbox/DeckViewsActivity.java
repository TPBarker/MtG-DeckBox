package com.example.mtgdeckbox;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mtgdeckbox.databinding.DeckViewsFragmentBinding;
import com.example.mtgdeckbox.fragment.adapters.DeckViewsTabsAdapter;
import com.example.mtgdeckbox.fragment.cardImageFragment;
import com.example.mtgdeckbox.fragment.cardPickerFragment;
import com.example.mtgdeckbox.fragment.commanderPickerFragment;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.Deck;
import com.example.mtgdeckbox.room.DeckCards;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the DeckViewsActivity, which hosts the fragments which contain the
 * functionality for the User to create, read, update and delete the contents
 * of the decks they create.
 * @author: Tom Barker
 */
public class DeckViewsActivity extends AppCompatActivity
    implements commanderPickerFragment.OnInputListener,
        cardPickerFragment.OnInputListener {
    private DeckViewsFragmentBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private CardViewModel viewModel;
    private Deck currentDeck;
    private Toasty toastMaker;
    private List<Card> deckContents;

    /**
     * This is the default constructor.
     */
    public DeckViewsActivity() {}

    /**
     * This method performs some data updates on demand for the rest of the
     * Activity.
     */
    public void collectLatestData() {
        /* If no name has been set for the deck yet, set the name of the deck
         * to be the name of the commander card.
         */
        if (currentDeck.getDeckName().isEmpty()) {
            try {
                binding.editTextDeckName.setText(
                        viewModel.getCardByID(currentDeck.getCommanderID()).get().getName());
            }
            catch (Exception e) {
                Log.d("DB ERROR:", "Could not execute query!");
            }
        }
        currentDeck.setDeckName(binding.editTextDeckName.getText().toString());
        viewModel.updateDeck(currentDeck);

        // Refresh deck contents.
        try {
            List<DeckCards> deckCards = viewModel.getDeckCards(currentDeck.getDeckID()).get();
            deckContents = new ArrayList<Card>();
            for (DeckCards entry : deckCards) {
                deckContents.add(viewModel.getCardByID(entry.getCardID()).get());
            }
        }
        catch (Exception e){
            Log.d("DB ERROR:", "Could not execute query!");
        }
    }

    /**
     * This is the Accessor method for the binding field.
     * @return a DeckViewsFragmentBinding object which is used to bind all of
     * the views in the Activity.
     */
    public DeckViewsFragmentBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the tabLayout field.
     * @return a TabLayout object which is used to display the tabs.
     */
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    /**
     * This is the Accessor method for the viewPager field.
     * @return a ViewPager2 object which is used to display the tab contents.
     */
    public ViewPager2 getViewPager() {
        return viewPager;
    }

    /**
     * This is the Accessor method for the viewModel field.
     * @return a CardViewModel object which is used to direct queries to the
     * SQL database.
     */
    public CardViewModel getViewModel() {
        return viewModel;
    }

    /**
     * This is the Accessor method for the currentDeck field.
     * @return a Deck object which contains the deck currently being
     * edited / viewed.
     */
    public Deck getCurrentDeck() {
        return currentDeck;
    }

    /**
     * This is the Accessor method for the toastMaker field.
     * @return a Toasty object which is used to create Toasts in this Activity.
     */
    public Toasty getToastMaker() {
        return toastMaker;
    }

    /**
     * This is the Accessor method for the deckContents field.
     * @return a List of Cards which represents the current contents of the deck.
     */
    public List<Card> getDeckContents() {
        return deckContents;
    }

    /**
     * This method runs when the Activity is launched.
     * @param savedInstanceState a Bundle which contains the saved instance state.
     *                           The Activity can may restore itself to a saved
     *                           state under special circumstances.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialise some fields and setup the Activity.
        super.onCreate(savedInstanceState);
        binding = DeckViewsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        tabLayout = binding.tabLayoutDeck;
        viewPager = binding.viewPagerTabs;
        toastMaker = new Toasty(this);

        /* Initialise the CardViewModel which grants us access to the Android
         * Room SQL database.
         */
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getApplication())
                .create(CardViewModel.class);

        /* Load the contents of the Deck we were passed from the deckListFragment.
         * If this is a new deck, it should be created, ready to be written to the DB.
         */
        int deckID = getIntent().getExtras().getInt("deckID");

        if (deckID == -1) {
            /* If this is true, then the deckListFragment did not finish writing
             * a new deck before it passed us the deckID. We should check the latest
             * deck now.
             */
            try {
                deckID = viewModel.getLatestDeck().get().getDeckID();
            }
            catch (Exception e) {
                Log.d("DB ERROR:", "Could not execute query!");
            }
        }

        // Retrieve the deck we are editing.
        try {
            currentDeck = viewModel.getDeck(deckID).get();
            List<DeckCards> deckCards = viewModel.getDeckCards(deckID).get();
            deckContents = new ArrayList<Card>();
            for (DeckCards entry : deckCards) {
                deckContents.add(viewModel.getCardByID(entry.getCardID()).get());
            }
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }

        // Setup an Observer which will set the commander and deck name.
        Observer<Deck> deckObserver = new Observer<Deck>() {
            @Override
            public void onChanged(@Nullable Deck deck) {
                try {
                    binding.textViewCommanderName.setText(
                            viewModel.getCardByID(deck.getCommanderID()).get().getName());
                    binding.editTextDeckName.setText(
                            deck.getDeckName());
                }
                catch (NullPointerException e) {
                    Log.d("ERROR", "Deck was null when observing.");
                }
                catch (Exception e) {
                    Log.d("DB ERROR:", "Could not execute query!");
                }
            }
        };

        // Setup an Observer to recreate the list of cards when it is updated.
        Observer<List<DeckCards>> contentsObserver = new Observer<List<DeckCards>>() {
            @Override
            public void onChanged(@Nullable List<DeckCards> deckCards) {
                try {
                    // Use the adapter for the tab view.
                    final DeckViewsTabsAdapter tabsAdapter = new DeckViewsTabsAdapter(
                            getSupportFragmentManager(), getLifecycle(), currentDeck.getDeckID());

                    viewPager.setAdapter(tabsAdapter);
                }
                catch (NullPointerException e) {
                    Log.d("ERROR", "Deck was null when observing.");
                }
                catch (Exception e) {
                    Log.d("DB ERROR:", "Could not execute query!");
                }
            }
        };

        viewModel.getLiveDeck(deckID).observe(this, deckObserver);
        viewModel.getLiveDeckCards(deckID).observe(this, contentsObserver);

        // Create the tabs for the tab view.
        tabLayout.addTab(tabLayout.newTab().setText("Deck Builder"));
        tabLayout.addTab(tabLayout.newTab().setText("Suggestions"));
        tabLayout.addTab(tabLayout.newTab().setText("Graphs"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Add the ability to page between each of the tabs.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Set the behaviour for the choose commander button.
        binding.buttonChooseCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a commander picker fragment with a list of choosable commander cards.
                commanderPickerFragment commanderPicker = new commanderPickerFragment();
                commanderPicker.show(getSupportFragmentManager(), "Commander Picker");
            }
        });

        // Set the behaviour for the save button.
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write the deck to the DB.
                collectLatestData();
                toastMaker.popToastShort("Deck saved to database!");
            }
        });

        // Enable the user to check their commander card.
        binding.textViewCommanderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Card commander = viewModel.getCardByID(currentDeck.getCommanderID()).get();
                    cardImageFragment cardImage = new cardImageFragment(commander);
                    cardImage.show(getSupportFragmentManager(), commander.getName());
                }
                catch (Exception e) {
                    Log.d("DB ERROR:", "Could not execute query!");
                }
            }
        });
    }

    /**
     * This method listens for data being sent from the commanderPicker fragment
     * and processes it.
     * @param chosenCard a Card which is the commander Card which the user has
     *                   picked in the commanderPicker fragment.
     */
    @Override
    public void sendInput(Card chosenCard) {
        // Set the chosen card as the deck commander.
        int cardID = chosenCard.getCardID();
        currentDeck.setCommanderID(cardID);
        collectLatestData();
    }

    /**
     * This method listens for data being sent from the cardPicker fragment
     * and processes it.
     * @param chosenCards an ArrayList of Cards which contains the Cards which
     *                    the user has picked in the cardPicker fragment.
     */
    @Override
    public void sendInput(ArrayList<Card> chosenCards) {
        // Receive the list of chosen Cards from the Card Picker.

        /* For each Card selected by the user, check to see if it is already in
         * in the deck. It will not be added.
         */
        ArrayList<Card> successfulSelections = new ArrayList<Card>();
        for (Card chosenCard : chosenCards) {
            // Check to see if this card is already in the deck.
            boolean duplicate = false;
            for (Card card : deckContents) {
                if (chosenCard.getCardID() == card.getCardID()) {
                    // Duplicate entry detected.
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                successfulSelections.add(chosenCard);
            }
        }

        // The remaining cards are new entries. Add them to the deck.
        for (Card card : successfulSelections) {
            viewModel.insertDeckCards(new DeckCards(
                    currentDeck.getDeckID(), card.getCardID(), 1));
        }
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding a DeckViewsFragmentBinding object which will be used to
     *                bind all of the views in the Activity.
     */
    public void setBinding(DeckViewsFragmentBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the tabLayout field.
     * @param tabLayout a TabLayout object which will be used to set the tabs.
     */
    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    /**
     * This is the Mutator method for the viewPager field.
     * @param viewPager a ViewPager2 object which will be used to display
     *                  the tabs.
     */
    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }

    /**
     * This is the Mutator method for the viewModel field.
     * @param viewModel a CardViewModel object which will be used to send queries
     *                  to the SQL database.
     */
    public void setViewModel(CardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * This is the Mutator method for the currentDeck field.
     * @param currentDeck a Deck object which contains the Deck currently
     *                    being edited.
     */
    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    /**
     * This is the Mutator method for the toastMaker field.
     * @param toastMaker a Toasty object which will be used to create Toasts
     *                   for the Activity.
     */
    public void setToastMaker(Toasty toastMaker) {
        this.toastMaker = toastMaker;
    }

    /**
     * This is the Mutator method for the deckContents field.
     * @param deckContents a List of Cards which contains the current contents
     *                     of the deck being edited.
     */
    public void setDeckContents(List<Card> deckContents) {
        this.deckContents = deckContents;
    }
}
