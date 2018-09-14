package com.ycj.paper.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManyToManyAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
	 * 7、我们的算法（OTM for short）：分别对DR用户按照需要的流量大小进行排序，对DS用户按照共享的流量大小进行排序；
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
						float temLoveDegree=Tools.loveDegreeToDR(DSUser,list_dr_original.get(j));
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
							float temLoveDegree=Tools.loveDegreeToDS(list_ds_original.get(k),list_dr_original.get(selectedDRTag));
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
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
							
								selectedDRTag=selectedDRTagSecond;
							}
							
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								dfferenceV3=dfferenceV4;
							
								selectedDSTag=selectedDSTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
							//dfferenceValue+=dfferenceV3;
							dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
	
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag)));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(selectedDSTag)));
							System.out.println(dfferenceV3);
							System.out.println();*/
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(selectedDSTag);
							successMatchUsers++;
							}else{
							//dfferenceValue+=dfferenceV1;
							dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag),list_ds_original.get(0));
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag),list_ds_original.get(0));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),list_ds_original.get(0));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag),list_ds_original.get(0));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag),list_ds_original.get(0));
							successMatchUsers++;
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), DSUser));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  DSUser));
							System.out.println(dfferenceV1);
							System.out.println();*/
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(0);
							}
						
						
						
						
						
						/*if(selectedDRTagSecond>=0){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							if(dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								selectedDRTag=selectedDRTagSecond;
							}
						}
						
						dfferenceValue+=dfferenceV1;
						list_dr_original.remove(selectedDRTag);
						list_ds_original.remove(DSUser);*/
						
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
						float temLoveDegree=Tools.loveDegreeToDS(list_ds_original.get(j),DRUser);
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
							float temLoveDegree=Tools.loveDegreeToDR(list_ds_original.get(selectedDSTag),list_dr_original.get(k));
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
							
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								selectedDSTag=selectedDSTagSecond;
							}
							
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								dfferenceV3=dfferenceV4;
								selectedDRTag=selectedDRTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
								successMatchUsers++;
							dfferenceValue+=dfferenceV3;
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag),list_ds_original.get(selectedDSTag));
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag)));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(selectedDSTag)));
							System.out.println(dfferenceV3);
							System.out.println();*/
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(selectedDSTag);
							
							}else{
							
								successMatchUsers++;
							dfferenceValue+=dfferenceV1;
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(0),list_ds_original.get(selectedDSTag));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(0),list_ds_original.get(selectedDSTag));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(0),list_ds_original.get(selectedDSTag));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(0),list_ds_original.get(selectedDSTag));
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), DSUser));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  DSUser));
							System.out.println(dfferenceV1);
							System.out.println();*/
							list_dr_original.remove(0);
							list_ds_original.remove(selectedDSTag);
							}
						
						
						
						
						
						/*if(selectedDRTagSecond>=0){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							if(dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								selectedDRTag=selectedDRTagSecond;
							}
						}
						
						dfferenceValue+=dfferenceV1;
						list_dr_original.remove(selectedDRTag);
						list_ds_original.remove(DSUser);*/
						
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
			
			
			
			
			
			
			
			
			
			//在MTM算法中，DS用户优先的匹配算法
			public static void DSPriortyAll(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
				ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
				ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
				int successMatchUsers=0;
				float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
				while(list_ds_original.size()>0){
					HashMap<String,String> DSUser=list_ds_original.get(0);
					float loveDegree=0;
					int selectedDRTag=-1,selectedDRTagSecond=-1;
					float dfferenceV1=-1,dfferenceV2=-1,dfferenceV3=-1,dfferenceV4=-1;
					float dfferenceDRV1=-1,dfferenceDRV2=-1,dfferenceDRV3=-1,dfferenceDRV4=-1;
					float dfferenceDSV1=-1,dfferenceDSV2=-1,dfferenceDSV3=-1,dfferenceDSV4=-1;
					float drCostsValue1=0,drCostsValue2=0,drCostsValue3=0,drCostsValue4=0;
					float dsGainsValue1=0,dsGainsValue2=0,dsGainsValue3=0,dsGainsValue4=0;
					boolean isFirtSelectDR=true;
					
					//找到最满意的DR用户，就是在满足DR用户的容忍时间内，选择需要流量接近共享流量大小的DR用户
					for(int j=0;j<list_dr_original.size();j++){
						float temLoveDegree=Tools.loveDegreeToDR(DSUser,list_dr_original.get(j));
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
						dfferenceDSV1=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag), DSUser);
						dfferenceDRV1=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), DSUser);
						drCostsValue1=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag), DSUser);
						dsGainsValue1=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag), DSUser);
						if(selectedDRTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							dfferenceDSV2=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							dfferenceDRV2=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							drCostsValue2=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							dsGainsValue2=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTagSecond), DSUser);
						}
						loveDegree=0;
						int selectedDSTag=-1,selectedDSTagSecond=-1;
						boolean isFirtSelectDS=true;
						//反向查询这个DR用户是不是最优搭配当前的DS用户
						for(int k=0;k<list_ds_original.size();k++){
							float temLoveDegree=Tools.loveDegreeToDS(list_ds_original.get(k),list_dr_original.get(selectedDRTag));
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
							dfferenceDSV3=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							dfferenceDRV3=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							drCostsValue3=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							dsGainsValue3=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							//根据差异值分别比较，找到这四队用户中最优的一对，并计算得到差异值，DS用户差异值，DR用户差异值
							if(selectedDSTagSecond>-1){
								dfferenceV4=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
								dfferenceDSV4=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
								dfferenceDRV4=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
								drCostsValue4=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
								dsGainsValue4=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
							}
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								dfferenceDSV1=dfferenceDSV2;
								dfferenceDRV1=dfferenceDRV2;
								drCostsValue1=drCostsValue2;
								dsGainsValue1=dsGainsValue2;
								selectedDRTag=selectedDRTagSecond;
							}
							
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								dfferenceV3=dfferenceV4;
								dfferenceDSV3=dfferenceDSV4;
								dfferenceDRV3=dfferenceDRV4;
								drCostsValue3=drCostsValue4;
								dsGainsValue3=dsGainsValue4;
								selectedDSTag=selectedDSTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
							dfferenceValue+=dfferenceV3;
							drExpectValue+=dfferenceDRV3;
							dsExpectValue+=dfferenceDSV3;
							drCostsValue+=drCostsValue3;
							dsGainsValue+=dsGainsValue3;
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag)));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(selectedDSTag)));
							System.out.println(dfferenceV3);
							System.out.println();*/
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(selectedDSTag);
							successMatchUsers++;
							}else{
							dfferenceValue+=dfferenceV1;
							drExpectValue+=dfferenceDRV1;
							dsExpectValue+=dfferenceDSV1;
							drCostsValue+=drCostsValue1;
							dsGainsValue+=dsGainsValue1;
							successMatchUsers++;
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), DSUser));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  DSUser));
							System.out.println(dfferenceV1);
							System.out.println();*/
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(DSUser);
							}
						
						
						
						
						
						/*if(selectedDRTagSecond>=0){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							if(dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								selectedDRTag=selectedDRTagSecond;
							}
						}
						
						dfferenceValue+=dfferenceV1;
						list_dr_original.remove(selectedDRTag);
						list_ds_original.remove(DSUser);*/
						
					}else{
						//没有找到DR用户
						list_ds_original.remove(DSUser);
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
			public static void DRPriortyAll(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
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
					float dfferenceDRV1=-1,dfferenceDRV2=-1,dfferenceDRV3=-1,dfferenceDRV4=-1;
					float dfferenceDSV1=-1,dfferenceDSV2=-1,dfferenceDSV3=-1,dfferenceDSV4=-1;
					float drCostsValue1=0,drCostsValue2=0,drCostsValue3=0,drCostsValue4=0;
					float dsGainsValue1=0,dsGainsValue2=0,dsGainsValue3=0,dsGainsValue4=0;
					boolean isFirtSelectDS=true;
					
					//找到最满意的DS用户，就是在满足DR用户的容忍时间内，选择需要流量接近需求流量大小的DS用户
					for(int j=0;j<list_ds_original.size();j++){
						float temLoveDegree=Tools.loveDegreeToDS(list_ds_original.get(j),DRUser);
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
						dfferenceDSV1=Tools.computeDSExpectValue(DRUser, list_ds_original.get(selectedDSTag));
						dfferenceDRV1=Tools.computeDRExpectValue(DRUser, list_ds_original.get(selectedDSTag));
						drCostsValue1=Tools.computeDRCostsValue(DRUser, list_ds_original.get(selectedDSTag));
						dsGainsValue1=Tools.computeDSGainsValue(DRUser, list_ds_original.get(selectedDSTag));
						if(selectedDSTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTagSecond));
							dfferenceDSV2=Tools.computeDSExpectValue(DRUser,list_ds_original.get(selectedDSTagSecond));
							dfferenceDRV2=Tools.computeDRExpectValue(DRUser,list_ds_original.get(selectedDSTagSecond));
							drCostsValue2=Tools.computeDRCostsValue(DRUser,list_ds_original.get(selectedDSTagSecond));
							dsGainsValue2=Tools.computeDSGainsValue(DRUser,list_ds_original.get(selectedDSTagSecond));
						}
						loveDegree=0;
						int selectedDRTag=-1,selectedDRTagSecond=-1;
						boolean isFirtSelectDR=true;
						//反向查询这个DR用户是不是最优搭配当前的DS用户
						for(int k=0;k<list_dr_original.size();k++){
							float temLoveDegree=Tools.loveDegreeToDR(list_ds_original.get(selectedDSTag),list_dr_original.get(k));
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
							dfferenceDSV3=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							dfferenceDRV3=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							drCostsValue3=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							dsGainsValue3=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							//根据差异值分别比较，找到这四队用户中最优的一对，并计算得到差异值，DS用户差异值，DR用户差异值
							if(selectedDRTagSecond>-1){
								dfferenceV4=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
								dfferenceDSV4=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
								dfferenceDRV4=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
								drCostsValue4=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
								dsGainsValue4=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
							}
							
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								dfferenceDSV1=dfferenceDSV2;
								dfferenceDRV1=dfferenceDRV2;
								drCostsValue1=drCostsValue2;
								dsGainsValue1=dsGainsValue2;
								selectedDSTag=selectedDSTagSecond;
							}
							
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								dfferenceV3=dfferenceV4;
								dfferenceDSV3=dfferenceDSV4;
								dfferenceDRV3=dfferenceDRV4;
								drCostsValue3=drCostsValue4;
								dsGainsValue3=dsGainsValue4;
								selectedDRTag=selectedDRTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
								successMatchUsers++;
							dfferenceValue+=dfferenceV3;
							drExpectValue+=dfferenceDRV3;
							dsExpectValue+=dfferenceDSV3;
							drCostsValue+=drCostsValue3;
							dsGainsValue+=dsGainsValue3;
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag)));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  list_ds_original.get(selectedDSTag)));
							System.out.println(dfferenceV3);
							System.out.println();*/
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(selectedDSTag);
							
							}else{
								successMatchUsers++;
							dfferenceValue+=dfferenceV1;
							drExpectValue+=dfferenceDRV1;
							dsExpectValue+=dfferenceDSV1;
							drCostsValue+=drCostsValue1;
							dsGainsValue+=dsGainsValue1;
							/*System.out.println(Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), DSUser));
							System.out.println(Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag),  DSUser));
							System.out.println(dfferenceV1);
							System.out.println();*/
							list_dr_original.remove(DRUser);
							list_ds_original.remove(selectedDSTag);
							}
						
						
						
						
						
						/*if(selectedDRTagSecond>=0){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							if(dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								selectedDRTag=selectedDRTagSecond;
							}
						}
						
						dfferenceValue+=dfferenceV1;
						list_dr_original.remove(selectedDRTag);
						list_ds_original.remove(DSUser);*/
						
					}else{
						//没有找到DR用户
						list_dr_original.remove(DRUser);
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
			
			
			
			//在MTM算法中，DR用户优先的匹配算法，备用方法，没有调用
			public static void DRPriortyBack(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
				ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
				ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
				int failTotal=0;
				float dfferenceValue=0,drExpectValue=0,dsExpectValue=0;
				while(list_dr_original.size()>0){
					HashMap<String,String> DRUser=list_dr_original.get(0);
					float loveDegree=0;
					int selectedDSTag=-1,selectedDSTagSecond=-1;
					float dfferenceV1=-1,dfferenceV2=-1,dfferenceV3=-1,dfferenceV4=-1;
					float dfferenceDRV1=-1,dfferenceDRV2=-1,dfferenceDRV3=-1,dfferenceDRV4=-1;
					float dfferenceDSV1=-1,dfferenceDSV2=-1,dfferenceDSV3=-1,dfferenceDSV4=-1;
					boolean isFirtSelectDS=true;
					//找到最满意的DS用户，就是在满足DR用户的容忍时间内，选择需要流量接近共享流量大小的DS用户
					for(int j=0;j<list_ds_original.size();j++){
						float temLoveDegree=Tools.loveDegreeToDS(list_ds_original.get(j),DRUser);
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
					//找到了DS用户
					if(selectedDSTag!=-1){
						//如果找到的满足条件的DS用户不止一个，我们取当前最优的DS用户的前面一个DS用户作为备选DS用户
						dfferenceV1=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTag));
						dfferenceDSV1=Tools.computeDSExpectValue(DRUser, list_ds_original.get(selectedDSTag));
						dfferenceDRV1=Tools.computeDRExpectValue(DRUser, list_ds_original.get(selectedDSTag));
						if(selectedDSTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTagSecond));
							dfferenceDSV2=Tools.computeDSExpectValue(DRUser, list_ds_original.get(selectedDSTagSecond));
							dfferenceDRV2=Tools.computeDRExpectValue(DRUser, list_ds_original.get(selectedDSTagSecond));
						}
						loveDegree=0;
						int selectedDRTag=-1,selectedDRTagSecond=-1;
						boolean isFirtSelectDR=true;
						//反向查询这个DS用户是不是最优搭配当前的DR用户
						for(int k=0;k<list_dr_original.size();k++){
							float temLoveDegree=Tools.loveDegreeToDR(list_ds_original.get(selectedDSTag),list_dr_original.get(k));
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
						
							//根据差异值分别比较，找到这四队用户中最优的一对，并计算得到差异值，DS用户差异值，DR用户差异值
							dfferenceV3=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							dfferenceDSV3=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							dfferenceDRV3=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							if(selectedDRTagSecond>-1){
								dfferenceV4=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), list_ds_original.get(selectedDSTag));
								dfferenceDSV4=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTagSecond),  list_ds_original.get(selectedDSTag));
								dfferenceDRV4=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTagSecond),  list_ds_original.get(selectedDSTag));
							}
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								dfferenceDSV1=dfferenceDSV2;
								dfferenceDRV1=dfferenceDRV2;
								selectedDSTag=selectedDSTagSecond;
							}
							
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								dfferenceV3=dfferenceV4;
								dfferenceDSV3=dfferenceDSV4;
								dfferenceDRV3=dfferenceDRV4;
								selectedDRTag=selectedDRTagSecond;
							}
							
							if(dfferenceV3<dfferenceV1){
							dfferenceValue+=dfferenceV3;
							drExpectValue+=dfferenceDRV3;
							dsExpectValue+=dfferenceDSV3;
							list_dr_original.remove(selectedDRTag);
							list_ds_original.remove(selectedDSTag);
							
							}else{
							dfferenceValue+=dfferenceV1;
							drExpectValue+=dfferenceDRV1;
							dsExpectValue+=dfferenceDSV1;
							list_ds_original.remove(selectedDSTag);
							list_dr_original.remove(DRUser);
							}
						
						
						
						
						
						/*if(selectedDRTagSecond>=0){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
							if(dfferenceV2<dfferenceV1){
								dfferenceV1=dfferenceV2;
								selectedDRTag=selectedDRTagSecond;
							}
						}
						
						dfferenceValue+=dfferenceV1;
						list_dr_original.remove(selectedDRTag);
						list_ds_original.remove(DSUser);*/
						
					}else{
						//没有找到DS用户
						list_dr_original.remove(DRUser);
						failTotal++;
					}

				}
				//计算累计值
				DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
				DataResult.dr_contary[index]+=drExpectValue;
				DataResult.ds_contary[index]+=dsExpectValue;
				DataResult.sum_contary[index]+=dfferenceValue;
			}
}
