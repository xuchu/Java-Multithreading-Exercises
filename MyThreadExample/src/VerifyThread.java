import java.util.LinkedList;

public class VerifyThread extends Thread{
    private LinkedList<Long> linkedList;
    public VerifyThread(LinkedList<Long> list){
        this.linkedList = list;
    }

    @Override
    public void run() {
        if( !verify() ){
            System.out.println("Verify failed.");
        }
        System.out.println("Verify succeed");
    }

    private boolean verify() {
        if( linkedList.size() != Main.LIMIT ){
            return false;
        }
        for( int i = 1; i < linkedList.size(); i++ ){
            if( linkedList.get(i-1) + 1 != linkedList.get(i)  ){
                return false;
            }
        }
        return true;
    }
}
