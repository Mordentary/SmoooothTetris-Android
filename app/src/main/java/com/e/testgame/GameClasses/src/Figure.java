package com.e.testgame.GameClasses.src;

import android.graphics.Canvas;

import com.e.testgame.GameClasses.Grid;
import com.e.testgame.GameClasses.IntersectionManager;
import com.e.testgame.GameEngine.Physics2D.Physics2D;
import com.e.testgame.MathClasses.AABB;
import com.e.testgame.MathClasses.MathHelper;
import com.e.testgame.MathClasses.OBB;
import com.e.testgame.MathClasses.Point;

import java.util.ArrayList;
import java.util.List;

public class Figure {

//16
    private OBB[] figure;
    private char name;
    private Point center;
    public boolean onBorderCheckR, onBorderCheckL;
    private boolean blockBusyMoving;
    private boolean blockBusyRotation;
    private boolean blockBusyCorrection;
    private boolean isGrounded;
    private int color;
    private boolean isFigureDamaged;

    public Figure(OBB reference, Physics2D P2D, char name) {
        figure = new OBB[4];
        figure[0] = new OBB(reference.getNumColumn(), reference.getNumRow(), (int) reference.widthFrag, reference.heightFrag, reference.getMaxNumOfColumn(), 0);
        this.name = name;
        this.color = MathHelper.randomColor();
        this.isFigureDamaged = false;
        generateFigure();
        updateCenter(0);
        addGravity(P2D);
    }

    /** Applies gravity settings */
    private void addGravity(Physics2D P2D) {
        for (OBB block : getFigureBlocks()) {
            P2D.addRigidbody(block._rigidBody);
        }
    }

