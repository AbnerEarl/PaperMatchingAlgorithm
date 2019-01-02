package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class DAAlgorithm {
	
	/**
	 * DA算法即延迟接受算法，每个男生在第一轮中向自己心中的No.1示爱。但是各位姑娘们不用立即决定（所以该方法名称中有”deferred”一词），
	 * 而是先hold住了。在第二轮中，每个男生再向心中的No.2示爱。从第二轮开始，每位姑娘们只保留自己到现在为止所收获的最心仪的男生（但是不用答应他，
	 * 只hold在心理），而拒绝其他所有人。而被拒绝的男生（也就是现在尚没有人hold着你的男生）则继续在下一轮中向心中排名的下一个姑娘表白。以此类推，
	 * 一轮轮继续下去，直到所有想示爱的男生都示完为止。此时，每个手中有offer的姑娘，可以选择接受。以上就是deferred acceptance algorithm的
	 * 做法。大家算一下，就会发现，在我们这个简单的例子中，最后的结果是x-A，y-B，z-C三组恋人终成眷侣。而这是一stable的结果。所有6人中，
	 * 你不可能找到一男一女符合以下条件：他们都更愿意抛弃已有的伴侣而与彼此在一起。
	 * 
	 * 延迟接受算法的操作：每个男生在第一轮中向自己心中的No.1示爱。但是各位姑娘们不用立即决定，而是先hold住了。
	 * 在第二轮中，每个男生再向心中的No.2示爱。从第二轮开始，每位姑娘们只保留自己到现在为止所收获的最心仪的男生
	 * （但是不用答应他，只hold在心理），而拒绝其他所有人。而被拒绝的男生（也就是现在尚没有人hold着你的男生）则继续在下一轮中向
	 * 心中排名的下一个姑娘表白。以此类推，一轮轮继续下去，直到所有想示爱的男生都示完为止。此时，每个手中有offer的姑娘，可以选择接受。
	 * @param list_ds
	 * @param list_dr
	 * @param index
	 */

	//在DA算法中，DS用户优先的匹配算法
		public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
			ArrayList<HashMap<String,String>> list_ds_original=list_ds;
			ArrayList<HashMap<String,String>> list_dr_original=list_dr;
			int successMatchUsers=0;
			float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
			for (int i=0;i<list_ds_original.size();i++){
				boolean isNotMatch=false;
				for(int j=0;j<list_dr_original.size();j++){
					if(Tools.isNotToleranceTime(list_dr_original.get(j), list_ds_original.get(i))){
						isNotMatch=true;
						dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
						drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(j), list_ds_original.get(i));
						dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(j),  list_ds_original.get(i));
						drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(j),  list_ds_original.get(i));
						dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(j),  list_ds_original.get(i));
						successMatchUsers++;
						list_dr_original.remove(j);
						break;
					}
				}
				
			}
			
			DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
			DataResult.dr_contary[index]+=drExpectValue;
			DataResult.ds_contary[index]+=dsExpectValue;
			DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
			DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
			DataResult.sum_contary[index]+=dfferenceValue;
		}
		
		//在DA算法中，DR用户优先的匹配算法
		public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
			ArrayList<HashMap<String,String>> list_ds_original=list_ds;
			ArrayList<HashMap<String,String>> list_dr_original=list_dr;
			int failTotal=0;
			int successMatchUsers=0;
			float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
			for (int i=0;i<list_dr_original.size();i++){
				boolean isNotMatch=false;
				for(int j=0;j<list_ds_original.size();j++){
					if(Tools.isNotToleranceTime(list_dr_original.get(i), list_ds_original.get(j))){
						isNotMatch=true;
						dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(i), list_ds_original.get(j));
						drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(i), list_ds_original.get(j));
						dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(i),  list_ds_original.get(j));
						drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(i),  list_ds_original.get(j));
						dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(i),  list_ds_original.get(j));
						successMatchUsers++;
						list_ds_original.remove(j);
						break;
					}
				}
				
				if(!isNotMatch){
					failTotal++;
				}
			}
			
			DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
			DataResult.dr_contary[index]+=drExpectValue;
			DataResult.ds_contary[index]+=dsExpectValue;
			DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
			DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
			DataResult.sum_contary[index]+=dfferenceValue;
		}
		
}
