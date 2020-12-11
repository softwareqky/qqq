package project.edge.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintImage {
	private static final Logger logger = LoggerFactory.getLogger(PrintImage.class);
	
	private Font font = new Font("黑体", Font.PLAIN, 25); // 添加字体的属性设置
	private static final int CONTENT_INDENTATION = 220; // 内容部分各行需去除的长度
	private static final int ITEM_WIDTH = 100; //内容抬头部分宽度

	private Graphics2D g = null;
	private BufferedImage bi = null;
	private int imageWidth = 0;
	private int contentPosH = 0;
	
	/**
	 * 导入本地图片到缓冲区
	 */
	public BufferedImage loadImageLocal(String imgName) {
		try {
			if (bi == null) {
				this.bi = ImageIO.read(new File(imgName));
				//this.g = bi.createGraphics();
				this.imageWidth = bi.getWidth();
			}
		} catch (IOException e) {
			this.bi = null;
			//this.g = null;
			logger.error("loadImageLocal error.", e);
		}
		return bi;
	}

	/**
	 * 导入网络图片到缓冲区
	 */
	public BufferedImage loadImageUrl(String imgName) {
		try {
			URL url = new URL(imgName);
			if (bi == null) {
				this.bi = ImageIO.read(url);
				//this.g = bi.createGraphics();
				this.imageWidth = bi.getWidth();
			}
		} catch (IOException e) {
			this.bi = null;
			//this.g = null;
			logger.error("loadImageUrl error.", e);
		}
		return bi;
	}

	/**
	 * 生成新图片到本地
	 */
	public void writeImageLocal(String newImage) {
		if (newImage != null && this.bi != null) {
			try {
				File outputfile = new File(newImage);
				ImageIO.write(this.bi, "jpg", outputfile);
			} catch (IOException e) {
				logger.error("writeImageLocal error.", e);
			}
		}
	}

	/**
	 * 设定文字的字体等
	 */
	public void setFont(Font font) {

		this.font = font;
	}

	public void writeRightAlign(String strContent, int loc_X, int loc_Y, int offset, Font font, Color color){
		//画笔参数设定
		g = bi.createGraphics();
        g.setFont(font);
        g.setColor(color);
        
        //获取字符串 字符的总宽度
        int strWidth = getStringLength(strContent);
        
        //每一行字符串宽度
        int rowWidth = imageWidth;
        
        //字符串总个数
        logger.debug("字符串总个数:"+strContent.length());
        
        //填写项目内容
        loc_X = rowWidth - strWidth - offset;
        g.drawString(strContent, loc_X, loc_Y);        
    }
	
	/**
	 * 书写主文本（抬头+内容）
	 * @param strItem 抬头
	 * @param strContent 内容
	 * @param loc_X 内容坐标X
	 * @param loc_Y 内容坐标Y (0：高度为自适配)
	 * @param font
	 * @param color
	 */
	public void writeContent(String strItem, String strContent, int loc_X, int loc_Y, Font font, Color color){
		//画笔参数设定
		g = bi.createGraphics();
        g.setFont(font);
        g.setColor(color);
        
        //获取字符串 字符的总宽度
        int strWidth = getStringLength(strContent);
        
        //设定内容起始纵坐标
        if (this.contentPosH == 0 && loc_Y > 0) {
        	this.contentPosH = loc_Y;
        } else {
        	loc_Y = this.contentPosH;
        }
        
        //每一行字符串宽度
        int rowWidth = imageWidth - CONTENT_INDENTATION;
        logger.debug("每行字符宽度:"+rowWidth);
        
        //获取字符高度
        int strHeight = getStringHeight();
        this.contentPosH = loc_Y + strHeight + 10;
        
        //字符串总个数
        logger.debug("字符串总个数:"+strContent.length());
        
        //填写项目内容
        g.drawString(strItem, loc_X - ITEM_WIDTH, loc_Y);
        
        if (strWidth > rowWidth) {
            int rowstrnum = getRowStrNum(strContent.length(), rowWidth, strWidth); // 每行字符个数
            int rows = getRowsCount(strWidth, rowWidth); // 总行数
            String lineStr = "";
            for (int i = 0; i < rows; i++) {
                if(i == rows-1){
                    //最后一行
                	lineStr=strContent.substring(i*rowstrnum, strContent.length());
                } else {
                	lineStr=strContent.substring(i*rowstrnum, i*rowstrnum + rowstrnum);
                }
                if(i > 0){
                    //第一行不需要增加字符高度，以后的每一行在换行的时候都需要增加字符高度
                    loc_Y = loc_Y + strHeight;
                    this.contentPosH = loc_Y + strHeight + 10;
                }
                g.drawString(lineStr, loc_X, loc_Y);
            }
        } else {
            //直接绘制
            g.drawString(strContent, loc_X, loc_Y);
        }
        
    }
	
	/**
	 * 获取字符总宽度
	 * @param str
	 * @return
	 */
	private int getStringLength(String str) {
        char[]  strcha=str.toCharArray();
        int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
        logger.debug("字符总宽度:"+strWidth);
        return strWidth;
    }
	
	/**
	 * 获取每行字符个数
	 */
	private int getRowStrNum(int strnum, int rowWidth, int strWidth) {
		int rowstrnum=0;
		rowstrnum=(rowWidth*strnum)/strWidth;
		logger.debug("每行的字符数:"+rowstrnum);
		return rowstrnum;
	}
	
	/**
	 * 计算总行数
	 * @param strWidth
	 * @param rowWidth
	 * @return
	 */
	private int getRowsCount(int strWidth, int rowWidth){
        int rows=0;
        if(strWidth%rowWidth>0){
            rows=strWidth/rowWidth+1;
        }else{
            rows=strWidth/rowWidth;
        }
        logger.debug("行数:"+rows);
        return rows;
    }
	
	private int getStringHeight() {
        int height = g.getFontMetrics().getHeight();
        logger.debug("字符高度:"+height);
        return height;
    }
	
	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 */
	public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y, Color color) {
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.BLUE);

			// g.setColor(new Color(120, 120, 110));//设置字体颜色
			g.setColor(color);// 设置字体颜色
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g.setStroke(new BasicStroke(3));
			if (this.font != null)
				g.setFont(this.font);
			if (content != null) {
				g.translate(0, 0);
				// g.rotate(8 * Math.PI / 180);
				g.drawString(content.toString(), x, y);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 *
	 * 时间:2007-10-8
	 *
	 * @param img
	 * @return
	 */
	public BufferedImage modifyImageYe(BufferedImage img) {

		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.blue);// 设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {

		try {
			int w = b.getWidth();
			int h = b.getHeight();
			g = d.createGraphics();
			g.drawImage(b, 100, 10, w, h, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return d;
	}

	/***
	 * 插入描边的字体
	 * 
	 * @param img
	 * @param content
	 * @param w
	 * @param h
	 * @return
	 */
	public BufferedImage modifyShapImg(BufferedImage img, String content, int w, int h, Color color) {
		// int w = img.getWidth();
		// int h = img.getHeight();
		g = img.createGraphics();

		// Font f = new Font("Courier New", Font.BOLD, 140);
		GlyphVector v = font.createGlyphVector(g.getFontMetrics(font).getFontRenderContext(), content);
		Shape shape = v.getOutline();
		if (content != null) {
			g.translate(w, h);
			// g.rotate(8 * Math.PI / 180);
			// g.drawString(content.toString(), x, y);
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setColor(color);
		g.fill(shape);
		//g.setColor(new Color(248, 248, 255));
		g.setColor(color);
		g.setStroke(new BasicStroke(2));
		g.draw(shape);

		return img;
	}
	
	
	
}
