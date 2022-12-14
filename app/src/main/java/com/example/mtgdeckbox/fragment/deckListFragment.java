package com.example.mtgdeckbox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgdeckbox.CSVReader;
import com.example.mtgdeckbox.DeckViewsActivity;
import com.example.mtgdeckbox.databinding.DecklistFragmentBinding;
import com.example.mtgdeckbox.fragment.adapters.DecklistAdapter;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * This Fragment contains a RecyclerView list of all the Decks in the database.
 * @author: Tom Barker
 */
public class deckListFragment extends Fragment {
    private DecklistFragmentBinding binding;
    private DecklistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardViewModel viewModel;
    private CSVReader reader;

    /**
     * This is the default constructor.
     */
    public deckListFragment(){}

    /**
     * This is the Accessor method for the binding field.
     * @return a DecklistFragmentBinding which is used to bind data to all
     * of the Views.
     */
    public DecklistFragmentBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the adapter field.
     * @return a DecklistAdapter which is used to populate the RecyclerView.
     */
    public DecklistAdapter getAdapter() {
        return adapter;
    }

    /**
     * This is the Accessor method for the layoutManager field.
     * @return a RecyclerView.LayoutManager which is used to help manage
     * the RecyclerView.
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * This is the Accessor method for the viewModel field.
     * @return a CardViewModel which provides access to the Android Room
     * database.
     */
    public CardViewModel getViewModel() {
        return viewModel;
    }

    /**
     * This is the Accessor method for the reader field.
     * @return a CSVReader object which is used to populate data into the Android
     * Room database from a supplied CSV file.
     */
    public CSVReader getReader() {
        return reader;
    }

    /**
     * This method first wipes the database clean, and then imports all of the
     * Card data from the CSV file and inserts it into the database.
     */
    public void importData() {
        viewModel.wipeDatabase();
        reader = new CSVReader(getContext());
        ArrayList<ArrayList<String>> cardData = reader.readCSV("final_data_set.csv");
        for (int i = 0; i < cardData.size(); i++) {
            String cardName = cardData.get(i).get(0);
            viewModel.addCard(new Card(cardData.get(i)));
        }
    }

    /**
     * This method initialises the view and binds appropriate data, when the fragment
     * is created.
     * @param inflater a LayoutInflater used to inflate all of the Views.
     * @param container a ViewGroup which contains all of the Views.
     * @param savedInstanceState a Bundle containing the saved instance state, which
     *                           the Application can use to restore to, if needed.
     * @return a View containing the graphical interface of the Fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DecklistFragmentBinding.inflate(inflater, container, false);

        // Get the ViewModel which allows us to access our SQL data.
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication())
                .create(CardViewModel.class);

        /* Since this is the first point in the application when the DB is opened,
         * check to see if any cards have been loaded yet. If not, read them from
         * the CSV file.
         */
        List<Card> contents = new ArrayList<Card>();
        try {
            contents = viewModel.getAllCardsByFuture().get();
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }

        if (contents.isEmpty())
        {
            importData();
        }

        // Setup the RecyclerView to display the list of decks.
        adapter = new DecklistAdapter(viewModel);

        // Create a line divider between items
        binding.recyclerViewDeckList.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        binding.recyclerViewDeckList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewDeckList.setLayoutManager(layoutManager);

        // Add an on-click listener for the floating action button.
        binding.fabAddDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Add a new deck to the DB and send it to the DeckViewsActivity
                 * for editing.
                 */
                Intent newDeckIntent = new Intent(getContext(), DeckViewsActivity.class);
                Bundle deck = new Bundle();
                viewModel.insertDeck(new Deck());

                int deckID = -1;
                try {
                    deckID = viewModel.getLatestDeck().get().getDeckID();
                }
                catch (Exception e) {
                    Log.d("DB ERROR:", "Could not execute query!");
                }

                deck.putInt("deckID", deckID);
                newDeckIntent.putExtras(deck);
                startActivity(newDeckIntent);
            }
        });
        return binding.getRoot();
    }

    /**
     * This method clears some objects from memory and resets the binding,
     * when the Fragment is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding a DecklistFragmentBinding object which is used to bind
     *                data into all of the Views.
     */
    public void setBinding(DecklistFragmentBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the adapter field.
     * @param adapter a DecklistAdapter object which provides data for the
     *                RecyclerView.
     */
    public void setAdapter(DecklistAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * This is the Mutator method for the layoutManager field.
     * @param layoutManager a RecyclerView.LayoutManager object which will
     *                      help manage the RecyclerView.
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    /**
     * This is the Mutator method for the viewModel field.
     * @param viewModel a CardViewModel object which will provide access to the
     *                  Android Room database.
     */
    public void setViewModel(CardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * This is the Mutator method for the reader field.
     * @param reader a CSVReader object which will be used to read in data
     *               from a CSV file.
     */
    public void setReader(CSVReader reader) {
        this.reader = reader;
    }
}
