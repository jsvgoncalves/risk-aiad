package test.logic;

import static org.junit.Assert.*;

import logic.Board;

import org.junit.Test;

import util.R;

public class TestBoardAuxiliarMethods {
	@Test
	public void testGetContinent() {
		Board b = new Board();
		
		assertTrue(b.getContinent(R.ANTARTICA).size()==3);
	}
}
