package aio.mathematics;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
/**
<p>一元实函数类</p><br>
函数是数学中描述变量间依赖关系的核心概念。<br>
其本质是定义域与值域之间遵循特定对应法则的映射关系。
*/
public class Function {
    /**
    <p>随机数生成器</p>
    */
    public static final Random RNG=new Random();
    /**
    <p>表达式树类</p><br>
    表达式树是一种特殊的二叉树，用于表示数学表达式的树状结构。
    */
    public static class ExpressionTree implements Cloneable {
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
        public ExpressionTree left;
        /**
        <p>右子树指针</p>
        */
        public ExpressionTree right;
        /**
        <p>表达式树化简</p><br>
        <p>此方法会修改调用对象。</p>
        @return 化简时删除的结点数量。
        */
        public int simplify() {
            ExpressionTree pins[]=new ExpressionTree[10];
            int pin=0,capacity=10;
            ExpressionTree now=this;
            ExpressionTree last=null;
            int count=0;
            while(now!=null||pin>0) {
                for(;now!=null;now=now.left) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        ExpressionTree newPins[]=new ExpressionTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
                    }
                    pins[pin++]=now;
                }
                ExpressionTree top=pins[pin-1];
                if(top.right!=null&&top.right!=last) {
                    now=top.right;
                } else {
                    if(top.type>0) {
                        ExpressionTree left=top.left;
                        ExpressionTree right=top.right;
                        if((top.type==1||top.type==2)&&right.value<0) {
                            top.type=3-top.type;
                            right.value=-right.value;
                        }
                        if(left.type<=0&&right.type<=0) {
                            if(left.type==0&&right.type==0) {
                                switch(top.type) {
                                    case 1-> {
                                        top.value=left.value+right.value;
                                    }
                                    case 2-> {
                                        top.value=left.value-right.value;
                                    }
                                    case 3-> {
                                        top.value=left.value*right.value;
                                    }
                                    case 4-> {
                                        top.value=left.value/right.value;
                                    }
                                    case 5-> {
                                        top.value=Math.pow(left.value,right.value);
                                    }
                                    case 6-> {
                                        top.value=Math.log(right.value)/Math.log(left.value);
                                    }
                                }
                                top.type=0;
                                count+=2;
                                top.left=null;
                                top.right=null;
                            } else if(left.type==-1&&right.type==-1) {
                                switch(top.type) {
                                    case 1-> {
                                        top.type=3;
                                        left.type=0;
                                        left.value=2;
                                        right.type=-1;
                                        right.value=0;
                                    }
                                    case 2-> {
                                        top.type=0;
                                        top.value=0;
                                        count+=2;
                                        top.left=null;
                                        top.right=null;
                                    }
                                    case 3-> {
                                        top.type=5;
                                        left.type=-1;
                                        left.value=0;
                                        right.type=0;
                                        right.value=2;
                                    }
                                    case 4-> {
                                        top.type=0;
                                        top.value=1;
                                        count+=2;
                                        top.left=null;
                                        top.right=null;
                                    }
                                    case 6-> {
                                        top.type=0;
                                        top.value=1;
                                        count+=2;
                                        top.left=null;
                                        top.right=null;
                                    }
                                }
                            } else if(left.type==-1&&right.type==0&&(top.type==1||top.type==3)) {
                                top.left=right;
                                top.right=left;
                            } else {
                                switch(top.type) {
                                    case 1-> {
                                        if(left.value<=0.00000001&&left.value>=-0.00000001) {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                    case 2-> {
                                        if(right.type==0) {
                                            if(right.value<=0.00000001&&right.value>=-0.00000001) {
                                                top.type=-1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            } else if(right.value<0) {
                                                top.type=1;
                                                right.value=-right.value;
                                                top.left=right;
                                                top.right=left;
                                            }
                                        }
                                    }
                                    case 3-> {
                                        if(left.value<=0.00000001&&left.value>=-0.00000001) {
                                            top.type=0;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        } else if(left.value<=1.00000001&&left.value>=0.99999999) {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                    case 4-> {
                                        if(left.type==0&&left.value<=0.00000001&&left.value>=-0.00000001) {
                                            top.type=0;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        } else if(right.type==0&&right.value<=1.00000001&&right.value>=0.99999999) {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                    case 5-> {
                                        if(left.type==0) {
                                            if(left.value<=0.00000001&&left.value>=-0.00000001) {
                                                top.type=0;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            } else if(left.value<=1.00000001&&left.value>=0.99999999) {
                                                top.type=0;
                                                top.value=1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                        } else {
                                            if(right.value<=0.00000001&&right.value>=-0.00000001) {
                                                top.type=0;
                                                top.value=1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            } else if(right.value<=1.00000001&&right.value>=0.99999999) {
                                                top.type=-1;
                                                top.left=null;
                                                top.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                    case 6-> {
                                        if(left.type==0&&left.value<=1.00000001&&left.value>=0.99999999) {
                                            top.type=-1;
                                            top.left=null;
                                            top.right=null;
                                            count+=2;
                                        }
                                    }
                                }
                            }
                        } else if(left.type>0&&right.type<=0&&left.left.type<=0&&left.right.type<=0) {
                            ExpressionTree leftLeft=left.left;
                            ExpressionTree leftRight=left.right;
                            switch(top.type) {
                                case 1-> {
                                    switch(left.type) {
                                        case 1-> {
                                            if(right.type==0) {
                                                left.value=leftLeft.value+right.value;
                                                right.type=-1;
                                                right.value=0;
                                            } else {
                                                right.type=3;
                                                left.type=0;
                                                leftLeft.value=2;
                                                right.left=leftLeft;
                                                right.right=leftRight;
                                            }
                                            left.type=0;
                                            left.left=null;
                                            left.right=null;
                                            count+=2;
                                        }
                                        case 2-> {
                                            if(leftLeft.type==0&&right.type==-1) {
                                                top.type=0;
                                                top.value=leftLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(leftRight.type==0&&right.type==-1) {
                                                top.type=2;
                                                right.type=0;
                                                right.value=leftRight.value;
                                                left.type=3;
                                                leftLeft.type=0;
                                                leftLeft.value=2;
                                                leftRight.type=-1;
                                                leftRight.value=0;
                                            } else if(leftLeft.type==0&&right.type==0) {
                                                top.type=2;
                                                left.type=0;
                                                left.value=leftLeft.value+right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            } else if(leftRight.type==0&&right.type==0) {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value-=leftRight.value;
                                                if(right.value<0) {
                                                    right.value=-right.value;
                                                    top.type=2;
                                                }
                                                count+=2;
                                            }
                                        }
                                        case 3-> {
                                            if(right.type==-1) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=leftLeft.value+1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(leftLeft.type==-1&&right.type==-1) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1/leftRight.value+1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 2-> {
                                    switch(left.type) {
                                        case 1-> {
                                            if(right.type==0) {
                                                right.value-=leftLeft.value;
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                if(right.value<0) {
                                                    top.type=1;
                                                    right.value=-right.value;
                                                }
                                                count+=2;
                                            } else {
                                                top.type=0;
                                                top.value=leftLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                        }
                                        case 2-> {
                                            if(leftLeft.type==0&&right.type==-1) {
                                                left.type=0;
                                                left.value=leftLeft.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=3;
                                                leftLeft.value=2;
                                                right.left=leftLeft;
                                                right.right=leftRight;
                                            } else if(leftRight.type==0&&right.type==-1) {
                                                top.type=0;
                                                top.value=-leftRight.value;
                                                top.left=null;
                                                top.right=null;
                                            } else if(leftLeft.type==0&&right.type==0) {
                                                left.type=0;
                                                left.value=leftLeft.value-right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            } else if(leftRight.type==0&&right.type==0) {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value+=leftRight.value;
                                                if(right.value<0) {
                                                    right.value=-right.value;
                                                    top.type=1;
                                                }
                                                count+=2;
                                            }
                                        }
                                        case 3-> {
                                            if(right.type==-1) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=leftLeft.value-1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(leftLeft.type==-1&&right.type==-1) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1/leftRight.value-1;
                                                left.left=null;
                                                left.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 3-> {
                                    switch(left.type) {
                                        case 1-> {
                                            top.type=1;
                                            if(right.type==0) {
                                                left.type=0;
                                                left.value=leftLeft.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=3;
                                                leftLeft.value=right.value;
                                                right.value=0;
                                                right.left=leftLeft;
                                                right.right=leftRight;
                                            } else {
                                                left.type=3;
                                                right.type=5;
                                                right.left=new ExpressionTree(-1);
                                                right.right=new ExpressionTree(0,2);
                                            }
                                        }
                                        case 2-> {
                                            if(leftLeft.type==0&&right.type==-1) {
                                                top.type=2;
                                                left.type=3;
                                                right.type=5;
                                                right.left=new ExpressionTree(-1);
                                                right.right=new ExpressionTree(0,2);
                                            } else if(leftRight.type==0&&right.type==-1) {
                                                top.type=2;
                                                left.type=5;
                                                right.type=3;
                                                right.left=new ExpressionTree(0,leftRight.value);
                                                right.right=new ExpressionTree(-1);
                                                leftRight.value=2;
                                            } else if(leftLeft.type==0&&right.type==0) {
                                                top.type=2;
                                                left.type=0;
                                                left.value=leftLeft.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=3;
                                                leftLeft.value=right.value;
                                                right.left=leftLeft;
                                                right.right=leftRight;
                                                right.value=0;
                                            } else if(leftRight.type==0&&right.type==0) {
                                                top.type=2;
                                                left.type=3;
                                                leftLeft.type=0;
                                                leftLeft.value=right.value;
                                                leftRight.type=-1;
                                                right.value*=leftRight.value;
                                                leftRight.value=0;
                                            }
                                        }
                                        case 3-> {
                                            if(right.type==-1) {
                                                left.type=0;
                                                left.value=leftLeft.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=2;
                                                right.right=leftRight;
                                            } else {
                                                left.type=0;
                                                left.value=leftLeft.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(leftLeft.type==-1&&right.type==-1) {
                                                top.type=4;
                                                right.type=0;
                                                right.value=leftRight.value;
                                                left.type=5;
                                                leftRight.value=2;
                                            } else if(leftRight.type==-1&&right.type==-1) {
                                                top.type=0;
                                                top.value=leftLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(leftLeft.type==0&&right.type==0) {
                                                top.type=4;
                                                left.type=0;
                                                left.value=leftLeft.value*right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            } else {
                                                left.type=0;
                                                left.value=right.value/leftRight.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            }
                                        }
                                        case 5-> {
                                            if(leftLeft.type==-1&&leftRight.type==0&&right.type==-1) {
                                                top.type=5;
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=0;
                                                right.value=leftRight.value+1;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 4-> {
                                    switch(left.type) {
                                        case 1-> {
                                            top.type=1;
                                            if(right.type==0) {
                                                left.type=0;
                                                left.value=leftLeft.value/right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=right.value;
                                                right.right=leftRight;
                                                right.value=0;
                                            } else {
                                                left.type=0;
                                                left.value=1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                right.left=leftLeft;
                                                right.right=leftRight;
                                            }
                                        }
                                        case 2-> {
                                            if(leftLeft.type==0&&right.type==-1) {
                                                top.type=2;
                                                left.type=4;
                                                right.type=0;
                                                right.value=1;
                                            } else if(leftRight.type==0&&right.type==-1) {
                                                top.type=2;
                                                left.type=0;
                                                left.value=1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                leftLeft.type=0;
                                                leftLeft.value=leftRight.value;
                                                right.left=leftLeft;
                                                leftRight.type=-1;
                                                leftRight.value=0;
                                                right.right=leftRight;
                                            } else if(leftLeft.type==0&&right.type==0) {
                                                top.type=2;
                                                left.type=0;
                                                left.value=leftLeft.value/right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=4;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=right.value;
                                                right.right=leftRight;
                                                right.value=0;
                                            } else if(leftRight.type==0&&right.type==0) {
                                                top.type=2;
                                                left.type=4;
                                                double temp=leftRight.value;
                                                leftRight.value=right.value;
                                                right.value=temp/right.value;
                                            }
                                        }
                                        case 3-> {
                                            if(right.type==-1) {
                                                top.type=0;
                                                top.value=leftLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value/=leftLeft.value;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(leftLeft.type==-1&&right.type==-1) {
                                                top.type=0;
                                                top.value=1/leftRight.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(leftRight.type==-1&&right.type==-1) {
                                                top.type=4;
                                                left.type=0;
                                                left.value=leftLeft.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=2;
                                                right.right=leftRight;
                                            } else if(leftLeft.type==0&&right.type==0) {
                                                top.type=4;
                                                left.type=0;
                                                left.value=leftLeft.value/right.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=-1;
                                                right.value=0;
                                                count+=2;
                                            } else {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=0;
                                                right.value*=leftRight.value;
                                                count+=2;
                                            }
                                        }
                                        case 5-> {
                                            if(leftLeft.type==-1&&leftRight.type==0&&right.type==-1) {
                                                top.type=5;
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.type=0;
                                                right.value=leftRight.value-1;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 5-> {
                                    switch(left.type) {
                                        case 3-> {
                                            if(right.type==0) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=Math.pow(leftLeft.value,right.value);
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=right.value;
                                                right.right=leftRight;
                                                right.value=0;
                                            }
                                        }
                                        case 4-> {
                                            if(leftLeft.type==0&&right.type==0) {
                                                top.type=4;
                                                left.type=0;
                                                left.value=Math.pow(leftLeft.value,right.value);
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=right.value;
                                                right.right=leftRight;
                                                right.value=0;
                                            } else if(leftRight.type==0&&right.type==0) {
                                                top.type=4;
                                                left.type=5;
                                                double temp=leftRight.value;
                                                leftRight.value=right.value;
                                                right.value=Math.pow(temp,right.value);
                                            }
                                        }
                                        case 5-> {
                                            if(leftLeft.type==-1&&leftRight.type==0&&right.type==0) {
                                                left.type=-1;
                                                left.left=null;
                                                left.right=null;
                                                right.value*=leftRight.value;
                                                count+=2;
                                            } else if(leftLeft.type==0&&leftRight.type==-1&&right.type==-1) {
                                                left.type=0;
                                                left.value=leftLeft.value;
                                                left.left=null;
                                                left.right=null;
                                                right.type=5;
                                                leftLeft.type=-1;
                                                leftLeft.value=0;
                                                right.left=leftLeft;
                                                leftRight.type=0;
                                                leftRight.value=2;
                                                right.right=leftRight;
                                            }
                                        }
                                    }
                                }
                            }
                        } else if(left.type<=0&&right.type>0&&right.left.type<=0&&right.right.type<=0) {
                            ExpressionTree rightLeft=right.left;
                            ExpressionTree rightRight=right.right;
                            switch(top.type) {
                                case 1-> {
                                    switch(right.type) {
                                        case 1-> {
                                            if(left.type==0) {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value+=rightLeft.value;
                                                count+=2;
                                            } else {
                                                right.type=3;
                                                rightLeft.value=2;
                                                left.type=0;
                                                left.value=rightLeft.value;
                                            }
                                        }
                                        case 2-> {
                                            if(left.type==-1&&rightLeft.type==0) {
                                                top.type=0;
                                                top.value=rightLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(left.type==-1&&rightRight.type==0) {
                                                top.type=2;
                                                right.type=0;
                                                right.value=rightRight.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=3;
                                                rightLeft.type=0;
                                                rightLeft.value=2;
                                                left.left=rightLeft;
                                                rightRight.type=-1;
                                                rightRight.value=0;
                                                left.right=rightRight;
                                            } else if(left.type==0&&rightLeft.type==0) {
                                                top.type=2;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value+=rightLeft.value;
                                                count+=2;
                                            } else if(left.type==0&&rightRight.type==0) {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value-=rightRight.value;
                                                if(left.value<0) {
                                                    left.value=-left.value;
                                                    top.type=2;
                                                    top.left=right;
                                                    top.right=left;
                                                }
                                                count+=2;
                                            }
                                        }
                                        case 3-> {
                                            if(left.type==-1) {
                                                top.type=3;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.type=0;
                                                left.value=rightLeft.value+1;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(left.type==-1&&rightLeft.type==-1) {
                                                top.type=3;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.type=0;
                                                left.value=1/rightRight.value+1;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 2-> {
                                    switch(right.type) {
                                        case 1-> {
                                            if(left.type==0) {
                                                left.value-=rightLeft.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            } else {
                                                top.type=0;
                                                top.value=-rightLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            }
                                        }
                                        case 2-> {
                                            if(left.type==-1&&rightLeft.type==0) {
                                                top.type=2;
                                                right.type=0;
                                                right.value=rightLeft.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=3;
                                                rightLeft.type=0;
                                                rightLeft.value=2;
                                                left.left=rightLeft;
                                                left.right=rightRight;
                                            } else if(left.type==-1&&rightRight.type==0) {
                                                top.type=0;
                                                top.value=rightRight.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(left.type==0&&rightLeft.type==0) {
                                                top.type=1;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value-=rightLeft.value;
                                                count+=2;
                                            } else if(left.type==0&&rightRight.type==0) {
                                                left.value+=rightRight.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 3-> {
                                            if(left.type==-1) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1-rightLeft.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(left.type==-1&&rightLeft.type==-1) {
                                                top.type=3;
                                                left.type=0;
                                                left.value=1-1/rightRight.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 3-> {
                                    switch(right.type) {
                                        case 1-> {
                                            top.type=1;
                                            if(left.type==0) {
                                                right.type=3;
                                                double temp=rightLeft.value;
                                                rightLeft.value=left.value;
                                                left.value*=temp;
                                            } else {
                                                right.type=3;
                                                left.type=5;
                                                left.left=new ExpressionTree(-1);
                                                left.right=new ExpressionTree(0,2);
                                                count-=2;
                                            }
                                        }
                                        case 2-> {
                                            if(left.type==-1&&rightLeft.type==0) {
                                                top.type=2;
                                                left.type=3;
                                                left.left=new ExpressionTree(0,rightLeft.value);
                                                left.right=new ExpressionTree(-1);
                                                right.type=5;
                                                rightLeft.type=-1;
                                                rightLeft.value=0;
                                                rightRight.value=2;
                                                count-=2;
                                            } else if(left.type==-1&&rightRight.type==0) {
                                                top.type=2;
                                                rightLeft.type=0;
                                                rightLeft.value=left.value;
                                                rightRight.type=-1;
                                                rightRight.value=0;
                                                left.type=5;
                                                left.right=new ExpressionTree(-1);
                                                left.left=new ExpressionTree(0,2);
                                                left.type=3;
                                                count-=2;
                                            } else if(left.type==0&&rightLeft.type==0) {
                                                top.type=2;
                                                right.type=3;
                                                double temp=rightLeft.value;
                                                rightLeft.value=left.value;
                                                left.value*=temp;
                                            } else if(left.type==0&&rightRight.type==0) {
                                                top.type=2;
                                                right.type=0;
                                                right.value=left.value*rightRight.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=3;
                                                rightLeft.type=0;
                                                rightLeft.value=left.value;
                                                left.left=rightLeft;
                                                rightRight.type=-1;
                                                rightRight.value=0;
                                                left.right=rightRight;
                                                left.value=0;
                                            }
                                        }
                                        case 3-> {
                                            if(left.type==-1) {
                                                left.type=0;
                                                left.value=rightLeft.value;
                                                right.type=5;
                                                rightLeft.type=-1;
                                                rightLeft.value=0;
                                                rightRight.type=0;
                                                rightRight.value=2;
                                            } else {
                                                left.value*=rightLeft.value;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(left.type==-1&&rightLeft.type==-1) {
                                                top.type=4;
                                                right.type=0;
                                                right.value=rightRight.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=5;
                                                left.left=rightLeft;
                                                rightRight.value=2;
                                                left.right=rightRight;
                                            } else if(rightRight.type==-1&&left.type==-1) {
                                                top.type=0;
                                                top.value=rightLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(left.type==0&&rightLeft.type==0) {
                                                top.type=4;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value*=rightLeft.value;
                                                count+=2;
                                            } else {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value/=rightRight.value;
                                                count+=2;
                                            }
                                        }
                                        case 5-> {
                                            if(left.type==-1&&rightLeft.type==-1&&rightRight.type==0) {
                                                top.type=5;
                                                right.type=0;
                                                right.value=rightRight.value+1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 4-> {
                                    switch(right.type) {
                                        case 3-> {
                                            if(left.type==-1) {
                                                top.type=0;
                                                top.value=1/rightLeft.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value/=rightLeft.value;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(left.type==-1&&rightLeft.type==-1) {
                                                top.type=0;
                                                top.value=rightRight.value;
                                                top.left=null;
                                                top.right=null;
                                                count+=4;
                                            } else if(rightRight.type==-1&&left.type==-1) {
                                                top.type=4;
                                                right.type=0;
                                                right.value=rightLeft.value;
                                                right.left=null;
                                                right.right=null;
                                                left.type=5;
                                                rightLeft.type=-1;
                                                rightLeft.value=0;
                                                left.left=rightLeft;
                                                rightRight.type=0;
                                                rightRight.value=2;
                                                left.right=rightRight;
                                            } else if(left.type==0&&rightLeft.type==0) {
                                                top.type=3;
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value/=rightLeft.value;
                                                count+=2;
                                            } else {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value*=rightRight.value;
                                                count+=2;
                                            }
                                        }
                                        case 5-> {
                                            if(left.type==-1&&rightLeft.type==-1&&rightRight.type==0) {
                                                top.type=5;
                                                right.type=0;
                                                right.value=1-rightRight.value;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                    }
                                }
                                case 5-> {
                                    switch(right.type) {
                                        case 3-> {
                                            if(left.type==0) {
                                                left.value=Math.pow(left.value,rightLeft.value);
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                count+=2;
                                            }
                                        }
                                        case 4-> {
                                            if(left.type==0&&rightRight.type==0) {
                                                right.type=-1;
                                                right.left=null;
                                                right.right=null;
                                                left.value=Math.pow(left.value,1/rightRight.value);
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
        public ExpressionTree(int type,ExpressionTree left,ExpressionTree right) {
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
        public ExpressionTree(int type,double value) {
            this.type=type;
            this.value=value;
        }
        /**
        <p>结点构造方法</p><br>
        构造一个指定值的常数表达式树结点。
        @param value 结点值。
        */
        public ExpressionTree(double value) {
            this.value=value;
        }
        /**
        <p>结点构造方法</p><br>
        构造一个指定结点类型的表达式树结点。<br>
        默认节点值为0.0。
        @param type 结点类型。
        */
        public ExpressionTree(int type) {
            this.type=type;
        }
        /**
        <p>构造方法</p><br>
        构造一个具有指定最大深度的表达式树。
        @param maxTreeDepth 最大树深度。
        @param full 是否生成满二叉树。<br>
        =<code>true</code>：生成满二叉树。<br>
        =<code>false</code>：生成随机非满二叉树。
        @param subtreeChance 生成随机非满二叉树时，继续生成子树的概率。<br>
        当生成满二叉树时，此参数无效。
        @param constantRange 常量结点值的范围。<br>
        常量结点值在[-<code>constantRange</code>,<code>constantRange</code>)范围内随机生成。
        */
        public ExpressionTree(int maxTreeDepth,boolean full,double subtreeChance,double constantRange) {
            constantRange=constantRange>=0?constantRange:-constantRange;
            if(maxTreeDepth<=1) {
                type=RNG.nextInt(2)-1;
                value=RNG.nextDouble(-constantRange,constantRange);
            } else {
                ExpressionTree pins[]=new ExpressionTree[maxTreeDepth];
                type=RNG.nextInt(6)+1;
                pins[0]=this;
                int pin=0;
                while(pin>=0) {
                    ExpressionTree now=pins[pin];
                    if(now.left==null) {
                        if(pin<maxTreeDepth-1&&(full||RNG.nextDouble()<subtreeChance)) {
                            pins[++pin]=now.left=new ExpressionTree(RNG.nextInt(6)+1);
                        } else {
                            int type=RNG.nextInt(2)-1;
                            now.left=new ExpressionTree(type,type==0?RNG.nextDouble(-constantRange,constantRange):0);
                        }
                    } else if(now.right==null) {
                        if(pin<maxTreeDepth-1&&(full||RNG.nextDouble()<subtreeChance)) {
                            pins[++pin]=now.right=new ExpressionTree(RNG.nextInt(6)+1);
                        } else {
                            int type=RNG.nextInt(2)-1;
                            now.right=new ExpressionTree(type,type==0?RNG.nextDouble(-constantRange,constantRange):0);
                        }
                    } else {
                        for(pin--;pin>=0&&pins[pin].right!=null;pin--);
                        if(pin>=0) {
                            if(pin<maxTreeDepth-1&&(full||RNG.nextDouble()<subtreeChance)) {
                                pins[pin+1]=pins[pin].right=new ExpressionTree(RNG.nextInt(6)+1);
                                pin++;
                            } else {
                                int type=RNG.nextInt(2)-1;
                                pins[pin].right=new ExpressionTree(type,type==0?RNG.nextDouble(-constantRange,constantRange):0);
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
        public int count() {
            ExpressionTree pins[]=new ExpressionTree[10];
            int pin=1,capacity=10;
            pins[0]=this;
            int count=0;
            while(pin>0) {
                ExpressionTree now=pins[--pin];
                count++;
                if(now.left!=null) {
                    pins[pin++]=now.left;
                }
                if(now.right!=null) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        ExpressionTree newPins[]=new ExpressionTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
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
        public int depth() {
            ExpressionTree pins[]=new ExpressionTree[10];
            int front=0,rear=1,capacity=10;
            boolean overturn=false;
            pins[0]=this;
            int levelSize=1;
            int depth=0;
            while(levelSize>0) {
                depth++;
                ExpressionTree now;
                int nextLevelSize=0;
                for(;levelSize>0;levelSize--) {
                    now=pins[front++];
                    if(front>=capacity) {
                        front=0;
                        overturn=false;
                    }
                    if(now.left!=null) {
                        if(front==rear&&overturn) {
                            ExpressionTree newPins[]=new ExpressionTree[(capacity<<1)+2];
                            System.arraycopy(pins,front,newPins,0,capacity-front);
                            System.arraycopy(pins,0,newPins,capacity-front,rear);
                            pins=newPins;
                            front=0;
                            rear=capacity;
                            capacity=(capacity<<1)+2;
                            overturn=false;
                        }
                        pins[rear++]=now.left;
                        nextLevelSize++;
                        if(rear>=capacity) {
                            rear=0;
                            overturn=true;
                        }
                    }
                    if(now.right!=null) {
                        if(front==rear&&overturn) {
                            ExpressionTree newPins[]=new ExpressionTree[(capacity<<1)+2];
                            System.arraycopy(pins,front,newPins,0,capacity-front);
                            System.arraycopy(pins,0,newPins,capacity-front,rear);
                            pins=newPins;
                            front=0;
                            rear=capacity;
                            capacity=(capacity<<1)+2;
                            overturn=false;
                        }
                        pins[rear++]=now.right;
                        nextLevelSize++;
                        if(rear>=capacity) {
                            rear=0;
                            overturn=true;
                        }
                    }
                }
                levelSize=nextLevelSize;
            }
            return depth;
        }
        /**
        <p>结点随机获取</p><br>
        随机获取表达式树中的一个结点。
        @return 随机获取的结点。
        */
        public ExpressionTree getNodeRandom() {
            int target=RNG.nextInt(count());
            ExpressionTree pins[]=new ExpressionTree[10];
            int capacity=10;
            pins[0]=this;
            for(int pin=1;pin>0;target--) {
                ExpressionTree now=pins[--pin];
                if(target==0) {
                    return now;
                }
                if(now.right!=null) {
                    pins[pin++]=now.right;
                }
                if(now.left!=null) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        ExpressionTree newPins[]=new ExpressionTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
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
        public double calculate(double x) {
            ExpressionTree pins[]=new ExpressionTree[10];
            int pin=0,capacity=10;
            ExpressionTree now=this;
            ExpressionTree last=null;
            double result[]=new double[10];
            int count=0,resultCapacity=10;
            while(now!=null||pin>0) {
                for(;now!=null;now=now.left) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        ExpressionTree newPins[]=new ExpressionTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
                    }
                    pins[pin++]=now;
                }
                ExpressionTree top=pins[pin-1];
                if(top.right!=null&&top.right!=last) {
                    now=top.right;
                } else {
                    if(top.type<=0&&count>=resultCapacity) {
                        resultCapacity=(resultCapacity<<1)+2;
                        double newResult[]=new double[resultCapacity];
                        System.arraycopy(result,0,newResult,0,count);
                        result=newResult;
                    }
                    switch(top.type) {
                        case -1->result[count++]=x;
                        case 0->result[count++]=top.value;
                        case 1-> {
                            double addend2=result[--count];
                            double addend1=result[--count];
                            result[count++]=addend1+addend2;
                        }
                        case 2-> {
                            double subtrahend=result[--count];
                            double minend=result[--count];
                            result[count++]=minend-subtrahend;
                        }
                        case 3-> {
                            double factor2=result[--count];
                            double factor1=result[--count];
                            result[count++]=factor1*factor2;
                        }
                        case 4-> {
                            double divisor=result[--count];
                            if(divisor==0) {
                                return Double.NaN;
                            }
                            double dividend=result[--count];
                            result[count++]=dividend/divisor;
                        }
                        case 5-> {
                            double exponential=result[--count];
                            double base=result[--count];
                            if(base==0&&exponential==0) {
                                return Double.NaN;
                            }
                            result[count++]=Math.pow(base,exponential);
                        }
                        case 6-> {
                            double power=result[--count];
                            double base=result[--count];
                            if(power<=0||base==1||base<=0) {
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
        <p>表达式树值计算</p><br>
        计算多个自变量值各自的表达式树的值。
        @param x 多个自变量值。
        @return 自变量值对应的表达式树的值数组。
        */
        public double[] calculate(double... x) {
            int length=x.length;
            ExpressionTree pins[]=new ExpressionTree[10];
            int pin=0,capacity=10;
            ExpressionTree now=this;
            ExpressionTree last=null;
            double result[][]=new double[length][10];
            int count=0,resultCapacity=10;
            while(now!=null||pin>0) {
                for(;now!=null;now=now.left) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        ExpressionTree newPins[]=new ExpressionTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
                    }
                    pins[pin++]=now;
                }
                ExpressionTree top=pins[pin-1];
                if(top.right!=null&&top.right!=last) {
                    now=top.right;
                } else {
                    if(top.type<=0&&count>=resultCapacity) {
                        resultCapacity=(resultCapacity<<1)+2;
                        double newResult[][]=new double[length][resultCapacity];
                        for(int i=0;i<length;i++) {
                            System.arraycopy(result[i],0,newResult[i],0,count);
                        }
                        result=newResult;
                    }
                    switch(top.type) {
                        case -1-> {
                            for(int i=0;i<length;i++) {
                                result[i][count]=x[i];
                            }
                            count++;
                        }
                        case 0-> {
                            for(int i=0;i<length;i++) {
                                result[i][count]=top.value;
                            }
                            count++;
                        }
                        case 1-> {
                            count--;
                            for(int i=0;i<length;i++) {
                                double addend2=result[i][count];
                                double addend1=result[i][count-1];
                                result[i][count-1]=addend1+addend2;
                            }
                        }
                        case 2-> {
                            count--;
                            for(int i=0;i<length;i++) {
                                double subtrahend=result[i][count];
                                double minend=result[i][count-1];
                                result[i][count-1]=minend-subtrahend;
                            }
                        }
                        case 3-> {
                            count--;
                            for(int i=0;i<length;i++) {
                                double factor2=result[i][count];
                                double factor1=result[i][count-1];
                                result[i][count-1]=factor1*factor2;
                            }
                        }
                        case 4-> {
                            count--;
                            for(int i=0;i<length;i++) {
                                double divisor=result[i][count];
                                divisor=divisor==0?10000000000.0:divisor;
                                double dividend=result[i][count-1];
                                result[i][count-1]=dividend/divisor;
                            }
                        }
                        case 5-> {
                            count--;
                            for(int i=0;i<length;i++) {
                                double exponential=result[i][count];
                                double base=result[i][count-1];
                                if(base==0&&exponential==0) {
                                    base=10000000000.0;
                                    exponential=1;
                                }
                                result[i][count-1]=Math.pow(base,exponential);
                            }
                        }
                        case 6-> {
                            count--;
                            for(int i=0;i<length;i++) {
                                double power=result[i][count];
                                double base=result[i][count-1];
                                if(power<=0||base==1||base<=0) {
                                    power=100000000000.0;
                                    base=2;
                                }
                                result[i][count-1]=Math.log(power)/Math.log(base);
                            }
                        }
                    }
                    last=top;
                    pin--;
                }
            }
            double returning[]=new double[length];
            for(int i=0;i<length;i++) {
                returning[i]=result[i][0];
            }
            return returning;
        }
        /**
        <p>适应度计算</p><br>
        计算表达式树对点集的适应度。
        @param x 自变量值数组。
        @param y 因变量值数组。
        @param complexityPenalty 复杂度惩罚系数。
        @return 表达式树对点集的适应度。
        */
        public double fitness(double x[],double y[],double complexityPenalty) {
            double squaredDifference=0;
            double values[]=calculate(x);
            for(int i=0;i<x.length;i++) {
                double difference=values[i]-y[i];
                squaredDifference+=difference*difference;
            }
            return squaredDifference/x.length+(complexityPenalty==0?0:complexityPenalty*count());
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
        public static ExpressionTree[] crossover(ExpressionTree individual1,ExpressionTree individual2) {
            ExpressionTree filial1=individual1.clone();
            ExpressionTree filial2=individual2.clone();
            ExpressionTree crossingPoint1=filial1.getNodeRandom();
            ExpressionTree crossingPoint2=filial2.getNodeRandom();
            int tempType=crossingPoint1.type;
            crossingPoint1.type=crossingPoint2.type;
            crossingPoint2.type=tempType;
            double tempValue=crossingPoint1.value;
            crossingPoint1.value=crossingPoint2.value;
            crossingPoint2.value=tempValue;
            ExpressionTree tempLeft=crossingPoint1.left;
            crossingPoint1.left=crossingPoint2.left;
            crossingPoint2.left=tempLeft;
            ExpressionTree tempRight=crossingPoint1.right;
            crossingPoint1.right=crossingPoint2.right;
            crossingPoint2.right=tempRight;
            filial1.simplify();
            filial2.simplify();
            return new ExpressionTree[]{filial1,filial2};
        }
        /**
        <p>突变</p><br>
        <p>此方法会修改调用对象。</p><br>
        随机改变表达式树的一个节点的类型或值。
        @param maxDepth 最大突变深度。
        @param constantRange 突变的常量结点值的范围。<br>
        常量结点值在[-<code>constantRange</code>,<code>constantRange</code>)范围内随机生成。
        @return 突变后的节点类型。
        */
        public int mutate(int maxDepth,double constantRange) {
            ExpressionTree mutationPoint=getNodeRandom();
            if(mutationPoint.type<=0) {
                if(RNG.nextBoolean()) {
                    if(mutationPoint.type==-1) {
                        mutationPoint.type=0;
                        mutationPoint.value=RNG.nextDouble(-constantRange,constantRange);
                    } else {
                        mutationPoint.type=-1;
                    }
                } else if(mutationPoint.type==0) {
                    mutationPoint.value=RNG.nextDouble(-constantRange,constantRange);
                }
            } else {
                if(RNG.nextBoolean()) {
                    mutationPoint.type=RNG.nextInt(6)+1;
                } else {
                    int mutatingDepth=RNG.nextInt(maxDepth)+1;
                    ExpressionTree mutatingSubtree=new ExpressionTree(mutatingDepth,RNG.nextBoolean(),0.7,constantRange);
                    mutationPoint.type=mutatingSubtree.type;
                    mutationPoint.value=mutatingSubtree.value;
                    mutationPoint.left=mutatingSubtree.left;
                    mutationPoint.right=mutatingSubtree.right;
                }
            }
            simplify();
            return mutationPoint.type;
        }
        /**
        <p>字符串表示</p>
        @return 表达式树的字符串表示。
        */
        public String toString() {
            ExpressionTree pins[]=new ExpressionTree[10];
            int pin=0,capacity=10;
            ExpressionTree now=this;
            ExpressionTree last=null;
            String result[]=new String[10];
            int count=0,resultCapacity=10;
            while(now!=null||pin>0) {
                for(;now!=null;now=now.left) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        ExpressionTree newPins[]=new ExpressionTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
                    }
                    pins[pin++]=now;
                }
                ExpressionTree top=pins[pin-1];
                if(top.right!=null&&top.right!=last) {
                    now=top.right;
                } else {
                    if(top.type<=0&&count>=resultCapacity) {
                        resultCapacity=(resultCapacity<<1)+2;
                        String newResult[]=new String[resultCapacity];
                        System.arraycopy(result,0,newResult,0,count);
                        result=newResult;
                    }
                    switch(top.type) {
                        case -1->result[count++]="x";
                        case 0->result[count++]=""+top.value;
                        case 1-> {
                            String addend2=result[--count];
                            String addend1=result[--count];
                            result[count++]="("+addend1+"+"+addend2+")";
                        }
                        case 2-> {
                            String subtrahend=result[--count];
                            String minuend=result[--count];
                            result[count++]="("+minuend+"-"+subtrahend+")";
                        }
                        case 3-> {
                            String factor2=result[--count];
                            String factor1=result[--count];
                            result[count++]="("+factor1+"*"+factor2+")";
                        }
                        case 4-> {
                            String divisor=result[--count];
                            String dividend=result[--count];
                            result[count++]="("+dividend+"/"+divisor+")";
                        }
                        case 5-> {
                            String exponential=result[--count];
                            String base=result[--count];
                            result[count++]="("+base+"^"+exponential+")";
                        }
                        case 6-> {
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
        public ExpressionTree clone() {
            try {
                ExpressionTree pinsOriginal[]=new ExpressionTree[10];
                ExpressionTree pinsCloned[]=new ExpressionTree[10];
                int pin=1,capacity=10;
                ExpressionTree cloned=(ExpressionTree)super.clone();
                pinsOriginal[0]=this;
                pinsCloned[0]=cloned;
                while(pin>0) {
                    ExpressionTree nowOriginal=pinsOriginal[--pin];
                    ExpressionTree nowCloned=pinsCloned[pin];
                    if(nowOriginal.right!=null) {
                        nowCloned.right=new ExpressionTree(nowOriginal.right.type,nowOriginal.right.value);
                        pinsOriginal[pin]=nowOriginal.right;
                        pinsCloned[pin++]=nowCloned.right;
                    }
                    if(nowOriginal.left!=null) {
                        if(pin>=capacity) {
                            capacity=(capacity<<1)+2;
                            ExpressionTree newPinsOriginal[]=new ExpressionTree[capacity];
                            ExpressionTree newPinsCloned[]=new ExpressionTree[capacity];
                            System.arraycopy(pinsOriginal,0,newPinsOriginal,0,pin);
                            System.arraycopy(pinsCloned,0,newPinsCloned,0,pin);
                            pinsOriginal=newPinsOriginal;
                            pinsCloned=newPinsCloned;
                        }
                        nowCloned.left=new ExpressionTree(nowOriginal.left.type,nowOriginal.left.value);
                        pinsOriginal[pin]=nowOriginal.left;
                        pinsCloned[pin++]=nowCloned.left;
                    }
                }
                return cloned;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    /**
    <p>遗传算法种群规模</p>
    */
    public static int populationSize=6000;
    /**
    <p>遗传算法最大迭代次数</p>
    */
    public static int maxGeneration=86400000;
    /**
    <p>遗传算法最佳个体保留比例</p><br>
    亲代最适应个体的比例。
    */
    public static double bestRate=0.005;
    /**
    <p>遗传算法个体选择率</p><br>
    迭代时单次竞争中选择的个体数量的倒数。
    */
    public static double survivalRate=0.02;
    /**
    <p>遗传算法新个体生成比例</p><br>
    迭代时新增野生个体的比例。
    */
    public static double newIndividualRate=0.2;
    /**
    <p>遗传算法交叉互换概率</p>
    */
    public static double crossoverRate=0.7;
    /**
    <p>遗传算法突变概率</p>
    */
    public static double mutationRate=0.3;
    /**
    <p>遗传算法最大树深度限制</p>
    */
    public static int maxTreeDepth=7;
    /**
    <p>遗传算法并发数量</p>
    */
    public static int concurrentCount=20;
    /**
    <p>函数表达式树</p><br>
    本一元实函数对象的表达式树。
    */
    public ExpressionTree functionTree;
    /**
    <p>函数适应度</p><br>
    本一元实函数对象对构造坐标集的适应度。
    */
    public double fitness;
    /**
    <p>构造方法</p><br>
    通过多个指定坐标构造一元实函数对象。
    @param coordinatesXnYn 用于拟合的多个坐标，格式为x1,y1,x2,y2,...,xN,yN。
    */
    public Function(double... coordinatesXnYn) {
        int count=coordinatesXnYn.length>>1;
        double x[]=new double[count];
        double y[]=new double[count];
        for(int i=0;i<count;i++) {
            x[i]=coordinatesXnYn[i<<1];
            y[i]=coordinatesXnYn[i<<1|1];
        }
        this(x,y);
    }
    /**
    <p>构造方法</p><br>
    通过多个指定坐标构造一元实函数对象。
    @param useConcurrent 是否使用并发计算。
    @param coordinatesXnYn 用于拟合的多个坐标，格式为x1,y1,x2,y2,...,xN,yN。
    */
    public Function(boolean useConcurrent,double... coordinatesXnYn) {
        int count=coordinatesXnYn.length>>1;
        double x[]=new double[count];
        double y[]=new double[count];
        for(int i=0;i<count;i++) {
            x[i]=coordinatesXnYn[i<<1];
            y[i]=coordinatesXnYn[i<<1|1];
        }
        this(useConcurrent,x,y);
    }
    /**
    <p>构造方法</p><br>
    通过两个坐标数组构造一元实函数对象。
    @param coordinatesXn 用于拟合的x坐标数组。
    @param coordinatesYn 用于拟合的y坐标数组。
    */
    public Function(double coordinatesXn[],double coordinatesYn[]) {
        int count=coordinatesXn.length;
        double localMutationRate=mutationRate;
        double maxX=-Double.MAX_VALUE,minX=Double.MAX_VALUE;
        double maxY=-Double.MAX_VALUE,minY=Double.MAX_VALUE;
        for(int i=0;i<count;i++) {
            double nowX=coordinatesXn[i];
            double nowY=coordinatesYn[i];
            maxX=nowX>maxX?nowX:maxX;
            minX=nowX<minX?nowX:minX;
            maxY=nowY>maxY?nowY:maxY;
            minY=nowY<minY?nowY:minY;
        }
        double constantRange=(maxY-minY)*2;
        if(constantRange==0) {
            functionTree=new ExpressionTree(0,maxY);
            fitness=0;
        } else {
            int bestCount=(int)(populationSize*bestRate);
            int newCount=(int)(populationSize*newIndividualRate);
            int mutateEdge=bestCount+newCount;
            int tournamentSize=(int)(1/survivalRate);
            ExpressionTree population[]=new ExpressionTree[populationSize];
            ExpressionTree nextGeneration[]=new ExpressionTree[populationSize];
            double fitness[]=new double[populationSize];
            int indexs[]=new int[(fitness.length<<1)+2];
            double globalMinFitness=Double.MAX_VALUE;
            int generationCount=maxGeneration;
            for(int i=0;i<populationSize;i++) {
                population[i]=new ExpressionTree(maxTreeDepth,RNG.nextBoolean(),0.7,constantRange);
            }
            for(int generation=0;!Thread.interrupted()&&generation<maxGeneration;generation++) {
                for(int i=generation==0?0:bestCount;i<populationSize;i++) {
                    double nowFitness=population[i].fitness(coordinatesXn,coordinatesYn,0.5);
                    if(Double.isNaN(nowFitness)||Double.isInfinite(nowFitness)) {
                        nowFitness=Double.MAX_VALUE;
                    }
                    fitness[i]=nowFitness;
                }
                indexs[0]=0;
                indexs[1]=fitness.length-1;
                int pin=2;
                while(pin>1) {
                    int indexRight=indexs[--pin];
                    int indexLeft=indexs[--pin];
                    if(indexLeft<indexRight) {
                        int length=indexRight-indexLeft+1;
                        double temp;
                        ExpressionTree tempTree;
                        if(length<5&&fitness[indexLeft]>fitness[indexRight]) {
                            temp=fitness[indexLeft];
                            tempTree=population[indexLeft];
                            fitness[indexLeft]=fitness[indexRight];
                            population[indexLeft]=population[indexRight];
                            fitness[indexRight]=temp;
                            population[indexRight]=tempTree;
                        }
                        double pivot1=fitness[indexLeft];
                        double pivot2=fitness[indexRight];
                        if(length>=5) {
                            int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                            double a=fitness[fifth[0]],b=fitness[fifth[1]],c=fitness[fifth[2]],d=fitness[fifth[3]],e=fitness[fifth[4]];
                            double lessWin1,lessLose1,lessWin2,lessLose2,lessCandidate1,lessCandidate2,min1,greatCandidate1,greatCandidate2,greatCandidate3,max1;
                            if(a<b) {
                                lessWin1=a;
                                lessLose1=b;
                            } else {
                                lessWin1=b;
                                lessLose1=a;
                            }
                            if(c<d) {
                                lessWin2=c;
                                lessLose2=d;
                            } else {
                                lessWin2=d;
                                lessLose2=c;
                            }
                            if(lessWin1<lessWin2) {
                                min1=lessWin1;
                                lessCandidate1=lessWin2;
                                lessCandidate2=lessLose1;
                                greatCandidate1=lessLose2;
                            } else {
                                min1=lessWin2;
                                lessCandidate1=lessWin1;
                                lessCandidate2=lessLose2;
                                greatCandidate1=lessLose1;
                            }
                            if(e<min1) {
                                pivot1=min1;
                                min1=e;
                                greatCandidate2=lessCandidate1;
                                greatCandidate3=lessCandidate2;
                            } else if(e<lessCandidate1) {
                                pivot1=e<lessCandidate2?e:lessCandidate2;
                                greatCandidate2=lessCandidate1;
                                greatCandidate3=e>lessCandidate2?e:lessCandidate2;
                            } else {
                                pivot1=lessCandidate1<lessCandidate2?lessCandidate1:lessCandidate2;
                                greatCandidate2=e;
                                greatCandidate3=lessCandidate1>lessCandidate2?lessCandidate1:lessCandidate2;
                            }
                            if(greatCandidate1>greatCandidate2) {
                                if(greatCandidate2>greatCandidate3) {
                                    max1=greatCandidate1;
                                    pivot2=greatCandidate2;
                                } else if(greatCandidate3>greatCandidate1) {
                                    max1=greatCandidate3;
                                    pivot2=greatCandidate1;
                                } else {
                                    max1=greatCandidate1;
                                    pivot2=greatCandidate3;
                                }
                            } else {
                                if(greatCandidate2<greatCandidate3) {
                                    max1=greatCandidate3;
                                    pivot2=greatCandidate2;
                                } else if(greatCandidate3<greatCandidate1) {
                                    max1=greatCandidate2;
                                    pivot2=greatCandidate1;
                                } else {
                                    max1=greatCandidate2;
                                    pivot2=greatCandidate3;
                                }
                            }
                            if(pivot1==pivot2) {
                                pivot1=min1;
                                pivot2=max1;
                            }
                            for(int pivotIndex=0;pivotIndex<5;pivotIndex++) {
                                if(pivot1==fitness[fifth[pivotIndex]]) {
                                    fitness[fifth[pivotIndex]]=fitness[indexLeft];
                                    fitness[indexLeft]=pivot1;
                                    tempTree=population[fifth[pivotIndex]];
                                    population[fifth[pivotIndex]]=population[indexLeft];
                                    population[indexLeft]=tempTree;
                                    break;
                                }
                            }
                            for(int pivotIndex=4;pivotIndex>=0;pivotIndex--) {
                                if(pivot2==fitness[fifth[pivotIndex]]) {
                                    fitness[fifth[pivotIndex]]=fitness[indexRight];
                                    fitness[indexRight]=pivot2;
                                    tempTree=population[fifth[pivotIndex]];
                                    population[fifth[pivotIndex]]=population[indexRight];
                                    population[indexRight]=tempTree;
                                    break;
                                }
                            }
                        }
                        int left=indexLeft;
                        int right=indexRight;
                        int k=indexLeft+1;
                        boolean back=false;
                        while(k<right) {
                            if(fitness[k]<pivot1) {
                                temp=fitness[++left];
                                tempTree=population[left];
                                fitness[left]=fitness[k];
                                population[left]=population[k];
                                fitness[k]=temp;
                                population[k++]=tempTree;
                            } else if(fitness[k]<=pivot2) {
                                k++;
                            } else {
                                back=false;
                                while(fitness[--right]>pivot2) {
                                    if(k>=right) {
                                        back=true;
                                        break;
                                    }
                                }
                                if(!back) {
                                    if(fitness[right]<pivot1) {
                                        temp=fitness[right];
                                        tempTree=population[right];
                                        fitness[right]=fitness[k];
                                        population[right]=population[k];
                                        fitness[k]=fitness[++left];
                                        population[k]=population[left];
                                        fitness[left]=temp;
                                        population[left]=tempTree;
                                    } else {
                                        temp=fitness[right];
                                        tempTree=population[right];
                                        fitness[right]=fitness[k];
                                        population[right]=population[k];
                                        fitness[k]=temp;
                                        population[k]=tempTree;
                                    }
                                    k++;
                                }
                            }
                        }
                        temp=fitness[indexLeft];
                        tempTree=population[indexLeft];
                        fitness[indexLeft]=fitness[left];
                        population[indexLeft]=population[left];
                        fitness[left]=temp;
                        population[left]=tempTree;
                        temp=fitness[indexRight];
                        tempTree=population[indexRight];
                        fitness[indexRight]=fitness[right];
                        population[indexRight]=population[right];
                        fitness[right]=temp;
                        population[right]=tempTree;
                        indexs[pin++]=right+1;
                        indexs[pin++]=indexRight;
                        if(pivot1!=pivot2) {
                            indexs[pin++]=left+1;
                            indexs[pin++]=right-1;
                        }
                        indexs[pin++]=indexLeft;
                        indexs[pin++]=left-1;
                    }
                }
                System.arraycopy(population,0,nextGeneration,0,bestCount);
                double generateMinFitness=fitness[0];
                if(population[0].fitness(coordinatesXn,coordinatesYn,0)==0) {
                    break;
                } else if(generateMinFitness<globalMinFitness) {
                    localMutationRate-=(globalMinFitness-generateMinFitness)/globalMinFitness*(localMutationRate-mutationRate);
                    globalMinFitness=generateMinFitness;
                    generationCount=(int)(globalMinFitness*3);
                    generationCount=generationCount<100?100:generationCount;
                    System.out.println("\n当前最适应个体偏差="+globalMinFitness+"\t\t当前突变概率="+localMutationRate+"\t\t当前最适应个体：\n"+population[0]);
                } else if(--generationCount<0) {
                    if(population[0].fitness(coordinatesXn,coordinatesYn,0)<0.02*constantRange) {
                        break;
                    } else {
                        if(localMutationRate<0.7) {
                            localMutationRate+=0.001;
                        }
                        generationCount=100;
                    }
                }
                for(int i=bestCount;i<mutateEdge;i++) {
                    nextGeneration[i]=new ExpressionTree(maxTreeDepth,RNG.nextBoolean(),0.7,constantRange);
                }
                for(int i=mutateEdge;i<populationSize;i++) {
                    int target=i;
                    double minFitness=Double.MAX_VALUE;
                    for(int j=0;j<tournamentSize;j++) {
                        int index=RNG.nextInt(populationSize);
                        double nowFitness=fitness[index];
                        if(nowFitness<minFitness) {
                            minFitness=nowFitness;
                            target=index;
                        }
                    }
                    ExpressionTree parent1=population[target].clone();
                    ExpressionTree filial;
                    if(RNG.nextDouble()<crossoverRate) {
                        target=i;
                        minFitness=Double.MAX_VALUE;
                        for(int j=0;j<tournamentSize;j++) {
                            int index=RNG.nextInt(populationSize);
                            double nowFitness=fitness[index];
                            if(nowFitness<minFitness) {
                                minFitness=nowFitness;
                                target=index;
                            }
                        }
                        ExpressionTree parent2=population[target].clone();
                        ExpressionTree crossovered[]=ExpressionTree.crossover(parent1,parent2);
                        filial=crossovered[RNG.nextInt(2)];
                    } else {
                        filial=parent1.clone();
                    }
                    if(RNG.nextDouble()<localMutationRate) {
                        filial.mutate(maxTreeDepth,constantRange);
                    }
                    nextGeneration[i]=filial;
                }
                ExpressionTree temp[]=population;
                population=nextGeneration;
                nextGeneration=temp;
            }
            double bestFitness=Double.MAX_VALUE;
            ExpressionTree bestTree=population[0];
            for(int i=0;i<populationSize;i++) {
                double nowFitness=population[i].fitness(coordinatesXn,coordinatesYn,0.5);
                if(Double.isNaN(nowFitness)||Double.isInfinite(nowFitness)) {
                    nowFitness=Double.MAX_VALUE;
                }
                if(nowFitness<bestFitness) {
                    bestFitness=nowFitness;
                    bestTree=population[i];
                }
            }
            this.fitness=bestTree.fitness(coordinatesXn,coordinatesYn,0);
            functionTree=bestTree;
        }
    }
    /**
    <p>构造方法</p><br>
    通过两个坐标数组构造一元实函数对象。
    @param useConcurrent 是否使用并发计算。
    @param coordinatesXn 用于拟合的x坐标数组。
    @param coordinatesYn 用于拟合的y坐标数组。
    */
    public Function(boolean useConcurrent,double coordinatesXn[],double coordinatesYn[]) {
        if(useConcurrent) {
            ExecutorService executor=Executors.newFixedThreadPool(concurrentCount);
            Future<?> futures[]=new Future<?>[concurrentCount];
            for(int i=0;i<concurrentCount;i++) {
                futures[i]=executor.submit(()-> {
                    return new Function(coordinatesXn,coordinatesYn);
                });
            }
            executor.shutdown();
            double globalBestFitness=Double.MAX_VALUE;
            int globalBestIndex=-1;
            int completed=0;
            int lastReported=0;
            while(completed<concurrentCount) {
                completed=0;
                double currentBestFitness=Double.MAX_VALUE;
                int currentBestIndex=-1;
                for(int i=0;i<concurrentCount;i++) {
                    if(futures[i].isDone()) {
                        completed++;
                        try {
                            Function f=(Function)futures[i].get();
                            double nowFitness=f.fitness;
                            if(nowFitness<currentBestFitness) {
                                currentBestFitness=nowFitness;
                                currentBestIndex=i;
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException e) {
                        }
                    }
                }
                if(currentBestIndex>=0&&currentBestFitness<globalBestFitness) {
                    globalBestFitness=currentBestFitness;
                    globalBestIndex=currentBestIndex;
                }
                if(completed>lastReported) {
                    try {
                        System.out.println("已完成: "+completed+"/"+concurrentCount+"\t当前最佳适应度: "+globalBestFitness+"\t最佳个体: "+futures[globalBestIndex].get());
                    } catch (InterruptedException|ExecutionException e) {
                    }
                    lastReported=completed;
                }
                if(globalBestFitness==0) {
                    System.out.println("适应度已达最优，停止等待。");
                    executor.shutdownNow();
                    break;
                }
                if(completed<concurrentCount) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            if(globalBestIndex<0) {
                for(int i=0;i<concurrentCount;i++) {
                    try {
                        futures[i].get();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                    }
                }
                globalBestIndex=0;
                for(int i=1;i<concurrentCount;i++) {
                    try {
                        if(((Function)futures[i].get()).fitness<((Function)futures[globalBestIndex].get()).fitness) {
                            globalBestIndex=i;
                        }
                    } catch (InterruptedException|ExecutionException e) {
                    }
                }
            }
            try {
                Function best=(Function)futures[globalBestIndex].get();
                functionTree=best.functionTree;
                fitness=best.fitness;
            } catch (InterruptedException|ExecutionException e) {
                Function best=new Function(coordinatesXn,coordinatesYn);
                functionTree=best.functionTree;
                fitness=best.fitness;
            }
            executor.shutdownNow();
        } else {
            Function best=new Function(coordinatesXn,coordinatesYn);
            functionTree=best.functionTree;
            fitness=best.fitness;
        }
    }
    /**
    <p>计算函数值</p><br>
    计算指定自变量值的函数值。
    @param x 自变量值。
    @return 函数值。
    */
    public double calculate(double x) {
        return functionTree.calculate(x);
    }
    /**
    <p>计算函数值</p><br>
    计算多个自变量值各自的函数值。
    @param x 多个自变量值。
    @return 自变量值对应的函数值数组。
    */
    public double[] calculate(double... x) {
        return functionTree.calculate(x);
    }
    /**
    <p>字符串表示</p>
    @return 函数的字符串表示。
    */
    public String toString() {
        return "y="+functionTree.toString();
    }
}