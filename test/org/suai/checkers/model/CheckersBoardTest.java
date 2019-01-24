/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.suai.checkers.model;

import java.io.IOException;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ppned
 */
public class CheckersBoardTest {
    
    public CheckersBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of cancelTurn method, of class CheckersBoard.
     */
    @Test
    public void testCancelTurn() throws Exception {
        System.out.println("cancelTurn");
        CheckersBoard instance = new CheckersBoard(false,"log.txt");
        instance.chooseChecker(5, 0);
        instance.makeTurn(4, 1);
        instance.saveBoard("log.txt");
        instance.cancelTurn();
        assertEquals(true, instance.chooseChecker(5, 0));
    }
 
    /**
     * Test of couldEat method, of class CheckersBoard.
     */
    @Test
    public void testCouldEat() throws Exception {
        System.out.println("couldEat");
        CheckersBoard instance = new CheckersBoard(false, "log.txt");
        instance.chooseChecker(5, 0);
        instance.makeTurn(4, 1);
        instance.chooseChecker(2, 3);
        instance.makeTurn(3, 2);
        boolean expResult = true;
        boolean result = instance.couldEat();
        assertEquals(expResult, result);
    }


    /**
     * Test of chooseChecker method, of class CheckersBoard.
     */
    @Test
    public void testChooseChecker() throws IOException {
        System.out.println("chooseChecker");
        int row = 0;
        int column = 0;
        CheckersBoard instance = new CheckersBoard(false, "log.txt");
        boolean expResult = false;
        boolean result = instance.chooseChecker(row, column);
        assertEquals(expResult, result);
    }

    
    /**
     * Test of makeTurn method, of class CheckersBoard.
     */
    @Test
    public void testMakeTurn() throws IOException {
        System.out.println("makeTurn");
        int r = 4;
        int c = 1;
        CheckersBoard instance = new CheckersBoard(false, "log.txt");
        instance.chooseChecker(5, 0);
        int expResult = 0;
        int result = instance.makeTurn(r, c);
        assertEquals(expResult, result);
    }

   
    /**
     * Test of gameOver method, of class CheckersBoard.
     */
    @Test
    public void testGameOver() throws IOException {
        System.out.println("gameOver");
        CheckersBoard instance = new CheckersBoard(true, "logtest.txt");
        int expResult = 1;
        int result = instance.gameOver();
        assertEquals(expResult, result);
    }
    
}
