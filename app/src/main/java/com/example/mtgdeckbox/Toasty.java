package com.example.mtgdeckbox;

import android.content.Context;
import android.widget.Toast;

/**
 * This class is designed to provide Toast functionality for wherever the rest
 * of the application may need it.
 * @author: Tom Barker
 */
public class Toasty {
    private Context myContext;

    /**
     * This is the default constructor for the class.
     */
    public Toasty(){
    }

    /**
     * This is a non-default constructor for the class.
     * @param thisContext the Context of the Activity which is making a Toast.
     */
    public Toasty(Context thisContext) {
        myContext = thisContext;
    }

    /**
     * This is the Accessor method for the myContext field.
     * @return the Context of the parent Activity.
     */
    public Context getMyContext() {
        return myContext;
    }

    /**
     * This method makes a long toast using the correct context.
     * @param message a String containing the toast text.
     */
    public void popToastLong (String message) {
        Toast.makeText(myContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * This method makes a short toast using the correct context.
     * @param message a String containing the toast text.
     */
    public void popToastShort (String message) {
        Toast.makeText(myContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This is the Mutator method for the myContext field.
     * @param newContext the Context of the Activity which will be the new
     *                   parent Activity.
     */
    public void setMyContext(Context newContext) {
        myContext = newContext;
    }
}
