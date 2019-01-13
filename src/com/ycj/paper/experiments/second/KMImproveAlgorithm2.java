package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class KMImproveAlgorithm2 {
	// ��KM�㷨�У�DS�û����ȵ�ƥ���㷨
	//����ȫ�ֱߵı���
	// 1��������֮���бߣ���δƥ�䣬2��������֮���Ѿ� ƥ�䣬0��������֮��û�б�
	public static int[][] StoR;
	public static int[][] RtoS;
	public static int[][] StoRWeight;
	public static int[][] RtoSWeight;
	//�������ߵĵ���б��
	public static int[] matchedPointS;
	public static int[] matchedPointR;
	//�������ߵĵ�������Ȩ�ش洢
	public static int[] matchedPointSMaxW;
	public static int[] matchedPointRMaxW;
	public static String matchPath="";
	public static boolean matchSuccess=false;
	//public static int MatchTimesTag=0;
	public static void DSPriorty(ArrayList<HashMap<String, String>> list_ds, ArrayList<HashMap<String, String>> list_dr,
			int index) {
		/*ArrayList<HashMap<String, String>> list_ds_original = list_ds;
		ArrayList<HashMap<String, String>> list_dr_original = list_dr;*/
		
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		int successMatchUsers = 0;
		float dfferenceValue = 0, drExpectValue = 0, dsExpectValue = 0, drCostsValue = 0, dsGainsValue = 0;
		//��ʼ����ķ��ʱ�Ǻ�Ȩ��
		matchedPointS = new int[list_ds_original.size()];
		matchedPointR = new int[list_dr_original.size()];
		matchedPointSMaxW = new int[list_ds_original.size()];
		matchedPointRMaxW = new int[list_dr_original.size()];
		// 1��������֮���бߣ���δƥ�䣬2��������֮���Ѿ� ƥ�䣬0��������֮��û�б�
		 StoR = new int[list_ds_original.size()][list_dr_original.size()];
		 RtoS = new int[list_dr_original.size()][list_ds_original.size()];
		 //��ʼ��Ȩ��
		 StoRWeight = new int[list_ds_original.size()][list_dr_original.size()];
		 RtoSWeight = new int[list_dr_original.size()][list_ds_original.size()];
		// ��ȡ���д�DS��DR�������ӵı�
		for (int i = 0; i < list_ds_original.size(); i++) {
			for (int j = 0; j < list_dr_original.size(); j++) {
				if (Tools.loveDegreeDRToDS(list_dr_original.get(j), list_ds_original.get(i)) > 0) {
					// ���ƫ�ó̶ȴ���0����������Խ���ƥ�䣬Ҳ���Ǵ��ڿ������ӵı�
					StoR[i][j] = 1;
					RtoS[j][i] = 1;
					int diffrenceV=(int)Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
					StoRWeight[i][j] =-diffrenceV;
					RtoSWeight[j][i] =-diffrenceV;
				} else {
					// ���ƫ�ó̶ȵ���0������������Խ���ƥ�䣬Ҳ���ǲ����ڿ������ӵı�
					StoR[i][j] = 0;
					RtoS[j][i] = 0;
					StoRWeight[i][j] =- 1000;
					RtoSWeight[j][i] = -1000;
				}
			}
		}
		
		//����������ߵ�����Ȩ�غͳ�ʼ��Ȩ��
		for(int i=0;i<matchedPointS.length;i++){
			int maxW=-999;
			for(int j=0;j<matchedPointR.length;j++){
				if(StoR[i][j]>maxW){
					maxW=StoR[i][j];
				}
			}
			matchedPointSMaxW[i]=maxW;
		}

		
		// ��������·����������ƥ������
		// ֻҪ˼�룬��һ��δƥ���DS���������������·���ҵ�һ��δƥ���DRʱֹͣ
		//�Ľ��㷨
		for(int i=0;i<matchedPointS.length;i++){
			getMatch();
		}
		
		/*ArrayList<HashMap<String,String>> ds_list=new ArrayList<>();
		ArrayList<HashMap<String,String>> dr_list=new ArrayList<>();*/
		//��������ƥ�������м���
		for(int i=0;i<matchedPointS.length;i++){
		
			for(int j=0;j<matchedPointR.length;j++){
				
				if(StoR[i][j]==2){
					System.out.println("�õ������"+i+":"+j+"="+StoR[i][j]);
					/*ds_list.add(list_ds_original.get(i));
					dr_list.add(list_dr_original.get(j));*/
					
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(j), list_ds_original.get(i));
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(j),  list_ds_original.get(i));
					drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(j),  list_ds_original.get(i));
					dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(j),  list_ds_original.get(i));
					successMatchUsers++;
			
					break;
				}
				
			}
		}
		
		//����MTM�㷨������һ���Ż�
		//MTMAlgorithm.DSPriorty(ds_list, dr_list, index);
		
		DataResult.fail_rate[index]+=(DataResult.DRNumber-successMatchUsers)/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;

	}
	
	
	private static void getMatch(){
		int maxSide=-1000,DS=-1,DR=-1;
		for(int i=0;i<matchedPointS.length;i++){
			
			for(int j=0;j<matchedPointR.length;j++){
				if(StoRWeight[i][j]>maxSide&&matchedPointS[i]==0&&matchedPointR[j]==0&&StoR[i][j]==1){
					maxSide=StoRWeight[i][j];
					DS=i;DR=j;
				}
			}
		}
		
		if(maxSide>-999&&DS>-1&&DR>-1){
			matchedPointS[DS]=1;
			matchedPointR[DR]=1;
			StoR[DS][DR]=2;
		}
	}
	
}
