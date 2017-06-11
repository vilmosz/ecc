package uk.ac.london.ecc;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class EccTest {

	@Test
	public void testAddPoint() {
		Ecc ecc = new Ecc(-2L, 13L, 103L);
		Point p = new Point(19, 97);
		assertEquals(new Point(61, 90), Ecc.addPoint(p, new Point(27, 81), ecc));
		assertEquals(new Point(38, 61), Ecc.addPoint(p, p, ecc));
	}

	@Test
	public void testInverseOf() {
		assertEquals(5, Ecc.inverseOf(3, 7).intValue());
	}

	@Test
	public void testMultiplyPoint() {
		Ecc ecc = new Ecc(2L, 13L, 103L);
		Point p = new Point(3, 6);
		assertEquals(new Point(96, 77), Ecc.multiplyPoint(p, 11, ecc));	
		assertEquals(null, Ecc.multiplyPoint(p, 18, ecc));		
		assertEquals(p, Ecc.multiplyPoint(p, 19, ecc));		
	}

	@Test
	public void testGetPoints() {
		Ecc ecc = new Ecc(-8L, 11L, 167L);
		ecc.setA(-8L);
		ecc.setB(11L);
		ecc.setK(167L);
		assertEquals(178, ecc.getPoints().size());		
	}
	
}
