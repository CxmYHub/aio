package aio.datetime;
/**
<p>日期时间类</p><br>
用于表示和计算日期时间。<br>
包括年、月、日、时、分、秒、毫秒、时区。<br>
本日期时间以推广公历实现，支持公元前，此时年存储为公元前年的倒数+1。<br>
例如公元前1年<code>year=0</code>，公元前2年<code>year=-1</code>。<br>
但在需要表示公元前n年时，应为构造方法传入<code>year=-n</code>。<br>
传入的<code>year=0</code>将被重置为<code>year=1</code><br><br>
若构造时未指定年、月、日，则退化为纯时间对象，无法进行日期处理。<br>
若构造时未指定时、分、秒，则为纯日期对象，但可进行时间处理。<br><br>
默认时区为东八区（UTC+8），可通过<code>Datetime.setDefaultTimeZone(int)</code>方法改变。
*/
public class Datetime implements Comparable<Datetime> {
    /**
    <p>默认时区</p><br>
    默认为东八区（UTC+8）。
    */
    public static int defaultTimeZone=8;
    /**
    <p>年</p>
    */
    public final int YEAR;
    /**
    <p>月</p>
    */
    public final int MONTH;
    /**
    <p>日</p>
    */
    public final int DAY;
    /**
    <p>时</p>
    */
    public final int HOUR;
    /**
    <p>分</p>
    */
    public final int MINUTE;
    /**
    <p>秒</p>
    */
    public final int SECOND;
    /**
    <p>毫秒</p>
    */
    public final int MILLISECOND;
    /**
    <p>时区</p>
    */
    public final int TIME_ZONE;
    /**
    <p>全参构造方法</p><br>
    通过年、月、日、时、分、秒、毫秒、时区构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param millisecond 毫秒。
    @param timeZone 时区。
    */
    public Datetime(int year,int month,int day,int hour,int minute,int second,int millisecond,int timeZone) {
        if(year!=Integer.MIN_VALUE&&year<0) {
            year++;
        } else if(year==0) {
            year=1;
        }
        if(year==Integer.MIN_VALUE||month<1||month>12||timeZone<-12||timeZone>12||hour<0||hour>23||minute<0||minute>59||second<0||second>59||millisecond<0||millisecond>999||day<1||day>(isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR[month]:Calendar.MONTH_DAY_IN_COMMON_YEAR[month])) {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            minute=now[4];
            second=now[5];
            millisecond=now[6];
            timeZone=now[7];
        }
        this.YEAR=year;
        this.MONTH=month;
        this.DAY=day;
        this.HOUR=hour;
        this.MINUTE=minute;
        this.SECOND=second;
        this.MILLISECOND=millisecond;
        this.TIME_ZONE=timeZone;
    }
    /**
    <p>构造方法</p><br>
    通过年、月、日、时、分、秒、时区构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param timeZone 时区。
    */
    public Datetime(int year,int month,int day,int hour,int minute,int second,int timeZone) {
        if(year!=Integer.MIN_VALUE&&year<0) {
            year++;
        } else if(year==0) {
            year=1;
        }
        if(year==Integer.MIN_VALUE||month<1||month>12||timeZone<-12||timeZone>12||hour<0||hour>23||minute<0||minute>59||second<0||second>59||day<1||day>(isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR[month]:Calendar.MONTH_DAY_IN_COMMON_YEAR[month])) {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            minute=now[4];
            second=now[5];
            timeZone=now[7];
        }
        this.YEAR=year;
        this.MONTH=month;
        this.DAY=day;
        this.HOUR=hour;
        this.MINUTE=minute;
        this.SECOND=second;
        MILLISECOND=0;
        this.TIME_ZONE=timeZone;
    }
    /**
    <p>构造方法</p><br>
    通过年、月、日、时区或时、分、秒、时区构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。<br><br>
    如需使用年、月、日、时区构造日期时间对象，请在第五个参数中输入<code>true</code>。<br>
    此时构造的对象为纯日期对象，时、分、秒、毫秒均为0。<br>
    如需使用时、分、秒、时区构造日期时间对象，请在第五个参数中输入<code>false</code>。<br>
    此时构造的对象为纯时间对象，年、月、日均为<code>Integer.MIN_VALUE</code>。<br>
    @param yearHour 年或时。
    @param monthMinute 月或分。
    @param daySecond 日或秒。
    @param timeZone 时区。
    @param trueDateFalseTime 是否使用年、月、日、时区构造日期时间对象。<br>
    如需使用年、月、日、时区构造纯日期对象，请输入<code>true</code>。<br>
    如需使用时、分、秒、时区构造纯时间对象，请输入<code>false</code>。
    */
    public Datetime(int yearHour,int monthMinute,int daySecond,int timeZone,boolean trueDateFalseTime) {
        if(trueDateFalseTime) {
            if(yearHour!=Integer.MIN_VALUE&&yearHour<0) {
                yearHour++;
            } else if(yearHour==0) {
                yearHour=1;
            }
            if(yearHour==Integer.MIN_VALUE||monthMinute<1||monthMinute>12||timeZone<-12||timeZone>12||daySecond<1||daySecond>(isLeapYear(yearHour)?Calendar.MONTH_DAY_IN_LEAP_YEAR[monthMinute]:Calendar.MONTH_DAY_IN_COMMON_YEAR[monthMinute])) {
                int now[]=now();
                yearHour=now[0];
                monthMinute=now[1];
                daySecond=now[2];
                timeZone=defaultTimeZone;
            }
            this.YEAR=yearHour;
            this.MONTH=monthMinute;
            this.DAY=daySecond;
            HOUR=0;
            MINUTE=0;
            SECOND=0;
            MILLISECOND=0;
            this.TIME_ZONE=timeZone;
        } else {
            if(yearHour==Integer.MIN_VALUE||yearHour>23||monthMinute<0||monthMinute>59||daySecond<0||daySecond>59||timeZone<-12||timeZone>12) {
                int now[]=now();
                yearHour=now[3];
                monthMinute=now[4];
                daySecond=now[5];
                timeZone=defaultTimeZone;
            }
            YEAR=Integer.MIN_VALUE;
            MONTH=Integer.MIN_VALUE;
            DAY=Integer.MIN_VALUE;
            this.HOUR=yearHour;
            this.MINUTE=monthMinute;
            this.SECOND=daySecond;
            this.MILLISECOND=0;
            this.TIME_ZONE=timeZone;
        }
    }
    /**
    <p>构造方法</p><br>
    通过年、月、日或时、分、秒构造日期时间对象。<br>
    若参数无效，则使用默认时区（默认为东八区）当前日期时间。<br><br>
    如需使用年、月、日构造日期时间对象，请在第四个参数中输入<code>true</code>。<br>
    此时构造的对象为纯日期对象，时、分、秒、毫秒均为0。<br>
    如需使用时、分、秒构造日期时间对象，请在第四个参数中输入<code>false</code>。<br>
    此时构造的对象为纯时间对象，年、月、日均为<code>Integer.MIN_VALUE</code>。<br>
    @param yearHour 年或时。
    @param monthMinute 月或分。
    @param daySecond 日或秒。
    @param trueDateFalseTime 是否使用年、月、日构造日期时间对象。<br>
    如需使用年、月、日构造纯日期对象，请输入<code>true</code>。<br>
    如需使用时、分、秒构造纯时间对象，请输入<code>false</code>。
    */
    public Datetime(int yearHour,int monthMinute,int daySecond,boolean trueDateFalseTime) {
        if(trueDateFalseTime) {
            if(yearHour!=Integer.MIN_VALUE&&yearHour<0) {
                yearHour++;
            } else if(yearHour==0) {
                yearHour=1;
            }
            if(yearHour==Integer.MIN_VALUE||monthMinute<1||monthMinute>12||daySecond<1||daySecond>(isLeapYear(yearHour)?Calendar.MONTH_DAY_IN_LEAP_YEAR[monthMinute]:Calendar.MONTH_DAY_IN_COMMON_YEAR[monthMinute])) {
                int now[]=now();
                yearHour=now[0];
                monthMinute=now[1];
                daySecond=now[2];
            }
            this.YEAR=yearHour;
            this.MONTH=monthMinute;
            this.DAY=daySecond;
            HOUR=0;
            MINUTE=0;
            SECOND=0;
            MILLISECOND=0;
            this.TIME_ZONE=defaultTimeZone;
        } else {
            if(yearHour==Integer.MIN_VALUE||yearHour>23||monthMinute<0||monthMinute>59||daySecond<0||daySecond>59) {
                int now[]=now();
                yearHour=now[3];
                monthMinute=now[4];
                daySecond=now[5];
            }
            this.YEAR=Integer.MIN_VALUE;
            this.MONTH=Integer.MIN_VALUE;
            this.DAY=Integer.MIN_VALUE;
            this.HOUR=yearHour;
            this.MINUTE=monthMinute;
            this.SECOND=daySecond;
            this.MILLISECOND=0;
            this.TIME_ZONE=defaultTimeZone;
        }
    }
    /**
    <p>构造方法</p><br>
    通过自公元元年1月1日0时0分0秒的毫秒时间戳和时区构造日期时间对象。
    @param timestamp 自公元元年1月1日0时0分0秒的毫秒时间戳。
    @param timeZone 时区。
    */
    public Datetime(long timestamp,int timeZone) {
        long millisecond,day;
        int second,minute,hour,month,year;
        if(timeZone<-12||timeZone>12) {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            minute=now[4];
            second=now[5];
            millisecond=now[6];
            timeZone=defaultTimeZone;
        } else {
            millisecond=timestamp+timeZone*3600000L;
            day=Math.floorDiv(millisecond,86400000L);
            int cycles=(int)Math.floorDiv(day,Calendar.DAY_IN_400_YEARS);
            int intDay=Math.floorMod(day,Calendar.DAY_IN_400_YEARS);
            year=cycles*400+1;
            cycles=intDay/Calendar.DAY_IN_100_YEARS;
            if(cycles==4) {
                cycles=3;
            }
            intDay-=cycles*Calendar.DAY_IN_100_YEARS;
            year+=cycles*100;
            cycles=intDay/Calendar.DAY_IN_4_YEARS;
            intDay-=cycles*Calendar.DAY_IN_4_YEARS;
            year+=cycles*4;
            cycles=intDay/Calendar.DAY_IN_COMMON_YEAR;
            if(cycles==4) {
                cycles=3;
            }
            intDay-=cycles*Calendar.DAY_IN_COMMON_YEAR;
            year+=cycles;
            int monthDay[]=isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            month=1;
            int right=12,left=1;
            while(right>=left) {
                int middle=(left+right)/2;
                if(monthDay[middle]>intDay&&monthDay[middle-1]<=intDay) {
                    month=middle;
                    intDay-=monthDay[month-1];
                    break;
                } else if(monthDay[middle-1]>intDay) {
                    right=middle-1;
                } else {
                    left=middle+1;
                }
            }
            millisecond=Math.floorMod(millisecond,86400000L);
            hour=(int)(millisecond/3600000L);
            minute=(int)(millisecond/60000L%60L);
            second=(int)(millisecond%60000L/1000L);
            millisecond%=1000L;
            day=intDay+1;
        }
        this.YEAR=year;
        this.MONTH=month;
        this.DAY=(int)day;
        this.HOUR=hour;
        this.MINUTE=minute;
        this.SECOND=second;
        this.MILLISECOND=(int)millisecond;
        this.TIME_ZONE=timeZone;
    }
    /**
    <p>构造方法</p><br>
    通过自公元元年1月1日的日时间戳和时区构造日期时间对象。
    @param timestampDay 自公元元年1月1日的日时间戳。
    @param timeZone 时区。
    */
    public Datetime(int timestampDay,int timeZone) {
        long hour=0;
        int day,month,year;
        if(timeZone<-12||timeZone>12) {
            int now[]=now();
            year=now[0];
            month=now[1];
            day=now[2];
            hour=now[3];
            timeZone=defaultTimeZone;
        } else {
            hour=timestampDay*24L+timeZone;
            timestampDay=(int)Math.floorDiv(hour,24L);
            day=timestampDay;
            int cycles=(int)Math.floorDiv(day,Calendar.DAY_IN_400_YEARS);
            day=Math.floorMod(day,Calendar.DAY_IN_400_YEARS);
            year=cycles*400+1;
            cycles=day/Calendar.DAY_IN_100_YEARS;
            if(cycles==4) {
                cycles=3;
            }
            day-=cycles*Calendar.DAY_IN_100_YEARS;
            year+=cycles*100;
            cycles=day/Calendar.DAY_IN_4_YEARS;
            day-=cycles*Calendar.DAY_IN_4_YEARS;
            year+=cycles*4;
            cycles=day/Calendar.DAY_IN_COMMON_YEAR;
            if(cycles==4) {
                cycles=3;
            }
            day-=cycles*Calendar.DAY_IN_COMMON_YEAR;
            year+=cycles;
            int monthDay[]=isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            month=1;
            int right=12,left=1;
            while(right>=left) {
                int middle=(left+right)/2;
                if(monthDay[middle]>day&&monthDay[middle-1]<=day) {
                    month=middle;
                    day-=monthDay[month-1];
                    break;
                } else if(monthDay[middle-1]>day) {
                    right=middle-1;
                } else {
                    left=middle+1;
                }
            }
            day++;
        }
        this.YEAR=year;
        this.MONTH=month;
        this.DAY=day;
        this.HOUR=(int)Math.floorMod(hour,24L);
        MINUTE=0;
        SECOND=0;
        MILLISECOND=0;
        this.TIME_ZONE=timeZone;
    }
    /**
    <p>构造方法</p><br>
    构造指定时区当前纯日期或纯时间的对象。
    @param timeZone 时区。
    @param trueDateFalseTime <br>
    如需构造纯日期对象，请输入<code>true</code>。<br>
    如需构造纯时间对象，请输入<code>false</code>。
    */
    public Datetime(int timeZone,boolean trueDateFalseTime) {
        int now[]=now(timeZone>=-12&&timeZone<=12?timeZone:defaultTimeZone);
        YEAR=trueDateFalseTime?now[0]:Integer.MIN_VALUE;
        MONTH=trueDateFalseTime?now[1]:Integer.MIN_VALUE;
        DAY=trueDateFalseTime?now[2]:Integer.MIN_VALUE;
        HOUR=trueDateFalseTime?0:now[3];
        MINUTE=trueDateFalseTime?0:now[4];
        SECOND=trueDateFalseTime?0:now[5];
        MILLISECOND=trueDateFalseTime?0:now[6];
        this.TIME_ZONE=now[7];
    }
    /**
    <p>构造方法</p><br>
    构造默认时区（默认为东八区）当前纯日期或纯时间的对象。
    @param trueDateFalseTime <br>
    如需构造纯日期对象，请输入<code>true</code>。<br>
    如需构造纯时间对象，请输入<code>false</code>。
    */
    public Datetime(boolean trueDateFalseTime) {
        int now[]=now();
        YEAR=trueDateFalseTime?now[0]:Integer.MIN_VALUE;
        MONTH=trueDateFalseTime?now[1]:Integer.MIN_VALUE;
        DAY=trueDateFalseTime?now[2]:Integer.MIN_VALUE;
        HOUR=trueDateFalseTime?0:now[3];
        MINUTE=trueDateFalseTime?0:now[4];
        SECOND=trueDateFalseTime?0:now[5];
        MILLISECOND=trueDateFalseTime?0:now[6];
        TIME_ZONE=defaultTimeZone;
    }
    /**
    <p>构造方法</p><br>
    构造指定时区当前日期时间的对象。
    @param timeZone 时区。
    */
    public Datetime(int timeZone) {
        int now[]=now(timeZone>=-12&&timeZone<=12?timeZone:defaultTimeZone);
        YEAR=now[0];
        MONTH=now[1];
        DAY=now[2];
        HOUR=now[3];
        MINUTE=now[4];
        SECOND=now[5];
        MILLISECOND=now[6];
        this.TIME_ZONE=now[7];
    }
    /**
    <p>无参构造方法</p><br>
    构造默认时区（默认为东八区）当前日期时间的对象。
    */
    public Datetime() {
        int now[]=now();
        YEAR=now[0];
        MONTH=now[1];
        DAY=now[2];
        HOUR=now[3];
        MINUTE=now[4];
        SECOND=now[5];
        MILLISECOND=now[6];
        TIME_ZONE=defaultTimeZone;
    }
    /**
    <p>获取默认时区</p><br>
    @return 默认时区。
    */
    public static int getDefaultTimeZone() {
        return defaultTimeZone;
    }
    /**
    <p>设置默认时区</p><br>
    若输入的时区不在[-12,12]范围内，则不设置。
    @param timeZone 默认时区。
    @return 是否成功设置。
    */
    public static boolean setDefaultTimeZone(int timeZone) {
        if(timeZone>=-12&&timeZone<=12) {
            defaultTimeZone=timeZone;
            return true;
        } else {
            return false;
        }
    }
    /**
    <p>现在日期时间数组获取（指定时区）</p><br>
    获取指定时区的现在日期时间数组。
    @param timeZone 时区。
    @return 现在日期时间数组。<br>
    <code>{年,月,日,时,分,秒,毫秒,时区}</code>
    @see #now()
    */
    public static int[] now(int timeZone) {
        if(timeZone>=-12&&timeZone<=12) {
            long millisecond=System.currentTimeMillis()+timeZone*3600000L;
            long day=millisecond/86400000L+Calendar.DAY_1970_1_1;
            int cycles=(int)(day/Calendar.DAY_IN_400_YEARS);
            day%=Calendar.DAY_IN_400_YEARS;
            int year=cycles*400+1;
            cycles=(int)(day/Calendar.DAY_IN_100_YEARS);
            if(cycles==4) {
                cycles=3;
            }
            day-=cycles*Calendar.DAY_IN_100_YEARS;
            year+=cycles*100;
            cycles=(int)(day/Calendar.DAY_IN_4_YEARS);
            day-=cycles*Calendar.DAY_IN_4_YEARS;
            year+=cycles*4;
            cycles=(int)(day/Calendar.DAY_IN_COMMON_YEAR);
            if(cycles==4) {
                cycles=3;
            }
            day-=cycles*Calendar.DAY_IN_COMMON_YEAR;
            year+=cycles;
            int monthDay[]=year%400==0||year%4==0&&year%100!=0?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            int month=1;
            int right=12,left=1;
            while(right>=left) {
                int middle=(left+right)/2;
                if(monthDay[middle]>day&&monthDay[middle-1]<=day) {
                    month=middle;
                    day-=monthDay[month-1];
                    break;
                } else if(monthDay[middle-1]>day) {
                    right=middle-1;
                } else {
                    left=middle+1;
                }
            }
            millisecond%=86400000L;
            int hour=(int)(millisecond/3600000L);
            int minute=(int)(millisecond/60000L%60L);
            int second=(int)(millisecond%60000L/1000L);
            millisecond%=1000L;
            return new int[]{year,month,(int)day+1,hour,minute,second,(int)millisecond,timeZone};
        } else {
            return now(defaultTimeZone);
        }
    }
    /**
    <p>现在日期时间数组获取（默认时区）</p><br>
    获取默认时区（默认为东八区）的现在日期时间数组。
    @return 现在日期时间数组。<br>
    <code>{年,月,日,时,分,秒,毫秒,时区}</code>
    */
    public static int[] now() {
        return now(defaultTimeZone);
    }
    /**
    <p>毫秒时间戳计算</p><br>
    计算当前日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。
    @return 当前日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。<br>
    对于纯时间对象，返回自当日0时0分0秒 UTC+0的毫秒时间戳。
    */
    public long timestamp() {
        long millisecond=this.MILLISECOND+SECOND*1000L+MINUTE*60000L+(HOUR-TIME_ZONE)*3600000L;
        if(YEAR!=Integer.MIN_VALUE) {
            int monthDayPrefixSum[]=isLeapYear()?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            int day=this.DAY+monthDayPrefixSum[MONTH-1];
            int year=this.YEAR-1;
            millisecond+=(year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1)*86400000L;
        }
        return millisecond;
    }
    /**
    <p>毫秒时间戳计算</p><br>
    计算指定日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param millisecond 毫秒。
    @param timeZone 时区。
    @return 指定日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。
    */
    public static long timestamp(int year,int month,int day,int hour,int minute,int second,int millisecond,int timeZone) {
        long totalMillisecond=millisecond+second*1000L+minute*60000L+(hour-timeZone)*3600000L;
        int monthDayPrefixSum[]=isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
        day+=monthDayPrefixSum[month-1];
        year--;
        totalMillisecond+=(year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1)*86400000L;
        return totalMillisecond;
    }
    /**
    <p>现在毫秒时间戳</p><br>
    计算现在日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。
    @return 现在日期时间自公元元年1月1日0时0分0秒的毫秒时间戳。
    */
    public static long timestampNow() {
        return System.currentTimeMillis()+Calendar.TIMESTAMP_1970_1_1;
    }
    /**
    <p>Unix时间戳计算</p><br>
    计算当前日期时间的Unix时间戳。<br>
    即当前日期时间自公元1970年1月1日0时0分0秒的毫秒时间戳。
    @return 当前日期时间的Unix时间戳。<br>
    对于纯时间对象，返回自当日0时0分0秒 UTC+0的毫秒时间戳。
    */
    public long timestampUnix() {
        long millisecond=this.MILLISECOND+SECOND*1000L+MINUTE*60000L+(HOUR-TIME_ZONE)*3600000L;
        if(YEAR!=Integer.MIN_VALUE) {
            int monthDayPrefixSum[]=isLeapYear()?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            int day=this.DAY+monthDayPrefixSum[MONTH-1];
            int year=this.YEAR-1;
            millisecond+=((year-1969)*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-478)*86400000L;
        }
        return millisecond;
    }
    /**
    <p>Unix时间戳计算</p><br>
    计算指定日期时间的Unix时间戳。<br>
    即指定日期时间自公元1970年1月1日0时0分0秒的毫秒时间戳。
    @param year 年。
    @param month 月。
    @param day 日。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @param millisecond 毫秒。
    @param timeZone 时区。
    @return 指定日期时间的Unix时间戳。
    */
    public static long timestampUnix(int year,int month,int day,int hour,int minute,int second,int millisecond,int timeZone) {
        long totalMillisecond=millisecond+second*1000L+minute*60000L+(hour-timeZone)*3600000L;
        if(year!=Integer.MIN_VALUE) {
            int monthDayPrefixSum[]=isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            day+=monthDayPrefixSum[month-1];
            year--;
            totalMillisecond+=((year-1969)*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-478)*86400000L;
        }
        return totalMillisecond;
    }
    /**
    <p>现在Unix时间戳</p><br>
    计算现在日期时间的Unix时间戳。<br>
    即现在日期时间自公元1970年1月1日0时0分0秒的毫秒时间戳。
    @return 现在日期时间的Unix时间戳。
    */
    public static long timestampUnixNow() {
        return System.currentTimeMillis();
    }
    /**
    <p>日时间戳计算</p><br>
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间自公元元年1月1日的日时间戳。
    @return 当前日期时间自公元元年1月1日的日时间戳。<br>
    对于纯时间对象，返回<code>Integer.MIN_VALUE</code>。
    */
    public int timestampDay() {
        if(YEAR!=Integer.MIN_VALUE) {
            int monthDayPrefixSum[]=isLeapYear()?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            int day=this.DAY+monthDayPrefixSum[MONTH-1]+Math.floorDiv(HOUR-TIME_ZONE,24);
            int year=this.YEAR-1;
            return year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>日时间戳计算</p><br>
    计算指定日期自公元元年1月1日的日时间戳。
    @param year 年。
    @param month 月。
    @param day 日。
    @return 指定日期自公元元年1月1日的日时间戳。
    */
    public static int timestampDay(int year,int month,int day) {
        int monthDayPrefixSum[]=isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
        day+=monthDayPrefixSum[month-1];
        year--;
        return year*365+Math.floorDiv(year,400)+Math.floorDiv(year,4)-Math.floorDiv(year,100)+day-1;
    }
    /**
    <p>现在日时间戳</p><br>
    计算现在日期自公元元年1月1日的日时间戳。
    @return 现在日期自公元元年1月1日的日时间戳。
    */
    public static int timestampDayNow() {
        return (int)(System.currentTimeMillis()/Calendar.DAY_MILLISECOND)+Calendar.DAY_1970_1_1;
    }
    /**
    <p>闰年判断</p><br>
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    判断当前日期时间的年份是否为闰年。
    @return 是否为闰年。<br>
    对于纯时间对象，返回<code>false</code>。
    */
    public boolean isLeapYear() {
        return YEAR!=Integer.MIN_VALUE&&(YEAR%400==0||YEAR%4==0&&YEAR%100!=0);
    }
    /**
    <p>闰年判断</p><br>
    判断指定年份是否为闰年。
    @param year 年份。
    @return 是否为闰年。
    */
    public static boolean isLeapYear(int year) {
        return year%400==0||year%4==0&&year%100!=0;
    }
    /**
    <p>星期计算</p><br>
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间的星期。
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
    public int weekday() {
        if(YEAR!=Integer.MIN_VALUE) {
            int c=Math.floorDiv(YEAR,100);
            int year=Math.floorMod(this.YEAR,100);
            int month=this.MONTH+(this.MONTH<3?12:0);
            year-=month>12?1:0;
            return Math.floorMod((-2*c+year+Math.floorDiv(c,4)+Math.floorDiv(year,4)+13*(month+1)/5+DAY-1),7);
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>星期计算</p><br>
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
    public static int weekday(int year,int month,int day) {
        int c=Math.floorDiv(year,100);
        year=Math.floorMod(year,100);
        month+=month<3?12:0;
        year-=month>12?1:0;
        return Math.floorMod((-2*c+year+Math.floorDiv(c,4)+Math.floorDiv(year,4)+13*(month+1)/5+day-1),7);
    }
    /**
    <p>已过天数计算</p><br>
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间在该年中的天数。
    @return 当前日期时间在该年中的天数。<br>
    对于纯时间对象，返回<code>Integer.MIN_VALUE</code>。
    */
    public int dayInYear() {
        if(YEAR!=Integer.MIN_VALUE) {
            return DAY+(isLeapYear()?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM[MONTH-1]:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM[MONTH-1]);
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>已过天数计算</p><br>
    计算指定日期在该年中的天数。
    @param year 年。
    @param month 月。
    @param day 日。
    @return 指定日期在该年中的天数。
    */
    public static int dayInYear(int year,int month,int day) {
        return day+(isLeapYear(year)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM[month-1]:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM[month-1]);
    }
    /**
    <p>天数增加</p><br>
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间经过指定天数后的日期时间。
    @param addDay 增加的天数，若为负数则减少天数。
    @return 当前日期时间经过指定天数后的日期时间。<br>
    对于纯时间对象，不做处理，返回<code>this</code>。
    */
    public Datetime addDay(int addDay) {
        if(YEAR!=Integer.MIN_VALUE) {
            int newTimestampDay=timestampDay()+addDay;
            int cycles=(int)Math.floorDiv(newTimestampDay,Calendar.DAY_IN_400_YEARS);
            newTimestampDay=Math.floorMod(newTimestampDay,Calendar.DAY_IN_400_YEARS);
            int newYear=cycles*400+1;
            cycles=newTimestampDay/Calendar.DAY_IN_100_YEARS;
            if(cycles==4) {
                cycles=3;
            }
            newTimestampDay-=cycles*Calendar.DAY_IN_100_YEARS;
            newYear+=cycles*100;
            cycles=newTimestampDay/Calendar.DAY_IN_4_YEARS;
            newTimestampDay-=cycles*Calendar.DAY_IN_4_YEARS;
            newYear+=cycles*4;
            cycles=newTimestampDay/Calendar.DAY_IN_COMMON_YEAR;
            if(cycles==4) {
                cycles=3;
            }
            newTimestampDay-=cycles*Calendar.DAY_IN_COMMON_YEAR;
            newYear+=cycles;
            int monthDay[]=isLeapYear(newYear)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
            int newMonth=1;
            int right=12,left=1;
            while(right>=left) {
                int middle=(left+right)/2;
                if(monthDay[middle]>newTimestampDay&&monthDay[middle-1]<=newTimestampDay) {
                    newMonth=middle;
                    newTimestampDay-=monthDay[newMonth-1];
                    break;
                } else if(monthDay[middle-1]>newTimestampDay) {
                    right=middle-1;
                } else {
                    left=middle+1;
                }
            }
            newTimestampDay++;
            return new Datetime(newYear==0?-1:newYear,newMonth,newTimestampDay,HOUR,MINUTE,SECOND,MILLISECOND,TIME_ZONE);
        } else {
            return this;
        }
    }
    /**
    <p>天数增加</p><br>
    计算指定日期经过指定天数后的日期时间。
    @param year 年。
    @param month 月。
    @param day 日。
    @param addDay 增加的天数，若为负数则减少天数。
    @return 指定日期经过指定天数后的日期时间。
    */
    public static Datetime addDay(int year,int month,int day,int addDay) {
        int newTimestampDay=timestampDay(year,month,day)+addDay;
        int cycles=(int)Math.floorDiv(newTimestampDay,Calendar.DAY_IN_400_YEARS);
        newTimestampDay=Math.floorMod(newTimestampDay,Calendar.DAY_IN_400_YEARS);
        int newYear=cycles*400+1;
        cycles=newTimestampDay/Calendar.DAY_IN_100_YEARS;
        if(cycles==4) {
            cycles=3;
        }
        newTimestampDay-=cycles*Calendar.DAY_IN_100_YEARS;
        newYear+=cycles*100;
        cycles=newTimestampDay/Calendar.DAY_IN_4_YEARS;
        newTimestampDay-=cycles*Calendar.DAY_IN_4_YEARS;
        newYear+=cycles*4;
        cycles=newTimestampDay/Calendar.DAY_IN_COMMON_YEAR;
        if(cycles==4) {
            cycles=3;
        }
        newTimestampDay-=cycles*Calendar.DAY_IN_COMMON_YEAR;
        newYear+=cycles;
        int monthDay[]=isLeapYear(newYear)?Calendar.MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM:Calendar.MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM;
        int newMonth=1;
        int right=12,left=1;
        while(right>=left) {
            int middle=(left+right)/2;
            if(monthDay[middle]>newTimestampDay&&monthDay[middle-1]<=newTimestampDay) {
                newMonth=middle;
                newTimestampDay-=monthDay[newMonth-1];
                break;
            } else if(monthDay[middle-1]>newTimestampDay) {
                right=middle-1;
            } else {
                left=middle+1;
            }
        }
        newTimestampDay++;
        return new Datetime(newYear==0?-1:newYear,newMonth,newTimestampDay,true);
    }
    /**
    <p>日期差</p><br>
    <p>此方法适用于纯日期对象和日期时间对象。</p><br>
    计算当前日期时间与指定日期时间相差的天数。
    @param to 终点日期时间对象。
    @return 当前日期时间与指定日期时间相差的天数。<br>
    若当前日期时间的时间晚于指定日期时间，则返回的天数为负数。<br>
    若当前日期时间或指定日期时间为纯时间对象，返回<code>Long.MIN_VALUE</code>。
    */
    public long intervalDay(Datetime to) {
        if(YEAR!=Integer.MIN_VALUE&&to.YEAR!=Integer.MIN_VALUE) {
            
            return to.timestampDay()-timestampDay();
        } else {
            return Long.MIN_VALUE;
        }
    }
    /**
    <p>日期差</p><br>
    计算两个日期相差的天数。
    @param startYear 起点日期的年。
    @param startMonth 起点日期的月。
    @param startDay 起点日期的日。
    @param endYear 终点日期的年。
    @param endMonth 终点日期的月。
    @param endDay 终点日期的日。
    @return 两个日期相差的天数。<br>
    若起点日期晚于终点日期，则返回的天数为负数。
    */
    public static long intervalDay(int startYear,int startMonth,int startDay,int endYear,int endMonth,int endDay) {
        return timestampDay(endYear,endMonth,endDay)-timestampDay(startYear,startMonth,startDay);
    }
    /**
    <p>日中秒序号</p><br>
    计算当前日期时间在该天中的秒数。
    @return 当前日期时间在该天中的秒数。
    */
    public int secondInDay() {
        return HOUR*3600+MINUTE*60+SECOND;
    }
    /**
    <p>日中秒序号</p><br>
    计算指定时间在该天中的秒数。
    @param hour 时。
    @param minute 分。
    @param second 秒。
    @return 指定时间在该天中的秒数。
    */
    public static int secondInDay(int hour,int minute,int second) {
        return hour*3600+minute*60+second;
    }
    /**
    <p>纯时间差</p><br>
    计算当前日期时间与指定日期时间相差的日间秒数。
    @param to 终点日期时间对象。
    @return 当前日期时间与终点日期时间相差的日间秒数。<br>
    若当前时间晚于终点时间，则返回的日间秒数为负数。<br>
    若当前时区与终点时区不同，则返回的秒数的绝对值可能大于一天的秒数。
    */
    public int intervalSecondInDay(Datetime to) {
        return (to.HOUR-to.TIME_ZONE)*3600+to.MINUTE*60+to.SECOND-(HOUR-TIME_ZONE)*3600-MINUTE*60-SECOND;
    }
    /**
    <p>纯时间差</p><br>
    计算两个时间相差的秒数。
    @param startHour 起点时间的时。
    @param startMinute 起点时间的分。
    @param startSecond 起点时间的秒。
    @param endHour 终点时间的时。
    @param endMinute 终点时间的分。
    @param endSecond 终点时间的秒。
    @return 两个时间相差的秒数。<br>
    若起点时间晚于终点时间，则返回的秒数为负数。
    */
    public static int intervalSecondInDay(int startHour,int startMinute,int startSecond,int endHour,int endMinute,int endSecond) {
        return endHour*3600+endMinute*60+endSecond-startHour*3600-startMinute*60-startSecond;
    }
    /**
    <p>字符串表示</p><br>
    @return 日期时间的字符串表示。<br>
    对于公元元年及之后的日期时间，格式为：<code>AD 年/月/日 时:分:秒.毫秒 UTC+时区</code><br>
    对于公元前年，格式为：<code>年/月/日 BC 时:分:秒.毫秒 UTC+时区</code><br>
    对于纯时间对象，格式为：<code>时:分:秒.毫秒 UTC+时区</code>
    */
    public String toString() {
        if(YEAR!=Integer.MIN_VALUE) {
            if(YEAR>0) {
                return String.format("AD %d/%02d/%02d %02d:%02d:%02d.%03d UTC%s",YEAR,MONTH,DAY,HOUR,MINUTE,SECOND,MILLISECOND,(TIME_ZONE>=0?"+"+TIME_ZONE:""+TIME_ZONE));
            } else {
                return String.format("%d/%02d/%02d BC %02d:%02d:%02d.%03d UTC%s",-YEAR+1,MONTH,DAY,HOUR,MINUTE,SECOND,MILLISECOND,(TIME_ZONE>=0?"+"+TIME_ZONE:""+TIME_ZONE));
            }
        } else {
            return String.format("%02d:%02d:%02d.%03d UTC%s",HOUR,MINUTE,SECOND,MILLISECOND,(TIME_ZONE>=0?"+"+TIME_ZONE:""+TIME_ZONE));
        }
    }
    /**
    <p>比较</p><br>
    比较当前日期时间与指定日期时间。
    @param another 指定日期时间对象。
    @return 当前日期时间与指定日期时间的时间的比较结果。<br>
    若混合比较日期时间对象和纯时间对象，则仅比较时间部分。<br>
    <ul>
        <li>0：当前日期时间与指定日期时间相同。<br>
        <li>&gt;0：当前日期时间晚于指定日期时间。<br>
        <li>&lt;0：当前日期时间早于指定日期时间。<br>
    </ul>
    */
    public int compareTo(Datetime another) {
        if(YEAR!=Integer.MIN_VALUE&&another.YEAR!=Integer.MIN_VALUE||YEAR==Integer.MIN_VALUE&&another.YEAR==Integer.MIN_VALUE) {
            long difference=timestamp()-another.timestamp();
            return difference>0?1:difference==0?0:-1;
        } else {
            return (HOUR-TIME_ZONE)*3600000+MINUTE*60000+SECOND*1000+MILLISECOND-(another.HOUR-another.TIME_ZONE)*3600000-another.MINUTE*60000-another.SECOND*1000-another.MILLISECOND;
        }
    }
}