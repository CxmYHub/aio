import tools.collection.*;
import tools.data_structure.*;
import tools.mathematics.*;
import tools.geography.*;
import tools.date.*;
import java.util.*;
import java.time.*;
public class App
{
    public static final Scanner s1=new Scanner(System.in);
    public static final Random RNG=new Random();
    public static void main(String args[])
    {
        linked_list list=new linked_list(7,1,4,2,8,5,9,3,6);
        System.out.println(list);
        list.sort_descend();
        System.out.println(list);
        list.sort_ascend();
        System.out.println(list);
    }
}