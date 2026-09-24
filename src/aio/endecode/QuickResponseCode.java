package aio.endecode;
import javax.swing.*;
import java.nio.charset.*;
import java.awt.*;
import java.awt.image.*;
/**
<p>二维码类</p><br>
用于表示、生成和解析二维码。
*/
public class QuickResponseCode {
    /**
    <p>二维码边长</p>
    */
    public int side=21;
    /**
    <p>纠错等级</p><br>
    <ul>
        <li>1:L 低纠错等级(7%)</li>
        <li>2:M 中纠错等级(15%)</li>
        <li>3:Q 高纠错等级(25%)</li>
        <li>4:H 超高纠错等级(30%)</li>
    </ul>
    */
    public int errorCorrectionLevel=1;
    /**
    <p>版本号</p><br>
    版本号∈[1,40]，对应的二维码边长为<code>(version-1)*4+21</code>。
    */
    public int version=1;
    /**
    <p>编码模式</p><br>
    <ul>
        <li>0:数字模式</li>
        <li>1:数字字母模式</li>
        <li>2:字节模式</li>
        <li>3:日文模式</li>
        <li>4:扩展解释模式（需要使用字节模式掩码）</li>
    </ul>
    */
    public int mode=2;
    /**
    <p>掩码</p><br>
    掩码∈[0,7]，用于选择不同的纠错等级。
    */
    public int mask=0;
    /**
    <p>二维码点阵</p><br>
    二维码的二进制表示，每个元素为二维码的一个点。
    */
    public boolean field[][];
    /**
    <p>构造方法</p><br>
    通过文本、编码模式、版本号和纠错等级构造二维码。
    @param text 要编码的文本。
    @param errorCorrectionLevel 纠错等级。<br>
    1:L 低纠错等级(7%)<br>
    2:M 中纠错等级(15%)<br>
    3:Q 高纠错等级(25%)<br>
    4:H 超高纠错等级(30%)
    @param version 版本。<br>
    版本∈[1,40]，对应的二维码边长为<code>(version-1)*4+21</code>。
    @param mode 编码模式。<br>
    0:数字模式<br>
    1:数字字母模式<br>
    2:字节模式<br>
    3:日文模式<br>
    4:扩展解释模式（需要使用字节模式掩码）
    */
    public QuickResponseCode(String text,int errorCorrectionLevel,int version,int mode) {
        int codeLength=text.length();
        if(version<1||version>40||codeLength>Barcode.EFFECTIVE_DATA_CODE_WORD_COUNT[version][errorCorrectionLevel][mode]) {
            this.version=-1;
            return;
        }
        this.version=version;
        this.mode=mode;
        this.errorCorrectionLevel=errorCorrectionLevel;
        if(mode>=2) {
            codeLength=text.getBytes(StandardCharsets.UTF_8).length;
        }
        byte modeFronterBinary=(byte)Barcode.MODE_MASK[mode];
        short characterCountFronterBinary=(short)(codeLength);
        int dataByteCount=Barcode.DATA_CODE_WORD_COUNT[version][errorCorrectionLevel];
        boolean data[]=new boolean[dataByteCount<<3];
        int dataPin=0;
        int headerBitCount=0;
        if(mode==4) {
            short eciFronterBinary=(short)(7<<8|26);
            headerBitCount+=12;
            for(;dataPin<headerBitCount;dataPin++) {
                data[dataPin]=(eciFronterBinary>>(headerBitCount-1-dataPin)&1)==1;
            }
        }
        headerBitCount+=4;
        for(;dataPin<headerBitCount;dataPin++) {
            data[dataPin]=(modeFronterBinary>>(headerBitCount-1-dataPin)&1)==1;
        }
        headerBitCount+=Barcode.CODE_LENGTH_BIT_COUNT[version][mode];
        for(;dataPin<headerBitCount;dataPin++) {
            data[dataPin]=(characterCountFronterBinary>>(headerBitCount-1-dataPin)&1)==1;
        }
        switch(mode) {
            case 0-> {
                int i=2;
                int numberGroup=0,groupBitMovement=-1;
                for(;i<codeLength;i+=3) {
                    numberGroup=(text.charAt(i-2)-'0')*100+(text.charAt(i-1)-'0')*10+(text.charAt(i)-'0');
                    for(groupBitMovement=9;groupBitMovement>=0;groupBitMovement--) {
                        data[dataPin++]=(numberGroup>>(groupBitMovement)&1)==1;
                    }
                }
                switch(i-codeLength) {
                    case 0-> {
                        numberGroup=(text.charAt(i-2)-'0')*10+(text.charAt(i-1)-'0');
                        groupBitMovement=6;
                    }
                    case 1-> {
                        numberGroup=text.charAt(i-2)-'0';
                        groupBitMovement=3;
                    }
                    default-> {
                        numberGroup=0;
                        groupBitMovement=-1;
                    }
                }
                for(;groupBitMovement>=0;groupBitMovement--) {
                    data[dataPin++]=(numberGroup>>(groupBitMovement)&1)==1;
                }
            }
            case 1-> {
                int i=1;
                int alphanumericGroup=0,groupBitMovement=-1;
                for(;i<codeLength;i+=2) {
                    alphanumericGroup=Barcode.ALPHANUMERIC_TABLE[text.charAt(i-1)]*45+Barcode.ALPHANUMERIC_TABLE[text.charAt(i)];
                    for(groupBitMovement=10;groupBitMovement>=0;groupBitMovement--) {
                        data[dataPin++]=(alphanumericGroup>>(groupBitMovement)&1)==1;
                    }
                }
                if(i==codeLength) {
                    alphanumericGroup=Barcode.ALPHANUMERIC_TABLE[text.charAt(codeLength-1)];
                    for(groupBitMovement=5;groupBitMovement>=0;groupBitMovement--) {
                        data[dataPin++]=(alphanumericGroup>>(groupBitMovement)&1)==1;
                    }
                }
            }
            default-> {
                byte textByte[]=text.getBytes(StandardCharsets.UTF_8);
                for(int i=0;i<codeLength;i++) {
                    byte now=textByte[i];
                    for(int j=0;j<8;j++) {
                        data[dataPin++]=(now>>(7-j)&1)==1;
                    }
                }
            }
        }
        for(int i=1;dataPin<data.length&&i<=4;dataPin++,i++) {
            data[dataPin]=false;
        }
        for(;dataPin<data.length&&dataPin%8>0;dataPin++) {
            data[dataPin]=false;
        }
        for(boolean is236=true;dataPin<data.length;dataPin+=8,is236=!is236) {
            if(is236) {
                data[dataPin]=true;
                data[dataPin+1]=true;
                data[dataPin+2]=true;
                data[dataPin+3]=false;
                data[dataPin+4]=true;
                data[dataPin+5]=true;
                data[dataPin+6]=false;
                data[dataPin+7]=false;
            } else {
                data[dataPin]=false;
                data[dataPin+1]=false;
                data[dataPin+2]=false;
                data[dataPin+3]=true;
                data[dataPin+4]=false;
                data[dataPin+5]=false;
                data[dataPin+6]=false;
                data[dataPin+7]=true;
            }
        }
        int blockCountPerGroup[]=Barcode.BLOCK_COUNT_PER_GROUP[version][errorCorrectionLevel];
        side=Barcode.SIDE_LENGTH[version];
        int groupCount=blockCountPerGroup.length;
        int blockedByte[][][]=new int[groupCount][][];
        int errorCorrectionCodeWordCountPerBlock=Barcode.ERROR_CORRECTION_CODE_WORD_COUNT_PER_BLOCK[version][errorCorrectionLevel];
        int generatorPolynomialCoefficient[]=Barcode.GENERATOR_POLYNOMIAL_COEFFICIENT[errorCorrectionCodeWordCountPerBlock];
        int dataConvertPin=0;
        int minBlockDataCount=Integer.MAX_VALUE;
        for(int i=0;i<groupCount;i++) {
            int groupSize=blockCountPerGroup[i];
            int blockDataCount=Barcode.DATA_CODE_WORD_COUNT_PER_BLOCK[version][errorCorrectionLevel][i];
            minBlockDataCount=minBlockDataCount<blockDataCount?minBlockDataCount:blockDataCount;
            int blockSize=blockDataCount+errorCorrectionCodeWordCountPerBlock;
            blockedByte[i]=new int[groupSize][];
            int thisGroup[][]=blockedByte[i];
            for(int j=0;j<groupSize;j++) {
                thisGroup[j]=new int[blockSize];
                int messagePolynomialCoefficient[]=thisGroup[j];
                int dataRemainderPolynomialCoefficient[]=new int[blockSize];
                for(int k=0;k<blockDataCount;k++,dataConvertPin+=8) {
                    messagePolynomialCoefficient[k]=(data[dataConvertPin]?128:0)+(data[dataConvertPin+1]?64:0)+(data[dataConvertPin+2]?32:0)+(data[dataConvertPin+3]?16:0)+(data[dataConvertPin+4]?8:0)+(data[dataConvertPin+5]?4:0)+(data[dataConvertPin+6]?2:0)+(data[dataConvertPin+7]?1:0);
                    dataRemainderPolynomialCoefficient[k]=messagePolynomialCoefficient[k];
                }
                for(int k=0;k<blockDataCount;k++) {
                    int factor=dataRemainderPolynomialCoefficient[k];
                    if(factor!=0) {
                        for(int l=0;l<=errorCorrectionCodeWordCountPerBlock;l++) {
                            if(factor!=0&&generatorPolynomialCoefficient[l]!=0) {
                                dataRemainderPolynomialCoefficient[k+l]^=Barcode.EXPONENTIAL_FINITE_FIELD_256[(Barcode.LOGARITHM_FINITE_FIELD_256[factor]+Barcode.LOGARITHM_FINITE_FIELD_256[generatorPolynomialCoefficient[l]])%255];
                            }
                        }
                    }
                }
                for(int k=blockDataCount;k<blockSize;k++) {
                    messagePolynomialCoefficient[k]=dataRemainderPolynomialCoefficient[k];
                }
            }
        }
        boolean bitStream[]=new boolean[side*side];
        dataConvertPin=0;
        for(int i=0;i<minBlockDataCount;i++) {
            for(int j=0;j<groupCount;j++) {
                int groupSize=blockCountPerGroup[j];
                for(int k=0;k<groupSize;k++,dataConvertPin+=8) {
                    int now=blockedByte[j][k][i];
                    bitStream[dataConvertPin]=(now>>7&1)==1;
                    bitStream[dataConvertPin+1]=(now>>6&1)==1;
                    bitStream[dataConvertPin+2]=(now>>5&1)==1;
                    bitStream[dataConvertPin+3]=(now>>4&1)==1;
                    bitStream[dataConvertPin+4]=(now>>3&1)==1;
                    bitStream[dataConvertPin+5]=(now>>2&1)==1;
                    bitStream[dataConvertPin+6]=(now>>1&1)==1;
                    bitStream[dataConvertPin+7]=(now&1)==1;
                }
            }
        }
        if(groupCount==2) {
            int groupSize=blockCountPerGroup[1];
            for(int k=0;k<groupSize;k++,dataConvertPin+=8) {
                int now=blockedByte[1][k][minBlockDataCount];
                bitStream[dataConvertPin]=(now>>7&1)==1;
                bitStream[dataConvertPin+1]=(now>>6&1)==1;
                bitStream[dataConvertPin+2]=(now>>5&1)==1;
                bitStream[dataConvertPin+3]=(now>>4&1)==1;
                bitStream[dataConvertPin+4]=(now>>3&1)==1;
                bitStream[dataConvertPin+5]=(now>>2&1)==1;
                bitStream[dataConvertPin+6]=(now>>1&1)==1;
                bitStream[dataConvertPin+7]=(now&1)==1;
            }
        }
        int group1ErrorCorrectionCodeWordStart=minBlockDataCount;
        int group1ErrorCorrectionCodeWordEnd=minBlockDataCount+errorCorrectionCodeWordCountPerBlock;
        for(int i=group1ErrorCorrectionCodeWordStart;i<group1ErrorCorrectionCodeWordEnd;i++) {
            for(int j=0;j<groupCount;j++) {
                int groupSize=blockCountPerGroup[j];
                for(int k=0;k<groupSize;k++,dataConvertPin+=8) {
                    int now=blockedByte[j][k][i+j];
                    bitStream[dataConvertPin]=(now>>7&1)==1;
                    bitStream[dataConvertPin+1]=(now>>6&1)==1;
                    bitStream[dataConvertPin+2]=(now>>5&1)==1;
                    bitStream[dataConvertPin+3]=(now>>4&1)==1;
                    bitStream[dataConvertPin+4]=(now>>3&1)==1;
                    bitStream[dataConvertPin+5]=(now>>2&1)==1;
                    bitStream[dataConvertPin+6]=(now>>1&1)==1;
                    bitStream[dataConvertPin+7]=(now&1)==1;
                }
            }
        }
        dataConvertPin+=Barcode.MESSAGE_BIT_STREAM_REST_COUNT[version];
        field=new boolean[side][side];
        boolean protect[][]=new boolean[side][side];
        for(int y=0;y<7;y++) {
            field[y][0]=true;
            field[y][6]=true;
            field[0][y]=true;
            field[6][y]=true;
            if(y>=2&&y<=4) {
                for(int x=2;x<=4;x++) {
                    field[y][x]=true;
                }
            }
        }
        for(int y=0;y<9;y++) {
            for(int x=0;x<9;x++) {
                protect[y][x]=true;
            }
        }
        for(int i=side-7;i<side;i++) {
            field[i][0]=true;
            field[i][6]=true;
            field[0][i]=true;
            field[6][i]=true;
            if(i!=side-6&&i!=side-2) {
                for(int j=1;j<6;j++) {
                    if(i==side-7||i==side-1||(j>=2&&j<=4)) {
                        field[i][j]=true;
                        field[j][i]=true;
                    }
                }
            }
        }
        for(int i=side-8;i<side;i++) {
            for(int j=0;j<9;j++) {
                protect[i][j]=true;
                protect[j][i]=true;
            }
        }
        if(version>=2) {
            int alignmentPatternCenterPosition[][]=Barcode.ALIGNMENT_PATTERN_CENTER_POSITION[version];
            for(int i=alignmentPatternCenterPosition.length-1;i>=0;i--) {
                int y=alignmentPatternCenterPosition[i][0];
                int x=alignmentPatternCenterPosition[i][1];
                field[y][x]=true;
                for(int di=-2;di<=2;di++) {
                    field[y+2][x+di]=true;
                    field[y-2][x+di]=true;
                    field[y+di][x+2]=true;
                    field[y+di][x-2]=true;
                }
                for(int dx=-2;dx<=2;dx++) {
                    for(int dy=-2;dy<=2;dy++) {
                        protect[y+dy][x+dx]=true;
                    }
                }
            }
        }
        boolean flapper=true;
        for(int i=side-9;i>=8;i--,flapper=!flapper) {
            field[6][i]=flapper;
            field[i][6]=flapper;
            protect[6][i]=true;
            protect[i][6]=true;
        }
        field[side-8][8]=true;
        if(version>=7) {
            for(int i=0;i<=5;i++) {
                for(int j=side-11;j<=side-9;j++) {
                    protect[i][j]=true;
                    protect[j][i]=true;
                }
            }
        }
        int pinX=side-1,pinY=side-1;
        boolean goingUp=true,goingLeft=true;
        for(dataPin=0;dataPin<dataConvertPin;) {
            if(!protect[pinY][pinX]) {
                field[pinY][pinX]=bitStream[dataPin++];
            }
            if(goingUp) {
                if(goingLeft) {
                    pinX--;
                    goingLeft=false;
                } else {
                    if(pinY>0) {
                        pinY--;
                        pinX++;
                    } else {
                        if(pinX!=7) {
                            pinX--;
                        } else {
                            pinX=5;
                            pinY=9;
                        }
                        goingUp=false;
                    }
                    goingLeft=true;
                }
            } else {
                if(goingLeft) {
                    pinX--;
                    goingLeft=false;
                } else {
                    if(pinY<side-1) {
                        pinY++;
                        pinX++;
                    } else {
                        pinX--;
                        goingUp=true;
                    }
                    goingLeft=true;
                }
            }
        }
        int minPunishmentMaskMode=0;
        boolean minPunishmentField[][]=null;
        int minPunishment=Integer.MAX_VALUE;
        final boolean pattern00001011101[]=Barcode.MATCHING_PATTERN_00001011101;
        final int next00001011101[]=Barcode.MATCHING_NEXT_00001011101;
        final boolean pattern10111010000[]=Barcode.MATCHING_PATTERN_10111010000;
        final int next10111010000[]=Barcode.MATCHING_NEXT_10111010000;
        for(;mask<8;mask++) {
            boolean maskedField[][]=new boolean[side][side];
            for(int y=0;y<side;y++) {
                System.arraycopy(field[y],0,maskedField[y],0,side);
            }
            switch(mask) {
                case 0-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&(y+x)%2==0;
                        }
                    }
                }
                case 1-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&y%2==0;
                        }
                    }
                }
                case 2-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&x%3==0;
                        }
                    }
                }
                case 3-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&(y+x)%3==0;
                        }
                    }
                }
                case 4-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&(y/2+x/3)%2==0;
                        }
                    }
                }
                case 5-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&(y*x)%2+(y*x)%3==0;
                        }
                    }
                }
                case 6-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&((y*x)%2+(y*x)%3)%2==0;
                        }
                    }
                }
                case 7-> {
                    for(int y=0;y<side;y++) {
                        for(int x=0;x<side;x++) {
                            maskedField[y][x]^=!protect[y][x]&&((y+x)%2+(y*x)%3)%2==0;
                        }
                    }
                }
            }
            int punishment=0;
            int trueCount=0;
            for(int y=0;y<side;y++) {
                int rowContinuity=0;
                int columnContinuity=0;
                int pin00001011101Row=0,pin10111010000Row=0;
                int pin00001011101Column=0,pin10111010000Column=0;
                for(int x=0;x<side;x++) {
                    boolean rowBit=maskedField[y][x];
                    boolean columnBit=maskedField[x][y];
                    if(rowBit) {
                        if(rowContinuity>=0) {
                            rowContinuity++;
                        } else {
                            if(rowContinuity<=-5) {
                                punishment-=rowContinuity+2;
                            }
                            rowContinuity=1;
                        }
                    } else {
                        if(rowContinuity<=0) {
                            rowContinuity--;
                        } else {
                            if(rowContinuity>=5) {
                                punishment+=rowContinuity-2;
                            }
                            rowContinuity=-1;
                        }
                    }
                    if(columnBit) {
                        if(columnContinuity>=0) {
                            columnContinuity++;
                        } else {
                            if(columnContinuity<=-5) {
                                punishment-=columnContinuity+2;
                            }
                            columnContinuity=1;
                        }
                    } else {
                        if(columnContinuity<=0) {
                            columnContinuity--;
                        } else {
                            if(columnContinuity>=5) {
                                punishment+=columnContinuity-2;
                            }
                            columnContinuity=-1;
                        }
                    }
                    if(x<side-1&&y<side-1&&((rowBit&&maskedField[y+1][x+1]&&maskedField[y+1][x]&&maskedField[y][x+1])||!(rowBit||maskedField[y+1][x+1]||maskedField[y+1][x]||maskedField[y][x+1]))) {
                        punishment+=3;
                    }
                    if(rowBit==pattern00001011101[pin00001011101Row]) {
                        pin00001011101Row++;
                        if(pin00001011101Row==11) {
                            pin00001011101Row=0;
                            punishment+=40;
                        }
                    } else {
                        pin00001011101Row=next00001011101[pin00001011101Row];
                    }
                    if(columnBit==pattern00001011101[pin00001011101Column]) {
                        pin00001011101Column++;
                        if(pin00001011101Column==11) {
                            pin00001011101Column=0;
                            punishment+=40;
                        }
                    } else {
                        pin00001011101Column=next00001011101[pin00001011101Column];
                    }
                    if(rowBit==pattern10111010000[pin10111010000Row]) {
                        pin10111010000Row++;
                        if(pin10111010000Row==11) {
                            pin10111010000Row=0;
                            punishment+=40;
                        }
                    } else {
                        pin10111010000Row=next10111010000[pin10111010000Row];
                    }
                    if(columnBit==pattern10111010000[pin10111010000Column]) {
                        pin10111010000Column++;
                        if(pin10111010000Column==11) {
                            pin10111010000Column=0;
                            punishment+=40;
                        }
                    } else {
                        pin10111010000Column=next10111010000[pin10111010000Column];
                    }
                    trueCount+=rowBit?1:0;
                }
                if(rowContinuity>=5) {
                    punishment+=rowContinuity-2;
                } else if(rowContinuity<=-5) {
                    punishment-=rowContinuity+2;
                }
                if(columnContinuity>=5) {
                    punishment+=columnContinuity-2;
                } else if(columnContinuity<=-5) {
                    punishment-=columnContinuity+2;
                }
            }
            int deltaPunishment=trueCount*200/(side*side)-100;
            punishment+=deltaPunishment>=0?deltaPunishment:-deltaPunishment;
            if(punishment<minPunishment) {
                minPunishment=punishment;
                minPunishmentMaskMode=mask;
                minPunishmentField=maskedField;
            }
        }
        field=minPunishmentField;
        mask=minPunishmentMaskMode;
        int formatCode=Barcode.FORMAT_CODE_TABLE[errorCorrectionLevel][mask];
        for(int i=0;i<15;i++) {
            boolean bit=(formatCode>>14-i&1)==1;
            if(i<=5) {
                field[8][i]=bit;
                field[side-i-1][8]=bit;
            } else if(i==6) {
                field[8][7]=bit;
                field[side-7][8]=bit;
            } else if(i<=8) {
                field[15-i][8]=bit;
                field[8][side+i-15]=bit;
            } else {
                field[14-i][8]=bit;
                field[8][side+i-15]=bit;
            }
        }
        if(version>=7) {
            int versionCode=Barcode.VERSION_CODE_TABLE[version];
            for(int i=0;i<18;i++) {
                boolean bit=(versionCode>>i&1)==1;
                field[side-11+i%3][i/3]=bit;
                field[i/3][side-11+i%3]=bit;
            }
        }
    }
    /**
    <p>构造方法</p><br>
    通过文本和纠错等级构造二维码。<br>
    自动选择合适的编码模式和最小可用的版本。
    @param text 要编码的文本。
    @param errorCorrectionLevel 纠错等级。<br>
    1:L 低纠错等级(7%)<br>
    2:M 中纠错等级(15%)<br>
    3:Q 高纠错等级(25%)<br>
    4:H 超高纠错等级(30%)
    */
    public QuickResponseCode(String text,int errorCorrectionLevel) {
        int length=text.length();
        int mode=0;
        for(int i=0;i<length;i++) {
            char now=text.charAt(i);
            if(mode<=2&&now>'\u00FF') {
                mode=4;
                break;
            }
            if(mode<=1&&(now>'Z'||now<'A'&&now>':'||now<'-'&&now>'+'||now<'*'&&now>'%'||now<'$'&&now>' '||now<' ')) {
                mode=2;
            }
            if(mode==0&&(now<'0'||now>'9')) {
                mode=1;
            }
        }
        int codeLength=text.length();
        int version=1;
        if(mode>=2) {
            codeLength=text.getBytes(StandardCharsets.UTF_8).length;
        }
        if(codeLength>Barcode.EFFECTIVE_DATA_CODE_WORD_COUNT[40][errorCorrectionLevel][mode]) {
            version=-1;
        }
        int left=1,right=40;
        while(left<=right) {
            int middle=left+right>>1;
            int nowCapacity=Barcode.EFFECTIVE_DATA_CODE_WORD_COUNT[middle][errorCorrectionLevel][mode];
            if(nowCapacity>=codeLength) {
                version=middle;
                right=middle-1;
            } else {
                left=middle+1;
            }
        }
        this(text,errorCorrectionLevel,version,mode);
    }
    /**
    <p>构造方法</p><br>
    通过文本构造二维码。<br>
    自动选择合适的编码模式和最小可用的版本。<br>
    默认纠错等级为L。
    @param text 要编码的文本。
    */
    public QuickResponseCode(String text) {
        this(text,1);
    }
    /**
    <p>编码</p><br>
    通过文本、编码模式、版本号和纠错等级计算二维码图形。
    @param text 要编码的文本。
    @param errorCorrectionLevel 纠错等级。<br>
    1:L 低纠错等级(7%)<br>
    2:M 中纠错等级(15%)<br>
    3:Q 高纠错等级(25%)<br>
    4:H 超高纠错等级(30%)
    @param version 版本。<br>
    版本∈[1,40]，对应的二维码边长为<code>(version-1)*4+21</code>。
    @param mode 编码模式。<br>
    0:数字模式<br>
    1:数字字母模式<br>
    2:字节模式<br>
    3:日文模式<br>
    4:扩展解释模式（需要使用字节模式掩码）
    @return 二维码图形的二维数组表示。<br>
    <code>true</code>表示二维码的该位置为黑色。<br>
    <code>false</code>表示二维码的该位置为白色。
    */
    public static boolean[][] encode(String text,int errorCorrectionLevel,int version,int mode) {
        QuickResponseCode quickResponseCode=new QuickResponseCode(text,errorCorrectionLevel,version,mode);
        System.out.println("边长："+quickResponseCode.side);
        System.out.println("纠错等级："+quickResponseCode.errorCorrectionLevel);
        System.out.println("版本："+quickResponseCode.version);
        System.out.println("编码模式："+quickResponseCode.mode);
        return quickResponseCode.field;
    }
    /**
    <p>编码</p><br>
    通过文本和纠错等级计算二维码图形。<br>
    自动选择合适的编码模式和最小可用的版本。
    @param text 要编码的文本。
    @param errorCorrectionLevel 纠错等级。<br>
    1:L 低纠错等级(7%)<br>
    2:M 中纠错等级(15%)<br>
    3:Q 高纠错等级(25%)<br>
    4:H 超高纠错等级(30%)<br>
    @return 二维码图形的二维数组表示。<br>
    <code>true</code>表示二维码的该位置为黑色。<br>
    <code>false</code>表示二维码的该位置为白色。
    */
    public static boolean[][] encode(String text,int errorCorrectionLevel) {
        QuickResponseCode quickResponseCode=new QuickResponseCode(text,errorCorrectionLevel);
        System.out.println("边长："+quickResponseCode.side);
        System.out.println("纠错等级："+quickResponseCode.errorCorrectionLevel);
        System.out.println("版本："+quickResponseCode.version);
        System.out.println("编码模式："+quickResponseCode.mode);
        return quickResponseCode.field;
    }
    /**
    <p>编码</p><br>
    通过文本计算二维码图形。<br>
    自动选择合适的编码模式和最小可用的版本。<br>
    默认纠错等级为L。
    @param text 要编码的文本。
    @return 二维码图形的二维数组表示。<br>
    <code>true</code>表示二维码的该位置为黑色。<br>
    <code>false</code>表示二维码的该位置为白色。
    */
    public static boolean[][] encode(String text) {
        QuickResponseCode quickResponseCode=new QuickResponseCode(text);
        System.out.println("边长："+quickResponseCode.side);
        System.out.println("纠错等级："+quickResponseCode.errorCorrectionLevel);
        System.out.println("版本："+quickResponseCode.version);
        System.out.println("编码模式："+quickResponseCode.mode);
        return quickResponseCode.field;
    }
    /**
    <p>图片显示</p><br>
    弹窗显示二维码。
    @param scale 像素块大小。
    */
    public void display(int scale) {
        int size=(side+8)*scale;
        BufferedImage image=new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph=image.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0,0,size,size);
        graph.setColor(Color.BLACK);
        for(int y=0;y<side;y++) {
            for(int x=0;x<side;x++) {
                if(field[y][x]) {
                    graph.fillRect((x+4)*scale,(y+4)*scale,scale,scale);
                }
            }
        }
        graph.dispose();
        JFrame frame=new JFrame(version+switch(errorCorrectionLevel){case 1->"L";case 2->"M";case 3->"Q";case 4->"H";default->"L";}+" 掩膜"+mask+" "+switch(mode){case 0->"数字模式";case 1->"数字字母模式";case 2->"字节模式";case 3->"日文模式";case 4->"扩展解释模式";default->"未知模式";});
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)),BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
    <p>图片显示</p><br>
    弹窗显示指定二维码图形。
    @param field 二维码图形的二维数组表示。
    @param scale 像素块大小。
    */
    public static void display(boolean[][] field,int scale) {
        int side=field.length;
        int size=(side+8)*scale;
        BufferedImage image=new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph=image.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0,0,size,size);
        graph.setColor(Color.BLACK);
        for(int y=0;y<side;y++) {
            for(int x=0;x<side;x++) {
                if(field[y][x]) {
                    graph.fillRect((x+4)*scale,(y+4)*scale,scale,scale);
                }
            }
        }
        graph.dispose();
        JFrame frame=new JFrame("二维码");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)),BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
    <p>图片显示</p><br>
    弹窗显示二维码。<br>
    自适应像素块大小。
    @return 是否成功显示二维码。<br>
    若二维码版本为-1，即未编码，则返回<code>false</code>。<br>
    否则，返回<code>true</code>。
    */
    public boolean display() {
        if(version==-1) {
            return false;
        }
        int scale=1000/(side+8);
        scale=scale>12?12:scale;
        int size=(side+8)*scale;
        BufferedImage image=new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph=image.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0,0,size,size);
        graph.setColor(Color.BLACK);
        for(int y=0;y<side;y++) {
            for(int x=0;x<side;x++) {
                if(field[y][x]) {
                    graph.fillRect((x+4)*scale,(y+4)*scale,scale,scale);
                }
            }
        }
        graph.dispose();
        JFrame frame=new JFrame(version+switch(errorCorrectionLevel){case 1->"L";case 2->"M";case 3->"Q";case 4->"H";default->"L";}+" 掩膜"+mask+" "+switch(mode){case 0->"数字模式";case 1->"数字字母模式";case 2->"字节模式";case 3->"日文模式";case 4->"扩展解释模式";default->"未知模式";});
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)),BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return true;
    }
    /**
    <p>图片显示</p><br>
    弹窗显示指定二维码图形。<br>
    自适应像素块大小。
    @param field 二维码图形的二维数组表示。
    */
    public static void display(boolean[][] field) {
        int side=field.length;
        int scale=1000/(side+8);
        scale=scale>12?12:scale;
        int size=(side+8)*scale;
        BufferedImage image=new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph=image.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fillRect(0,0,size,size);
        graph.setColor(Color.BLACK);
        for(int y=0;y<side;y++) {
            for(int x=0;x<side;x++) {
                if(field[y][x]) {
                    graph.fillRect((x+4)*scale,(y+4)*scale,scale,scale);
                }
            }
        }
        graph.dispose();
        JFrame frame=new JFrame("二维码");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)),BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
    <p>解码</p><br>
    从二维码图形中提取内容。
    @param field 二维码图形的二维数组表示。
    @return 解码后的内容。
    */
    public static String decode(boolean field[][]) {
        int side=field.length;
        int version=side-17>>2;
        if(version>=7) {

        }
        return "";
    }
    /**
    <p>字符串表示</p><br>
    @return 二维码元数据和图形的字符串表示。
    */
    public String toString() {
        StringBuilder result=new StringBuilder();
        result.append("纠错等级:"+errorCorrectionLevel+"\n");
        result.append("版本:"+version+"\n");
        result.append("编码模式:"+mode+"\n");
        result.append("掩膜编号:"+mask+"\n");
        for(int y=0;y<side;y++) {
            for(int x=0;x<side;x++) {
                result.append(field[y][x]?"██":"  ");
            }
            result.append("\n");
        }
        result.delete(result.length()-1,result.length());
        return result.toString();
    }
}