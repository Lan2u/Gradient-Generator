package backend;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreatingImages {
    public static void main(String[] args){
        try {
            new CreatingImages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CreatingImages() throws IOException, InterruptedException {
        ColourComponent red = new ColourComponent(Colours.RED, Function.NONE, Function.CONSTANT,255,100);
        ColourComponent green = new ColourComponent(Colours.GREEN, Function.DECREASE, Function.NONE, 102,102);
        ColourComponent blue = new ColourComponent(Colours.BLUE, Function.CONSTANT, Function.CONSTANT, 102,102);
        BufferedImage image = generateGradient(500,500,red,green,blue);
        String fileName = generateFileName(red,green,blue);
        ImageIO.write(image, "png", createFile("Generated Images/Output/" + fileName));
    }

    @NotNull
    private String functionToLetter(Function f, int fValue){
        switch(f){
            case INCREASE:
                return "I";
            case SIN:
                return "S";
            case DECREASE:
                return "D";
            case NONE:
                return "N";
            case COS:
                return "C";
            case CONSTANT:
                if (fValue >= 100){
                    return String.valueOf(fValue);
                }else if(fValue <100 && fValue >= 10){
                    return "0" + String.valueOf(fValue);
                } else if (fValue < 10){
                    return "00" + String.valueOf(fValue);
                }
            default:
            throw new IllegalArgumentException("Function not recognised when converting to a letter");
        }
    }

    private String generateFileName(ColourComponent red, ColourComponent green, ColourComponent blue) {
        StringBuilder name = new StringBuilder();
        name.append(functionToLetter(red.getRowFunction(),red.getRowValue()));
        name.append(functionToLetter(red.getColFunction(),red.getColValue()));
        name.append(functionToLetter(green.getRowFunction(),green.getRowValue()));
        name.append(functionToLetter(green.getColFunction(),green.getColValue()));
        name.append(functionToLetter(blue.getRowFunction(),blue.getRowValue()));
        name.append(functionToLetter(blue.getColFunction(),blue.getColValue()));
        name.append(".png");
        return name.toString();
    }

    // Generate a gradient using 2 functions to describe the change in colour of each of the 3 components of RGB
    // TODO Add RGB colour addition so that if there are 2 constant values for column and row the displayed colour is the average of the 2
    public BufferedImage generateGradient(int width, int height , ColourComponent red, ColourComponent green, ColourComponent blue){
        //Width and height in pixels
        // Checking the arguments are what is expected
        if (red.getColour() != Colours.RED) throw new IllegalArgumentException("Red Component must actually be red");
        if (green.getColour() != Colours.GREEN) throw new IllegalArgumentException("Green Component must actually be green");
        if (blue.getColour() != Colours.BLUE) throw new IllegalArgumentException("Blue Component must actually be blue");
        if (width <=0) throw new IllegalArgumentException("Width must be greater than 0px");
        if (height <=0) throw new IllegalArgumentException("Height must be greater than 0px");

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        int[] pixelMatrix = new int[image.getWidth() * image.getHeight()];
        float r=0.0f,g=0.0f,b=0.0f;
        for (int y = 0; y < image.getHeight(); y++){ // For each row
            switch(red.getRowFunction()){ // Decide what each pixel is going to do for each row
                case INCREASE:
                    r = (float)y/image.getHeight();
                    break;
                case DECREASE:
                    r = 1.0f - (float)y/(image.getHeight()-1);
                    break;
                case SIN:
                    break;
                case COS:
                    break;
                case CONSTANT:
                    r = red.getRowValue()/255.0f;
                    break;
                case NONE:
                    break;
            }
            switch(green.getRowFunction()){
                case INCREASE:
                    g = (float)y/image.getHeight();
                    break;
                case DECREASE:
                    g = 1.0f - (float)y/image.getHeight();
                    break;
                case SIN:
                    break;
                case COS:
                    break;
                case CONSTANT:
                    g = green.getRowValue()/255.0f;
                    break;
                case NONE:
                    break;
            }
            switch(blue.getRowFunction()){
                case INCREASE:
                    b = (float)y/image.getHeight();
                    break;
                case DECREASE:
                    b = 1.0f - (float)y/image.getHeight();
                    break;
                case SIN:
                    break;
                case COS:
                    break;
                case CONSTANT:
                    b = blue.getRowValue()/225.0f;
                    break;
                case NONE:
                    break;
            }
            for (int x= 0; x < image.getWidth(); x++){ // For each pixel in each row
                // Decide what to do each pixel
                switch(red.getColFunction()){
                    case INCREASE:
                        r = (float)x/(image.getWidth()-1);
                        break;
                    case DECREASE:
                        r = 1.0f - (float)x/(image.getWidth()-1);
                        break;
                    case SIN:
                        break;
                    case COS:
                        break;
                    case CONSTANT:
                        r = red.getColValue()/255.0f;
                        break;
                    case NONE:
                        break;
                }
                switch(green.getColFunction()){
                    case INCREASE:
                        g = (float)x/(image.getWidth()-1);
                        break;
                    case DECREASE:
                        g = 1.0f - (float)x/(image.getWidth()-1);
                        break;
                    case SIN:
                        break;
                    case COS:
                        break;
                    case CONSTANT:
                        g = green.getColValue()/255.0f;
                        break;
                    case NONE:
                        break;
                }
                switch(blue.getColFunction()){
                    case INCREASE:
                        b = (float)x/(image.getWidth()-1);
                        break;
                    case DECREASE:
                        b = 1.0f - (float)x/(image.getWidth()-1);
                        break;
                    case SIN:
                        break;
                    case COS:
                        break;
                    case CONSTANT:
                        b = blue.getColValue()/255.0f;
                        break;
                    case NONE:
                        break;
                }

                pixelMatrix[y*image.getWidth() + x] = new Color(r,g,b).getRGB();
            }
        }

        image.getRaster().setDataElements(0, 0, image.getWidth(), image.getHeight(), pixelMatrix);
        return image;
    }

    BufferedImage createRandomImage(int width, int height){ // Creates an RGB image of randomly coloured pixels
        int[] pixelMatrix = new int[width*height]; // Is the size of the width times the height because that is how many pixels there are in the image
        for (int i = 0; i < width*height; i++){
            pixelMatrix[i] = new Color((int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255)).getRGB();
        }
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); // Creates a buffered image using RGB (no alpha) colours
        image.getRaster().setDataElements(0,0,width,height,pixelMatrix); // Sets all image pixels to the content of the pixel matrix
        return image;
    }

    private void wipeImage(BufferedImage image, Color colour){ // Completely wipes an image to the set colour
        int[] colorMatrix = new int[image.getWidth()*image.getHeight()];
        for (int i = 0; i < image.getWidth()*image.getHeight(); i++){
            colorMatrix[i] = colour.getRGB();
        }
        image.getRaster().setDataElements(0,0,image.getWidth(),image.getHeight(),colorMatrix);
    }

    File createFile(String path) throws IOException { // Creates a file adding a increasing number to stop the file conflicting with existing files
        File f = new File(path.substring(0,path.lastIndexOf("/"))); // Gets the all of the string before the last / (eg the directory of the file)
        if (f.mkdirs()){
            System.out.println("New folder @ " + f.getPath());
        }

        File file = new File(path);
        if (!file.createNewFile()) {
            String[] splitName = path.split("\\.");
            String name = splitName[0];
            String type = splitName[1];
            splitName = null;
            int i = 0;
            do {
                i++;
                file = new File(name + "#" +i + "." + type);
            } while (!file.createNewFile());
        }
        return file;
    }
}