/** 
* Project Name:wordsplit 
* File Name:SplitWordUtil.java 
* Package Name:com.gaojie.wordsplit.util 
* Date:2018年2月27日下午1:55:13 
* Copyright (c) 2018, taoge@tmd.me All Rights Reserved. 
* 
*/

package com.gaojie.wordsplit.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apdplat.word.WordFrequencyStatistics;

import com.csu202.ExtractTxtFromSomeFile.ExtractToStr;
import com.gaojie.wordsplit.server.StatWordCount;

/**
 * ClassName:WordFrequencyStatisticsUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年2月27日 下午1:55:13 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class WordFrequencyStatisticsUtil {
	
	static String inputbasedir = "F:/体育专业博士论文";
	static List<File> filelist = new ArrayList<>();//存储所有的输入word文档
	
	// 统计一篇文章中文本内容的词频
	public static void StatWordFrequency(String content, String filename) {
		WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics();
		wordFrequencyStatistics.setRemoveStopWord(true);
		String projectpath = WordFrequencyStatisticsUtil.class.getResource("").getPath();
		projectpath = projectpath.substring(1, projectpath.indexOf("wordsplit") + 10);
		File f = new File(projectpath + "WordFrequencyStatistics" + "/" + filename + ".txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		wordFrequencyStatistics.setResultPath(projectpath + "WordFrequencyStatistics" + "/" + filename + ".txt");
		// wordFrequencyStatistics.setSegmentationAlgorithm(SegmentationAlgorithm.MaxNgramScore);
		// 开始分词
		wordFrequencyStatistics.seg(content);
		// 输出词频统计结果
		wordFrequencyStatistics.dump();
	}

	public static void CountTFIDF(int startYear, int endYear) {// 不带后缀,起止年限是获取计算IDF值时的全集合是哪些
		
		try {
			ExtractToStr extractToStr = new ExtractToStr();
			String projectpath = StatWordCount.class.getResource("").getPath();
			projectpath = projectpath.substring(1, projectpath.indexOf("wordsplit") + 10);
			File targetbase = new File(projectpath+"WordFrequencyStatistics");
			File[] listfiles = targetbase.listFiles();
			Map<String, Double> wordIDF = new HashMap<>();
			int countAllFile = 0;
			for (int i = 0; i < listfiles.length; i++) {
				if(listfiles[i].isDirectory()){
					continue;
				}
				for(int year=startYear;year<=endYear;year++){
					if(listfiles[i].getName().contains(""+year)){
						String content = extractToStr.extractToString(listfiles[i].getAbsolutePath());
						//System.out.println(content);
						String[] strings = content.split("\n");
						if(i==0){
							for (String s : strings) {
								if(s.split(" ")[0].length()>1){
									wordIDF.put(s.split(" ")[0], 1.0);
								}
							}
						}else{
							Set<String> keySet = wordIDF.keySet();
							for (String s : strings){
								if(s.split(" ")[0].length()>1){
									if(keySet.contains(s.split(" ")[0])){
										wordIDF.replace(s.split(" ")[0], wordIDF.get(s.split(" ")[0]), wordIDF.get(s.split(" ")[0])+1);
									}else{
										wordIDF.put(s.split(" ")[0], 1.0);
									}
								}
							}
						}
						
						countAllFile++;
						break;
					}
				}
			}
			if(countAllFile==0){
				countAllFile = 1;
			}
			Map<String, Double> wordTFIDF = new HashMap<>();
			for (int i = 0; i < listfiles.length; i++) {
				if(listfiles[i].isDirectory()){
					continue;
				}
				for(int year=startYear;year<=endYear;year++){
					if(listfiles[i].getName().contains(""+year)){
						String content = extractToStr.extractToString(listfiles[i].getAbsolutePath());
						//System.out.println(content);
						String[] strings = content.split("\n");
						for (String s : strings) {
							if(s.split(" ")[0].length()>1){
								wordTFIDF.put(s.split(" ")[0], Double.valueOf(s.split(" ")[1].substring(0, s.split(" ")[1].length()-1)));
							}
						}
						double sumValue = 0;
						Set<String> set = wordTFIDF.keySet();
						for (String s : set) {
							sumValue +=wordTFIDF.get(s);
						}
						if(sumValue==0){
							sumValue=1;
						}
						for (String s : set) {
							wordTFIDF.replace(s, wordTFIDF.get(s), Double.valueOf(String.format("%.4f",(wordTFIDF.get(s)/sumValue)*(Math.log(((double)countAllFile)/wordIDF.get(s))))));
						}
						saveFile(wordTFIDF, listfiles[i]);
						break;
					}
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T extends Number> void saveFile(Map<String, T> map,File f){//f是要存到的文件
		try {
			Map<String, T> sortedMap = sortMapByValue(map);
			
			//System.out.println(sortedMap.toString());
			FileOutputStream out = new FileOutputStream(f);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
			String resultcontent = sortedMap.toString();
			resultcontent = resultcontent.replace("{", "");
			resultcontent = resultcontent.replace("}", "");
			resultcontent = resultcontent.replace(", ", "\r\n");
			resultcontent = resultcontent.replace("=", " ");
			bw.write(resultcontent);
			bw.flush();
			bw.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//获取所有的word文档
		public static void FileList(File inputBaseFile,List<File> filelist){
			File[] listfiles = inputBaseFile.listFiles();
			for (File f : listfiles) {
				if(f.isDirectory()){
					FileList(f,filelist);
				}else{
					if(f.getName().substring(f.getName().lastIndexOf(".")).contains("doc")){
						filelist.add(f);
					}
				}
			}
		}
	
	
	
	/**
     * 使用 Map按value进行排序
     * @param map
     * @return
     */
    public static <T extends Number> Map<String, T> sortMapByValue(Map<String, T> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, T> sortedMap = new LinkedHashMap<String, T>();
        List<Map.Entry<String, T>> entryList = new ArrayList<Map.Entry<String, T>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, T>> iter = entryList.iterator();
        Map.Entry<String, T> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
    
	/**
     * 使用 Map按listvalue进行排序
     * @param map
     * @return
     */
    public static Map<String, List<Long>> sortMapByListValue(Map<String, List<Long>> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, List<Long>> sortedMap = new LinkedHashMap<String, List<Long>>();
        List<Map.Entry<String, List<Long>>> entryList = new ArrayList<Map.Entry<String, List<Long>>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapListValueComparator());

        Iterator<Map.Entry<String, List<Long>>> iter = entryList.iterator();
        Map.Entry<String, List<Long>> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}

