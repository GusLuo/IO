package cn.com.bio.one;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BioThreadPool {

    private ExecutorService executorService;

    /**
     * 创建线程池
     * int corePoolSize, 核心线程数
     int maximumPoolSize, 最大线程数
     int QueueNums 阻塞队列存储的线程数
     */
    public BioThreadPool(int corePoolSize,int maximumPoolSize,int QueueNums) {
        executorService = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(QueueNums));
    }

    /**
     * 执行对应线程
     * @param runnable
     */
    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }
}