    /** Builds a figure by blocks, setting the coordinates of the blocks depending on the letter parameter */
    private void generateFigure() {
        switch (name) {

            case 'I':
                figure[1] = new OBB(figure[0].getNumColumn(), (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn(), (figure[1].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn(), (figure[2].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;
            case 'L':
                figure[1] = new OBB(figure[0].getNumColumn() + 1, figure[0].getNumRow(), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn(), (figure[1].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn(), (figure[2].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;
            case 'J':
                figure[1] = new OBB(figure[0].getNumColumn() - 1, figure[0].getNumRow(), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn(), (figure[1].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn(), (figure[2].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;
            case 'N':
                figure[1] = new OBB(figure[0].getNumColumn() - 1, figure[0].getNumRow(), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn() - 1, (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn() - 2, (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;
            case 'O':
                figure[1] = new OBB(figure[0].getNumColumn(), figure[0].getNumRow() - 1, (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn() + 1, (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn() + 1, (figure[0].getNumRow()), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;
            case 'Z':
                figure[1] = new OBB(figure[0].getNumColumn() + 1, figure[0].getNumRow(), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn() + 1, (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn() + 2, (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;
            case 'T':
                figure[1] = new OBB(figure[0].getNumColumn() + 1, figure[0].getNumRow(), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[2] = new OBB(figure[0].getNumColumn() + 2, figure[0].getNumRow(), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                figure[3] = new OBB(figure[0].getNumColumn() + 1, (figure[0].getNumRow() - 1), (int) figure[0].widthFrag, figure[0].heightFrag, figure[0].getMaxNumOfColumn(), 0);
                break;

        }
        figure[0].setOwnerFigure(this);
        figure[1].setOwnerFigure(this);
        figure[2].setOwnerFigure(this);
        figure[3].setOwnerFigure(this);
    }

    /**Checks if the shape is on the screen border*/
    private void checkFigureOnBorder() {
        onBorderCheckL = false;
        onBorderCheckR = false;
        for (int i = 0; i < figure.length; i++) {
            if (figure[i] != null) {
                if (figure[i].isOnBorderLeft() || figure[i].isOnBorderRight()) {
                    onBorderCheckL = figure[i].isOnBorderLeft();
                    onBorderCheckR = figure[i].isOnBorderRight();
                    break;
                }
            }
        }
        for (OBB block : figure) {
            if (block != null) {
                block.setOnBorderLeft(onBorderCheckL);
                block.setOnBorderRight(onBorderCheckR);
            }
        }
    }


    public char getName() {
        return name;
    }

    public boolean isBlockBusyMoving() {
        return blockBusyMoving;
    }

    public void setBlockBusyMoving(boolean blockBusyMoving) {
        this.blockBusyMoving = blockBusyMoving;
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
    public void printNumOfColumn() {
        System.out.println(
                " Block 0: " + figure[0].getNumColumn() +
                        " Block 1: " + figure[1].getNumColumn() +
                        " Block 2: " + figure[2].getNumColumn() +
                        " Block 3: " + figure[3].getNumColumn()
        );
    }

    public void printBlocksGrounded() {
        System.out.println(
                " Block 0: " + figure[0].isGrounded() +
                        " Block 1: " + figure[1].isGrounded() +
                        " Block 2: " + figure[2].isGrounded() +
                        " Block 3: " + figure[3].isGrounded()
        );
    }

    public void printCenters() {
        System.out.println(
                " Center 0: " + figure[0].getCenter().getX() +
                        " Center 1: " + figure[1].getCenter().getX() +
                        " Center 2: " + figure[2].getCenter().getX() +
                        " Center 3: " + figure[3].getCenter().getX()
        );
    }

    public void printNumOfRow() {
        System.out.println(
                " Block 0: " + figure[0].getNumRow() +
                        " Block 1: " + figure[1].getNumRow() +
                        " Block 2: " + figure[2].getNumRow() +
                        " Block 3: " + figure[3].getNumRow()
        );
    }


    public void printBordersL() {
        System.out.println(
                " BlockL 1: " + figure[0].isOnBorderLeft() +
                        " BlockL 2: " + figure[1].isOnBorderLeft() +
                        " BlockL 3: " + figure[2].isOnBorderLeft() +
                        " BlockL 4: " + figure[3].isOnBorderLeft() +
                        " FigureL: " + onBorderCheckL);
    }

    public void printBordersR() {
        System.out.println(
                " BlockR 1: " + figure[0].isOnBorderRight() +
                        " BlockR 2: " + figure[1].isOnBorderRight() +
                        " BlockR 3: " + figure[2].isOnBorderRight() +
                        " BlockR 4: " + figure[3].isOnBorderRight() +
                        " FigureR: " + onBorderCheckL);
    }

    /** Takes the sides in which the figure must be rotated 90 degrees  */
    public void figureRotate(String side) {
        if (side == "left") {
            for (OBB block : figure) {
                if (block != null) {
                    block.rotateSide = "left";
                    block.rotateCount--;
                }
            }
        }
        if (side == "right") {
            for (OBB block : figure) {
                if (block != null) {
                    block.rotateSide = "right";
                    block.rotateCount++;
                }
            }
        }
    }

    public boolean isGrounded() {
        return isGrounded;
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
    }

    public Point getCenter() {
        return center;
    }

    public boolean isFigureDamaged() {
        return isFigureDamaged;
    }

    public void setFigureDamaged(boolean figureDamaged) {
        isFigureDamaged = figureDamaged;
    }

    public OBB[] getFigureBlocks() {
        return figure;
    }

    /** Takes the sides in which the figure must be translated */
    public void figureTranslate(String side) {
        if (side == "left" && !onBorderCheckL) {
            for (OBB block : figure) {
                if (block != null) {
                    block.translateSide = "left";
                    block.setNumColumn(block.getNumColumn() - 1);
                }
            }
        }
        if (side == "right" && !onBorderCheckR) {
            for (OBB block : figure) {
                if (block != null) {
                    block.translateSide = "right";
                    block.setNumColumn(block.getNumColumn() + 1);
                }
            }
        }
    }

    /** Prevents figures from rotating near the screen borders or near other figures **/
    public boolean preventFigureRotation(List<Figure> allFigure, String side)
    {
        for (OBB block : figure) {
            if (block != null) {
              if(block.preventRotation(allFigure, side)) {
                  return false;
              }
            }
        }
        return true;
    }
public boolean isFigureRotating()
{
    for (OBB block : figure) {
        if (block != null) {
            if(block.isBlockBusyRotation()) {
                return true;
            }
        }
    }
    return false;
}
    /** Prevents figure from translating into other figures from the side **/
    public boolean preventFigureTranslation(List<Figure> allFigure, String side)
    {
        for (OBB block : figure) {
            if (block != null) {
                if(block.preventTranslation(allFigure, side)) {
                    return false;
                }
            }
        }
        return true;
    }
    /** Update figure every frame **/
    public void update(ArrayList<Figure> allFigure, AABB floor) {
        for (OBB block : figure) {
            if (block != null) {
                updateCenter(block.getResultVelocity().y);
                break;
            }
        }
        if (!isGrounded()) {
            for (OBB block : figure) {
                if (block != null && !block.isGrounded()) {
                    block.update(this.center);
                }
            }
        }
        checkCollision(allFigure, floor);
        setGroundingOfFigure();
        checkFigureOnBorder();
        correctionOnGround();
    }
    /** Straightens the figure if it is crooked **/
    private void correctionOnGround() {
        if (isGrounded()) {
            for (OBB block : figure) {
                if (block != null) {
                    block.correctionOnGroundOBB();
                }
            }
        }
    }
    /** Starts adjusting the figure's position based on the future rotation  **/
    public void correctPosition(String side) {
        for (OBB block : figure) {
            if (block != null) {
                block.correctPositionOBB(side);
            }
        }
    }
    /** Removes gravity from all parts of the destroyed figure after all of its parts have landed **/
    private void setGroundingOfFigure() {
        int blockExistCounter = 0;
        int blockIsGroundedCounter = 0;
        for (OBB block : figure) {
            if (block != null) {
                blockExistCounter++;
                if (block.isGrounded()) {
                    blockIsGroundedCounter++;
                }
            }
        }
        if (blockExistCounter == blockIsGroundedCounter) {
            this.setGrounded(true);
        }
    }
    /**Checks for all collisions of a figure with other figures, floor, etc. Depending on whether the figure is damaged or not,
    there are two options for perceiving the figure: as a set of blocks (if it is destroyed) and as one whole (if the figure is intact) **/
    private void checkCollision(ArrayList<Figure> allFigure, AABB floor) {
        if (!isFigureDamaged()) {
            for (OBB block : figure) {
                if (block != null) {
                    if (IntersectionManager.AABBAndOOB(floor, block)) {

                        setGrounded(true);
                        break;
                    }
                }
            }
            for (OBB dynamicBlock : figure) {

                for (Figure figure : allFigure) {

                    if (dynamicBlock != null && figure != null) {

                        for (OBB staticBlock : figure.getFigureBlocks()) {

                            if (staticBlock != null && dynamicBlock.getNumColumn() == staticBlock.getNumColumn() && figure.isGrounded() && !isGrounded() && IntersectionManager.OBBAndOOB(staticBlock, dynamicBlock)) {
                                setGrounded(true);
                            }
                        }
                    }
                }
            }
        } else {

            for (OBB block : figure) {
                if (block != null) {
                    if (IntersectionManager.AABBAndOOBVer2(floor, block)) {
                        block.setGrounded(true);
                    }
                }
            }

            for (OBB dynamicBlock : figure) {

                for (Figure figure : allFigure) {

                    if (dynamicBlock != null && figure != null && !dynamicBlock.isGrounded()) {

                        for (OBB staticBlock : figure.getFigureBlocks()) {

                            if (staticBlock != null && staticBlock != dynamicBlock && dynamicBlock.getNumColumn() == staticBlock.getNumColumn() && staticBlock.isGrounded() && IntersectionManager.OBBAndOOBVer2(staticBlock, dynamicBlock)) {
                                dynamicBlock.setGrounded(true);
                            }
                        }
                    }
                }
            }

            /*for (OBB selfBlock1 : figure) {

                for (OBB selfBlock2 : figure) {

                    if (selfblock1 && selfBlock1 != selfBlock2) {
                        if (IntersectionManager.OBBAndOOB(selfBlock1, selfBlock2)) {
                            selfBlock2.setGrounded(true);
                        }
                    }

                }
            }*/
        }
        if (isGrounded()) {
            for (OBB block : figure) {
                if (block != null) {
                    block.setGrounded(true);
                    block._rigidBody.zeroForcesOnSide("bottom");
                }
            }
        } else {
            for (OBB block : figure) {
                if (block != null) {
                     block.turnOffGravity();
                }
            }
        }
    }

    private void updateCenter(float gravity) {
        float x = 0;
        float y = 0;
        for (OBB block : figure) {
            if (block != null) {
                x += block.getCenter().getX();
                y += block.getCenter().getY();
            }
        }

        this.center = new Point(x / figure.length, y / figure.length + gravity);

      /*  this.center = new Point(((figure[0].getCenter().getX() + figure[1].getCenter().getX() + figure[2].getCenter().getX() + figure[3].getCenter().getX()) / 4),
                ((figure[0].getCenter().getY() + figure[1].getCenter().getY() + figure[2].getCenter().getY() + figure[3].getCenter().getY()) / 4) + gravity);*/
    }

    public void draw(Canvas canvas) {
        for (OBB block : figure) {
            if (block != null) {
                block.draw(canvas, color);
            }
        }
    }
}
