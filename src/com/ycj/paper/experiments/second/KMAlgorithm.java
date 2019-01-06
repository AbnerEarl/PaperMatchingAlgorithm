package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class KMAlgorithm {

	/**
	 * KM算法C代码如下：
	 * 
	 * 
	 */
	

	// 在Hu算法中，DS用户优先的匹配算法
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
		ArrayList<HashMap<String, String>> list_ds_original = list_ds;
		ArrayList<HashMap<String, String>> list_dr_original = list_dr;
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
		for (int k = 0; k < matchedPointS.length; k++) {
			
			if (matchedPointS[k] == 0) {
				for (int l = 0; l < matchedPointR.length; l++) {
					//MatchTimesTag=0;
					//找到增广路径
					String result=getMatchPath(k);
					if(matchSuccess){
						System.out.println(result);
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
					System.out.println("得到结果："+i+":"+j+"="+StoR[i][j]);
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
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
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
		System.out.println("匹配结果："+points[0]+":"+points[points.length-1]);
				
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
				System.out.println("匹配过程："+result);
				if(verifyResult(result)){
					matchSuccess = true;
					break;
				}
				
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
	
	private static boolean verifyResult(String result){
		boolean isNotSuccess=false;
		String []resultPoints=result.split(",");
		int []points=new int[resultPoints.length];
		for(int k=0;k<resultPoints.length;k++){
			points[k]=Integer.parseInt(resultPoints[k]);
		}
		//得到最后一对匹配用户之间存在的数据差值
		int differenceV=StoRWeight[points[points.length-2]][points[points.length-1]]-matchedPointSMaxW[points[points.length-2]];
		//验证左右两边的点的权重之和与边的权重相等
		for(int i=0;i<points.length-1;i=i+2){
			//先加
			matchedPointSMaxW[i]-=differenceV;
			matchedPointRMaxW[i+1]+=differenceV;
		}
		isNotSuccess=true;
		return isNotSuccess;
	}

	// 找到未匹配的路径
	/*private static int findNotMatchPoint(int[][] points, int startIndex, int allPoint) {
		MatchTimesTag++;
		int notMatchIndex = -1,DRpoint=-1;
		boolean isNotExsit=false,decreaseWeight=false,increaseWeight=false;
		if(MatchTimesTag>1000){
			return -1;
		}
		for (int i = 0; i < allPoint; i++) {
			if (points[startIndex][i] == 1) {
				if(matchedPointSMaxW[startIndex]+matchedPointRMaxW[i]==StoRWeight[startIndex][i]&&StoRWeight[startIndex][i]>-999){
					notMatchIndex = i;
					break;
				}else if(matchedPointSMaxW[startIndex]+matchedPointRMaxW[i]>StoRWeight[startIndex][i]&&matchedPointS[startIndex]==0){
					//未匹配的点，且最大权值之和大于边的权重
					matchedPointSMaxW[startIndex]--;
					decreaseWeight=true;
					break;
				}else if(matchedPointSMaxW[startIndex]+matchedPointRMaxW[i]<StoRWeight[startIndex][i]&&matchedPointS[startIndex]==1){
					//对于已经匹配的定点，左边进行了减法，右边应该进行加
					increaseWeight=true;
					int diffrentV=getDSPointMaxValueV(startIndex,allPoint)-matchedPointSMaxW[startIndex];
					matchedPointRMaxW[i]+=diffrentV;
					break;
				}
				else if(StoRWeight[startIndex][i]>-999){
					DRpoint=i;
					isNotExsit=true;
				}
				
			}
		}
		//存在可以连接的边，但是冲突了，则进行左减右加操作，之后再进行匹配
		if(decreaseWeight){
			return findNotMatchPoint(points,startIndex,allPoint);	
		}else if(increaseWeight){
			return findNotMatchPoint(points,startIndex,allPoint);
		}
		else if(notMatchIndex==-1&&isNotExsit){
			matchedPointSMaxW[startIndex]--;
			matchedPointRMaxW[DRpoint]++;
			return findNotMatchPoint(points,startIndex,allPoint);
		}else{
			return notMatchIndex;
		}
		
	}
*/
	private static int findNotMatchPoint(int[][] points, int startIndex, int allPoint) {
		int notMatchIndex = -1,DRpoint=-1;
		
		return getDRPointNotMatchedMaxSide(startIndex,allPoint);
	}

	private static int getDSPointHadMatchedMaxValue(int startIndex,int length){
		int maxV=-999,index=-1;
		for(int i=0;i<length;i++){
			if(StoRWeight[startIndex][i]>maxV&&StoR[startIndex][i]==2){
				maxV=StoRWeight[startIndex][i];
				index=i;
			}
		}
		return maxV;
	}
	
	private static int getDRPointNotMatchedMaxSide(int startIndex,int length){
		int maxV=-999,index=-1;
		for(int i=0;i<length;i++){
			if(StoRWeight[startIndex][i]>maxV&&StoR[startIndex][i]==1){
				maxV=StoRWeight[startIndex][i];
				index=i;
			}
		}
		return index;
	}
	
	// 找到已匹配的路径
	/*private static int findHadMatchPoint(int[][] points, int startIndex, int allPoint) {
		int hadMatchIndex = -1,DSpoint=-1;
		boolean isNotExsit=false,decreaseWeight=false;
		if(MatchTimesTag>1000){
			return -1;
		}
		for (int i = 0; i < allPoint; i++) {
			if (points[startIndex][i] == 2) {
				if(matchedPointRMaxW[startIndex]+matchedPointSMaxW[i]==RtoSWeight[startIndex][i]&&RtoSWeight[startIndex][i]>-999){
					hadMatchIndex = i;
					break;
				}else if(matchedPointRMaxW[startIndex]+matchedPointSMaxW[i]>RtoSWeight[startIndex][i]&&RtoSWeight[startIndex][i]>-999){
					//已经匹配的点，且最大权值之和大于边的权重
					matchedPointSMaxW[i]--;
					decreaseWeight=true;
					break;
				}
				else if(RtoSWeight[startIndex][i]>-999){
					DSpoint=i;
					isNotExsit=true;
				}
				
			}
		}
		
		//存在可以连接的边，但是冲突了，则进行左减右加操作，之后再进行匹配
		if (decreaseWeight) {
			return findHadMatchPoint(points, startIndex, allPoint);
		} else if (hadMatchIndex == -1 && isNotExsit) {
			//matchedPointRMaxW[startIndex]++;
			matchedPointSMaxW[DSpoint]--;
			return findHadMatchPoint(points, startIndex, allPoint);
		}else{
			return hadMatchIndex;
		}

	
	}*/
	
	
	private static int findHadMatchPoint(int[][] points, int startIndex, int allPoint) {
		int hadMatchIndex = -1,DSpoint=-1;
		
		return getDSPointHadMatchedMaxSide(startIndex,allPoint);
		
	
	}
	
	
	private static int getDSPointHadMatchedMaxSide(int startIndex,int length){
		int maxV=-999,index=-1;
		for(int i=0;i<length;i++){
			if(RtoSWeight[startIndex][i]>maxV&&RtoS[startIndex][i]==2){
				maxV=RtoSWeight[startIndex][i];
				index=i;
			}
		}
		return index;
	}
	
	
}
