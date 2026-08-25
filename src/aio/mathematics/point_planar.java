package aio.mathematics;
/**
<p>平面点类</p><br>
平面点表示平面中的一个位置。<br>
包含直角坐标表示和极坐标表示。<br>
横坐标、纵坐标取值范围为任意实数。<br>
极径取值范围为非负实数，极角取值范围为[0,2π)。
*/
public class point_planar
{
    /**
    <p>横坐标</p>
    */
    public double x;
    /**
    <p>纵坐标</p>
    */
    public double y;
    /**
    <p>极径</p>
    */
    public double rho;
    /**
    <p>极角</p>
    */
    public double theta;
    /**
    <p>计算极坐标</p><br>
    通过当前平面点的直角坐标计算其极坐标表示。
    */
    public void calculate_polar_coordinate()
    {
        rho=Math.sqrt(x*x+y*y);
        if(x>=0&&y==0)
        {
            theta=0;
        }
        else if(x<0)
        {
            theta=Math.PI+Math.atan(y/x);
        }
        else if(y>=0)
        {
            theta=Math.atan(y/x);
        }
        else
        {
            theta=6.283185307179586+Math.atan(y/x);
        }
    }
    /**
    <p>直角坐标转极坐标</p><br>
    将一组直角坐标转换为极坐标。
    @param x 横坐标。
    @param y 纵坐标。
    @return 极坐标数组。<br>
    <code>{极径,极角}</code>
    */
    public static double[] cartesian_to_polar(double x,double y)
    {
        double[] polar=new double[2];
        polar[0]=Math.sqrt(x*x+y*y);
        if(x>=0&&y==0)
        {
            polar[1]=0;
        }
        else if(x<0)
        {
            polar[1]=Math.PI+Math.atan(y/x);
        }
        else if(y>=0)
        {
            polar[1]=Math.atan(y/x);
        }
        else
        {
            polar[1]=6.283185307179586+Math.atan(y/x);
        }
        return polar;
    }
    /**
    <p>计算直角坐标</p><br>
    通过当前平面点的极坐标计算其直角坐标表示。
    */
    public void calculate_cartesian_coordinate()
    {
        x=rho*Math.cos(theta);
        y=rho*Math.sin(theta);
    }
    /**
    <p>极坐标转直角坐标</p><br>
    将一组极坐标转换为直角坐标。
    @param rho 极径。
    @param theta 极角。
    @return 直角坐标数组。<br>
    <code>{横坐标,纵坐标}</code>
    */
    public static double[] polar_to_cartesian(double rho,double theta)
    {
        return new double[]{rho*Math.cos(theta),rho*Math.sin(theta)};
    }
    /**
    <p>构造方法</p><br>
    构造一个平面点对象。
    @param rho_x 极径或横坐标。
    @param theta_y 极角或纵坐标。
    @param true_polar_false_cartesian 是否使用极坐标构造平面点对象。<br>
    如需使用极坐标构造平面点对象，请输入<code>true</code>。<br>
    如需使用直角坐标构造平面点对象，请输入<code>false</code>。
    */
    public point_planar(double rho_x,double theta_y,boolean true_polar_false_cartesian)
    {
        if(true_polar_false_cartesian)
        {
            rho=rho_x;
            theta=theta_y;
            calculate_cartesian_coordinate();
        }
        else
        {
            x=rho_x;
            y=theta_y;
            calculate_polar_coordinate();
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认的平面点对象，表示原点。
    */
    public point_planar()
    {
        x=0;
        y=0;
        rho=0;
        theta=0;
    }
    /**
    <p>点象限计算</p><br>
    计算当前平面点所在的象限。
    @return 当前平面点所在的象限。
    */
    public int quadrant()
    {
        return y>0?x>0?1:2:x<0?3:4;
    }
    /**
    <p>点象限计算</p><br>
    计算指定平面点所在的象限。
    @param x 横坐标。
    @param y 纵坐标。
    @return 平面点(<code>x</code>,<code>y</code>)所在的象限。
    */
    public static int quadrant(double x,double y)
    {
        return y>0?x>0?1:2:x<0?3:4;
    }
    /**
    <p>坐标相加</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前平面点与另一个平面点相加。
    @param coordinate_cartesian 要相加的平面点对象。
    */
    public void add(point_planar coordinate_cartesian)
    {
        x+=coordinate_cartesian.x;
        y+=coordinate_cartesian.y;
        calculate_polar_coordinate();
    }
    /**
    <p>坐标相加</p><br>
    计算两个平面点的和 <code>coordinate1</code>+<code>coordinate2</code>。
    @param coordinate1 第一个平面点对象。
    @param coordinate2 第二个平面点对象。
    @return 两个平面点的和。
    */
    public static point_planar add(point_planar coordinate1,point_planar coordinate2)
    {
        return new point_planar(coordinate1.x+coordinate2.x,coordinate1.y+coordinate2.y,false);
    }
    /**
    <p>坐标相减</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前平面点与另一个平面点相减。
    @param coordinate_cartesian 要相减的平面点对象。
    */
    public void subtract(point_planar coordinate_cartesian)
    {
        x-=coordinate_cartesian.x;
        y-=coordinate_cartesian.y;
        calculate_polar_coordinate();
    }
    /**
    <p>坐标相减</p><br>
    计算两个平面点的差 <code>coordinate1</code>-<code>coordinate2</code>。
    @param coordinate1 第一个平面点对象。
    @param coordinate2 第二个平面点对象。
    @return 两个平面点的差。
    */
    public static point_planar subtract(point_planar coordinate1,point_planar coordinate2)
    {
        return new point_planar(coordinate1.x-coordinate2.x,coordinate1.y-coordinate2.y,false);
    }
    /**
    <p>坐标数乘</p><br>
    <p>此方法会修改调用对象。</p><br>
    计算当前平面点与一个系数的乘积。
    @param coefficient 系数。
    */
    public void multiply_scalar(double coefficient)
    {
        x*=coefficient;
        y*=coefficient;
        rho*=coefficient;
    }
    /**
    <p>坐标数乘</p><br>
    计算一个平面点与一个系数的乘积 <code>coordinate_cartesian</code>*<code>coefficient</code>。
    @param coordinate_cartesian 要数乘的平面点对象。
    @param coefficient 系数。
    @return 一个平面点与一个系数的乘积。
    */
    public static point_planar multiply_scalar(point_planar coordinate_cartesian,double coefficient)
    {
        return new point_planar(coordinate_cartesian.x*coefficient,coordinate_cartesian.y*coefficient,false);
    }
    /**
    <p>两点距离</p><br>
    计算当前平面点与另一个平面点的距离。
    @param coordinate_cartesian 要计算距离的平面点对象。
    @return 当前平面点与另一个平面点的距离。
    */
    public double distance(point_planar coordinate_cartesian)
    {
        return Math.sqrt((x-coordinate_cartesian.x)*(x-coordinate_cartesian.x)+(y-coordinate_cartesian.y)*(y-coordinate_cartesian.y));
    }
    /**
    <p>两点距离</p><br>
    计算两点间的距离。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 两点间距离。
    */
    public double distance(double x0,double y0,double xt,double yt)
    {
        return Math.sqrt((xt-x0)*(xt-x0)+(yt-y0)*(yt-y0));
    }
    /**
    <p>相对角度</p><br>
    计算该点到终点的角度。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 该点到终点的角度。
    */
    public double angle(double xt,double yt)
    {
        double dx=xt-x;
        double dy=yt-y;
        if(dx>=0&&dy==0)
        {
            return 0;
        }
        else if(dx<0)
        {
            return 180+Math.atan((double)dy/dx)*180/Math.PI;
        }
        else if(dy>=0)
        {
            return Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 360+Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    <p>相对角度</p><br>
    计算该点到终点的角度。
    @param target 终点的平面点对象。
    @return 该点到终点的角度。
    */
    public double angle(point_planar target)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        if(dx>=0&&dy==0)
        {
            return 0;
        }
        else if(dx<0)
        {
            return 180+Math.atan((double)dy/dx)*180/Math.PI;
        }
        else if(dy>=0)
        {
            return Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 360+Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    <p>相对角度</p><br>
    计算起点到终点的角度。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 起点到终点的角度。
    */
    public static double angle(double x0,double y0,double xt,double yt)
    {
        double dx=xt-x0;
        double dy=yt-y0;
        if(dx>=0&&dy==0)
        {
            return 0;
        }
        else if(dx<0)
        {
            return 180+Math.atan((double)dy/dx)*180/Math.PI;
        }
        else if(dy>=0)
        {
            return Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 360+Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    <p>中点</p><br>
    计算该平面点与指定平面点连线的中点。
    @param target 目标平面点对象。
    @return 该平面点与指定平面点连线的中点。
    */
    public point_planar middle_point(point_planar target)
    {
        return new point_planar((target.x+x)/2,(target.y+y)/2,false);
    }
    /**
    <p>线性插值</p><br>
    计算该平面点到指定平面点的线性插值。
    @param target 目标平面点对象。
    @param ratio 线性插值比例。<br>
    <code>ratio</code>∈[0,1]，0表示起点，1表示目标点。
    @return 该平面点到指定平面点的线性插值。
    */
    public point_planar linear_interpolation(point_planar target,double ratio)
    {
        return new point_planar(x+ratio*(target.x-x),y+ratio*(target.y-y),false);
    }
    /**
    <p>向目标点位移距离</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前平面点向指定平面点位移指定距离。<br>
    注意，坐标经计算后可能存在双精度浮点数精度误差，导致位移结果不准确。
    @param target 目标平面点对象。
    @param move_distance 距离。
    @return 位移的距离占总距离的比例。<br>
    0表示起点，1表示目标点。
    */
    public double move_distance_towards(point_planar target,double move_distance)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        double total_distance=Math.sqrt(dx*dx+dy*dy);
        if(total_distance==0.0)
        {
            return 0.0;
        }
        double ratio=move_distance/total_distance;
        x+=ratio*dx;
        y+=ratio*dy;
        return ratio;
    }
    /**
    <p>向目标点位移比例</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前平面点向指定平面点位移指定比例。
    @param target 目标平面点对象。
    @param move_ratio 比例。<br>
    <code>move_ratio</code>∈[0,1]，0表示起点，1表示目标点。
    @return 位移的距离。
    */
    public double move_ratio_towards(point_planar target,double move_ratio)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        x+=move_ratio*dx;
        y+=move_ratio*dy;
        return move_ratio*Math.sqrt(dx*dx+dy*dy);
    }
    /**
    <p>多边形周长</p><br>
    计算一个多边形的周长。
    @param coordinates_ccw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
    @return 多边形的周长。<br>
    若多边形的顶点不足2个，则返回0.0。
    */
    public static double perimeter(point_planar... coordinates_ccw)
    {
        if(coordinates_ccw.length>=2)
        {
            double temp[]=new double[coordinates_ccw.length<<1];
            for(int i=0;i<coordinates_ccw.length;i++)
            {
                temp[i<<1]=coordinates_ccw[i].x;
                temp[(i<<1)|1]=coordinates_ccw[i].y;
            }
            double result=0;
            for(int i=0;i+3<temp.length;i+=2)
            {
                result+=Math.sqrt((temp[i+2]-temp[i])*(temp[i+2]-temp[i])+(temp[i+3]-temp[i+1])*(temp[i+3]-temp[i+1]));
            }
            result+=Math.sqrt((temp[temp.length-2]-temp[0])*(temp[temp.length-2]-temp[0])+(temp[temp.length-1]-temp[1])*(temp[temp.length-1]-temp[1]));
            return result;
        }
        else
        {
            return 0.0;
        }
    }
    /**
    <p>多边形面积</p><br>
    计算一个多边形的面积。
    @param coordinates_ccw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
    @return 多边形的面积。<br>
    若多边形的顶点不足3个，则返回0.0。
    */
    public static double area(point_planar... coordinates_ccw)
    {
        if(coordinates_ccw.length>=3)
        {
            double temp[]=new double[coordinates_ccw.length<<1];
            for(int i=0;i<coordinates_ccw.length;i++)
            {
                temp[i<<1]=coordinates_ccw[i].x;
                temp[(i<<1)|1]=coordinates_ccw[i].y;
            }
            double median=maths.median(temp);
            for(int i=0;i<temp.length;i++)
            {
                temp[i]-=median;
            }
            double sum1=0,sum2=0;
            for(int i=0;i+3<temp.length;i+=2)
            {
                sum1+=temp[i]*temp[i+3];
                sum2+=temp[i+1]*temp[i+2];
            }
            sum1+=temp[temp.length-2]*temp[1];
            sum2+=temp[temp.length-1]*temp[0];
            return Math.abs(sum1-sum2)/2;
        }
        else
        {
            return 0.0;
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 平面点的字符串表示。
    */
    public String toString()
    {
        return "xy=("+x+","+y+") ρθ=("+rho+","+theta+")";
    }
}