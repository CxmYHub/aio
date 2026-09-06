package aio.mathematics;
import java.util.Random;
/**
<p>一元实函数类</p><br>
函数是数学中描述变量间依赖关系的核心概念。<br>
其本质是定义域与值域之间遵循特定对应法则的映射关系。
*/
public class function
{
    /**
    <p>随机数生成器</p>
    */
    public static final Random RNG=new Random();
    /**
    <p>表达式树类</p><br>
    表达式树是一种特殊的二叉树，用于表示数学表达式的树状结构。
    */
    public static class expression_tree implements Cloneable
    {
        /**
        <p>结点类型</p><br>
        表示表达式树中结点的类型：<br>
        <ul>
            <li>=-1：自变量结点。</li>
            <li>=0：常量结点。</li>
            <li>=1：加法运算结点。</li>
            <li>=2：减法运算结点。</li>
            <li>=3：乘法运算结点。</li>
            <li>=4：除法运算结点。</li>
            <li>=5：指数运算结点。</li>
            <li>=6：对数运算结点。</li>
        </ul>
        */
        public int type;
        /**
        <p>结点值</p><br>
        表示表达式树中常量结点的值。<br>
        对于其他结点，值为0.0。
        */
        public double value;
        /**
        <p>左子树指针</p>
        */
        public expression_tree left;
        /**
        <p>右子树指针</p>
        */
        public expression_tree right;
        /**
        <p>表达式树化简</p><br>
        <p>此方法会修改调用对象。</p>
        @return 化简时删除的结点数量。
        */
        public int simplify()
        {
            expression_tree pins[]=new expression_tree[10];
            int pin=0,capacity=10;
            expression_tree now=this;
            expression_tree last=null;
            int count=0;
            while(now!=null||pin>0)
            {
                for(;now!=null;now=now.left)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        expression_tree new_pins[]=new expression_tree[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now;
                }
                expression_tree top=pins[pin-1];
                if(top.right!=null&&top.right!=last)
                {
                    now=top.right;
                }
                else
                {
                    if(top.type>0)
                    {
                        expression_tree left=top.left;
                        expression_tree right=top.right;
                        if((top.type==1||top.type==2)&&right.value<0)
                        {
                            top.type=3-top.type;
                            right.value=-right.value;
                        }
                        if(left.type<=0&&right.type<=0)
                        {
                            if(left.type==0&&right.type==0)
                            {
                                switch(top.type)
                                {
                                    case 1->
                                    {
                                        top.value=left.value+right.value;
                                    }
                                    case 2->
                                    {
                                        top.value=left.value-right.value;
                                    }
                                    case 3->
                                    {
                                        top.value=left.value*right.value;
                                    }
                                    case 4->
                                    {
                                        top.value=left.value/right.value;
                                    }
                                    case 5->
                                    {
                                        top.value=Math.pow(left.value,right.value);
                                    }
                                    case 6->
                                    {
                                        top.value=Math.log(right.value)/Math.log(left.value);
                                    }
                                }
                                top.type=0;
                                count+=2;
                                top.left=null;
                                top.right=null;
                            }
                            else if(left.type==-1&&right.type==-1)
                            {
                                switch(top.type)
                                {
                                    case 1->
                                    {
                                        top.type=3;
                                        left.type=0;
                                        left.value=2;
                                        right.type=-1;
                                        right.value=0;
                                    }
                                    case 2->
                                    {
                                        top.type=0;
                                        top.value=0;
                                        count+=2;
                                        top.left=null;
                                        top.right=null;
                                    }
                                    case 3->
                                    {
                                        top.type=5;
                                        left.type=-1;
                                        left.value=0;
                                        right.type=0;
                                        right.value=2;
                                    }
                                    case 4->
                                    {
                                        top.type=0;
                                        top.value=1;
                                        count+=2;
                                        top.left=null;
                                        top.right=null;
                                    }
                                    case 6->
                                    {
                                        top.type=0;
                                        top.value=1;
                                        count+=2;
                                        top.left=null;
                                        top.right=null;
                                    }
                                }
                            }
                            else if(left.type==-1&&right.type==0&&(top.type==1||top.type==3))
                            {
                                top.left=right;
                                top.right=left;
                            }
                            else
                            {
                                switch(top.type)
                                {
                                    case 1->
                                    {
                                        if(left.value<=0.00000001&&left.value>=-0.00000001)
                                        {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                    case 2->
                                    {
                                        if(right.type==0)
                                        {
                                            if(right.value<=0.00000001&&right.value>=-0.00000001)
                                            {
                                                top.type=-1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                            else if(right.value<0)
                                            {
                                                top.type=1;
                                                right.value=-right.value;
                                                top.left=right;
                                                top.right=left;
                                            }
                                        }
                                    }
                                    case 3->
                                    {
                                        if(left.value<=0.00000001&&left.value>=-0.00000001)
                                        {
                                            top.type=0;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                        else if(left.value<=1.00000001&&left.value>=0.99999999)
                                        {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                    case 4->
                                    {
                                        if(left.type==0&&left.value<=0.00000001&&left.value>=-0.00000001)
                                        {
                                            top.type=0;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                        else if(right.type==0&&right.value<=1.00000001&&right.value>=0.99999999)
                                        {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                    case 5->
                                    {
                                        if(left.type==0)
                                        {
                                            if(left.value<=0.00000001&&left.value>=-0.00000001)
                                            {
                                                top.type=0;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                            else if(left.value<=1.00000001&&left.value>=0.99999999)
                                            {
                                                top.type=0;
                                                top.value=1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                        }
                                        else
                                        {
                                            if(right.value<=0.00000001&&right.value>=-0.00000001)
                                            {
                                                top.type=0;
                                                top.value=1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                            else if(right.value<=1.00000001&&right.value>=0.99999999)
                                            {
                                                top.type=-1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                    case 6->
                                    {
                                        if(left.type==0&&left.value<=1.00000001&&left.value>=0.99999999)
                                        {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                }
                            }
                        }
                        else if(left.type>0&&right.type<=0&&left.left.type<=0&&left.right.type<=0)
                        {
                            expression_tree left_left=left.left;
                            expression_tree left_right=left.right;
                            switch(top.type)
                            {
                                case 1->
                                {
                                    switch(left.type)
                                    {
                                        case 1->
                                        {
                                            if(right.type==0)
                                            {
                                                left.value=left_left.value+right.value;
                                                right.type=-1;
                                                right.value=0;
                                            }
                                            else
                                            {
                                                right.type=3;
                                                left.type=0;
                                                left_left.value=2;
                                                right.left=left_left;
                                                right.right=left_right;
                                            }
                                            left.type=0;
                                            left.left=null;
                                            left.right=null;
                                            count+=2;
                                        }
                                        case 2->
                                        {
                                            if(left_left.type==0&&right.type==-1)
                                            {
                                                top.type=0;
                                                top.value=left_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(left_right.type==0&&right.type==-1)
                                            {
                                                top.type=2;
                                                right.type=0;
                                                right.value=left_right.value;
                                                left.type=3;
                                                left_left.type=0;
                                                left_left.value=2;
                                                left_right.type=-1;
                                                left_right.value=0;
                                            }
                                            else if(left_left.type==0&&right.type==0)
                                            {
                                                top.type=2;
                                                left.type=0;
                                                left.value=left_left.value+right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                            else if(left_right.type==0&&right.type==0)
                                            {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value-=left_right.value;
                                                if(right.value<0)
                                                {
                                                    right.value=-right.value;
                                                    top.type=2;
                                                }
                                                count+=2;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(right.type==-1)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=left_left.value+1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left_left.type==-1&&right.type==-1)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1/left_right.value+1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 2->
                                {
                                    switch(left.type)
                                    {
                                        case 1->
                                        {
                                            if(right.type==0)
                                            {
                                                right.value-=left_left.value;
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                if(right.value<0)
                                                {
                                                    top.type=1;
                                                    right.value=-right.value;
                                                }
                                                count+=2;
                                            }
                                            else
                                            {
                                                top.type=0;
                                                top.value=left_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                        }
                                        case 2->
                                        {
                                            if(left_left.type==0&&right.type==-1)
                                            {
                                                left.type=0;
                                                left.value=left_left.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=3;
                                                left_left.value=2;
                                                right.left=left_left;
                                                right.right=left_right;
                                            }
                                            else if(left_right.type==0&&right.type==-1)
                                            {
                                                top.type=0;
                                                top.value=-left_right.value;
                                                top.left=null;
                                                top.right=null;
                                            }
                                            else if(left_left.type==0&&right.type==0)
                                            {
                                                left.type=0;
                                                left.value=left_left.value-right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                            else if(left_right.type==0&&right.type==0)
                                            {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value+=left_right.value;
                                                if(right.value<0)
                                                {
                                                    right.value=-right.value;
                                                    top.type=1;
                                                }
                                                count+=2;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(right.type==-1)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=left_left.value-1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left_left.type==-1&&right.type==-1)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1/left_right.value-1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 3->
                                {
                                    switch(left.type)
                                    {
                                        case 1->
                                        {
                                            top.type=1;
                                            if(right.type==0)
                                            {
                                                left.type=0;
                                                left.value=left_left.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=3;
                                                left_left.value=right.value;
                                                right.value=0;
                                                right.left=left_left;
                                                right.right=left_right;
                                            }
                                            else
                                            {
                                                left.type=3;
                                                right.type=5;
                                                right.left=new expression_tree(-1);
                                                right.right=new expression_tree(0,2);
                                            }
                                        }
                                        case 2->
                                        {
                                            if(left_left.type==0&&right.type==-1)
                                            {
                                                top.type=2;
                                                left.type=3;
                                                right.type=5;
                                                right.left=new expression_tree(-1);
                                                right.right=new expression_tree(0,2);
                                            }
                                            else if(left_right.type==0&&right.type==-1)
                                            {
                                                top.type=2;
                                                left.type=5;
                                                right.type=3;
                                                right.left=new expression_tree(0,left_right.value);
                                                right.right=new expression_tree(-1);
                                                left_right.value=2;
                                            }
                                            else if(left_left.type==0&&right.type==0)
                                            {
                                                top.type=2;
                                                left.type=0;
                                                left.value=left_left.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=3;
                                                left_left.value=right.value;
                                                right.left=left_left;
                                                right.right=left_right;
                                                right.value=0;
                                            }
                                            else if(left_right.type==0&&right.type==0)
                                            {
                                                top.type=2;
                                                left.type=3;
                                                left_left.type=0;
                                                left_left.value=right.value;
                                                left_right.type=-1;
                                                right.value*=left_right.value;
                                                left_right.value=0;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(right.type==-1)
                                            {
                                                left.type=0;
                                                left.value=left_left.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=2;
                                                right.right=left_right;
                                            }
                                            else
                                            {
                                                left.type=0;
                                                left.value=left_left.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left_left.type==-1&&right.type==-1)
                                            {
                                                top.type=4;
                                                right.type=0;
                                                right.value=left_right.value;
                                                left.type=5;
                                                left_right.value=2;
                                            }
                                            else if(left_right.type==-1&&right.type==-1)
                                            {
                                                top.type=0;
                                                top.value=left_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(left_left.type==0&&right.type==0)
                                            {
                                                top.type=4;
                                                left.type=0;
                                                left.value=left_left.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                            else
                                            {
                                                left.type=0;
                                                left.value=right.value/left_right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                        }
                                        case 5->
                                        {
                                            if(left_left.type==-1&&left_right.type==0&&right.type==-1)
                                            {
                                                top.type=5;
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=0;
                                                right.value=left_right.value+1;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 4->
                                {
                                    switch(left.type)
                                    {
                                        case 1->
                                        {
                                            top.type=1;
                                            if(right.type==0)
                                            {
                                                left.type=0;
                                                left.value=left_left.value/right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=right.value;
                                                right.right=left_right;
                                                right.value=0;
                                            }
                                            else
                                            {
                                                left.type=0;
                                                left.value=1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                right.left=left_left;
                                                right.right=left_right;
                                            }
                                        }
                                        case 2->
                                        {
                                            if(left_left.type==0&&right.type==-1)
                                            {
                                                top.type=2;
                                                left.type=4;
                                                right.type=0;
                                                right.value=1;
                                            }
                                            else if(left_right.type==0&&right.type==-1)
                                            {
                                                top.type=2;
                                                left.type=0;
                                                left.value=1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                left_left.type=0;
                                                left_left.value=left_right.value;
                                                right.left=left_left;
                                                left_right.type=-1;
                                                left_right.value=0;
                                                right.right=left_right;
                                            }
                                            else if(left_left.type==0&&right.type==0)
                                            {
                                                top.type=2;
                                                left.type=0;
                                                left.value=left_left.value/right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=right.value;
                                                right.right=left_right;
                                                right.value=0;
                                            }
                                            else if(left_right.type==0&&right.type==0)
                                            {
                                                top.type=2;
                                                left.type=4;
                                                double temp=left_right.value;
                                                left_right.value=right.value;
                                                right.value=temp/right.value;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(right.type==-1)
                                            {
                                                top.type=0;
                                                top.value=left_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else
                                            {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value/=left_left.value;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left_left.type==-1&&right.type==-1)
                                            {
                                                top.type=0;
                                                top.value=1/left_right.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(left_right.type==-1&&right.type==-1)
                                            {
                                                top.type=4;
                                                left.type=0;
                                                left.value=left_left.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=2;
                                                right.right=left_right;
                                            }
                                            else if(left_left.type==0&&right.type==0)
                                            {
                                                top.type=4;
                                                left.type=0;
                                                left.value=left_left.value/right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                            else
                                            {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=0;
                                                right.value*=left_right.value;
                                                count+=2;
                                            }
                                        }
                                        case 5->
                                        {
                                            if(left_left.type==-1&&left_right.type==0&&right.type==-1)
                                            {
                                                top.type=5;
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=0;
                                                right.value=left_right.value-1;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 5->
                                {
                                    switch(left.type)
                                    {
                                        case 3->
                                        {
                                            if(right.type==0)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=Math.pow(left_left.value,right.value);
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=right.value;
                                                right.right=left_right;
                                                right.value=0;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left_left.type==0&&right.type==0)
                                            {
                                                top.type=4;
                                                left.type=0;
                                                left.value=Math.pow(left_left.value,right.value);
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=right.value;
                                                right.right=left_right;
                                                right.value=0;
                                            }
                                            else if(left_right.type==0&&right.type==0)
                                            {
                                                top.type=4;
                                                left.type=5;
                                                double temp=left_right.value;
                                                left_right.value=right.value;
                                                right.value=Math.pow(temp,right.value);
                                            }
                                        }
                                        case 5->
                                        {
                                            if(left_left.type==-1&&left_right.type==0&&right.type==0)
                                            {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value*=left_right.value;
                                                count+=2;
                                            }
                                            else if(left_left.type==0&&left_right.type==-1&&right.type==-1)
                                            {
                                                left.type=0;
                                                left.value=left_left.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                left_left.type=-1;
                                                left_left.value=0;
                                                right.left=left_left;
                                                left_right.type=0;
                                                left_right.value=2;
                                                right.right=left_right;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else if(left.type<=0&&right.type>0&&right.left.type<=0&&right.right.type<=0)
                        {
                            expression_tree right_left=right.left;
                            expression_tree right_right=right.right;
                            switch(top.type)
                            {
                                case 1->
                                {
                                    switch(right.type)
                                    {
                                        case 1->
                                        {
                                            if(left.type==0)
                                            {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value+=right_left.value;
                                                count+=2;
                                            }
                                            else
                                            {
                                                right.type=3;
                                                right_left.value=2;
                                                left.type=0;
                                                left.value=right_left.value;
                                            }
                                        }
                                        case 2->
                                        {
                                            if(left.type==-1&&right_left.type==0)
                                            {
                                                top.type=0;
                                                top.value=right_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(left.type==-1&&right_right.type==0)
                                            {
                                                top.type=2;
                                                right.type=0;
                                                right.value=right_right.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=3;
                                                right_left.type=0;
                                                right_left.value=2;
                                                left.left=right_left;
                                                right_right.type=-1;
                                                right_right.value=0;
                                                left.right=right_right;
                                            }
                                            else if(left.type==0&&right_left.type==0)
                                            {
                                                top.type=2;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value+=right_left.value;
                                                count+=2;
                                            }
                                            else if(left.type==0&&right_right.type==0)
                                            {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value-=right_right.value;
                                                if(left.value<0)
                                                {
                                                    left.value=-left.value;
                                                    top.type=2;
                                                    top.left=right;
                                                    top.right=left;
                                                }
                                                count+=2;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(left.type==-1)
                                            {
                                                top.type=3;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.type=0;
                                                left.value=right_left.value+1;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left.type==-1&&right_left.type==-1)
                                            {
                                                top.type=3;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.type=0;
                                                left.value=1/right_right.value+1;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 2->
                                {
                                    switch(right.type)
                                    {
                                        case 1->
                                        {
                                            if(left.type==0)
                                            {
                                                left.value-=right_left.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                            else
                                            {
                                                top.type=0;
                                                top.value=-right_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                        }
                                        case 2->
                                        {
                                            if(left.type==-1&&right_left.type==0)
                                            {
                                                top.type=2;
                                                right.type=0;
                                                right.value=right_left.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=3;
                                                right_left.type=0;
                                                right_left.value=2;
                                                left.left=right_left;
                                                left.right=right_right;
                                            }
                                            else if(left.type==-1&&right_right.type==0)
                                            {
                                                top.type=0;
                                                top.value=right_right.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(left.type==0&&right_left.type==0)
                                            {
                                                top.type=1;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value-=right_left.value;
                                                count+=2;
                                            }
                                            else if(left.type==0&&right_right.type==0)
                                            {
                                                left.value+=right_right.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(left.type==-1)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1-right_left.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left.type==-1&&right_left.type==-1)
                                            {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1-1/right_right.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 3->
                                {
                                    switch(right.type)
                                    {
                                        case 1->
                                        {
                                            top.type=1;
                                            if(left.type==0)
                                            {
                                                right.type=3;
                                                double temp=right_left.value;
                                                right_left.value=left.value;
                                                left.value*=temp;
                                            }
                                            else
                                            {
                                                right.type=3;
                                                left.type=5;
                                                left.left=new expression_tree(-1);
                                                left.right=new expression_tree(0,2);
                                                count-=2;
                                            }
                                        }
                                        case 2->
                                        {
                                            if(left.type==-1&&right_left.type==0)
                                            {
                                                top.type=2;
                                                left.type=3;
                                                left.left=new expression_tree(0,right_left.value);
                                                left.right=new expression_tree(-1);
                                                right.type=5;
                                                right_left.type=-1;
                                                right_left.value=0;
                                                right_right.value=2;
                                                count-=2;
                                            }
                                            else if(left.type==-1&&right_right.type==0)
                                            {
                                                top.type=2;
                                                right_left.type=0;
                                                right_left.value=left.value;
                                                right_right.type=-1;
                                                right_right.value=0;
                                                left.type=5;
                                                left.right=new expression_tree(-1);
                                                left.left=new expression_tree(0,2);
                                                left.type=3;
                                                count-=2;
                                            }
                                            else if(left.type==0&&right_left.type==0)
                                            {
                                                top.type=2;
                                                right.type=3;
                                                double temp=right_left.value;
                                                right_left.value=left.value;
                                                left.value*=temp;
                                            }
                                            else if(left.type==0&&right_right.type==0)
                                            {
                                                top.type=2;
                                                right.type=0;
                                                right.value=left.value*right_right.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=3;
                                                right_left.type=0;
                                                right_left.value=left.value;
                                                left.left=right_left;
                                                right_right.type=-1;
                                                right_right.value=0;
                                                left.right=right_right;
                                                left.value=0;
                                            }
                                        }
                                        case 3->
                                        {
                                            if(left.type==-1)
                                            {
                                                left.type=0;
                                                left.value=right_left.value;
                                                right.type=5;
                                                right_left.type=-1;
                                                right_left.value=0;
                                                right_right.type=0;
                                                right_right.value=2;
                                            }
                                            else
                                            {
                                                left.value*=right_left.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left.type==-1&&right_left.type==-1)
                                            {
                                                top.type=4;
                                                right.type=0;
                                                right.value=right_right.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=5;
                                                left.left=right_left;
                                                right_right.value=2;
                                                left.right=right_right;
                                            }
                                            else if(right_right.type==-1&&left.type==-1)
                                            {
                                                top.type=0;
                                                top.value=right_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(left.type==0&&right_left.type==0)
                                            {
                                                top.type=4;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value*=right_left.value;
                                                count+=2;
                                            }
                                            else
                                            {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value/=right_right.value;
                                                count+=2;
                                            }
                                        }
                                        case 5->
                                        {
                                            if(left.type==-1&&right_left.type==-1&&right_right.type==0)
                                            {
                                                top.type=5;
                                                right.type=0;
                                                right.value=right_right.value+1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 4->
                                {
                                    switch(right.type)
                                    {
                                        case 3->
                                        {
                                            if(left.type==-1)
                                            {
                                                top.type=0;
                                                top.value=1/right_left.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else
                                            {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value/=right_left.value;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left.type==-1&&right_left.type==-1)
                                            {
                                                top.type=0;
                                                top.value=right_right.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                            else if(right_right.type==-1&&left.type==-1)
                                            {
                                                top.type=4;
                                                right.type=0;
                                                right.value=right_left.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=5;
                                                right_left.type=-1;
                                                right_left.value=0;
                                                left.left=right_left;
                                                right_right.type=0;
                                                right_right.value=2;
                                                left.right=right_right;
                                            }
                                            else if(left.type==0&&right_left.type==0)
                                            {
                                                top.type=3;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value/=right_left.value;
                                                count+=2;
                                            }
                                            else
                                            {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value*=right_right.value;
                                                count+=2;
                                            }
                                        }
                                        case 5->
                                        {
                                            if(left.type==-1&&right_left.type==-1&&right_right.type==0)
                                            {
                                                top.type=5;
                                                right.type=0;
                                                right.value=1-right_right.value;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 5->
                                {
                                    switch(right.type)
                                    {
                                        case 3->
                                        {
                                            if(left.type==0)
                                            {
                                                left.value=Math.pow(left.value,right_left.value);
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4->
                                        {
                                            if(left.type==0&&right_right.type==0)
                                            {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value=Math.pow(left.value,1/right_right.value);
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    last=top;
                    pin--;
                }
            }
            return count;
        }
        /**
        <p>结点构造方法</p><br>
        构造一个指定结点类型和子树的表达式树结点。
        @param type 结点类型。
        @param left 左子树指针。
        @param right 右子树指针。
        */
        public expression_tree(int type,expression_tree left,expression_tree right)
        {
            this.type=type;
            this.left=left;
            this.right=right;
        }
        /**
        <p>结点构造方法</p><br>
        构造一个指定结点类型和值的表达式树结点。
        @param type 结点类型。
        @param value 结点值。
        */
        public expression_tree(int type,double value)
        {
            this.type=type;
            this.value=value;
        }
        /**
        <p>结点构造方法</p><br>
        构造一个指定值的常数表达式树结点。
        @param value 结点值。
        */
        public expression_tree(double value)
        {
            this.value=value;
        }
        /**
        <p>结点构造方法</p><br>
        构造一个指定结点类型的表达式树结点。<br>
        默认节点值为0.0。
        @param type 结点类型。
        */
        public expression_tree(int type)
        {
            this.type=type;
        }
        /**
        <p>构造方法</p><br>
        构造一个具有指定最大深度的表达式树。
        @param max_tree_depth 最大树深度。
        @param full 是否生成满二叉树。<br>
        =<code>true</code>：生成满二叉树。<br>
        =<code>false</code>：生成随机非满二叉树。
        @param subtree_chance 生成随机非满二叉树时，继续生成子树的概率。<br>
        当生成满二叉树时，此参数无效。
        @param constant_range 常量结点值的范围。<br>
        常量结点值在[-<code>constant_range</code>,<code>constant_range</code>)范围内随机生成。
        */
        public expression_tree(int max_tree_depth,boolean full,double subtree_chance,double constant_range)
        {
            constant_range=constant_range>=0?constant_range:-constant_range;
            if(max_tree_depth<=1)
            {
                type=RNG.nextInt(2)-1;
                value=RNG.nextDouble(-constant_range,constant_range);
            }
            else
            {
                expression_tree pins[]=new expression_tree[max_tree_depth];
                type=RNG.nextInt(6)+1;
                pins[0]=this;
                int pin=0;
                while(pin>=0)
                {
                    expression_tree now=pins[pin];
                    if(now.left==null)
                    {
                        if(pin<max_tree_depth-1&&(full||RNG.nextDouble()<subtree_chance))
                        {
                            pins[++pin]=now.left=new expression_tree(RNG.nextInt(6)+1);
                        }
                        else
                        {
                            int type=RNG.nextInt(2)-1;
                            now.left=new expression_tree(type,type==0?RNG.nextDouble(-constant_range,constant_range):0);
                        }
                    }
                    else if(now.right==null)
                    {
                        if(pin<max_tree_depth-1&&(full||RNG.nextDouble()<subtree_chance))
                        {
                            pins[++pin]=now.right=new expression_tree(RNG.nextInt(6)+1);
                        }
                        else
                        {
                            int type=RNG.nextInt(2)-1;
                            now.right=new expression_tree(type,type==0?RNG.nextDouble(-constant_range,constant_range):0);
                        }
                    }
                    else
                    {
                        for(pin--;pin>=0&&pins[pin].right!=null;pin--);
                        if(pin>=0)
                        {
                            if(pin<max_tree_depth-1&&(full||RNG.nextDouble()<subtree_chance))
                            {
                                pins[pin+1]=pins[pin].right=new expression_tree(RNG.nextInt(6)+1);
                                pin++;
                            }
                            else
                            {
                                int type=RNG.nextInt(2)-1;
                                pins[pin].right=new expression_tree(type,type==0?RNG.nextDouble(-constant_range,constant_range):0);
                            }
                        }
                    }
                }
                simplify();
            }
        }
        /**
        <p>结点计数</p><br>
        计算表达式树的结点数。
        @return 表达式树的结点数。
        */
        public int count()
        {
            expression_tree pins[]=new expression_tree[10];
            int pin=1,capacity=10;
            pins[0]=this;
            int count=0;
            while(pin>0)
            {
                expression_tree now=pins[--pin];
                count++;
                if(now.left!=null)
                {
                    pins[pin++]=now.left;
                }
                if(now.right!=null)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        expression_tree new_pins[]=new expression_tree[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now.right;
                }
            }
            return count;
        }
        /**
        <p>树深度计算</p><br>
        计算表达式树的深度。
        @return 表达式树的深度。
        */
        public int depth()
        {
            expression_tree pins[]=new expression_tree[10];
            int front=0,rear=1,capacity=10;
            boolean overturn=false;
            pins[0]=this;
            int level_size=1;
            int depth=0;
            while(level_size>0)
            {
                depth++;
                expression_tree now;
                int next_level_size=0;
                for(;level_size>0;level_size--)
                {
                    now=pins[front++];
                    if(front>=capacity)
                    {
                        front=0;
                        overturn=false;
                    }
                    if(now.left!=null)
                    {
                        if(front==rear&&overturn)
                        {
                            expression_tree new_pins[]=new expression_tree[(capacity<<1)+2];
                            System.arraycopy(pins,front,new_pins,0,capacity-front);
                            System.arraycopy(pins,0,new_pins,capacity-front,rear);
                            pins=new_pins;
                            front=0;
                            rear=capacity;
                            capacity=(capacity<<1)+2;
                            overturn=false;
                        }
                        pins[rear++]=now.left;
                        next_level_size++;
                        if(rear>=capacity)
                        {
                            rear=0;
                            overturn=true;
                        }
                    }
                    if(now.right!=null)
                    {
                        if(front==rear&&overturn)
                        {
                            expression_tree new_pins[]=new expression_tree[(capacity<<1)+2];
                            System.arraycopy(pins,front,new_pins,0,capacity-front);
                            System.arraycopy(pins,0,new_pins,capacity-front,rear);
                            pins=new_pins;
                            front=0;
                            rear=capacity;
                            capacity=(capacity<<1)+2;
                            overturn=false;
                        }
                        pins[rear++]=now.right;
                        next_level_size++;
                        if(rear>=capacity)
                        {
                            rear=0;
                            overturn=true;
                        }
                    }
                }
                level_size=next_level_size;
            }
            return depth;
        }
        /**
        <p>结点随机获取</p><br>
        随机获取表达式树中的一个结点。
        @return 随机获取的结点。
        */
        public expression_tree get_node_random()
        {
            int target=RNG.nextInt(count());
            expression_tree pins[]=new expression_tree[10];
            int capacity=10;
            pins[0]=this;
            for(int pin=1;pin>0;target--)
            {
                expression_tree now=pins[--pin];
                if(target==0)
                {
                    return now;
                }
                if(now.right!=null)
                {
                    pins[pin++]=now.right;
                }
                if(now.left!=null)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        expression_tree new_pins[]=new expression_tree[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now.left;
                }
            }
            return this;
        }
        /**
        <p>表达式树值计算</p><br>
        计算指定自变量的表达式树的值。
        @param x 自变量值。
        @return 指定自变量的表达式树的值。
        */
        public double calculate(double x)
        {
            expression_tree pins[]=new expression_tree[10];
            int pin=0,capacity=10;
            expression_tree now=this;
            expression_tree last=null;
            double result[]=new double[10];
            int count=0,result_capacity=10;
            while(now!=null||pin>0)
            {
                for(;now!=null;now=now.left)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        expression_tree new_pins[]=new expression_tree[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now;
                }
                expression_tree top=pins[pin-1];
                if(top.right!=null&&top.right!=last)
                {
                    now=top.right;
                }
                else
                {
                    if(top.type<=0&&count>=result_capacity)
                    {
                        result_capacity=(result_capacity<<1)+2;
                        double new_result[]=new double[result_capacity];
                        System.arraycopy(result,0,new_result,0,count);
                        result=new_result;
                    }
                    switch(top.type)
                    {
                        case -1->result[count++]=x;
                        case 0->result[count++]=top.value;
                        case 1->
                        {
                            double addend2=result[--count];
                            double addend1=result[--count];
                            result[count++]=addend1+addend2;
                        }
                        case 2->
                        {
                            double subtrahend=result[--count];
                            double minend=result[--count];
                            result[count++]=minend-subtrahend;
                        }
                        case 3->
                        {
                            double factor2=result[--count];
                            double factor1=result[--count];
                            result[count++]=factor1*factor2;
                        }
                        case 4->
                        {
                            double divisor=result[--count];
                            if(divisor==0)
                            {
                                return Double.NaN;
                            }
                            double dividend=result[--count];
                            result[count++]=dividend/divisor;
                        }
                        case 5->
                        {
                            double exponential=result[--count];
                            double base=result[--count];
                            if(base==0&&exponential==0)
                            {
                                return Double.NaN;
                            }
                            result[count++]=Math.pow(base,exponential);
                        }
                        case 6->
                        {
                            double power=result[--count];
                            double base=result[--count];
                            if(power<=0||base==1||base<=0)
                            {
                                return Double.NaN;
                            }
                            result[count++]=Math.log(power)/Math.log(base);
                        }
                    }
                    last=top;
                    pin--;
                }
            }
            return Double.isNaN(result[0])?Double.NaN:result[0];
        }
        /**
        <p>适应度计算</p><br>
        计算表达式树对点集的适应度。
        @param x 自变量值数组。
        @param y 因变量值数组。
        @param complexity_penalty 复杂度惩罚系数。
        @return 表达式树对点集的适应度。
        */
        public double fitness(double x[],double y[],double complexity_penalty)
        {
            double squared_difference=0;
            for(int i=0;i<x.length;i++)
            {
                double difference=calculate(x[i])-y[i];
                squared_difference+=difference*difference;
            }
            return squared_difference/x.length+(complexity_penalty==0?0:complexity_penalty*count());
        }
        /**
        <p>交叉互换</p><br>
        <p>此方法会修改调用对象。</p><br>
        互换两个表达式树的节点以及它们的子树。
        @param individual1 表达式树个体1。
        @param individual2 表达式树个体2。
        @return 一个表达式树数组，包含交叉互换后的两个表达式树。<br>
        <code>{个体1的变异个体,个体2的变异个体}</code>
        */
        public static expression_tree[] crossover(expression_tree individual1,expression_tree individual2)
        {
            expression_tree filial1=individual1.clone();
            expression_tree filial2=individual2.clone();
            expression_tree crossing_point1=filial1.get_node_random();
            expression_tree crossing_point2=filial2.get_node_random();
            int temp_type=crossing_point1.type;
            crossing_point1.type=crossing_point2.type;
            crossing_point2.type=temp_type;
            double temp_value=crossing_point1.value;
            crossing_point1.value=crossing_point2.value;
            crossing_point2.value=temp_value;
            expression_tree temp_left=crossing_point1.left;
            crossing_point1.left=crossing_point2.left;
            crossing_point2.left=temp_left;
            expression_tree temp_right=crossing_point1.right;
            crossing_point1.right=crossing_point2.right;
            crossing_point2.right=temp_right;
            filial1.simplify();
            filial2.simplify();
            return new expression_tree[]{filial1,filial2};
        }
        /**
        <p>突变</p><br>
        <p>此方法会修改调用对象。</p><br>
        随机改变表达式树的一个节点的类型或值。
        @param max_depth 最大突变深度。
        @param constant_range 突变的常量结点值的范围。<br>
        常量结点值在[-<code>constant_range</code>,<code>constant_range</code>)范围内随机生成。
        @return 突变后的节点类型。
        */
        public int mutate(int max_depth,double constant_range)
        {
            expression_tree mutation_point=get_node_random();
            if(mutation_point.type<=0)
            {
                if(RNG.nextBoolean())
                {
                    if(mutation_point.type==-1)
                    {
                        mutation_point.type=0;
                        mutation_point.value=RNG.nextDouble(-constant_range,constant_range);
                    }
                    else
                    {
                        mutation_point.type=-1;
                    }
                }
                else if(mutation_point.type==0)
                {
                    mutation_point.value=RNG.nextDouble(-constant_range,constant_range);
                }
            }
            else
            {
                if(RNG.nextBoolean())
                {
                    mutation_point.type=RNG.nextInt(6)+1;
                }
                else
                {
                    int mutating_depth=RNG.nextInt(max_depth)+1;
                    expression_tree mutating_subtree=new expression_tree(mutating_depth,RNG.nextBoolean(),0.7,constant_range);
                    mutation_point.type=mutating_subtree.type;
                    mutation_point.value=mutating_subtree.value;
                    mutation_point.left=mutating_subtree.left;
                    mutation_point.right=mutating_subtree.right;
                }
            }
            simplify();
            return mutation_point.type;
        }
        /**
        <p>字符串表示</p>
        @return 表达式树的字符串表示。
        */
        public String toString()
        {
            expression_tree pins[]=new expression_tree[10];
            int pin=0,capacity=10;
            expression_tree now=this;
            expression_tree last=null;
            String result[]=new String[10];
            int count=0,result_capacity=10;
            while(now!=null||pin>0)
            {
                for(;now!=null;now=now.left)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        expression_tree new_pins[]=new expression_tree[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now;
                }
                expression_tree top=pins[pin-1];
                if(top.right!=null&&top.right!=last)
                {
                    now=top.right;
                }
                else
                {
                    if(top.type<=0&&count>=result_capacity)
                    {
                        result_capacity=(result_capacity<<1)+2;
                        String new_result[]=new String[result_capacity];
                        System.arraycopy(result,0,new_result,0,count);
                        result=new_result;
                    }
                    switch(top.type)
                    {
                        case -1->result[count++]="x";
                        case 0->result[count++]=""+top.value;
                        case 1->
                        {
                            String addend2=result[--count];
                            String addend1=result[--count];
                            result[count++]="("+addend1+"+"+addend2+")";
                        }
                        case 2->
                        {
                            String subtrahend=result[--count];
                            String minuend=result[--count];
                            result[count++]="("+minuend+"-"+subtrahend+")";
                        }
                        case 3->
                        {
                            String factor2=result[--count];
                            String factor1=result[--count];
                            result[count++]="("+factor1+"*"+factor2+")";
                        }
                        case 4->
                        {
                            String divisor=result[--count];
                            String dividend=result[--count];
                            result[count++]="("+dividend+"/"+divisor+")";
                        }
                        case 5->
                        {
                            String exponential=result[--count];
                            String base=result[--count];
                            result[count++]="("+base+"^"+exponential+")";
                        }
                        case 6->
                        {
                            String power=result[--count];
                            String base=result[--count];
                            result[count++]="log_"+base+"("+power+")";
                        }
                    }
                    last=top;
                    pin--;
                }
            }
            return result[0];
        }
        /**
        <p>深拷贝表达式树</p>
        @return 原表达式树的深拷贝。
        */
        public expression_tree clone()
        {
            try
            {
                expression_tree pins_original[]=new expression_tree[10];
                expression_tree pins_cloned[]=new expression_tree[10];
                int pin=1,capacity=10;
                expression_tree cloned=(expression_tree)super.clone();
                pins_original[0]=this;
                pins_cloned[0]=cloned;
                while(pin>0)
                {
                    expression_tree now_original=pins_original[--pin];
                    expression_tree now_cloned=pins_cloned[pin];
                    if(now_original.right!=null)
                    {
                        now_cloned.right=new expression_tree(now_original.right.type,now_original.right.value);
                        pins_original[pin]=now_original.right;
                        pins_cloned[pin++]=now_cloned.right;
                    }
                    if(now_original.left!=null)
                    {
                        if(pin>=capacity)
                        {
                            capacity=(capacity<<1)+2;
                            expression_tree new_pins_original[]=new expression_tree[capacity];
                            expression_tree new_pins_cloned[]=new expression_tree[capacity];
                            System.arraycopy(pins_original,0,new_pins_original,0,pin);
                            System.arraycopy(pins_cloned,0,new_pins_cloned,0,pin);
                            pins_original=new_pins_original;
                            pins_cloned=new_pins_cloned;
                        }
                        now_cloned.left=new expression_tree(now_original.left.type,now_original.left.value);
                        pins_original[pin]=now_original.left;
                        pins_cloned[pin++]=now_cloned.left;
                    }
                }
                return cloned;
            }
            catch(CloneNotSupportedException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
    /**
    <p>遗传算法种群规模</p>
    */
    public static int population_size=6000;
    /**
    <p>遗传算法最大迭代次数</p>
    */
    public static int max_generation=86400000;
    /**
    <p>遗传算法最佳个体保留比例</p><br>
    亲代最适应个体的比例。
    */
    public static double best_rate=0.005;
    /**
    <p>遗传算法个体选择率</p><br>
    迭代时单次竞争中选择的个体数量的倒数。
    */
    public static double survival_rate=0.02;
    /**
    <p>遗传算法新个体生成比例</p><br>
    迭代时新增野生个体的比例。
    */
    public static double new_individual_rate=0.2;
    /**
    <p>遗传算法交叉互换概率</p>
    */
    public static double crossover_rate=0.7;
    /**
    <p>遗传算法突变概率</p>
    */
    public static double mutation_rate=0.3;
    /**
    <p>遗传算法最大树深度限制</p>
    */
    public static int max_tree_depth=7;
    /**
    <p>函数表达式树</p><br>
    本一元实函数对象的表达式树。
    */
    public expression_tree function_tree;
    /**
    <p>函数适应度</p><br>
    本一元实函数对象对构造坐标集的适应度。
    */
    public double fitness;
    /**
    <p>构造方法</p><br>
    通过多个指定坐标构造一元实函数对象。
    @param coordinates_xn_yn 用于拟合的多个坐标，格式为x_1,y_1,x_2,y_2,...,x_n,y_n。
    */
    public function(double... coordinates_xn_yn)
    {
        int count=coordinates_xn_yn.length>>1;
        double x[]=new double[count];
        double y[]=new double[count];
        for(int i=0;i<count;i++)
        {
            x[i]=coordinates_xn_yn[i<<1];
            y[i]=coordinates_xn_yn[i<<1|1];
        }
        this(x,y);
    }
    /**
    <p>构造方法</p><br>
    通过两个坐标数组构造一元实函数对象。
    @param coordinates_xn 用于拟合的x坐标数组。
    @param coordinates_yn 用于拟合的y坐标数组。
    */
    public function(double coordinates_xn[],double coordinates_yn[])
    {
        int count=coordinates_xn.length;
        double local_mutation_rate=mutation_rate;
        double max_x=-Double.MAX_VALUE,min_x=Double.MAX_VALUE;
        double max_y=-Double.MAX_VALUE,min_y=Double.MAX_VALUE;
        for(int i=0;i<count;i++)
        {
            double now_x=coordinates_xn[i];
            double now_y=coordinates_yn[i];
            max_x=now_x>max_x?now_x:max_x;
            min_x=now_x<min_x?now_x:min_x;
            max_y=now_y>max_y?now_y:max_y;
            min_y=now_y<min_y?now_y:min_y;
        }
        double constant_range=(max_y-min_y)*2;
        if(constant_range==0)
        {
            function_tree=new expression_tree(0,max_y);
            fitness=0;
        }
        else
        {
            double middle_y=(max_y+min_y)/2;
            int best_count=(int)(population_size*best_rate);
            int new_count=(int)(population_size*new_individual_rate);
            int mutate_edge=best_count+new_count;
            int tournament_size=(int)(1/survival_rate);
            expression_tree population[]=new expression_tree[population_size];
            expression_tree next_generation[]=new expression_tree[population_size];
            double fitness[]=new double[population_size];
            int indexs[]=new int[(fitness.length<<1)+2];
            double global_min_fitness=Double.MAX_VALUE;
            int generation_count=max_generation;
            for(int i=0;i<population_size;i++)
            {
                population[i]=new expression_tree(max_tree_depth,RNG.nextBoolean(),0.7,constant_range);
            }
            for(int generation=0;generation<max_generation;generation++)
            {
                for(int i=generation==0?0:best_count;i<population_size;i++)
                {
                    double now_fitness=population[i].fitness(coordinates_xn,coordinates_yn,0.5);
                    if(Double.isNaN(now_fitness)||Double.isInfinite(now_fitness))
                    {
                        now_fitness=Double.MAX_VALUE;
                    }
                    fitness[i]=now_fitness;
                }
                indexs[0]=0;
                indexs[1]=fitness.length-1;
                int pin=2;
                while(pin>1)
                {
                    int index_right=indexs[--pin];
                    int index_left=indexs[--pin];
                    if(index_left<index_right)
                    {
                        int length=index_right-index_left+1;
                        double temp;
                        expression_tree temp_tree;
                        if(length<5&&fitness[index_left]>fitness[index_right])
                        {
                            temp=fitness[index_left];
                            temp_tree=population[index_left];
                            fitness[index_left]=fitness[index_right];
                            population[index_left]=population[index_right];
                            fitness[index_right]=temp;
                            population[index_right]=temp_tree;
                        }
                        double pivot1=fitness[index_left];
                        double pivot2=fitness[index_right];
                        if(length>=5)
                        {
                            int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
                            double a=fitness[fifth[0]],b=fitness[fifth[1]],c=fitness[fifth[2]],d=fitness[fifth[3]],e=fitness[fifth[4]];
                            double less_win1,less_lose1,less_win2,less_lose2,less_candidate1,less_candidate2,min1,great_candidate1,great_candidate2,great_candidate3,max1;
                            if(a<b)
                            {
                                less_win1=a;
                                less_lose1=b;
                            }
                            else
                            {
                                less_win1=b;
                                less_lose1=a;
                            }
                            if(c<d)
                            {
                                less_win2=c;
                                less_lose2=d;
                            }
                            else
                            {
                                less_win2=d;
                                less_lose2=c;
                            }
                            if(less_win1<less_win2)
                            {
                                min1=less_win1;
                                less_candidate1=less_win2;
                                less_candidate2=less_lose1;
                                great_candidate1=less_lose2;
                            }
                            else
                            {
                                min1=less_win2;
                                less_candidate1=less_win1;
                                less_candidate2=less_lose2;
                                great_candidate1=less_lose1;
                            }
                            if(e<min1)
                            {
                                pivot1=min1;
                                min1=e;
                                great_candidate2=less_candidate1;
                                great_candidate3=less_candidate2;
                            }
                            else if(e<less_candidate1)
                            {
                                pivot1=e<less_candidate2?e:less_candidate2;
                                great_candidate2=less_candidate1;
                                great_candidate3=e>less_candidate2?e:less_candidate2;
                            }
                            else
                            {
                                pivot1=less_candidate1<less_candidate2?less_candidate1:less_candidate2;
                                great_candidate2=e;
                                great_candidate3=less_candidate1>less_candidate2?less_candidate1:less_candidate2;
                            }
                            if(great_candidate1>great_candidate2)
                            {
                                if(great_candidate2>great_candidate3)
                                {
                                    max1=great_candidate1;
                                    pivot2=great_candidate2;
                                }
                                else if(great_candidate3>great_candidate1)
                                {
                                    max1=great_candidate3;
                                    pivot2=great_candidate1;
                                }
                                else
                                {
                                    max1=great_candidate1;
                                    pivot2=great_candidate3;
                                }
                            }
                            else
                            {
                                if(great_candidate2<great_candidate3)
                                {
                                    max1=great_candidate3;
                                    pivot2=great_candidate2;
                                }
                                else if(great_candidate3<great_candidate1)
                                {
                                    max1=great_candidate2;
                                    pivot2=great_candidate1;
                                }
                                else
                                {
                                    max1=great_candidate2;
                                    pivot2=great_candidate3;
                                }
                            }
                            if(pivot1==pivot2)
                            {
                                pivot1=min1;
                                pivot2=max1;
                            }
                            for(int pivot_index=0;pivot_index<5;pivot_index++)
                            {
                                if(pivot1==fitness[fifth[pivot_index]])
                                {
                                    fitness[fifth[pivot_index]]=fitness[index_left];
                                    fitness[index_left]=pivot1;
                                    temp_tree=population[fifth[pivot_index]];
                                    population[fifth[pivot_index]]=population[index_left];
                                    population[index_left]=temp_tree;
                                    break;
                                }
                            }
                            for(int pivot_index=4;pivot_index>=0;pivot_index--)
                            {
                                if(pivot2==fitness[fifth[pivot_index]])
                                {
                                    fitness[fifth[pivot_index]]=fitness[index_right];
                                    fitness[index_right]=pivot2;
                                    temp_tree=population[fifth[pivot_index]];
                                    population[fifth[pivot_index]]=population[index_right];
                                    population[index_right]=temp_tree;
                                    break;
                                }
                            }
                        }
                        int left=index_left;
                        int right=index_right;
                        int k=index_left+1;
                        boolean back=false;
                        while(k<right)
                        {
                            if(fitness[k]<pivot1)
                            {
                                temp=fitness[++left];
                                temp_tree=population[left];
                                fitness[left]=fitness[k];
                                population[left]=population[k];
                                fitness[k]=temp;
                                population[k++]=temp_tree;
                            }
                            else if(fitness[k]<=pivot2)
                            {
                                k++;
                            }
                            else
                            {
                                back=false;
                                while(fitness[--right]>pivot2)
                                {
                                    if(k>=right)
                                    {
                                        back=true;
                                        break;
                                    }
                                }
                                if(!back)
                                {
                                    if(fitness[right]<pivot1)
                                    {
                                        temp=fitness[right];
                                        temp_tree=population[right];
                                        fitness[right]=fitness[k];
                                        population[right]=population[k];
                                        fitness[k]=fitness[++left];
                                        population[k]=population[left];
                                        fitness[left]=temp;
                                        population[left]=temp_tree;
                                    }
                                    else
                                    {
                                        temp=fitness[right];
                                        temp_tree=population[right];
                                        fitness[right]=fitness[k];
                                        population[right]=population[k];
                                        fitness[k]=temp;
                                        population[k]=temp_tree;
                                    }
                                    k++;
                                }
                            }
                        }
                        temp=fitness[index_left];
                        temp_tree=population[index_left];
                        fitness[index_left]=fitness[left];
                        population[index_left]=population[left];
                        fitness[left]=temp;
                        population[left]=temp_tree;
                        temp=fitness[index_right];
                        temp_tree=population[index_right];
                        fitness[index_right]=fitness[right];
                        population[index_right]=population[right];
                        fitness[right]=temp;
                        population[right]=temp_tree;
                        indexs[pin++]=right+1;
                        indexs[pin++]=index_right;
                        if(pivot1!=pivot2)
                        {
                            indexs[pin++]=left+1;
                            indexs[pin++]=right-1;
                        }
                        indexs[pin++]=index_left;
                        indexs[pin++]=left-1;
                    }
                }
                System.arraycopy(population,0,next_generation,0,best_count);
                double generate_min_fitness=fitness[0];
                if(population[0].fitness(coordinates_xn,coordinates_yn,0)==0)
                {
                    break;
                }
                else if(generate_min_fitness<global_min_fitness)
                {
                    global_min_fitness=generate_min_fitness;
                    generation_count=(int)(global_min_fitness*3);
                    generation_count=generation_count<100?100:generation_count;
                    System.out.println("\n"+global_min_fitness+"\t"+local_mutation_rate+"\n"+population[0]);
                }
                else if(--generation_count<0)
                {
                    if(population[0].fitness(coordinates_xn,coordinates_yn,0)<0.05*middle_y)
                    {
                        break;
                    }
                    else
                    {
                        local_mutation_rate+=0.001;
                        generation_count=100;
                    }
                }
                for(int i=best_count;i<mutate_edge;i++)
                {
                    next_generation[i]=new expression_tree(max_tree_depth,RNG.nextBoolean(),0.7,constant_range);
                }
                for(int i=mutate_edge;i<population_size;i++)
                {
                    int target=i;
                    double min_fitness=Double.MAX_VALUE;
                    for(int j=0;j<tournament_size;j++)
                    {
                        int index=RNG.nextInt(population_size);
                        double now_fitness=fitness[index];
                        if(now_fitness<min_fitness)
                        {
                            min_fitness=now_fitness;
                            target=index;
                        }
                    }
                    expression_tree parent1=population[target].clone();
                    expression_tree filial;
                    if(RNG.nextDouble()<crossover_rate)
                    {
                        target=i;
                        min_fitness=Double.MAX_VALUE;
                        for(int j=0;j<tournament_size;j++)
                        {
                            int index=RNG.nextInt(population_size);
                            double now_fitness=fitness[index];
                            if(now_fitness<min_fitness)
                            {
                                min_fitness=now_fitness;
                                target=index;
                            }
                        }
                        expression_tree parent2=population[target].clone();
                        expression_tree crossovered[]=expression_tree.crossover(parent1,parent2);
                        filial=crossovered[RNG.nextInt(2)];
                    }
                    else
                    {
                        filial=parent1.clone();
                    }
                    if(RNG.nextDouble()<local_mutation_rate)
                    {
                        filial.mutate(max_tree_depth,constant_range);
                    }
                    next_generation[i]=filial;
                }
                expression_tree temp[]=population;
                population=next_generation;
                next_generation=temp;
            }
            double best_fitness=Double.MAX_VALUE;
            expression_tree best_tree=population[0];
            for(int i=0;i<population_size;i++)
            {
                double now_fitness=population[i].fitness(coordinates_xn,coordinates_yn,0.5);
                if(Double.isNaN(now_fitness)||Double.isInfinite(now_fitness))
                {
                    now_fitness=Double.MAX_VALUE;
                }
                if(now_fitness<best_fitness)
                {
                    best_fitness=now_fitness;
                    best_tree=population[i];
                }
            }
            this.fitness=best_tree.fitness(coordinates_xn,coordinates_yn,0);
            function_tree=best_tree;
        }
    }
    /**
    <p>计算函数值</p><br>
    计算指定自变量值的函数值。
    @param x 自变量值。
    @return 函数值。
    */
    public double calculate(double x)
    {
        return function_tree.calculate(x);
    }
    /**
    <p>计算函数值</p><br>
    计算多个指定自变量值各自的函数值。
    @param x 多个自变量值。
    @return 自变量值对应的函数值数组。
    */
    public double[] calculate(double... x)
    {
        double result[]=new double[x.length];
        for(int i=0;i<x.length;i++)
        {
            result[i]=function_tree.calculate(x[i]);
        }
        return result;
    }
    /**
    <p>字符串表示</p>
    @return 函数的字符串表示。
    */
    public String toString()
    {
        return "y="+function_tree.toString();
    }
}