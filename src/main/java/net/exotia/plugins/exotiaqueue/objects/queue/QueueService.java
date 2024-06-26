package net.exotia.plugins.exotiaqueue.objects.queue;

import java.util.*;

public class QueueService {
    private Set<Queue> queues = new HashSet<>();

    public Set<Queue> getQueues() {
        return this.queues;
    }
    public Queue getQueue(String queue) {
        return this.queues.stream().filter(queues -> queues.getServer().equals(queue)).findAny().orElse(null);
    }
    public void addQueue(String queue) {
        this.queues.add(new Queue(queue));
    }
}
