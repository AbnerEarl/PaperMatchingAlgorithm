package com.ycj.paper.experiments;

import java.util.ArrayList;
import java.util.HashMap;

public class TwoEndsMatchingAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
	 * 6������ƥ���㷨Two Ends Matching��TEM for short�����ֱ��DR�û�������Ҫ��������С��������
	 * ��DS�û����չ����������С��������DR�û�����ԭ�����ȴ�DR������ѡ���һ��DR�û���
	 * ��DS�����д�ͷ�����ҵ�һ����������ʱ���DS�û�����ƥ�䣬�ٴ��ô�DR������ѡȡ���һ��DR�û���
	 * ��DS�����д�β������һ����������ʱ���DS�û�����ƥ�䣬�ظ�����Ĳ��裬ֱ��ƥ����ɣ�DS�û�����ԭ��
	 * ���ȴ�DS������ѡ���һ��DS�û�����DR�����д�ͷ�����ҵ�һ����������ʱ���DR�û�����ƥ�䣬�ٴ��ô�DS������ѡȡ���һ��DS�û���
	 * ��DR�����д�β������һ����������ʱ���DR�û�����ƥ�䣬�ظ�����Ĳ��裬ֱ��ƥ����ɡ�
	 * 
	 */
	
	//������ƥ���㷨�У�DS�û����ȵ�ƥ���㷨
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
	
	//������ƥ���㷨�У�DR�û����ȵ�ƥ���㷨
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
