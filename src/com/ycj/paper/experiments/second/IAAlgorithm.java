package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class IAAlgorithm {

	/*
	 * �������ܷ���Ҳ������ͨ���㷨immediate acceptance��IA for short����DR�û�����ԭ�򣬴�DR����������ѡ��һ��DR�û���
	 * ��DR�û�����һ����������ʱ��DS�û����������DS�û�����ƥ�䣻DS�û�����ԭ�򣬴�DS����������ѡ��һ��DS�û�����DR�����е�һ
	 * �����������ʱ���DR�û�����ƥ�䣻
	 * ���ߵ�DS�û�����һ��������������ʱ���DR�û����������DR�û�����ƥ�䡣
	 * 
	 * 
	 */
	

	//��IA�㷨�У�DS�û����ȵ�ƥ���㷨
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for (int i=0;i<list_ds_original.size();i++){
			boolean isNotMatch=false;
			for(int j=0;j<list_dr_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(j), list_ds_original.get(i))){
					isNotMatch=true;
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(j), list_ds_original.get(i));
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(j),  list_ds_original.get(i));
					drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(j),  list_ds_original.get(i));
					dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(j),  list_ds_original.get(i));
					successMatchUsers++;
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
	
	//����ͨ�㷨�У�DR�û����ȵ�ƥ���㷨
	public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		int failTotal=0;
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for (int i=0;i<list_dr_original.size();i++){
			boolean isNotMatch=false;
			for(int j=0;j<list_ds_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(i), list_ds_original.get(j))){
					isNotMatch=true;
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(i), list_ds_original.get(j));
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(i), list_ds_original.get(j));
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(i),  list_ds_original.get(j));
					drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(i),  list_ds_original.get(j));
					dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(i),  list_ds_original.get(j));
					successMatchUsers++;
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
