package tools.data_structure;
/**
<p>图类</p><br>
图是由顶点和边组成的一种数据结构。<br>
顶点表示图中的一个点，边表示顶点之间的关系。<br>
本图以邻接矩阵形式实现。
*/
public class graph
{
    /**
    <p>邻接矩阵</p>
    */
    public int elements[][];
    /**
    <p>顶点数量</p>
    */
    public int vexs=1;
    /**
    <p>是否为有向图</p>
    */
    public boolean directed=false;
    /**
    <p>是否为有权图</p>
    */
    public boolean righted=false;
    /**
    <p>构造方法</p><br>
    通过图字符串构造一个图。
    @param graph_string 图字符串，格式为<code>{(v1,v2,w1),(v2,v3,w2),...}</code>。
    @param type 图的类型。<br>
    <ol>
        <li>无向无权图。</li>
        <li>无向有权图。</li>
        <li>有向有权图。</li>
    </ol>
    */
    public graph(String graph_string,int type)
    {
        int graph_array[][]=new int[graph_string.length()/5][3];
        for(int i=0;i<graph_array.length;i++)
        {
            graph_array[i][0]=-1;
            graph_array[i][1]=-1;
            graph_array[i][2]=1;
        }
        int count=0;
        int this_number=0;
        int max_number=Integer.MIN_VALUE;
        int round=0;
        for(int i=0;i<graph_string.length();i++)
        {
            if(graph_string.charAt(i)=='{')
            {
                count=0;
                round++;
            }
            else if(graph_string.charAt(i)==','||graph_string.charAt(i)=='}')
            {
                graph_array[round-1][count]=this_number;
                if(count<2&&max_number<this_number)
                {
                    max_number=this_number;
                }
                this_number=0;
                count++;
            }
            else
            {
                this_number*=10;
                this_number+=graph_string.charAt(i)-'0';
            }
        }
        vexs=max_number;
        elements=new int[vexs][vexs];
        if(type==3)
        {
            directed=true;
            righted=true;
        }
        else if(type==2)
        {
            directed=true;
        }
        else if(type==1)
        {
            righted=true;
        }
        if(righted==true)
        {
            for(int i=0;i<vexs;i++)
            {
                for(int j=0;j<vexs;j++)
                {
                    elements[i][j]=Integer.MAX_VALUE;
                }
            }
            for(int i=0;i<round;i++)
            {
                elements[graph_array[i][0]-1][graph_array[i][1]-1]=graph_array[i][2];
                if(directed==false)
                {
                    elements[graph_array[i][1]-1][graph_array[i][0]-1]=graph_array[i][2];
                }
            }
        }
        else
        {
            for(int i=0;i<round;i++)
            {
                elements[graph_array[i][0]-1][graph_array[i][1]-1]=graph_array[i][2];
                if(directed==false)
                {
                    elements[graph_array[i][1]-1][graph_array[i][0]-1]=graph_array[i][2];
                }
            }
        }
    }
    /**
    <p>最小路径成本</p><br>
    计算从起始顶点到目标顶点的最小路径成本。
    @param start 起始顶点的编号。
    @param end 目标顶点的编号。
    @return 从起始顶点到目标顶点的最小路径成本。
    */
    public int cost_min(int start,int end)
    {
        start--;
        end--;
        int cost[]=new int[vexs];
        int tag[]=new int[vexs];
        tag[start]=1;
        int pin=start;
        int min_pin;
        for(int i=0;i<vexs;i++)
        {
            cost[i]=Integer.MAX_VALUE;
        }
        cost[start]=0;
        for(int i=0;i<vexs;i++)
        {
            min_pin=-1;
            for(int j=0;j<vexs;j++)
            {
                if(min_pin==-1&&tag[j]==1)
                {
                    min_pin=j;
                }
                if(tag[j]==1&&cost[min_pin]>cost[j])
                {
                    min_pin=j;
                }
            }
            pin=min_pin;
            for(int j=0;j<vexs;j++)
            {
                if(elements[pin][j]<Integer.MAX_VALUE&&cost[j]>cost[pin]+elements[pin][j])
                {
                    cost[j]=cost[pin]+elements[pin][j];
                    tag[j]=1;
                }
            }
            tag[pin]=2;
            if(pin==end)
            {
                break;
            }
        }
        return cost[end];
    }
    /**
    <p>字符串表示</p><br>
    @return 图的字符串表示。
    */
    public String toString()
    {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<vexs;i++)
        {
            result.append("{");
            for(int j=0;j<vexs;j++)
            {
                if(j>0)
                {
                    result.append(",");
                }
                if(elements[i][j]==Integer.MAX_VALUE)
                {
                    result.append("∞");
                }
                else
                {
                    result.append(elements[i][j]);
                }
            }
            result.append("}\n");
        }
        return result.toString();
    }
}