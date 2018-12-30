package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;


public class MTMAlgorithm {

	/*
	 * 我们的算法（MTM for short）：分别对DR用户按照需要的流量大小进行排序，对DS用户按照共享的流量大小进行排序；
	 * DR用户优先原则，分别计算出每个DR用户对DS用户集合的一个偏好程度的排序集合RS，每个DR用户从它偏好的RS集合中选取第一个且没有匹配的DS用户，
	 * 计算这个DS用户与其他的DR用户的差异值是否比当前的DR用户的差异值大，如果大，则匹配当前的DR用户和DS用户，如果小则匹配最小差异值的DR用户和DS用户；
	 * DS用户优先原则，分别计算出每个DS用户对DR用户集合的一个偏好程度的排序集合SR，每个DS用户从它偏好的SR集合中选取第一个且没有匹配的DR用户，
	 * 计算这个DR用户与其他的DS用户的差异值是否比当前的DR用户的差异值大，如果大，则匹配当前的DR用户和DS用户，如果小则匹配最小差异值的DR用户和DS用户。
	 * 
	 * 
	 */


		//在MTM算法中，DS用户优先的匹配算法
			public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
				ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
				ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
				int successMatchUsers=0;
				float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
				while(list_ds_original.size()>0){
					HashMap<String,String> DSUser=list_ds_original.get(0);
					float loveDegree=0;
					int selectedDRTag=-1,selectedDRTagSecond=-1;
					float dfferenceV1=-1,dfferenceV2=-1,dfferenceV3=-1,dfferenceV4=-1;
					boolean isFirtSelectDR=true;
					
					//找到最满意的DR用户，就是在满足DR用户的容忍时间内，选择需要流量接近共享流量大小的DR用户
					for(int j=0;j<list_dr_original.size();j++){
						float temLoveDegree=Tools.loveDegreeDRToDS(list_dr_original.get(j),DSUser);
						if(temLoveDegree>loveDegree){
							if(!isFirtSelectDR){
								selectedDRTagSecond=selectedDRTag;
							}
							selectedDRTag=j;
							loveDegree=temLoveDegree;
						}
						if(loveDegree>=1){
							break;
						}
						isFirtSelectDR=false;
					}
					//找到了DR用户
					if(selectedDRTag!=-1){
						//如果找到的满足条件的DR用户不止一个，我们取当前最优的DR用户的前面一个DR用户作为备选DR用户
						dfferenceV1=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), DSUser);
						if(selectedDRTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
						}
						loveDegree=0;
						int selectedDSTag=-1,selectedDSTagSecond=-1;
						boolean isFirtSelectDS=true;
						//反向查询这个DR用户是不是最优搭配当前的DS用户
						for(int k=0;k<list_ds_original.size();k++){
							float temLoveDegree=Tools.loveDegreeDSToDR(list_ds_original.get(k),list_dr_original.get(selectedDRTag));
							if(temLoveDegree>loveDegree){
								if(!isFirtSelectDS){
									selectedDSTagSecond=selectedDSTag;
								}
								selectedDSTag=k;
								loveDegree=temLoveDegree;
							}
							if(loveDegree>=1){
								break;
							}
							isFirtSelectDS=false;
						}
						
					
							//我们取最优的DS的用户，以及最优的DS用户的前面一个DS用户作为备选DS用户
							dfferenceV3=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							
							//根据差异值分别比较，找到这四队用户中最优的一对，并计算得到差异值，DS用户差异值，DR用户差异值
							if(selectedDSTagSecond>-1){
								dfferenceV4=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
								
							}
							int successDS1=0,successDR1=selectedDRTag;
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								//第一次匹配选取差异值最小的一对用户
								dfferenceV1=dfferenceV2;
								//第一次匹配对应的下标交换
								//selectedDRTag=selectedDRTagSecond;
								successDR1=selectedDRTagSecond;
							}
							int successDR2=selectedDRTag,successDS2=selectedDSTag;
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								//第二次匹配选取差异值最小的一对用户
								dfferenceV3=dfferenceV4;
								//第二次匹配对应的下标交换
								//selectedDSTag=selectedDSTagSecond;
								successDS2=selectedDSTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
							//dfferenceValue+=dfferenceV3;
							dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							successMatchUsers++;
							
							//测试打印差异值数据计算是否正确
							System.out.println("最后计算的differenceValue："+Tools.computeDifferenceValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2)));
							System.out.println("比较得来的differenceValue："+dfferenceV3);
							
							list_dr_original.remove(successDR2);
							list_ds_original.remove(successDS2);
							
							}else{
							//dfferenceValue+=dfferenceV1;
							dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(successDR1),list_ds_original.get(0));
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(successDR1),list_ds_original.get(0));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(successDR1),list_ds_original.get(0));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(successDR1),list_ds_original.get(0));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(successDR1),list_ds_original.get(0));
							successMatchUsers++;
							
							//测试打印差异值数据计算是否正确
							System.out.println("最后计算的differenceValue："+Tools.computeDifferenceValue(list_dr_original.get(successDR1),list_ds_original.get(0)));
							System.out.println("比较得来的differenceValue："+dfferenceV1);
						
							list_dr_original.remove(successDR1);
							list_ds_original.remove(0);
							}
						
						
					}else{
						//没有找到DR用户
						list_ds_original.remove(0);
					}

				}
				//计算累计值
				DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
				DataResult.dr_contary[index]+=drExpectValue;
				DataResult.ds_contary[index]+=dsExpectValue;
				DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
				DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
				DataResult.sum_contary[index]+=dfferenceValue;
			}
			
			
			
			
			//在MTM算法中，DR用户优先的匹配算法
			public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
				ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
				ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
				float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
				int failTotal=0;
				int successMatchUsers=0;
				while(list_dr_original.size()>0){
					HashMap<String,String> DRUser=list_dr_original.get(0);
					float loveDegree=0;
					int selectedDSTag=-1,selectedDSTagSecond=-1;
					float dfferenceV1=-1,dfferenceV2=-1,dfferenceV3=-1,dfferenceV4=-1;
					boolean isFirtSelectDS=true;
					
					//找到最满意的DS用户，就是在满足DR用户的容忍时间内，选择需要流量接近需求流量大小的DS用户
					for(int j=0;j<list_ds_original.size();j++){
						float temLoveDegree=Tools.loveDegreeDSToDR(list_ds_original.get(j),DRUser);
						if(temLoveDegree>loveDegree){
							if(!isFirtSelectDS){
								selectedDSTagSecond=selectedDSTag;
							}
							selectedDSTag=j;
							loveDegree=temLoveDegree;
						}
						if(loveDegree>=1){
							break;
						}
						isFirtSelectDS=false;
					}
					//找到了DR用户
					if(selectedDSTag!=-1){
						//如果找到的满足条件的DS用户不止一个，我们取当前最优的DS用户的前面一个DR用户作为备选DS用户
						dfferenceV1=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTag));
						
						if(selectedDSTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTagSecond));
						}
						loveDegree=0;
						int selectedDRTag=-1,selectedDRTagSecond=-1;
						boolean isFirtSelectDR=true;
						//反向查询这个DR用户是不是最优搭配当前的DS用户
						for(int k=0;k<list_dr_original.size();k++){
							float temLoveDegree=Tools.loveDegreeDRToDS(list_dr_original.get(k),list_ds_original.get(selectedDSTag));
							if(temLoveDegree>loveDegree){
								if(!isFirtSelectDR){
									selectedDRTagSecond=selectedDRTag;
								}
								selectedDRTag=k;
								loveDegree=temLoveDegree;
							}
							if(loveDegree>=1){
								break;
							}
							isFirtSelectDR=false;
						}
						
					
							//我们取最优的DS的用户，以及最优的DS用户的前面一个DS用户作为备选DS用户
							dfferenceV3=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							
							//根据差异值分别比较，找到这四队用户中最优的一对，并计算得到差异值，DS用户差异值，DR用户差异值
							if(selectedDRTagSecond>-1){
								dfferenceV4=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
		
							}
							int successDR1=0,successDS1=selectedDSTag;
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								//selectedDSTag=selectedDSTagSecond;
								successDS1=selectedDSTagSecond;
							}
							int successDS2=selectedDSTag,successDR2=selectedDRTag;
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								dfferenceV3=dfferenceV4;
								//selectedDRTag=selectedDRTagSecond;
								successDR2=selectedDRTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
							
							dfferenceValue+=dfferenceV3;
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2));
							successMatchUsers++;
							
							//测试打印差异值数据计算是否正确
							System.out.println("最后计算的differenceValue："+Tools.computeDifferenceValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2)));
							System.out.println("比较得来的differenceValue："+dfferenceV3);
							
							list_dr_original.remove(successDR2);
							list_ds_original.remove(successDS2);
							
							}else{
							
							dfferenceValue+=dfferenceV1;
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							successMatchUsers++;
							
							//测试打印差异值数据计算是否正确
							System.out.println("最后计算的differenceValue："+Tools.computeDifferenceValue(list_dr_original.get(0),list_ds_original.get(successDS1)));
							System.out.println("比较得来的differenceValue："+dfferenceV1);
							
							list_dr_original.remove(0);
							list_ds_original.remove(successDS1);
							}
						
						
					}else{
						//没有找到DR用户
						list_dr_original.remove(0);
						failTotal++;
					}

				}
				//计算累计值
				DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
				DataResult.dr_contary[index]+=drExpectValue;
				DataResult.ds_contary[index]+=dsExpectValue;
				DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
				DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
				DataResult.sum_contary[index]+=dfferenceValue;
			}
			
			
			
			
}
