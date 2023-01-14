package com.e.testgame.MathClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;

import java.util.ArrayList;
import java.util.List;



/** Representation of AABB(Axis-Aligned Bounding Box). This term means that the quadrilateral is parallel to the axes, i.e. it cannot be rotated.
 * Such properties are useful for static objects such as floors.
 * */
public class AABB extends Rigidbody2D{



    public Vector2f min, max;


  public AABB(Vector2f min, Vector2f max)
  {

      this.min = min;
      this.max = max;


  }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawLine(min.x,min.y,max.x,min.y,paint);
        canvas.drawLine(min.x,min.y,min.x,max.y,paint);
        canvas.drawLine(min.x,max.y,max.x,max.y,paint);
        canvas.drawLine(max.x,min.y,max.x,max.y,paint);
    }


}
