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
        avl_tree tree=new avl_tree(1,2,3,4,5,6,7,8,9);
        System.out.println(tree);
        tree.remove(5);
        System.out.println(tree);
    }
}