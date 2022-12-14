package com.example.mtgdeckbox.fragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class transforms and provides the data for the RecyclerView in the
 * CommanderPickerFragment.
 * @author: Tom Barker
 */
public class CommanderAdapter extends
        RecyclerView.Adapter<CommanderAdapter.ViewHolder> {

    // The list of cards for this adapter.
    private List<Card> deckContents;
    private Card chosenCard;
    private ArrayList<ViewHolder> holders;

    /**
     * This is the default constructor.
     */
    public CommanderAdapter() {}

    /**
     * This is the non-default constructor.
     * @param contents a List of Cards which contains the contents of the Deck.
     */
    public CommanderAdapter(List<Card> contents) {
        deckContents = contents;
        chosenCard = null;
        holders = new ArrayList<>();
    }

    /**
     * This method clears the selected card from the RecyclerView.
     */
    public void clearSelection() {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).itemView.setBackgroundResource(0);
        }
    }

    /**
     * This is the Accessor method for the chosenCard field.
     * @return a Card which has been chosen by the user.
     */
    public Card getChosenCard() {
        return chosenCard;
    }

    /**
     * This is the Accessor method for the deckContents field.
     * @return a List of Cards which contains the contents of the
     * current Deck.
     */
    public List<Card> getDeckContents() {
        return deckContents;
    }

    /**
     * This is the Accessor method for the holder field.
     * @return an ArrayList which contains all the ViewHolders for the
     * RecyclerView.
     */
    public ArrayList<ViewHolder> getHolders() {
        return holders;
    }

    /**
     * This method returns the size of the list.
     * @return an Integer containing the full size of the list.
     */
    @Override
    public int getItemCount() {
        return deckContents.size();
    }

    /**
     * This is the method which runs when data is bound to the ViewHolder.
     * @param holder    the ViewHolder to bind data to.
     * @param position  an Integer containing the ViewHolder's position in
     *                  the layout.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holders.add(holder);
        holder.commanderName.setText(deckContents.get(position).getName());
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

        View deckContentsView = inflater.inflate(R.layout.commander_picker_item,
                parent, false);

        return new ViewHolder(deckContentsView);
    }

    /**
     * This class represents the ViewHolder which will bind necessary
     * data to a RecyclerView row.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView commanderName;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            commanderName = itemView.findViewById(R.id.textView_commanderName);

            /* Set the behaviour for clicking on each RecyclerView row. We need
             * to set this behaviour on both the textView and the itemView, so
             * that the same behaviour happens no matter where the user clicks.
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* Clear all previously selected cards. Set the chosen card
                     * into memory, and then highlight it on the view.
                     */
                    clearSelection();
                    chosenCard = deckContents.get(getAbsoluteAdapterPosition());
                    holders.get(getLayoutPosition()).itemView
                                    .setBackgroundResource(R.drawable.frame_border);
                }
            });

            commanderName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Same behaviour as above.
                    clearSelection();
                    chosenCard = deckContents.get(getAbsoluteAdapterPosition());
                    holders.get(getLayoutPosition()).itemView
                            .setBackgroundResource(R.drawable.frame_border);
                }
            });
        }
    }

    /**
     * This is the Mutator method for the deckContents field.
     * @param deckContents a List of Cards to be set as the current contents
     *                     of the Deck.
     */
    public void setDeckContents(List<Card> deckContents) {
        this.deckContents = deckContents;
    }

    /**
     * This is the Mutator method for the chosenCard field.
     * @param chosenCard a Card which represents the Card chosen by the user.
     */
    public void setChosenCard(Card chosenCard) {
        this.chosenCard = chosenCard;
    }

    /**
     * This is the Mutator method for the holders field.
     * @param holders an ArrayList of ViewHolders which contains all of the
     *                ViewHolders used in the RecyclerView.
     */
    public void setHolders(ArrayList<ViewHolder> holders) {
        this.holders = holders;
    }
}
