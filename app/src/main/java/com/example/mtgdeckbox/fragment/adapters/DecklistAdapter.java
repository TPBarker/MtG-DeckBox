package com.example.mtgdeckbox.fragment.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgdeckbox.DeckViewsActivity;
import com.example.mtgdeckbox.R;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.Deck;

import java.util.List;

/**
 * This class contains the data which powers the RecyclerView for the user to
 * see the list of Decks they have created.
 */
public class DecklistAdapter extends
        RecyclerView.Adapter<DecklistAdapter.ViewHolder> {
    private List<Deck> userDecks;
    private CardViewModel viewModel;

    /**
     * This is the default constructor.
     */
    public DecklistAdapter() {}

    /**
     * This is a non-default constructor.
     * @param decks a List of Decks which contains all of the user's created
     *              Decks.
     */
    public DecklistAdapter(List<Deck> decks) {
        userDecks = decks;
    }

    /**
     * This is a non-default constructor.
     * @param viewModel a CardViewModel which will provide access to the
     *                  Android Room database.
     */
    public DecklistAdapter(CardViewModel viewModel) {
        this.viewModel = viewModel;
        try {
            userDecks = viewModel.getAllDecksByFuture().get();
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }
    }

    /**
     * This method removes a Deck from the user's list of Decks.
     * @param position an Integer describing the Deck's position in the list
     *                 of the user's Decks, which should be deleted.
     */
    public void deleteDeck(int position) {
        viewModel.removeDeck(userDecks.get(position));
        userDecks.remove(position);
        notifyDataSetChanged();
    }

    /**
     * This method selects a Deck to be edited and calls the DeckViewsActivity,
     * to begin editing that deck.
     * @param position an Integer containing the Deck's position in the list of
     *                 the user's Decks.
     * @param context a Context for the next Activity to be started.
     */
    public void editDeck(int position, Context context) {
        int deckID = userDecks.get(position).getDeckID();
        Intent editIntent = new Intent(context, DeckViewsActivity.class);
        Bundle deck = new Bundle();
        deck.putInt("deckID", deckID);
        editIntent.putExtras(deck);
        context.startActivity(editIntent);
    }

    /**
     * This method returns the size of the list.
     * @return an Integer containing the full size of the list.
     */
    @Override
    public int getItemCount() {
        return userDecks.size();
    }

    /**
     * This is the Accessor method for the userDecks field.
     * @return a List of Decks which the user has created.
     */
    public List<Deck> getUserDecks() {
        return userDecks;
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
     * This is the method which runs when data is bound to the ViewHolder.
     * @param holder    the ViewHolder to bind data to.
     * @param position  an Integer containing the ViewHolder's position in
     *                  the layout.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.deckName.setText(userDecks.get(position).getDeckName());
        holder.position = position;
    }

    /**
     * This is the method which runs when the ViewHolder is created, as the adapter
     * is first initialised.
     * @param parent    the ViewGroup which the ViewHolder is attached to.
     * @param viewType  an Integer describing the viewType.
     * @return          the ViewHolder which is now holding the inflated View.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View deckListView = inflater.inflate(R.layout.decklist_item_deck,
                parent, false);
        return new ViewHolder(deckListView);
    }

    /**
     * This class represents the ViewHolder which will bind necessary
     * data to a RecyclerView row.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deckName;
        public ImageButton editButton;
        public ImageButton deleteButton;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);

            deckName = itemView.findViewById(R.id.textView_deckName);
            editButton = itemView.findViewById(R.id.imageButton_edit);
            deleteButton = itemView.findViewById(R.id.imageButton_delete);

            // Set the behaviour for the delete button.
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(deleteButton.getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Delete " + deckName.getText().toString() + "?");
                    builder.setMessage("This action will permanently delete " +
                            "this deck! Are you sure?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteDeck(getAbsoluteAdapterPosition());
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            // Set the behaviour for the edit button.
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editDeck(getAbsoluteAdapterPosition(), view.getContext());
                }
            });
        }
    }

    /**
     * This is the Mutator method for the userDecks field.
     * @param userDecks a List of Decks which the user has created.
     */
    public void setUserDecks(List<Deck> userDecks) {
        this.userDecks = userDecks;
    }

    /**
     * This is the Mutator method for the viewModel field.
     * @param viewModel a CardViewModel object which will provide access to the
     *                  Android Room database.
     */
    public void setViewModel(CardViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
