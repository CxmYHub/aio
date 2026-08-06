package aio.collection;
/**
<p>字符串工具类</p><br>
用于对字符串进行操作。
*/
public class string
{
    /**
    <p>字符串反转</p><br>
    计算一个字符串的反转字符串。
    @param string 字符串。
    @return 反转的字符串。
    */
    public static String reverse(String string)
    {
        char char_string[]=string.toCharArray();
        int length=char_string.length;
        int half_length=length/2;
        char temp;
        for(int i=0;i<half_length;i++)
        {
            temp=char_string[i];
            char_string[i]=char_string[length-1-i];
            char_string[length-1-i]=temp;
        }
        return new String(char_string);
    }
    /**
    <p>字符串包含判断</p><br>
    判断一个字符串中是否包含另一个字符串。
    @param base 基字符串。
    @param pattern 模式字符串。
    @return 是否包含。
    */
    public static boolean contains(String base,String pattern)
    {
        int base_length=base.length();
        int pattern_length=pattern.length();
        if(base_length<pattern_length)
        {
            return false;
        }
        char char_base[]=base.toCharArray();
        char char_pattern[]=pattern.toCharArray();
        int next[]=new int[pattern_length];
        next[0]=-1;
        int pin=-1;
        for(int i=0;i<pattern_length-1;)
        {
            if(pin==-1||char_pattern[pin]==char_pattern[i])
            {
                pin++;
                i++;
                next[i]=char_pattern[pin]==char_pattern[i]?next[pin]:pin;
            }
            else
            {
                pin=next[pin];
            }
        }
        pin=0;
        for(int i=0;i<base_length&&pin<pattern_length;)
        {
            if(pin==-1||char_pattern[pin]==char_base[i])
            {
                pin++;
                i++;
            }
            else
            {
                pin=next[pin];
            }
        }
        return pin==pattern_length;
    }
    /**
    <p>回文串判断</p><br>
    判断一个字符串是否为回文字符串。
    @param string 字符串。
    @return 是否为回文字符串。
    */
    public static boolean is_palindrome(String string)
    {
        boolean is_palindrome=true;
        int length_2=string.length()/2;
        for(int i=0;i<length_2;i++)
        {
            if(string.charAt(i)!=string.charAt(string.length()-1-i))
            {
                is_palindrome=false;
                break;
            }
        }
        return is_palindrome;
    }
    /**
    <p>最长回文子串</p><br>
    计算一个字符串的最长回文子串。
    @param string 字符串。
    @return 最长回文子串。
    */
    public static String longest_palindrome(String string)
    {
        if(string==null)
        {
            return null;
        }
        else if(string.length()==0)
        {
            return "";
        }
        int length=string.length();
        char char_string[]=new char[length*2+3];
        char_string[0]='*';
        char_string[1]='#';
        for(int i=0;i<length;i++)
        {
            char_string[i*2+2]=string.charAt(i);
            char_string[i*2+3]='#';
        }
        length=length*2+3;
        char_string[length-1]='&';
        int radius[]=new int[length];
        int max_right=0;
        int right_center=0;
        int max_length=-1;
        int max_center=-1;
        length--;
        for(int i=1;i<length;i++)
        {
            radius[i]=i>max_right?1:Math.min(radius[2*right_center-i],max_right-i);
            while(char_string[i+radius[i]]==char_string[i-radius[i]])
            {
                radius[i]++;
            }
            if(i+radius[i]>max_right)
            {
                max_right=i+radius[i];
                right_center=i;
            }
            if(radius[i]-1>max_length)
            {
                max_length=radius[i]-1;
                max_center=i;
            }
        }
        return string.substring((max_center-max_length)/2,(max_center+max_length)/2);
    }
    /**
    <p>最长回文子串长度</p><br>
    计算一个字符串的最长回文子串的长度。
    @param string 字符串。
    @return 最长回文子串的长度。
    */
    public static int longest_palindrome_length(String string)
    {
        if(string==null)
        {
            return -1;
        }
        else if(string.length()==0)
        {
            return 0;
        }
        int length=string.length();
        char char_string[]=new char[length*2+3];
        char_string[0]='*';
        char_string[1]='#';
        for(int i=0;i<length;i++)
        {
            char_string[i*2+2]=string.charAt(i);
            char_string[i*2+3]='#';
        }
        length=length*2+3;
        char_string[length-1]='&';
        int radius[]=new int[length];
        int max_right=0;
        int right_center=0;
        int max_length=-1;
        length--;
        for(int i=1;i<length;i++)
        {
            radius[i]=i>max_right?1:Math.min(radius[2*right_center-i],max_right-i);
            while(char_string[i+radius[i]]==char_string[i-radius[i]])
            {
                radius[i]++;
            }
            if(i+radius[i]>max_right)
            {
                max_right=i+radius[i];
                right_center=i;
            }
            if(radius[i]-1>max_length)
            {
                max_length=radius[i]-1;
            }
        }
        return max_length;
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
    public static boolean match_regular_expression(String string,String expression)
    {
        char char_string[]=string.toCharArray();
        char char_expression[]=expression.toCharArray();
        int length_string=char_string.length;
        int length_expression=char_expression.length;
        boolean match[][]=new boolean[length_string+1][length_expression+1];
        match[0][0]=true;
        for(int j=0;j<length_expression;j++)
        {
            match[0][j+1]=char_expression[j]=='*'&&match[0][j-1];
        }
        for(int i=0;i<length_string;i++)
        {
            char this_char=char_string[i];
            for(int j=0;j<length_expression;j++)
            {
                char this_expression=char_expression[j];
                if(this_expression=='*')
                {
                    char before_star=char_expression[j-1];
                    match[i+1][j+1]=match[i+1][j-1];
                    if(before_star==this_char||before_star=='.')
                    {
                        match[i+1][j+1]=match[i+1][j+1]||match[i][j+1];
                    }
                }
                else if(this_char==this_expression||char_expression[j]=='.')
                {
                    match[i+1][j+1]=match[i][j];
                }
            }
        }
        return match[length_string][length_expression];
    }
}