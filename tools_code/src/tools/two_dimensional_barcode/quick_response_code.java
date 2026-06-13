package tools.two_dimensional_barcode;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
/**
<p>二维码类。</p><br>
用于表示、生成和解析二维码。<br>
*/
public class quick_response_code
{
    public boolean field[][];
    public int side=21;
    public int version=1;
    public int error_correction_level=1;
    public int mode=2;
    public String encoded_text;
    /**
    通过文本、编码模式、版本号和纠错等级构造二维码。
    @param text 要编码的文本。
    @param mode 编码模式。<br>
    0:数字模式<br>
    1:数字字母模式<br>
    2:字节模式<br>
    3:日文模式<br>
    4:扩展解释模式（需要使用字节模式掩码）
    @param version 版本。<br>
    版本∈[1,40]，对应的二维码边长为<code>(version-1)*4+21</code>。
    @param error_correction_level 纠错等级。<br>
    1:L 低纠错等级(7%)<br>
    2:M 中纠错等级(15%)<br>
    3:Q 高纠错等级(25%)<br>
    4:H 超高纠错等级(30%)
    */
    public quick_response_code(String text,int mode,int version,int error_correction_level)
    {
        this.version=version;
        if(version<1||version>40)
        {
            this.version=-1;
            return;
        }
        this.error_correction_level=error_correction_level;
        encoded_text=text;
        int code_length=text.length();
        if(mode>=2)
        {
            try
            {
                code_length=text.getBytes("UTF-8").length;
            }
            catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
                this.version=-1;
                return;
            }
        }
        byte mode_header_binary=(byte)meta.mode_mask[mode];
        short character_count_header_binary=(short)(code_length);
        int data_byte_count=meta.data_code_word_count[version][error_correction_level];
        boolean data[]=new boolean[data_byte_count<<3];
        int data_pin=0;
        int header_bit_count=0;
        if(mode==4)
        {
            short eci_header_binary=(short)(7<<8|26);
            header_bit_count+=12;
            for(;data_pin<header_bit_count;data_pin++)
            {
                data[data_pin]=(eci_header_binary>>(header_bit_count-1-data_pin)&1)==1;
            }
        }
        header_bit_count+=4;
        for(;data_pin<header_bit_count;data_pin++)
        {
            data[data_pin]=(mode_header_binary>>(header_bit_count-1-data_pin)&1)==1;
        }
        header_bit_count+=meta.code_length_bit_count[version][mode];
        for(;data_pin<header_bit_count;data_pin++)
        {
            data[data_pin]=(character_count_header_binary>>(header_bit_count-1-data_pin)&1)==1;
        }
        switch(mode)
        {
            case 0->
            {
                int i=2;
                int number_group=0,group_bit_movement=-1;
                for(;i<code_length;i+=3)
                {
                    number_group=(text.charAt(i-2)-'0')*100+(text.charAt(i-1)-'0')*10+(text.charAt(i)-'0');
                    for(group_bit_movement=9;group_bit_movement>=0;group_bit_movement--)
                    {
                        data[data_pin++]=(number_group>>(group_bit_movement)&1)==1;
                    }
                }
                switch(i-code_length)
                {
                    case 0->
                    {
                        number_group=(text.charAt(i-2)-'0')*10+(text.charAt(i-1)-'0');
                        group_bit_movement=6;
                    }
                    case 1->
                    {
                        number_group=text.charAt(i-2)-'0';
                        group_bit_movement=3;
                    }
                    default->
                    {
                        number_group=0;
                        group_bit_movement=-1;
                    }
                }
                for(;group_bit_movement>=0;group_bit_movement--)
                {
                    data[data_pin++]=(number_group>>(group_bit_movement)&1)==1;
                }
            }
            case 1->
            {
                int i=1;
                int alphanumeric_group=0,group_bit_movement=-1;
                for(;i<code_length;i+=2)
                {
                    alphanumeric_group=meta.alphanumeric_table[text.charAt(i-1)]*45+meta.alphanumeric_table[text.charAt(i)];
                    for(group_bit_movement=10;group_bit_movement>=0;group_bit_movement--)
                    {
                        data[data_pin++]=(alphanumeric_group>>(group_bit_movement)&1)==1;
                    }
                }
                if(i==code_length)
                {
                    alphanumeric_group=meta.alphanumeric_table[text.charAt(code_length-1)];
                    for(group_bit_movement=5;group_bit_movement>=0;group_bit_movement--)
                    {
                        data[data_pin++]=(alphanumeric_group>>(group_bit_movement)&1)==1;
                    }
                }
            }
            default->
            {
                try
                {
                    byte text_byte[]=text.getBytes("UTF-8");
                    for(int i=0;i<code_length;i++)
                    {
                        byte now=text_byte[i];
                        for(int j=0;j<8;j++)
                        {
                            data[data_pin++]=(now>>(7-j)&1)==1;
                        }
                    }
                }
                catch(UnsupportedEncodingException e)
                {
                    System.out.println("字符集UTF-8不受支持。");
                }
            }
        }
        for(int i=1;data_pin<data.length&&i<=4;data_pin++,i++)
        {
            data[data_pin]=false;
        }
        for(;data_pin<data.length&&data_pin%8>0;data_pin++)
        {
            data[data_pin]=false;
        }
        for(boolean is_236=true;data_pin<data.length;data_pin+=8,is_236=!is_236)
        {
            if(is_236)
            {
                data[data_pin]=true;
                data[data_pin+1]=true;
                data[data_pin+2]=true;
                data[data_pin+3]=false;
                data[data_pin+4]=true;
                data[data_pin+5]=true;
                data[data_pin+6]=false;
                data[data_pin+7]=false;
            }
            else
            {
                data[data_pin]=false;
                data[data_pin+1]=false;
                data[data_pin+2]=false;
                data[data_pin+3]=true;
                data[data_pin+4]=false;
                data[data_pin+5]=false;
                data[data_pin+6]=false;
                data[data_pin+7]=true;
            }
        }
        int block_count_per_group[]=meta.block_count_per_group[version][error_correction_level];
        side=meta.side_length[version];
        int group_count=block_count_per_group.length;
        int blocked_byte[][][]=new int[group_count][][];
        int error_correction_code_word_count_per_block=meta.error_correction_code_word_count_per_block[version][error_correction_level];
        int generator_polynomial_coefficient[]=meta.generator_polynomial_coefficient[error_correction_code_word_count_per_block];
        int data_convert_pin=0;
        int min_block_data_count=Integer.MAX_VALUE;
        for(int i=0;i<group_count;i++)
        {
            int group_size=block_count_per_group[i];
            int block_data_count=meta.data_code_word_count_per_block[version][error_correction_level][i];
            min_block_data_count=min_block_data_count<block_data_count?min_block_data_count:block_data_count;
            int block_size=block_data_count+error_correction_code_word_count_per_block;
            blocked_byte[i]=new int[group_size][];
            int this_group[][]=blocked_byte[i];
            for(int j=0;j<group_size;j++)
            {
                this_group[j]=new int[block_size];
                int message_polynomial_coefficient[]=this_group[j];
                int data_remainder_polynomial_coefficient[]=new int[block_size];
                for(int k=0;k<block_data_count;k++,data_convert_pin+=8)
                {
                    message_polynomial_coefficient[k]=(data[data_convert_pin]?128:0)+(data[data_convert_pin+1]?64:0)+(data[data_convert_pin+2]?32:0)+(data[data_convert_pin+3]?16:0)+(data[data_convert_pin+4]?8:0)+(data[data_convert_pin+5]?4:0)+(data[data_convert_pin+6]?2:0)+(data[data_convert_pin+7]?1:0);
                    data_remainder_polynomial_coefficient[k]=message_polynomial_coefficient[k];
                }
                for(int k=0;k<block_data_count;k++)
                {
                    int factor=data_remainder_polynomial_coefficient[k];
                    if(factor!=0)
                    {
                        for(int l=0;l<=error_correction_code_word_count_per_block;l++)
                        {
                            if(factor!=0&&generator_polynomial_coefficient[l]!=0)
                            {
                                data_remainder_polynomial_coefficient[k+l]^=meta.exponential_finite_field_256[(meta.logarithm_finite_field_256[factor]+meta.logarithm_finite_field_256[generator_polynomial_coefficient[l]])%255];
                            }
                        }
                    }
                }
                for(int k=block_data_count;k<block_size;k++)
                {
                    message_polynomial_coefficient[k]=data_remainder_polynomial_coefficient[k];
                }
            }
        }
        boolean bit_stream[]=new boolean[side*side];
        data_convert_pin=0;
        for(int i=0;i<min_block_data_count;i++)
        {
            for(int j=0;j<group_count;j++)
            {
                int group_size=block_count_per_group[j];
                for(int k=0;k<group_size;k++,data_convert_pin+=8)
                {
                    int now=blocked_byte[j][k][i];
                    bit_stream[data_convert_pin]=(now>>7&1)==1;
                    bit_stream[data_convert_pin+1]=(now>>6&1)==1;
                    bit_stream[data_convert_pin+2]=(now>>5&1)==1;
                    bit_stream[data_convert_pin+3]=(now>>4&1)==1;
                    bit_stream[data_convert_pin+4]=(now>>3&1)==1;
                    bit_stream[data_convert_pin+5]=(now>>2&1)==1;
                    bit_stream[data_convert_pin+6]=(now>>1&1)==1;
                    bit_stream[data_convert_pin+7]=(now&1)==1;
                }
            }
        }
        if(group_count==2)
        {
            int group_size=block_count_per_group[1];
            for(int k=0;k<group_size;k++,data_convert_pin+=8)
            {
                int now=blocked_byte[1][k][min_block_data_count];
                bit_stream[data_convert_pin]=(now>>7&1)==1;
                bit_stream[data_convert_pin+1]=(now>>6&1)==1;
                bit_stream[data_convert_pin+2]=(now>>5&1)==1;
                bit_stream[data_convert_pin+3]=(now>>4&1)==1;
                bit_stream[data_convert_pin+4]=(now>>3&1)==1;
                bit_stream[data_convert_pin+5]=(now>>2&1)==1;
                bit_stream[data_convert_pin+6]=(now>>1&1)==1;
                bit_stream[data_convert_pin+7]=(now&1)==1;
            }
        }
        int group_1_error_correction_code_word_start=min_block_data_count;
        int group_1_error_correction_code_word_end=min_block_data_count+error_correction_code_word_count_per_block;
        for(int i=group_1_error_correction_code_word_start;i<group_1_error_correction_code_word_end;i++)
        {
            for(int j=0;j<group_count;j++)
            {
                int group_size=block_count_per_group[j];
                for(int k=0;k<group_size;k++,data_convert_pin+=8)
                {
                    int now=blocked_byte[j][k][i+j];
                    bit_stream[data_convert_pin]=(now>>7&1)==1;
                    bit_stream[data_convert_pin+1]=(now>>6&1)==1;
                    bit_stream[data_convert_pin+2]=(now>>5&1)==1;
                    bit_stream[data_convert_pin+3]=(now>>4&1)==1;
                    bit_stream[data_convert_pin+4]=(now>>3&1)==1;
                    bit_stream[data_convert_pin+5]=(now>>2&1)==1;
                    bit_stream[data_convert_pin+6]=(now>>1&1)==1;
                    bit_stream[data_convert_pin+7]=(now&1)==1;
                }
            }
        }
        data_convert_pin+=meta.message_bit_stream_rest_count[version];
        field=new boolean[side][side];
        boolean protect[][]=new boolean[side][side];
        for(int y=0;y<7;y++)
        {
            field[y][0]=true;
            field[y][6]=true;
            field[0][y]=true;
            field[6][y]=true;
            if(y>=2&&y<=4)
            {
                for(int x=2;x<=4;x++)
                {
                    field[y][x]=true;
                }
            }
        }
        for(int y=0;y<9;y++)
        {
            for(int x=0;x<9;x++)
            {
                protect[y][x]=true;
            }
        }
        for(int i=side-7;i<side;i++)
        {
            field[i][0]=true;
            field[i][6]=true;
            field[0][i]=true;
            field[6][i]=true;
            if(i!=side-6&&i!=side-2)
            {
                for(int j=1;j<6;j++)
                {
                    if(i==side-7||i==side-1||(j>=2&&j<=4))
                    {
                        field[i][j]=true;
                        field[j][i]=true;
                    }
                }
            }
        }
        for(int i=side-8;i<side;i++)
        {
            for(int j=0;j<9;j++)
            {
                protect[i][j]=true;
                protect[j][i]=true;
            }
        }
        if(version>=2)
        {
            int alignment_pattern_center_position[][]=meta.alignment_pattern_center_position[version];
            for(int i=alignment_pattern_center_position.length-1;i>=0;i--)
            {
                int y=alignment_pattern_center_position[i][0];
                int x=alignment_pattern_center_position[i][1];
                field[y][x]=true;
                for(int di=-2;di<=2;di++)
                {
                    field[y+2][x+di]=true;
                    field[y-2][x+di]=true;
                    field[y+di][x+2]=true;
                    field[y+di][x-2]=true;
                }
                for(int dx=-2;dx<=2;dx++)
                {
                    for(int dy=-2;dy<=2;dy++)
                    {
                        protect[y+dy][x+dx]=true;
                    }
                }
            }
        }
        boolean flapper=true;
        for(int i=side-9;i>=8;i--,flapper=!flapper)
        {
            field[6][i]=flapper;
            field[i][6]=flapper;
            protect[6][i]=true;
            protect[i][6]=true;
        }
        field[side-8][8]=true;
        if(version>=7)
        {
            for(int i=0;i<=5;i++)
            {
                for(int j=side-11;j<=side-9;j++)
                {
                    protect[i][j]=true;
                    protect[j][i]=true;
                }
            }
        }
        int pin_x=side-1,pin_y=side-1;
        boolean going_up=true,going_left=true;
        for(data_pin=0;data_pin<data_convert_pin;)
        {
            if(!protect[pin_y][pin_x])
            {
                field[pin_y][pin_x]=bit_stream[data_pin++];
            }
            if(going_up)
            {
                if(going_left)
                {
                    pin_x--;
                    going_left=false;
                }
                else
                {
                    if(pin_y>0)
                    {
                        pin_y--;
                        pin_x++;
                    }
                    else
                    {
                        if(pin_x!=7)
                        {
                            pin_x--;
                        }
                        else
                        {
                            pin_x=5;
                            pin_y=9;
                        }
                        going_up=false;
                    }
                    going_left=true;
                }
            }
            else
            {
                if(going_left)
                {
                    pin_x--;
                    going_left=false;
                }
                else
                {
                    if(pin_y<side-1)
                    {
                        pin_y++;
                        pin_x++;
                    }
                    else
                    {
                        pin_x--;
                        going_up=true;
                    }
                    going_left=true;
                }
            }
        }
        for(int y=0;y<side;y++)
        {
            for(int x=0;x<side;x++)
            {
                field[y][x]^=!protect[y][x]&&(y+x)%2==0;
            }
        }
        byte mask_mode=0;
        int format_code=(meta.error_correction_mask[error_correction_level]<<3|mask_mode)<<10;
        int error_correction_bit=format_code;
        int format_generator_polynomial_coefficient=0b10100110111;
        for(;;)
        {
            int bit_delta=0;
            for(int temp=error_correction_bit;temp>0;temp>>=1,bit_delta++);
            if(bit_delta<=10)
            {
                break;
            }
            for(int temp=format_generator_polynomial_coefficient;temp>0;temp>>=1,bit_delta--);
            error_correction_bit^=format_generator_polynomial_coefficient<<bit_delta;
        }
        format_code|=error_correction_bit;
        format_code^=0b101010000010010;
        for(int i=0;i<15;i++)
        {
            boolean bit=(format_code>>14-i&1)==1;
            if(i<=5)
            {
                field[8][i]=bit;
                field[side-i-1][8]=bit;
            }
            else if(i==6)
            {
                field[8][7]=bit;
                field[side-7][8]=bit;
            }
            else if(i<=8)
            {
                field[15-i][8]=bit;
                field[8][side+i-15]=bit;
            }
            else
            {
                field[14-i][8]=bit;
                field[8][side+i-15]=bit;
            }
        }
        if(version>=7)
        {
            int version_code=version<<12;
            error_correction_bit=version_code;
            int version_generator_polynomial_coefficient=0b1111100100101;
            for(;;)
            {
                int bit_delta=0;
                for(int temp=error_correction_bit;temp>0;temp>>=1,bit_delta++);
                if(bit_delta<=12)
                {
                    break;
                }
                for(int temp=version_generator_polynomial_coefficient;temp>0;temp>>=1,bit_delta--);
                error_correction_bit^=version_generator_polynomial_coefficient<<bit_delta;
            }
            version_code|=error_correction_bit;
            for(int i=0;i<18;i++)
            {
                boolean bit=(version_code>>i&1)==1;
                field[side-11+i%3][i/3]=bit;
                field[i/3][side-11+i%3]=bit;
            }
        }
    }
    /**
    通过文本和纠错等级构造二维码。<br>
    自动选择合适的编码模式和最小可用的版本。
    @param text 要编码的文本。
    @param error_correction_level 纠错等级。<br>
    1:L 低纠错等级(7%)<br>
    2:M 中纠错等级(15%)<br>
    3:Q 高纠错等级(25%)<br>
    4:H 超高纠错等级(30%)<br>
    */
    public quick_response_code(String text,int error_correction_level)
    {
        int length=text.length();
        int mode=0;
        for(int i=0;i<length;i++)
        {
            char now=text.charAt(i);
            if(mode<=2&&now>'\u007F')
            {
                mode=4;
                break;
            }
            if(mode<=1&&(now>'Z'||now<'A'&&now>':'||now<'-'&&now>'+'||now<'*'&&now>'%'||now<'$'&&now>' '||now<' '))
            {
                mode=2;
            }
            if(mode==0&&(now<'0'||now>'9'))
            {
                mode=1;
            }
        }
        int code_length=text.length();
        int version=1;
        if(mode>=2)
        {
            try
            {
                code_length=text.getBytes("UTF-8").length;
            }
            catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
                version=-1;
            }
        }
        if(code_length>meta.effective_data_code_word_count[40][1][mode])
        {
            version=-1;
        }
        int left=1,right=40;
        while(left<=right)
        {
            int middle=left+right>>1;
            int now_capacity=meta.effective_data_code_word_count[middle][error_correction_level][mode];
            if(now_capacity>=code_length)
            {
                version=middle;
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        this(text,mode,version,error_correction_level);
    }
    /**
    通过文本构造二维码。<br>
    自动选择合适的编码模式和最小可用的版本。<br>
    默认纠错等级为L。
    @param text 要编码的文本。
    */
    public quick_response_code(String text)
    {
        this(text,1);
    }
    /**
    弹窗显示二维码。
    @param scale 像素块大小。
    */
    public void display(int scale)
    {
        int size=(side+8)*scale;
        BufferedImage image=new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph=image.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0,0,size,size);
        graph.setColor(Color.BLACK);
        for(int i=0;i<side;i++)
        {
            for(int j=0;j<side;j++)
            {
                if(field[i][j])
                {
                    graph.fillRect((i+4)*scale,(j+4)*scale,scale,scale);
                }
            }
        }
        graph.dispose();
        JFrame frame=new JFrame("二维码");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)),BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder();
        result.append("版本:"+version+"\n");
        result.append("纠错等级:"+error_correction_level+"\n");
        for(int i=0;i<side;i++)
        {
            for(int j=0;j<side;j++)
            {
                result.append(field[i][j]?"██":"  ");
            }
            result.append("\n");
        }
        result.delete(result.length()-1,result.length());
        return result.toString();
    }
}