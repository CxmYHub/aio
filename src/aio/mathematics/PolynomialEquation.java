package aio.mathematics;
/**
<p>自然数次多项式方程类</p><br>
自然数次多项式方程是指未知数只有一个，且每一项次数都是非负整数的多项式方程。<br>
本类可以解析形如"aNx^n+a_(n-1)x^(n-1)+...+a1x+a0=0"的多项式方程。<br>
其中n是未知数的指数，为自然数。<br>
本类可以计算自然数次多项式方程的根，即满足方程的所有实数解。
*/
public class PolynomialEquation {
    /**
    <p>未知数的最高次数</p><br>
    多项式方程中未知数的指数，为自然数。
    */
    public int times=0;
    /**
    <p>未知数字母</p><br>
    多项式方程中未知数的标签，例如<code>'x'</code>。
    */
    public char unknown;
    /**
    <p>系数数组</p><br>
    多项式方程中系数的数组，为整数数组。
    */
    public int coefficients[];
    /**
    <p>构造方法</p><br>
    通过字符串解析自然数次多项式方程。
    @param unknownTag 未知数字母，例如<code>'x'</code>。
    @param equationString 多项式方程的字符串表示，例如<code>2x^2+3x+1=0</code>。
    */
    public PolynomialEquation(char unknownTag,String equationString) {
        unknown=unknownTag;
        int tag=0;
        boolean hasIndex=false;
        boolean hasUnknown=false;
        int thisIndex=0;
        for(int i=0;i<equationString.length();i++) {
            char ch=equationString.charAt(i);
            if(ch==unknownTag) {
                hasUnknown=true;
            } else if(hasUnknown&&ch=='^') {
                hasIndex=true;
            }
            if(hasIndex) {
                if(tag==0&&ch=='^') {
                    tag=1;
                } else if(tag==1&&(ch>='0'&&ch<='9')) {
                    thisIndex*=10;
                    thisIndex+=ch-'0';
                } else if(tag==1) {
                    if(times<thisIndex) {
                        times=thisIndex;
                    }
                    thisIndex=0;
                    tag=0;
                }
            } else if(hasUnknown) {
                times=1;
            }
        }
        double doubleCoefficients[]=new double[times+1];
        char currentCoefficient[]=new char[equationString.length()];
        int noPassedEqual=1;
        int dotIndex=-1;
        int maxDecimal=0;
        tag=0;
        thisIndex=0;
        for(int i=0;i<equationString.length();i++) {
            char ch=equationString.charAt(i);
            if(ch>='0'&&ch<='9'||ch=='.') {
                if(tag>=0) {
                    currentCoefficient[tag++]=ch;
                } else if(tag==-3) {
                    thisIndex*=10;
                    thisIndex+=ch-'0';
                }
                if(ch=='.') {
                    dotIndex=i;
                }
            } else if(ch=='+'||ch=='-') {
                if(tag==-2) {
                    thisIndex=1;
                }
                if(i>0) {
                    try {
                        doubleCoefficients[thisIndex]+=noPassedEqual*(Double.parseDouble(new String(currentCoefficient)));
                    } catch (NumberFormatException ex) {
                        if(currentCoefficient[0]=='-') {
                            doubleCoefficients[thisIndex]+=-1*noPassedEqual;
                        } else {
                            doubleCoefficients[thisIndex]+=noPassedEqual;
                        }
                    }
                }
                if(dotIndex!=-1&&i-dotIndex-1>maxDecimal) {
                    maxDecimal=i-dotIndex-1;
                    dotIndex=-1;
                }
                currentCoefficient[0]=ch;
                for(int j=1;j<currentCoefficient.length;j++) {
                    currentCoefficient[j]=0;
                }
                thisIndex=0;
                tag=1;
            } else if(ch=='=') {
                if(tag==-2) {
                    thisIndex=1;
                }
                if(i>0) {
                    doubleCoefficients[thisIndex]+=Double.parseDouble(new String(currentCoefficient));
                }
                if(dotIndex!=-1&&i-dotIndex-1>maxDecimal) {
                    maxDecimal=i-dotIndex-1;
                    dotIndex=-1;
                }
                for(int j=0;j<currentCoefficient.length;j++) {
                    currentCoefficient[j]=0;
                }
                noPassedEqual=-1;
                thisIndex=0;
                tag=0;
            } else if(ch==unknownTag) {
                tag=-2;
                if(dotIndex!=-1&&i-dotIndex-1>maxDecimal) {
                    maxDecimal=i-dotIndex-1;
                    dotIndex=-1;
                }
            } else if(ch=='^') {
                tag=-3;
            }
        }
        if(dotIndex!=-1&&equationString.length()-dotIndex-1>maxDecimal) {
            maxDecimal=equationString.length()-dotIndex-1;
        }
        if(tag==-2) {
            thisIndex=1;
        }
        try {
            doubleCoefficients[thisIndex]+=noPassedEqual*(Double.parseDouble(new String(currentCoefficient)));
        } catch (NumberFormatException ex) {
            if(currentCoefficient[0]=='-') {
                doubleCoefficients[thisIndex]+=-1*noPassedEqual;
            } else {
                doubleCoefficients[thisIndex]+=noPassedEqual;
            }
        }
        for(int i=doubleCoefficients.length-1;i>=0;i--) {
            if((doubleCoefficients[i]>0.000001||doubleCoefficients[i]<-0.000001)) {
                times=i;
                break;
            }
        }
        if(maxDecimal>0) {
            for(int i=times;i>=0;i--) {
                doubleCoefficients[i]*=Math.pow(10,maxDecimal);
            }
        }
        coefficients=new int[times+1];
        for(int i=0;i<=times;i++) {
            coefficients[i]=(int)doubleCoefficients[i];
        }
        simplify();
    }
    /**
    <p>方程化简</p><br>
    <p>此方法会修改调用对象。</p><br>
    化简多项式方程。
    @return 化简系数的最大公因数。
    */
    public int simplify() {
        int positiveCoefficient[]=new int[times+1];
        System.arraycopy(coefficients,0,positiveCoefficient,0,times+1);
        for(int i=0;i<positiveCoefficient.length;i++) {
            if(positiveCoefficient[i]<0) {
                positiveCoefficient[i]*=-1;
            }
        }
        int gcdValue=Maths.gcd(positiveCoefficient);
        if(gcdValue>1) {
            for(int i=0;i<coefficients.length;i++) {
                coefficients[i]/=gcdValue;
            }
        }
        return gcdValue;
    }
    /**
    <p>方程求解</p><br>
    求解多项式方程。<br>
    除返回多项式方程的根数组外，还会通过标准输出打印原方程的解集。
    @return 多项式方程的根数组，如果方程无解则返回<code>null</code>。
    */
    public double[] solve() {
        switch(times) {
            case 0: {
                System.out.println("0次方程无法求解。");
                return null;
            }
            case 1: {
                double k=coefficients[1];
                double b=coefficients[0];
                System.out.println("x="+(b/k));
                return new double[]{b/k};
            }
            case 2: {
                double a=coefficients[2];
                double b=coefficients[1];
                double c=coefficients[0];
                if(a<0) {
                    a*=-1;
                    b*=-1;
                    c*=-1;
                }
                double x[]=new double[2];
                double delta=b*b-4*a*c;
                double a2=a*2;
                SquareRoot sqrtDelta=null;
                int division=1;
                if(delta>0) {
                    sqrtDelta=new SquareRoot((int)delta);
                }
                System.out.println("Δ="+delta);
                if(delta>=0) {
                    double x1PlusX2=-b/a;
                    double x1MutiplyX2=c/a;
                    double x1x1PlusX2x2=b*b/a/a-2*c/a;
                    if(delta>0) {
                        x[0]=((-b+Math.sqrt(delta))/a2);
                        x[1]=((-b-Math.sqrt(delta))/a2);
                        division=Maths.gcd((int)Math.abs(a2),(int)Math.abs(b),Math.abs(sqrtDelta.coefficient));
                        a2/=division;
                        b/=division;
                        sqrtDelta.coefficient/=division;
                        if(a2==1) {
                            System.out.println("此方程有两个实数解。\nx1 = "+(-b)+"+"+sqrtDelta+"\nx2 = "+(-b)+"-"+sqrtDelta);
                        } else {
                            System.out.println("此方程有两个实数解。\nx1 = ("+(-b)+"+"+sqrtDelta+")/"+a2+"\nx2 = ("+(-b)+"-"+sqrtDelta+")/"+a2);
                        }
                        System.out.println("或\nx1 = "+x[0]+"\nx2 = "+x[1]);
                        System.out.println("此外，\nx1+x2 = "+x1PlusX2+"\n x1x2 = "+x1MutiplyX2+"\nx1^2+x2^2 = "+x1x1PlusX2x2);
                        return new double[]{x[0],x[1]};
                    } else {
                        x[0]=(-b/a2);
                        division=Maths.gcd((int)Math.abs(a2),(int)Math.abs(b));
                        a2/=division;
                        b/=division;
                        if(a2==1) {
                            System.out.println("此方程有一个实数解。\nx1=x2 = "+(-b));
                        } else {
                            System.out.println("此方程有一个实数解。\nx1=x2 = "+(-b)+"/"+a2);
                        }
                        System.out.println("或\nx1=x2 = "+x[0]);
                        System.out.println("此外，\nx1+x2 = "+x1PlusX2+"\n x1x2 = "+x1MutiplyX2+"\nx1^2+x2^2 = "+x1x1PlusX2x2);
                        return new double[]{x[0],x[0]};
                    }
                } else {
                    System.out.println("此方程无实数解。");
                    return new double[]{0,0};
                }
            }
            case 3: {
                // double a=coefficients[3];
                // double b=coefficients[2];
                // double c=coefficients[1];
                // double d=coefficients[0];
                return new double[]{0,0,0};
            }
            case 4: {
                // double a=coefficients[4];
                // double b=coefficients[3];
                // double c=coefficients[2];
                // double d=coefficients[1];
                // double e=coefficients[0];
                return new double[]{0,0,0,0};
            }
            default: {
                System.out.println("5次及以上方程无法求解。");
                return null;
            }
        }
    }
    /**
    <p>单项式获取</p><br>
    获取多项式方程的一个单项式。
    @param index 单项式的次数。
    @return 多项式方程的单项式字符串。
    */
    public String getMonomial(int index) {
        if(coefficients[index]==0) {
            return "0";
        } else if(index==0) {
            return ""+coefficients[0];
        } else if(index==1) {
            return ""+coefficients[1]+unknown;
        } else {
            return ""+coefficients[index]+unknown+"^"+index;
        }
    }
    /**
    <p>正号单项式获取</p><br>
    获取带正号的多项式方程的一个单项式。
    @param index 单项式的次数。
    @return 带正号的多项式方程的单项式字符串。
    */
    public String getMonomialWithPositiveSymbol(int index) {
        if(coefficients[index]>0) {
            return "+"+getMonomial(index);
        } else {
            return getMonomial(index);
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 多项式方程的字符串表示。
    */
    public String toString() {
        String result=""+getMonomial(times);
        for(int i=times-1;i>=0;i--) {
            result+=getMonomialWithPositiveSymbol(i);
        }
        result+="=0";
        return result;
    }
}