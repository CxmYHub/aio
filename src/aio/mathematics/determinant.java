package aio.mathematics;
/**
<p>行列式类</p><br>
行列式是一个数字方阵，其本质是一个数，用于计算矩阵的行列式值。<br>
本行列式以二维数组实现，仅支持整数元素。
*/
public class Determinant implements Comparable<Determinant> {
    /**
    <p>阶数</p>
    */
    public int order;
    /**
    <p>元素</p><br>
    <code>elements[x][y]</code> 表示第x行第y列的元素。
    */
    public int elements[][];
    /**
    <p>系数</p>
    */
    public int coefficient=1;
    /**
    <p>构造方法</p><br>
    构造一个行列式对象，包含指定元素。
    @param orderNumber 行列式的阶数。
    @param elementNumbers 行列式的元素。
    */
    public Determinant(int orderNumber,int... elementNumbers) {
        if(orderNumber<=0) {
            orderNumber=(int)Math.sqrt(elementNumbers.length-1)+1;
        }
        order=orderNumber;
        elements=new int[orderNumber][orderNumber];
        for(int i=0;i<elementNumbers.length;i++) {
            elements[i/orderNumber][i%orderNumber]=elementNumbers[i];
        }
    }
    /**
    <p>构造方法</p><br>
    构造一个指定阶数的零矩阵的行列式对象。
    @param orderNumber 行列式的阶数。
    */
    public Determinant(int orderNumber) {
        order=orderNumber;
        elements=new int[orderNumber][orderNumber];
    }
    /**
    <p>构造方法</p><br>
    复制一个行列式对象。
    @param copingDeterminant 要复制的行列式对象。
    */
    public Determinant(Determinant copingDeterminant) {
        order=copingDeterminant.order;
        elements=new int[order][order];
        for(int i=0;i<order;i++) {
            System.arraycopy(copingDeterminant.elements[i],0,elements[i],0,order);
        }
    }
    /**
    <p>构造方法</p><br>
    通过矩阵构造该矩阵的行列式。
    @param squareMatrix 矩阵对象。<br>
    若矩阵不是方阵，则构造一个阶数为1的零行列式对象。
    */
    public Determinant(Matrix squareMatrix) {
        if(squareMatrix.row==squareMatrix.column) {
            order=squareMatrix.row;
            elements=new int[order][order];
            for(int i=0;i<order;i++) {
                for(int j=0;j<order;j++) {
                    elements[i][j]=squareMatrix.elements[i][j];
                }
            }
        } else {
            order=1;
            elements=new int[1][1];
        }
    }
    /**
    <p>元素获取</p><br>
    获取行列式的指定元素。
    @param targetRow 要获取的元素的行号。
    @param targetColumn 要获取的元素的列号。
    @return 指定元素的值。<br>
    若行号或列号超出范围则返回<code>Integer.MIN_VALUE</code>。
    */
    public int getElement(int targetRow,int targetColumn) {
        if(targetRow>=1&&targetRow<=order&&targetColumn>=1&&targetColumn<=order) {
            return elements[targetRow-1][targetColumn-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>行交换</p><br>
    <p>此方法会修改调用对象。</p><br>
    交换行列式的两行。
    @param row1 要交换的第一行号。
    @param row2 要交换的第二行号。
    @return 行列式的系数。<br>
    若行号超出范围则返回<code>Integer.MIN_VALUE</code>。
    */
    public int exchangeRow(int row1,int row2) {
        if(row1!=row2&&row1>0&&row2>0&&row1<=order&&row2<=order) {
            int temp;
            for(int i=0;i<order;i++) {
                temp=elements[row1-1][i];
                elements[row1-1][i]=elements[row2-1][i];
                elements[row2-1][i]=temp;
            }
            coefficient*=-1;
            return coefficient;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列交换</p><br>
    <p>此方法会修改调用对象。</p><br>
    交换行列式的两列。
    @param column1 要交换的第一列号。
    @param column2 要交换的第二列号。
    @return 行列式的系数。<br>
    若列号超出范围则返回<code>Integer.MIN_VALUE</code>。
    */
    public int exchangeColumn(int column1,int column2) {
        if(column1!=column2&&column1>0&&column2>0&&column1<=order&&column2<=order) {
            int temp;
            for(int i=0;i<order;i++) {
                temp=elements[i][column1-1];
                elements[i][column1-1]=elements[i][column2-1];
                elements[i][column2-1]=temp;
            }
            coefficient*=-1;
            return coefficient;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>转置行列式</p><br>
    <p>此方法会修改调用对象。</p><br>
    @return <code>this</code>。
    */
    public Determinant reverse() {
        int temp;
        for(int i=0;i<order;i++) {
            for(int j=0;j<i;j++) {
                temp=elements[j][i];
                elements[j][i]=elements[i][j];
                elements[i][j]=temp;
            }
        }
        return this;
    }
    /**
    <p>转置行列式</p><br>
    计算行列式的转置行列式。
    @param reversingDeterminant 要转置的行列式对象。
    @return 转置行列式。
    */
    public static Determinant reverse(Determinant reversingDeterminant) {
        Determinant result=new Determinant(reversingDeterminant.order,reversingDeterminant.order);
        for(int i=0;i<reversingDeterminant.order;i++) {
            for(int j=0;j<reversingDeterminant.order;j++) {
                result.elements[j][i]=reversingDeterminant.elements[i][j];
            }
        }
        return result;
    }
    /**
    <p>余子式计算</p><br>
    计算行列式的余子式。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 余子式对象。<br>
    若行号或列号超出范围则不进行计算，返回<code>this</code>。
    */
    public Determinant cofactor(int baseRow,int baseColumn) {
        if(baseRow>=1&&baseRow<=order&&baseColumn>=1&&baseColumn<=order) {
            Determinant result=new Determinant(order-1);
            int originRow=0;
            int originColumn=0;
            for(int i=0;i<result.order;i++) {
                originColumn=0;
                for(int j=0;j<result.order;j++) {
                    if(originRow==baseRow-1) {
                        originRow++;
                    }
                    if(originColumn==baseColumn-1) {
                        originColumn++;
                    }
                    result.elements[i][j]=elements[originRow][originColumn++];
                }
                originRow++;
            }
            return result;
        } else {
            return this;
        }
    }
    /**
    <p>余子式计算</p><br>
    计算行列式的余子式。
    @param targetDeterminant 要计算余子式的行列式对象。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 余子式对象。<br>
    若行号或列号超出范围则不进行计算，返回目标行列式对象。
    */
    public static Determinant cofactor(Determinant targetDeterminant,int baseRow,int baseColumn) {
        if(baseRow>=1&&baseRow<=targetDeterminant.order&&baseColumn>=1&&baseColumn<=targetDeterminant.order) {
            Determinant result=new Determinant(targetDeterminant.order-1);
            int originRow=0;
            int originColumn=0;
            for(int i=0;i<result.order;i++) {
                originColumn=0;
                for(int j=0;j<result.order;j++) {
                    if(originRow==baseRow-1) {
                        originRow++;
                    }
                    if(originColumn==baseColumn-1) {
                        originColumn++;
                    }
                    result.elements[i][j]=targetDeterminant.elements[originRow][originColumn++];
                }
                originRow++;
            }
            return result;
        } else {
            return targetDeterminant;
        }
    }
    /**
    <p>代数余子式计算</p><br>
    计算行列式的代数余子式。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 代数余子式对象。<br>
    若行号或列号超出范围则不进行计算，返回<code>this</code>。
    */
    public Determinant cofactorAlgebraic(int baseRow,int baseColumn) {
        Determinant result=cofactor(baseRow,baseColumn);
        if(result.order<order) {
            result.coefficient=(int)Math.pow(-1,baseRow+baseColumn);
            return result;
        } else {
            return this;
        }
    }
    /**
    <p>代数余子式计算</p><br>
    计算行列式的代数余子式。
    @param targetDeterminant 要计算代数余子式的行列式对象。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 代数余子式对象。<br>
    若行号或列号超出范围则不进行计算，返回目标行列式对象。
    */
    public static Determinant cofactorAlgebraic(Determinant targetDeterminant,int baseRow,int baseColumn) {
        Determinant result=cofactor(targetDeterminant,baseRow,baseColumn);
        if(result.order<targetDeterminant.order) {
            result.coefficient=(int)Math.pow(-1,baseRow+baseColumn);
            return result;
        } else {
            return targetDeterminant;
        }
    }
    /**
    <p>余子式系数计算</p><br>
    计算行列式的余子式的系数。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 带有系数的余子式对象。<br>
    若行号或列号超出范围则不进行计算，返回<code>this</code>。
    */
    public Determinant cofactorCoefficient(int baseRow,int baseColumn) {
        Determinant result=cofactor(baseRow,baseColumn);
        if(result.order<order) {
            result.coefficient=(int)(coefficient*elements[baseRow-1][baseColumn-1]*Math.pow(-1,baseRow+baseColumn));
            return result;
        } else {
            return this;
        }
    }
    /**
    <p>余子式系数计算</p><br>
    计算行列式的余子式的系数。
    @param targetDeterminant 要计算余子式系数的行列式对象。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 带有系数的余子式对象。<br>
    若行号或列号超出范围则不进行计算，返回目标行列式对象。
    */
    public static Determinant cofactorCoefficient(Determinant targetDeterminant,int baseRow,int baseColumn) {
        Determinant result=cofactor(targetDeterminant,baseRow,baseColumn);
        if(result.order<targetDeterminant.order) {
            result.coefficient=(int)(targetDeterminant.coefficient*targetDeterminant.elements[baseRow-1][baseColumn-1]*Math.pow(-1,baseRow+baseColumn));
            return result;
        } else {
            return targetDeterminant;
        }
    }
    /**
    <p>行列式化简</p><br>
    <p>此方法会修改调用对象。</p>
    @return 化简后的系数。
    */
    public int simplify() {
        int gcd=1;
        for(int i=0;i<order;i++) {
            gcd=Maths.gcd(elements[i]);
            if(gcd>1) {
                for(int j=0;j<order;j++) {
                    elements[i][j]/=gcd;
                }
            }
            coefficient*=gcd;
        }
        int column[]=new int[order];
        for(int j=0;j<order;j++) {
            for(int i=0;i<order;i++) {
                column[i]=elements[i][j];
            }
            gcd=Maths.gcd(column);
            for(int i=0;i<order;i++) {
                elements[i][j]/=gcd;
            }
            coefficient*=gcd;
        }
        return coefficient;
    }
    /**
    <p>系数提取</p><br>
    <p>此方法会修改调用对象。</p><br>
    提取行列式的系数。
    @return 行列式的系数。
    */
    public int extractCoefficient() {
        int result=coefficient;
        coefficient=1;
        return result;
    }
    /**
    <p>行列式值计算</p><br>
    计算行列式的值。
    @return 行列式的值。
    */
    public double value() {
        double value=0;
        double thisValue;
        int indexNumbers[]=new int[order];
        int N;
        boolean upNeeded=true,distinctNeeded=true;
        for(int i=0;i<order;i++) {
            indexNumbers[i]=i+1;
        }
        for(int i=0;i<Maths.A[order][order];i++) {
            thisValue=1;
            N=0;
            for(int j=0;j<order;j++) {
                thisValue*=elements[j][indexNumbers[j]-1];
                for(int k=j+1;k<order;k++) {
                    if(indexNumbers[j]>indexNumbers[k]) {
                        N++;
                    }
                }
            }
            if(N%2==1) {
                value-=thisValue;
            } else {
                value+=thisValue;
            }
            indexNumbers[order-1]++;
            upNeeded=true;
            distinctNeeded=true;
            do {
                if(distinctNeeded) {
                    for(int j=1;j<order;j++) {
                        for(int k=0;k<j;k++) {
                            if(indexNumbers[j]==indexNumbers[k]) {
                                indexNumbers[j]++;
                                if(indexNumbers[j]>order) {
                                    upNeeded=true;
                                }
                                k=-1;
                            }
                        }
                    }
                    distinctNeeded=false;
                }
                if(upNeeded) {
                    for(int j=order-1;j>0;j--) {
                        if(indexNumbers[j]>order) {
                            indexNumbers[j-1]++;
                            indexNumbers[j]=1;
                            distinctNeeded=true;
                        }
                    }
                    upNeeded=false;
                }
            } while(upNeeded||distinctNeeded);
        }
        return coefficient*value;
    }
    /**
    <p>字符串表示</p><br>
    @return 行列式的字符串表示。
    */
    public String toString() {
        String result="\n";
        if(coefficient==-1) {
            result+="-";
        } else if(coefficient>1||coefficient<-1) {
            result+=""+coefficient;
        } else if(coefficient==0) {
            return "0";
        }
        for(int i=0;i<order;i++) {
            result+="\t|";
            for(int j=0;j<order;j++) {
                result+=""+elements[i][j]+"\t";
            }
            result+="|\n";
        }
        return result;
    }
    /**
    <p>比较</p><br>
    比较当前行列式与指定行列式的值。
    @param comparing 要比较的行列式对象。
    @return 当前行列式与指定行列式的值比较结果。<br>
    <ul>
        <li>=0：当前行列式与指定行列式相等。</li>
        <li>&gt;0：当前行列式大于指定行列式。</li>
        <li>&lt;0：当前行列式小于指定行列式。</li>
    </ul>
    */
    public int compareTo(Determinant comparing) {
        double result=value()-comparing.value();
        if(result>-0.0000001&&result<0.0000001) {
            return 0;
        } else if(result>0) {
            return 1;
        } else {
            return -1;
        }
    }
}