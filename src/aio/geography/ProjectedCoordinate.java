package aio.geography;
import aio.mathematics.Maths;
/**
<p>投影坐标类</p><br>
投影坐标是指在平面上的一个点的横坐标和纵坐标。<br>
横坐标正方向为东，纵坐标正方向为北，即坐标北。<br>
横坐标和纵坐标都是实数，取值范围为任意实数。<br>
本投影坐标单位为米。<br>
*/
public class ProjectedCoordinate {
    /**
    <p>横坐标</p>
    */
    public double x;
    /**
    <p>纵坐标</p>
    */
    public double y;
    /**
    <p>全参构造方法</p><br>
    构造一个指定位置的投影坐标对象。
    @param x 横坐标。
    @param y 纵坐标。
    */
    public ProjectedCoordinate(double x,double y) {
        this.x=x;
        this.y=y;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认的投影坐标对象，表示原点。
    */
    public ProjectedCoordinate() {
        x=0;
        y=0;
    }
    /**
    <p>坐标位移</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前投影坐标进行位移。
    @param deltaX 向东位移。
    @param deltaY 向北位移。
    @return 是否实际移动，即位移是否大于0.
    */
    public boolean move(double deltaX,double deltaY) {
        x+=deltaX;
        y+=deltaY;
        return deltaX!=0||deltaY!=0;
    }
    /**
    <p>坐标偏移</p><br>
    计算指定投影坐标偏移指定量的投影坐标。
    @param coordinate 基准投影坐标对象。
    @param deltaX 向东偏移。
    @param deltaY 向北偏移。
    @return 偏移后的投影坐标。
    */
    public static ProjectedCoordinate offset(ProjectedCoordinate coordinate,double deltaX,double deltaY) {
        return new ProjectedCoordinate(coordinate.x+deltaX,coordinate.y+deltaY);
    }
    /**
    <p>相对坐标</p><br>
    计算一个投影坐标到当前投影坐标的相对坐标。
    @param coordinate 目标投影坐标对象。
    @return 相对坐标数组。<br>
    <code>{相对横坐标,相对纵坐标}</code>。
    */
    public double[] relativePosition(ProjectedCoordinate coordinate) {
        return new double[]{coordinate.x-x,coordinate.y-y};
    }
    /**
    <p>两点间距离</p><br>
    计算当前投影坐标与另一个投影坐标的距离。
    @param coordinate 要计算距离的投影坐标对象。
    @return 当前投影坐标与另一个投影坐标的距离。
    */
    public double distance(ProjectedCoordinate coordinate) {
        return Math.sqrt((x-coordinate.x)*(x-coordinate.x)+(y-coordinate.y)*(y-coordinate.y));
    }
    /**
    <p>两点间距离</p><br>
    计算两点间的距离。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 两点间距离。
    */
    public static double distance(double x0,double y0,double xt,double yt) {
        return Math.sqrt((xt-x0)*(xt-x0)+(yt-y0)*(yt-y0));
    }
    /**
    <p>近似相等</p><br>
    判断当前投影坐标与指定投影坐标是否在指定容差内。
    @param coordinate 投影坐标对象。
    @param tolerance 容差。
    @return 是否在指定容差内。
    */
    public boolean equalsApproximate(ProjectedCoordinate coordinate,double tolerance) {
        return distance(coordinate)<=tolerance;
    }
    /**
    <p>相对方位角</p><br>
    计算该点到终点的方位角。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 该点到终点的方位角。
    */
    public double azimuthAngle(double xt,double yt) {
        double dx=xt-x;
        double dy=yt-y;
        if(dx==0&&dy>=0) {
            return 0;
        } else if(dx==0&&dy<0) {
            return 180;
        } else if(dx<0) {
            return 270-Math.atan((double)dy/dx)*180/Math.PI;
        } else {
            return 90-Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    <p>相对方位角</p><br>
    计算该点到目标点的方位角。
    @param target 目标投影坐标对象。
    @return 该点到目标点的方位角。
    */
    public double azimuthAngle(ProjectedCoordinate target) {
        double dx=target.x-x;
        double dy=target.y-y;
        if(dx==0&&dy>=0) {
            return 0;
        } else if(dx==0&&dy<0) {
            return 180;
        } else if(dx<0) {
            return 270-Math.atan((double)dy/dx)*180/Math.PI;
        } else {
            return 90-Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    <p>相对方位角</p><br>
    计算起点到终点的方位角。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 起点到终点的方位角。
    */
    public static double azimuthAngle(double x0,double y0,double xt,double yt) {
        double dx=xt-x0;
        double dy=yt-y0;
        if(dx==0&&dy>=0) {
            return 0;
        } else if(dx==0&&dy<0) {
            return 180;
        } else if(dx<0) {
            return 270-Math.atan((double)dy/dx)*180/Math.PI;
        } else {
            return 90-Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    <p>向方位位移距离</p><br>
    计算该投影坐标以指定方位角位移指定距离后的投影坐标。
    @param azimuthAngle 方位角。
    @param distance 距离。
    @return 以指定方位角位移指定距离后的投影坐标对象。
    */
    public ProjectedCoordinate destination(double azimuthAngle,double distance) {
        double destinationX=x;
        double destinationY=y;
        double halfDistance=distance/2;
        azimuthAngle=(azimuthAngle+360)%360;
        if(azimuthAngle==0) {
            destinationY+=distance;
        } else if(azimuthAngle==30) {
            destinationX+=halfDistance;
            destinationY+=halfDistance*Math.sqrt(3);
        } else if(azimuthAngle==60) {
            destinationX+=halfDistance*Math.sqrt(3);
            destinationY+=halfDistance;
        } else if(azimuthAngle==90) {
            destinationX+=distance;
        } else if(azimuthAngle==120) {
            destinationX+=halfDistance*Math.sqrt(3);
            destinationY-=halfDistance;
        } else if(azimuthAngle==150) {
            destinationX+=halfDistance;
            destinationY-=halfDistance*Math.sqrt(3);
        } else if(azimuthAngle==180) {
            destinationY-=distance;
        } else if(azimuthAngle==210) {
            destinationX-=halfDistance;
            destinationY-=halfDistance*Math.sqrt(3);
        } else if(azimuthAngle==240) {
            destinationX-=halfDistance*Math.sqrt(3);
            destinationY-=halfDistance;
        } else if(azimuthAngle==270) {
            destinationX-=distance;
        } else if(azimuthAngle==300) {
            destinationX-=halfDistance*Math.sqrt(3);
            destinationY+=halfDistance;
        } else if(azimuthAngle==330) {
            destinationX-=halfDistance;
            destinationY+=halfDistance*Math.sqrt(3);
        } else {
            destinationX+=distance*Math.cos((90-azimuthAngle)*Math.PI/180);
            destinationY+=distance*Math.sin((90-azimuthAngle)*Math.PI/180);
        }
        return new ProjectedCoordinate(destinationX,destinationY);
    }
    /**
    <p>中点</p><br>
    计算该投影坐标与指定投影坐标连线的中点。
    @param target 目标投影坐标对象。
    @return 该投影坐标与指定投影坐标连线的中点。
    */
    public ProjectedCoordinate middlePoint(ProjectedCoordinate target) {
        return new ProjectedCoordinate((target.x+x)/2,(target.y+y)/2);
    }
    /**
    <p>线性插值</p><br>
    计算该投影坐标到指定投影坐标的线性插值。
    @param target 目标投影坐标对象。
    @param ratio 线性插值比例。<br>
    ratio∈[0,1]，0表示起点，1表示目标点。
    @return 该投影坐标到指定投影坐标的线性插值。
    */
    public ProjectedCoordinate linearInterpolation(ProjectedCoordinate target,double ratio) {
        return new ProjectedCoordinate(x+ratio*(target.x-x),y+ratio*(target.y-y));
    }
    /**
    <p>向目标点位移距离</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前投影坐标向指定投影坐标位移指定距离。<br>
    注意，坐标经计算后可能存在双精度浮点数精度误差，导致位移结果不准确。
    @param target 目标投影坐标对象。
    @param moveDistance 距离。
    @return 位移的距离占总距离的比例。<br>
    0表示起点，1表示目标点。
    */
    public double moveDistanceTowards(ProjectedCoordinate target,double moveDistance) {
        double dx=target.x-x;
        double dy=target.y-y;
        double totalDistance=Math.sqrt(dx*dx+dy*dy);
        if(totalDistance==0.0) {
            return 0.0;
        }
        double ratio=moveDistance/totalDistance;
        x+=ratio*dx;
        y+=ratio*dy;
        return ratio;
    }
    /**
    <p>向目标点位移比例</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前投影坐标向指定投影坐标位移指定比例。
    @param target 目标投影坐标对象。
    @param moveRatio 比例。<br>
    <code>moveRatio</code>∈[0,1]，0表示起点，1表示目标点。
    @return 位移的距离。
    */
    public double moveRatioTowards(ProjectedCoordinate target,double moveRatio) {
        double dx=target.x-x;
        double dy=target.y-y;
        x+=moveRatio*dx;
        y+=moveRatio*dy;
        return moveRatio*Math.sqrt(dx*dx+dy*dy);
    }
    /**
    <p>多边形周长</p><br>
    计算一个多边形区域的周长。
    @param coordinatesCcw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
    @return 多边形区域的周长。<br>
    若多边形的顶点不足2个，则返回0.0。
    */
    public static double perimeter(ProjectedCoordinate... coordinatesCcw) {
        if(coordinatesCcw.length>=2) {
            double temp[]=new double[coordinatesCcw.length<<1];
            for(int i=0;i<coordinatesCcw.length;i++) {
                temp[i<<1]=coordinatesCcw[i].x;
                temp[(i<<1)|1]=coordinatesCcw[i].y;
            }
            double result=0;
            for(int i=0;i+3<temp.length;i+=2) {
                result+=Math.sqrt((temp[i+2]-temp[i])*(temp[i+2]-temp[i])+(temp[i+3]-temp[i+1])*(temp[i+3]-temp[i+1]));
            }
            result+=Math.sqrt((temp[temp.length-2]-temp[0])*(temp[temp.length-2]-temp[0])+(temp[temp.length-1]-temp[1])*(temp[temp.length-1]-temp[1]));
            return result;
        } else {
            return 0.0;
        }
    }
    /**
    <p>多边形面积</p><br>
    计算一个多边形区域的面积。
    @param coordinatesCcw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
    @return 多边形区域的面积。<br>
    若多边形的顶点不足3个，则返回0.0。
    */
    public static double area(ProjectedCoordinate... coordinatesCcw) {
        if(coordinatesCcw.length>=3) {
            double temp[]=new double[coordinatesCcw.length<<1];
            for(int i=0;i<coordinatesCcw.length;i++) {
                temp[i<<1]=coordinatesCcw[i].x;
                temp[(i<<1)|1]=coordinatesCcw[i].y;
            }
            double median=Maths.median(temp);
            for(int i=0;i<temp.length;i++) {
                temp[i]-=median;
            }
            double sum1=0,sum2=0;
            for(int i=0;i+3<temp.length;i+=2) {
                sum1+=temp[i]*temp[i+3];
                sum2+=temp[i+1]*temp[i+2];
            }
            sum1+=temp[temp.length-2]*temp[1];
            sum2+=temp[temp.length-1]*temp[0];
            return Math.abs(sum1-sum2)/2;
        } else {
            return 0.0;
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 投影坐标的字符串表示。
    */
    public String toString() {
        return "("+x+","+y+")";
    }
    /**
    <p>相等判断</p><br>
    判断当前投影坐标是否严格等于指定投影坐标。
    @param another 投影坐标对象。
    @return 是否严格等于指定投影坐标。<br>
    注意，坐标经计算后可能存在双精度浮点数精度误差，导致判断结果不准确。
    */
    public boolean equals(Object another) {
        if(another instanceof ProjectedCoordinate) {
            if(another==this) {
                return true;
            } else {
                ProjectedCoordinate target=(ProjectedCoordinate)another;
                return target.x==x&&target.y==y;
            }
        } else {
            return false;
        }
    }
}