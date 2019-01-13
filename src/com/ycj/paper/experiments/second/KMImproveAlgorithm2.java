package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class KMImproveAlgorithm2 {
	// 在KM算法中，DS用户优先的匹配算法
	//定义全局边的变量
	// 1代表两点之间有边，且未匹配，2代表两点之间已经 匹配，0代表两点之间没有边
	public static int[][] StoR;
	public static int[][] RtoS;
	public static int[][] StoRWeight;
	public static int[][] RtoSWeight;
	//左右两边的点进行标记
	public static int[] matchedPointS;
	public static int[] matchedPointR;
	//左右两边的点进行最大权重存储
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
		//初始化点的访问标记和权重
		matchedPointS = new int[list_ds_original.size()];
		matchedPointR = new int[list_dr_original.size()];
		matchedPointSMaxW = new int[list_ds_original.size()];
		matchedPointRMaxW = new int[list_dr_original.size()];
		// 1代表两点之间有边，且未匹配，2代表两点之间已经 匹配，0代表两点之间没有边
		 StoR = new int[list_ds_original.size()][list_dr_original.size()];
		 RtoS = new int[list_dr_original.size()][list_ds_original.size()];
		 //初始化权重
		 StoRWeight = new int[list_ds_original.size()][list_dr_original.size()];
		 RtoSWeight = new int[list_dr_original.size()][list_ds_original.size()];
		// 获取所有从DS到DR可以连接的边
		for (int i = 0; i < list_ds_original.size(); i++) {
			for (int j = 0; j < list_dr_original.size(); j++) {
				if (Tools.loveDegreeDRToDS(list_dr_original.get(j), list_ds_original.get(i)) > 0) {
					// 如果偏好程度大于0，则表明可以进行匹配，也就是存在可以连接的边
					StoR[i][j] = 1;
					RtoS[j][i] = 1;
					int diffrenceV=(int)Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
					StoRWeight[i][j] =-diffrenceV;
					RtoSWeight[j][i] =-diffrenceV;
				} else {
					// 如果偏好程度等于0，则表明不可以进行匹配，也就是不存在可以连接的边
					StoR[i][j] = 0;
					RtoS[j][i] = 0;
					StoRWeight[i][j] =- 1000;
					RtoSWeight[j][i] = -1000;
				}
			}
		}
		
		//标记左右两边点的最大权重和初始化权重
		for(int i=0;i<matchedPointS.length;i++){
			int maxW=-999;
			for(int j=0;j<matchedPointR.length;j++){
				if(StoR[i][j]>maxW){
					maxW=StoR[i][j];
				}
			}
			matchedPointSMaxW[i]=maxW;
		}

		
		// 利用增广路径，获得最大匹配连接
		// 只要思想，从一个未匹配的DS点出发，经过交替路径找到一个未匹配的DR时停止
		//改进算法
		for(int i=0;i<matchedPointS.length;i++){
			getMatch();
		}
		
		/*ArrayList<HashMap<String,String>> ds_list=new ArrayList<>();
		ArrayList<HashMap<String,String>> dr_list=new ArrayList<>();*/
		//根据最后的匹配结果进行计算
		for(int i=0;i<matchedPointS.length;i++){
		
			for(int j=0;j<matchedPointR.length;j++){
				
				if(StoR[i][j]==2){
					System.out.println("得到结果："+i+":"+j+"="+StoR[i][j]);
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
		
		//调用MTM算法，进行一次优化
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
