package com.ycj.paper.experiments.third;

import java.util.ArrayList;
import java.util.HashMap;

public class KMImproveAlgorithm {
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
		int left=0,right=0,center=0;
		left=right=matchedPointS.length/2;
		for (int k = 0; k < matchedPointS.length; k++) {
			//ÿ��ȡ���ĵ㣬��������ƥ��
			if(k%2==0){
				center=right;
				right++;
			}else{
				center=left;
				left--;
			}
			if (matchedPointS[center] == 0) {
				for (int l = 0; l < matchedPointR.length; l++) {
					//MatchTimesTag=0;
					//�ҵ�����·��
					String result=getMatchPath(center);
					if(matchSuccess){
						System.out.println(result);
						visiteResult(result);
						break;
					}
				}
				
				//����ǰ����ʧ�ܵĺۼ���ԭ
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
		if(successMatchUsers>0){
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		}
		DataResult.sum_contary[index]+=dfferenceValue;

	}
	
	
	private static void visiteResult(String result){
		//����ǰ����ʧ�ܵĺۼ���ԭ
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
		
		//���DS�㣬�����Ѿ�ƥ��
		matchedPointS[points[0]] = 1;
		matchedPointR[points[points.length-1]] = 1;
		System.out.println("ƥ������"+points[0]+":"+points[points.length-1]);
				
		for(int n=0;n<points.length-1;n=n+2){
			//��ߵĵ���б��
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
			
			//�ұߵĵ���б��
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
				System.out.println("ƥ����̣�"+result);
				matchSuccess = true;
				break;
			}
			
			if (startFind) {
				startFind = false;
				if(matchedPointS[exixistDS]==0){
					exixistDR=getDRPointNotMatchedMaxSide(exixistDS,matchedPointR.length);
				}else{
					
				}
				exixistDR = getDRPointNotMatchedMaxSide(exixistDS, matchedPointR.length);
				result+=exixistDS+","+exixistDR+",";
				//result+=exixistDS+",";
				if (exixistDR != -1) {
					//�޸ķ��ʱ��
					StoR[exixistDS][exixistDR]=4;
					RtoS[exixistDR][exixistDS]=4;
					
				} else {
					break;
				}
			} else {
				startFind = true;
				exixistDS = getDSPointHadMatchedMaxSide(exixistDR, matchedPointS.length);
				//result+=exixistDR+",";
				if (exixistDS != -1) {
					//�޸ķ��ʱ��
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
			if(StoRWeight[startIndex][i]>maxV&&StoR[startIndex][i]==1&&StoRWeight[startIndex][i]==matchedPointSMaxW[startIndex]+matchedPointRMaxW[i]){
				maxV=StoRWeight[startIndex][i];
				index=i;
			}
		}
		if(matchedPointR[index]!=0){
			int DSpoint=getDSPointHadMatchedMaxSide(index,matchedPointS.length);
			if(StoRWeight[startIndex][index]>RtoSWeight[index][DSpoint]){
				
			}else{
				//ȡ�ڶ���ıߣ�������һ��Ȩֵ����
				
			}
		}
		
		return index;
	}
	
	private static int getDSPointNotMatchedSecondValue(int startIndex,int length){
		int maxV=-999,index=-1;
		for(int i=0;i<length;i++){
			if(RtoSWeight[startIndex][i]>maxV&&RtoS[startIndex][i]==2){
				maxV=RtoSWeight[startIndex][i];
				index=i;
			}
		}
		return index;
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
