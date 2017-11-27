import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConsumerThread extends Thread {
    private ConcurrentLinkedQueue<Long> concurrentLinkedQueue;
    private LinkedList<Long> linkedList;

    public ConsumerThread(ConcurrentLinkedQueue<Long> concurrentLinkedQueue,
                          LinkedList<Long> list){
        this.concurrentLinkedQueue = concurrentLinkedQueue;
        this.linkedList = list;
    }

    @Override
    public void run() {
        while (true) {
            if( concurrentLinkedQueue.isEmpty() ){
                String threadName = Thread.currentThread().getName();
                System.out.println("Thread " + threadName + " exits");
                return;
            }
            try {
                long headValue = concurrentLinkedQueue.poll();
                writeToList(headValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void writeToList(long value) throws InterruptedException{
        synchronized (linkedList) {
            if( linkedList.size() == 0 ){
                linkedList.add(value);
                return;
            }
            long lastListValue = linkedList.peekLast();
            while ( value != lastListValue + 1) {
                System.out.println("waiting for another thread to get the right value");
                linkedList.wait();
                lastListValue = linkedList.peekLast();
            }
            linkedList.add(value);
            linkedList.notifyAll();
        }
    }
}
