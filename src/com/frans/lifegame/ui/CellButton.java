package com.frans.lifegame.ui;

import java.awt.Color;

import javax.swing.JButton;

public class CellButton extends JButton{
    private static final long serialVersionUID = 1L;
    public static final Color SURVIVAL_COLOR = Color.BLACK;  //存活状态的格子颜色：黑
    public static final Color DEATH_COLOR = Color.WHITE;  //死亡状态的格子颜色：白
    private int xInCellPanel;  //格子在panel中的位置：x
    private int yInCellPanel;  //格子在panel中的位置：y

    public CellButton(int xInCellPanel, int yInCellPanel) {
        this.xInCellPanel = xInCellPanel;
        this.yInCellPanel = yInCellPanel;
        setSize(10, 10);
        setBackground(DEATH_COLOR);
    }

    public int getxInCellPanel() {
        return xInCellPanel;
    }

    public void setxInCellPanel(int xInCellPanel) {
        this.xInCellPanel = xInCellPanel;
    }

    public int getyInCellPanel() {
        return yInCellPanel;
    }

    public void setyInCellPanel(int yInCellPanel) {
        this.yInCellPanel = yInCellPanel;
    }
}
