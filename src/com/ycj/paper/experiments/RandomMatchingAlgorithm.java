package com.ycj.paper.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomMatchingAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
	 * 
	 * 5、随机匹配算法Random Matching （RM for short）：DR用户优先原则，一个DR用户随机的从DS集合中选择一个满足容忍时间的DS用户，
	 * 与这个DS用户进行匹配；DS用户优先原则，一个DS用户随机的从DR集合中选择一个满足容忍时间的DR用户，与这个DR用户进行匹配。
	 * 
	 */

	//在随机匹配算法中，DS用户优先的匹配算法
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		Random random=new Random();
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for(int i=0;i<list_ds_original.size();i++){
			ArrayList<HashMap<String,String>> SR=new ArrayList<>();
			for (int j=0;j<list_dr_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(j), list_ds_original.get(i))){
					SR.add(list_dr_original.get(j));
				}
			}
			if(SR.size()>0){
				HashMap<String,String> DRUser=SR.get(random.nextInt(SR.size()));
				dfferenceValue+=Tools.computeDifferenceValue(DRUser, list_ds_original.get(i));
				drExpectValue+=Tools.computeDRExpectValue(DRUser, list_ds_original.get(i));
				dsExpectValue+=Tools.computeDSExpectValue(DRUser,  list_ds_original.get(i));
				drCostsValue+=Tools.computeDRCostsValue(DRUser,  list_ds_original.get(i));
				dsGainsValue+=Tools.computeDSGainsValue(DRUser,  list_ds_original.get(i));
				successMatchUsers++;
				list_dr_original.remove(DRUser);
				SR.clear();
			}
		}
		DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;
	}
	
	//在随机匹配算法中，DR用户优先的匹配算法
	public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		int failTotal=0;
		int successMatchUsers=0;
		Random random=new Random();
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for(int i=0;i<list_dr_original.size();i++){
			ArrayList<HashMap<String,String>> RS=new ArrayList<>();
			for (int j=0;j<list_ds_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(i), list_ds_original.get(j))){
					RS.add(list_ds_original.get(j));
				}
			}
			if(RS.size()>0){
				successMatchUsers++;
				HashMap<String,String> DSUser=RS.get(random.nextInt(RS.size()));
				dfferenceValue+=Tools.computeDifferenceValue( list_dr_original.get(i),DSUser);
				drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(i), DSUser);
				dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(i), DSUser);
				drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(i), DSUser);
				dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(i), DSUser);
				list_ds_original.remove(DSUser);
				RS.clear();
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
