package com.kexin.photoshop;

import java.io.File;

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

}
