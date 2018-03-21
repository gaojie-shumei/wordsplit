 /** 
 * Project Name:wordsplit 
 * File Name:StatWordCount.java 
 * Package Name:com.gaojie.wordsplit.server 
 * Date:2018年2月27日下午1:56:08 
 * Copyright (c) 2018, taoge@tmd.me All Rights Reserved. 
 * 
 */  
  
package com.gaojie.wordsplit.server;  
/** 
 * ClassName:StatWordCount <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年2月27日 下午1:56:08 <br/> 
 * @author   Administrator 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csu202.ExtractTxtFromSomeFile.ExtractToStr;
//import com.gaojie.wordsplit.util.ExtractUtil;
import com.gaojie.wordsplit.util.WordFrequencyStatisticsUtil;

public class StatWordCount {
	static String inputbasedir = "F:/体育专业博士论文";
	static Map<String, Double> wordCount = new HashMap<>();//map中的String存储词汇，Double是该词在startYear和endYear阶段每篇文章对应词的TfIDF值之和
	//static Map<String, List<Long>> newwordCount = new HashMap<>();//map中的String存储词汇，List中有两个值，第一个是所有文章中该词出现的次数，第二个是该词出现的文章数
	static List<File> filelist = new ArrayList<>();//存储所有的输入word文档
	
	/**
	 * 提取所有word文档的内容，并分别对各个文章进行词频统计(未归一化)
	 */
	public static void Extract_WordFrequencyStat(int startYear,int endYear){
		try {
			File inputBaseFile = new File(inputbasedir);
			WordFrequencyStatisticsUtil.FileList(inputBaseFile,filelist);
//		System.out.println(filelist.size());
			for (File f : filelist) {
				for(int year = startYear;year<=endYear;year++){
					if(f.getName().contains(year+"")){
						String content = new ExtractToStr().extractToString(f.getAbsolutePath());//ExtractUtil.ExtractToString(f.getAbsolutePath());
						WordFrequencyStatisticsUtil.StatWordFrequency(content, f.getName().substring(0, f.getName().lastIndexOf(".")));
						break;
					}
				}
			}
			WordFrequencyStatisticsUtil.CountTFIDF(startYear, endYear);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 统计所有文章的词频
	 */
	public static void StatWordCount(int startYear,int endYear){
		try {
			String projectpath = StatWordCount.class.getResource("").getPath();
			projectpath = projectpath.substring(1, projectpath.indexOf("wordsplit") + 10);
			File targetbase = new File(projectpath+"WordFrequencyStatistics");
			File[] listfiles = targetbase.listFiles();
			for (int i = 0; i < listfiles.length; i++) {
				for(int year=startYear;year<=endYear;year++){
					if(listfiles[i].getName().contains(""+year)){
						if(listfiles[i].isDirectory()){
							continue;
						}
						String content = new ExtractToStr().extractToString(listfiles[i].getAbsolutePath());
						//System.out.println(content);
						String[] strings = content.split("\n");
						if(i==0){
							for (String s : strings) {
								String[] split = s.split(" ");
								if(split.length>1){
									if(split[0].length()>1){
										if(split[1].length()>1){
										//System.out.println(Long.valueOf(s.split(" ")[1].substring(0, s.split(" ")[1].length()-1)));
											wordCount.put(split[0], Double.valueOf(split[1].substring(0, split[1].length()-1)));
										}
									}
								}
							}
						}else{
							Set<String> keySet = wordCount.keySet();
							for (String s : strings){
								String[] split = s.split(" ");
								if(split.length>1){
									if(split[0].length()>1){
										if(keySet.contains(split[0])){
											if(split[1].length()>1){
												wordCount.replace(split[0], wordCount.get(split[0]), wordCount.get(split[0])+Double.valueOf(split[1].substring(0, split[1].length()-1)));
											}
										}else{
											if(split[1].length()>1){
												wordCount.put(split[0], Double.valueOf(split[1].substring(0, split[1].length()-1)));
											}
										}
									}
								}
							}
						}
						break;
					}
					
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主入口函数
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String projectpath = StatWordCount.class.getResource("").getPath();
			projectpath = projectpath.substring(1, projectpath.indexOf("wordsplit") + 10);
			int startYear = 2000;
			int endYear = 2005;
			Extract_WordFrequencyStat(startYear,endYear);
			StatWordCount(startYear,endYear);
			
			File resultFile = new File(projectpath+"WordFrequencyStatistics"+"/result/"+startYear+"-"+endYear+".txt");
			if(!resultFile.exists()){
				resultFile.createNewFile();
			}
			WordFrequencyStatisticsUtil.saveFile(wordCount, resultFile,"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.exit(0);
	}
	
}
  