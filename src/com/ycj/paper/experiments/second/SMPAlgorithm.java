package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class SMPAlgorithm {

	/*
	 * 稳定搭配算法Stable Matching Proglem（SMP for short），稳定匹配分为弱稳定、一般稳定、强稳定，此处使用一般的稳定匹配：
	 * DR用户优先原则，分别计算出每个DR用户对DS用户集合的一个偏好程度的排序集合RS，
	 * 每个DR用户从它偏好的RS集合中选取第一个且没有匹配的DS用户进行匹配；DS用户优先原则，分别计算出每个DS用户对DR用户集合的一个偏好程度的排序集合SR，
	 * 每个DS用户从它偏好的SR集合中选取第一个且没有匹配的DR用户进行匹配。
	 * 
	 * 
	 */

	
	//在SMP算法中，DS用户优先的匹配算法
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for(int i=0;i<list_ds_original.size();i++){
			float loveDegree=0;
			int selectedDRTag=-1;
			for(int j=0;j<list_dr_original.size();j++){
				
				float temLoveDegree=Tools.loveDegreeDRToDS(list_dr_original.get(j),list_ds_original.get(i));
				if(temLoveDegree>loveDegree){
					selectedDRTag=j;
					loveDegree=temLoveDegree;
				}
				if(loveDegree>=1){
					break;
				}
				
			}
			if(selectedDRTag!=-1){
				successMatchUsers++;
				dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(i));
				drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(i));
				dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(i));
				drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(i));
				dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(i));
				list_dr_original.remove(selectedDRTag);
			}
		}
		DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;
	}
	
	//在SMP算法中，DR用户优先的匹配算法
	public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		int failTotal=0;
		for(int i=0;i<list_dr_original.size();i++){
			float loveDegree=0;
			int selectedDSTag=-1;
			for(int j=0;j<list_ds_original.size();j++){
				
				float temLoveDegree=Tools.loveDegreeDSToDR(list_ds_original.get(j),list_dr_original.get(i));
				if(temLoveDegree>loveDegree){
					selectedDSTag=j;
					loveDegree=temLoveDegree;
				}
				if(loveDegree>=1){
					break;
				}
				
			}
			if(selectedDSTag!=-1){
				successMatchUsers++;
				dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(i), list_ds_original.get(selectedDSTag));
				drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(i), list_ds_original.get(selectedDSTag));
				dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(i),  list_ds_original.get(selectedDSTag));
				drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(i),  list_ds_original.get(selectedDSTag));
				dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(i),  list_ds_original.get(selectedDSTag));
				list_ds_original.remove(selectedDSTag);
			}else{
				failTotal++;
			}
		}
		DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;
	}
}
