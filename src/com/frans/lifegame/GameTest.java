package com.frans.lifegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game1= new Game(10,10,500);
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testInit() {
//		fail("Not yet implemented");
		//测试思路：调用init()方法，对每个细胞的状态与DEATH进行对比
		game1.init();
		for(int i = 0; i < game1.getWidth(); i++) {
			for(int j = 0; j < game1.getHeight(); j++) {
				assertEquals(Cell.LifeStatus.DEATH, game1.getCellByXY(i, j).getStatus());
			}
		}
	};

	@Test
	public void testGetCellByXY() {
//		fail("Not yet implemented");
		//测试思路：调用初始化方法，确保游戏地图中
		//每一个cell都是存在且拥有初始化状态
		game1.init();
		for(int i = 0; i < game1.getWidth(); i++) {
			for(int j = 0; j < game1.getHeight(); j++) {
				assertEquals(Cell.LifeStatus.DEATH, game1.getCellByXY(i, j).getStatus());
				assertEquals(i, game1.getCellByXY(i, j).getX());
				assertEquals(j, game1.getCellByXY(i, j).getY());
			}
		}
	}
	
	@Test
	public void testJudgeAndDo() {
//		fail("Not yet implemented");
		//测试思路：创建一个细胞，对其状态进行设置
		//调用judgeAndDO()方法，将细胞与期望邻居存活数作为参数输入
		//观察结果
		Cell cell1 = new Cell(0, 0);
		cell1.setStatus(Cell.LifeStatus.DEATH);
		game1.judgeAndDo(cell1, 0);
		assertEquals(Cell.LifeStatus.DEATH, cell1.getNextStatus());
		
		Cell cell2 = new Cell(0, 0);
		cell2.setStatus(Cell.LifeStatus.DEATH);
		game1.judgeAndDo(cell2, 3);
		assertEquals(Cell.LifeStatus.SURVIVAL, cell2.getNextStatus());
		
		Cell cell3 = new Cell(0, 0);
		cell3.setStatus(Cell.LifeStatus.DEATH);
		game1.judgeAndDo(cell3, 2);
		assertEquals(Cell.LifeStatus.DEATH, cell3.getStatus());
	}

	@Test
	public void testRandomInit() {
//		fail("Not yet implemented");
		//测试思路：对每个细胞进行遍历，确保被成功初始化
		game1.randomInit(30);
		for(int i = 0; i < game1.getWidth(); i++) {
			for(int j = 0; j < game1.getHeight(); j++) {
				assertEquals(i, game1.getCellByXY(i, j).getX());
				assertEquals(j, game1.getCellByXY(i, j).getY());
			}
		}
	}

	@Test
	public void testGetNeighbor() {
//		fail("Not yet implemented");
		//测试思路：
		//1. 全为DEATH：对每个细胞的邻居存活数进行判断是否都为0
		//2. 随机：对每个细胞的邻居存活数设置一个判断区间（0~8），存活数在范围内即可
		game1.init();
		for(int i = 0; i < game1.getWidth(); i++) {
			for(int j = 0; j < game1.getHeight(); j++) {
				assertEquals(0, game1.getNeighbor(i, j)); 
			}
		}
		game1.randomInit(30);
		for(int i = 0; i < game1.getWidth(); i++) {
			for(int j = 0; j < game1.getHeight(); j++) {
				assertEquals(4, game1.getNeighbor(i, j), 4); 
			}
		}
	}

}
