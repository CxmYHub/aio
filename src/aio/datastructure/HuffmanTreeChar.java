package aio.datastructure;
/**
<p>霍夫曼树类(字符型)</p><br>
霍夫曼树用于压缩数据。<br>
本树用于字符型数据的压缩。
*/
public class HuffmanTreeChar {
    /**
    <p>字符元数组</p>
    */
    public char characters[];
    /**
    <p>字符元权值数组</p>
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
    <p>字符元霍夫曼编码数组</p>
    */
    public boolean code[][];
    /**
    <p>原文霍夫曼编码数组</p>
    */
    public boolean codeOfCharacter[][];
    /**
    <p>字符元排序</p><br>
    <p>此方法会修改调用对象。</p><br>
    对字符元数组和权值数组依据权值进行快速排序。
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
                char tempCharacter;
                int tempWeight;
                if(length<5&&weight[indexLeft]>weight[indexRight]) {
                    tempWeight=weight[indexLeft];
                    weight[indexLeft]=weight[indexRight];
                    weight[indexRight]=tempWeight;
                    tempCharacter=characters[indexLeft];
                    characters[indexLeft]=characters[indexRight];
                    characters[indexRight]=tempCharacter;
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
                            tempCharacter=characters[fifth[pivotIndex]];
                            characters[fifth[pivotIndex]]=characters[indexLeft];
                            characters[indexLeft]=tempCharacter;
                            break;
                        }
                    }
                    for(int pivotIndex=4;pivotIndex>=0;pivotIndex--) {
                        if(pivot2==weight[fifth[pivotIndex]]) {
                            weight[fifth[pivotIndex]]=weight[indexRight];
                            weight[indexRight]=pivot2;
                            tempCharacter=characters[fifth[pivotIndex]];
                            characters[fifth[pivotIndex]]=characters[indexRight];
                            characters[indexRight]=tempCharacter;
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
                        tempCharacter=characters[left];
                        characters[left]=characters[k];
                        characters[k]=tempCharacter;
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
                                tempCharacter=characters[right];
                                characters[right]=characters[k];
                                characters[k]=characters[left];
                                characters[left]=tempCharacter;
                            } else {
                                tempWeight=weight[right];
                                weight[right]=weight[k];
                                weight[k]=tempWeight;
                                tempCharacter=characters[right];
                                characters[right]=characters[k];
                                characters[k]=tempCharacter;
                            }
                            k++;
                        }
                    }
                }
                tempWeight=weight[indexLeft];
                weight[indexLeft]=weight[left];
                weight[left]=tempWeight;
                tempCharacter=characters[indexLeft];
                characters[indexLeft]=characters[left];
                characters[left]=tempCharacter;
                tempWeight=weight[indexRight];
                weight[indexRight]=weight[right];
                weight[right]=tempWeight;
                tempCharacter=characters[indexRight];
                characters[indexRight]=characters[right];
                characters[right]=tempCharacter;
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
    通过字符串构建霍夫曼树。
    @param text 字符串。
    */
    public HuffmanTreeChar(String text) {
        if(text.length()==0) {
            characters=new char[0];
            weight=new int[0];
            left=new int[0];
            right=new int[0];
            code=new boolean[0][0];
            codeOfCharacter=new boolean[0][0];
            return;
        }
        weight=new int[65536];
        int length=text.length();
        int characterCount=0;
        for(int i=0;i<length;i++) {
            if(weight[text.charAt(i)]++==0) {
                characterCount++;
            }
        }
        if(characterCount==1) {
            characters=new char[1];
            characters[0]=text.charAt(0);
            weight=new int[1];
            weight[0]=length;
            left=new int[1];
            left[0]=-1;
            right=new int[1];
            right[0]=-1;
            code=new boolean[1][1];
            code[0][0]=false;
            codeOfCharacter=new boolean[65536][1];
            codeOfCharacter[text.charAt(0)][0]=false;
            return;
        }
        characters=new char[characterCount];
        int nodeCount=characterCount*2+1;
        int counts[]=new int[nodeCount];
        int pinC=0;
        for(int i=0;i<65536;i++) {
            if(weight[i]!=0) {
                characters[pinC]=(char)i;
                counts[pinC]=weight[i];
                pinC++;
            }
        }
        weight=counts;
        quickSortDualPivotForHuffmanTree();
        left=new int[nodeCount];
        right=new int[nodeCount];
        weight[characterCount]=weight[0]+weight[1];
        left[characterCount]=0;
        right[characterCount]=1;
        int leafIndex=2;
        int nodeIndex=characterCount;
        int pin=characterCount;
        while(weight[pin]<length) {
            if(leafIndex<characterCount-1&&weight[leafIndex+1]<=weight[nodeIndex]) {
                weight[++pin]=weight[leafIndex]+weight[leafIndex+1];
                left[pin]=leafIndex;
                right[pin]=leafIndex+1;
                leafIndex+=2;
            } else if(nodeIndex<pin&&leafIndex<characterCount&&weight[nodeIndex+1]<=weight[leafIndex]||leafIndex>=characterCount) {
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
            newLeft[i]=i<characterCount?-1:left[i];
            newRight[i]=i<characterCount?-1:right[i];
        }
        weight=newWeight;
        left=newLeft;
        right=newRight;
        code=new boolean[pin][];
        int pins[]=new int[characterCount+1];
        pins[0]=pin-1;
        for(int i=1;i<=characterCount;i++) {
            pins[i]=-1;
        }
        int depth=0;
        code[pin-1]=new boolean[0];
        codeOfCharacter=new boolean[65536][];
        while(depth>=0) {
            int now=pins[depth];
            if(left[now]!=-1&&left[now]!=pins[depth+1]&&right[now]!=pins[depth+1]) {
                code[left[now]]=new boolean[depth+1];
                for(int i=0;i<depth;i++) {
                    code[left[now]][i]=code[now][i];
                }
                code[left[now]][depth]=false;
                if(left[now]<characterCount) {
                    codeOfCharacter[characters[left[now]]]=code[left[now]];
                }
                pins[++depth]=left[now];
            } else if(right[now]!=-1&&right[now]!=pins[depth+1]) {
                code[right[now]]=new boolean[depth+1];
                for(int i=0;i<depth;i++) {
                    code[right[now]][i]=code[now][i];
                }
                code[right[now]][depth]=true;
                if(right[now]<characterCount) {
                    codeOfCharacter[characters[right[now]]]=code[right[now]];
                }
                pins[++depth]=right[now];
            } else {
                depth--;
            }
        }
    }
    /**
    <p>编码获取（单个字符）</p><br>
    获取指定字符的霍夫曼编码。
    @param character 待编码字符。
    @return 字符的霍夫曼编码。<br>
    若字符不存在于树中，则返回<code>null</code>。
    */
    public String getCode(char character) {
        if(codeOfCharacter[character]==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        for(boolean b:codeOfCharacter[character]) {
            result.append(b?"1":"0");
        }
        return result.toString();
    }
    /**
    <p>编码获取（所有字符）</p><br>
    获取所有字符的霍夫曼编码。
    @return 所有字符的霍夫曼编码。
    */
    public String getAllCodes() {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<characters.length;i++) {
            result.append(characters[i]+"\t");
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
    压缩字符串数据。
    @param text 要压缩的字符串。
    @return 压缩后的字符串。
    */
    public String encode(String text) {
        int length=text.length();
        StringBuilder result=new StringBuilder();
        for(int i=0;i<length;i++) {
            for(boolean b:codeOfCharacter[text.charAt(i)]) {
                result.append(b?"1":"0");
            }
        }
        return result.toString();
    }
    /**
    <p>解码</p><br>
    通过霍夫曼编码解压缩数据。
    @param code 霍夫曼编码字符串。
    @return 解压缩后的字符串。
    */
    public String decode(String code) {
        int length=code.length();
        if(characters.length==1) {
            char character=characters[0];
            return String.valueOf(character).repeat(length);
        }
        int root=weight.length-1;
        int now=root;
        StringBuilder result=new StringBuilder();
        for(int i=0;i<length;i++) {
            char nowBit=code.charAt(i);
            if(nowBit=='0') {
                now=left[now];
            } else {
                now=right[now];
            }
            if(left[now]==-1) {
                result.append(characters[now]);
                now=root;
            }
        }
        return result.toString();
    }
}