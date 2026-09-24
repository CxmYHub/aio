package aio.datastructure;
/**
<p>字典树类</p><br>
字典树是一种用于存储字符串的树状结构。<br>
本字典树以边的形式存储字母（不区分大小写）。<br>
结点则存储入边是否为本字符串的结束。<br>
不支持空字符串。
*/
public class Trie {
    /**
    <p>结点深度</p>
    */
    public int depth;
    /**
    <p>结点是否为字符串结束</p>
    */
    public boolean isEnd;
    /**
    <p>子结点指针</p>
    */
    public Trie children[]=new Trie[26];
    /**
    <p>结点构造方法</p><br>
    构造一个指定深度的字典树结点。
    @param depth 结点的深度。
    */
    public Trie(int depth) {
        this.depth=depth;
        isEnd=false;
    }
    /**
    <p>无参结点构造方法</p><br>
    构造一个深度为1的字典树。<br><br>
    注意本方法构造的是一个新的字典树。<br>
    包含1个结点，其深度为1。
    */
    public Trie() {
        depth=1;
        isEnd=false;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含所有指定字符串的字典树。
    @param words 多个字符串。
    */
    public Trie(String... words) {
        for(int i=0;i<words.length;i++) {
            if(words[i].length()==0) {
                continue;
            }
            char charWord[]=words[i].toLowerCase().toCharArray();
            Trie now=this;
            for(int j=0;j<charWord.length;j++) {
                int nowChar=charWord[j]-'a';
                int depth=charWord.length-j+1;
                if(now.children[nowChar]==null) {
                    now.children[nowChar]=new Trie();
                    now.depth=depth>now.depth?depth:now.depth;
                } else {
                    int nowDepth=now.depth;
                    now.depth=depth>nowDepth?depth:nowDepth;
                }
                now=now.children[nowChar];
            }
            now.isEnd=true;
        }
    }
    /**
    <p>字符串计数</p><br>
    计算字典树中字符串数量。
    @return 字符串的数量。
    */
    public int count() {
        Trie pins[]=new Trie[16];
        int pin=1,capacity=16;
        pins[0]=this;
        int count=0;
        while(pin>0) {
            Trie now=pins[--pin];
            count+=now.isEnd?1:0;
            for(int i=0;i<26;i++) {
                if(now.children[i]!=null) {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        Trie newPins[]=new Trie[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
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
    public int depth() {
        return depth;
    }
    /**
    <p>最长字符串长度计算</p><br>
    计算字典树最长字符串的长度。<br>
    最长字符串长度=深度-1。
    @return 最长字符串长度。<br>
    即字典树的深度-1。
    */
    public int maxLength() {
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
    public int input(String word) {
        if(word.length()==0) {
            return Integer.MIN_VALUE;
        }
        char charWord[]=word.toLowerCase().toCharArray();
        Trie now=this;
        int count=0;
        for(int j=0;j<charWord.length;j++) {
            int nowChar=charWord[j]-'a';
            int depth=charWord.length-j+1;
            if(now.children[nowChar]==null) {
                now.children[nowChar]=new Trie();
                now.depth=depth>now.depth?depth:now.depth;
                count++;
            } else {
                int nowDepth=now.depth;
                now.depth=depth>nowDepth?depth:nowDepth;
            }
            now=now.children[nowChar];
        }
        if(now.isEnd) {
            return Integer.MIN_VALUE;
        } else {
            now.isEnd=true;
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
    public int inputMore(String... words) {
        int count=0;
        for(int i=0;i<words.length;i++) {
            if(words[i].length()==0) {
                continue;
            }
            char charWord[]=words[i].toLowerCase().toCharArray();
            Trie now=this;
            for(int j=0;j<charWord.length;j++) {
                int nowChar=charWord[j]-'a';
                int depth=charWord.length-j+1;
                if(now.children[nowChar]==null) {
                    now.children[nowChar]=new Trie();
                    now.depth=depth>now.depth?depth:now.depth;
                } else {
                    int nowDepth=now.depth;
                    now.depth=depth>nowDepth?depth:nowDepth;
                }
                now=now.children[nowChar];
            }
            if(!now.isEnd) {
                now.isEnd=true;
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
    public boolean exist(String word) {
        char charWord[]=word.toLowerCase().toCharArray();
        Trie now=this;
        for(int j=0;j<charWord.length;j++) {
            int nowChar=charWord[j]-'a';
            if(now.children[nowChar]==null) {
                return false;
            }
            now=now.children[nowChar];
        }
        return now.isEnd;
    }
    /**
    <p>字符串获取（所有字符串）</p><br>
    获取字典树中所有的字符串。
    @return 所有字符串的数组。
    */
    public String[] getAllWords() {
        Trie pins[]=new Trie[16];
        char letters[]=new char[16];
        int pin=0,capacity=16;
        pins[0]=this;
        String result[]=new String[count()];
        int count=0;
        while(pin>=0) {
            Trie now=pins[pin];
            int next=letters[pin]-'a';
            for(next=(next<0?0:next+1);next<26&&now.children[next]==null;next++);
            if(next<26) {
                letters[pin]=(char)(next+'a');
                now=now.children[next];
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Trie newPins[]=new Trie[capacity];
                    char newLetters[]=new char[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    System.arraycopy(letters,0,newLetters,0,pin);
                    pins=newPins;
                    letters=newLetters;
                }
                pins[++pin]=now;
                if(now.isEnd) {
                    result[count++]=new String(letters,0,pin);
                }
            } else {
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
    public boolean remove(String word) {
        char charWord[]=word.toLowerCase().toCharArray();
        int length=charWord.length;
        Trie pins[]=new Trie[16];
        int maxBesidesDepth[]=new int[16];
        int pin=0,capacity=16;
        Trie now=this;
        int maxDepth=0;
        for(int i=0;i<length;i++) {
            int nowChar=charWord[i]-'a';
            maxDepth=0;
            for(int j=0;j<nowChar;j++) {
                Trie nowBeside=now.children[j];
                if(nowBeside!=null) {
                    maxDepth=maxDepth>nowBeside.depth?maxDepth:nowBeside.depth;
                }
            }
            for(int j=nowChar+1;j<26;j++) {
                Trie nowBeside=now.children[j];
                if(nowBeside!=null) {
                    maxDepth=maxDepth>nowBeside.depth?maxDepth:nowBeside.depth;
                }
            }
            if(now.children[nowChar]==null) {
                return false;
            }
            if(pin>=capacity) {
                capacity=(capacity<<1)+2;
                Trie newPins[]=new Trie[capacity];
                int newMaxBesidesDepth[]=new int[capacity];
                System.arraycopy(pins,0,newPins,0,pin);
                System.arraycopy(maxBesidesDepth,0,newMaxBesidesDepth,0,pin);
                pins=newPins;
                maxBesidesDepth=newMaxBesidesDepth;
            }
            pins[pin]=now;
            maxBesidesDepth[pin++]=maxDepth;
            now=now.children[nowChar];
        }
        if(now.isEnd) {
            if(now.depth==1) {
                for(pin--;pin>0&&maxBesidesDepth[pin]==0&&!pins[pin].isEnd;pin--);
                now=pins[pin];
                now.children[charWord[pin]-'a']=null;
                for(int i=1;pin>=0;i++,pin--) {
                    now=pins[pin];
                    maxDepth=maxBesidesDepth[pin]+1;
                    if(maxDepth>i) {
                        now.depth=maxDepth;
                        i=maxDepth;
                    } else {
                        now.depth=i;
                    }
                }
            } else {
                now.isEnd=false;
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
    public String toString() {
        Trie pins[]=new Trie[16];
        char letters[]=new char[16];
        int pin=0,capacity=16;
        pins[0]=this;
        StringBuilder result=new StringBuilder("{");
        while(pin>=0) {
            Trie now=pins[pin];
            int next=letters[pin]-'a';
            for(next=(next<0?0:next+1);next<26&&now.children[next]==null;next++);
            if(next<26) {
                letters[pin]=(char)(next+'a');
                now=now.children[next];
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Trie newPins[]=new Trie[capacity];
                    char newLetters[]=new char[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    System.arraycopy(letters,0,newLetters,0,pin);
                    pins=newPins;
                    letters=newLetters;
                }
                pins[++pin]=now;
                if(now.isEnd) {
                    result.append(new String(letters,0,pin)+",");
                }
            } else {
                letters[pin--]=0;
            }
        }
        return result.delete(result.length()-1,result.length()).append("}").toString();
    }
}