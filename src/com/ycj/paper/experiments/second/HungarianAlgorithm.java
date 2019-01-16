package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class HungarianAlgorithm {

	/**
	 * 
	 * 
	 * 匈牙利算法C代码如下：
	 *
	 * 
	 * */
	
	
	// 在Hu算法中，DS用户优先的匹配算法
	//定义全局边的变量
	// 1代表两点之间有边，且未匹配，2代表两点之间已经 匹配，0代表两点之间没有边
	public static int[][] StoR;
	public static int[][] RtoS;
	public static int[] matchedPointS;
	public static int[] matchedPointR;
	public static String matchPath="";
	public static boolean matchSuccess=false;
	public static void DSPriorty(ArrayList<HashMap<String, String>> list_ds, ArrayList<HashMap<String, String>> list_dr,
			int index) {
		ArrayList<HashMap<String, String>> list_ds_original = list_ds;
		ArrayList<HashMap<String, String>> list_dr_original = list_dr;
		int successMatchUsers = 0;
		float dfferenceValue = 0, drExpectValue = 0, dsExpectValue = 0, drCostsValue = 0, dsGainsValue = 0;
		matchedPointS = new int[list_ds_original.size()];
		matchedPointR = new int[list_dr_original.size()];

		// 1代表两点之间有边，且未匹配，2代表两点之间已经 匹配，0代表两点之间没有边
		 StoR = new int[list_ds_original.size()][list_dr_original.size()];
		 RtoS = new int[list_dr_original.size()][list_ds_original.size()];
		// 获取所有从DS到DR可以连接的边
		for (int i = 0; i < list_ds_original.size(); i++) {
			for (int j = 0; j < list_dr_original.size(); j++) {
				if (Tools.loveDegreeDRToDS(list_dr_original.get(j), list_ds_original.get(i)) > 0) {
					// 如果偏好程度大于0，则表明可以进行匹配，也就是存在可以连接的边
					StoR[i][j] = 1;
					RtoS[j][i] = 1;
				} else {
					// 如果偏好程度等于0，则表明不可以进行匹配，也就是不存在可以连接的边
					StoR[i][j] = 0;
					RtoS[j][i] = 0;
				}
			}
		}

		// 利用增广路径，获得最大匹配连接
		// 只要思想，从一个未匹配的DS点出发，经过交替路径找到一个未匹配的DR时停止
		for (int k = 0; k < matchedPointS.length; k++) {
			
			if (matchedPointS[k] == 0) {
				for (int l = 0; l < matchedPointR.length; l++) {
					//找到增广路径

					String result=getMatchPath(k);
					if(matchSuccess){
						//System.out.println(result);
						visiteResult(result);
						break;
					}
				}
				
				//把以前访问失败的痕迹还原
				for(int i=0;i<matchedPointS.length;i++){
					for(int j=0;j<matchedPointR.length;j++){
						if(StoR[i][j]==4){
							StoR[i][j]=1;
						}
						if(RtoS[j][i]==4){
							RtoS[j][i]=1;
						}
						if(StoR[i][j]==5){
							StoR[i][j]=2;
						}
						if(RtoS[j][i]==5){
							RtoS[j][i]=2;
						}
					}
				}
				
			
			} 

		}
		
		//根据最后的匹配结果进行计算
		for(int i=0;i<matchedPointS.length;i++){
		
			for(int j=0;j<matchedPointR.length;j++){
				
				if(StoR[i][j]==2){
					//System.out.println("得到结果："+i+":"+j+"="+StoR[i][j]);
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
		
		DataResult.fail_rate[index]+=(DataResult.DRNumber-successMatchUsers)/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		if(successMatchUsers>0){
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		}
		DataResult.sum_contary[index]+=dfferenceValue;

	}
	
	
	private static void visiteResult(String result){
		//把以前访问失败的痕迹还原
		for(int i=0;i<matchedPointS.length;i++){
			for(int j=0;j<matchedPointR.length;j++){
				if(StoR[i][j]==4){
					StoR[i][j]=1;
				}
				if(RtoS[j][i]==4){
					RtoS[j][i]=1;
				}
				if(StoR[i][j]==5){
					StoR[i][j]=2;
				}
				if(RtoS[j][i]==5){
					RtoS[j][i]=2;
				}
			}
		}
		String []resultPoints=result.split(",");
		int []points=new int[resultPoints.length];
		for(int k=0;k<resultPoints.length;k++){
			points[k]=Integer.parseInt(resultPoints[k]);
		}
		
		//标记DS点，代表已经匹配
		matchedPointS[points[0]] = 1;
		matchedPointR[points[points.length-1]] = 1;
		//System.out.println("匹配结果："+points[0]+":"+points[points.length-1]);
				
		for(int n=0;n<points.length-1;n=n+2){
			//左边的点进行标记
			if(StoR[points[n]][points[n+1]]==1){
				StoR[points[n]][points[n+1]]=2;
			}else if(StoR[points[n]][points[n+1]]==2){
				StoR[points[n]][points[n+1]]=1;
			}
			
			if(RtoS[points[n+1]][points[n]]==1){
				RtoS[points[n+1]][points[n]]=2;
			}else if(RtoS[points[n+1]][points[n]]==2){
				RtoS[points[n+1]][points[n]]=1;
			}
			
			//右边的点进行标记
			if((n+2)<points.length){
			if(RtoS[points[n+1]][points[n+2]]==1){
				RtoS[points[n+1]][points[n+2]]=2;
			}else if(RtoS[points[n+1]][points[n+2]]==2){
				RtoS[points[n+1]][points[n+2]]=1;
			}
			
			if(StoR[points[n+2]][points[n+1]]==1){
				StoR[points[n+2]][points[n+1]]=2;
			}else if(StoR[points[n+2]][points[n+1]]==2){
				StoR[points[n+2]][points[n+1]]=1;
			}
		
			}
			
		}
		
	}
	
	private static String getMatchPath(int DSPoint){
		String result="";
		matchSuccess = false;
		boolean startFind = true;
		int exixistDR = -1, exixistDS = DSPoint;
	
		while (true) {
			
			if (exixistDR!=-1&&matchedPointR[exixistDR] == 0) {
				matchSuccess = true;
				break;
			}
			
			if (startFind) {
				startFind = false;
				exixistDR = findNotMatchPoint(StoR, exixistDS, matchedPointR.length);
				result+=exixistDS+","+exixistDR+",";
				//result+=exixistDS+",";
				if (exixistDR != -1) {
					//修改访问标记
					StoR[exixistDS][exixistDR]=4;
					RtoS[exixistDR][exixistDS]=4;
					
				} else {
					break;
				}
			} else {
				startFind = true;
				exixistDS = findHadMatchPoint(RtoS, exixistDR, matchedPointS.length);
				//result+=exixistDR+",";
				if (exixistDS != -1) {
					//修改访问标记
					StoR[exixistDS][exixistDR]=5;
					RtoS[exixistDR][exixistDS]=5;
				} else {
					break;
				}
			}
			
			//result+=exixistDS+","+exixistDR+",";
		}
		
		
				
		return result;
	}

	// 找到未匹配的路径
	private static int findNotMatchPoint(int[][] points, int startIndex, int allPoint) {
		int notMatchIndex = -1;
		for (int i = 0; i < allPoint; i++) {
			if (points[startIndex][i] == 1) {
				notMatchIndex = i;
				break;
			}
		}
		return notMatchIndex;
	}

	// 找到已匹配的路径
	private static int findHadMatchPoint(int[][] points, int startIndex, int allPoint) {
		int hadMatchIndex = -1;
		for (int i = 0; i < allPoint; i++) {
			if (points[startIndex][i] == 2) {
				hadMatchIndex = i;
				break;
			}
		}
		return hadMatchIndex;
	}
	
	
	// 找到未匹配的路径并且已经访问过的路径
		private static int findNotMatchPointByVisited(int[][] points, int startIndex, int allPoint,int notPoint) {
			int notMatchIndex = -1;
			for (int i = 0; i < allPoint; i++) {
				if (points[startIndex][i] == 4&&notPoint!=i) {
					notMatchIndex = i;
					break;
				}
			}
			return notMatchIndex;
		}

		// 找到已匹配的路径并且已经访问过的路径
		private static int findHadMatchPointByVisited(int[][] points, int startIndex, int allPoint,int notPoint) {
			int hadMatchIndex = -1;
			for (int i = 0; i < allPoint; i++) {
				if (points[startIndex][i] == 5&&i!=notPoint) {
					hadMatchIndex = i;
					break;
				}
			}
			return hadMatchIndex;
		}
		
	//对增广路径取反，并进行标记
	private static void addVisitedPoint(int startDS,int endDR){
		System.out.println("DS:"+startDS+"    DR:"+endDR);
		boolean startFind=true;
		int exixistDR = -1, exixistDS = startDS,hadDS=-2,hadDR=-2;
		while (true) {
			if(exixistDR==endDR){
				break;
			}
			
			if (startFind) {
				startFind = false;
				exixistDR = findNotMatchPointByVisited(StoR, exixistDS, matchedPointR.length,hadDR);
				if (exixistDR != -1) {
					//修改访问标记
					System.out.println(exixistDS+":"+exixistDR+"=3="+StoR[exixistDS][exixistDR]);
					StoR[exixistDS][exixistDR] = 3;
					RtoS[exixistDR][exixistDS] = 3;
					hadDS=exixistDS;
					

				} else {
					break;
				}
			} else {
				startFind = true;
				exixistDS = findHadMatchPointByVisited(RtoS, exixistDR, matchedPointS.length,hadDS);
				if (exixistDS != -1) {
					//修改访问标记
					System.out.println(exixistDS+":"+exixistDR+"=1="+StoR[exixistDS][exixistDR]);
					StoR[exixistDS][exixistDR] = 1;
					RtoS[exixistDR][exixistDS] = 1;
					hadDR=exixistDR;
					
				} else {
					break;
				}
			}
			

		}
		
		//标记新的边路径
		for(int i=0;i<matchedPointS.length;i++){
			for(int j=0;j<matchedPointR.length;j++){
				if(StoR[i][j]==3){
					StoR[i][j]=2;
				}
				
				if(RtoS[j][i]==3){
					RtoS[j][i]=2;
				}
			}
		}
		
		//标记DS点，代表已经匹配
		matchedPointS[startDS] = 1;
		matchedPointR[endDR] = 1;
	}

}
