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
        linked_list_doubly list=new linked_list_doubly();
        System.out.println(list);
        list.insert(0,6);
        System.out.println(list);
        list.input_more_tail(7,8,9);
        System.out.println(list);
        list.input_more_head(3,4,5);
        System.out.println(list);
        System.out.println(list.count);
        System.out.println(list.insert_more(-1,-1,-2,-3));
        System.out.println(list.count);
        System.out.println(list);
        System.out.println(Arrays.toString(list.traversal_forward()));
        System.out.println(Arrays.toString(list.traversal_backward()));
    }
}