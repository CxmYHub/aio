import java.util.*;
import aio.mathematics.*;
import aio.mathematics.function.expression_tree;
public class expression_tree_test
{
    public static void main(String args[])
    {
        expression_tree et=new expression_tree(1);
        et.left=new expression_tree(0,2);
        et.right=new expression_tree(2);
        et.right.left=new expression_tree(0,3);
        et.right.right=new expression_tree(-1);
        System.out.println(et);
        et.simplify();
        System.out.println(et);
    }
}