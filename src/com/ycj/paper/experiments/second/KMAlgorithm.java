package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class KMAlgorithm {

	/**
	 * KM�㷨C�������£�
	 * 
	 * 
	 */
	

	// ��Hu�㷨�У�DS�û����ȵ�ƥ���㷨
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
		ArrayList<HashMap<String, String>> list_ds_original = list_ds;
		ArrayList<HashMap<String, String>> list_dr_original = list_dr;
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
		for (int k = 0; k < matchedPointS.length; k++) {
			
			if (matchedPointS[k] == 0) {
				for (int l = 0; l < matchedPointR.length; l++) {
					//MatchTimesTag=0;
					//�ҵ�����·��
					String result=getMatchPath(k);
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
		
		//��������ƥ�������м���
		for(int i=0;i<matchedPointS.length;i++){
		
			for(int j=0;j<matchedPointR.length;j++){
				
				if(StoR[i][j]==2){
					System.out.println("�õ������"+i+":"+j+"="+StoR[i][j]);
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
					//�޸ķ��ʱ��
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
	
	private static boolean verifyResult(String result){
		boolean isNotSuccess=false;
		String []resultPoints=result.split(",");
		int []points=new int[resultPoints.length];
		for(int k=0;k<resultPoints.length;k++){
			points[k]=Integer.parseInt(resultPoints[k]);
		}
		//�õ����һ��ƥ���û�֮����ڵ����ݲ�ֵ
		int differenceV=StoRWeight[points[points.length-2]][points[points.length-1]]-matchedPointSMaxW[points[points.length-2]];
		//��֤�������ߵĵ��Ȩ��֮����ߵ�Ȩ�����
		for(int i=0;i<points.length-1;i=i+2){
			//�ȼ�
			matchedPointSMaxW[i]-=differenceV;
			matchedPointRMaxW[i+1]+=differenceV;
		}
		isNotSuccess=true;
		return isNotSuccess;
	}

	// �ҵ�δƥ���·��
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
					//δƥ��ĵ㣬�����Ȩֵ֮�ʹ��ڱߵ�Ȩ��
					matchedPointSMaxW[startIndex]--;
					decreaseWeight=true;
					break;
				}else if(matchedPointSMaxW[startIndex]+matchedPointRMaxW[i]<StoRWeight[startIndex][i]&&matchedPointS[startIndex]==1){
					//�����Ѿ�ƥ��Ķ��㣬��߽����˼������ұ�Ӧ�ý��м�
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
		//���ڿ������ӵıߣ����ǳ�ͻ�ˣ����������ҼӲ�����֮���ٽ���ƥ��
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
	
	// �ҵ���ƥ���·��
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
					//�Ѿ�ƥ��ĵ㣬�����Ȩֵ֮�ʹ��ڱߵ�Ȩ��
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
		
		//���ڿ������ӵıߣ����ǳ�ͻ�ˣ����������ҼӲ�����֮���ٽ���ƥ��
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
