package com.kexin.photoshop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @Description:图片处理工具
 * @author:liuyc
 * @time:2016年5月27日 上午10:18:00
 */
public class ImageHandleHelper {

	/**
	 * @Description:截图
	 * @author:liuyc
	 * @time:2016年5月27日 上午10:18:23
	 * @param srcFile源图片
	 *            、targetFile截好后图片全名、startAcross
	 *            开始截取位置横坐标、StartEndlong开始截图位置纵坐标、width截取的长，hight截取的高
	 */
	public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width,
			int hight) throws Exception {
		// 取得图片读入器
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = readers.next();
		// 取得图片读入流
		InputStream source = new FileInputStream(srcFile);
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		// 图片参数对象
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
	}

	/**
	 * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:52:24
	 * @param files
	 *            要拼接的文件列表
	 * @param type1
	 *            横向拼接， 2 纵向拼接
	 */
	public static void mergeImage(String[] files, int type, String targetFile) {
		int len = files.length;
		if (len < 1) {
			throw new RuntimeException("图片数量小于1");
		}
		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(files[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];
			ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
		}
		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			// 横向
			if (type == 1) {
				newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
				newWidth += images[i].getWidth();
			} else if (type == 2) {// 纵向
				newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
				newHeight += images[i].getHeight();
			}
		}
		if (type == 1 && newWidth < 1) {
			return;
		}
		if (type == 2 && newHeight < 1) {
			return;
		}

		// 生成新图片
		try {
			BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			int width_i = 0;
			for (int i = 0; i < images.length; i++) {
				if (type == 1) {
					ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
							images[i].getWidth());
					width_i += images[i].getWidth();
				} else if (type == 2) {
					ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
					height_i += images[i].getHeight();
				}
			}
			// 输出想要的图片
			ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:小图片贴到大图片形成一张图(合成)
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:51:20
	 */
	public static final void overlapImage(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final void overlapImage(String bigPath, String smallPath, String outFile, int x, int y) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();

			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static final void overlapImage(String bigPath, String smallPath, OutputStream os, int x, int y) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();

			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, bigPath.split("\\.")[1], os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String zipImageFile(File oldFile, File newFile, int width, int height, float quality) {
		if (oldFile == null) {
		return null;
		}
		try {
		/** 对服务器上的临时文件进行处理 */
		Image srcFile = ImageIO.read(oldFile);
		int w = srcFile.getWidth(null);
		int h = srcFile.getHeight(null);
		double bili;
		if (width > 0) {
		bili = width / (double) w;
		height = (int) (h * bili);
		} else {
		if (height > 0) {
		bili = height / (double) h;
		width = (int) (w * bili);
		}
		}

		String srcImgPath = newFile.getAbsoluteFile().toString();
		// System.out.println(srcImgPath);
		String subfix = "jpg";
		subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1, srcImgPath.length());

		BufferedImage buffImg = null;
		if (subfix.equals("png")) {
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}

		Graphics2D graphics = buffImg.createGraphics();
		graphics.setBackground(new Color(255, 255, 255));
		graphics.setColor(new Color(255, 255, 255));
		graphics.fillRect(0, 0, width, height);
		graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

		ImageIO.write(buffImg, subfix, new File(srcImgPath));

		} catch (Exception e) {
		e.printStackTrace();
		}
		return newFile.getAbsolutePath();
		}
	
	public static void resize(File originalFile, File resizedFile,  
            int newWidth, float quality) throws IOException {  
  
        if (quality > 1) {  
            throw new IllegalArgumentException(  
                    "Quality has to be between 0 and 1");  
        }  
  
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());  
        Image i = ii.getImage();  
        Image resizedImage = null;  
  
        int iWidth = i.getWidth(null);  
        int iHeight = i.getHeight(null);  
  
        if (iWidth > iHeight) {  
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)  
                    / iWidth, Image.SCALE_SMOOTH);  
        } else {  
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,  
                    newWidth, Image.SCALE_SMOOTH);  
        }  
  
        // This code ensures that all the pixels in the image are loaded.  
        Image temp = new ImageIcon(resizedImage).getImage();  
  
        // Create the buffered image.  
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);  
  
        // Copy image to buffered image.  
        Graphics g = bufferedImage.createGraphics();  
  
        // Clear background and paint the image.  
        g.setColor(Color.white);  
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
        g.drawImage(temp, 0, 0, null);  
        g.dispose();  
  
        // Soften.  
        float softenFactor = 0.05f;  
        float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
        Kernel kernel = new Kernel(3, 3, softenArray);  
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
        bufferedImage = cOp.filter(bufferedImage, null);  
  
        // Write the jpeg to a file.  
        FileOutputStream out = new FileOutputStream(resizedFile);  
  
        // Encodes image as a JPEG data stream  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
  
        JPEGEncodeParam param = encoder  
                .getDefaultJPEGEncodeParam(bufferedImage);  
  
        param.setQuality(quality, true);  
  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(bufferedImage);  
    } //

	public static void main(String[] args) throws Exception {
		/*
		 * String outFile = "C:\\Users\\Administrator\\Desktop\\out.png"; String
		 * smallPath =
		 * "C:\\Users\\Administrator\\Desktop\\微信图片_20180322200100.png"; String
		 * bigPath = "C:\\Users\\Administrator\\Desktop\\1521720100.png";
		 * ImageHandleHelper.overlapImage(bigPath, smallPath, outFile,-100,0);
		 */
		String path = "C:\\Users\\Administrator\\Desktop\\tupian\\";
		String cutPath = "C:\\Users\\Administrator\\Desktop\\tupian\\cut\\";
		String water = "C:\\Users\\Administrator\\Desktop\\tupian\\0323\\big.png";
		String orignla = "C:\\Users\\Administrator\\Desktop\\tupian\\newla.png";
		String la = "C:\\Users\\Administrator\\Desktop\\tupian\\cut\\lacut.png";
		//ImageHandleHelper.zipImageFile(new File(orignla), new File(la), 200,200, 0.5f);
		for (int i = 1; i <= 9; i++) {
			String source = path +  i + ".jpg";
			String target = cutPath + i + "cut.jpg";
			int width =  1080;
			ImageHandleHelper.cutImage(source, target, 0, 498, width, 810);
		//	String water = "water";
			String addWater = cutPath + "0323\\" +i + "milk1.jpg";
			//if(i<10)
				ImageHandleHelper.overlapImage(target, water, addWater,670,130);
			
				String addLa = cutPath + "0323\\" +i + "milk_la1.jpg";
		//	if(i<10)
		//		ImageHandleHelper.overlapImage(addWater, la, addLa,600,610);
			//else
				ImageHandleHelper.overlapImage(addWater, la, addLa,550,610);
				
		}
	}

}