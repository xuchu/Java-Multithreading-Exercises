import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class VerifyTask extends RecursiveTask<Boolean> {
    private int THRESHOLD = 10000;
    private ArrayList<Long> arrayList;
    private int startIndex;
    private int endIndex;

    public VerifyTask(ArrayList<Long> arrayList, int startIndex, int endIndex){
        this.arrayList = arrayList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Boolean compute() {
        boolean result = true;
        int listSize = (endIndex - startIndex) + 1;
        if( listSize > THRESHOLD ){
            int middleIndex = (startIndex + endIndex) / 2 + 1;
            VerifyTask verifyLeftListTask = new VerifyTask(arrayList, startIndex, middleIndex-1);
            VerifyTask verifyRightListTask = new VerifyTask(arrayList, middleIndex, endIndex);

            verifyLeftListTask.fork();
            verifyRightListTask.fork();

            boolean leftVerifyResult = verifyLeftListTask.join();
            boolean rightVerifyResult = verifyRightListTask.join();

            result = leftVerifyResult && rightVerifyResult;
        }
        else {
            result = doVerify(startIndex, endIndex);
        }
        return result;
    }

    /**
     * Split the list of [startIndex, endIndex] into two sublist
     * [startIndex, middle-1], [middle, endIndex]
     *
     * For a list contains even number of elements. List of 10: 0,1,2,3,4,5,6,7,8,9
     * middle = ( startIndex + endIndex ) / 2 + 1;
     * middle is index 5 for the above list
     * Split the list into [0,1,2,3,4] and [5,6,7,8,9]
     *
     * For a list of size contains odd number of elements. List of 9: 0,1,2,3,4,5,6,7,8
     * middle is index 5
     * Split the list into [0,1,2,3,4] and [5,6,7,8]
     *
     * For a List of 10: 0,1,2,3,4,5,6,7,8,9
     * if startIndex is 0, endIndex is 5, this function will verify the sublist: [0,1,2,3,4] and [5,6,7,8,9]
     */
    private boolean doVerify(int start, int end){
        System.out.println("verify from " + start + " to " + end);
        for(int i = start; i < end; i++ ){
            if( arrayList.get(i) + 1 != arrayList.get(i+1) ){
                return false;
            }
        }
        // test the edge case,
        if( end < arrayList.size()-1 && arrayList.get(end)+1 != arrayList.get(end+1) ){
            return false;
        }
        return true;
    }
}
