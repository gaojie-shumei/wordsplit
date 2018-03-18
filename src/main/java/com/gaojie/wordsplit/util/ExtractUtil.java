 /** 
 * Project Name:wordsplit 
 * File Name:ExtractUtil.java 
 * Package Name:com.gaojie.wordsplit.util 
 * Date:2018年2月27日下午1:54:39 
 * Copyright (c) 2018, taoge@tmd.me All Rights Reserved. 
 * 
 */  
  
package com.gaojie.wordsplit.util;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

/** 
 * ClassName:ExtractUtil <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年2月27日 下午1:54:39 <br/> 
 * @author   Administrator 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class ExtractUtil {
	public static String ExtractToString(String filepath){
		String content = "";
		try {
			File inputFile = new File(filepath);
			if(!inputFile.exists()){
				return content;
			}
			Tika tika = new Tika();
			tika.setMaxStringLength(999999999);
			content = tika.parseToString(inputFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
}
  