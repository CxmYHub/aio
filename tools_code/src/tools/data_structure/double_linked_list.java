package tools.data_structure;
/**
<p>双向链表类。</p><br>
双向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br>
<p>本双向链表以管理类+内部节点类形式实现，管理器存储链表的元素数量和头尾指针，内部节点存储链表的元素。</p>
*/
public class double_linked_list
{
    public static class double_linked_list_node
    {
        public int element;
        public double_linked_list_node next;
        public double_linked_list_node previous;
    }
    public int count=0;
    public double_linked_list_node head=null;
    public double_linked_list_node tail=null;
}