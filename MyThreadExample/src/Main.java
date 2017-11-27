import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ProducerThread puts Main.LIMIT number of elements into ConcurrentLinkedQueue.
 * THREAD_NUM of ConsumerThread to read from ConcurrentLinkedQueue and put into a linkedlist sequential
 * After all ConsumerThread exists, a VerifyThread is created to verify the ordering of elements in LinkedList
 */
public class Main {
    static final long LIMIT = 1000000;
    static final int THREAD_NUM = 20;
    public static void main (String [] args) throws InterruptedException {
        System.out.println("Hello World");
        ConcurrentLinkedQueue<Long> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        LinkedList<Long> linkedList = new LinkedList<>();

        ProducerThread ProducerThread = new ProducerThread(concurrentLinkedQueue);
        ProducerThread.start();

        List<Thread> consumerThreads = new ArrayList<>();
        for(int i = 0; i < THREAD_NUM; i++ ) {
            ConsumerThread ConsumerThread = new ConsumerThread(concurrentLinkedQueue, linkedList);
            consumerThreads.add(ConsumerThread);
            ConsumerThread.start();
        }
       while( consumerThreads.stream().anyMatch( t -> t.isAlive() )){ }
       Thread.sleep(1000);
       System.out.println("All threads exits! Starting verify");
       VerifyThread verifyThread = new VerifyThread(linkedList);
       verifyThread.start();
    }
}
