package com.ycj.paper.experiments;

import java.util.ArrayList;
import java.util.HashMap;

public class TwoEndsMatchingAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
	 * 6、两端匹配算法Two Ends Matching（TEM for short）：分别对DR用户按照需要的流量大小进行排序，
	 * 对DS用户按照共享的流量大小进行排序；DR用户优先原则，首先从DR集合中选择第一个DR用户，
	 * 在DS集合中从头依次找到一个满足容忍时间的DS用户进行匹配，再次用从DR集合中选取最后一个DR用户，
	 * 在DS集合中从尾依次找一个满足容忍时间的DS用户进行匹配，重复上面的步骤，直到匹配完成；DS用户优先原则，
	 * 首先从DS集合中选择第一个DS用户，在DR集合中从头依次找到一个满足容忍时间的DR用户进行匹配，再次用从DS集合中选取最后一个DS用户，
	 * 在DR集合中从尾依次找一个满足容忍时间的DR用户进行匹配，重复上面的步骤，直到匹配完成。
	 * 
	 */
	
	//在两端匹配算法中，DS用户优先的匹配算法
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		boolean change=false;
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		while(list_ds_original.size()>0){
			HashMap<String,String> DSUser;
			int totalDR=list_ds_original.size();
			if(!change){
				DSUser=list_ds_original.remove(0);
				change=true;
			}else{
				DSUser=list_ds_original.remove(totalDR-1);
				change=false;
			}
			
			for(int j=0;j<list_dr_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(j), DSUser)){
					successMatchUsers++;
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(j), DSUser);
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(j), DSUser);
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(j),  DSUser);
					drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(j),  DSUser);
					dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(j),  DSUser);
					list_dr_original.remove(j);
					break;
				}
			}
			
		}
		
		DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;
	}
	
	//在两端匹配算法中，DR用户优先的匹配算法
	public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		boolean change=false;
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		int failTotal=0;
		while(list_dr_original.size()>0){
			HashMap<String,String> DRUser;
			int totalDR=list_dr_original.size();
			if(!change){
				DRUser=list_dr_original.remove(0);
				change=true;
			}else{
				DRUser=list_dr_original.remove(totalDR-1);
				change=false;
			}
			boolean isNotMatch=false;
			for(int j=0;j<list_ds_original.size();j++){
				if(Tools.isNotToleranceTime(DRUser,list_ds_original.get(j))){
					isNotMatch=true;
					successMatchUsers++;
					dfferenceValue+=Tools.computeDifferenceValue(DRUser,list_ds_original.get(j));
					drExpectValue+=Tools.computeDRExpectValue(DRUser, list_ds_original.get(j));
					dsExpectValue+=Tools.computeDSExpectValue(DRUser,  list_ds_original.get(j));
					drCostsValue+=Tools.computeDRCostsValue(DRUser,  list_ds_original.get(j));
					dsGainsValue+=Tools.computeDSGainsValue(DRUser,  list_ds_original.get(j));
					list_ds_original.remove(j);
					break;
				}
			}
			
			if(!isNotMatch){
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
