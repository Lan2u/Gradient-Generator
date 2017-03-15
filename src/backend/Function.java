package backend;

/**
 * Created by paul on 27/08/2016.
 */
public enum Function {
    INCREASE, // Increase the value as the row/column progresses
    DECREASE, // Decrease the value as the row/column progresses
    SIN, // Increase and decrease the value according to sin (parameters
    COS, // Increase and decrease the value according to cos (parameters
    CONSTANT, // Keep the value constant parameters (constant value (int))
    NONE; // Do nothing (required as the colunm section if you want variance as the row increases because column
          // override row functions
}
