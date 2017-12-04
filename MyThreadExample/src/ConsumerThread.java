import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConsumerThread extends Thread {
    private ConcurrentLinkedQueue<Long> concurrentLinkedQueue;
    private ArrayList<Long> arrayList;

    public ConsumerThread(ConcurrentLinkedQueue<Long> concurrentLinkedQueue,
                          ArrayList<Long> list){
        this.concurrentLinkedQueue = concurrentLinkedQueue;
        this.arrayList = list;
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
        synchronized (arrayList) {
            if( arrayList.size() == 0 ){
                arrayList.add(value);
                return;
            }
            long lastListValue = getLastValue(arrayList);
            while ( value != lastListValue + 1) {
                arrayList.wait();
                lastListValue = getLastValue(arrayList);
            }
            arrayList.add(value);
            arrayList.notifyAll();
        }
    }

    private long getLastValue(ArrayList<Long> arrayList){
        return arrayList.get(arrayList.size() - 1);
    }
}
