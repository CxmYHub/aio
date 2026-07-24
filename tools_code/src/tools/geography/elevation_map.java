package tools.geography;
import java.util.Random;
import tools.mathematics.histogram;
/**
<p>高程地图类。</p><br>
高程地图表示一个区域的高程数据。<br>
本高程地图以二维数组存储，每个元素表示该位置的高程。<br>
高程单位为米，0表示海平面。
*/
public class elevation_map implements java.io.Serializable
{
    public static final long serialVersionUID=1228991341984353796L;
    /**
    <p>共16个元素。</p><br>
    梯度向量表。<br>
    */
    public static final double gradient[][]={{1,0},{0.9238795325112867,0.38268343236508984},{0.7071067811865476,0.7071067811865476},{0.38268343236508984,0.9238795325112867},{0,1},{-0.38268343236508984,0.9238795325112867},{-0.7071067811865476,0.7071067811865476},{-0.9238795325112867,0.38268343236508984},{-1,0},{-0.9238795325112867,-0.38268343236508984},{-0.7071067811865476,-0.7071067811865476},{-0.38268343236508984,-0.9238795325112867},{0,-1},{0.38268343236508984,-0.9238795325112867},{0.7071067811865476,-0.7071067811865476},{0.9238795325112867,-0.38268343236508984}};
    public double elevation[][];
    public int length;
    public int width;
    public double max;
    public double min;
    public double average;
    public double median;
    /**
    构造一个指定长度和宽度的平坦高程地图对象。
    @param length 长度。
    @param width 宽度。
    */
    public elevation_map(int length,int width)
    {
        this.length=length;
        this.width=width;
        this.max=0;
        this.min=0;
        elevation=new double[width][length];
    }
    /**
    构造一个指定边长的正方形平坦高程地图对象。
    @param length 边长。
    */
    public elevation_map(int length)
    {
        this.length=length;
        this.width=length;
        this.max=0;
        this.min=0;
        elevation=new double[length][length];
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    计算当前高程地图的统计信息。<br>
    包括最大值、最小值、平均值、中位数。
    @return 值域是否发生变化。<br>
    若最大值或最小值发生变化，则返回<code>true</code>；否则返回<code>false</code>。
    */
    public boolean calculate_statistics()
    {
        int size=width*length;
        int half_size=size>>1;
        double max=Double.MIN_VALUE,min=Double.MAX_VALUE;
        double sum=0;
        double numbers[]=new double[width*length];
        int pin=0;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                double now=elevation[i][j];
                numbers[pin++]=now;
                sum+=now;
                max=now>max?now:max;
                min=now<min?now:min;
            }
        }
        int pivot_index=0;
        boolean is_odd=(size&1)==1;
        double median=0;
        int index_left=0;
        int index_right=size-1;
        while(index_left<=index_right)
        {
            int length=index_right-index_left+1;
            double pivot=numbers[index_right];
            if(length>=5)
            {
                int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
                double a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                if(a>b)
                {
                    double t=a;
                    a=b;
                    b=t;
                }
                if(c>d)
                {
                    double t=c;
                    c=d;
                    d=t;
                }
                if(a>c)
                {
                    double t=a;
                    a=c;
                    c=t;
                    t=b;
                    b=d;
                    d=t;
                }
                if(b>e)
                {
                    double t=b;
                    b=e;
                    e=t;
                }
                if(b>c)
                {
                    double t=b;
                    b=c;
                    c=t;
                }
                pivot=c<e?c:e;
                for(int median_index=0;median_index<5;median_index++)
                {
                    if(pivot==numbers[fifth[median_index]])
                    {
                        numbers[fifth[median_index]]=numbers[index_right];
                        numbers[index_right]=pivot;
                        break;
                    }
                }
            }
            int left=index_left-1,right=index_right;
            double temp;
            while(left<right)
            {
                for(left++;left<right&&numbers[left]<pivot;left++);
                for(right--;left<right&&numbers[right]>pivot;right--);
                if(left<right&&numbers[left]!=numbers[right])
                {
                    temp=numbers[left];
                    numbers[left]=numbers[right];
                    numbers[right]=temp;
                }
            }
            numbers[index_right]=numbers[left];
            numbers[left]=pivot;
            pivot_index=left;
            if(pivot_index==half_size)
            {
                if(is_odd)
                {
                    median+=pivot;
                    break;
                }
                else
                {
                    median+=pivot;
                    half_size++;
                    index_left=0;
                    index_right=size-1;
                    is_odd=true;
                    continue;
                }
            }
            else if(pivot_index<half_size)
            {
                index_left=pivot_index+1;
            }
            else
            {
                index_right=pivot_index-1;
            }
        }
        boolean different=true;
        if(this.max-max<0.00000001&&this.max-max>-0.00000001&&this.min-min<0.00000001&&this.min-min>-0.00000001)
        {
            different=false;
        }
        this.max=max;
        this.min=min;
        average=sum/(width*length);
        this.median=(size&1)==1?median:median/2;
        return different;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    计算当前高程地图的直方图。<br>
    直方图的区间数为100。
    @return 当前高程地图的直方图。
    */
    public histogram calculate_histogram()
    {
        histogram histogram=new histogram(100,min,max);
        for(int i=0;i<width;i++)
        {
            histogram.input_more(elevation[i]);
        }
        return histogram;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    提高当前高程地图的整体高程值。
    @param increase_height 高程增加量。
    @return 高程变化值。
    */
    public double elevate(double increase_height)
    {
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]+=increase_height;
            }
        }
        min+=increase_height;
        max+=increase_height;
        average+=increase_height;
        median+=increase_height;
        return increase_height;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    降低当前高程地图的整体高程值。
    @param decrease_height 高程减少量。
    @return 高程变化值。
    */
    public double sink(double decrease_height)
    {
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]-=decrease_height;
            }
        }
        min-=decrease_height;
        max-=decrease_height;
        average-=decrease_height;
        median-=decrease_height;
        return -decrease_height;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图的高程值规整至[<code>min</code>,<code>max</code>]区间。
    @param min 最小值。
    @param max 最大值。
    @return 高程变化系数。
    */
    public double normalize(double min,double max)
    {
        double vertical_coefficient=(max-min)/(this.max-this.min);
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=(elevation[i][j]-this.min)*vertical_coefficient+min;
            }
        }
        average=(average-this.min)*vertical_coefficient+min;
        median=(median-this.min)*vertical_coefficient+min;
        this.min=min;
        this.max=max;
        return vertical_coefficient;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图的高程值归一化至[0,1]区间。
    @return 高程变化系数。
    */
    public double normalize()
    {
        double max_difference=max-min;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=(elevation[i][j]-min)/max_difference;
            }
        }
        average-=min;
        median-=min;
        min=0;
        max=1;
        average/=max_difference;
        median/=max_difference;
        return 1/max_difference;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直线性缩放。<br>
    线性缩放将所有高程值乘缩放系数。<br>
    操作后地形将保持相对比例。
    @param vertical_coefficient 竖直缩放系数。
    @return 高程变化系数。
    */
    public double linear_scale(double vertical_coefficient)
    {
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]*=vertical_coefficient;
            }
        }
        min*=vertical_coefficient>=0?vertical_coefficient:-vertical_coefficient;
        max*=vertical_coefficient>=0?vertical_coefficient:-vertical_coefficient;
        average*=vertical_coefficient;
        median*=vertical_coefficient;
        return vertical_coefficient;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直指数缩放。<br>
    指数缩放将所有高程值取指数，并整体减去1，使0↦0。<br>
    操作后高地和山脉将变得更陡峭。
    @param vertical_exponent 竖直缩放指数。
    @return 高程平均值变化系数。
    */
    public double exponential_scale(double vertical_exponent)
    {
        double sum=0;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=(Math.pow(vertical_exponent,elevation[i][j])-1)/(vertical_exponent-1);
                sum+=elevation[i][j];
            }
        }
        min=(Math.pow(vertical_exponent,min)-1)/(vertical_exponent-1);
        max=(Math.pow(vertical_exponent,max)-1)/(vertical_exponent-1);
        median=(Math.pow(vertical_exponent,median)-1)/(vertical_exponent-1);
        double old_average=average;
        average=sum/(width*length);
        return average/old_average;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直指数规整。<br>
    指数规整将所有高程值取指数，并整体减去1，使最小值不变。<br>
    操作后高地和山脉将变得更陡峭。
    @param normalize_exponent 竖直规整指数。
    @return 高程平均值变化系数。
    */
    public double exponential_normalize(double normalize_exponent)
    {
        double original_min=min;
        double max_difference=max-min;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=(elevation[i][j]-original_min)/max_difference;
            }
        }
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=(Math.pow(normalize_exponent,elevation[i][j])-1)/(normalize_exponent-1);
            }
        }
        double sum=0;
        max=Double.MIN_VALUE;
        min=Double.MAX_VALUE;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                double now=elevation[i][j]*max_difference+original_min;
                elevation[i][j]=now;
                sum+=now;
                max=now>max?now:max;
                min=now<min?now:min;
            }
        }
        median=(Math.pow(normalize_exponent,(median-original_min)/max_difference)-1)/(normalize_exponent-1)*max_difference+original_min;
        double old_average=average;
        average=sum/(width*length);
        return average/old_average;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前高程地图进行竖直正割奇函数规整。<br>
    正割奇函数规整将所有高程值取正割函数，并整体减去一个修正值，使0↦0。<br>
    注意正割函数是偶函数，本操作将原函数小于0的部分取反，使其变为奇函数。<br>
    操作后平原和浅海将变得更辽阔，更平缓，高地和山脉将变得更陡峭；海沟和海盆将变得更深。
    @return 高程平均值变化系数。
    */
    public double secant_odd_normalize()
    {
        double original_min=min;
        double max_difference=(max-min)/2;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=(elevation[i][j]-original_min)/max_difference-1;
            }
        }
        double cos1_divide_1_substract_cos1=Math.cos(1)/(1-Math.cos(1));
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]=elevation[i][j]>0?(1/Math.cos(elevation[i][j])-1)*cos1_divide_1_substract_cos1:(1-1/Math.cos(elevation[i][j]))*cos1_divide_1_substract_cos1;
            }
        }
        double sum=0;
        max=Double.MIN_VALUE;
        min=Double.MAX_VALUE;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                double now=(elevation[i][j]+1)*max_difference+original_min;
                elevation[i][j]=now;
                sum+=now;
                max=now>max?now:max;
                min=now<min?now:min;
            }
        }
        double normalized_median=(median-original_min)/max_difference-1;
        median=normalized_median>0?((1/Math.cos(normalized_median)-1)*cos1_divide_1_substract_cos1+1)*max_difference+original_min:((1-1/Math.cos(normalized_median))*cos1_divide_1_substract_cos1+1)*max_difference+original_min;
        double old_average=average;
        average=sum/(width*length);
        return average/old_average;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。
    @param seed 种子。不同的种子会生成不同的地形。
    @param horizontal_scale 水平拉伸系数。值越大，地形越平缓。通常取30-100的值。
    @param octaves 细节等级。值越大，地形越丰富。通常取4-8的值。
    @param persistence 振幅增长系数。值越大，地形越崎岖。通常取<code>lacunarity</code>的倒数且小于1。
    @param lacunarity 频率增长系数。值越大，地形高度差越大。通常取<code>persistence</code>的倒数且大于1。
    @param vertical_scale 竖直拉伸系数。值越大，地形高差越大。通常取500-10000的值。
    @return 地形的最大高度差。
    */
    public double overlay_perlin_terrain(long seed,double horizontal_scale,int octaves,double persistence,double lacunarity,double vertical_scale)
    {
        Random random_generator=new Random(seed);
        int permutation[]=new int[65536];
        for(int i=0;i<permutation.length;i++)
        {
            permutation[i]=i;
        }
		for(int i=permutation.length-1;i>0;i--)
		{
			int random_index=random_generator.nextInt(i+1);
			int temp=permutation[i];
			permutation[i]=permutation[random_index];
			permutation[random_index]=temp;
		}
        double overlay_elevation[][]=new double[width][length];
        double overlay_max=Double.MIN_VALUE,overlay_min=Double.MAX_VALUE;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                double x=j/horizontal_scale;
                double y=i/horizontal_scale;
                double value=0;
                double amplitude=1;
                double frequency=1;
                double max_amplitude=0;
                for(int o=0;o<octaves;o++)
                {
                    double frequencied_x=x*frequency;
                    double frequencied_y=y*frequency;
                    int xi=(int)Math.floor(frequencied_x)&permutation.length-1;
                    int yi=(int)Math.floor(frequencied_y)&permutation.length-1;
                    double dx=frequencied_x-Math.floor(frequencied_x);
                    double dy=frequencied_y-Math.floor(frequencied_y);
                    int gradient_00=permutation[permutation[xi]+yi&permutation.length-1]%gradient.length;
                    int gradient_01=permutation[permutation[xi+1&permutation.length-1]+yi&permutation.length-1]%gradient.length;
                    int gradient_10=permutation[permutation[xi]+yi+1&permutation.length-1]%gradient.length;
                    int gradient_11=permutation[permutation[xi+1&permutation.length-1]+yi+1&permutation.length-1]%gradient.length;
                    double dot_00=gradient[gradient_00][0]*dx+gradient[gradient_00][1]*dy;
                    double dot_01=gradient[gradient_01][0]*(dx-1)+gradient[gradient_01][1]*dy;
                    double dot_10=gradient[gradient_10][0]*dx+gradient[gradient_10][1]*(dy-1);
                    double dot_11=gradient[gradient_11][0]*(dx-1)+gradient[gradient_11][1]*(dy-1);
                    double u=dx*dx*dx*(dx*(dx*6-15)+10);
                    double v=dy*dy*dy*(dy*(dy*6-15)+10);
                    double interpolated_x0=dot_00+(dot_01-dot_00)*u;
                    double interpolated_x1=dot_10+(dot_11-dot_10)*u;
                    value+=amplitude*(interpolated_x0+(interpolated_x1-interpolated_x0)*v);
                    max_amplitude+=amplitude;
                    amplitude*=persistence;
                    frequency*=lacunarity;
                }
                value=(value/max_amplitude+1)/2;
                overlay_max=value>overlay_max?value:overlay_max;
                overlay_min=value<overlay_min?value:overlay_min;
                overlay_elevation[i][j]=value;
            }
        }
        double vertical_coefficient=vertical_scale/(overlay_max-overlay_min);
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<length;j++)
            {
                elevation[i][j]+=(overlay_elevation[i][j]-overlay_min)*vertical_coefficient;
            }
        }
        calculate_statistics();
        return max-min;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。需要提供种子和高程系数。<br>
    该方法填入默认参数，调用<code>overlay_perlin_terrain(seed,horizontal_scale,octaves,persistence,lacunarity,vertical_scale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>horizontal_scale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
    </ul>
    @param seed 种子。不同的种子会生成不同的地形。
    @param vertical_scale 竖直拉伸系数。值越大，地形高差越大。通常取500-10000的值。
    @return 地形的最大高度差。
    */
    public double overlay_perlin_terrain(long seed,double vertical_scale)
    {
        return overlay_perlin_terrain(seed,300,6,0.5,2,vertical_scale);
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。需要提供高程系数。<br>
    该方法填入默认参数，调用<code>overlay_perlin_terrain(seed,horizontal_scale,octaves,persistence,lacunarity,vertical_scale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>seed=(long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1))</code>，即随机生成一个种子。</li>
        <li><code>horizontal_scale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
    </ul>
    @param vertical_scale 竖直拉伸系数。值越大，地形高差越大。通常取500-10000的值。
    @return 地形的最大高度差。
    */
    public double overlay_perlin_terrain(double vertical_scale)
    {
        return overlay_perlin_terrain((long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1)),300,6,0.5,2,vertical_scale);
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。需要提供种子。<br>
    该方法填入默认参数，调用<code>overlay_perlin_terrain(seed,horizontal_scale,octaves,persistence,lacunarity,vertical_scale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>horizontal_scale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
        <li><code>vertical_scale=1000</code>（竖直拉伸系数）</li>
    </ul>
    @param seed 种子。不同的种子会生成不同的地形。
    @return 地形的最大高度差。
    */
    public double overlay_perlin_terrain(long seed)
    {
        return overlay_perlin_terrain(seed,300,6,0.5,2,1000);
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    基于柏林噪声算法为当前高程地图叠加地形。无需提供参数。<br>
    该方法填入默认参数，调用<code>overlay_perlin_terrain(seed,horizontal_scale,octaves,persistence,lacunarity,vertical_scale)</code>方法。<br>
    默认参数为：
    <ul>
        <li><code>seed=(long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1))</code>，即随机生成一个种子。</li>
        <li><code>horizontal_scale=300</code>（水平拉伸系数）</li>
        <li><code>octaves=6</code>（细节等级）</li>
        <li><code>persistence=0.5</code>（振幅增长系数）</li>
        <li><code>lacunarity=2</code>（频率增长系数）</li>
        <li><code>vertical_scale=1000</code>（竖直拉伸系数）</li>
    </ul>
    @return 地形的最大高度差。
    */
    public double overlay_perlin_terrain()
    {
        return overlay_perlin_terrain((long)(((Math.random()*Long.MAX_VALUE)+1)*(Math.random()>=0.5?1:-1)),300,6,0.5,2,1000);
    }
    public String toString()
    {
        return "elevation_map{length="+length+",width="+width+",min="+min+",max="+max+",average="+average+",median="+median+"}";
    }
}