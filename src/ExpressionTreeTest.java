import java.util.*;
import aio.mathematics.*;
import aio.mathematics.Function.ExpressionTree;
public class ExpressionTreeTest {
    public static void main(String args[]) {
        ExpressionTree et=new ExpressionTree(1);
        et.left=new ExpressionTree(0,2);
        et.right=new ExpressionTree(2);
        et.right.left=new ExpressionTree(0,3);
        et.right.right=new ExpressionTree(-1);
        System.out.println(et);
        et.simplify();
        System.out.println(et);
    }
}