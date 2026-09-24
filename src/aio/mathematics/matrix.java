package aio.mathematics;
/**
<p>矩阵类</p><br>
矩阵是一个按照长方阵列排列的复数或实数集合。<br>
本矩阵以二维数组实现，仅支持整数元素。
*/
public class Matrix {
    /**
    <p>行数</p>
    */
    public int row;
    /**
    <p>列数</p>
    */
    public int column;
    /**
    <p>元素</p>
    */
    public int elements[][];
    /**
    <p>系数</p>
    */
    public int coefficient=1;
    /**
    <p>构造方法</p><br>
    通过行、列数和元素构造矩阵。
    @param rowNumber 矩阵的行数。
    @param columnNumber 矩阵的列数。
    @param elementNumbers 矩阵的元素(行优先)。
    */
    public Matrix(int rowNumber,int columnNumber,int... elementNumbers) {
        if(rowNumber<=0&&columnNumber>0) {
            rowNumber=(int)((elementNumbers.length-1)/columnNumber)+1;
        } else if(columnNumber<=0&&rowNumber>0) {
            columnNumber=(int)((elementNumbers.length-1)/rowNumber)+1;
        } else if(rowNumber<=0&&columnNumber<=0) {
            rowNumber=(int)Math.sqrt(elementNumbers.length-1)+1;
            columnNumber=rowNumber;
        }
        row=rowNumber;
        column=columnNumber;
        elements=new int[rowNumber][columnNumber];
        for(int i=0;i<elementNumbers.length;i++) {
            elements[i/columnNumber][i%columnNumber]=elementNumbers[i];
        }
    }
    /**
    <p>构造方法</p><br>
    通过元素数组构造矩阵。
    @param elementNumbers 元素数组(行优先)。
    */
    public Matrix(int elementNumbers[][]) {
        row=elementNumbers.length;
        column=elementNumbers[0].length;
        elements=new int[row][column];
        for(int i=0;i<row;i++) {
            System.arraycopy(elementNumbers[i],0,elements[i],0,column);
        }
    }
    /**
    <p>构造方法</p><br>
    通过行、列数构造一个零矩阵。
    @param rowNumber 矩阵的行数。
    @param columnNumber 矩阵的列数。
    */
    public Matrix(int rowNumber,int columnNumber) {
        row=rowNumber;
        column=columnNumber;
        elements=new int[rowNumber][columnNumber];
    }
    /**
    <p>构造方法</p><br>
    通过行列式构造矩阵。
    @param mirrorDeterminant 要构造的矩阵的行列式。
    */
    public Matrix(Determinant mirrorDeterminant) {
        row=mirrorDeterminant.order;
        column=mirrorDeterminant.order;
        elements=new int[row][column];
        for(int i=0;i<row;i++) {
            System.arraycopy(mirrorDeterminant.elements[i],0,elements[i],0,column);
        }
    }
    /**
    <p>元素获取</p><br>
    获取矩阵中指定位置的元素。
    @param targetRow 要获取的元素所在的行。
    @param targetColumn 要获取的元素所在的列。
    @return 如果指定位置在矩阵范围内，则返回该位置的元素。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int getElement(int targetRow,int targetColumn) {
        if(targetRow>=1&&targetRow<=row&&targetColumn>=1&&targetColumn<=column) {
            return elements[targetRow-1][targetColumn-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>行交换</p><br>
    <p>此方法会修改调用对象。</p><br>
    交换矩阵中的两行。
    @param row1 要交换的第一行。
    @param row2 要交换的第二行。
    @return 如果交换成功则返回矩阵系数。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int rowExchange(int row1,int row2) {
        if(row1!=row2&&row1>0&&row2>0&&row1<=row&&row2<=row) {
            int temp;
            for(int i=0;i<column;i++) {
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
    <p>行倍乘</p><br>
    <p>此方法会修改调用对象。</p><br>
    将矩阵中的一行乘以一个非零数。
    @param targetRow 要操作的行。
    @param k 要乘以的非零数。
    @return 如果操作成功则返回该行的第一个元素。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int rowMultiply(int targetRow,int k) {
        if(targetRow>0&&targetRow<=row) {
            for(int i=0;i<column;i++) {
                elements[targetRow-1][i]*=k;
            }
            return elements[targetRow-1][0];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>行倍加</p><br>
    <p>此方法会修改调用对象。</p><br>
    将矩阵中的一行加上另一行的倍数。<br>
    将<code>sourceRow</code>行的每个元素乘以<code>k</code>，加至<code>targetRow</code>行的对应元素。
    @param sourceRow 要加上的行。
    @param targetRow 要操作的行。
    @param k 要乘以的倍数。
    @return 如果操作成功则返回该行的第一个元素。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int rowAddTo(int sourceRow,int targetRow,int k) {
        if(sourceRow!=targetRow&&sourceRow>0&&targetRow>0&&sourceRow<=row&&targetRow<=row) {
            for(int i=0;i<column;i++) {
                elements[targetRow-1][i]+=elements[sourceRow-1][i]*k;
            }
            return elements[targetRow-1][0];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列交换</p><br>
    <p>此方法会修改调用对象。</p><br>
    交换矩阵中的两列。
    @param column1 要交换的第一列。
    @param column2 要交换的第二列。
    @return 如果交换成功则返回矩阵系数。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int columnExchange(int column1,int column2) {
        if(column1!=column2&&column1>0&&column2>0&&column1<=column&&column2<=column) {
            int temp;
            for(int i=0;i<row;i++) {
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
    <p>列倍乘</p><br>
    <p>此方法会修改调用对象。</p><br>
    将矩阵中的一列乘以一个非零数。
    @param targetColumn 要操作的列。
    @param k 要乘以的非零数。
    @return 如果操作成功则返回该列的第一个元素。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int columnMultiply(int targetColumn,int k) {
        if(targetColumn>0&&targetColumn<=column) {
            for(int i=0;i<row;i++) {
                elements[i][targetColumn-1]*=k;
            }
            return elements[0][targetColumn-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列倍加</p><br>
    <p>此方法会修改调用对象。</p><br>
    将矩阵中的一列加上另一列的倍数。<br>
    将<code>sourceColumn</code>列的每个元素乘以<code>k</code>，加至<code>targetColumn</code>列的对应元素。
    @param sourceColumn 要加上的列。
    @param targetColumn 要操作的列。
    @param k 要乘以的倍数。
    @return 如果操作成功则返回该列的第一个元素。<br>
    否则返回<code>Integer.MIN_VALUE</code>。
    */
    public int columnAddTo(int sourceColumn,int targetColumn,int k) {
        if(sourceColumn!=targetColumn&&sourceColumn>0&&targetColumn>0&&sourceColumn<=column&&targetColumn<=column) {
            for(int i=0;i<row;i++) {
                elements[i][targetColumn-1]+=elements[i][sourceColumn-1]*k;
            }
            return elements[0][targetColumn-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>矩阵转置</p><br>
    计算矩阵的转置矩阵。
    @param reversingMatrix 要计算转置矩阵的矩阵。
    @return 转置后的矩阵。
    */
    public static Matrix reverse(Matrix reversingMatrix) {
        Matrix result=new Matrix(reversingMatrix.column,reversingMatrix.row);
        for(int i=0;i<reversingMatrix.row;i++) {
            for(int j=0;j<reversingMatrix.column;j++) {
                result.elements[j][i]=reversingMatrix.elements[i][j];
            }
        }
        return result;
    }
    /**
    <p>余子式计算</p><br>
    计算矩阵的余子式。
    @param targetMatrix 要计算余子式的矩阵。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 余子式矩阵。<br>
    若行号或列号超出范围则不进行计算，返回目标矩阵对象。
    */
    public static Matrix cofactor(Matrix targetMatrix,int baseRow,int baseColumn) {
        if(baseRow>=1&&baseRow<=targetMatrix.row&&baseColumn>=1&&baseColumn<=targetMatrix.column) {
            Matrix result=new Matrix(targetMatrix.row-1,targetMatrix.column-1);
            int originRow=0;
            int originColumn=0;
            for(int i=0;i<result.row;i++) {
                originColumn=0;
                for(int j=0;j<result.column;j++) {
                    if(originRow==baseRow-1) {
                        originRow++;
                    }
                    if(originColumn==baseColumn-1) {
                        originColumn++;
                    }
                    result.elements[i][j]=targetMatrix.elements[originRow][originColumn++];
                }
                originRow++;
            }
            return result;
        } else {
            return targetMatrix;
        }
    }
    /**
    <p>代数余子式计算</p><br>
    计算矩阵的代数余子式。
    @param targetMatrix 要计算代数余子式的矩阵。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 代数余子式矩阵。<br>
    若行号或列号超出范围则不进行计算，返回目标矩阵对象。
    */
    public static Matrix cofactorAlgebraic(Matrix targetMatrix,int baseRow,int baseColumn) {
        Matrix result=cofactor(targetMatrix,baseRow,baseColumn);
        if(result.row<targetMatrix.row) {
            result.coefficient=(int)Math.pow(-1,baseRow+baseColumn);
            return result;
        } else {
            return targetMatrix;
        }
    }
    /**
    <p>余子式系数计算</p><br>
    计算矩阵的余子式的系数。
    @param targetMatrix 要计算余子式系数的矩阵。
    @param baseRow 基准行。
    @param baseColumn 基准列。
    @return 带有系数的余子式矩阵。<br>
    若行号或列号超出范围则不进行计算，返回目标矩阵对象。
    */
    public static Matrix cofactorCoefficient(Matrix targetMatrix,int baseRow,int baseColumn) {
        Matrix result=cofactor(targetMatrix,baseRow,baseColumn);
        if(result.row<targetMatrix.row) {
            result.coefficient=(int)(targetMatrix.coefficient*targetMatrix.elements[baseRow-1][baseColumn-1]*Math.pow(-1,baseRow+baseColumn));
            return result;
        } else {
            return targetMatrix;
        }
    }
    /**
    <p>伴随矩阵</p><br>
    计算矩阵的伴随矩阵。
    @param targetMatrix 要计算伴随矩阵的矩阵。
    @return 伴随矩阵。
    */
    public static Matrix adjugate(Matrix targetMatrix) {
        if(targetMatrix.row==targetMatrix.column) {
            Matrix result=new Matrix(targetMatrix.row,targetMatrix.row);
            Determinant mirror=new Determinant(targetMatrix);
            for(int i=0;i<result.row;i++) {
                for(int j=0;j<result.column;j++) {
                    result.elements[j][i]=(int)Determinant.cofactorAlgebraic(mirror,i+1,j+1).value();
                }
            }
            return result;
        } else {
            return targetMatrix;
        }
    }
    /**
    <p>矩阵加法运算</p><br>
    计算本矩阵与指定矩阵的和。
    @param source 加数矩阵。
    @return 本矩阵与指定矩阵的和。
    */
    public Matrix add(Matrix source) {
        if(row==source.row&&column==source.column) {
            Matrix result=new Matrix(row,column);
            for(int i=0;i<result.row;i++) {
                for(int j=0;j<result.column;j++) {
                    result.elements[i][j]=elements[i][j]+source.elements[i][j];
                }
            }
            return result;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵加法运算</p><br>
    计算两个矩阵的和。
    @param source1 第一个矩阵。
    @param source2 第二个矩阵。
    @return 两个矩阵的和。
    */
    public static Matrix add(Matrix source1,Matrix source2) {
        if(source1.row==source2.row&&source1.column==source2.column) {
            Matrix result=new Matrix(source1.row,source1.column);
            for(int i=0;i<result.row;i++) {
                for(int j=0;j<result.column;j++) {
                    result.elements[i][j]=source1.elements[i][j]+source2.elements[i][j];
                }
            }
            return result;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵减法运算</p><br>
    计算本矩阵与指定矩阵的差。
    @param subtrahend 减数矩阵。
    @return 本矩阵与指定矩阵的差。
    */
    public Matrix subtract(Matrix subtrahend) {
        if(row==subtrahend.row&&column==subtrahend.column) {
            Matrix result=new Matrix(row,column);
            for(int i=0;i<result.row;i++) {
                for(int j=0;j<result.column;j++) {
                    result.elements[i][j]=elements[i][j]-subtrahend.elements[i][j];
                }
            }
            return result;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵减法运算</p><br>
    计算两个矩阵的差。
    @param minuend 被减数矩阵。
    @param subtrahend 减数矩阵。
    @return 两个矩阵的差。
    */
    public static Matrix subtract(Matrix minuend,Matrix subtrahend) {
        if(minuend.row==subtrahend.row&&minuend.column==subtrahend.column) {
            Matrix result=new Matrix(minuend.row,minuend.column);
            for(int i=0;i<result.row;i++) {
                for(int j=0;j<result.column;j++) {
                    result.elements[i][j]=minuend.elements[i][j]-subtrahend.elements[i][j];
                }
            }
            return result;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵数乘运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    计算矩阵的数乘。
    @param coefficient 系数。
    @return 数乘后的矩阵系数。
    */
    public int multiplyScalar(int coefficient) {
        for(int i=0;i<row;i++) {
            for(int j=0;j<column;j++) {
                elements[i][j]*=coefficient;
            }
        }
        return this.coefficient*coefficient;
    }
    /**
    <p>矩阵数乘运算</p><br>
    计算矩阵的数乘。
    @param coefficient 系数。
    @param factor 要数乘的矩阵。
    @return 数乘矩阵。
    */
    public static Matrix multiplyScalar(int coefficient,Matrix factor) {
        Matrix result=new Matrix(factor.row,factor.column);
        for(int i=0;i<result.row;i++) {
            for(int j=0;j<result.column;j++) {
                result.elements[i][j]=factor.elements[i][j]*coefficient;
            }
        }
        return result;
    }
    /**
    <p>矩阵乘法运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    计算两个矩阵的乘积。
    @param factor 右矩阵数组。
    @return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回<code>this</code>；<br>
    否则返回一个1*1的矩阵。
    */
    public Matrix multiply(int factor[][]) {
        if(column==factor.length) {
            int result[][]=new int[row][factor[0].length];
            int temp;
            for(int i=0;i<row;i++) {
                for(int j=0;j<factor[0].length;j++) {
                    temp=0;
                    for(int k=0;k<column;k++) {
                        temp+=elements[i][k]*factor[k][j];
                    }
                    result[i][j]=temp;
                }
            }
            elements=result;
            column=factor[0].length;
            return this;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵乘法运算</p><br>
    计算两个二维数组的矩阵乘积。
    @param factorLeft 左矩阵数组。
    @param factorRight 右矩阵数组。
    @return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回矩阵乘积的二维数组形式；<br>
    否则返回<code>null</code>。
    */
    public static int[][] multiply(int factorLeft[][],int factorRight[][]) {
        if(factorLeft[0].length==factorRight.length) {
            int result[][]=new int[factorLeft.length][factorRight[0].length];
            int temp;
            for(int i=0;i<result.length;i++) {
                for(int j=0;j<result[0].length;j++) {
                    temp=0;
                    for(int k=0;k<factorLeft[0].length;k++) {
                        temp+=factorLeft[i][k]*factorRight[k][j];
                    }
                    result[i][j]=temp;
                }
            }
            return result;
        } else {
            return null;
        }
    }
    /**
    <p>矩阵乘法运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    右乘矩阵。
    @param factor 右矩阵。
    @return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回<code>this</code>；<br>
    否则返回一个1*1的矩阵。
    */
    public Matrix multiply(Matrix factor) {
        if(column==factor.row) {
            int result[][]=new int[row][factor.column];
            int temp;
            for(int i=0;i<row;i++) {
                for(int j=0;j<factor.column;j++) {
                    temp=0;
                    for(int k=0;k<column;k++) {
                        temp+=elements[i][k]*factor.elements[k][j];
                    }
                    result[i][j]=temp;
                }
            }
            elements=result;
            column=factor.column;
            return this;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵乘法运算</p><br>
    计算两个矩阵的乘积。
    @param factorLeft 左矩阵。
    @param factorRight 右矩阵。
    @return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回乘积矩阵<br>
    否则返回一个1*1的矩阵。
    */
    public static Matrix multiply(Matrix factorLeft,Matrix factorRight) {
        if(factorLeft.column==factorRight.row) {
            Matrix result=new Matrix(factorLeft.row,factorRight.column);
            int temp;
            for(int i=0;i<result.row;i++) {
                for(int j=0;j<result.column;j++) {
                    temp=0;
                    for(int k=0;k<factorLeft.column;k++) {
                        temp+=factorLeft.elements[i][k]*factorRight.elements[k][j];
                    }
                    result.elements[i][j]=temp;
                }
            }
            return result;
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵幂运算</p><br>
    计算矩阵的幂。
    @param power 幂次。
    @return 矩阵的幂。
    */
    public Matrix power(int power) {
        if(row==column) {
            int[][] result=new int[row][column];
            for(int i=0;i<row;i++) {
                result[i][i]=1;
            }
            while(power>0) {
                if(power%2==0) {
                    elements=multiply(elements,elements);
                    power>>=1;
                } else {
                    result=multiply(result,elements);
                    power--;
                }
            }
            elements=result;
            return new Matrix(result);
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵幂运算</p><br>
    计算矩阵的幂。
    @param matrix 要计算幂的矩阵。
    @param power 幂次。
    @return 矩阵的幂。
    */
    public static Matrix power(Matrix matrix,int power) {
        if(matrix.row==matrix.column) {
            int[][] result=new int[matrix.row][matrix.column];
            for(int i=0;i<matrix.row;i++) {
                result[i][i]=1;
            }
            while(power>0) {
                if(power%2==0) {
                    matrix=multiply(matrix,matrix);
                    power>>=1;
                } else {
                    result=multiply(result,matrix.elements);
                    power--;
                }
            }
            return new Matrix(result);
        } else {
            return new Matrix(1,1);
        }
    }
    /**
    <p>矩阵化简</p><br>
    化简矩阵。
    @return 化简后的系数。
    */
    public int simplify() {
        int gcd=1;
        int elementsArray[]=new int[row*column];
        for(int i=0;i<row;i++) {
            for(int j=0;j<column;j++) {
                elementsArray[j+i*row]=elements[i][j];
            }
        }
        gcd=Maths.gcd(elementsArray);
        if(gcd>1) {
            for(int i=0;i<row;i++) {
                for(int j=0;j<column;j++) {
                    elements[i][j]/=gcd;
                }
            }
        }
        coefficient*=gcd;
        return coefficient;
    }
    /**
    <p>矩阵系数提取</p><br>
    <p>此方法会修改调用对象。</p><br>
    提取矩阵的系数。
    @return 矩阵的系数。
    */
    public int extractCoefficient() {
        int result=coefficient;
        coefficient=1;
        return result;
    }
    /**
    <p>字符串表示</p><br>
    @return 矩阵的字符串表示。
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
        for(int i=0;i<row;i++) {
            result+="\t[";
            for(int j=0;j<column;j++) {
                result+=""+elements[i][j]+"\t";
            }
            result+="]\n";
        }
        return result;
    }
}