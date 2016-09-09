package energy;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.utils.proccess.SubThread;

public class UnitTest {
	
	@Test
	public void test2(){
		long startTime = System.currentTimeMillis();
		String name = "线程 1";
		for (int i = 1; i <= 100; i++) {
			SubThread sub = new SubThread(name,i);
			sub.run();
		}
		long endTime = System.currentTimeMillis();
		long total = endTime-startTime;
		System.out.println("一共用时："+total);
		System.out.println("liuxiaodong test");
	}

	@Test
	public void test() {
//		fail("Not yet implemented");
		System.out.println("unit tst");
		
		//创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
//		ExecutorService exe = Executors.newFixedThreadPool(110);
		
		//创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
		ExecutorService exe = Executors.newCachedThreadPool();
		
		//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
//		ExecutorService exe = Executors.newSingleThreadExecutor();
		
		//创建一个定长线程池，支持定时及周期性任务执行。
//		ScheduledExecutorService exe = Executors.newScheduledThreadPool(100);
		
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= 100; i++) {
			String name = "线程 " + i;
			exe.execute(new SubThread(name,i));
//			exe.schedule(new SubThread(name,i), 0, TimeUnit.SECONDS);//延迟3秒执行
//			scheduled.scheduleAtFixedRate(new SubThread(name,i), 3, 30, TimeUnit.SECONDS);//延迟3秒，每30秒固定频率执行
		}
		exe.shutdown();
		while (true) {
			if (exe.isTerminated()) {
				long endTime = System.currentTimeMillis();
				long total = endTime-startTime;
				System.out.println("一共用时："+total);
				System.out.println("结束了！");  
				break;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
