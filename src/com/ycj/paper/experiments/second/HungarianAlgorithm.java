package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class HungarianAlgorithm {

	/**
	 * 
	 * 
	 * 匈牙利算法C代码如下：
	 *
	 * #include<bits/stdc++.h> #define MAXN 9999 using namespace std; int
	 * nx,ny;//nx表示二分图左边顶点的个数，ny表示二分图右边顶点的个数 int m;//m代表边的条数 int
	 * cx[MAXN],cy[MAXN];//如果有cx[i]=j，则必有cy[j]=i，说明i点和j点能够匹配 int x,y;//x点到y点有边
	 * int e[MAXN][MAXN];//邻接矩阵 int visited[MAXN];//标记数组，标记的永远是二分图右边的顶点 int
	 * ret;//最后结果 int point(int
	 * u)//这个函数的作用是寻找增广路和更新cx，xy数组，如果找到了增广路，函数返回1，找不到，函数返回0。 {     for(int
	 * v=1;v<=ny;v++)//依次遍历右边的所有顶点     {        
	 * if(e[u][v]&&!visited[v])//条件一：左边的u顶点和右边的v顶点有连通边，条件二：右边的v顶点在没有被访问过，这两个条件必须同时满足
	 *         {             visited[v]=1;//将v顶点标记为访问过的            
	 * if(cy[v]==-1||point(cy[v]))//条件一：右边的v顶点没有左边对应的匹配的点，条件二：以v顶点在左边的匹配点为起点能够找到一条增广路（如果能够到达条件二，说明v顶点在左边一定有对应的匹配点）。
	 *             {                 cx[u]=v;//更新cx，cy数组                
	 * cy[v]=u;                 return 1;             }         }     }    
	 * return 0;//如果程序到达了这里，说明对右边所有的顶点都访问完了，没有满足条件的。 } int main() {     while
	 * (cin>>m>>nx>>ny)     {         memset(cx,-1,sizeof(cx));//初始化cx，cy数组的值为-1
	 *         memset(cy,-1,sizeof(cy));         memset(e,0,sizeof(e));//初始化邻接矩阵
	 *         ret=0;         while (m--)//输入边的信息和更新邻接矩阵         {            
	 * cin>>x>>y;             e[x][y]=1;         }         for(int
	 * i=1;i<=nx;i++)//对二分图左边的所有顶点进行遍历         {            
	 * if(cx[i]==-1)//如果左边的i顶点还没有匹配的点，就对i顶点进行匹配             {                
	 * memset(visited,0,sizeof(visited));//每次进行point时，都要对visited数组进行初始化        
	 *         ret+=point(i);//point函数传入的参数永远是二分图左边的点             }         }  
	 *       cout<<ret<<endl;     }
	 * 
	 * }
	 * 
	 */
	
	
	// 在Hu算法中，DS用户优先的匹配算法
	//定义全局边的变量
	// 1代表两点之间有边，且未匹配，2代表两点之间已经 匹配，0代表两点之间没有边
	public static int[][] StoR;
	public static int[][] RtoS;
	public static int[] matchedPointS;
	public static int[] matchedPointR;
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
				if (Tools.loveDegreeDRToDS(list_dr_original.get(j), list_ds_original.get(i)) > 0.0) {
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
			boolean DRPoint = false, startFind = true;
			int exixistDR = -1, exixistDS = k;
			if (matchedPointS[k] == 0) {
				while (true) {
					if (exixistDR!=-1&&matchedPointR[exixistDR] == 0) {
						DRPoint = true;
						break;
					}
					
					if (startFind) {
						startFind = false;
						exixistDR = findNotMatchPoint(StoR, exixistDS, matchedPointR.length);
						if (exixistDR != -1) {
							//修改访问标记
						
						} else {
							break;
						}
					} else {
						startFind = true;
						exixistDS = findHadMatchPoint(RtoS, exixistDR, matchedPointS.length);
						if (exixistDS != -1) {
							//修改访问标记

						} else {
							break;
						}
					}
				}

			} else {
				continue;

			}
			//找到增广路径
			if(DRPoint){
				addVisitedPoint(k, exixistDR);
			}
			
		}
		
		//根据最后的匹配结果进行计算
		for(int i=0;i<matchedPointS.length;i++){
			/*System.out.println("================================");
			for(int j=0;j<matchedPointR.length;j++){
				System.out.println(i+":"+j+"="+RtoS[j][i]);
			}
			System.out.println("----------------------------");*/
			for(int j=0;j<matchedPointR.length;j++){
				
				if(StoR[i][j]==2||RtoS[j][i]==2){
					System.out.println(i+":"+j+"="+StoR[i][j]);
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
	
	//对增广路径取反，并进行标记
	private static void addVisitedPoint(int startDS,int endDR){
		boolean startFind=true;
		int exixistDR = -1, exixistDS = startDS;
		while (true) {
			if(exixistDR==endDR){
				break;
			}
			
			if (startFind) {
				startFind = false;
				exixistDR = findNotMatchPoint(StoR, exixistDS, matchedPointR.length);
				if (exixistDR != -1) {
					//修改访问标记
					StoR[exixistDS][exixistDR] = 3;
					RtoS[exixistDR][exixistDS] = 3;
					

				} else {
					break;
				}
			} else {
				startFind = true;
				exixistDS = findHadMatchPoint(RtoS, exixistDR, matchedPointS.length);
				if (exixistDS != -1) {
					//修改访问标记
					StoR[exixistDS][exixistDR] = 1;
					RtoS[exixistDR][exixistDS] = 1;
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
