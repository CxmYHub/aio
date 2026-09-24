package aio.collection;
/**
<p>字符串工具类</p><br>
用于对字符串进行操作。
*/
public class Strings {
    /**
    <p>字符串反转</p><br>
    计算一个字符串的反转字符串。
    @param string 字符串。
    @return 反转的字符串。
    */
    public static String reverse(String string) {
        char charString[]=string.toCharArray();
        int length=charString.length;
        int halfLength=length/2;
        char temp;
        for(int i=0;i<halfLength;i++) {
            temp=charString[i];
            charString[i]=charString[length-1-i];
            charString[length-1-i]=temp;
        }
        return new String(charString);
    }
    /**
    <p>字符串包含查找</p><br>
    查找一个字符串中，另一个字符串的第一个起始索引。
    @param base 基字符串。
    @param pattern 模式字符串。
    @return 基字符串中，模式字符串的第一个起始索引。<br>
    若模式字符串不存在于基字符串中，则返回-1。
    */
    public static int containingIndex(String base,String pattern) {
        int baseLength=base.length();
        int patternLength=pattern.length();
        if(baseLength<patternLength) {
            return -1;
        }
        char charBase[]=base.toCharArray();
        char charPattern[]=pattern.toCharArray();
        int next[]=new int[patternLength];
        next[0]=-1;
        int pin=-1;
        for(int i=0;i<patternLength-1;) {
            if(pin==-1||charPattern[pin]==charPattern[i]) {
                pin++;
                i++;
                next[i]=charPattern[pin]==charPattern[i]?next[pin]:pin;
            } else {
                pin=next[pin];
            }
        }
        pin=0;
        int i=0;
        while(i<baseLength&&pin<patternLength) {
            if(pin==-1||charPattern[pin]==charBase[i]) {
                pin++;
                i++;
            } else {
                pin=next[pin];
            }
        }
        return pin==patternLength?i-patternLength:-1;
    }
    /**
    <p>字符串包含计数</p><br>
    计算一个字符串中，另一个字符串的出现次数。
    @param base 基字符串。
    @param pattern 模式字符串。
    @return 基字符串中，模式字符串的出现次数。
    */
    public static int containingCount(String base,String pattern) {
        int baseLength=base.length();
        int patternLength=pattern.length();
        if(baseLength<patternLength) {
            return -1;
        }
        char charBase[]=base.toCharArray();
        char charPattern[]=pattern.toCharArray();
        int next[]=new int[patternLength];
        next[0]=-1;
        int pin=-1;
        for(int i=0;i<patternLength-1;) {
            if(pin==-1||charPattern[pin]==charPattern[i]) {
                pin++;
                i++;
                next[i]=charPattern[pin]==charPattern[i]?next[pin]:pin;
            } else {
                pin=next[pin];
            }
        }
        pin=0;
        int count=0;
        for(int i=0;i<baseLength;) {
            if(pin==-1||charPattern[pin]==charBase[i]) {
                pin++;
                i++;
                if(pin>=patternLength) {
                    count++;
                    pin=0;
                }
            } else {
                pin=next[pin];
            }
        }
        return count;
    }
    /**
    <p>回文串判断</p><br>
    判断一个字符串是否为回文字符串。
    @param string 字符串。
    @return 是否为回文字符串。
    */
    public static boolean isPalindrome(String string) {
        boolean isPalindrome=true;
        int length2=string.length()/2;
        for(int i=0;i<length2;i++) {
            if(string.charAt(i)!=string.charAt(string.length()-1-i)) {
                isPalindrome=false;
                break;
            }
        }
        return isPalindrome;
    }
    /**
    <p>最长回文子串</p><br>
    计算一个字符串的最长回文子串。
    @param string 字符串。
    @return 最长回文子串。
    */
    public static String longestPalindrome(String string) {
        if(string==null) {
            return null;
        } else if(string.length()==0) {
            return "";
        }
        int length=string.length();
        char charString[]=new char[length*2+3];
        charString[0]='*';
        charString[1]='#';
        for(int i=0;i<length;i++) {
            charString[i*2+2]=string.charAt(i);
            charString[i*2+3]='#';
        }
        length=length*2+3;
        charString[length-1]='&';
        int radius[]=new int[length];
        int maxRight=0;
        int rightCenter=0;
        int maxLength=-1;
        int maxCenter=-1;
        length--;
        for(int i=1;i<length;i++) {
            radius[i]=i>maxRight?1:Math.min(radius[2*rightCenter-i],maxRight-i);
            while(charString[i+radius[i]]==charString[i-radius[i]]) {
                radius[i]++;
            }
            if(i+radius[i]>maxRight) {
                maxRight=i+radius[i];
                rightCenter=i;
            }
            if(radius[i]-1>maxLength) {
                maxLength=radius[i]-1;
                maxCenter=i;
            }
        }
        return string.substring((maxCenter-maxLength)/2,(maxCenter+maxLength)/2);
    }
    /**
    <p>最长回文子串长度</p><br>
    计算一个字符串的最长回文子串的长度。
    @param string 字符串。
    @return 最长回文子串的长度。
    */
    public static int longestPalindromeLength(String string) {
        if(string==null) {
            return -1;
        } else if(string.length()==0) {
            return 0;
        }
        int length=string.length();
        char charString[]=new char[length*2+3];
        charString[0]='*';
        charString[1]='#';
        for(int i=0;i<length;i++) {
            charString[i*2+2]=string.charAt(i);
            charString[i*2+3]='#';
        }
        length=length*2+3;
        charString[length-1]='&';
        int radius[]=new int[length];
        int maxRight=0;
        int rightCenter=0;
        int maxLength=-1;
        length--;
        for(int i=1;i<length;i++) {
            radius[i]=i>maxRight?1:Math.min(radius[2*rightCenter-i],maxRight-i);
            while(charString[i+radius[i]]==charString[i-radius[i]]) {
                radius[i]++;
            }
            if(i+radius[i]>maxRight) {
                maxRight=i+radius[i];
                rightCenter=i;
            }
            if(radius[i]-1>maxLength) {
                maxLength=radius[i]-1;
            }
        }
        return maxLength;
    }
    /**
    <p>正则表达式匹配</p><br>
    判断一个字符串是否匹配一个正则表达式。
    @param string 字符串。
    @param expression 正则表达式。<br>
    支持的字符：<br>
    <ul>
        <li>. 匹配任意单个字符。</li>
        <li>* 匹配零次或多次前一个字符。</li>
    </ul>
    @return 是否匹配。
    */
    public static boolean matchRegularExpression(String string,String expression) {
        if(expression.charAt(0)=='*') {
            return false;
        }
        char charString[]=string.toCharArray();
        char charExpression[]=expression.toCharArray();
        int lengthString=charString.length;
        int lengthExpression=charExpression.length;
        boolean match[][]=new boolean[lengthString+1][lengthExpression+1];
        match[0][0]=true;
        for(int j=0;j<lengthExpression;j++) {
            match[0][j+1]=charExpression[j]=='*'&&match[0][j-1];
        }
        for(int i=0;i<lengthString;i++) {
            char thisChar=charString[i];
            for(int j=0;j<lengthExpression;j++) {
                char thisExpression=charExpression[j];
                if(thisExpression=='*') {
                    char beforeStar=charExpression[j-1];
                    match[i+1][j+1]=match[i+1][j-1];
                    if(beforeStar==thisChar||beforeStar=='.') {
                        match[i+1][j+1]=match[i+1][j+1]||match[i][j+1];
                    }
                } else if(thisChar==thisExpression||charExpression[j]=='.') {
                    match[i+1][j+1]=match[i][j];
                }
            }
        }
        return match[lengthString][lengthExpression];
    }
}