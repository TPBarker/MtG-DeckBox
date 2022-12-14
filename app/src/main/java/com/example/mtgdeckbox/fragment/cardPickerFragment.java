package com.example.mtgdeckbox.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgdeckbox.fragment.adapters.CardPickerAdapter;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.databinding.FragmentCardPickerBinding;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.Deck;
import com.example.mtgdeckbox.room.DeckCards;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This Fragment allows the user to choose Cards to add into their Deck.
 * @author: Tom Barker
 */
public class cardPickerFragment extends DialogFragment {
    private FragmentCardPickerBinding binding;
    private CardPickerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardViewModel viewModel;
    private final int deckID;
    private OnInputListener myInputListener;
    private String type;

    /**
     * This is the default constructor.
     */
    public cardPickerFragment() {
        this.deckID = -1;
    }

    /**
     * This is the non-default constructor.
     * @param deckID an Integer containing the ID number of the Deck being edited.
     * @param type a String which tells the Fragment which kind of list of
     *             Cards to display to the user.
     */
    public cardPickerFragment(int deckID, String type){
        this.deckID = deckID;
        this.type = type;
    }

    /**
     * This is the Accessor method for the binding field.
     * @return a FragmentCardPickerBinding object which is used to bind data
     * to all of the Views.
     */
    public FragmentCardPickerBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the adapter field.
     * @return a CardPickerAdapter which provides data for the RecyclerView.
     */
    public CardPickerAdapter getAdapter() {
        return adapter;
    }

    /**
     * This is the Accessor method for the layoutManager field.
     * @return a RecyclerView.LayoutManager object which is used to help
     * manage the RecyclerView.
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * This is the Accessor method for the viewModel field.
     * @return a CardViewModel object which provides access to the Android Room
     * database.
     */
    public CardViewModel getViewModel() {
        return viewModel;
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
     * This is the Accessor method for the myInputListener field.
     * @return an OnInputListener object which is used to send data back to
     * the parent Fragment.
     */
    public OnInputListener getMyInputListener() {
        return myInputListener;
    }

    /**
     * This is the Accessor method for the type field.
     * @return a String will tells the Fragment what kind of data to display
     * to the user.
     */
    public String getType() {
        return type;
    }

    /**
     * This method runs when the Fragment is displayed, and attaches an
     * InputListener so we can send data back to the parent Fragment.
     * @param context a Context which helps the Fragment send data back
     *                to the parent Fragment.
     */
    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        try {
            myInputListener = (OnInputListener)getActivity();
        }
        catch (Exception e) {
            Log.d("ERROR!", "Could not attach the Input Listener!");
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
        binding = FragmentCardPickerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.setCancelable(false);

        // Setup the RecyclerView to display the deck contents.
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                getActivity().getApplication())
                .create(CardViewModel.class);

        /* Retrieve a list of cards to choose from to add to this deck.
         * This will vary depending on the type of list we have been
          * asked to display.
         */
        CompletableFuture<List<Card>> cardList = null;
        try {

            switch (type) {
                case "ramp":
                    cardList = viewModel.getRampCardsByFuture();
                    break;
                case "draw":
                    cardList = viewModel.getDrawCardsByFuture();
                    break;
                case "removal":
                    cardList = viewModel.getRemovalCardsByFuture();
                    break;
                case "wipe":
                    cardList = viewModel.getBoardWipesByFuture();
                    break;
                default:
                    cardList = viewModel.getAllCardsByFuture();
                    break;
            }
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }

        // Create the adapter based on the retrieved list of cards.
        try {
            adapter = new CardPickerAdapter(cardList.get());
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }

        // Create a line divider between items
        binding.recyclerViewCardPicker.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        binding.recyclerViewCardPicker.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewCardPicker.setLayoutManager(layoutManager);

        // Set the behaviour for the cancel button.
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Set the behaviour for the add to deck button.
        binding.buttonAddToDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Card> chosenCards = adapter.getSelections();
                if (!chosenCards.isEmpty()) {
                    myInputListener.sendInput(chosenCards);
                }
                dismiss();
            }
        });
        return view;
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
     * This Interface defines an OnInputListener which allows the DialogFragment
     * to send data back to the parent Fragment.
     */
    public interface OnInputListener {
        void sendInput(ArrayList<Card> chosenCards);
    }

    /**
     * This method resizes the Fragment, so that the Views on the Fragment
     * display the user properly. Thanks and credit to Blix247 for their answer
     * to help solve this problem: https://stackoverflow.com/a/24213921
     */
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding a FragmentCardPickerBinding object which will be used to
     *                bind data to all of the Views.
     */
    public void setBinding(FragmentCardPickerBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the adapter field.
     * @param adapter a CardPickerAdapter object which will provide data for
     *                the RecyclerView.
     */
    public void setAdapter(CardPickerAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * This is the Mutator method for the layoutManager field.
     * @param layoutManager a RecyclerView.LayoutManager object which will help
     *                      manager to RecyclerView.
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    /**
     * This is the Mutator method for the viewModel field.
     * @param viewModel a CardViewModel object which will provide access to
     *                  the Android Room database.
     */
    public void setViewModel(CardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * This is the Mutator method for the myInputListener field.
     * @param myInputListener an OnInputListener object which will be used to
     *                        send data back to the parent Fragment.
     */
    public void setMyInputListener(OnInputListener myInputListener) {
        this.myInputListener = myInputListener;
    }

    /**
     * This is the Mutator method for the type field.
     * @param type a String which describes the type of data the Fragment should
     *             display to the user.
     */
    public void setType(String type) {
        this.type = type;
    }
}
