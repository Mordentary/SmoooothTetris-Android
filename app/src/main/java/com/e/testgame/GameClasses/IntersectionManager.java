package com.e.testgame.GameClasses;

import com.e.testgame.MathClasses.AABB;
import com.e.testgame.MathClasses.MathHelper;
import com.e.testgame.MathClasses.OBB;
import com.e.testgame.MathClasses.Vector2f;

public class IntersectionManager {

    /**
     * Checks for collision between floor and block using OOB algorithm
     */
    public static boolean AABBAndOOB(AABB floor, OBB dynamicBlock) {
        Vector2f axesToTest[] = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1), new Vector2f(1, 0)
        };
        MathHelper.rotate(axesToTest[2], dynamicBlock._rigidBody.getRotation(), new Vector2f(dynamicBlock.getCenter().getX(), dynamicBlock.getCenter().getY()));
        MathHelper.rotate(axesToTest[3], dynamicBlock._rigidBody.getRotation(), new Vector2f(dynamicBlock.getCenter().getX(), dynamicBlock.getCenter().getY()));
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(floor, dynamicBlock, axesToTest[i])) {
                return false;
            }

        }
        positionAdjustmentFloor(floor, dynamicBlock);
        return true;
    }

    /**
     * Checks for collision between floor and parts of damaged block using OOB algorithm
     */
    public static boolean AABBAndOOBVer2(AABB floor, OBB dynamicBlock) {
        Vector2f axesToTest[] = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1), new Vector2f(1, 0)
        };
        MathHelper.rotate(axesToTest[2], dynamicBlock._rigidBody.getRotation(), new Vector2f(dynamicBlock.getCenter().getX(), dynamicBlock.getCenter().getY()));
        MathHelper.rotate(axesToTest[3], dynamicBlock._rigidBody.getRotation(), new Vector2f(dynamicBlock.getCenter().getX(), dynamicBlock.getCenter().getY()));
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(floor, dynamicBlock, axesToTest[i])) {
                return false;
            }

        }
        positionAdjustmentFloorVer2(floor, dynamicBlock);
        return true;
    }

    /**
     * Checks for collision between two blocks(static block and dynamic block) using OOB algorithm
     */
    public static boolean OBBAndOOB(OBB staticB, OBB dynamicB) {
        Vector2f axesToTest[] = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1), new Vector2f(1, 0)
        };
        MathHelper.rotate(axesToTest[2], dynamicB._rigidBody.getRotation(), new Vector2f(dynamicB.getCenter().getX(), dynamicB.getCenter().getY()));
        MathHelper.rotate(axesToTest[3], dynamicB._rigidBody.getRotation(), new Vector2f(dynamicB.getCenter().getX(), dynamicB.getCenter().getY()));
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(staticB, dynamicB, axesToTest[i])) {
                return false;
            }
        }
        positionAdjustmentOBB(staticB, dynamicB);
        return true;
    }

    /**
     * Checks for collision between static block(that grounded) and block of damaged figure using OOB algorithm
     */
    public static boolean OBBAndOOBVer2(OBB staticB, OBB dynamicB) {
        Vector2f axesToTest[] = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1), new Vector2f(1, 0)
        };
        MathHelper.rotate(axesToTest[2], dynamicB._rigidBody.getRotation(), new Vector2f(dynamicB.getCenter().getX(), dynamicB.getCenter().getY()));
        MathHelper.rotate(axesToTest[3], dynamicB._rigidBody.getRotation(), new Vector2f(dynamicB.getCenter().getX(), dynamicB.getCenter().getY()));
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(staticB, dynamicB, axesToTest[i])) {
                return false;
            }
        }
        positionAdjustmentOBBVer2(staticB, dynamicB);
        return true;
    }

    /**
     * Aligns to the block the figure landed on
     */
    private static void positionAdjustmentOBB(OBB b1, OBB b2) {
        float difference = findTopEdge(b1) - findBottomEdge(b2);
        if (difference != 0) {
            for (OBB dynamicBlocks : b2.getOwnerFigure().getFigureBlocks()) {
                if (dynamicBlocks != null) {
                    for (int i = 0; i < dynamicBlocks.getVertices().length; i++) {

                        dynamicBlocks.getVertices()[i].y += difference;

                    }
                }
            }
        }
    }

    /**
     * Aligns to the block the block landed on
     */
    private static void positionAdjustmentOBBVer2(OBB b1, OBB b2) {
        float difference = findTopEdge(b1) - findBottomEdge(b2);

        if (b2 != null) {
            for (int i = 0; i < b2.getVertices().length; i++) {

                b2.getVertices()[i].y += difference;

            }
        }

    }

    /**
     * Searches the lowest point of the block
     */
    private static float findBottomEdge(OBB b1) {
        float minY = b1.getVertices()[0].y;
        for (int i = 0; i < b1.getVertices().length; i++) {

            if (b1.getVertices()[i].y > minY) {
                minY = b1.getVertices()[i].y;
            }
        }
        return minY;
    }

    /**
     * Searches the highest point of the block
     */
    private static float findTopEdge(OBB b1) {
        float minY = b1.getVertices()[0].y;
        for (int i = 0; i < b1.getVertices().length; i++) {

            if (b1.getVertices()[i].y < minY) {
                minY = b1.getVertices()[i].y;
            }
        }
        return minY;
    }

    /**
     * Aligns to the floor the Figure landed on
     */
    private static void positionAdjustmentFloor(AABB b1, OBB b2) {

        float difference = b1.min.y - findBottomEdge(b2);
        if (difference != 0) {
            for (OBB dynamicBlocks : b2.getOwnerFigure().getFigureBlocks()) {
                if (dynamicBlocks != null) {
                    for (int i = 0; i < dynamicBlocks.getVertices().length; i++) {

                        dynamicBlocks.getVertices()[i].y += difference;
                    }

                }
            }
        }
    }
    /**
     * Aligns to the floor the block landed on
     */
    private static void positionAdjustmentFloorVer2(AABB b1, OBB b2) {

        float difference = b1.min.y - findBottomEdge(b2);

        if (b2 != null) {
            for (int i = 0; i < b2.getVertices().length; i++) {

                b2.getVertices()[i].y += difference;
            }

        }
    }


// =============================================================================
// SAT helpers
// =============================================================================



    private static boolean overlapOnAxis(AABB b1, OBB b2, Vector2f axis) {
        Vector2f interval1 = getInterval(b1, axis);
        Vector2f interval2 = getInterval(b2, axis);
        return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
    }

    private static boolean overlapOnAxis(OBB b1, OBB b2, Vector2f axis) {
        Vector2f interval1 = getInterval(b1, axis);
        Vector2f interval2 = getInterval(b2, axis);
        return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
    }

    private static Vector2f getInterval(AABB rect, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f min = rect.min;
        Vector2f max = rect.max;

        Vector2f vertices[] = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        result.x = axis.dot(vertices[0]);
        result.y = result.x;
        for (int i = 1; i < 4; i++) {
            float projection = axis.dot(vertices[i]);
            if (projection < result.x) {
                result.x = projection;
            }
            if (projection > result.y) {
                result.y = projection;
            }
        }
        return result;
    }

    private static Vector2f getInterval(OBB rect, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f vertices[] = rect.getVertices();

        result.x = axis.dot(vertices[0]);
        result.y = result.x;
        for (int i = 1; i < 4; i++) {
            float projection = axis.dot(vertices[i]);
            if (projection < result.x) {
                result.x = projection;
            }
            if (projection > result.y) {
                result.y = projection;
            }
        }
        return result;
    }
}




