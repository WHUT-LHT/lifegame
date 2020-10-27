package com.frans.lifegame;

public class Time {
	private int interval;  //进程挂起的时长
	
	//构造函数
	public Time(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
}
