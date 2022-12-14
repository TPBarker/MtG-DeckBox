package com.example.mtgdeckbox.fragment.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mtgdeckbox.fragment.deckContentsFragment;
import com.example.mtgdeckbox.fragment.deckGraphsFragment;
import com.example.mtgdeckbox.fragment.deckSuggestionsFragment;

/**
 * This class powers the tabs in the DeckViewsActivity so the user can switch
 * between viewing different Fragments.
 * @author: Tom Barker
 */
public class DeckViewsTabsAdapter extends FragmentStateAdapter {
    private int deckID;

    /**
     * This is the non-default constructor.
     * @param fm a FragmentManager which will help to manage the Fragments
     *           loaded into the tabs.
     * @param lifecycle a Lifecycle which describes the Lifecycle of the parent
     *                  Activity or Fragment.
     * @param deckID an Integer containing the deck ID of the Deck which has been
     *               selected for editing.
     */
    public DeckViewsTabsAdapter(@NonNull FragmentManager fm,
                               @NonNull Lifecycle lifecycle,
                                int deckID) {
        super(fm, lifecycle);
        this.deckID = deckID;
    }

    /**
     * This method creates a new Fragment to be inserted into the tab layout.
     * @param position an Integer describing the position in which to insert
     *                 the new Fragment.
     * @return a created Fragment, to be displayed in the tabs.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new deckContentsFragment(deckID);
            case 1:
                return new deckSuggestionsFragment(deckID);
            case 2:
                return new deckGraphsFragment(deckID);
            default:
                return new Fragment();
        }
    }

    /**
     * This is the Accessor method for the deckID field.
     * @return an Integer containing the ID number of the Deck which has been
     * selected for editing.
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
        return 3;
    }

    /**
     * This is the Mutator method for the deckID field.
     * @param deckID an Integer containing the ID number of the Deck which has
     *               been selected for editing.
     */
    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }
}
