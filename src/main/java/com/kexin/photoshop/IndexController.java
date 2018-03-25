package com.kexin.photoshop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(Model model) {

		List<String> list = new ArrayList<String>();
		int i = 1;
		while (true) {
			String sourcePic = srcPath + i + ".jpg";
			if (!new File(sourcePic).exists())
				break;
			String webPic = webPath + i + ".jpg";
			list.add(webPic);
			i++;

		}

		model.addAttribute("list", list);
		model.addAttribute("addPic1", webPath + "add1.png");
		model.addAttribute("addPic2", webPath + "add2.png");
		model.addAttribute("addPic3", webPath + "add3.png");
		return "index";
	}
	@RequestMapping("/del")
	public String del(Integer index) {
		index ++;
		int i = 1;
		String endPic = "";
		while (true) {
			endPic = srcPath + i + ".jpg";
			if (!new File(endPic).exists())
				break;
			i++;
		}
		int end = i;
		String sourcePic = srcPath + index + ".jpg";
		if (new File(sourcePic).exists())
			new File(sourcePic).delete();

		for(int j=index+1;j<end;j++){
			new File(srcPath + j + ".jpg").renameTo(new File(srcPath + (j-1) + ".jpg"));
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadSrc")
	public String uploadSrc(@RequestParam("fileSrc") MultipartFile fileSrc,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		int i = 1;
		String sourcePic = "";
		while (true) {
		     sourcePic = srcPath + i + ".jpg";
			if (!new File(sourcePic).exists())
				break;
			i++;
		}
		if (!fileSrc.isEmpty()) {
			File addPic1 = new File(sourcePic);
			if (addPic1.exists())
				addPic1.delete();
			try {
				Files.copy(fileSrc.getInputStream(), addPic1.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String handleFileUpload(@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String no = request.getParameter("no");
		if (!file1.isEmpty()) {
			File addPic1 = new File(addPicFile1);
			if (addPic1.exists())
				addPic1.delete();
			try {
				Files.copy(file1.getInputStream(), addPic1.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (!file2.isEmpty()) {
			try {
				File addPic2 = new File(addPicFile2);
				if (addPic2.exists())
					addPic2.delete();
				Files.copy(file2.getInputStream(), addPic2.toPath());
			} catch (IOException e) {
			}
		}
		return "redirect:/";
	}

	@RequestMapping("/preview")
	public void preview(Integer demoNo, Integer type, Integer x1, Integer y1,
			Integer x2, Integer y2, HttpServletResponse res) {

		String firstPic = srcPath + demoNo + ".jpg";
		String addPic1 = srcPath + "add1.png";
		String addPic2 = srcPath + "add2.png";
		String fileName = "upload.jpg";
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		OutputStream os = null;
		try {
			os = res.getOutputStream();
			if (type == 0)
				ImageHandleHelper.overlapImage(firstPic, addPic1, os, x1, y1);
			else {
				String tmpDir = srcPath + "tmp" + File.separatorChar;
				File tmpDirFile = new File(tmpDir);
				if (!tmpDirFile.exists())
					tmpDirFile.mkdirs();
				String tmpFile = tmpDir + UUID.randomUUID().toString() + ".jpg";
				ImageHandleHelper.overlapImage(firstPic, addPic1, tmpFile, x1,
						y1);
				ImageHandleHelper.overlapImage(tmpFile, addPic2, os, x2, y2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	@RequestMapping("/deal")
	public void deal(Integer demoNo, Integer type, Integer x1,
			Integer y1, Integer x2, Integer y2, HttpServletResponse res) {
		int i = 1;

		String addPic1 = srcPath + "add1.png";
		String addPic2 = srcPath + "add2.png";
		String fileName = "pic.zip";
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		OutputStream os;
		try {
			os = res.getOutputStream();

			ZipOutputStream zip = new ZipOutputStream(os);
			while (true) {
				String sourcePic = srcPath + i + ".jpg";
				if (!new File(sourcePic).exists())
					break;

				String firstPic = srcPath + demoNo + ".jpg";
				try {
					os = res.getOutputStream();
					zip.putNextEntry(new ZipEntry(i + ".jpg"));
					if (type == 0)
						ImageHandleHelper.overlapImage(firstPic, addPic1, zip,
								x1, y1);
					
					else {
						String tmpDir = srcPath + "tmp" + File.separatorChar;
						File tmpDirFile = new File(tmpDir);
						if (!tmpDirFile.exists())
							tmpDirFile.mkdirs();
						String tmpFile = tmpDir + UUID.randomUUID().toString()
								+ ".jpg";
						ImageHandleHelper.overlapImage(firstPic, addPic1,
								tmpFile, x1, y1);
						ImageHandleHelper.overlapImage(tmpFile, addPic2, zip,
								x2, y2);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {

				}
				i++;
			}
			IOUtils.closeQuietly(zip);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	

	public static final String srcPath = "/usr/share/nginx/pic/";
	public static final String webPath = "http://47.93.243.234/pic/";
	public static final String addPicFile1 = srcPath + "add1.png";
	public static final String addPicFile2 = srcPath + "add2.png";
}
