package com.ycj.paper.experiments;

import java.util.ArrayList;
import java.util.HashMap;

public class OneToManyAlgorithm {

	//一对多算法的DS用户优先匹配，一个DS用户，多个DR用户
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		HashMap<String,String> DSUser=list_ds_original.get(0);
		float loveDegree=0;
		int selectedDRTag=-1,selectedDRTagSecond=-1;
		boolean isFirtSelectDR=true;
		for (int i=0;i<list_dr_original.size();i++){
			float temLoveDegree=Tools.loveDegreeToDR(DSUser,list_dr_original.get(i));
			if(temLoveDegree>loveDegree){
				if(!isFirtSelectDR){
					selectedDRTagSecond=selectedDRTag;
				}
				selectedDRTag=i;
				loveDegree=temLoveDegree;
			}
			if(loveDegree>=1){
				break;
			}
			isFirtSelectDR=false;
		}
		
		if(selectedDRTag>-1){
			
		
		float dfferenceV1=-1,dfferenceV2=-1;
		dfferenceV1=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTag), DSUser);
		if(selectedDRTagSecond>-1){
			dfferenceV2=Tools.computeDifferenceValue(list_dr_original.get(selectedDRTagSecond), DSUser);	
		}
		if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
			dfferenceValue=dfferenceV2;
			dsExpectValue=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTagSecond), DSUser);
			drExpectValue=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTagSecond), DSUser);
			drCostsValue=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTagSecond), DSUser);
			dsGainsValue=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTagSecond), DSUser);
			list_dr_original.remove(selectedDRTagSecond);
		}else{
			dfferenceValue=dfferenceV1;
			dsExpectValue=Tools.computeDSExpectValue(list_dr_original.get(selectedDRTag), DSUser);
			drExpectValue=Tools.computeDRExpectValue(list_dr_original.get(selectedDRTag), DSUser);
			drCostsValue=Tools.computeDRCostsValue(list_dr_original.get(selectedDRTag), DSUser);
			dsGainsValue=Tools.computeDSGainsValue(list_dr_original.get(selectedDRTag), DSUser);
			list_dr_original.remove(selectedDRTag);
		}
		}
		DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue;
		DataResult.ds_gains[index]+=dsGainsValue;
		DataResult.sum_contary[index]+=dfferenceValue;
		
	}
	
	//一对多算法的DR用户优先匹配，一个DR用户，多个DS用户
	public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		HashMap<String,String> DRUser=list_dr_original.get(0);
		float loveDegree=0;
		int failTotal=0;
		int selectedDSTag=-1,selectedDSTagSecond=-1;
		boolean isFirtSelectDS=true;
		for (int i=0;i<list_ds_original.size();i++){
			float temLoveDegree=Tools.loveDegreeToDS(list_ds_original.get(i),DRUser);
			if(temLoveDegree>loveDegree){
				if(!isFirtSelectDS){
					selectedDSTagSecond=selectedDSTag;
				}
				selectedDSTag=i;
				loveDegree=temLoveDegree;
			}
			if(loveDegree>=1){
				break;
			}
			isFirtSelectDS=false;
		}
		
		if(selectedDSTag>-1){
			
		
		float dfferenceV1=-1,dfferenceV2=-1;
		dfferenceV1=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTag));
		if(selectedDSTagSecond>-1){
			dfferenceV2=Tools.computeDifferenceValue(DRUser,list_ds_original.get(selectedDSTagSecond));
		}
		if(dfferenceV2>-1&&dfferenceV2<dfferenceV1){
			dfferenceValue=dfferenceV2;
			dsExpectValue=Tools.computeDSExpectValue(DRUser,list_ds_original.get(selectedDSTagSecond));
			drExpectValue=Tools.computeDRExpectValue(DRUser,list_ds_original.get(selectedDSTagSecond));
			drCostsValue=Tools.computeDRCostsValue(DRUser,list_ds_original.get(selectedDSTagSecond));
			dsGainsValue=Tools.computeDSGainsValue(DRUser,list_ds_original.get(selectedDSTagSecond));
			list_ds_original.remove(selectedDSTagSecond);
		}else{
			dfferenceValue=dfferenceV1;
			dsExpectValue=Tools.computeDSExpectValue(DRUser,list_ds_original.get(selectedDSTag));
			drExpectValue=Tools.computeDRExpectValue(DRUser,list_ds_original.get(selectedDSTag));
			drCostsValue=Tools.computeDRCostsValue(DRUser,list_ds_original.get(selectedDSTag));
			dsGainsValue=Tools.computeDSGainsValue(DRUser,list_ds_original.get(selectedDSTag));
			list_ds_original.remove(selectedDSTag);
		}
		}else{
			failTotal++;
		}
		DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue;
		DataResult.ds_gains[index]+=dsGainsValue;
		DataResult.sum_contary[index]+=dfferenceValue;
	}


}
