package aio.datastructure;
/**
<p>图类</p><br>
图是由顶点和边组成的一种数据结构。<br>
顶点表示图中的一个点，边表示顶点之间的关系。<br>
本图以邻接矩阵形式实现。
*/
public class Graph {
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
    @param graphString 图字符串，格式为<code>{(v1,v2,w1),(v2,v3,w2),...}</code>。
    @param type 图的类型。<br>
    <ol>
        <li>无向无权图。</li>
        <li>无向有权图。</li>
        <li>有向有权图。</li>
    </ol>
    */
    public Graph(String graphString,int type) {
        int graphArray[][]=new int[graphString.length()/5][3];
        for(int i=0;i<graphArray.length;i++) {
            graphArray[i][0]=-1;
            graphArray[i][1]=-1;
            graphArray[i][2]=1;
        }
        int count=0;
        int thisNumber=0;
        int maxNumber=Integer.MIN_VALUE;
        int round=0;
        for(int i=0;i<graphString.length();i++) {
            if(graphString.charAt(i)=='{') {
                count=0;
                round++;
            } else if(graphString.charAt(i)==','||graphString.charAt(i)=='}') {
                graphArray[round-1][count]=thisNumber;
                if(count<2&&maxNumber<thisNumber) {
                    maxNumber=thisNumber;
                }
                thisNumber=0;
                count++;
            } else {
                thisNumber*=10;
                thisNumber+=graphString.charAt(i)-'0';
            }
        }
        vexs=maxNumber;
        elements=new int[vexs][vexs];
        if(type==3) {
            directed=true;
            righted=true;
        } else if(type==2) {
            directed=true;
        } else if(type==1) {
            righted=true;
        }
        if(righted==true) {
            for(int i=0;i<vexs;i++) {
                for(int j=0;j<vexs;j++) {
                    elements[i][j]=Integer.MAX_VALUE;
                }
            }
            for(int i=0;i<round;i++) {
                elements[graphArray[i][0]-1][graphArray[i][1]-1]=graphArray[i][2];
                if(directed==false) {
                    elements[graphArray[i][1]-1][graphArray[i][0]-1]=graphArray[i][2];
                }
            }
        } else {
            for(int i=0;i<round;i++) {
                elements[graphArray[i][0]-1][graphArray[i][1]-1]=graphArray[i][2];
                if(directed==false) {
                    elements[graphArray[i][1]-1][graphArray[i][0]-1]=graphArray[i][2];
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
    public int costMin(int start,int end) {
        start--;
        end--;
        int cost[]=new int[vexs];
        int tag[]=new int[vexs];
        tag[start]=1;
        int pin=start;
        int minPin;
        for(int i=0;i<vexs;i++) {
            cost[i]=Integer.MAX_VALUE;
        }
        cost[start]=0;
        for(int i=0;i<vexs;i++) {
            minPin=-1;
            for(int j=0;j<vexs;j++) {
                if(minPin==-1&&tag[j]==1) {
                    minPin=j;
                }
                if(tag[j]==1&&cost[minPin]>cost[j]) {
                    minPin=j;
                }
            }
            pin=minPin;
            for(int j=0;j<vexs;j++) {
                if(elements[pin][j]<Integer.MAX_VALUE&&cost[j]>cost[pin]+elements[pin][j]) {
                    cost[j]=cost[pin]+elements[pin][j];
                    tag[j]=1;
                }
            }
            tag[pin]=2;
            if(pin==end) {
                break;
            }
        }
        return cost[end];
    }
    /**
    <p>字符串表示</p><br>
    @return 图的字符串表示。
    */
    public String toString() {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<vexs;i++) {
            result.append("{");
            for(int j=0;j<vexs;j++) {
                if(j>0) {
                    result.append(",");
                }
                if(elements[i][j]==Integer.MAX_VALUE) {
                    result.append("∞");
                } else {
                    result.append(elements[i][j]);
                }
            }
            result.append("}\n");
        }
        return result.toString();
    }
}