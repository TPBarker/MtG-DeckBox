package com.example.mtgdeckbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mtgdeckbox.databinding.SignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class provides the functionality for the Signup/Register screen. It
 * provides the methods which will allow the user to signup with a new account
 * and insert that information into the Google Firebase authentication service.
 * @author: Tom Barker
 */
public class SignupActivity extends AppCompatActivity {
    private SignupBinding binding;
    private FirebaseAuth auth;
    private Validator validate;

    /**
     * This is the default constructor.
     */
    public SignupActivity() {}

    /**
     * This is the Accessor method for the binding field.
     * @return a SignupBinding which can bind all of the views in the Activity.
     */
    public SignupBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the auth field.
     * @return a FirebaseAuth object used to log the user into the application.
     */
    public FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * This is the Accessor method for the validate field.
     * @return a Validator object, used to validate the user's email address
     * and password entry.
     */
    public Validator getValidate() {
        return validate;
    }

    /**
     * This method runs when the Activity is called from the main Activity.
     * @param savedInstanceState a Bundle which contains the saved instance state.
     *                           The Activity can may restore itself to a saved
     *                           state under special circumstances.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialise fields and setup Activity.
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        validate = new Validator();
        Toasty toastMaker = new Toasty(this);

        // Load the logo image into the imageView.
        Glide
                .with(getApplicationContext())
                .load(R.drawable.logo)
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.imageViewLogo);

        // Set the behaviour of the back button.
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to login screen.
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

        // Set the behaviour of the register button.
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean proceed = true;
                binding.textViewEmailValidation.setText("");
                binding.textViewPasswordValidation.setText("");
                String passwordValidation = getResources().getString(R.string.signup_password_error);
                String emailValidation = getResources().getString(R.string.signup_email_error);

                // Validate the entered email address.
                String email = binding.editTextEmail.getText().toString();
                if (!validate.validateEmail(email)) {
                    // Invalid email address.
                    binding.textViewEmailValidation.setText(emailValidation);
                    proceed = false;
                }

                // Validate the entered password (as a password).
                String pass = binding.editTextPassword.getText().toString();
                if (!validate.validatePassword(pass)) {
                    // Password not strong enough.
                    binding.textViewPasswordValidation.setText(passwordValidation);
                    proceed = false;
                }

                // Proceed with use signup, if required validations have passed.
                if (proceed) {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Notify the user and send them back to login.
                                    toastMaker.popToastShort("Registration Success!");
                                    startActivity(new Intent(SignupActivity.this,
                                            MainActivity.class));
                                } else {
                                    // Signup has failed.
                                    toastMaker.popToastShort("Registration Failure!");
                                }
                            }
                        }
                    );
                }
            }
        });
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding a SignupBinding which is used to bind all of the views
     *                in the Activity.
     */
    public void setBinding(SignupBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the auth field.
     * @param auth a FirebaseAuth object which is used for the user to log into
     *             the application.
     */
    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    /**
     * This is the Mutator method for the validate field.
     * @param validate a Validation object which will be used to validate the
     *                 user's email address and password entry.
     */
    public void setValidate(Validator validate) {
        this.validate = validate;
    }
}
