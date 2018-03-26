package com.kexin.photoshop;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class PhotoshopApplicationTests {

	@Test
	public void milk1merge() throws Exception {
			String rootPath = "C:\\Users\\Administrator\\Desktop\\pic\\";
			String srcPath = rootPath +"src\\";
			String targetPath = rootPath +"target\\";
			String lajiangPic = srcPath + "lajiang.png";
		
			//ImageHandleHelper.zipImageFile(new File(orignla), new File(la), 200,200, 0.5f);
			int i = 1;
			while(true) {
				String sourcePic = srcPath +  i + ".jpg";
				if(!new File(sourcePic).exists())
					break;
				String  addLajiang = targetPath + i + "+lajiang.jpg";
				ImageHandleHelper.overlapImage(sourcePic, lajiangPic, addLajiang,550,610);//增加辣酱
				i++;
			}
			i = 1;
			String milk = srcPath + "milk1.png";
			while(true) {
				String sourcePic = srcPath +  i + ".jpg";
				if(!new File(sourcePic).exists())
					break;
				String  addLajiang = targetPath + i + "+lajiang.jpg"; 
				String lajiangAddMilk = targetPath + i + "lajiang+milk.jpg";
				ImageHandleHelper.overlapImage(addLajiang, milk,lajiangAddMilk ,750,450);//增加饮料
				i++;
				
			}
			i = 1;
			String milk2 = srcPath + "milk2.png";
			while(true) {
				String sourcePic = srcPath +  i + ".jpg";
				if(!new File(sourcePic).exists())
					break;
				String  addLajiang = targetPath + i + "+lajiang.jpg"; 
				String lajiangAddMilk = targetPath + i + "lajiang+milk2.jpg";
				ImageHandleHelper.overlapImage(addLajiang, milk2,lajiangAddMilk ,750,420);//增加饮料
				i++;
				
			}
		
		
	}
	//@Test
	void cutImage() {
		
		
	
		
	}
public static void main(String[] args) {
	
	int width = 200;
	float quality;
	int height = 200;
	String old = "/Users/fengchuang1/eclipse-workspace/newtest.png";
	String newpng = "/Users/fengchuang1/eclipse-workspace/newtest.png";
	File newFile= new File("/Users/fengchuang1/eclipse-workspace/newtest.png");
	File oldFile = new File("/Users/fengchuang1/eclipse-workspace/test.png");
 //ImageHandleHelper.zipImageFile(oldFile , newFile, width, height, 0.5f);
	
		//ImageHandleHelper.reSizePicture(old, newpng, 200, 200);
		ImageHandleHelper.scalcPicture(old, newpng, 0.5f);
	
	
}
}
