import java.util.*;
import aio.mathematics.*;
public class FunctionTest {
    public static Scanner s1=new Scanner(System.in);
    public static Random RNG=new Random();
    public static void main(String args[]) {
        // double x[]={1,2,3,4,5};
        // double y[]={1,32,243,1024,3125};
        // double x[]={1,10,100,1000,10000};
        // double y[]={0,1,2,3,4};
        double x[]={-5,-3,-1,1,3,5};
        double y[]={1,2,3,4,5,6};
        long start=System.currentTimeMillis();
        Function f=new Function(true,x,y);
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