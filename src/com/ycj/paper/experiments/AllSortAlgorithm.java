package com.ycj.paper.experiments;

import java.util.ArrayList;
import java.util.HashMap;

public class AllSortAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
	 * 3��ȫ������㷨All Sort��AS for short�����ֱ��DR�û�������Ҫ��������С�������򣬶�DS�û����չ����������С��������DR�û�����ԭ��
	 * ��һ��DR�û�����һ����������ʱ��DS�û����������DS�û�����ƥ�䣻DS�û�����ԭ�򣬵�һ��DS�û�����һ��������������ʱ���DR�û���
	 * �������DR�û�����ƥ�䡣
	 * 
	 */

	//��ȫ�����㷨�У�DS�û����ȵ�ƥ���㷨
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0;
		for (int i=0;i<list_ds_original.size();i++){
			boolean isNotMatch=false;
		
			for(int j=0;j<list_dr_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(j), list_ds_original.get(i))){
					isNotMatch=true;
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(j), list_ds_original.get(i));
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(j), list_ds_original.get(i));
					list_dr_original.remove(j);
					break;
				}
			}
			
		}
		
		DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.sum_contary[index]+=dfferenceValue;
	}

		//��ȫ�����㷨�У�DR�û����ȵ�ƥ���㷨
		public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
			ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
			ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
			
			int failTotal=0;
			float dfferenceValue=0,drExpectValue=0,dsExpectValue=0;
			for (int i=0;i<list_dr_original.size();i++){
				boolean isNotMatch=false;
				for(int j=0;j<list_ds_original.size();j++){
					if(Tools.isNotToleranceTime(list_dr_original.get(i), list_ds_original.get(j))){
						isNotMatch=true;
						dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(i), list_ds_original.get(j));
						drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(i), list_ds_original.get(j));
						dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(i),  list_ds_original.get(j));
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
			DataResult.sum_contary[index]+=dfferenceValue;
		}
		
}
