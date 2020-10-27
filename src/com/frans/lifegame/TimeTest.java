package com.frans.lifegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimeTest {

	Time time = new Time(500);
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetInterval() {
//		fail("Not yet implemented");
		assertEquals(500, time.getInterval());
	}

	@Test
	public void testSetInterval() {
//		fail("Not yet implemented");
		time.setInterval(300);
		assertEquals(300, time.getInterval());
	}

}
