import java.util.*;
import aio.collection.*;
import aio.datastructure.*;
public class SortTest {
    public static void main(String args[]) {
        Random RNG=new Random();
        boolean judging=false;
        int time=4;
        int length=100000000;
        int numbers[]=new int[length];
        long sumOfficalTime=0;
        long sumTime=0;
        for(int i=1;i<=time;i++) {
            for(int j=0;j<length;j++) {
                numbers[j]=RNG.nextInt();
                // numbers[j]=0;
                // numbers[j]=j;
                // numbers[j]=-j;
            }
            int another[]=null;
            long officalStart=0;
            long officalEnd=0;
            if(judging) {
                another=numbers.clone();
                officalStart=System.nanoTime();
                // Arrays.sort(another);
                Arrays.parallelSort(another);
                officalEnd=System.nanoTime();
            }
            long start=System.nanoTime();
            ConcurrentSort.concurrentQuickDualPivot(numbers);
            // sort.quickDualPivot(numbers);
            // sort.radix(numbers);
            long end=System.nanoTime();
            if(judging) {
                System.out.println((Arrays.equals(numbers,another)?"[AC]\t"+length+"个元素耗时 "+(end-start)/1000+"μs    "+"\t标准sort耗时 "+(officalEnd-officalStart)/1000+"μs\t":"[WA]\t"+length+"个元素耗时 "+(end-start)/1000+"μs排序错误\t")+i+"/"+time);
                sumOfficalTime+=officalEnd-officalStart;
            } else {
                System.out.println(length+"个元素耗时 "+(end-start)/1000+"μs\t"+i+"/"+time);
            }
            sumTime+=end-start;
        }
        if(judging) {
            System.out.println("平均耗时 "+sumTime/time/1000+"μs   \t标准sort耗时 "+sumOfficalTime/time/1000+"μs");
        } else {
            System.out.println("平均耗时 "+sumTime/time/1000+"μs");
        }
    }
}