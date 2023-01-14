package com.e.testgame.MathClasses;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.NonNull;

import com.e.testgame.GameClasses.src.Figure;
import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;

import java.util.List;
/** Representation of OBB(Oriented-Bounded Box). This term means that the quadrilateral can be in any orientation in space, not even parallel to the axes.
 *This can be used for any rotating quads, but in my game I use this for the blocks that make up the figure
 * */
public class OBB extends Rigidbody2D {

    private Vector2f min, max;
    private Vector2f resultVelocity;
    private Figure ownerFigure;
    private Point center, futureCenter;
    private float rotation, difference, targetPos;
    private boolean onBorderLeft, onBorderRight;
    private boolean isGrounded;
    private boolean needCorrectionM, needFinalCorrectionM, correctionOnGround;
    private boolean blockBusyMoving, blockBusyRotation, blockBusyCorrection;
    private Vector2f[] vertices;
    private int numColumn;
    private int numRow;
    private int maxNumOfColumn;
    public int height, width, rotateCount;
    public Rigidbody2D _rigidBody;
    public String rotateSide, translateSide;
    public float widthFrag, heightFrag, firstDif, secondDif;


    public OBB(Vector2f min, Vector2f max, float rotation) {
        this.min = min;
        this.max = max;
        center = new Point((min.x + max.x) / 2,
                (min.y + max.y) / 2);

        this.rotation = rotation;
        _rigidBody = new Rigidbody2D();
        _rigidBody.setMass(100f);

        rotateSide = "";
        translateSide = "";
        rotateCount = 0;
        numColumn = 0;

        generateVertices();

    }

    public OBB(Point center, int width, int height, float rotation) {
        this.min = new Vector2f(center.getX() - width, center.getY() - height);
        this.max = new Vector2f(center.getX() + width, center.getY() + height);
        this.center = center;

        this.height = height;
        this.width = width;

        this.rotation = rotation;
        _rigidBody = new Rigidbody2D();
        _rigidBody.setMass(100f);

        rotateSide = "";
        translateSide = "";
        rotateCount = 0;
        numColumn = 0;

        generateVertices();

    }

    public OBB(int column, int row, float widthFrag, float heightFrag, int maxColumn, float rotation) {
        this.center = new Point((widthFrag) * column + ((widthFrag) / 2), (heightFrag * row) + ((widthFrag) / 2));
        this.min = new Vector2f(center.getX() - widthFrag / 2, center.getY() - widthFrag / 2);
        this.max = new Vector2f(center.getX() + widthFrag / 2, center.getY() + widthFrag / 2);

        this.widthFrag = widthFrag;
        this.heightFrag = heightFrag;
        this.rotation = rotation;
        _rigidBody = new Rigidbody2D();
        _rigidBody.setMass(100f);
        resultVelocity = new Vector2f();


        correctionOnGround = false;
        needCorrectionM = false;
        rotateSide = "";
        translateSide = "";
        rotateCount = 0;
        numColumn = column;
        numRow = row;
        maxNumOfColumn = maxColumn;
        generateVertices();
    }


    private static void rotateVertex(Vector2f indices, Point center, float rotationSpeed) {
        float x1 = indices.x - center.getX();
        float y1 = indices.y - center.getY();
        float x2 = (float) (x1 * (Math.cos(rotationSpeed * (Math.PI / 180))) - y1 * (Math.sin(rotationSpeed * (Math.PI / 180))));
        float y2 = (float) (x1 * (Math.sin(rotationSpeed * (Math.PI / 180))) + y1 * (Math.cos(rotationSpeed * (Math.PI / 180))));
        indices.x = x2 + center.getX();
        indices.y = y2 + center.getY();
    }


    /**
     * Prevents the block from moving if there are other blocks on the sides
     */
    public boolean preventTranslation(List<Figure> allFigure, String side) {

        boolean cancelTranslation = false;

        if (side == "left") {
            for (Figure figure : allFigure) {
                if (figure != null && figure != getOwnerFigure()) {
                    for (OBB block : figure.getFigureBlocks()) {
                        if (block != null && this != block && block.getNumColumn() == getNumColumn()-1 && block.getNumRow() == getNumRow()) {
                            cancelTranslation = true;
                        }
                    }
                }
            }
        }
        if (side == "right") {
            for (Figure figure : allFigure) {
                if (figure != null && figure != getOwnerFigure()) {
                    for (OBB block : figure.getFigureBlocks()) {
                        if (block != null && this != block && block.getNumColumn() ==  getNumColumn()+1 && block.getNumRow() ==  getNumRow()) {
                            cancelTranslation = true;
                        }
                    }
                }
            }
        }
        return cancelTranslation;
    }

