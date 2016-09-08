package com.utils.proccess;

public class SubThread extends Thread{
	private Integer number = 0;
	private String name = "";
	
	public SubThread(String name, int i){ 
		this.number = i;
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("启动: " + name);
		for(int i=1;i<1000;i++){
			System.out.println(name+">>>>>"+i);
		}
		super.run();
	}
	
	

}
