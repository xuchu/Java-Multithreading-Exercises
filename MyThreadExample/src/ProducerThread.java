import java.util.concurrent.ConcurrentLinkedQueue;

public class ProducerThread extends Thread {

    private ConcurrentLinkedQueue<Long> concurrentLinkedQueue;
    public ProducerThread(ConcurrentLinkedQueue<Long> concurrentLinkedQueue){
        this.concurrentLinkedQueue = concurrentLinkedQueue;
    }

    @Override
    public void run() {
        try {
            write();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void write() throws InterruptedException {
        for(long i = 0; i < Main.LIMIT; i++ ) {
            concurrentLinkedQueue.add(i);
        }
        System.out.println("ProducerThread exits");
    }
}
