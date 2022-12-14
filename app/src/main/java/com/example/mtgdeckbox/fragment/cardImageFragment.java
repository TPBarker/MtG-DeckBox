package com.example.mtgdeckbox.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mtgdeckbox.R;
import com.example.mtgdeckbox.databinding.FragmentCardImageBinding;
import com.example.mtgdeckbox.retrofit.ImageURI;
import com.example.mtgdeckbox.retrofit.RetrofitClient;
import com.example.mtgdeckbox.retrofit.RetrofitInterface;
import com.example.mtgdeckbox.retrofit.SearchResponse;
import com.example.mtgdeckbox.room.Card;
import com.example.mtgdeckbox.room.CardViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This Fragment uses Retrofit to query the Scryfall API and display an
 * image of a particular Card.
 * @author: Tom Barker
 */
public class cardImageFragment extends DialogFragment {
    private FragmentCardImageBinding binding;
    private CardViewModel viewModel;
    private Card selectedCard;
    private RetrofitInterface retrofitInterface;

    /**
     * This is the default constructor.
     */
    public cardImageFragment() {}

    /**
     * This is the non-default constructor.
     * @param card a Card of which the Fragment should display an image.
     */
    public cardImageFragment (Card card) {
        selectedCard = card;
    }

    /**
     * This is the Accessor method for the binding field.
     * @return a FragmentCardImageBinding object which is used to bind data
     * to all of the Views.
     */
    public FragmentCardImageBinding getBinding() {
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
     * This is the Accessor method for the selectedCard field.
     * @return the Card which has been selected for display by the user.
     */
    public Card getSelectedCard() {
        return selectedCard;
    }

    /**
     * This is the Accessor method for the retrofitInterface field.
     * @return a RetrofitInterface which is used to place calls to the
     * Scryfall API.
     */
    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
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
        binding = FragmentCardImageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialise the viewModel.
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication())
                .create(CardViewModel.class);

        // Setup Retrofit.
        retrofitInterface = RetrofitClient.getRetrofitService();
        Call<SearchResponse> callAsync = retrofitInterface.customSearch(
                selectedCard.getScryfallID());

        // Log some data for debugging.
        Log.d("CALL:", callAsync.request().toString());

        // Place a call to the Scryfall API using Retrofit.
        callAsync.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    // Parse the necessary JSON objects.
                    ImageURI imageURI = response.body().imageURI;
                    String normalURI = imageURI.getNormal();

                    // Load the imageURI into the imageView.
                    Glide
                            .with(getContext())
                            .load(normalURI)
                            .fitCenter()
                            .error(R.mipmap.ic_launcher)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(binding.imageViewCard);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("ERROR!", "Retrofit Request Failed! " + call.request().toString());
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
     * This method resizes the Fragment so that the image we have loaded
     * from the Scryfall API displays correctly. Thanks and credit to
     * Patrick for their answer and help in solving this problem:
     * https://stackoverflow.com/a/28596836
     */
    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setLayout((int) (width * 0.84), (int) (height * 0.6));
        window.setGravity(Gravity.CENTER);
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding a FragmentCardImageBinding object which will be used to
     *                bind data to all of the Views.
     */
    public void setBinding(FragmentCardImageBinding binding) {
        this.binding = binding;
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
     * This is the Mutator method for the selectedCard field.
     * @param selectedCard the Card for which the Fragment should display
     *                     an image.
     */
    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    /**
     * This is the Mutator method for the retrofitInterface field.
     * @param retrofitInterface a RetrofitInterface which will be used to place
     *                          call to the Scryfall API.
     */
    public void setRetrofitInterface(RetrofitInterface retrofitInterface) {
        this.retrofitInterface = retrofitInterface;
    }
}
