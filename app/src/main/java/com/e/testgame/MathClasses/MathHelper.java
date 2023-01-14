package com.e.testgame.MathClasses;

import android.graphics.Color;

import java.util.Random;

public class MathHelper {


    /**
     * Rotates a point around another point by a specified number of degrees
     */
    public static void rotate(Vector2f vec, float angleDeg, Vector2f origin) {

        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = -(float)Math.cos(Math.toRadians(angleDeg));
        float sin = (float)Math.sin(Math.toRadians(angleDeg));

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }
    /**
     * Generates random char. Every char represents different figures.
     */
    public static char randomCharForFigure()
    {
        Random rnd = new Random();

        switch(rnd.nextInt(7))
        {
            case 0:  return 'L';

            case 1:  return 'O';

            case 2:   return 'I';

            case 3:   return 'Z';

            case 4:   return 'T';

            case 5: return 'N';

            case 6: return 'J';

        }
        return 'I';
    }

    /**
     * Generates random color.
     */
    public static int randomColor() {

        Random rnd  = new Random();

        int R = 65 +(rnd.nextInt(190));
        int G = 65 +(rnd.nextInt(190));
        int B = 65 +(rnd.nextInt(190));
        return Color.rgb(R, G, B);

    }
}
