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

import com.example.mtgdeckbox.databinding.FragmentDeckGraphsBinding;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.DeckCards;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Fragment displays graphs to the User to give them visual feedback
 * about their Deck.
 */
public class deckGraphsFragment extends Fragment {
    private FragmentDeckGraphsBinding binding;
    private CardViewModel viewModel;
    private final int deckID;
    private List<Card> deckContents;

    /**
     * This is the default constructor.
     */
    public deckGraphsFragment() {
        this.deckID = -1;
    }

    /**
     * This is the non-default constructor.
     * @param deckID an Integer containing the ID number of the Deck
     *               being edited.
     */
    public deckGraphsFragment(int deckID) {
        this.deckID = deckID;
        deckContents = new ArrayList<Card>();
    }

    /**
     * This is the Accessor method for the binding field.
     * @return a FragmentDeckGraphsBinding object which is used to bind data
     * to all of the Views.
     */
    public FragmentDeckGraphsBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the viewModel field.
     * @return a CardViewModel object which provides access to the Android
     * Room database.
     */
    public CardViewModel getViewModel() {
        return viewModel;
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer containing the ID number of the Deck being edited.
     */
    public int getDeckID() {
        return deckID;
    }

    /**
     * This is the Accessor method for the deckContents field.
     * @return a List of Cards containing the Cards in the current Deck.
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
        binding = FragmentDeckGraphsBinding.inflate(inflater, container, false);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication())
                .create(CardViewModel.class);

        // Setup an Observer, to create and refresh bar graph data.
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

                // Setup some helper variables.
                int column0 = 0;
                int column1 = 0;
                int column2 = 0;
                int column3 = 0;
                int column4 = 0;
                int column5 = 0;
                int column6 = 0;
                int column7 = 0;
                int column8 = 0;
                int column9 = 0;
                int column10 = 0;
                int column10plus = 0;

                for (Card card : deckContents) {
                    switch (card.getManaValue()) {
                        case 0:
                            column0++;
                            break;
                        case 1:
                            column1++;
                            break;
                        case 2:
                            column2++;
                            break;
                        case 3:
                            column3++;
                            break;
                        case 4:
                            column4++;
                            break;
                        case 5:
                            column5++;
                            break;
                        case 6:
                            column6++;
                            break;
                        case 7:
                            column7++;
                            break;
                        case 8:
                            column8++;
                            break;
                        case 9:
                            column9++;
                            break;
                        case 10:
                            column10++;
                            break;
                        default:
                            column10plus++;
                            break;
                    }
                }

                // Create and set our bar chart.
                List<BarEntry> barEntries = new ArrayList<>();
                barEntries.add(new BarEntry(0, column0));
                barEntries.add(new BarEntry(1, column1));
                barEntries.add(new BarEntry(2, column2));
                barEntries.add(new BarEntry(3, column3));
                barEntries.add(new BarEntry(4, column4));
                barEntries.add(new BarEntry(5, column5));
                barEntries.add(new BarEntry(6, column6));
                barEntries.add(new BarEntry(7, column7));
                barEntries.add(new BarEntry(8, column8));
                barEntries.add(new BarEntry(9, column9));
                barEntries.add(new BarEntry(10, column10));
                barEntries.add(new BarEntry(11, column10plus));

                BarDataSet barDataSet = new BarDataSet(barEntries, "Count of Cards");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                List<String> xAxisValues = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4",
                        "5", "6", "7", "8", "9", "10", "10-plus"));
                binding.barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.
                        formatter.IndexAxisValueFormatter(xAxisValues));
                BarData barData = new BarData(barDataSet);
                binding.barChart.setData(barData);

                barData.setBarWidth(1.0f);
                binding.barChart.setVisibility(View.VISIBLE);
                binding.barChart.animateY(4000);

                // Provide a description label.
                Description description = new Description();
                description.setText("Count of Cards in Deck by Mana Value");
                binding.barChart.setDescription(description);

                // Refresh the chart.
                binding.barChart.invalidate();
            }
        };

        viewModel.getLiveDeckCards(deckID).observe(getViewLifecycleOwner(), myObserver);

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
     * @param binding a FragmentDeckGraphsBinding object which will be used to
     *                bind data to all of the Views.
     */
    public void setBinding(FragmentDeckGraphsBinding binding) {
        this.binding = binding;
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
     * This is the Mutator method for the deckContents field.
     * @param deckContents a List of Cards containing the Cards in the Deck
     *                     currently being edited.
     */
    public void setDeckContents(List<Card> deckContents) {
        this.deckContents = deckContents;
    }
}