package com.example.mtgdeckbox.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtgdeckbox.databinding.FragmentDeckContentsBinding;
import com.example.mtgdeckbox.fragment.adapters.DeckContentsAdapter;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.DeckCards;

import java.util.ArrayList;
import java.util.List;

/**
 * This Fragment contains a RecyclerView which displays all of the Cards in
 * the Deck currently being edited.
 * @author: Tom Barker
 */
public class deckContentsFragment extends Fragment {
    private FragmentDeckContentsBinding binding;
    private DeckContentsAdapter adapter;
    private CardViewModel viewModel;
    private RecyclerView.LayoutManager layoutManager;
    private final int deckID;
    private List<Card> deckContents;

    /**
     * This is the default constructor.
     */
    public deckContentsFragment() {
        this.deckID = -1;
    }

    /**
     * This is the non-default constructor.
     * @param deckID an Integer containing the ID number of the Deck currently
     *               being edited.
     */
    public deckContentsFragment(int deckID) {
        this.deckID = deckID;
        deckContents = new ArrayList<Card>();
    }

    /**
     * This is the Accessor method for the binding field.
     * @return a FragmentDeckContentsBinding object which is used to bind data
     * to all of the Views.
     */
    public FragmentDeckContentsBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the adapter field.
     * @return a DeckContentsAdapter object which provides data for the
     * RecyclerView.
     */
    public DeckContentsAdapter getAdapter() {
        return adapter;
    }

    /**
     * This is the Accessor method for the viewModel field.
     * @return a CardViewModel which will provide access to the Android Room
     * database.
     */
    public CardViewModel getViewModel() {
        return viewModel;
    }

    /**
     * This is the Accessor method for the layoutManager field.
     * @return a RecyclerView.LayoutManager object used to help manage
     * the RecyclerView.
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer containing the ID number of the Deck currently
     * being edited.
     */
    public int getDeckID() {
        return deckID;
    }

    /**
     * This is the Accessor method for the deckContents field.
     * @return a List of Cards containing all the Cards in the Deck.
     */
    public List<Card> getDeckContents() {
        return deckContents;
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
        binding = FragmentDeckContentsBinding.inflate(inflater, container, false);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication())
                .create(CardViewModel.class);

        // To track the contents of the deck, setup the ArrayList of contents.
        try {
            List<DeckCards> currentDeck = viewModel.getDeckCards(deckID).get();
            for (DeckCards deckCard : currentDeck) {
                deckContents.add(viewModel.getCardByID(deckCard.getCardID()).get());
            }
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }

        // Setup the RecyclerView to display the deck contents.
        adapter = new DeckContentsAdapter(deckID, deckContents, viewModel, getActivity());

        // Setup an Observer to refresh the contents of the Deck when it is edited.
        Observer<List<DeckCards>> myObserver = new Observer<List<DeckCards>>() {
            @Override
            public void onChanged(@Nullable List<DeckCards> deckCards) {
                try {
                    adapter = new DeckContentsAdapter(deckID, deckContents, viewModel, getActivity());
                    binding.recyclerViewDeckContents.setAdapter(adapter);
                }
                catch (NullPointerException e) {
                    Log.d("ERROR", "Deck was null when observing.");
                }
                catch (Exception e) {
                    Log.d("DB ERROR:", "Could not execute query!");
                }
            }
        };

        viewModel.getLiveDeckCards(deckID).observe(getViewLifecycleOwner(), myObserver);

        // Create a line divider between items
        binding.recyclerViewDeckContents.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        binding.recyclerViewDeckContents.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewDeckContents.setLayoutManager(layoutManager);

        // Set the behaviour for the floating action button.
        binding.fabAddCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardPickerFragment cardPicker = new cardPickerFragment(deckID, "");
                cardPicker.show(getActivity().getSupportFragmentManager(), "Card Picker");
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
     * @param binding a FragmentDeckContentsBinding object which will be used to
     *                bind data to all of the Views.
     */
    public void setBinding(FragmentDeckContentsBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the adapter field.
     * @param adapter a DeckContentsAdapter object which will provide data for
     *                the RecyclerView.
     */
    public void setAdapter(DeckContentsAdapter adapter) {
        this.adapter = adapter;
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
     * This is the Mutator method for the layoutManager field.
     * @param layoutManager a RecyclerView.LayoutManager object which will be used
     *                      to help manager the RecyclerView.
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    /**
     * This is the Mutator method for the deckContents field.
     * @param deckContents a List of Cards which contains the Cards in the Deck
     *                     currently being edited.
     */
    public void setDeckContents(List<Card> deckContents) {
        this.deckContents = deckContents;
    }
}