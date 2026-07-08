package tools.date_time;
/**
<p>日期时间类。</p><br>
用于表示和计算日期时间。<br>
包括年、月、日、时、分、秒、毫秒、时区。<br>
本日期时间以推广公历实现，支持公元前，此时年存储为公元前年的倒数+1。<br>
例如公元前1年<code>year=0</code>，公元前2年<code>year=-1</code>。<br>
但在需要表示公元前n年时，应为构造方法传入<code>year=-n</code>。<br>
传入的<code>year=0</code>将被重置为<code>year=1</code><br><br>
若构造时未指定年、月、日，则退化为纯时间对象，无法进行日期处理。<br>
若构造时未指定时、分、秒，则为纯日期对象，但可进行时间处理。<br><br>
默认时区为东八区（UTC+8），可通过<code>datetime.set_default_time_zone(int)</code>方法改变。
*/
public class datetime implements Comparable<datetime>
{
    public static int default_time_zone=8;
    public final int year;
    public final int month;
    public final int day;
    public final int hour;
    public final int minute;
    public final int second;
    public final int millisecond;
    public final int time_zone;
    /**
    通过年、月、日、时、分、秒、毫秒、时区构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param millisecond 毫秒。
    @param time_zone 时区。
    */
    public datetime(int year,int month,int day,int hour,int minute,int second,int millisecond,int time_zone)
    {
        if(year!=Integer.MIN_VALUE&&year<0)
        {
            year++;
        }
        else if(year==0)
        {
            year=1;
        }
        if(year==Integer.MIN_VALUE||month<1||month>12||time_zone<-12||time_zone>12||hour<0||hour>23||minute<0||minute>59||second<0||second>59||millisecond<0||millisecond>999||day<1||day>(is_leap_year(year)?calendar.month_day_in_leap_year[month]:calendar.month_day_in_common_year[month]))
        {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            minute=now[4];
            second=now[5];
            millisecond=now[6];
            time_zone=now[7];
        }
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minute=minute;
        this.second=second;
        this.millisecond=millisecond;
        this.time_zone=time_zone;
    }
    /**
    通过年、月、日、时、分、秒、时区构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param time_zone 时区。
    */
    public datetime(int year,int month,int day,int hour,int minute,int second,int time_zone)
    {
        if(year!=Integer.MIN_VALUE&&year<0)
        {
            year++;
        }
        else if(year==0)
        {
            year=1;
        }
        if(year==Integer.MIN_VALUE||month<1||month>12||time_zone<-12||time_zone>12||hour<0||hour>23||minute<0||minute>59||second<0||second>59||day<1||day>(is_leap_year(year)?calendar.month_day_in_leap_year[month]:calendar.month_day_in_common_year[month]))
        {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            minute=now[4];
            second=now[5];
            time_zone=now[7];
        }
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minute=minute;
        this.second=second;
        this.millisecond=0;
        this.time_zone=time_zone;
    }
    /**
    通过年、月、日、时区或时、分、秒、时区构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。<br><br>
    如需使用年、月、日、时区构造日期时间对象，请在第五个参数中输入<code>true</code>。<br>
    此时构造的对象为纯日期对象，时、分、秒、毫秒均为0。<br>
    如需使用时、分、秒、时区构造日期时间对象，请在第五个参数中输入<code>false</code>。<br>
    此时构造的对象为纯时间对象，年、月、日均为<code>Integer.MIN_VALUE</code>。<br>
    @param year_hour 年或时。
    @param month_minute 月或分。
    @param day_second 日或秒。
    @param time_zone 时区。
    @param true_date_false_time 是否使用年、月、日、时区构造日期时间对象。<br>
    如需使用年、月、日、时区构造纯日期对象，请输入<code>true</code>。<br>
    如需使用时、分、秒、时区构造纯时间对象，请输入<code>false</code>。<br>
    */
    public datetime(int year_hour,int month_minute,int day_second,int time_zone,boolean true_date_false_time)
    {
        if(true_date_false_time)
        {
            if(year_hour!=Integer.MIN_VALUE&&year_hour<0)
            {
                year_hour++;
            }
            else if(year_hour==0)
            {
                year_hour=1;
            }
            if(year_hour==Integer.MIN_VALUE||month_minute<1||month_minute>12||time_zone<-12||time_zone>12||day_second<1||day_second>(is_leap_year(year_hour)?calendar.month_day_in_leap_year[month_minute]:calendar.month_day_in_common_year[month_minute]))
            {
                int now[]=now();
                year_hour=now[0];
                month_minute=now[1];
                day_second=now[2];
                time_zone=default_time_zone;
            }
            this.year=year_hour;
            this.month=month_minute;
            this.day=day_second;
            this.hour=0;
            this.minute=0;
            this.second=0;
            this.millisecond=0;
            this.time_zone=time_zone;
        }
        else
        {
            if(year_hour==Integer.MIN_VALUE||year_hour>23||month_minute<0||month_minute>59||day_second<0||day_second>59||time_zone<-12||time_zone>12)
            {
                int now[]=now();
                year_hour=now[3];
                month_minute=now[4];
                day_second=now[5];
                time_zone=default_time_zone;
            }
            this.year=Integer.MIN_VALUE;
            this.month=Integer.MIN_VALUE;
            this.day=Integer.MIN_VALUE;
            this.hour=year_hour;
            this.minute=month_minute;
            this.second=day_second;
            this.millisecond=0;
            this.time_zone=time_zone;
        }
    }
    /**
    通过年、月、日或时、分、秒构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。<br><br>
    如需使用年、月、日构造日期时间对象，请在第四个参数中输入<code>true</code>。<br>
    此时构造的对象为纯日期对象，时、分、秒、毫秒均为0。<br>
    如需使用时、分、秒构造日期时间对象，请在第四个参数中输入<code>false</code>。<br>
    此时构造的对象为纯时间对象，年、月、日均为<code>Integer.MIN_VALUE</code>。<br>
    @param year_hour 年或时。
    @param month_minute 月或分。
    @param day_second 日或秒。
    @param true_date_false_time 是否使用年、月、日构造日期时间对象。<br>
    如需使用年、月、日构造纯日期对象，请输入<code>true</code>。<br>
    如需使用时、分、秒构造纯时间对象，请输入<code>false</code>。<br>
    */
    public datetime(int year_hour,int month_minute,int day_second,boolean true_date_false_time)
    {
        if(true_date_false_time)
        {
            if(year_hour!=Integer.MIN_VALUE&&year_hour<0)
            {
                year_hour++;
            }
            else if(year_hour==0)
            {
                year_hour=1;
            }
            if(year_hour==Integer.MIN_VALUE||month_minute<1||month_minute>12||day_second<1||day_second>(is_leap_year(year_hour)?calendar.month_day_in_leap_year[month_minute]:calendar.month_day_in_common_year[month_minute]))
            {
                int now[]=now();
                year_hour=now[0];
                month_minute=now[1];
                day_second=now[2];
            }
            this.year=year_hour;
            this.month=month_minute;
            this.day=day_second;
            this.hour=0;
            this.minute=0;
            this.second=0;
            this.millisecond=0;
            this.time_zone=default_time_zone;
        }
        else
        {
            if(year_hour==Integer.MIN_VALUE||year_hour>23||month_minute<0||month_minute>59||day_second<0||day_second>59)
            {
                int now[]=now();
                year_hour=now[3];
                month_minute=now[4];
                day_second=now[5];
            }
            this.year=Integer.MIN_VALUE;
            this.month=Integer.MIN_VALUE;
            this.day=Integer.MIN_VALUE;
            this.hour=year_hour;
            this.minute=month_minute;
            this.second=day_second;
            this.millisecond=0;
            this.time_zone=default_time_zone;
        }
    }
    /**
    通过自公元元年1月1日0时0分0秒的毫秒时间戳构造日期时间对象。
    @param timestamp 自公元元年1月1日0时0分0秒的毫秒时间戳。
    @param time_zone 时区。
    */
    public datetime(long timestamp,int time_zone)
    {
        long millisecond,day;
        int second,minute,hour,month,year;
        if(time_zone<-12||time_zone>12)
        {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            minute=now[4];
            second=now[5];
            millisecond=now[6];
            time_zone=default_time_zone;
        }
        else
        {
            millisecond=timestamp+time_zone*3600000L;
            day=Math.floorDiv(millisecond,86400000L);
            int cycles=(int)Math.floorDiv(day,calendar.day_in_400_years);
            int int_day=Math.floorMod(day,calendar.day_in_400_years);
            year=cycles*400+1;
            cycles=int_day/calendar.day_in_100_years;
            if(cycles==4)
            {
                cycles=3;
            }
            int_day-=cycles*calendar.day_in_100_years;
            year+=cycles*100;
            cycles=int_day/calendar.day_in_4_years;
            int_day-=cycles*calendar.day_in_4_years;
            year+=cycles*4;
            cycles=int_day/calendar.day_in_common_year;
            if(cycles==4)
            {
                cycles=3;
            }
            int_day-=cycles*calendar.day_in_common_year;
            year+=cycles;
            int month_day[]=is_leap_year(year)?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            month=1;
            int right=12,left=1;
            while(right>=left)
            {
                int middle=(left+right)/2;
                if(month_day[middle]>int_day&&month_day[middle-1]<=int_day)
                {
                    month=middle;
                    int_day-=month_day[month-1];
                    break;
                }
                else if(month_day[middle-1]>int_day)
                {
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            millisecond=Math.floorMod(millisecond,86400000L);
            hour=(int)(millisecond/3600000L);
            minute=(int)(millisecond/60000L%60L);
            second=(int)(millisecond%60000L/1000L);
            millisecond%=1000L;
            day=int_day+1;
        }
        this.year=year;
        this.month=month;
        this.day=(int)day;
        this.hour=hour;
        this.minute=minute;
        this.second=second;
        this.millisecond=(int)millisecond;
        this.time_zone=time_zone;
    }
    /**
    通过自公元元年1月1日的日时间戳构造日期时间对象。
    @param timestamp_day 自公元元年1月1日的日时间戳。
    @param time_zone 时区。
    */
    public datetime(int timestamp_day,int time_zone)
    {
        long hour=0;
        int day,month,year;
        if(time_zone<-12||time_zone>12)
        {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            time_zone=default_time_zone;
        }
        else
        {
            hour=timestamp_day*24L+time_zone;
            timestamp_day=(int)Math.floorDiv(hour,24L);
            day=timestamp_day;
            int cycles=(int)Math.floorDiv(day,calendar.day_in_400_years);
            day=Math.floorMod(day,calendar.day_in_400_years);
            year=cycles*400+1;
            cycles=day/calendar.day_in_100_years;
            if(cycles==4)
            {
                cycles=3;
            }
            day-=cycles*calendar.day_in_100_years;
            year+=cycles*100;
            cycles=day/calendar.day_in_4_years;
            day-=cycles*calendar.day_in_4_years;
            year+=cycles*4;
            cycles=day/calendar.day_in_common_year;
            if(cycles==4)
            {
                cycles=3;
            }
            day-=cycles*calendar.day_in_common_year;
            year+=cycles;
            int month_day[]=is_leap_year(year)?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            month=1;
            int right=12,left=1;
            while(right>=left)
            {
                int middle=(left+right)/2;
                if(month_day[middle]>day&&month_day[middle-1]<=day)
                {
                    month=middle;
                    day-=month_day[month-1];
                    break;
                }
                else if(month_day[middle-1]>day)
                {
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            day++;
        }
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=(int)Math.floorMod(hour,24L);
        this.minute=0;
        this.second=0;
        this.millisecond=0;
        this.time_zone=time_zone;
    }
    /**
    构造指定时区当前纯日期或纯时间的对象。
    @param time_zone 时区。
    @param true_date_false_time <br>
    如需构造纯日期对象，请输入<code>true</code>。<br>
    如需构造纯时间对象，请输入<code>false</code>。
    */
    public datetime(int time_zone,boolean true_date_false_time)
    {
        int now[]=now(time_zone>=-12&&time_zone<=12?time_zone:default_time_zone);
        this.year=true_date_false_time?now[0]:Integer.MIN_VALUE;
        this.month=true_date_false_time?now[1]:Integer.MIN_VALUE;
        this.day=true_date_false_time?now[2]:Integer.MIN_VALUE;
        this.hour=true_date_false_time?0:now[3];
        this.minute=true_date_false_time?0:now[4];
        this.second=true_date_false_time?0:now[5];
        this.millisecond=true_date_false_time?0:now[6];
        this.time_zone=now[7];
    }
    /**
    构造指定时区当前日期时间的对象。
    @param time_zone 时区。
    */
    public datetime(int time_zone)
    {
        int now[]=now(time_zone>=-12&&time_zone<=12?time_zone:default_time_zone);
        this.year=now[0];
        this.month=now[1];
        this.day=now[2];
        this.hour=now[3];
        this.minute=now[4];
        this.second=now[5];
        this.millisecond=now[6];
        this.time_zone=now[7];
    }
    /**
    构造默认时区（默认为东八区）当前纯日期或纯时间的对象。
    @param true_date_false_time <br>
    如需构造纯日期对象，请输入<code>true</code>。<br>
    如需构造纯时间对象，请输入<code>false</code>。
    */
    public datetime(boolean true_date_false_time)
    {
        int now[]=now();
        this.year=true_date_false_time?now[0]:Integer.MIN_VALUE;
        this.month=true_date_false_time?now[1]:Integer.MIN_VALUE;
        this.day=true_date_false_time?now[2]:Integer.MIN_VALUE;
        this.hour=true_date_false_time?0:now[3];
        this.minute=true_date_false_time?0:now[4];
        this.second=true_date_false_time?0:now[5];
        this.millisecond=true_date_false_time?0:now[6];
        this.time_zone=default_time_zone;
    }
    /**
    构造默认时区（默认为东八区）当前日期时间的对象。
    */
    public datetime()
    {
        int now[]=now();
        this.year=now[0];
        this.month=now[1];
        this.day=now[2];
        this.hour=now[3];
        this.minute=now[4];
        this.second=now[5];
        this.millisecond=now[6];
        this.time_zone=default_time_zone;
    }
    /**
    获取默认时区。
    @return 默认时区。
    */
    public static int get_default_time_zone()
    {
        return default_time_zone;
    }
    /**
    设置默认时区。
    @param time_zone 默认时区。
    @return 是否成功设置。
    */
    public static boolean set_default_time_zone(int time_zone)
    {
        if(time_zone>=-12&&time_zone<=12)
        {
            default_time_zone=time_zone;
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
    获取指定时区的现在日期时间数组。
    @param time_zone 时区。
    @return 现在日期时间数组。<br>
    <code>{年,月,日,时,分,秒,毫秒,时区}</code>
    @see #now()
    */
    public static int[] now(int time_zone)
    {
        if(time_zone>=-12&&time_zone<=12)
        {
            long millisecond=System.currentTimeMillis()+time_zone*3600000L;
            long day=millisecond/86400000L+calendar.day_1970_1_1-1L;
            int cycles=(int)(day/calendar.day_in_400_years);
            day%=calendar.day_in_400_years;
            int year=cycles*400+1;
            cycles=(int)(day/calendar.day_in_100_years);
            if(cycles==4)
            {
                cycles=3;
            }
            day-=cycles*calendar.day_in_100_years;
            year+=cycles*100;
            cycles=(int)(day/calendar.day_in_4_years);
            day-=cycles*calendar.day_in_4_years;
            year+=cycles*4;
            cycles=(int)(day/calendar.day_in_common_year);
            if(cycles==4)
            {
                cycles=3;
            }
            day-=cycles*calendar.day_in_common_year;
            year+=cycles;
            int month_day[]=year%400==0||year%4==0&&year%100!=0?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            int month=1;
            int right=12,left=1;
            while(right>=left)
            {
                int middle=(left+right)/2;
                if(month_day[middle]>day&&month_day[middle-1]<=day)
                {
                    month=middle;
                    day-=month_day[month-1];
                    break;
                }
                else if(month_day[middle-1]>day)
                {
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            millisecond%=86400000L;
            int hour=(int)(millisecond/3600000L);
            int minute=(int)(millisecond/60000L%60L);
            int second=(int)(millisecond%60000L/1000L);
            millisecond%=1000L;
            return new int[]{year,month,(int)day+1,hour,minute,second,(int)millisecond,time_zone};
        }
        else
        {
            return now(default_time_zone);
        }
    }
    /**
    获取默认时区（默认为东八区）的现在日期时间数组。
    @return 现在日期时间数组。<br>
    <code>{年,月,日,时,分,秒,毫秒,时区}</code>
    */
    public static int[] now()
    {
        return now(default_time_zone);
    }
    /**
    计算当前日期时间对象自公元元年1月1日0时0分0秒的毫秒时间戳。
    @return 当前日期时间对象自公元元年1月1日0时0分0秒的毫秒时间戳。<br>
    对于纯时间对象，返回自当日0时0分0秒 UTC+0的毫秒时间戳。
    */
    public long timestamp()
    {
        long millisecond=this.millisecond+second*1000L+minute*60000L+(hour-time_zone)*3600000L;
        if(year!=Integer.MIN_VALUE)
        {
            int month_day[]=is_leap_year()?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            int day=this.day+month_day[month-1];
            int year=this.year-1;
            millisecond+=(year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1)*86400000L;
        }
        return millisecond;
    }
    /**
    计算指定日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param millisecond 毫秒。
    @return 指定日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。<br>
    对于纯时间对象，返回自当日0时0分0秒的毫秒时间戳。
    */
    public static long timestamp(int year,int month,int day,int hour,int minute,int second,int millisecond)
    {
        long total_millisecond=millisecond+second*1000L+minute*60000L+hour*3600000L;
        if(year!=Integer.MIN_VALUE)
        {
            int month_day[]=is_leap_year(year)?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            day+=month_day[month-1];
            year--;
            total_millisecond+=(year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1)*86400000L;
        }
        return total_millisecond;
    }
    /**
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间对象自公元元年1月1日的日时间戳。
    @return 当前日期时间对象自公元元年1月1日的日时间戳。<br>
    对于纯时间对象，返回<code>Integer.MIN_VALUE</code>。
    */
    public int timestamp_day()
    {
        if(year!=Integer.MIN_VALUE)
        {
            int month_day[]=is_leap_year()?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            int day=this.day+month_day[month-1];
            int year=this.year-1;
            return year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
    计算指定日期自公元元年1月1日的日时间戳。
    @param year 年。
    @param month 月。
    @param day 日。
    @return 指定日期自公元元年1月1日的日时间戳。
    */
    public static int timestamp_day(int year,int month,int day)
    {
        int month_day[]=is_leap_year(year)?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
        day+=month_day[month-1];
        year--;
        return year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1;
    }
    /**
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    判断当前日期时间对象年份是否为闰年。
    @return 是否为闰年。<br>
    对于纯时间对象，返回<code>false</code>。
    */
    public boolean is_leap_year()
    {
        return year!=Integer.MIN_VALUE&&(year%400==0||year%4==0&&year%100!=0);
    }
    /**
    判断指定年份是否为闰年。
    @param year 年份。
    @return 是否为闰年。
    */
    public static boolean is_leap_year(int year)
    {
        return year%400==0||year%4==0&&year%100!=0;
    }
    /**
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间对象的星期。
    @return 当前日期时间对象的星期。<br>
    <ul>
        <li>0=星期日。</li>
        <li>1=星期一。</li>
        <li>2=星期二。</li>
        <li>3=星期三。</li>
        <li>4=星期四。</li>
        <li>5=星期五。</li>
        <li>6=星期六。</li>
    </ul>
    <br>
    对于纯时间对象，返回<code>Integer.MIN_VALUE</code>。
    */
    public int weekday()
    {
        if(year!=Integer.MIN_VALUE)
        {
            int c=Math.floorDiv(year,100);
            int year=Math.floorMod(this.year,100);
            int month=this.month+(this.month<3?12:0);
            year-=month>12?1:0;
            return (-2*c+year+Math.floorDiv(c,4)+Math.floorDiv(year,4)+13*(month+1)/5+day-1)%7;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
    计算指定日期的星期。
    @param year 年。
    @param month 月。
    @param day 日。
    @return 指定日期的星期。<br>
    <ul>
        <li>0=星期日。</li>
        <li>1=星期一。</li>
        <li>2=星期二。</li>
        <li>3=星期三。</li>
        <li>4=星期四。</li>
        <li>5=星期五。</li>
        <li>6=星期六。</li>
    </ul>
    */
    public static int weekday(int year,int month,int day)
    {
        int c=Math.floorDiv(year,100);
        year=Math.floorMod(year,100);
        month+=month<3?12:0;
        year-=month>12?1:0;
        return (-2*c+year+Math.floorDiv(c,4)+Math.floorDiv(year,4)+13*(month+1)/5+day-1)%7;
    }
    /**
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间对象在该年中的天数。
    @return 当前日期时间对象在该年中的天数。<br>
    对于纯时间对象，返回<code>Integer.MIN_VALUE</code>。
    */
    public int day_in_year()
    {
        if(year!=Integer.MIN_VALUE)
        {
            return day+(is_leap_year()?calendar.month_day_in_leap_year_prefix_sum[month-1]:calendar.month_day_in_common_year_prefix_sum[month-1]);
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
    计算指定日期在该年中的天数。
    @param year 年。
    @param month 月。
    @param day 日。
    @return 指定日期在该年中的天数。
    */
    public static int day_in_year(int year,int month,int day)
    {
        return day+(is_leap_year(year)?calendar.month_day_in_leap_year_prefix_sum[month-1]:calendar.month_day_in_common_year_prefix_sum[month-1]);
    }
    /**
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间对象经过指定天数后的日期时间。
    @param add_day 增加的天数，若为负数则减少天数。
    @return 当前日期时间对象经过指定天数后的日期时间对象。<br>
    对于纯时间对象，不做处理，返回<code>this</code>。<br>
    */
    public datetime add_day(int add_day)
    {
        if(year!=Integer.MIN_VALUE)
        {
            int new_timestamp_day=timestamp_day()+add_day;
            int cycles=(int)Math.floorDiv(new_timestamp_day,calendar.day_in_400_years);
            new_timestamp_day=Math.floorMod(new_timestamp_day,calendar.day_in_400_years);
            int new_year=cycles*400+1;
            cycles=new_timestamp_day/calendar.day_in_100_years;
            if(cycles==4)
            {
                cycles=3;
            }
            new_timestamp_day-=cycles*calendar.day_in_100_years;
            new_year+=cycles*100;
            cycles=new_timestamp_day/calendar.day_in_4_years;
            new_timestamp_day-=cycles*calendar.day_in_4_years;
            new_year+=cycles*4;
            cycles=new_timestamp_day/calendar.day_in_common_year;
            if(cycles==4)
            {
                cycles=3;
            }
            new_timestamp_day-=cycles*calendar.day_in_common_year;
            new_year+=cycles;
            int month_day[]=is_leap_year(new_year)?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
            int new_month=1;
            int right=12,left=1;
            while(right>=left)
            {
                int middle=(left+right)/2;
                if(month_day[middle]>new_timestamp_day&&month_day[middle-1]<=new_timestamp_day)
                {
                    new_month=middle;
                    new_timestamp_day-=month_day[new_month-1];
                    break;
                }
                else if(month_day[middle-1]>new_timestamp_day)
                {
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            new_timestamp_day++;
            return new datetime(new_year,new_month,new_timestamp_day,this.hour,this.minute,this.second,this.millisecond,this.time_zone);
        }
        else
        {
            return this;
        }
    }
    /**
    计算指定日期经过指定天数后的日期时间。
    @param year 年。
    @param month 月。
    @param day 日。
    @param add_day 增加的天数，若为负数则减少天数。
    @return 指定日期经过指定天数后的日期时间对象。<br>
    */
    public static datetime add_day(int year,int month,int day,int add_day)
    {
        int new_timestamp_day=timestamp_day(year,month,day)+add_day;
        int cycles=(int)Math.floorDiv(new_timestamp_day,calendar.day_in_400_years);
        new_timestamp_day=Math.floorMod(new_timestamp_day,calendar.day_in_400_years);
        int new_year=cycles*400+1;
        cycles=new_timestamp_day/calendar.day_in_100_years;
        if(cycles==4)
        {
            cycles=3;
        }
        new_timestamp_day-=cycles*calendar.day_in_100_years;
        new_year+=cycles*100;
        cycles=new_timestamp_day/calendar.day_in_4_years;
        new_timestamp_day-=cycles*calendar.day_in_4_years;
        new_year+=cycles*4;
        cycles=new_timestamp_day/calendar.day_in_common_year;
        if(cycles==4)
        {
            cycles=3;
        }
        new_timestamp_day-=cycles*calendar.day_in_common_year;
        new_year+=cycles;
        int month_day[]=is_leap_year(new_year)?calendar.month_day_in_leap_year_prefix_sum:calendar.month_day_in_common_year_prefix_sum;
        int new_month=1;
        int right=12,left=1;
        while(right>=left)
        {
            int middle=(left+right)/2;
            if(month_day[middle]>new_timestamp_day&&month_day[middle-1]<=new_timestamp_day)
            {
                new_month=middle;
                new_timestamp_day-=month_day[new_month-1];
                break;
            }
            else if(month_day[middle-1]>new_timestamp_day)
            {
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        new_timestamp_day++;
        return new datetime(new_year,new_month,new_timestamp_day,true);
    }
    /**
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间对象与指定日期时间对象相差的天数。
    @param to 终点日期时间对象。
    @return 当前日期时间对象与指定日期时间对象相差的天数。<br>
    若当前日期时间对象的时间晚于指定日期时间，则返回的天数为负数。<br>
    若当前日期时间对象或指定日期时间对象为纯时间对象，返回<code>Long.MIN_VALUE</code>。
    */
    public long interval_day(datetime to)
    {
        if(year!=Integer.MIN_VALUE&&to.year!=Integer.MIN_VALUE)
        {
            
            return to.timestamp_day()-timestamp_day();
        }
        else
        {
            return Long.MIN_VALUE;
        }
    }
    /**
    计算两个日期相差的天数。
    @param start_year 起点日期的年。
    @param start_month 起点日期的月。
    @param start_day 起点日期的日。
    @param end_year 终点日期的年。
    @param end_month 终点日期的月。
    @param end_day 终点日期的日。
    @return 两个日期相差的天数。<br>
    若起点日期晚于终点日期，则返回的天数为负数。
    */
    public static long interval_day(int start_year,int start_month,int start_day,int end_year,int end_month,int end_day)
    {
        return timestamp_day(end_year,end_month,end_day)-timestamp_day(start_year,start_month,start_day);
    }
    /**
    计算当前日期时间对象在该天中的秒数。
    @return 当前日期时间对象在该天中的秒数。
    */
    public int second_in_day()
    {
        return hour*3600+minute*60+second;
    }
    /**
    计算指定时间在该天中的秒数。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @return 指定时间在该天中的秒数。
    */
    public static int second_in_day(int hour,int minute,int second)
    {
        return hour*3600+minute*60+second;
    }
    /**
    计算当前日期时间对象与指定日期时间对象相差的日间秒数。
    @param to 终点日期时间对象。
    @return 当前日期时间对象与终点日期时间对象相差的日间秒数。<br>
    若当前时间晚于终点时间，则返回的日间秒数为负数。<br>
    若当前时区与终点时区不同，则返回的秒数的绝对值可能大于一天的秒数。
    */
    public int interval_second_in_day(datetime to)
    {
        return (to.hour-to.time_zone)*3600+to.minute*60+to.second-(hour-time_zone)*3600-minute*60-second;
    }
    /**
    计算两个时间相差的秒数。
    @param start_hour 起点时间的时。
    @param start_minute 起点时间的分。
    @param start_second 起点时间的秒。
    @param end_hour 终点时间的时。
    @param end_minute 终点时间的分。
    @param end_second 终点时间的秒。
    @return 两个时间相差的秒数。<br>
    若起点时间晚于终点时间，则返回的秒数为负数。
    */
    public static int interval_second_in_day(int start_hour,int start_minute,int start_second,int end_hour,int end_minute,int end_second)
    {
        return end_hour*3600+end_minute*60+end_second-start_hour*3600-start_minute*60-start_second;
    }
    public String toString()
    {
        if(year!=Integer.MIN_VALUE)
        {
            if(year>0)
            {
                return String.format("AD %d/%02d/%02d %02d:%02d:%02d.%03d UTC%s",year,month,day,hour,minute,second,millisecond,(time_zone>=0?"+"+time_zone:""+time_zone));
            }
            else
            {
                return String.format("%d/%02d/%02d BC %02d:%02d:%02d.%03d UTC%s",-year+1,month,day,hour,minute,second,millisecond,(time_zone>=0?"+"+time_zone:""+time_zone));
            }
        }
        else
        {
            return String.format("%02d:%02d:%02d.%03d UTC%s",hour,minute,second,millisecond,(time_zone>=0?"+"+time_zone:""+time_zone));
        }
    }
    /**
    比较当前日期时间对象与指定日期时间对象的时间。
    @param another 指定日期时间对象。
    @return 当前日期时间对象与指定日期时间对象的时间的比较结果。<br>
    若当前日期时间对象的时间晚于指定日期时间，则返回的整数为正数。<br>
    若当前日期时间对象的时间与指定日期时间相同，则返回0。<br>
    若当前日期时间对象的时间早于指定日期时间，则返回的整数为负数。<br>
    若混合比较日期时间对象和纯时间对象，则仅比较时间部分。
    */
    public int compareTo(datetime another)
    {
        if(year!=Integer.MIN_VALUE&&another.year!=Integer.MIN_VALUE||year==Integer.MIN_VALUE&&another.year==Integer.MIN_VALUE)
        {
            long difference=timestamp()-another.timestamp();
            return difference>0?1:difference==0?0:-1;
        }
        else
        {
            return (hour-time_zone)*3600000+minute*60000+second*1000+millisecond-(another.hour-another.time_zone)*3600000-another.minute*60000-another.second*1000-another.millisecond;
        }
    }
}