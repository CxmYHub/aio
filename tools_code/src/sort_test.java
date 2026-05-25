import tools.collection.*;
import tools.data_structure.*;
import tools.mathematics.*;
import tools.date.*;
import java.util.*;
import java.time.*;
public class sort_test
{
    public static void main(String args[])
    {
        Random RNG=new Random();
        boolean judging=true;
        int time=5;
        int length=10000000;
        int numbers[]=new int[length];
        long sum_offical_time=0;
        long sum_time=0;
        for(int i=1;i<=time;i++)
        {
            for(int j=0;j<length;j++)
            {
                numbers[j]=RNG.nextInt();
                // numbers[j]=0;
                // numbers[j]=j;
                // numbers[j]=-j;
            }
            int another[]=null;
            long offical_start=0;
            long offical_end=0;
            if(judging)
            {
                another=numbers.clone();
                offical_start=System.nanoTime();
                Arrays.sort(another);
                // Arrays.parallelSort(another);
                offical_end=System.nanoTime();
            }
            long start=System.nanoTime();
            sort.radix(numbers);
            // concurrent_sort.concurrent_quick_dual_pivot(numbers);
            long end=System.nanoTime();
            if(judging)
            {
                System.out.println((Arrays.equals(numbers,another)?"[AC]\t"+length+"个元素耗时 "+(end-start)/1000+"μs    "+"\t标准sort耗时 "+(offical_end-offical_start)/1000+"μs\t":"[WA]\t"+length+"个元素耗时 "+(end-start)/1000+"μs排序错误\t")+i+"/"+time);
                sum_offical_time+=offical_end-offical_start;
            }
            else
            {
                System.out.println(length+"个元素耗时 "+(end-start)/1000+"μs\t"+i+"/"+time);
            }
            sum_time+=end-start;
        }
        if(judging)
        {
            System.out.println("平均耗时 "+sum_time/time/1000+"μs   \t标准sort耗时 "+sum_offical_time/time/1000+"μs");
        }
        else
        {
            System.out.println("平均耗时 "+sum_time/time/1000+"μs");
        }
    }
}