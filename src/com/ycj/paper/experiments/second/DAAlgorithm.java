package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class DAAlgorithm {
	
	/**
	 * DA�㷨���ӳٽ����㷨��ÿ�������ڵ�һ�������Լ����е�No.1ʾ�������Ǹ�λ�����ǲ����������������Ը÷����������С�deferred��һ�ʣ���
	 * ������holdס�ˡ��ڵڶ����У�ÿ�������������е�No.2ʾ�����ӵڶ��ֿ�ʼ��ÿλ������ֻ�����Լ�������Ϊֹ���ջ�������ǵ����������ǲ��ô�Ӧ����
	 * ֻhold�����������ܾ����������ˡ������ܾ���������Ҳ����������û����hold��������������������һ������������������һ�������ס��Դ����ƣ�
	 * һ���ּ�����ȥ��ֱ��������ʾ����������ʾ��Ϊֹ����ʱ��ÿ��������offer�Ĺ������ѡ����ܡ����Ͼ���deferred acceptance algorithm��
	 * �����������һ�£��ͻᷢ�֣�����������򵥵������У����Ľ����x-A��y-B��z-C���������ճɾ��¡�������һstable�Ľ��������6���У�
	 * �㲻�����ҵ�һ��һŮ�����������������Ƕ���Ը���������еİ��¶���˴���һ��
	 * 
	 * �ӳٽ����㷨�Ĳ�����ÿ�������ڵ�һ�������Լ����е�No.1ʾ�������Ǹ�λ�����ǲ�������������������holdס�ˡ�
	 * �ڵڶ����У�ÿ�������������е�No.2ʾ�����ӵڶ��ֿ�ʼ��ÿλ������ֻ�����Լ�������Ϊֹ���ջ�������ǵ�����
	 * �����ǲ��ô�Ӧ����ֻhold�����������ܾ����������ˡ������ܾ���������Ҳ����������û����hold��������������������һ������
	 * ������������һ�������ס��Դ����ƣ�һ���ּ�����ȥ��ֱ��������ʾ����������ʾ��Ϊֹ����ʱ��ÿ��������offer�Ĺ������ѡ����ܡ�
	 * @param list_ds
	 * @param list_dr
	 * @param index
	 */

	//��DA�㷨�У�DS�û����ȵ�ƥ���㷨
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
		
		//��DA�㷨�У�DR�û����ȵ�ƥ���㷨
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
