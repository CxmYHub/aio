import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import tools.geography.*;
public class elevation_test
{
    /**
     * 可视化二维double数组（默认每个数据点占1个像素）
     *
     * @param data 二维double数组，不能为null
     */
    public static void visualize(double[][] data)
    {
        visualize(data,1);
    }
    /**
     * 可视化二维double数组，可指定每个数据点的显示尺寸
     *
     * @param data     二维double数组
     * @param cellSize 每个数据点的显示尺寸（像素），必须为正数
     */
    public static void visualize(double[][] data, int cellSize)
    {
        if (data == null || data.length == 0 || data[0].length == 0)
        {
            System.err.println("数组为空，无法可视化");
            return;
        }
        if (cellSize < 1) cellSize = 1;

        int rows = data.length;
        int cols = data[0].length;

        // 1. 计算有效最小值和最大值（忽略NaN和无穷大）
        double minVal = Double.POSITIVE_INFINITY;
        double maxVal = Double.NEGATIVE_INFINITY;
        boolean hasValid = false;
        for (double[] row : data)
        {
            for (double v : row)
            {
                if (Double.isFinite(v))
                {
                    hasValid = true;
                    if (v < minVal) minVal = v;
                    if (v > maxVal) maxVal = v;
                }
            }
        }
        if (!hasValid)
        {
            // 没有有效数值，使用默认范围
            minVal = 0.0;
            maxVal = 1.0;
        }

        // 2. 构建与数据相同尺寸的BufferedImage（每个元素对应一个像素）
        BufferedImage rawImage = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                double val = data[i][j];
                int rgb;
                if (!Double.isFinite(val))
                {
                    // 非有限值（NaN, Inf）用洋红色表示
                    rgb = 0xFF00FF;
                }
                else
                {
                    // 归一化到 [0,1]
                    double t = (val - minVal) / (maxVal - minVal);
                    rgb = jetColor(t);
                }
                rawImage.setRGB(j, i, rgb);
            }
        }

        // 3. 创建显示面板（负责按cellSize缩放绘制图像）
        final int scaledWidth = cols * cellSize;
        final int scaledHeight = rows * cellSize;
        Canvas canvas = new Canvas()
        {
            @Override
            public void paint(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                // 使用最近邻插值，保证每个数据块边界清晰
                // g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.drawImage(rawImage,0,0,scaledWidth, scaledHeight,this);
            }
            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(scaledWidth, scaledHeight);
            }
        };
        canvas.setSize(scaledWidth, scaledHeight);
        // 4. 放入带滚动条的面板
        ScrollPane scrollPane=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(canvas);
        // 5. 创建颜色图例面板
        LegendPanel legend=new LegendPanel(minVal, maxVal);
        // 6. 创建主窗口
        Frame frame = new Frame("二维数组热力图 - " + rows + "×" + cols +"  范围: [" + String.format("%.4g", minVal) + ", "+String.format("%.4g", maxVal) + "]");
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(legend, BorderLayout.EAST);
        frame.setSize(cols * cellSize+180, rows * cellSize+60);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

    /**
     * Jet色图：输入t [0,1] 输出RGB整数值
     */
    private static int jetColor(double t)
    {
        // 限制t范围
        t = Math.min(1.0, Math.max(0.0, t));
        int r, g, b;
        if (t < 0.125)
        {
            r = 0;
            g = 0;
            b = (int) (255 * (0.5 + t / 0.125 * 0.5));
        }
        else if (t < 0.375)
        {
            r = 0;
            g = (int) (255 * ((t - 0.125) / 0.25));
            b = 255;
        }
        else if (t < 0.625)
        {
            r = (int) (255 * ((t - 0.375) / 0.25));
            g = 255;
            b = (int) (255 * (1 - (t - 0.375) / 0.25));
        }
        else if (t < 0.875)
        {
            r = 255;
            g = (int) (255 * (1 - (t - 0.625) / 0.25));
            b = 0;
        }
        else
        {
            r = (int) (255 * (1 - (t - 0.875) / 0.125));
            g = 0;
            b = 0;
        }
        return (r << 16) | (g << 8) | b;
    }

    /**
     * 颜色图例面板（显示渐变条和数值）
     */
    static class LegendPanel extends Panel
    {
        private final double min;
        private final double max;
        private static final int LEGEND_WIDTH = 20;
        private static final int LEGEND_HEIGHT = 200;

        LegendPanel(double min, double max)
        {
            this.min = min;
            this.max = max;
            setPreferredSize(new Dimension(120, LEGEND_HEIGHT + 60));
        }

        @Override
        public void paint(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            // 绘制渐变条背景
            int x = 20;
            int y = 40;
            for (int i = 0; i < LEGEND_HEIGHT; i++)
            {
                double t = 1.0 - (double) i / (LEGEND_HEIGHT - 1); // 从上到下从高到低
                int color = jetColor(t);
                g2d.setColor(new Color(color));
                g2d.fillRect(x, y + i, LEGEND_WIDTH, 1);
            }
            // 边框
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, LEGEND_WIDTH, LEGEND_HEIGHT);

            // 数值标注
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 11));
            g2d.setColor(Color.BLACK);
            // 最大值 (顶部)
            String maxStr = String.format("%.4g", max);
            g2d.drawString(maxStr, x + LEGEND_WIDTH + 5, y + 10);
            // 最小值 (底部)
            String minStr = String.format("%.4g", min);
            g2d.drawString(minStr, x + LEGEND_WIDTH + 5, y + LEGEND_HEIGHT - 5);
            // 中间值
            String midStr = String.format("%.4g", (min + max) / 2.0);
            g2d.drawString(midStr, x + LEGEND_WIDTH + 5, y + LEGEND_HEIGHT / 2 + 3);
            // 图例标题
            g2d.drawString("海拔（米）", x, y - 10);
        }
    }
    public static void main(String args[])
    {
        elevation_map map=new elevation_map(1280,864);
        double range=map.overlay_perlin_terrain(System.currentTimeMillis(),300,8,0.5,2,2500);
        // map.elevate(1000);
        map.sink(1000);
        System.out.println(range);
        // map.normalize();
        System.out.println("初始状态：\n"+map);
        // map.linear_scale(2);
        visualize(map.elevation);
        // System.out.println("线性缩放2倍后：\n"+map);
        // double returning_coefficient=map.normalize();
        // System.out.println("归一化后：\n"+map);
        // double coefficient=map.exponential_scale(Math.E);
        // System.out.println(coefficient);
        // System.out.println("指数缩放后：\n"+map);
        // map.linear_scale(1/returning_coefficient);
        // System.out.println("线性缩放回归后：\n"+map);
        // visualize(map.elevation);
        // map.exponential_normalize(Math.E);
        // System.out.println("指数规整后：\n"+map);
        map.secant_odd_normalize();
        System.out.println("正割奇函数规整后：\n"+map);
        map.calculate_statistics();
        System.out.println(map);
        System.out.println(map.calculate_histogram());
        visualize(map.elevation);
    }
}