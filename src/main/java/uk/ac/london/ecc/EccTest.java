package uk.ac.london.ecc;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class EccTest {

	@Test
	public void testAddPoint() {
		Ecc ecc = new Ecc();
		ecc.setA(-2L);
		ecc.setB(13L);
		ecc.setK(103L);
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
		Ecc ecc = new Ecc();
		ecc.setA(2L);
		ecc.setB(3L);
		ecc.setK(103L);
		Point p = new Point(3, 6);
		assertEquals(new Point(96, 77), Ecc.multiplyPoint(p, 11, ecc));		
		assertEquals(null, Ecc.multiplyPoint(p, 18, ecc));		
		assertEquals(p, Ecc.multiplyPoint(p, 19, ecc));		
	}

	@Test
	public void testGetPoints() {
		Ecc ecc = new Ecc();
		ecc.setA(2L);
		ecc.setB(3L);
		ecc.setK(103L);
		assertEquals(108, ecc.getPoints().size());		
	}
	
}
