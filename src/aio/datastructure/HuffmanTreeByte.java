package aio.datastructure;
/**
<p>霍夫曼树类(字节型)</p><br>
霍夫曼树用于压缩数据。<br>
本树用于字节型数据的压缩。
*/
public class HuffmanTreeByte {
    /**
    <p>字节元数组</p>
    */
    public byte bytes[];
    /**
    <p>字节元权值数组</p>
    */
    public int weight[];
    /**
    <p>左子树索引数组</p>
    */
    public int left[];
    /**
    <p>右子树索引数组</p>
    */
    public int right[];
    /**
    <p>字节元霍夫曼编码数组</p>
    */
    public boolean code[][];
    /**
    <p>原文霍夫曼编码数组</p>
    */
    public boolean codeOfByte[][];
    /**
    <p>字节元排序</p><br>
    <p>此方法会修改调用对象。</p><br>
    对字节元数组和权值数组依据权值进行快速排序。
    */
    private void quickSortDualPivotForHuffmanTree() {
        int indexs[]=new int[(weight.length<<1)+2];
        indexs[0]=0;
        indexs[1]=weight.length-1;
        int pin=2;
        while(pin>1) {
            int indexRight=indexs[--pin];
            int indexLeft=indexs[--pin];
            if(indexLeft<indexRight) {
                int length=indexRight-indexLeft+1;
                byte tempByte;
                int tempWeight;
                if(length<5&&weight[indexLeft]>weight[indexRight]) {
                    tempWeight=weight[indexLeft];
                    weight[indexLeft]=weight[indexRight];
                    weight[indexRight]=tempWeight;
                    tempByte=bytes[indexLeft];
                    bytes[indexLeft]=bytes[indexRight];
                    bytes[indexRight]=tempByte;
                }
                int pivot1=weight[indexLeft];
                int pivot2=weight[indexRight];
                if(length>=5) {
                    int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                    int a=weight[fifth[0]],b=weight[fifth[1]],c=weight[fifth[2]],d=weight[fifth[3]],e=weight[fifth[4]];
                    int lessWin1,lessLose1,lessWin2,lessLose2,lessCandidate1,lessCandidate2,min1;
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
                    } else {
                        min1=lessWin2;
                        lessCandidate1=lessWin1;
                        lessCandidate2=lessLose2;
                    }
                    if(e<min1) {
                        pivot1=min1;
                        min1=e;
                    } else if(e<lessCandidate1) {
                        pivot1=e<lessCandidate2?e:lessCandidate2;
                    } else {
                        pivot1=lessCandidate1<lessCandidate2?lessCandidate1:lessCandidate2;
                    }
                    int greatWin1,greatLose1,greatWin2,greatLose2,greatCandidate1,greatCandidate2,max1;
                    if(a>b) {
                        greatWin1=a;
                        greatLose1=b;
                    } else {
                        greatWin1=b;
                        greatLose1=a;
                    }
                    if(c>d) {
                        greatWin2=c;
                        greatLose2=d;
                    } else {
                        greatWin2=d;
                        greatLose2=c;
                    }
                    if(greatWin1>greatWin2) {
                        max1=greatWin1;
                        greatCandidate1=greatWin2;
                        greatCandidate2=greatLose1;
                    } else {
                        max1=greatWin2;
                        greatCandidate1=greatWin1;
                        greatCandidate2=greatLose2;
                    }
                    if(e>max1) {
                        pivot2=max1;
                        max1=e;
                    } else if(e>greatCandidate1) {
                        pivot2=e>greatCandidate2?e:greatCandidate2;
                    } else {
                        pivot2=greatCandidate1>greatCandidate2?greatCandidate1:greatCandidate2;
                    }
                    if(pivot1==pivot2) {
                        pivot1=min1;
                        pivot2=max1;
                    }
                    for(int pivotIndex=0;pivotIndex<5;pivotIndex++) {
                        if(pivot1==weight[fifth[pivotIndex]]) {
                            weight[fifth[pivotIndex]]=weight[indexLeft];
                            weight[indexLeft]=pivot1;
                            tempByte=bytes[fifth[pivotIndex]];
                            bytes[fifth[pivotIndex]]=bytes[indexLeft];
                            bytes[indexLeft]=tempByte;
                            break;
                        }
                    }
                    for(int pivotIndex=4;pivotIndex>=0;pivotIndex--) {
                        if(pivot2==weight[fifth[pivotIndex]]) {
                            weight[fifth[pivotIndex]]=weight[indexRight];
                            weight[indexRight]=pivot2;
                            tempByte=bytes[fifth[pivotIndex]];
                            bytes[fifth[pivotIndex]]=bytes[indexRight];
                            bytes[indexRight]=tempByte;
                            break;
                        }
                    }
                }
                int left=indexLeft;
                int right=indexRight;
                int k=indexLeft+1;
                boolean back=false;
                while(k<right) {
                    if(weight[k]<pivot1) {
                        tempWeight=weight[++left];
                        weight[left]=weight[k];
                        weight[k]=tempWeight;
                        tempByte=bytes[left];
                        bytes[left]=bytes[k];
                        bytes[k]=tempByte;
                        k++;
                    } else if(weight[k]<=pivot2) {
                        k++;
                    } else {
                        back=false;
                        while(weight[--right]>pivot2) {
                            if(k>=right) {
                                back=true;
                                break;
                            }
                        }
                        if(!back) {
                            if(weight[right]<pivot1) {
                                tempWeight=weight[right];
                                weight[right]=weight[k];
                                weight[k]=weight[++left];
                                weight[left]=tempWeight;
                                tempByte=bytes[right];
                                bytes[right]=bytes[k];
                                bytes[k]=bytes[left];
                                bytes[left]=tempByte;
                            } else {
                                tempWeight=weight[right];
                                weight[right]=weight[k];
                                weight[k]=tempWeight;
                                tempByte=bytes[right];
                                bytes[right]=bytes[k];
                                bytes[k]=tempByte;
                            }
                            k++;
                        }
                    }
                }
                tempWeight=weight[indexLeft];
                weight[indexLeft]=weight[left];
                weight[left]=tempWeight;
                tempByte=bytes[indexLeft];
                bytes[indexLeft]=bytes[left];
                bytes[left]=tempByte;
                tempWeight=weight[indexRight];
                weight[indexRight]=weight[right];
                weight[right]=tempWeight;
                tempByte=bytes[indexRight];
                bytes[indexRight]=bytes[right];
                bytes[right]=tempByte;
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
    }
    /**
    <p>构造方法</p><br>
    通过字节数组构建霍夫曼树。
    @param data 字节数组。
    */
    public HuffmanTreeByte(byte data[]) {
        if(data.length==0) {
            bytes=new byte[0];
            weight=new int[0];
            left=new int[0];
            right=new int[0];
            code=new boolean[0][0];
            codeOfByte=new boolean[0][0];
            return;
        }
        weight=new int[256];
        int length=data.length;
        int byteCount=0;
        for(int i=0;i<length;i++) {
            if(weight[data[i]+128]++==0) {
                byteCount++;
            }
        }
        if(byteCount==1) {
            bytes=new byte[1];
            bytes[0]=data[0];
            weight=new int[1];
            weight[0]=length;
            left=new int[1];
            left[0]=-1;
            right=new int[1];
            right[0]=-1;
            code=new boolean[1][1];
            code[0][0]=false;
            codeOfByte=new boolean[256][1];
            codeOfByte[data[0]+128][0]=false;
            return;
        }
        bytes=new byte[byteCount];
        int nodeCount=byteCount*2+1;
        int counts[]=new int[nodeCount];
        int pinC=0;
        for(int i=0;i<256;i++) {
            if(weight[i]!=0) {
                bytes[pinC]=(byte)i;
                counts[pinC]=weight[i];
                pinC++;
            }
        }
        weight=counts;
        quickSortDualPivotForHuffmanTree();
        left=new int[nodeCount];
        right=new int[nodeCount];
        weight[byteCount]=weight[0]+weight[1];
        left[byteCount]=0;
        right[byteCount]=1;
        int leafIndex=2;
        int nodeIndex=byteCount;
        int pin=byteCount;
        while(weight[pin]<length) {
            if(leafIndex<byteCount-1&&weight[leafIndex+1]<=weight[nodeIndex]) {
                weight[++pin]=weight[leafIndex]+weight[leafIndex+1];
                left[pin]=leafIndex;
                right[pin]=leafIndex+1;
                leafIndex+=2;
            } else if(nodeIndex<pin&&leafIndex<byteCount&&weight[nodeIndex+1]<=weight[leafIndex]||leafIndex>=byteCount) {
                weight[++pin]=weight[nodeIndex]+weight[nodeIndex+1];
                left[pin]=nodeIndex;
                right[pin]=nodeIndex+1;
                nodeIndex+=2;
            } else {
                weight[++pin]=weight[leafIndex]+weight[nodeIndex];
                left[pin]=leafIndex;
                right[pin]=nodeIndex;
                leafIndex++;
                nodeIndex++;
            }
        }
        pin++;
        int newWeight[]=new int[pin];
        int newLeft[]=new int[pin];
        int newRight[]=new int[pin];
        for(int i=0;i<pin;i++) {
            newWeight[i]=weight[i];
            newLeft[i]=i<byteCount?-1:left[i];
            newRight[i]=i<byteCount?-1:right[i];
        }
        weight=newWeight;
        left=newLeft;
        right=newRight;
        code=new boolean[pin][];
        int pins[]=new int[byteCount+1];
        pins[0]=pin-1;
        for(int i=1;i<=byteCount;i++) {
            pins[i]=-1;
        }
        int depth=0;
        code[pin-1]=new boolean[0];
        codeOfByte=new boolean[256][];
        while(depth>=0) {
            int now=pins[depth];
            if(left[now]!=-1&&left[now]!=pins[depth+1]&&right[now]!=pins[depth+1]) {
                code[left[now]]=new boolean[depth+1];
                for(int i=0;i<depth;i++) {
                    code[left[now]][i]=code[now][i];
                }
                code[left[now]][depth]=false;
                if(left[now]<byteCount) {
                    codeOfByte[bytes[left[now]]+128]=code[left[now]];
                }
                pins[++depth]=left[now];
            } else if(right[now]!=-1&&right[now]!=pins[depth+1]) {
                code[right[now]]=new boolean[depth+1];
                for(int i=0;i<depth;i++) {
                    code[right[now]][i]=code[now][i];
                }
                code[right[now]][depth]=true;
                if(right[now]<byteCount) {
                    codeOfByte[bytes[right[now]]+128]=code[right[now]];
                }
                pins[++depth]=right[now];
            } else {
                depth--;
            }
        }
    }
    /**
    <p>编码获取（单个字节）</p><br>
    获取指定字节的霍夫曼编码。
    @param datum 待编码字节。
    @return 字节的霍夫曼编码。<br>
    若字节不存在于树中，则返回<code>null</code>。
    */
    public String getCode(byte datum) {
        if(codeOfByte[datum+128]==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        for(boolean b:codeOfByte[datum+128]) {
            result.append(b?"1":"0");
        }
        return result.toString();
    }
    /**
    <p>编码获取（所有字节）</p><br>
    获取所有字节的霍夫曼编码。
    @return 所有字节的霍夫曼编码。
    */
    public String getAllCodes() {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<bytes.length;i++) {
            result.append(bytes[i]+"\t");
            if(code[i]==null) {
                result.append("null\n");
                continue;
            }
            for(boolean b:code[i]) {
                result.append(b?"1":"0");
            }
            result.append("\n");
        }
        return result.toString();
    }
    /**
    <p>编码</p><br>
    压缩字节数据。
    @param data 要压缩的字节数组。
    @return 压缩后的字符串。
    */
    public String encode(byte data[]) {
        int length=data.length;
        StringBuilder result=new StringBuilder();
        for(int i=0;i<length;i++) {
            for(boolean b:codeOfByte[data[i]+128]) {
                result.append(b?"1":"0");
            }
        }
        return result.toString();
    }
    /**
    <p>解码</p><br>
    通过霍夫曼编码解压缩数据。
    @param code 霍夫曼编码字符串。
    @return 解压缩后的字节数组。
    */
    public byte[] decode(String code) {
        int length=code.length();
        if(bytes.length==1) {
            byte data=bytes[0];
            byte result[]=new byte[length];
            for(int j=0;j<length;j++) {
                result[j]=data;
            }
            return result;
        }
        int root=weight.length-1;
        int now=root;
        byte result[]=new byte[length];
        int index=0;
        for(int i=0;i<length;i++) {
            char nowBit=code.charAt(i);
            if(nowBit=='0') {
                now=left[now];
            } else {
                now=right[now];
            }
            if(left[now]==-1) {
                result[index++]=bytes[now];
                now=root;
            }
        }
        byte returnBytes[]=new byte[index];
        for(int i=0;i<index;i++) {
            returnBytes[i]=result[i];
        }
        return returnBytes;
    }
}