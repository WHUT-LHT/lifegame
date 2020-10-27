package com.frans.lifegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Cao & Luo
 *
 */
public class Game implements Runnable{
    
    private Cell[][] cells;  //二维对象（Cell）数组：表示游戏地图内的细胞
    private int width;  //棋盘宽度
    private int height;  //棋盘高度
    private volatile boolean run;  //volatile：表示进程间同步
    private Time time;  //计时类
//    private int interval;
    
    private CellChangeListener cellChangeListener;
    private CellListener cellListener;
    
    //构造函数
    public Game(int width, int height, int interval) {
        this.width = width;
        this.height = height;
        this.run = true;
        time = new Time(interval);
        cells = new Cell[width][height];
    }
    
    /**
         * 初始化全部cell为death
     * @return this
     */
    public Game init() {
    	//遍历
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);  //实例化对象，防止空指针报错
                cells[i][j].setStatus(Cell.LifeStatus.DEATH);  //将每个细胞的生存状态设为：DEATH
            }
        }
        return this;
    }
    
    /**
     * @param x
     * @param y
     * @return 坐标中的cell
     */
    public Cell getCellByXY(int x, int y) {
        return cells[x][y];
    }
    
    /**
         * 随机初始化
     * @param probability
     * @return
     */
    public Game randomInit(int probability) {
        Random rand = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (cells[i][j] == null)
                    cells[i][j] = new Cell(i, j);
                //根据随机数分配存活状态
                if (probability >= rand.nextInt(100)) {
                    cells[i][j].setStatus(Cell.LifeStatus.SURVIVAL);
                } else {
                    cells[i][j].setStatus(Cell.LifeStatus.DEATH);
                }
            }
        }
        return this;
    }
    
    /**
     * @param x 
     * @param y
     * @return 生存的邻居数量
     */
    public int getNeighbor(int x, int y) {
        int sum = 0;
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
            	//判断条件：在地图范围内；不是自己；如果状态为SURVIVAL
                if (i >= 0 && i < width && j >= 0 && j < height
                        && !(i == x && j == y) 
                        && cells[i][j].getStatus() == Cell.LifeStatus.SURVIVAL) {
                    ++sum;
                }
            }
        }
        return sum;
    }

    public void setCellChangeListener(CellChangeListener cellChangeListener) {
        this.cellChangeListener = cellChangeListener;
    }

    public void setCellListener(CellListener cellListener) {
        this.cellListener = cellListener;
    }
    
    public void judgeAndDo(Cell cell, int num) {
    	//根据细胞的邻居数对进行一次迭代后的存活状态进行判断并记录
        if (num == 2)
            cell.setNextStatus(cell.getStatus());
        else if (num == 3)
            cell.setNextStatus(Cell.LifeStatus.SURVIVAL);
        else
            cell.setNextStatus(Cell.LifeStatus.DEATH);
    }
    
    public void run() {
        while (true) {
            if (run) {
                List<Cell> changeCells = new ArrayList<Cell>();
                for (int j = 0; j < height; ++j) {
                    for (int i = 0; i < width; ++i){
                    	//根据细胞的邻居数对进行一次迭代后的存活状态进行判断并记录
                    	int num = getNeighbor(i, j);
                        this.judgeAndDo(cells[i][j], num);
                    }
                }
                //记录存活状态发生改变的细胞
                for (int j = 0; j < height; ++j) {
                    for (int i = 0; i < width; ++i) {
                        if (cells[i][j].evolve())
                            changeCells.add(cells[i][j]);
                    }
                }
                if (cellListener != null)
                    cellListener.cellArray(cells, run);
                if (cellChangeListener != null)
                    cellChangeListener.cellChange(changeCells, run);
                try {
                    Thread.sleep(time.getInterval());  //进程挂起，时长：time.getInterval()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void setRun(boolean run) {
        this.run = run;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    /**
     * 监听所有的cells
     *
     */
    @FunctionalInterface
    public interface CellListener{
        public void cellArray(final Cell[][] cells, boolean run);
    }
    
    /**
     * 监听改变过的cells
     *
     */
    @FunctionalInterface
    public interface CellChangeListener{
        public void cellChange(final List<Cell> changeCells, boolean run);
    }
    
    public static void main(String[] args) {
        Game game = new Game(10, 10, 500);
        game.randomInit(50);
        game.setCellListener((cells, run) -> {
            for (int i = 0; i < game.getWidth(); ++i) {
                for (int j = 0; j < game.getHeight(); ++j) {
                    System.out.print(cells[i][j].getStatus() == Cell.LifeStatus.SURVIVAL ?
                            " " : "@");
                }
                System.out.println();
            }
        });
        new Thread(game).start();
    }
}
