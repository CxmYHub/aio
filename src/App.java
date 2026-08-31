import java.util.*;
import aio.collection.*;
import aio.data_structure.*;
import aio.date_time.*;
import aio.encode_decode.*;
import aio.geography.*;
import aio.mathematics.*;
import java.time.*;
import java.io.*;
public class App
{
    public static Scanner s1=new Scanner(System.in);
    public static Random RNG=new Random();
    public static void main(String args[])
    {
        double x[]={0,1,2,3,4,5,6,7,8,9,10};
        double y[]={0,1,8,27,64,125,216,343,512,729,1000};
        long count=1;
        long start=System.currentTimeMillis();
        function f=new function(x,y);
        long end=System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(f);
        System.out.println(f.fitness);
        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(y));
        System.out.println(count);
        // double xy[]=new double[x.length<<1];
        // for(int i=0;i<x.length;i++)
        // {
        //     xy[i<<1]=x[i];
        //     xy[i<<1|1]=y[i];
        // }
        // System.out.println(Arrays.toString(xy));
        // function g=new function(xy);
        // System.out.println(g);
    }
}