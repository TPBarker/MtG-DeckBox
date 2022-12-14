package com.example.mtgdeckbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.mtgdeckbox.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is the Main Activity which runs when the application is first launched.
 * It contains the functionality for the User to log into the application,
 * using Google Firebase.
 * @author: Tom Barker
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private Validator validate;
    private Toasty toastMaker;

    /**
     * This is the default constructor.
     */
    public MainActivity() {}

    /**
     * This is the Accessor method for the binding field.
     * @return an ActivityMainBinding object used to bind all of the views
     * in the Activity.
     */
    public ActivityMainBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the auth field.
     * @return a FirebaseAuth object which is used to log the user into the
     * application.
     */
    public FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * This is the Accessor method for the validate field.
     * @return a Validator object which is used to validate the user's entered
     * email address and password.
     */
    public Validator getValidate() {
        return validate;
    }

    /**
     * This is the Accessor method for the toastMaker field.
     * @return a Toasty object, which is used to create Toast messages.
     */
    public Toasty getToastMaker() {
        return toastMaker;
    }

    /**
     * This method runs when the Activity is launched.
     * @param savedInstanceState a Bundle which contains the saved instance state.
     *                           The Activity can may restore itself to a saved
     *                           state under special circumstances.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialise fields and setup the Activity.
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        validate = new Validator();
        toastMaker = new Toasty(this);

        // Load the logo image into the imageView.
        Glide
            .with(getApplicationContext())
            .load(R.drawable.logo)
            .fitCenter()
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.imageViewLogo);

        // Set the behaviour for the register button.
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send the user to the Signup Activity to create an account.
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

        // Set the behaviour for the signin button.
        binding.buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean proceed = true;
                binding.textViewEmailValidation.setText("");
                binding.textViewPasswordValidation.setText("");

                // Get the user email address and validate it.
                String email = binding.editTextEmail.getText().toString();
                if (!validate.validateEmail(email)) {
                    // Email address is invalid.
                    binding.textViewEmailValidation.setText(getResources()
                            .getString(R.string.signup_password_error));
                    proceed = false;
                }

                // Get the user password and validate it.
                // @todo: Add a way to toggle the password to viewable state.
                // binding.textViewPasswordValidation.setTransformationMethod(null);
                String pass = binding.editTextPassword.getText().toString();
                if (!validate.validateString(pass)) {
                    // Password is not a valid string.
                    binding.textViewPasswordValidation.setText(getResources()
                            .getString(R.string.main_password_error));
                    proceed = false;
                }

                if (proceed) {
                    userLogin(email, pass);
                }
            }
        });
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding an ActivityMainBinding object which will be used to bind
     *                all of the views in the Activity.
     */
    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the auth field.
     * @param auth a FirebaseAuth object which will be used to allow the user to
     *             log into the application.
     */
    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    /**
     * This is the Mutator method for the validate field.
     * @param validate a Validator object which will be used to validate the
     *                 user's entered email address and password.
     */
    public void setValidate(Validator validate) {
        this.validate = validate;
    }

    /**
     * This is the Mutator method for the toastMaker field.
     * @param toastMaker a Toasty object which will be used to create Toasts
     *                   for this Activity.
     */
    public void setToastMaker(Toasty toastMaker) {
        this.toastMaker = toastMaker;
    }

    /**
     * This method connects to the Google Firebase service and attempts to log
     * the user into the application.
     * @param email     a String containing the user's entered email address.
     * @param password  a String containing the user's entered password.
     */
    public void userLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(
            new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // Login is successful - user should be moved to the next activity.
                    toastMaker.popToastShort("Login Successful!");
                    Intent loggedIn = new Intent(MainActivity.this, HomeActivity.class);
                    Bundle userData = new Bundle();
                    userData.putString("userID", authResult.getUser().getUid());
                    loggedIn.putExtras(userData);
                    startActivity(loggedIn);
                }
            }
        );

        auth.signInWithEmailAndPassword(email, password).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Login has failed, possibly due to invalid Firebase credentials.
                        toastMaker.popToastShort("Login failed! Please try again!");
                    }
                }
        );
    }
}