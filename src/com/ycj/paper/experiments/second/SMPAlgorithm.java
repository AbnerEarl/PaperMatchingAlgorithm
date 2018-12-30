package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class SMPAlgorithm {

	/*
	 * �ȶ������㷨Stable Matching Proglem��SMP for short�����ȶ�ƥ���Ϊ���ȶ���һ���ȶ���ǿ�ȶ����˴�ʹ��һ����ȶ�ƥ�䣺
	 * DR�û�����ԭ�򣬷ֱ�����ÿ��DR�û���DS�û����ϵ�һ��ƫ�ó̶ȵ����򼯺�RS��
	 * ÿ��DR�û�����ƫ�õ�RS������ѡȡ��һ����û��ƥ���DS�û�����ƥ�䣻DS�û�����ԭ�򣬷ֱ�����ÿ��DS�û���DR�û����ϵ�һ��ƫ�ó̶ȵ����򼯺�SR��
	 * ÿ��DS�û�����ƫ�õ�SR������ѡȡ��һ����û��ƥ���DR�û�����ƥ�䡣
	 * 
	 * 
	 */

	
	//��SMP�㷨�У�DS�û����ȵ�ƥ���㷨
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
	
	//��SMP�㷨�У�DR�û����ȵ�ƥ���㷨
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