    /**
     * Prevents the block from rotation if there are blocks in the rotation radius
     */
    public boolean preventRotation(List<Figure> allFigure, String side) {
        Point futureCenter = getRotatedFuturePoint(side);
        boolean cancelRotation = false;
        float difference = 0;
        float firstDif = futureCenter.getX() - (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) + (widthFrag / 2)));
        float secondDif = futureCenter.getX() - (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) - (widthFrag / 2)));


        if (Math.abs(firstDif) <= Math.abs(secondDif)) {
            targetPos = (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) + (widthFrag / 2)));
            difference = firstDif;
        } else {
            targetPos = (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) - (widthFrag / 2)));
            difference = secondDif;
        }
        if (Math.round(futureCenter.getX()) != targetPos && Math.abs(difference) > 10f) {

                futureCenter.setX(targetPos);
        }

        if (futureCenter.getX() < widthFrag / 2) {
            cancelRotation = true;
        }
        if (futureCenter.getX() > widthFrag * (maxNumOfColumn-1) + (widthFrag/2)) {
            cancelRotation = true;
        }

        for (Figure figure : allFigure) {
            if (figure != null && figure != getOwnerFigure()) {
                for (OBB block : figure.getFigureBlocks()) {
                    if (block != null && this != block &&  block.getCenter().getX() == futureCenter.getX() && block.getCenter().getY() == futureCenter.getX()) {
                        cancelRotation = true;
                    }
                }
            }
        }

        return cancelRotation;

    }


    /**
     *After the rotation, the figure is in the wrong position (not in the column). The method checks in which direction is the shortest path and makes a verdict on the need for adjustment
     */
    public void correctPositionOBB(String side) {

        Point futureCenter = getRotatedFuturePoint(side);
        this.futureCenter = futureCenter;

        firstDif = futureCenter.getX() - (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) + (widthFrag / 2)));
        secondDif = futureCenter.getX() - (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) - (widthFrag / 2)));

        if (Math.abs(firstDif) <= Math.abs(secondDif)) {
            targetPos = (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) + (widthFrag / 2)));
            difference = firstDif;
        } else {
            targetPos = (((widthFrag * ((int) (futureCenter.getX() / widthFrag))) - (widthFrag / 2)));
            difference = secondDif;
        }


     //   System.out.println("finalDif " + difference);
        if (Math.round(futureCenter.getX()) != targetPos && Math.abs(difference) > 10f) {
            needCorrectionM = true;
            setBlockBusyCorrection(true);
            getOwnerFigure().setBlockBusyCorrection(true);
        } else {
            needCorrectionM = false;
        }
    }

    /**
     *Returns point after rotation
     */
    @NonNull
    private Point getRotatedFuturePoint(String side) {
        Vector2f[] futureVertices = new Vector2f[]{
                new Vector2f(vertices[0]),
                new Vector2f(vertices[1]),
                new Vector2f(vertices[2]),
                new Vector2f(vertices[3])};

        for (Vector2f vec : futureVertices) {
            if (side == "left") {
                rotateVertex(vec, ownerFigure.getCenter(), -90);
            }
            if (side == "right") {
                rotateVertex(vec, ownerFigure.getCenter(), 90);
            }
        }

        Point futureCenter = new Point(((futureVertices[0].x + futureVertices[3].x) / 2),
                (futureVertices[0].y + futureVertices[3].y) / 2);

        return futureCenter;
    }
    /**
     *Smoothly aligns block to the normal position
     */
    private void positionAdjustment(float speed) {
        System.out.println("dif" + difference);
        if (difference != 0) {
            if (difference > 0) {

                if (difference < speed) {
                    for (Vector2f vec : vertices) {
                        vec.sub(difference, 0);
                    }
                    difference = 0;
                }
                difference -= speed;
                for (Vector2f vec : vertices) {
                    vec.sub(speed, 0);
                }
            }
            if (difference < 0) {
                if (difference > -speed) {
                    for (Vector2f vec : vertices) {
                        vec.add(difference, 0);
                    }
                    difference = 0;
                }
                difference += speed;
                for (Vector2f vec : vertices) {
                    vec.add(speed, 0);
                }
            }
            updateCenter();
        } else {
            onBorderCheck();
            getOwnerFigure().setBlockBusyCorrection(false);
            needCorrectionM = false;
            needFinalCorrectionM = true;

        }
    }

    /**
         *Rotates all vertices of box about the center
     */
    private void verticesRotationAboutCenter(float rotationSpeed, Point center) {
        rotation += rotationSpeed;
        for (Vector2f vec : vertices) {
            rotateVertex(vec, center, rotationSpeed);
        }
    }

    private void updateColumn() {

        numColumn = (int) (this.center.getX() / widthFrag);

    }

    private void updateRow() {

        numRow = (int) (this.center.getY() / heightFrag);

    }


    /**
     * Smoothly rotates the block in one direction (depending on the input parameter) by 90 degrees.
     */
    private void rotateToSide(int speed, Point center) {
        if (!isBlockBusyMoving()) {
            switch (rotateSide) {
                case "left":

                    if (rotation != 90 * rotateCount) {
                        verticesRotationAboutCenter(-speed, center);
                        setBlockBusyRotation(true);
                        getOwnerFigure().setBlockBusyRotation(true);
                    } else {
                        setBlockBusyRotation(false);
                        getOwnerFigure().setBlockBusyRotation(false);
                        rotateSide = "";
                        updateColumn();
                    }
                    break;
                case "right":

                    if (rotation != 90 * rotateCount) {
                        verticesRotationAboutCenter(speed, center);
                        setBlockBusyRotation(true);
                        getOwnerFigure().setBlockBusyRotation(true);
                    } else {
                        setBlockBusyRotation(false);
                        getOwnerFigure().setBlockBusyRotation(false);
                        rotateSide = "";
                        updateColumn();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    public Figure getOwnerFigure() {
        return ownerFigure;
    }

    public void setOwnerFigure(Figure ownerFigure) {
        this.ownerFigure = ownerFigure;
    }

    public boolean isBlockBusyMoving() {
        return blockBusyMoving;
    }

    public void setBlockBusyMoving(boolean blockBusyMoving) {
        this.blockBusyMoving = blockBusyMoving;
    }

    public boolean isGrounded() {
        return isGrounded;
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
    }

    public boolean isBlockBusyRotation() {
        return blockBusyRotation;
    }

    public void setBlockBusyRotation(boolean blockBusyRotation) {
        this.blockBusyRotation = blockBusyRotation;
    }

    public boolean isBlockBusyCorrection() {
        return blockBusyCorrection;
    }

    public void setBlockBusyCorrection(boolean blockBusyCorrection) {
        this.blockBusyCorrection = blockBusyCorrection;
    }

    public boolean isOnBorderLeft() {
        return onBorderLeft;
    }

    public void setOnBorderLeft(boolean onBorderLeft) {
        this.onBorderLeft = onBorderLeft;
    }

    public void setVertices(Vector2f[] vertices) {
        this.vertices = vertices;
    }

    public boolean isOnBorderRight() {
        return onBorderRight;
    }

    public void setOnBorderRight(boolean onBorderRight) {
        this.onBorderRight = onBorderRight;
    }

    public Vector2f getResultVelocity() {
        return resultVelocity;
    }

    public int getNumRow() {
        return numRow;
    }

    public int getMaxNumOfColumn() {
        return maxNumOfColumn;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public Point getCenter() {
        return center;
    }

    public float getRotation() {
        return rotation;
    }

    public int getNumColumn() {
        return numColumn;
    }

    public void setNumColumn(int numColumn) {
        this.numColumn = numColumn;
    }

    /**
     * Generates 4 vertices based on two vertices (min, max).
     * Min - top right vertex, Max - bottom left vertex
     */
    public void generateVertices() {
        Vector2f min = this.min;
        Vector2f max = this.max;

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(max.x, min.y),
                new Vector2f(min.x, max.y), new Vector2f(max.x, max.y)
        };
        if (this.vertices == null) {
            this.vertices = vertices;
        }
    }

    public Vector2f[] getVertices() {
        return this.vertices;
    }


    /**
     *Smoothly moves the block in one of the directions depending on the parameter.
     */
    private void translateToSide(float speed) {
        if (!getOwnerFigure().isGrounded()) {
            switch (translateSide) {
                case "left":
                    if (numColumn < 0) {
                        numColumn = 0;
                    }
                    if (numColumn >= 0 && !onBorderLeft) {

                        if (center.getX() > (widthFrag * numColumn) + (widthFrag / 2)) {
                            verticesTranslation(-speed);
                            setBlockBusyMoving(true);
                            getOwnerFigure().setBlockBusyMoving(true);
                        } else {
                            getOwnerFigure().setBlockBusyMoving(false);
                            setBlockBusyMoving(false);
                            translateSide = "";
                            onBorderCheck();
                        }
                    }
                    break;
                case "right":
                    if (numColumn > (getMaxNumOfColumn() - 1)) {
                        numColumn = (getMaxNumOfColumn() - 1);
                    }
                    if (numColumn <= (getMaxNumOfColumn() - 1) && !onBorderRight) {

                        if (center.getX() < (widthFrag * numColumn) + (widthFrag / 2)) {
                            verticesTranslation(speed);
                            setBlockBusyMoving(true);
                            getOwnerFigure().setBlockBusyMoving(true);
                        } else {
                            getOwnerFigure().setBlockBusyMoving(false);
                            setBlockBusyMoving(false);
                            translateSide = "";
                            onBorderCheck();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     *Checks if figure is on border
     */
    private void onBorderCheck() {
        if (numColumn == 0) {
            onBorderLeft = true;
        } else {
            onBorderLeft = false;
        }

        if (numColumn == getMaxNumOfColumn() - 1) {

            onBorderRight = true;
        } else {
            onBorderRight = false;
        }
    }


    private void verticesTranslation(float speed) {
        for (Vector2f vec : vertices) {
            vec.add(new Vector2f(speed, 0));
        }
    }


    /**
     * Adjusts the block position for the last time after another adjustment
     */
    private void positionCorrection() {
        if (!isBlockBusyRotation() && getOwnerFigure().getName() != 'O') {
            if (needCorrectionM) {
                positionAdjustment(3f);
            } else if (center.getX() != targetPos && targetPos != 0 && needFinalCorrectionM) {

                for (Vector2f vec : vertices) {
                    vec.add(new Vector2f(targetPos - center.getX(), 0));
                }
                setBlockBusyCorrection(false);
                needFinalCorrectionM = false;

            }

            updateCenter();
        }
    }
    /**
     *Adjusts the position of the block after landing. (It is necessary if, for example, the block fell while moving to one of the sides and because of this it landed crookedly)
     */
    public void correctionOnGroundOBB() {
        String side = translateSide;
        if (getOwnerFigure().isGrounded() && correctionOnGround == false) {

            float nearGoal = 0;
            float firstGoal = (((widthFrag * ((int) (center.getX() / widthFrag))) + (widthFrag / 2)));
            float secondGoal = (((widthFrag * ((int) (center.getX() / widthFrag))) - (widthFrag / 2)));

            float firstPath = center.getX() - firstGoal;
            float secondPath = center.getX() - secondGoal;

           // System.out.println("Goal1:" + firstPath);
           // System.out.println("Goal2:" + secondPath);

            if (Math.abs(firstPath) <= Math.abs(secondPath)) {
                nearGoal = firstPath;
            } else {
                nearGoal = secondPath;
            }

            //System.out.println("ResGoal:" + nearGoal);

            float speedDiff = 30;

            if(side == "right")
            {
                speedDiff = 30;
            }
            if(side == "left") {
                speedDiff = -30;
            }
            if(side == "")
            {speedDiff = 0;}


            for (Vector2f vec : vertices) {

              //  System.out.println("vec1:" + vec);

                        vec.sub(new Vector2f(nearGoal + speedDiff, 0));
              //  System.out.println("vec2:" + vec);
            }
            updateColumn();
            updateCenter();
                correctionOnGround = true;
        }
    }


    public void turnOffGravity() {

        if (isGrounded()) {
            this._rigidBody.zeroForcesOnSide("bottom");
        }
    }

    public void update(Point center) {

        updateCenter();
        positionCorrection();
        rotateToSide(10, center);
        translateToSide(30);
        applyGravity();
        updateRow();


    }

    private void applyGravity() {
        resultVelocity = _rigidBody.getResultVelocity();

        for (Vector2f vec : vertices) {
            vec.add(resultVelocity);
        }
    }

    private void updateCenter() {
        this.center = new Point(Math.round((vertices[0].x + vertices[3].x) / 2),
                (vertices[0].y + vertices[3].y) / 2);
    }

    public void draw(Canvas canvas, int color) {

        Path block = new Path();
        block.reset();
        block.moveTo(vertices[0].x, vertices[0].y);
        block.lineTo(vertices[1].x, vertices[1].y);
        block.lineTo(vertices[3].x, vertices[3].y);
        block.lineTo(vertices[2].x, vertices[2].y);
        block.close();

        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(color);
        fillPaint.setAntiAlias(true);
        fillPaint.setDither(true);


        canvas.drawPath(block, fillPaint);

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);

        canvas.drawPath(block, borderPaint);


    }

}

