package com.example.mtgdeckbox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtgdeckbox.R;
import com.example.mtgdeckbox.databinding.AboutFragmentBinding;

/**
 * This Fragment displays simple instructions to help the user.
 * @author: Tom BVarker
 */
public class aboutFragment extends Fragment {
    private AboutFragmentBinding binding;

    /**
     * This is the default constructor.
     */
    public aboutFragment(){}

    /**
     * This is the Accessor method for the binding field.
     * @return an AboutFragmentBinding object which is used to bind data to
     * all of the Views.
     */
    public AboutFragmentBinding getBinding() {
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
        binding = AboutFragmentBinding.inflate(inflater, container, false);

        // Load the logo image into the imageView.
        Glide
                .with(getActivity().getApplicationContext())
                .load(R.drawable.logo)
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.imageViewLogo);

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
     * @param binding an AboutFragmentBinding object which will be used to bind
     *                data to all of the Views.
     */
    public void setBinding(AboutFragmentBinding binding) {
        this.binding = binding;
    }
}
