package com.example.mtgdeckbox.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.mtgdeckbox.databinding.FragmentCommanderPickerBinding;
import com.example.mtgdeckbox.fragment.adapters.CommanderAdapter;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This Fragment provides a list of commander-eligible Cards for the user to
 * pick from, to set the commander of the Deck.
 * @author: Tom Barker
 */
public class commanderPickerFragment extends DialogFragment {
    private FragmentCommanderPickerBinding binding;
    private CommanderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardViewModel viewModel;
    public OnInputListener myInputListener;

    /**
     * This is the default constructor.
     */
    public commanderPickerFragment(){}

    /**
     * This is the Accessor method for the binding field.
     * @return a FragmentCommanderPickerBinding object which binds data to all
     * of the Views.
     */
    public FragmentCommanderPickerBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the adapter field.
     * @return a CommanderAdapter object which provides data for the
     * RecyclerView.
     */
    public CommanderAdapter getAdapter() {
        return adapter;
    }

    /**
     * This is the Accessor method for the layoutManager field.
     * @return a RecyclerView.LayoutManager object which helps to manager
     * the RecyclerView.
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
     * This is the Accessor method for the myInputListener field.
     * @return an OnInputListener object which allows the DialogFragment to send
     * data back to the parent Fragment.
     */
    public OnInputListener getMyInputListener() {
        return myInputListener;
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
            Log.d("ERROR", "Could not attach the Input Listener!");
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
        binding = FragmentCommanderPickerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.setCancelable(false);

        // Initialise the viewModel.
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication())
                .create(CardViewModel.class);

        // Retrieve a list of commander-eligible Cards.
        CompletableFuture<List<Card>> commanderCardsCompletableFuture = viewModel.getAllCommanders();
        try {
            adapter = new CommanderAdapter(commanderCardsCompletableFuture.get());
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }

        // Create a line divider between items
        binding.recyclerViewCommanderPicker.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        binding.recyclerViewCommanderPicker.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewCommanderPicker.setLayoutManager(layoutManager);

        // Set the behaviour for the dismiss button.
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Set the behaviour for the choose button.
        binding.buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected commander card and write it into the deck.
                Card chosenCommander = adapter.getChosenCard();
                if (chosenCommander == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(binding.buttonChoose.getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Error!");
                    builder.setMessage("No commander card chosen! Please choose a " +
                            "commander, or use the 'Cancel' button to go back.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    myInputListener.sendInput(chosenCommander);
                    dismiss();
                }
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
        void sendInput(Card chosenCard);
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
     * @param binding a FragmentCommanderPickerBinding object which will be used
     *                to bind data to all of the Views.
     */
    public void setBinding(FragmentCommanderPickerBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the adapter field.
     * @param adapter a CommanderAdapter object which will provide data for the
     *                RecyclerView.
     */
    public void setAdapter(CommanderAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * This is the Mutator method for the layoutManager field.
     * @param layoutManager a RecyclerView.LayoutManager object which will help
     *                      manager the RecyclerView.
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
     * This is the Mutator method for the myInputListener field.
     * @param myInputListener an OnInputListener object which will be used to send
     *                        data back to the parent Fragment.
     */
    public void setMyInputListener(OnInputListener myInputListener) {
        this.myInputListener = myInputListener;
    }
}
