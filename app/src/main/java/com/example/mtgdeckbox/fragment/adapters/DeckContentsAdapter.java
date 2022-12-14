package com.example.mtgdeckbox.fragment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgdeckbox.R;
import com.example.mtgdeckbox.fragment.cardImageFragment;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;
import com.example.mtgdeckbox.room.DeckCards;

import java.util.List;

/**
 * This class is the adapter which provides the data for the RecyclerView to see
 * the contents of the current Deck.
 * @author: Tom Barker
 */
public class DeckContentsAdapter extends
        RecyclerView.Adapter<DeckContentsAdapter.ViewHolder> {
    private int deckID;
    private List<Card> contents;
    private CardViewModel viewModel;
    private FragmentActivity parentActivity;

    /**
     * This is the default constructor.
     */
    public DeckContentsAdapter() {
        deckID = -1;
        contents = null;
        viewModel = null;
        parentActivity = null;
    }

    /**
     * This is the non-default constructor.
     * @param deckID an Integer containing the ID number of the Deck currently
     *               being edited.
     * @param deckContents a List of Cards which contains the current contents
     *                     of the Deck being edited.
     * @param viewModel a CardViewModel object which provides access to the
     *                  Android Room database.
     * @param myContext a Context which provides the context for this adapter
     *                  object.
     */
    public DeckContentsAdapter(int deckID, List<Card> deckContents,
                               CardViewModel viewModel, Context myContext) {
        this.deckID = deckID;
        contents = deckContents;
        this.viewModel = viewModel;
        parentActivity = (FragmentActivity)myContext;
    }

    /**
     * This method removes a Card from the deck contents.
     * @param position an Integer containing the position of the Card which
     *                 should be removed from the contents.
     */
    public void deleteCard(int position) {
        // Remove the card from the deck.
        Card removedCard = contents.get(position);
        contents.remove(position);

        try {
            viewModel.removeSpecificDeckCards(deckID, removedCard.getCardID());
        }
        catch (Exception e) {
            Log.d("DB ERROR:", "Could not execute query!");
        }
    }

    /**
     * This is the Accessor method for the contents field.
     * @return a List of Cards which contains the current contents of the
     * Deck being edited.
     */
    public List<Card> getContents() {
        return contents;
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer which contains the ID number of the Deck currently
     * being edited.
     */
    public int getDeckID() {
        return deckID;
    }

    /**
     * This method returns the size of the list.
     * @return an Integer containing the full size of the list.
     */
    @Override
    public int getItemCount() {
        return contents.size();
    }

    /**
     * This is the Accessor method for the parentActivity field.
     * @return a FragmentActivity which is the parent Activity for this
     * adapter.
     */
    public FragmentActivity getParentActivity() {
        return parentActivity;
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
        holder.cardName.setText(contents.get(position).getName());
        holder.cardType.setText(contents.get(position).getTypes());
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
    public DeckContentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View deckContentsView = inflater.inflate(R.layout.deck_content_item,
                parent, false);

        return new DeckContentsAdapter.ViewHolder(deckContentsView);
    }

    /**
     * This class represents the ViewHolder which will bind necessary
     * data to a RecyclerView row.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardName;
        public TextView cardType;
        public ImageButton deleteButton;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);

            cardName = itemView.findViewById(R.id.textView_cardName);
            cardType = itemView.findViewById(R.id.textView_cardType);
            deleteButton = itemView.findViewById(R.id.imageButton_delete);

            // @todo: Define onClickListener() somewhere else and re-use it.
            /* Set the behaviour for clicking on each RecyclerView row. We need
             * to set this behaviour on the name textView, type textView and the
             * itemView itself, so the same behaviour happens no matter where
             * on the row the user clicks.
             */
            cardName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Card clickedCard = contents.get(getAbsoluteAdapterPosition());
                        cardImageFragment cardImage = new cardImageFragment(clickedCard);
                        cardImage.show(parentActivity.getSupportFragmentManager(),
                                clickedCard.getName());
                    }
                    catch (Exception e) {
                        Log.d("DB ERROR:", "Could not execute query!");
                    }
                }
            });

            // Set the behaviour for clicking on the RecyclerView row.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Card clickedCard = contents.get(getAbsoluteAdapterPosition());
                        cardImageFragment cardImage = new cardImageFragment(clickedCard);
                        cardImage.show(parentActivity.getSupportFragmentManager(),
                                clickedCard.getName());
                    }
                    catch (Exception e) {
                        Log.d("DB ERROR:", "Could not execute query!");
                    }
                }
            });

            // Set the behaviour for clicking on the card type text.
            cardType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Card clickedCard = contents.get(getAbsoluteAdapterPosition());
                        cardImageFragment cardImage = new cardImageFragment(clickedCard);
                        cardImage.show(parentActivity.getSupportFragmentManager(),
                                clickedCard.getName());
                    }
                    catch (Exception e) {
                        Log.d("DB ERROR:", "Could not execute query!");
                    }
                }
            });

            // Set the behaviour for clicking on the delete button.
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCard(getAbsoluteAdapterPosition());
                }
            });
        }
    }

    /**
     * This is the Mutator method for the contents field.
     * @param contents a List of Cards containing the contents of the Deck
     *                 currently being edited.
     */
    public void setContents(List<Card> contents) {
        this.contents = contents;
    }

    /**
     * This is the Mutator method for the deckID field.
     * @param deckID an Integer containing the ID number of the Deck currently
     *               being edited.
     */
    public void setDeckID(int deckID) {
        this.deckID = deckID;
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
     * This is the Mutator method for the parentActivity field.
     * @param parentActivity a FragmentActivity object which describes the parent
     *                       Activity for this adapter.
     */
    public void setParentActivity(FragmentActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
