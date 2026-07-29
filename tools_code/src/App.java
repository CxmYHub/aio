import tools.collection.*;
import tools.data_structure.*;
import tools.date_time.*;
import tools.encode_decode.*;
import tools.mathematics.*;
import tools.geography.*;
import java.util.*;
import java.time.*;
import java.io.*;
public class App
{
    public static Scanner s1=new Scanner(System.in);
    public static Random RNG=new Random();
    public static void main(String args[])
    {
        int count=10000000;
        int data[]=new int[count];
        for(int i=0;i<count;i++)
        {
            data[i]=RNG.nextInt(1000000);
        }
        b_plus_tree bpt=new b_plus_tree();
        long start=System.currentTimeMillis();
        for(int i=0;i<count;i++)
        {
            bpt.input(data[i]);
        }
        long end=System.currentTimeMillis();
        System.out.println("Time cost: "+(end-start)+"ms");
        int result[]=bpt.traversal();
        int error=0;
        for(int i=1;i<result.length;i++)
        {
            if(result[i]<result[i-1])
            {
                error++;
            }
        }
        System.out.println(bpt.count());
        System.out.println("Error: "+error);
        System.out.println(bpt.count(0,40));
        System.out.println(bpt.get(-2,40).length);
        System.out.println(Arrays.toString(bpt.get(-2,40)));
    }
}