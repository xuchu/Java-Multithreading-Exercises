import java.util.ArrayList;

public class VerifyThread extends Thread{
    private ArrayList<Long> arrayList;
    public VerifyThread(ArrayList<Long> list){
        this.arrayList = list;
    }

    @Override
    public void run() {
        if( !verify() ){
            System.out.println("Verify failed.");
        }
        System.out.println("Verify succeed");
    }

    private boolean verify() {
        if( arrayList.size() != Main.LIMIT ){
            return false;
        }
        for(int i = 1; i < arrayList.size(); i++ ){
            if( arrayList.get(i-1) + 1 != arrayList.get(i)  ){
                return false;
            }
        }
        return true;
    }
}
