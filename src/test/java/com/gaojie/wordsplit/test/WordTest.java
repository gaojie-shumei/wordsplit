 /** 
 * Project Name:wordsplit 
 * File Name:WordTest.java 
 * Package Name:com.gaojie.wordsplit.test 
 * Date:2018年1月12日下午5:12:32 
 * Copyright (c) 2018, taoge@tmd.me All Rights Reserved. 
 * 
 */  
  
package com.gaojie.wordsplit.test;

import java.net.URL;
import java.util.List;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import com.gaojie.wordsplit.util.WordFrequencyStatisticsUtil;

/** 
 * ClassName:WordTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年1月12日 下午5:12:32 <br/> 
 * @author   Administrator 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class WordTest {

	public static void main(String[] args) {
		
		//移除停用词
//		List<Word> words = WordSegmenter.seg("我们要做好我们自己分内的事");
//		System.out.println("移除停用词:"+words);
//		//保留停用词
//		words = WordSegmenter.segWithStopWords("我们要做好我们自己分内的事");
//		System.out.println(words);
		String content = "当前1234，我国正处在由原计划经济向社会主义市场经济转变的重要时期。社会主义市场 经济体制的建立、政治体制改革的深入，改变了竞技体育运行的经济和政治条件。习惯于 计划经济体制下运行的中国竞技体育如何适应新的形势、新的社会环境，使中国的竞技体 育蓬勃发展，是社会关注的一个重大课题，也是体育的决策部门和体育界普遍关心的，以 及体育理论界一直在研究的课题。本文采用文献研究、专家访谈、理论分析与实证分析相结合和综合比较等研究方法， 从利益的角度，围绕经济体制转轨时期中国竞技体育运行模式的要旨，分析讨论了中国竞 技体育的利益格局与其运行的管理体制之间的关系及影响；分析了原计划经济体制和社会 主义市场经济体制对中国竞技体育运行的影响；揭示了“双重体制”下，中国竞技体育选 择目标运行模式的社会学和经济学因素。通过理论与实证分析，本文认为，竞技体育是通过竞技来追求利益的一种活动过程。 竞技体育的利益结构由利益主体结构、利益客体结构和利益空间结构组成。经济体制直接 影响着竞技体育利益格局的形成。原计划经济时期我国竞技体育运行的模式是政府干预型 模式。在实行市场经济体制的国家中，竞技体育运行的模式有：以社会指导和市场调节为 主的运行模式、以政府行政干预为主的运行模式、以政府行政干预与社会组织干预相结合 的运行模式三种。三种模式，在组织管理体系、法规体系和资源配置体系方面对竞技体育 运行的调控作用与调控强度不同，呈现出不同的特点。但三种模式正逐渐向市场调节的模 式转变。经济体制、政治体制和文化体制分别从深层、中层和浅层三个层面共同影响竞技 体育运行模式的选择。分析认为，经济体制转轨时期我国的竞技体育体制改革是一种渐进式改革，竞技体育 的运行模式是，以政府行政干预为主的模式，并在逐步向以政府行政干预与社会组织干预 相结合的运行模式发展。渐进式改革有利于中国竞技体育的稳步发展，有利于新形势下发 育竞技体育利益主体，但也存在一些问题，需要逐步完善。经济体制转轨时期，我国竞技体育的社会化不可能一步到位，其运行模式的选择不能 一概而论，应具有多元性。依据竞技运动项目的市场化程度分别采用不同的调控模式，对 于社会接受程度高的竞技运动项目，可采用社会投资，社会指导和市场调节的调控模式； 对于非市场化的优势竞技运动项目，可采用政府投资和行政干预为主的调控模式；对于那 些普及程度高，社会接受程度一般的，具有半市场化的运动竞技项目，可采用社会投资和 政府投资相结合的社会指导和政府行政干预结合型的模式。从社会的发展看，市场经济条 件下的中国竞技体育运行的目标模式应该是社会指导和市场调节的模式。 关键词：竞技体育、利益、运行模式、机制.";
		String filename = "2000-001";
		WordFrequencyStatisticsUtil.StatWordFrequency(content, filename);
		
	}

}
  