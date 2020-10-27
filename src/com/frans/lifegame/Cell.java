package com.frans.lifegame;

/**
 * @author Cao & Luo
 *
 */
public class Cell {
	private LifeStatus status;  //细胞的存活状态
    private LifeStatus nextStatus;  //细胞经历一次迭代后的存活状态
    private int x;  //细胞所处的位置：x
    private int y;  //细胞所处的位置：y
    //构造函数
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return: 状态是否改变过
     */
    public boolean evolve() {
        if (status == nextStatus)
            return false;  //状态未发生改变
        status = nextStatus;
        return true;  //状态发生改变
    }
    public LifeStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(LifeStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public void setStatus(LifeStatus status) {
        this.status = status;
    }
    public LifeStatus getStatus() {
        return status;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    //枚举类型，定义细胞生存的状态
    public enum LifeStatus{
        SURVIVAL,
        DEATH,
    }
}

