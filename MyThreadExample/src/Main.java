import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * ProducerThread puts Main.LIMIT number of elements into ConcurrentLinkedQueue.
 * THREAD_NUM of ConsumerThread to read from ConcurrentLinkedQueue and put into a linkedlist sequential
 * After all ConsumerThread exists, a VerifyThread is created to verify the ordering of elements in LinkedList
 */
public class Main {
    static final long LIMIT = 5000000;
    static final int THREAD_NUM = 10;
    public static void main (String [] args) {
        System.out.println("Hello World");
        ConcurrentLinkedQueue<Long> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        ArrayList<Long> arrayList = new ArrayList<Long>((int)LIMIT);

        ProducerThread ProducerThread = new ProducerThread(concurrentLinkedQueue);
        ProducerThread.start();

        List<Thread> consumerThreads = new ArrayList<>();
        for(int i = 0; i < THREAD_NUM; i++ ) {
            ConsumerThread ConsumerThread = new ConsumerThread(concurrentLinkedQueue, arrayList);
            consumerThreads.add(ConsumerThread);
            ConsumerThread.start();
        }
       while( consumerThreads.stream().anyMatch( t -> t.isAlive() )){ }
       System.out.println("All threads exits! Starting verify");
       //VerifyThread verifyThread = new VerifyThread(arrayList);
       //verifyThread.start();

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        VerifyTask verifyTask = new VerifyTask(arrayList, 0, arrayList.size()-1);
        Future<Boolean> result = forkJoinPool.submit(verifyTask);
        try {
            Boolean aBoolean = result.get();
            if( aBoolean ){
                System.out.println("Verify succeed");
            }
            else{
                System.out.println("Verify failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
