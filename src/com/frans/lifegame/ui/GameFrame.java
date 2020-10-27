package com.frans.lifegame.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.frans.lifegame.Cell;
import com.frans.lifegame.Game;

public class GameFrame extends JFrame {

    /**

     */
    private static final long serialVersionUID = 1L;
    private Game game;
    private CellsPanel leftPanel;  //左面板
    private JPanel rightPanel;  //右面板：按钮面板
    private JButton startBtn;  //开始按钮
    private JButton pauseBtn;  //暂停按钮
    private JButton randomBtn;  //随机初始化按钮
    private JButton clearBtn;  //清除按钮
    private Thread gameThread;
    private int width = 80;  //宽
    private int height = 80;  //高
    private int randomProbability = 30;
    private boolean run = false;;
    //构造函数
    public GameFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        game = new Game(width, height, 100).init();  //将所有格子初始化为DEATH
        //lambda表达式：(parameters) -> expression
        //或
        //(parameters) ->{ statements; }
        //即：(参数)->(表达式或函数体)
        game.setCellChangeListener((cells, run) -> {
            cells.forEach((cell) -> {
                updateCellBtnUI(cell);  //刷新每个格子
            });
            leftPanel.updateUI();  //刷新操作
        });
        gameThread = new Thread(game);  //创建进程
        initUI();
    }
    /**
     * 根据cell更新cellButton的ui
     * @param cell
     */
    public void updateCellBtnUI(final Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        Component c = leftPanel.getComponentAtXY(x, y);  //获取到位置为(x, y)的组件，即细胞
        if (cell.getStatus() == Cell.LifeStatus.SURVIVAL) {
            c.setBackground(CellButton.SURVIVAL_COLOR);
        } else {
            c.setBackground(CellButton.DEATH_COLOR);
        }
    }
    public void startGame() {
    	//开始游戏
        run = true;
        if (!gameThread.isAlive())
            gameThread.start();
        else {
            game.setRun(run);
        }
    }
    public void pauseGame() {
    	//暂停游戏
        run = false;
        game.setRun(run);
    }
    public void initUI() {
        leftPanel = new CellsPanel(width, height);
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));  //GridLayout(0, 1): 列数固定为1，行数不作限制
        add(leftPanel, BorderLayout.WEST);  //leftPanel放置在west位置
        add(rightPanel, BorderLayout.EAST);  //rightPanel放置在east位置
        leftPanel.setPreferredSize(new Dimension(800, 800));  //设置leftPanel的尺寸
        startBtn = new JButton("start");  //实例化“开始游戏”的按钮
        startBtn.addActionListener((e) -> {
        	//按钮操作
            startGame();  //开始游戏
            startBtn.setEnabled(false);  //“开始游戏”按钮无法被点击
            pauseBtn.setEnabled(true);  //“暂停游戏”按钮可以被点击
        });
        rightPanel.add(startBtn);  //将“开始游戏”按钮加入到rightPanel中
        pauseBtn = new JButton("pause");  //实例化“暂停游戏”按钮
        pauseBtn.addActionListener((e) -> {
        	//按钮操作
            pauseGame();  //暂停游戏
            pauseBtn.setEnabled(false);
            startBtn.setEnabled(true);
        });
        pauseBtn.setEnabled(false);  //默认“暂停游戏”按钮无法点击
        rightPanel.add(pauseBtn);  //将“暂停游戏”按钮加入到rightPanel中
        randomBtn = new JButton("random");
        randomBtn.addActionListener((e) -> {
        	//按钮操作
            if (!run) {
                game.randomInit(randomProbability);  //随机初始化地图
                for (int i = 0; i < game.getWidth(); ++i) {
                    for (int j = 0; j < game.getHeight(); ++j) {
                        updateCellBtnUI(game.getCellByXY(i, j));  //刷新面板
                    }
                }
            }
        });
        rightPanel.add(randomBtn);
        clearBtn = new JButton("clear");
        clearBtn.addActionListener((e) -> {
        	//按钮操作
            if (!run) {
                game.init();  //将所有细胞的状态变更为：DEATH
                for (int i = 0; i < game.getWidth(); ++i) {
                    for (int j = 0; j < game.getHeight(); ++j) {
                        updateCellBtnUI(game.getCellByXY(i, j));  //刷新面板
                    }
                }
            }
        });
        rightPanel.add(clearBtn);
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                CellButton btn = new CellButton(i, j);  //实例化每一个细胞（它在UI中是按钮）
                btn.addActionListener((e) -> {
                	//点击每一个细胞时的事件
                    if (!run) {
                        if (btn.getBackground() == CellButton.DEATH_COLOR) {
                            btn.setBackground(CellButton.SURVIVAL_COLOR);
                            game.getCellByXY(btn.getxInCellPanel(), btn.getyInCellPanel()).setStatus(Cell.LifeStatus.SURVIVAL);
                        } else {
                            btn.setBackground(CellButton.DEATH_COLOR);
                            game.getCellByXY(btn.getxInCellPanel(), btn.getyInCellPanel()).setStatus(Cell.LifeStatus.DEATH);
                        }
                    }
                });
                leftPanel.add(btn);
            }
        }
        //pack(): 调整此窗口的大小，以适合其子组件的首选大小和布局
        pack();
    }

}
