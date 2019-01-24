/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.suai.checkers.model;

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
public class CheckersAITest {
    
    public CheckersAITest() {
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
     * Test of makeTurn method, of class CheckersAI.
     */
    @Test
    public void testMakeTurn() throws Exception {
        System.out.println("makeTurn");
        CheckersBoard desk = new CheckersBoard(false, "log.txt");
        while (desk.gameOver() == 0)
        {
            CheckersAI instance = new CheckersAI(desk, 0);
            instance.makeTurn();
            desk.saveBoard("log.txt");
        }
        assertEquals(true, desk.gameOver() > 0);
    }
    
}
