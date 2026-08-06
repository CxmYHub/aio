package aio.data_structure;
/**
<p>字典树类</p><br>
字典树是一种用于存储字符串的树状结构。<br>
本字典树以边的形式存储字母（不区分大小写）。<br>
结点则存储入边是否为本字符串的结束。<br>
不支持空字符串。
*/
public class trie
{
    /**
    <p>结点深度</p>
    */
    public int depth;
    /**
    <p>结点是否为字符串结束</p>
    */
    public boolean is_end;
    /**
    <p>子结点指针</p>
    */
    public trie children[]=new trie[26];
    /**
    <p>结点构造方法</p><br>
    构造一个指定深度的字典树结点。
    @param depth 结点的深度。
    */
    public trie(int depth)
    {
        this.depth=depth;
        is_end=false;
    }
    /**
    <p>无参结点构造方法</p><br>
    构造一个深度为1的字典树。<br><br>
    注意本方法构造的是一个新的字典树。<br>
    包含1个结点，其深度为1。
    */
    public trie()
    {
        depth=1;
        is_end=false;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含所有指定字符串的字典树。
    @param words 多个字符串。
    */
    public trie(String... words)
    {
        for(int i=0;i<words.length;i++)
        {
            if(words[i].length()==0)
            {
                continue;
            }
            char char_word[]=words[i].toLowerCase().toCharArray();
            trie now=this;
            for(int j=0;j<char_word.length;j++)
            {
                int now_char=char_word[j]-'a';
                int depth=char_word.length-j+1;
                if(now.children[now_char]==null)
                {
                    now.children[now_char]=new trie();
                    now.depth=depth>now.depth?depth:now.depth;
                }
                else
                {
                    int now_depth=now.depth;
                    now.depth=depth>now_depth?depth:now_depth;
                }
                now=now.children[now_char];
            }
            now.is_end=true;
        }
    }
    /**
    <p>字符串计数</p><br>
    计算字典树中字符串数量。
    @return 字符串的数量。
    */
    public int count()
    {
        trie pins[]=new trie[16];
        int pin=1,capacity=16;
        pins[0]=this;
        int count=0;
        while(pin>0)
        {
            trie now=pins[--pin];
            count+=now.is_end?1:0;
            for(int i=0;i<26;i++)
            {
                if(now.children[i]!=null)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        trie new_pins[]=new trie[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now.children[i];
                }
            }
        }
        return count;
    }
    /**
    <p>树深度计算</p><br>
    计算字典树的深度。<br>
    最长字符串长度=深度-1。
    @return 深度。
    */
    public int depth()
    {
        return depth;
    }
    /**
    <p>最长字符串长度计算</p><br>
    计算字典树最长字符串的长度。<br>
    最长字符串长度=深度-1。
    @return 最长字符串长度。<br>
    即字典树的深度-1。
    */
    public int max_length()
    {
        return depth-1;
    }
    /**
    <p>字符串输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向字典树中添加一个字符串。
    @param word 字符串。
    @return 新增的结点个数。<br>
    若字符串已存在，或字符串为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input(String word)
    {
        if(word.length()==0)
        {
            return Integer.MIN_VALUE;
        }
        char char_word[]=word.toLowerCase().toCharArray();
        trie now=this;
        int count=0;
        for(int j=0;j<char_word.length;j++)
        {
            int now_char=char_word[j]-'a';
            int depth=char_word.length-j+1;
            if(now.children[now_char]==null)
            {
                now.children[now_char]=new trie();
                now.depth=depth>now.depth?depth:now.depth;
                count++;
            }
            else
            {
                int now_depth=now.depth;
                now.depth=depth>now_depth?depth:now_depth;
            }
            now=now.children[now_char];
        }
        if(now.is_end)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            now.is_end=true;
            return count;
        }
    }
    /**
    <p>字符串批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向字典树中添加多个字符串。
    @param words 多个字符串。
    @return 新增的字符串的个数。
    */
    public int input_more(String... words)
    {
        int count=0;
        for(int i=0;i<words.length;i++)
        {
            if(words[i].length()==0)
            {
                continue;
            }
            char char_word[]=words[i].toLowerCase().toCharArray();
            trie now=this;
            for(int j=0;j<char_word.length;j++)
            {
                int now_char=char_word[j]-'a';
                int depth=char_word.length-j+1;
                if(now.children[now_char]==null)
                {
                    now.children[now_char]=new trie();
                    now.depth=depth>now.depth?depth:now.depth;
                }
                else
                {
                    int now_depth=now.depth;
                    now.depth=depth>now_depth?depth:now_depth;
                }
                now=now.children[now_char];
            }
            if(!now.is_end)
            {
                now.is_end=true;
                count++;
            }
        }
        return count;
    }
    /**
    <p>字符串存在性判断</p><br>
    判断字典树中是否存在指定字符串。
    @param word 字符串。
    @return 是否存在。
    */
    public boolean exist(String word)
    {
        char char_word[]=word.toLowerCase().toCharArray();
        trie now=this;
        for(int j=0;j<char_word.length;j++)
        {
            int now_char=char_word[j]-'a';
            if(now.children[now_char]==null)
            {
                return false;
            }
            now=now.children[now_char];
        }
        return now.is_end;
    }
    /**
    <p>字符串获取（所有字符串）</p><br>
    获取字典树中所有的字符串。
    @return 所有字符串的数组。
    */
    public String[] get_all_words()
    {
        trie pins[]=new trie[16];
        char letters[]=new char[16];
        int pin=0,capacity=16;
        pins[0]=this;
        String result[]=new String[count()];
        int count=0;
        while(pin>=0)
        {
            trie now=pins[pin];
            int next=letters[pin]-'a';
            for(next=(next<0?0:next+1);next<26&&now.children[next]==null;next++);
            if(next<26)
            {
                letters[pin]=(char)(next+'a');
                now=now.children[next];
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    trie new_pins[]=new trie[capacity];
                    char new_letters[]=new char[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    System.arraycopy(letters,0,new_letters,0,pin);
                    pins=new_pins;
                    letters=new_letters;
                }
                pins[++pin]=now;
                if(now.is_end)
                {
                    result[count++]=new String(letters,0,pin);
                }
            }
            else
            {
                letters[pin--]=0;
            }
        }
        return result;
    }
    /**
    <p>字符串删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    从字典树中删除一个字符串。
    @param word 字符串。
    @return 是否成功删除。
    */
    public boolean remove(String word)
    {
        char char_word[]=word.toLowerCase().toCharArray();
        int length=char_word.length;
        trie pins[]=new trie[16];
        int max_besides_depth[]=new int[16];
        int pin=0,capacity=16;
        trie now=this;
        int max_depth=0;
        for(int i=0;i<length;i++)
        {
            int now_char=char_word[i]-'a';
            max_depth=0;
            for(int j=0;j<now_char;j++)
            {
                trie now_beside=now.children[j];
                if(now_beside!=null)
                {
                    max_depth=max_depth>now_beside.depth?max_depth:now_beside.depth;
                }
            }
            for(int j=now_char+1;j<26;j++)
            {
                trie now_beside=now.children[j];
                if(now_beside!=null)
                {
                    max_depth=max_depth>now_beside.depth?max_depth:now_beside.depth;
                }
            }
            if(now.children[now_char]==null)
            {
                return false;
            }
            if(pin>=capacity)
            {
                capacity=(capacity<<1)+2;
                trie new_pins[]=new trie[capacity];
                int new_max_besides_depth[]=new int[capacity];
                System.arraycopy(pins,0,new_pins,0,pin);
                System.arraycopy(max_besides_depth,0,new_max_besides_depth,0,pin);
                pins=new_pins;
                max_besides_depth=new_max_besides_depth;
            }
            pins[pin]=now;
            max_besides_depth[pin++]=max_depth;
            now=now.children[now_char];
        }
        if(now.is_end)
        {
            if(now.depth==1)
            {
                for(pin--;pin>0&&max_besides_depth[pin]==0&&!pins[pin].is_end;pin--);
                now=pins[pin];
                now.children[char_word[pin]-'a']=null;
                for(int i=1;pin>=0;i++,pin--)
                {
                    now=pins[pin];
                    max_depth=max_besides_depth[pin]+1;
                    if(max_depth>i)
                    {
                        now.depth=max_depth;
                        i=max_depth;
                    }
                    else
                    {
                        now.depth=i;
                    }
                }
            }
            else
            {
                now.is_end=false;
            }
            return true;
        }
        return false;
    }
    /**
    <p>导出字符串</p><br>
    将字典树中所有的字符串转换为一个字符串。<br>
    整体用大括号括起，每个字符串之间用逗号隔开。<br>
    例如：<code>{"abc","abd","def"}</code>
    @return 包含所有字符串的字符串。
    */
    public String toString()
    {
        trie pins[]=new trie[16];
        char letters[]=new char[16];
        int pin=0,capacity=16;
        pins[0]=this;
        StringBuilder result=new StringBuilder("{");
        while(pin>=0)
        {
            trie now=pins[pin];
            int next=letters[pin]-'a';
            for(next=(next<0?0:next+1);next<26&&now.children[next]==null;next++);
            if(next<26)
            {
                letters[pin]=(char)(next+'a');
                now=now.children[next];
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    trie new_pins[]=new trie[capacity];
                    char new_letters[]=new char[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    System.arraycopy(letters,0,new_letters,0,pin);
                    pins=new_pins;
                    letters=new_letters;
                }
                pins[++pin]=now;
                if(now.is_end)
                {
                    result.append(new String(letters,0,pin)+",");
                }
            }
            else
            {
                letters[pin--]=0;
            }
        }
        return result.delete(result.length()-1,result.length()).append("}").toString();
    }
}