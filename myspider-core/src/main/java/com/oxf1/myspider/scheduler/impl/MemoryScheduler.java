package com.oxf1.myspider.scheduler.impl;

import com.oxf1.myspider.Task;
import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.config.ConfigKeys;
import com.oxf1.myspider.exception.MySpiderException;
import com.oxf1.myspider.request.Request;
import com.oxf1.myspider.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by cxu on 2015/9/18.
 */
public class MemoryScheduler  extends Scheduler {
    /**
     *
     * @param taskConfig
     */
    public MemoryScheduler(TaskConfig taskConfig) {
        super(taskConfig);
        if(taskConfig.getTaskSharedObject(ConfigKeys.MEM_SCHEDULER_QUEUE)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys.MEM_SCHEDULER_QUEUE)==null){
                    ConcurrentLinkedQueue<Request> queue = new ConcurrentLinkedQueue<Request>();
                    taskConfig.addTaskSharedObject(ConfigKeys.MEM_SCHEDULER_QUEUE, queue);
                }
            }
        }
    }

    @Override
    public int push(List<Request> requests) throws MySpiderException {
        ConcurrentLinkedQueue<Request> queue = this.getQueue();
        queue.addAll(requests);
        return requests.size();
    }

    @Override
    public List<Request> poll(int n) throws MySpiderException {
        ConcurrentLinkedQueue<Request> queue = this.getQueue();
        List<Request> requests = new ArrayList<Request>(n);
        for (int i = 0; i < n; i++) {
            Request req = queue.poll();
            if (req == null) {
                break;
            }
            requests.add(req);
        }
        return requests;
    }

    @Override
    public int remove(List<Request> requests) {
        return requests.size();
    }

    @Override
    public void close() {
        //do nothing
    }

    private ConcurrentLinkedQueue<Request> getQueue() {
        TaskConfig taskConfig = getTaskConfig();
        ConcurrentLinkedQueue<Request> queue = (ConcurrentLinkedQueue<Request>)this.getTaskConfig().getTaskSharedObject(ConfigKeys.MEM_SCHEDULER_QUEUE);
        return queue;
    }
}
