package backend;

import com.sun.istack.internal.Nullable;

/**
 * Created by Paul Lancaster on 26/08/2016.
 */

// Stores information about how to vary one of the 3 Colour components of an RGB gradient
public class ColourComponent{

    private Colours colour;
    private Function rowFunction, colFunction;
    private int rowValue, colValue;

    // PARAMS
    // The colour (red, blue or green) ,
    // The way the colour should change as the row increases,
    // The way the colour should change as the column increases,
    // An int value to be plugged into the row Function,
    // An int value to be plugged into the column Function
    ColourComponent(Colours colour, Function rowFunction, Function colFunction, @Nullable int rowValue, @Nullable int colValue){
        this.colour = colour;
        this.rowFunction = rowFunction;
        this.colFunction = colFunction;
        this.rowValue = rowValue;
        this.colValue = colValue;
    }

    public Colours getColour() {
        return colour;
    }

    public Function getRowFunction() {
        return rowFunction;
    }

    public int getColValue() {
        return colValue;
    }

    public int getRowValue() {
        return rowValue;
    }

    public Function getColFunction() {
        return colFunction;
    }
}
