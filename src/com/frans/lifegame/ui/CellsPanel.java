package com.frans.lifegame.ui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class CellsPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int rows;  //行数
    private int cols;  //列数
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public CellsPanel(int rows, int cols) {
    	//GridLayout(rows, cols, 0, 0): 网格布局，参数: (行数，列数，水平间隔，垂直间隔)
        setLayout(new GridLayout(rows, cols, 0, 0));  
        this.rows = rows;
        this.cols = cols;
    }
    
    public Component getComponentAtXY(int x, int y) {
    	//x + y * cols: 定位元素
    	//x：第x列
    	//y：第y行
    	
    	//getComponents()返回的是一个一维数组，故位置判断要考虑地图（二维数组）与一维数组的关系
       return getComponents()[x + y * cols];  
    }
}
