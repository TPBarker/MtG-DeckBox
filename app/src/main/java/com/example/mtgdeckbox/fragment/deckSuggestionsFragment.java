package com.example.mtgdeckbox.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtgdeckbox.databinding.FragmentDeckSuggestionsBinding;
import com.example.mtgdeckbox.fragment.adapters.DeckContentsAdapter;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.DeckCards;

import java.util.ArrayList;
import java.util.List;

/**
 * This Fragment provides suggestions for particular Cards for the User to
 * include in their Deck.
 * @author: Tom Barker
 */
public class deckSuggestionsFragment extends Fragment {
    private FragmentDeckSuggestionsBinding binding;
    private final int deckID;
    private CardViewModel viewModel;
    private List<Card> deckContents;

    /**
     * This is the default constructor.
     */
    public deckSuggestionsFragment() {
        this.deckID = -1;
    }

    /**
     * This is the non-default constructor.
     * @param deckID an Integer containing the ID number of the Deck
     *               being edited.
     */
    public deckSuggestionsFragment(int deckID) {
        this.deckID = deckID;
        deckContents = new ArrayList<Card>();
        }

    /**
     * This is the Accessor method for the binding field.
     * @return a FragmentDeckSuggestionsBinding which is used to bind data to
     * all of the Views.
     */
    public FragmentDeckSuggestionsBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer containing the ID number of the Deck currently being
     * edited.
     */
    public int getDeckID() {
        return deckID;
    }

    /**
     * This is the Accessor method for the viewModel field.
     * @return a CardViewModel which allows access to the Room database.
     */
    public CardViewModel getViewModel() {
        return viewModel;
    }

    /**
     * This is the Accessor method for the deckContents field.
     * @return a List of Cards which contains all the Cards currently
     * in this Deck.
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
        binding = FragmentDeckSuggestionsBinding.inflate(inflater, container, false);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication())
                .create(CardViewModel.class);

        // Setup an Observer which can track the quantities and update them for us.
        Observer<List<DeckCards>> myObserver = new Observer<List<DeckCards>>() {
            @Override
            public void onChanged(@Nullable List<DeckCards> deckCards) {
                // To track the contents of the deck, re-populate the ArrayList of contents.
                try {
                    List<DeckCards> currentDeck = viewModel.getDeckCards(deckID).get();
                    deckContents.clear();
                    for (DeckCards deckCard : currentDeck) {
                        deckContents.add(viewModel.getCardByID(deckCard.getCardID()).get());
                    }
                }
                catch (Exception e) {
                    Log.d("DB ERROR:", "Could not execute query!");
                }

                // Count the number of cards in each category currently in the deck.
                int ramp = 0;
                int wipe = 0;
                int draw = 0;
                int removal = 0;

                for (Card card : deckContents) {
                    String categories = card.getCategories();
                    if (categories.contains("cardraw")) {
                        draw++;
                    }
                    if (categories.contains("wrath")) {
                        wipe++;
                    }
                    if (categories.contains("removal")) {
                        removal++;
                    }
                    if (categories.contains("ramp") || categories.contains("mana")) {
                        ramp++;
                    }
                }

                // Set the quantities to the display.
                binding.textViewRampCurrent.setText(String.valueOf(ramp));
                binding.textViewDrawCurrent.setText(String.valueOf(draw));
                binding.textViewRemovalCurrent.setText(String.valueOf(removal));
                binding.textViewWipeCurrent.setText(String.valueOf(wipe));
            }
        };

        viewModel.getLiveDeckCards(deckID).observe(getViewLifecycleOwner(), myObserver);

        // Set the behaviour for each of the buttons.
        binding.buttonRamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardPickerFragment cardPicker = new cardPickerFragment(deckID, "ramp");
                cardPicker.show(getActivity().getSupportFragmentManager(), "Card Picker");
            }
        });

        binding.buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardPickerFragment cardPicker = new cardPickerFragment(deckID, "draw");
                cardPicker.show(getActivity().getSupportFragmentManager(), "Card Picker");
            }
        });

        binding.buttonRemoval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardPickerFragment cardPicker = new cardPickerFragment(deckID, "removal");
                cardPicker.show(getActivity().getSupportFragmentManager(), "Card Picker");
            }
        });

        binding.buttonWipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardPickerFragment cardPicker = new cardPickerFragment(deckID, "wipe");
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
     * @param binding a FragmentDeckSuggestionsBinding which will be used to bind
     *                data to all of the Views.
     */
    public void setBinding(FragmentDeckSuggestionsBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the viewModel field.
     * @param viewModel a CardViewModel object which provides access to the
     *                  Android Room database.
     */
    public void setViewModel(CardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * This is the Mutator method for the deckContents field.
     * @param deckContents a List of Cards containing all the Cards currently
     *                     in this Deck.
     */
    public void setDeckContents(List<Card> deckContents) {
        this.deckContents = deckContents;
    }
}