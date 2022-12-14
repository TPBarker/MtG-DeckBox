package com.example.mtgdeckbox;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This is a custom class for opening and reading a CSV file.
 * @author: Tom Barker. Credit to Marcin Kosinski for their stackoverflow solution
 *          for reading comma-separated values which contain commas.
 * @see:    https://stackoverflow.com/a/24078092
 */
public class CSVReader {
    private Context myContext;

    /**
     * This is the default constructor.
     */
    public CSVReader(){}

    /**
     * This is the non-default constructor.
     * @param newContext the parent Context from which this CSVReader is being used.
     */
    public CSVReader(Context newContext) {
        myContext = newContext;
    }

    /**
     * This is the Accessor method for the myContext field.
     * @return a Context which describes the context for this CSVReader.
     */
    public Context getMyContext() {
        return myContext;
    }

    /**
     * This method reads a csv file (of formatted card data) and returns it.
     * @param filename the filename of the csv file to be read.
     * @return  an ArrayList of ArrayLists of Strings. Each element of the ArrayList
     *          is an ArrayList of Strings, and each String within those ArrayLists
     *          contains all the card data from that row of the csv file.
     */
    public ArrayList<ArrayList<String>> readCSV(String filename) {
        ArrayList<ArrayList<String>> cardsList = new ArrayList<>();
        try {
            // Initialise some variables.
            BufferedInputStream bufferedInputStream = new BufferedInputStream
                    (myContext.getAssets().open(filename));
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(bufferedInputStream));
            String line;

            // Read the first line and discard it, to skip the headers.
            bufferedReader.readLine();

            // Scan through each line and digest the data.
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<String> card = new ArrayList<>();
                /* Some card names and types are in quotations - these fields have
                 * commas built into the them. Some fancy magic is required
                 * to ignore those commas.
                 * For each character of each comma separated value, check to
                 * see if it is a comma. If it is and it is in quotations,
                 * add it to the String value. If it is and it is not in
                 * quotations, it is a delimiter.
                 */
                boolean inQuotes = false;
                StringBuilder b = new StringBuilder();
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case ',':
                            if (inQuotes) {
                                b.append(c);
                            } else {
                                card.add(b.toString());
                                b = new StringBuilder();
                            }
                            break;
                        case '\"':
                            inQuotes = !inQuotes;
                            break;
                        default:
                            b.append(c);
                            break;
                    }
                }
                if (b.toString().isEmpty()) {
                    card.add("");
                } else {
                    card.add(b.toString());
                }
                cardsList.add(card);
            }
        } catch (Exception e) {
            Log.d("CSV ERROR:", "Could not read CSV file!");
        }
        return cardsList;
    }

    /**
     * This is the Mutator method for the myContext field.
     * @param myContext a Context object which describes the Context for this
     *                  CSVReader object.
     */
    public void setMyContext(Context myContext) {
        this.myContext = myContext;
    }
}
