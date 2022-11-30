package homework;

import edu.princeton.cs.algs4.Point2D;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuickHullTest {

    @Test
    void findsExtrema() {
        List<Point2D> points = new ArrayList<>();
        points.add(new Point2D(0, 0));
        points.add(new Point2D(1, 1));
        points.add(new Point2D(0, 1));
        points.add(new Point2D(1, 0));
        Line2D line = new QuickHull(points).extrema();
        assertEquals("(0.0, 0.0)", line.a().toString());
        assertEquals("(1.0, 1.0)", line.b().toString());
    }

    @Test
    void findsLeftSet() {
        List<Point2D> points = new ArrayList<>();
        points.add(new Point2D(0, 0));
        points.add(new Point2D(1, 1));
        points.add(new Point2D(0, 1));
        points.add(new Point2D(1, 0));
        Line2D line = new Line2D(new Point2D(0, 0), new Point2D(1, 1));
        List<Point2D> left = new QuickHull(points).leftSet(points, line);
        assertEquals(1, left.size());
        assertTrue(left.contains(new Point2D(0, 1)));
    }

    @Test
    void findsLeftSetForVerticalLine() {
        List<Point2D> points = new ArrayList<>();
        points.add(new Point2D(0, 0));
        points.add(new Point2D(0, 2));
        points.add(new Point2D(-1, 1));
        points.add(new Point2D(1, 1));
        Line2D line = new Line2D(new Point2D(0, 0), new Point2D(0, 2));
        List<Point2D> left = new QuickHull(points).leftSet(points, line);
        assertEquals(1, left.size());
        assertTrue(left.contains(new Point2D(-1, 1)));
    }

    @Test
    void findsFarthestPoint() {
        List<Point2D> lefts = new ArrayList<>();
        lefts.add(new Point2D(1, 2));
        lefts.add(new Point2D(3, 5));
        Line2D line = new Line2D(new Point2D(0, 0), new Point2D(5, 5));
        Point2D p = new QuickHull(lefts).farthestLeft(lefts, line);
        assertEquals(new Point2D(3, 5), p);
    }

    @Test
    void findsHull() {
        List<Point2D> points = new ArrayList<>();
        for (int[] coords : new int[][] {{1, 0}, {2, 4}, {3, 1}, {0, 2}, {5, 2}, {1, 4}, {3, 3}, {2, 0}}) {
            points.add(new Point2D(coords[0], coords[1]));
        }
        QuickHull qh = new QuickHull(points);
        List<Point2D> hull = qh.hull();
        assertEquals(6, hull.size());
        assertTrue(hull.contains(new Point2D(0, 2)));
        assertTrue(hull.contains(new Point2D(1, 0)));
        assertTrue(hull.contains(new Point2D(2, 0)));
        assertTrue(hull.contains(new Point2D(5, 2)));
        assertTrue(hull.contains(new Point2D(1, 4)));
        assertTrue(hull.contains(new Point2D(2, 4)));
        System.out.println(hull);
    }

}