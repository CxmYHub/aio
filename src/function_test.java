import java.util.*;
import aio.mathematics.*;
public class function_test
{
    public static Scanner s1=new Scanner(System.in);
    public static Random RNG=new Random();
    public static void main(String args[])
    {
        double x[]={1,2,3,4,5};
        double y[]={1,32,243,1024,3125};
        // double x[]={0,1,2,3,4};
        // double y[]={0,1,4,9,16};
        long start=System.currentTimeMillis();
        function f=new function(x,y,true);
        long end=System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(f);
        System.out.println(f.fitness);
        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(y));
        System.out.println(Arrays.toString(f.calculate(x)));
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