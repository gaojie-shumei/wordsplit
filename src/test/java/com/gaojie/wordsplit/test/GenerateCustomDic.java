/** 
* Project Name:wordsplit 
* File Name:GenerateCustomDic.java 
* Package Name:com.gaojie.wordsplit.test 
* Date:2018年3月18日下午1:47:56 
* Copyright (c) 2018, taoge@tmd.me All Rights Reserved. 
* 
*/

package com.gaojie.wordsplit.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.csu202.ExtractTxtFromSomeFile.ExtractToStr;
import com.gaojie.wordsplit.server.StatWordCount;

/**
 * ClassName:GenerateCustomDic <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月18日 下午1:47:56 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class GenerateCustomDic {

	static List<File> filelist = new ArrayList<>();// 存储所有的生成词库所需文件
	static List<String> diclist = new ArrayList<>();// 将获取的词库存入diclist

	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			File f = new File("F:/张老师数据统计相关资料/ForGenerateDic");
			if (!f.exists()) {
				f.mkdirs();
			}
			// System.out.println(f.exists());
			FileList(f);
			// File file = filelist.get(0);
			for (File file : filelist) {
				System.out.println(file.getName());
				String content = new ExtractToStr().extractToString(file.getAbsolutePath());
				String[] contentarr = content.split("\r\n");
				// System.out.println(contentarr.length);
				for (String s : contentarr) {
					if (s.startsWith("K1")) {
						String[] words = s.substring(3).split(";");
						for (String word : words) {
							if (!"".equals(word.trim())) {
								diclist.add(word.trim());
							}
						}
					}
				}

			}
			// for (String s : diclist) {
			// System.out.println(s);
			// }
//			 System.out.println(diclist.toString());
			String projectpath = GenerateCustomDic.class.getResource("").getPath();
			projectpath = projectpath.substring(1, projectpath.indexOf("wordsplit") + 10);
			File outputfile = new File(projectpath + "src/main/resource/custom_dic.txt");
			if (!outputfile.exists()) {
				outputfile.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(outputfile);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
			String outputstr = diclist.toString();
			outputstr = outputstr.replace("[", "");
			outputstr = outputstr.replace("]", "");
			outputstr = outputstr.replace(", ", "\r\n");
			bw.write(outputstr);
			bw.flush();
			bw.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取所有的生成词库所需文件
	public static void FileList(File inputBaseFile) {
		File[] listfiles = inputBaseFile.listFiles();
		for (File f : listfiles) {
			if (f.isDirectory()) {
				FileList(f);
			} else {
				if (f.getName().substring(f.getName().lastIndexOf(".")).contains("txt")) {
					filelist.add(f);
				}
			}
		}
	}

}
