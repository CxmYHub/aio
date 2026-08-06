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
        red_black_tree t=new red_black_tree(3,2,4,1,5);
        System.out.println(Arrays.toString(t.traversal()));
        t.input(6);
        System.out.println(Arrays.toString(t.traversal()));
        t.input_more(7,8,9);
        System.out.println(Arrays.toString(t.traversal()));
        t.remove(3);
        System.out.println(Arrays.toString(t.traversal()));
        t.remove(0);
        t.remove(1);
        t.remove(2);
        System.out.println(Arrays.toString(t.traversal()));
    }
}