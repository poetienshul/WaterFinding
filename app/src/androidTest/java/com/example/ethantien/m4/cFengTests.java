package com.example.ethantien.m4;
import org.junit.Before;
import org.junit.Test;
import android.util.SparseArray;

import com.example.ethantien.m4.model.Node;
import com.example.ethantien.m4.model.vars;
import com.jjoe64.graphview.series.DataPoint;

import static org.junit.Assert.*;

/**
 * Created by Changliang Feng on 4/9/17.
 * Tester for getPoints() in vars.java which is required to work in ViewGraph.java
 */

public class cFengTests {
    private SparseArray<Node> elements;

    /**
     * put things in elements to pass into getPoints
     * @throws Exception just in case
     */
    @Before
    public void setUp() throws Exception {
        elements = new SparseArray<>();
        Node num1 = new Node(2.0);
        elements.put(1, num1);
        elements.get(1).addValue(8.0);
        elements.get(1).incrementCount();
        Node num2 = new Node(20.0);
        elements.put(2, num2);
        elements.get(2).addValue(20.0);
        elements.get(2).incrementCount();
        Node num3 = new Node(5.0);
        elements.put(3, num3);
        elements.get(3).addValue(5.0);
        elements.get(3).incrementCount();
        elements.get(3).addValue(5.0);
        elements.get(3).incrementCount();
        Node num4 = new Node(10.0);
        elements.put(4, num4);
        elements.get(4).addValue(5.0);
        elements.get(4).incrementCount();
        elements.get(4).addValue(6.0);
        elements.get(4).incrementCount();
        Node num5 = new Node(10.0);
        elements.put(5, num5);
        elements.get(5).addValue(5.0);
        elements.get(5).incrementCount();
        elements.get(5).addValue(10.0);
        elements.get(5).incrementCount();
        elements.get(5).addValue(10.0);
        elements.get(5).incrementCount();
        elements.get(5).addValue(10.0);
        elements.get(5).incrementCount();
        //return elements;

    }

    /**
     * Just makes sures everything is added correctly
     * @throws Exception just in case
     */
    @Test
    public void resultTest() throws Exception {
        //SparseArray<ViewGraph.Node> elements = setUp();
        //DataPoint[] pts = tearDown();
        DataPoint[] pts;
        pts = new DataPoint[5];
        pts[0] = new DataPoint(1, 5);
        pts[1] = new DataPoint(2, 20);
        pts[2] = new DataPoint(3, 5);
        pts[3] = new DataPoint(4, 7);
        pts[4] = new DataPoint(5, 9);
        DataPoint[] result = vars.getPoints(elements);
        for (int i = 0; i < pts.length; i++) {
            assertTrue(pts[i].getX() == result[i].getX());
            assertTrue(pts[i].getY() == result[i].getY());
        }
    }
}
