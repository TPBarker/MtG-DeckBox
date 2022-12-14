package com.example.mtgdeckbox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtgdeckbox.R;
import com.example.mtgdeckbox.databinding.HomeFragmentBinding;

/**
 * This fragment is displayed on the Home Screen of the application.
 * @author: Tom Barker
 */
public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;

    /**
     * This is the default constructor.
     */
    public HomeFragment(){}

    /**
     * This is the Accessor method for the binding field.
     * @return a HomeFragmentBinding which is used to bind data to the Views.
     */
    public HomeFragmentBinding getBinding() {
        return binding;
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
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.textViewDate.setText(java.time.LocalDate.now().toString());

        // Load the logo image into the imageView.
        Glide
                .with(getActivity().getApplicationContext())
                .load(R.drawable.logo)
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.imageViewLogo);

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
     * This is the Mutator method for the binding field.
     * @param binding a HomeFragmentBinding object which will be used to bind
     *                all of the data to the Views.
     */
    public void setBinding(HomeFragmentBinding binding) {
        this.binding = binding;
    }
}
