package com.e.testgame.GameClasses.src;

import com.e.testgame.GameClasses.Grid;
import com.e.testgame.MathClasses.OBB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BlockDeleter {

//4
    /** Checks all rows for fullness and returns the full row */
    public static int findFullRow(ArrayList<Figure> allFigure, Grid grid) {
        int counter = 0;
        for (int i = 0; i < grid.getMaxNumOfRow(); i++) {
            for (Figure figure : allFigure) {
                for (OBB block : figure.getFigureBlocks()) {
                    if (figure != null && block != null) {
                        if (figure.isGrounded() && block.getNumRow() == i) {
                            counter++;
                        }
                    }
                }
            }

            if (counter == grid.getMaxNumOfColumn()) {
                return i;
            }
            counter = 0;
        }


        return -1;
    }

    /** Turns on gravity for blocks that are above the deleted line */
    private static void turnOnGravity(ArrayList<Figure> allFigure, int fullColumn) {
        if (fullColumn != -1) {
            for (Figure figure : allFigure) {
                for (OBB block : figure.getFigureBlocks()) {
                    if (figure != null && block != null) {
                        for (int i = fullColumn; i > 0; i--) {
                            if (figure.isGrounded() && block.getNumRow() == i) {
                                figure.setGrounded(false);
                                for (OBB innerBlock : figure.getFigureBlocks()) {
                                    if(innerBlock != null) {
                                        innerBlock.setGrounded(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /** Removes blocks that are in full row */
    public static void deleteBlocks(ArrayList<Figure> allFigure, Grid grid) {
        int fullRow = findFullRow(allFigure, grid);

        for (Figure figure : allFigure) {
            for (OBB block : figure.getFigureBlocks()) {
                if (figure != null && block != null) {
                    if (figure.isGrounded() && block.getNumRow() == fullRow) {
                        for (int i = 0; i < figure.getFigureBlocks().length; i++) {
                            if (figure.getFigureBlocks()[i] != null && figure.getFigureBlocks()[i].equals(block)) {
                                figure.getFigureBlocks()[i] = null;
                                figure.setFigureDamaged(true);
                            }
                        }
                    }
                }
            }
        }
        cleanNullFigure(allFigure);
        turnOnGravity(allFigure, fullRow);
    }

    /** Deletes figures, that doesn't have any blocks*/
    private static void cleanNullFigure(ArrayList<Figure> allFigure) {
        int counter = 0;
        Queue<Figure> figureToRemove = new LinkedList<>();
        for (Figure figure : allFigure) {
            for (OBB block : figure.getFigureBlocks()) {
                if (block == null) {
                    counter++;
                }
            }
            if (counter == figure.getFigureBlocks().length) {
                figureToRemove.add(figure);
            }
            counter = 0;
        }
        int num = 0;
        while (!figureToRemove.isEmpty()) {
            num++;
            allFigure.remove(figureToRemove.poll());
        }
        if (num != 0) {
           // System.out.println("number" + num);
        }
    }
}


