package aio.geography;
import java.util.Random;
import aio.mathematics.Histogram;
/**
<p>高程地图类</p><br>
高程地图表示一个区域的高程数据。<br>
本高程地图以二维数组存储，每个元素表示该位置的高程。<br>
高程单位为米，0表示海平面。
*/
public class ElevationMap implements java.io.Serializable {
    /**
    <p>序列化版本号</p>
    */
    public static final long SERIAL_VERSION_UID=1228991341984353796L;
    /**
    <p>梯度向量表</p><br>
    <p>共16个元素。</p>
    */
    public static final double GRADIENT[][]={{1,0},{0.9238795325112867,0.38268343236508984},{0.7071067811865476,0.7071067811865476},{0.38268343236508984,0.9238795325112867},{0,1},{-0.38268343236508984,0.9238795325112867},{-0.7071067811865476,0.7071067811865476},{-0.9238795325112867,0.38268343236508984},{-1,0},{-0.9238795325112867,-0.38268343236508984},{-0.7071067811865476,-0.7071067811865476},{-0.38268343236508984,-0.9238795325112867},{0,-1},{0.38268343236508984,-0.9238795325112867},{0.7071067811865476,-0.7071067811865476},{0.9238795325112867,-0.38268343236508984}};
    /**
    <p>高程值</p><br>
    栅格高程数据。<br>
    每个元素表示该位置的高程。
    */
    public double elevation[][];
    /**
    <p>长度</p><br>
    高程地图的x轴跨度。
    */
    public int length;
    /**
    <p>宽度</p><br>
    高程地图的y轴跨度。
    */
    public int width;
    /**
    <p>最大值</p><br>
    高程地图中所有点的最高值。
    */
    public double max;
    /**
    <p>最小值</p><br>
    高程地图中所有点的最低值。
    */
    public double min;
    /**
    <p>平均值</p><br>
    高程地图中所有点的平均值。
    */
    public double average;
    /**
    <p>中位数</p><br>
    高程地图中所有点的中位数。
    */
    public double median;
    /**
    <p>构造方法</p><br>
    构造一个指定长度和宽度的平坦高程地图对象。
    @param length 长度。
    @param width 宽度。
    */
    public ElevationMap(int length,int width) {
        this.length=length;
        this.width=width;
        max=0;
        min=0;
        elevation=new double[width][length];
    }
    /**
    <p>构造方法</p><br>
    构造一个指定边长的正方形平坦高程地图对象。
    @param length 边长。
    */
    public ElevationMap(int length) {
        this.length=length;
        width=length;
        max=0;
        min=0;
        elevation=new double[length][length];
    }
    /**
    <p>统计数据计算</p><br>
    <p>此方法会修改调用对象。</p><br>
    计算当前高程地图的统计数据。<br>
    包括最大值、最小值、平均值、中位数。
    @return 值域是否发生变化。<br>
    若最大值或最小值发生变化，则返回<code>true</code>；否则返回<code>false</code>。
    */
    public boolean calculateStatistics() {
        int size=width*length;
        int halfSize=size>>1;
        double max=Double.MIN_VALUE,min=Double.MAX_VALUE;
        double sum=0;
        double numbers[]=new double[size];
        int pin=0;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                double now=elevation[i][j];
                numbers[pin++]=now;
                sum+=now;
                max=now>max?now:max;
                min=now<min?now:min;
            }
        }
        int pivotIndex=0;
        boolean isOdd=(size&1)==1;
        double median=0;
        int indexLeft=0;
        int indexRight=size-1;
        while(indexLeft<=indexRight) {
            int length=indexRight-indexLeft+1;
            double pivot=numbers[indexRight];
            if(length>=5) {
                int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                double a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                if(a>b) {
                    double t=a;
                    a=b;
                    b=t;
                }
                if(c>d) {
                    double t=c;
                    c=d;
                    d=t;
                }
                if(a>c) {
                    double t=a;
                    a=c;
                    c=t;
                    t=b;
                    b=d;
                    d=t;
                }
                if(b>e) {
                    double t=b;
                    b=e;
                    e=t;
                }
                if(b>c) {
                    double t=b;
                    b=c;
                    c=t;
                }
                pivot=c<e?c:e;
                for(int medianIndex=0;medianIndex<5;medianIndex++) {
                    if(pivot==numbers[fifth[medianIndex]]) {
                        numbers[fifth[medianIndex]]=numbers[indexRight];
                        numbers[indexRight]=pivot;
                        break;
                    }
                }
            }
            int left=indexLeft-1,right=indexRight;
            double temp;
            while(left<right) {
                for(left++;left<right&&numbers[left]<pivot;left++);
                for(right--;left<right&&numbers[right]>pivot;right--);
                if(left<right&&numbers[left]!=numbers[right]) {
                    temp=numbers[left];
                    numbers[left]=numbers[right];
                    numbers[right]=temp;
                }
            }
            numbers[indexRight]=numbers[left];
            numbers[left]=pivot;
            pivotIndex=left;
            if(pivotIndex==halfSize) {
                if(isOdd) {
                    median+=pivot;
                    break;
                } else {
                    median+=pivot;
                    halfSize--;
                    indexLeft=0;
                    indexRight=size-1;
                    isOdd=true;
                    continue;
                }
            } else if(pivotIndex<halfSize) {
                indexLeft=pivotIndex+1;
            } else {
                indexRight=pivotIndex-1;
            }
        }
        boolean different=true;
        if(this.max-max<0.00000001&&this.max-max>-0.00000001&&this.min-min<0.00000001&&this.min-min>-0.00000001) {
            different=false;
        }
        this.max=max;
        this.min=min;
        average=sum/(width*length);
        this.median=(size&1)==1?median:median/2;
        return different;
    }
    /**
    <p>直方图计算</p><br>
    <p>此方法会修改调用对象。</p><br>
    计算当前高程地图的直方图。<br>
    直方图的区间数为100。
    @return 当前高程地图的直方图。
    */
    public Histogram calculateHistogram() {
        Histogram histogram=new Histogram(100,min,max);
        for(int i=0;i<width;i++) {
            histogram.inputMore(elevation[i]);
        }
        return histogram;
    }
    /**
    <p>高程提升</p><br>
    <p>此方法会修改调用对象。</p><br>
    提高当前高程地图的整体高程值。
    @param increaseHeight 高程增加量。
    @return 高程变化值。
    */
    public double elevate(double increaseHeight) {
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]+=increaseHeight;
            }
        }
        min+=increaseHeight;
        max+=increaseHeight;
        average+=increaseHeight;
        median+=increaseHeight;
        return increaseHeight;
    }
    /**
    <p>高程下降</p><br>
    <p>此方法会修改调用对象。</p><br>
    降低当前高程地图的整体高程值。
    @param decreaseHeight 高程减少量。
    @return 高程变化值。
    */
    public double sink(double decreaseHeight) {
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]-=decreaseHeight;
            }
        }
        min-=decreaseHeight;
        max-=decreaseHeight;
        average-=decreaseHeight;
        median-=decreaseHeight;
        return -decreaseHeight;
    }
    /**
    <p>高程规整</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图的高程值规整至[<code>min</code>,<code>max</code>]区间。
    @param min 最小值。
    @param max 最大值。
    @return 高程变化系数。
    */
    public double normalize(double min,double max) {
        double verticalCoefficient=(max-min)/(this.max-this.min);
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=(elevation[i][j]-this.min)*verticalCoefficient+min;
            }
        }
        average=(average-this.min)*verticalCoefficient+min;
        median=(median-this.min)*verticalCoefficient+min;
        this.min=min;
        this.max=max;
        return verticalCoefficient;
    }
    /**
    <p>高程归一化</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图的高程值归一化至[0,1]区间。
    @return 高程变化系数。
    */
    public double normalize() {
        double maxDifference=max-min;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=(elevation[i][j]-min)/maxDifference;
            }
        }
        average-=min;
        median-=min;
        min=0;
        max=1;
        average/=maxDifference;
        median/=maxDifference;
        return 1/maxDifference;
    }
    /**
    <p>竖直线性缩放</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直线性缩放。<br>
    线性缩放将所有高程值乘缩放系数。<br>
    操作后地形将保持相对比例。
    @param verticalCoefficient 竖直缩放系数。
    @return 高程变化系数。
    */
    public double linearScale(double verticalCoefficient) {
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]*=verticalCoefficient;
            }
        }
        min*=verticalCoefficient>=0?verticalCoefficient:-verticalCoefficient;
        max*=verticalCoefficient>=0?verticalCoefficient:-verticalCoefficient;
        average*=verticalCoefficient;
        median*=verticalCoefficient;
        return verticalCoefficient;
    }
    /**
    <p>竖直指数缩放</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直指数缩放。<br>
    指数缩放将所有高程值取指数，并整体减去1，使0↦0。<br>
    操作后高地和山脉将变得更高、更陡峭。
    @param verticalBase 竖直缩放底数。
    @return 高程平均值变化系数。
    */
    public double exponentialScale(double verticalBase) {
        double sum=0;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=(Math.pow(verticalBase,elevation[i][j])-1)/(verticalBase-1);
                sum+=elevation[i][j];
            }
        }
        min=(Math.pow(verticalBase,min)-1)/(verticalBase-1);
        max=(Math.pow(verticalBase,max)-1)/(verticalBase-1);
        median=(Math.pow(verticalBase,median)-1)/(verticalBase-1);
        double oldAverage=average;
        average=sum/(width*length);
        return average/oldAverage;
    }
    /**
    <p>竖直指数规整</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直指数规整。<br>
    指数规整将所有高程值取指数，并整体减去1，使0↦0。<br>
    操作后海拔最大值和最小值不变，但高地和山脉将变得更陡峭。
    @param normalizeBase 竖直规整底数。
    @return 高程平均值变化系数。
    */
    public double exponentialNormalize(double normalizeBase) {
        double originalMin=min;
        double maxDifference=max-min;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=(elevation[i][j]-originalMin)/maxDifference;
            }
        }
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=(Math.pow(normalizeBase,elevation[i][j])-1)/(normalizeBase-1);
            }
        }
        double sum=0;
        max=Double.MIN_VALUE;
        min=Double.MAX_VALUE;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                double now=elevation[i][j]*maxDifference+originalMin;
                elevation[i][j]=now;
                sum+=now;
                max=now>max?now:max;
                min=now<min?now:min;
            }
        }
        median=(Math.pow(normalizeBase,(median-originalMin)/maxDifference)-1)/(normalizeBase-1)*maxDifference+originalMin;
        double oldAverage=average;
        average=sum/(width*length);
        return average/oldAverage;
    }
    /**
    <p>竖直正割奇函数规整</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直正割奇函数规整。<br>
    正割奇函数规整将所有高程值取正割函数，并整体减去一个修正值，使0↦0。<br>
    注意正割函数是偶函数，本操作将原函数小于0的部分取反，使其变为奇函数。<br>
    操作后平原和浅海将变得更辽阔，更平缓，高地和山脉将变得更陡峭；海沟和海盆将变得更深。
    @return 高程平均值变化系数。
    */
    public double secantOddNormalize() {
        double originalMin=min;
        double maxDifference=(max-min)/2;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=(elevation[i][j]-originalMin)/maxDifference-1;
            }
        }
        double cos1Divide1SubstractCos1=Math.cos(1)/(1-Math.cos(1));
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]=elevation[i][j]>0?(1/Math.cos(elevation[i][j])-1)*cos1Divide1SubstractCos1:(1-1/Math.cos(elevation[i][j]))*cos1Divide1SubstractCos1;
            }
        }
        double sum=0;
        max=Double.MIN_VALUE;
        min=Double.MAX_VALUE;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                double now=(elevation[i][j]+1)*maxDifference+originalMin;
                elevation[i][j]=now;
                sum+=now;
                max=now>max?now:max;
                min=now<min?now:min;
            }
        }
        double normalizedMedian=(median-originalMin)/maxDifference-1;
        median=normalizedMedian>0?((1/Math.cos(normalizedMedian)-1)*cos1Divide1SubstractCos1+1)*maxDifference+originalMin:((1-1/Math.cos(normalizedMedian))*cos1Divide1SubstractCos1+1)*maxDifference+originalMin;
        double oldAverage=average;
        average=sum/(width*length);
        return average/oldAverage;
    }
    /**
    <p>叠加柏林噪声地形</p><br>
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。
    @param seed 种子。不同的种子会生成不同的地形。
    @param horizontalScale 水平拉伸系数。值越大，地形越平缓。通常取30-100的值。
    @param octaves 细节等级。值越大，地形越丰富。通常取4-8的值。
    @param persistence 振幅增长系数。值越大，地形越崎岖。通常取<code>lacunarity</code>的倒数且小于1。
    @param lacunarity 频率增长系数。值越大，地形高度差越大。通常取<code>persistence</code>的倒数且大于1。
    @param verticalScale 竖直拉伸系数。值越大，地形高差越大。通常取500-10000的值。
    @return 地形的最大高度差。
    */
    public double overlayPerlinTerrain(long seed,double horizontalScale,int octaves,double persistence,double lacunarity,double verticalScale) {
        Random randomGenerator=new Random(seed);
        int permutation[]=new int[65536];
        for(int i=0;i<permutation.length;i++) {
            permutation[i]=i;
        }
        for(int i=permutation.length-1;i>0;i--) {
            int randomIndex=randomGenerator.nextInt(i+1);
            int temp=permutation[i];
            permutation[i]=permutation[randomIndex];
            permutation[randomIndex]=temp;
        }
        double overlayElevation[][]=new double[width][length];
        double overlayMax=Double.MIN_VALUE,overlayMin=Double.MAX_VALUE;
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                double x=j/horizontalScale;
                double y=i/horizontalScale;
                double value=0;
                double amplitude=1;
                double frequency=1;
                double maxAmplitude=0;
                for(int o=0;o<octaves;o++) {
                    double frequenciedX=x*frequency;
                    double frequenciedY=y*frequency;
                    int xi=(int)Math.floor(frequenciedX)&permutation.length-1;
                    int yi=(int)Math.floor(frequenciedY)&permutation.length-1;
                    double dx=frequenciedX-Math.floor(frequenciedX);
                    double dy=frequenciedY-Math.floor(frequenciedY);
                    int gradient00=permutation[permutation[xi]+yi&permutation.length-1]%GRADIENT.length;
                    int gradient01=permutation[permutation[xi+1&permutation.length-1]+yi&permutation.length-1]%GRADIENT.length;
                    int gradient10=permutation[permutation[xi]+yi+1&permutation.length-1]%GRADIENT.length;
                    int gradient11=permutation[permutation[xi+1&permutation.length-1]+yi+1&permutation.length-1]%GRADIENT.length;
                    double dot00=GRADIENT[gradient00][0]*dx+GRADIENT[gradient00][1]*dy;
                    double dot01=GRADIENT[gradient01][0]*(dx-1)+GRADIENT[gradient01][1]*dy;
                    double dot10=GRADIENT[gradient10][0]*dx+GRADIENT[gradient10][1]*(dy-1);
                    double dot11=GRADIENT[gradient11][0]*(dx-1)+GRADIENT[gradient11][1]*(dy-1);
                    double u=dx*dx*dx*(dx*(dx*6-15)+10);
                    double v=dy*dy*dy*(dy*(dy*6-15)+10);
                    double interpolatedX0=dot00+(dot01-dot00)*u;
                    double interpolatedX1=dot10+(dot11-dot10)*u;
                    value+=amplitude*(interpolatedX0+(interpolatedX1-interpolatedX0)*v);
                    maxAmplitude+=amplitude;
                    amplitude*=persistence;
                    frequency*=lacunarity;
                }
                value=(value/maxAmplitude+1)/2;
                overlayMax=value>overlayMax?value:overlayMax;
                overlayMin=value<overlayMin?value:overlayMin;
                overlayElevation[i][j]=value;
            }
        }
        double verticalCoefficient=verticalScale/(overlayMax-overlayMin);
        for(int i=0;i<width;i++) {
            for(int j=0;j<length;j++) {
                elevation[i][j]+=(overlayElevation[i][j]-overlayMin)*verticalCoefficient;
            }
        }
        calculateStatistics();
        return max-min;
    }
    /**
    <p>叠加柏林噪声地形</p><br>
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。需要提供种子和高程系数。<br>
    该方法填入默认参数，调用<code>overlayPerlinTerrain(seed,horizontalScale,octaves,persistence,lacunarity,verticalScale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>horizontalScale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
    </ul>
    @param seed 种子。不同的种子会生成不同的地形。
    @param verticalScale 竖直拉伸系数。值越大，地形高差越大。通常取500-10000的值。
    @return 地形的最大高度差。
    */
    public double overlayPerlinTerrain(long seed,double verticalScale) {
        return overlayPerlinTerrain(seed,300,6,0.5,2,verticalScale);
    }
    /**
    <p>叠加柏林噪声地形</p><br>
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。需要提供高程系数。<br>
    该方法填入默认参数，调用<code>overlayPerlinTerrain(seed,horizontalScale,octaves,persistence,lacunarity,verticalScale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>seed=(long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1))</code>，即随机生成一个种子。</li>
        <li><code>horizontalScale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
    </ul>
    @param verticalScale 竖直拉伸系数。值越大，地形高差越大。通常取500-10000的值。
    @return 地形的最大高度差。
    */
    public double overlayPerlinTerrain(double verticalScale) {
        return overlayPerlinTerrain((long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1)),300,6,0.5,2,verticalScale);
    }
    /**
    <p>叠加柏林噪声地形</p><br>
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。需要提供种子。<br>
    该方法填入默认参数，调用<code>overlayPerlinTerrain(seed,horizontalScale,octaves,persistence,lacunarity,verticalScale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>horizontalScale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
        <li><code>verticalScale=1000</code>（竖直拉伸系数）</li>
    </ul>
    @param seed 种子。不同的种子会生成不同的地形。
    @return 地形的最大高度差。
    */
    public double overlayPerlinTerrain(long seed) {
        return overlayPerlinTerrain(seed,300,6,0.5,2,1000);
    }
    /**
    <p>叠加柏林噪声地形</p><br>
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。无需提供参数。<br>
    该方法填入默认参数，调用<code>overlayPerlinTerrain(seed,horizontalScale,octaves,persistence,lacunarity,verticalScale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>seed=(long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1))</code>，即随机生成一个种子。</li>
        <li><code>horizontalScale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
        <li><code>verticalScale=1000</code>（竖直拉伸系数）</li>
    </ul>
    @return 地形的最大高度差。
    */
    public double overlayPerlinTerrain() {
        return overlayPerlinTerrain((long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1)),300,6,0.5,2,1000);
    }
    /**
    <p>字符串表示</p><br>
    @return 高程地图的元数据字符串表示。
    */
    public String toString() {
        return "elevationMap{length="+length+",width="+width+",min="+min+",max="+max+",average="+average+",median="+median+"}";
    }
}