/**
 * 对map进行以value值大小排序
 * ClassName: MapValueComparator <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年3月3日 下午5:28:23 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.8
 */
class MapValueComparator<T extends Number> implements Comparator<Map.Entry<String, T>> {

    @Override
    public int compare(Entry<String, T> me1, Entry<String, T> me2) {
    	if(me1.getValue() instanceof Long){
    		Long me1value = (Long) me1.getValue();
    		Long me2value = (Long) me2.getValue();
    		return me2value.compareTo(me1value);
    	}else if(me1.getValue() instanceof Float){
    		Float me1value = (Float) me1.getValue();
    		Float me2value = (Float) me2.getValue();
    		return me2value.compareTo(me1value);
    	}else if(me1.getValue() instanceof Double){
    		Double me1value = (Double) me1.getValue();
    		Double me2value = (Double) me2.getValue();
    		return me2value.compareTo(me1value);
    	}else if(me1.getValue() instanceof Integer){
    		Integer me1value = (Integer) me1.getValue();
    		Integer me2value = (Integer) me2.getValue();
    		return me2value.compareTo(me1value);
    	}else if(me1.getValue() instanceof Short){
    		Short me1value = (Short) me1.getValue();
    		Short me2value = (Short) me2.getValue();
    		return me2value.compareTo(me1value);
    	}else if(me1.getValue() instanceof Byte){
    		Byte me1value = (Byte) me1.getValue();
    		Byte me2value = (Byte) me2.getValue();
    		return me2value.compareTo(me1value);
    	}else{
    		return 0;
    	}
//        return me2.getValue().compareTo(me1.getValue());//降序
        //return me1.getValue().compareTo(me2.getValue());//升序
    }
}

/**
 * 对map进行以List<>value值大小排序
 * ClassName: MapListValueComparator <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年3月3日 下午5:28:23 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.8
 */
class MapListValueComparator implements Comparator<Map.Entry<String, List<Long>>> {

    @Override
    public int compare(Entry<String, List<Long>> me1, Entry<String, List<Long>> me2) {

    	Long me2value = me2.getValue().get(0)/me2.getValue().get(1);
    	Long me1value = me1.getValue().get(0)/me1.getValue().get(1);
        return me2value.compareTo(me1value);//降序
        //return me1.getValue().compareTo(me2.getValue());//升序
    }
}

