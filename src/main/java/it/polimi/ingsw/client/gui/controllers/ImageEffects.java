package it.polimi.ingsw.client.gui.controllers;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * This class is used to apply effects to images
 */
public class ImageEffects {

    /**
     * This method is used to reset the zoom effect on an image, scaling it at the normal size
     * @param a it's the image on which the effect will be applied
     * @return false
     */
    public static boolean zoomOut(ImageView a) {
        a.setScaleX(1.0);
        a.setScaleY(1.0);
        return false;
    }

    /**
     * This method is used to set an effect that adds a white glow to an image
     * @param i it's the image on which the effect will be applied
     */
    public static void addWhiteGlow(ImageView i) {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.WHITE);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);

        i.setEffect(borderGlow);
    }

    /**
     * This method is used to set an effect that adds a shadow to an image
     * @param i it's the image on which the effect will be applied
     */
    public static void addShadow(ImageView i) {
        DropShadow borderShadow = new DropShadow();
        borderShadow.setOffsetY(0f);
        borderShadow.setOffsetX(0f);
        borderShadow.setColor(Color.BLACK);
        borderShadow.setWidth(21);
        borderShadow.setHeight(21);
        borderShadow.setRadius(10);

        i.setEffect(borderShadow);
    }

    /**
     * This method is used to set an effect that adds a light blue shadow to an image
     * @param i it's the image on which the effect will be applied
     */
    public static void addLightBluShadow(ImageView i) {
        turnOn(i);

        DropShadow bright = new DropShadow();
        bright.setOffsetY(0f);
        bright.setOffsetX(0f);
        Color brightColor = Color.valueOf(String.valueOf(Color.rgb(92, 236, 255)));
        bright.setColor(brightColor);
        bright.setWidth(21);
        bright.setHeight(21);
        bright.setRadius(10);
        bright.setSpread(0.23);

        i.setEffect(bright);
    }

    /**
     * This method is used to show the image with its normal brightness and contrast values
     * @param i it's the image on which the effect will be applied
     */
    public static void turnOn(ImageView i) {
        ColorAdjust light = new ColorAdjust();
        light.setBrightness(0.0);
        light.setContrast(0.0);

        i.setEffect(light);
    }

    /**
     * This method is used to show the image with less brightness and less contrast,
     * which means that the image is currently unavailable
     * @param i it's the image on which the effect will be applied
     */
    public static void turnOff(ImageView i) {
        ColorAdjust dark = new ColorAdjust();
        dark.setBrightness(-0.61);
        dark.setContrast(-0.52);

        i.setEffect(dark);
    }

    /**
     * This method is used to set a custom cursor to the scene
     * @param scene it's the scene on which the cursor will be customized
     */
    public static void setCursor(Scene scene){
        Image image = new Image("images/texts/cursor.png");
        scene.setCursor(new ImageCursor(image));
    }
}
