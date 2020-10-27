package com.frans.lifegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CellTest {
	
	private Cell cell1 = new Cell(0, 0);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEvolve() {
//		fail("Not yet implemented");
		//测试思路：设置细胞当前状态和下一
		//阶段的状态，调用evolve()方法对返回值进行判断
		cell1.setStatus(Cell.LifeStatus.DEATH);
		cell1.setNextStatus(Cell.LifeStatus.DEATH);
		assertEquals(false, cell1.evolve());
		
		cell1.setStatus(Cell.LifeStatus.DEATH);
		cell1.setNextStatus(Cell.LifeStatus.SURVIVAL);
		assertEquals(true, cell1.evolve());
	}

}
