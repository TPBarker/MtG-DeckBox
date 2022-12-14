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
 * CardPickerFragment.
 * @author: Tom Barker
 */
public class CardPickerAdapter extends
        RecyclerView.Adapter<CardPickerAdapter.ViewHolder> {
    private List<Card> allCards;
    private Card chosenCard;
    private ArrayList<Card> selections;
    private ArrayList<ViewHolder> holders;

    /**
     * This is the default constructor.
     */
    public CardPickerAdapter() {
        allCards = null;
        chosenCard = new Card();
        selections = new ArrayList<>();
        holders = new ArrayList<>();
    }

    /**
     * This is the non-default constructor, which takes a list of Cards.
     * @param cards a List of Cards to be loaded into the Picker.
     */
    public CardPickerAdapter(List<Card> cards) {
        allCards = cards;
        chosenCard = new Card();
        selections = new ArrayList<>();
        holders = new ArrayList<>();
    }

    /**
     * This method adds a Card to an ArrayList of selected Cards, when one is
     * clicked on in the RecyclerView. This logic keeps track of the Cards the
     * user is selecting to be added into their deck.
     * @param chosenCard    a Card which has been clicked on in the list.
     * @return              a Boolean which is true if this action is a
     *                      de-selection and false if this action is a selection.
     */
    public boolean addSelections(Card chosenCard) {
        // Initialise some variables.
        boolean match = false;
        int matchIndex = -1;

        // For each Card already selected, check if the clicked Card matches.
        for (int i = 0; i < selections.size(); i++) {
            if (selections.get(i).getName().equals(chosenCard.getName())) {
                // We found a match. This Card should be de-selected.
                match = true;
                matchIndex = i;
                break;
            }
        }
        if (match) {
            // Remove the de-selected Card.
            selections.remove(matchIndex);
        } else {
            // Add the selected Card.
            selections.add(chosenCard);
        }
        return match;
    }

    /**
     * This is the Accessor method for the allCards field.
     * @return a List of all the Cards loaded into the CardPicker.
     */
    public List<Card> getAllCards() {
        return allCards;
    }

    /**
     * This is the Accessor method for the chosenCard field.
     * @return the Card which has been clicked on by the user.
     */
    public Card getChosenCard() {
        return chosenCard;
    }

    /**
     * This is the Accessor method for the holders field.
     * @return an ArrayList of ViewHolders, which are all of the ViewHolders
     * in the RecyclerView.
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
        return allCards.size();
    }

    /**
     * This is the Accessor method for the selections field.
     * @return  an ArrayList of Cards, containing all the Cards which the user
     *          has selected from this CardPicker.
     */
    public ArrayList<Card> getSelections() {
        return selections;
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
        holder.cardName.setText(allCards.get(position).getName());
        holder.cardType.setText(allCards.get(position).getTypes());
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

        View cardPickerView = inflater.inflate(R.layout.card_picker_item,
                parent, false);

        return new ViewHolder(cardPickerView);
    }

    /**
     * This class represents the ViewHolder which will bind necessary
     * data to a RecyclerView row.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardName;
        public TextView cardType;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);

            cardName = itemView.findViewById(R.id.textView_cardName);
            cardType = itemView.findViewById(R.id.textView_cardType);

            // @todo: Define onClickListener() somewhere else and re-use it.
            /* Set the behaviour for clicking on each RecyclerView row. We need
             * to set this behaviour on the name textView, type textView and the
             * itemView itself, so the same behaviour happens no matter where
             * on the row the user clicks.
             */
            cardName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* If the clicked card is unselected, select it. If it is
                     * already selected, unselect it.
                     */
                    chosenCard = allCards.get(getAbsoluteAdapterPosition());
                    boolean removed = addSelections(chosenCard);
                    if (removed) {
                        holders.get(getAbsoluteAdapterPosition()).itemView
                                .setBackgroundResource(0);
                    } else {
                        holders.get(getAbsoluteAdapterPosition()).itemView
                                .setBackgroundResource(R.drawable.frame_border);
                    }
                }
            });

            // Set the behaviour for clicking on the RecyclerView row.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Same behaviour as above.
                    chosenCard = allCards.get(getAbsoluteAdapterPosition());
                    boolean removed = addSelections(chosenCard);
                    if (removed) {
                        holders.get(getAbsoluteAdapterPosition()).itemView
                                .setBackgroundResource(0);
                    } else {
                        holders.get(getAbsoluteAdapterPosition()).itemView
                                .setBackgroundResource(R.drawable.frame_border);
                    }
                }
            });

            // Set the behaviour for clicking on the card type text.
            cardType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Same behaviour as above.
                    chosenCard = allCards.get(getAbsoluteAdapterPosition());
                    boolean removed = addSelections(chosenCard);
                    if (removed) {
                        holders.get(getAbsoluteAdapterPosition()).itemView
                                .setBackgroundResource(R.drawable.frame_border);
                    } else {
                        holders.get(getAbsoluteAdapterPosition()).itemView
                                .setBackgroundResource(0);
                    }
                }
            });
        }
    }

    /**
     * This is the Mutator method for the allCards field.
     * @param allCards a List of Cards which should be displayed in the
     *                 RecyclerView.
     */
    public void setAllCards(List<Card> allCards) {
        this.allCards = allCards;
    }

    /**
     * This is the Mutator method for the chosenCard field.
     * @param chosenCard a Card which has been selected by the user.
     */
    public void setChosenCard(Card chosenCard) {
        this.chosenCard = chosenCard;
    }

    /**
     * This is the Mutator method for the selections field.
     * @param selections an ArrayList containing the Cards which have been
     *                   selected by the user.
     */
    public void setSelections(ArrayList<Card> selections) {
        this.selections = selections;
    }

    /**
     * This is the Mutator method for the holders field.
     * @param holders an ArrayList containing all of the ViewHolders in the
     *                RecyclerView.
     */
    public void setHolders(ArrayList<ViewHolder> holders) {
        this.holders = holders;
    }
}
