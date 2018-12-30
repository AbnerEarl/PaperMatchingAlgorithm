package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;


public class MTMAlgorithm {

	/*
	 * ���ǵ��㷨��MTM for short�����ֱ��DR�û�������Ҫ��������С�������򣬶�DS�û����չ����������С��������
	 * DR�û�����ԭ�򣬷ֱ�����ÿ��DR�û���DS�û����ϵ�һ��ƫ�ó̶ȵ����򼯺�RS��ÿ��DR�û�����ƫ�õ�RS������ѡȡ��һ����û��ƥ���DS�û���
	 * �������DS�û���������DR�û��Ĳ���ֵ�Ƿ�ȵ�ǰ��DR�û��Ĳ���ֵ���������ƥ�䵱ǰ��DR�û���DS�û������С��ƥ����С����ֵ��DR�û���DS�û���
	 * DS�û�����ԭ�򣬷ֱ�����ÿ��DS�û���DR�û����ϵ�һ��ƫ�ó̶ȵ����򼯺�SR��ÿ��DS�û�����ƫ�õ�SR������ѡȡ��һ����û��ƥ���DR�û���
	 * �������DR�û���������DS�û��Ĳ���ֵ�Ƿ�ȵ�ǰ��DR�û��Ĳ���ֵ���������ƥ�䵱ǰ��DR�û���DS�û������С��ƥ����С����ֵ��DR�û���DS�û���
	 * 
	 * 
	 */


		//��MTM�㷨�У�DS�û����ȵ�ƥ���㷨
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
					
					//�ҵ��������DR�û�������������DR�û�������ʱ���ڣ�ѡ����Ҫ�����ӽ�����������С��DR�û�
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
					//�ҵ���DR�û�
					if(selectedDRTag!=-1){
						//����ҵ�������������DR�û���ֹһ��������ȡ��ǰ���ŵ�DR�û���ǰ��һ��DR�û���Ϊ��ѡDR�û�
						dfferenceV1=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), DSUser);
						if(selectedDRTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);
						}
						loveDegree=0;
						int selectedDSTag=-1,selectedDSTagSecond=-1;
						boolean isFirtSelectDS=true;
						//�����ѯ���DR�û��ǲ������Ŵ��䵱ǰ��DS�û�
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
						
					
							//����ȡ���ŵ�DS���û����Լ����ŵ�DS�û���ǰ��һ��DS�û���Ϊ��ѡDS�û�
							dfferenceV3=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							
							//���ݲ���ֵ�ֱ�Ƚϣ��ҵ����Ķ��û������ŵ�һ�ԣ�������õ�����ֵ��DS�û�����ֵ��DR�û�����ֵ
							if(selectedDSTagSecond>-1){
								dfferenceV4=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTagSecond));
								
							}
							int successDS1=0,successDR1=selectedDRTag;
							if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
								//��һ��ƥ��ѡȡ����ֵ��С��һ���û�
								dfferenceV1=dfferenceV2;
								//��һ��ƥ���Ӧ���±꽻��
								//selectedDRTag=selectedDRTagSecond;
								successDR1=selectedDRTagSecond;
							}
							int successDR2=selectedDRTag,successDS2=selectedDSTag;
							if(dfferenceV4>-1&&dfferenceV4<dfferenceV3){
								//�ڶ���ƥ��ѡȡ����ֵ��С��һ���û�
								dfferenceV3=dfferenceV4;
								//�ڶ���ƥ���Ӧ���±꽻��
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
							
							//���Դ�ӡ����ֵ���ݼ����Ƿ���ȷ
							System.out.println("�������differenceValue��"+Tools.computeDifferenceValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2)));
							System.out.println("�Ƚϵ�����differenceValue��"+dfferenceV3);
							
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
							
							//���Դ�ӡ����ֵ���ݼ����Ƿ���ȷ
							System.out.println("�������differenceValue��"+Tools.computeDifferenceValue(list_dr_original.get(successDR1),list_ds_original.get(0)));
							System.out.println("�Ƚϵ�����differenceValue��"+dfferenceV1);
						
							list_dr_original.remove(successDR1);
							list_ds_original.remove(0);
							}
						
						
					}else{
						//û���ҵ�DR�û�
						list_ds_original.remove(0);
					}

				}
				//�����ۼ�ֵ
				DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
				DataResult.dr_contary[index]+=drExpectValue;
				DataResult.ds_contary[index]+=dsExpectValue;
				DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
				DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
				DataResult.sum_contary[index]+=dfferenceValue;
			}
			
			
			
			
			//��MTM�㷨�У�DR�û����ȵ�ƥ���㷨
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
					
					//�ҵ��������DS�û�������������DR�û�������ʱ���ڣ�ѡ����Ҫ�����ӽ�����������С��DS�û�
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
					//�ҵ���DR�û�
					if(selectedDSTag!=-1){
						//����ҵ�������������DS�û���ֹһ��������ȡ��ǰ���ŵ�DS�û���ǰ��һ��DR�û���Ϊ��ѡDS�û�
						dfferenceV1=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTag));
						
						if(selectedDSTagSecond>-1){
							dfferenceV2=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTagSecond));
						}
						loveDegree=0;
						int selectedDRTag=-1,selectedDRTagSecond=-1;
						boolean isFirtSelectDR=true;
						//�����ѯ���DR�û��ǲ������Ŵ��䵱ǰ��DS�û�
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
						
					
							//����ȡ���ŵ�DS���û����Լ����ŵ�DS�û���ǰ��һ��DS�û���Ϊ��ѡDS�û�
							dfferenceV3=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), list_ds_original.get(selectedDSTag));
							
							//���ݲ���ֵ�ֱ�Ƚϣ��ҵ����Ķ��û������ŵ�һ�ԣ�������õ�����ֵ��DS�û�����ֵ��DR�û�����ֵ
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
							
							//���Դ�ӡ����ֵ���ݼ����Ƿ���ȷ
							System.out.println("�������differenceValue��"+Tools.computeDifferenceValue(list_dr_original.get(successDR2),list_ds_original.get(successDS2)));
							System.out.println("�Ƚϵ�����differenceValue��"+dfferenceV3);
							
							list_dr_original.remove(successDR2);
							list_ds_original.remove(successDS2);
							
							}else{
							
							dfferenceValue+=dfferenceV1;
							drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(0),list_ds_original.get(successDS1));
							successMatchUsers++;
							
							//���Դ�ӡ����ֵ���ݼ����Ƿ���ȷ
							System.out.println("�������differenceValue��"+Tools.computeDifferenceValue(list_dr_original.get(0),list_ds_original.get(successDS1)));
							System.out.println("�Ƚϵ�����differenceValue��"+dfferenceV1);
							
							list_dr_original.remove(0);
							list_ds_original.remove(successDS1);
							}
						
						
					}else{
						//û���ҵ�DR�û�
						list_dr_original.remove(0);
						failTotal++;
					}

				}
				//�����ۼ�ֵ
				DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
				DataResult.dr_contary[index]+=drExpectValue;
				DataResult.ds_contary[index]+=dsExpectValue;
				DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
				DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
				DataResult.sum_contary[index]+=dfferenceValue;
			}
			
			
			
			
}
