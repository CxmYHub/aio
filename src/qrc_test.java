import tools.encode_decode.*;
public class qrc_test
{
    public static void display_test()
    {
        String test[]=
        {
            // "Hello, 二维码测试！👋 2026-06-13","https://github.com/qrcode-generator-example","+86 138-0000-1234 | 订单号: ORD-20260613-001","The quick brown fox jumps over 13 lazy dogs.","{\"user\":\"test\",\"score\":99,\"vip\":true}",
            // """
            // 第一行：产品名称
            // 第二行：SN=QB93K2L
            // 第三行：有效期至2026-12-31
            // """
            // ,
            """
            以下是几段随机生成的测试文本，适合用于二维码编码测试：
        
            1. **普通文本**  
                `Hello, 二维码测试！👋 2026-06-13`
            
            2. **网址链接**  
                `https://github.com/qrcode-generator-example`
            
            3. **纯数字 + 符号**  
                `+86 138-0000-1234 | 订单号: ORD-20260613-001`
            
            4. **简短英文**  
                `The quick brown fox jumps over 13 lazy dogs.`
            
            5. **JSON 格式数据**  
                `{"user":"test","score":99,"vip":true}`
            
            6. **混合字符（含换行）**  
                ```
                第一行：产品名称
                第二行：SN=QB93K2L
                第三行：有效期至2026-12-31
                ```
            
            你可以将这些文本逐条输入你的二维码生成器，生成二维码后用扫码工具验证解码结果是否一致。
            """
        };
        for(String i:test)
        {
            quick_response_code qrc=new quick_response_code(i);
            qrc.display();
        }
    }
    public static void print_generator_polynomial()
    {
        int[] versions = {37, 38, 39, 40};
        
        for (int v : versions)
        {
            int ecPerBlock = barcode.error_correction_code_word_count_per_block[v][1];
            int[] genPoly = barcode.generator_polynomial_coefficient[ecPerBlock];
            
            System.out.printf("版本%d-L: 纠错码字/块=%d, 多项式长度=%d, 首项=%d%n",
                v, ecPerBlock, 
                genPoly != null ? genPoly.length : -1,
                genPoly != null && genPoly.length > 0 ? genPoly[0] : -1);
        }
    }
    public static void main(String args[])
    {
        display_test();
    }
}