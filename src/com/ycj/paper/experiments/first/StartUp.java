package com.ycj.paper.experiments.first;

import java.util.ArrayList;
import java.util.HashMap;

public class StartUp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for(int n=10;n<101;n=n+10){
			
			//动态指定记录过程的文件名称
			DataResult.FileName="mtm"+n;
			DataResult.DRNumber=n+1;
			DataResult.DSNumber=51;
			
		//初始化变量
		for(int k=0;k<DataResult.fail_rate.length;k++){
			DataResult.fail_rate[k]=0;
			DataResult.sum_contary[k]=0;
			DataResult.dr_contary[k]=0;
			DataResult.ds_contary[k]=0;
			DataResult.dr_costs[k]=0;
			DataResult.ds_gains[k]=0;
		}
		
		
		
		//开始执行算法
		for(int i=0;i<DataResult.CycleTimes;i++){
			//初始化集合
			DataResult.list_dr_original_common=new ArrayList<>(); 
			DataResult.list_ds_original_common=new ArrayList<>(); 
			
			DataResult.list_dr_original_semisort=new ArrayList<>(); 
			DataResult.list_ds_original_semisort=new ArrayList<>(); 
			
			DataResult.list_dr_original_allsort=new ArrayList<>(); 
			DataResult.list_ds_original_allsort=new ArrayList<>(); 
			
			DataResult.list_dr_original_smp=new ArrayList<>(); 
			DataResult.list_ds_original_smp=new ArrayList<>(); 
			
			DataResult.list_dr_original_rm=new ArrayList<>(); 
			DataResult.list_ds_original_rm=new ArrayList<>(); 
			
			
			DataResult.list_dr_original_tem=new ArrayList<>(); 
			DataResult.list_ds_original_tem=new ArrayList<>(); 
			
			DataResult.list_dr_original_mtm=new ArrayList<>(); 
			DataResult.list_ds_original_mtm=new ArrayList<>(); 
			
			//生成用户
			ArrayList<HashMap<String,String>> DRUsersList=Tools.generateDRUsers(DataResult.DRNumber);
			ArrayList<HashMap<String,String>> DSUsersList=Tools.generateDSUsers(DataResult.DSNumber);
			//System.out.println("\n\n\n====================================原始数据==========================================");
			Tools.saveRecord("\n\n\n====================================原始数据======================================");
			Tools.printDRUsers(DRUsersList);
			Tools.printDSUsers(DSUsersList);
			//System.out.println("==================================================================================\n\n");
			Tools.saveRecord("==================================================================================\n\n");
			
			//调用不同的算法，进行计算，DS用户优先原则
			CommonAlgorithm.DSPriorty(DataResult.list_ds_original_common, DataResult.list_dr_original_common, 0);
			SemiSortAlgorithm.DSPriorty(DataResult.list_ds_original_semisort, DataResult.list_dr_original_semisort, 1);
			AllSortAlgorithm.DSPriorty(DataResult.list_ds_original_allsort, DataResult.list_dr_original_allsort, 2);
			StableMatchingProglemAlgorithm.DSPriorty(DataResult.list_ds_original_smp, DataResult.list_dr_original_smp, 3);
			RandomMatchingAlgorithm.DSPriorty(DataResult.list_ds_original_rm, DataResult.list_dr_original_rm, 4);
			TwoEndsMatchingAlgorithm.DSPriorty(DataResult.list_ds_original_tem, DataResult.list_dr_original_tem, 5);
			ManyToManyAlgorithm.DSPriorty(DataResult.list_ds_original_mtm, DataResult.list_dr_original_mtm, 6);
			//OneToManyAlgorithm.DSPriorty(DataResult.list_ds_original_mtm, DataResult.list_dr_original_mtm, 6);
			
			//调用不同的算法，进行计算，DR用户优先原则
			/*CommonAlgorithm.DRPriorty(DataResult.list_ds_original_common, DataResult.list_dr_original_common, 0);
			SemiSortAlgorithm.DRPriorty(DataResult.list_ds_original_semisort, DataResult.list_dr_original_semisort, 1);
			AllSortAlgorithm.DRPriorty(DataResult.list_ds_original_allsort, DataResult.list_dr_original_allsort, 2);
			StableMatchingProglemAlgorithm.DRPriorty(DataResult.list_ds_original_smp, DataResult.list_dr_original_smp, 3);
			RandomMatchingAlgorithm.DRPriorty(DataResult.list_ds_original_rm, DataResult.list_dr_original_rm, 4);
			TwoEndsMatchingAlgorithm.DRPriorty(DataResult.list_ds_original_tem, DataResult.list_dr_original_tem, 5);
			ManyToManyAlgorithm.DRPriorty(DataResult.list_ds_original_mtm, DataResult.list_dr_original_mtm, 6);*/
			
			//运行一次，各个算法得到的结果
			//System.out.println("====================================运行结果==========================================");
			Tools.saveRecord("====================================运行结果======================================");
			for(int k=0;k<7;k++){
				
				//System.out.println("第 "+i+" 次，算法 "+(k+1)+" 的累积失败率为："+DataResult.fail_rate[k]);
				Tools.saveRecord("第 "+(i+1)+" 次，算法 "+(k+1)+" 的累积失败率为："+DataResult.fail_rate[k]);
				//System.out.println("第 "+i+" 次，算法 "+(k+1)+" 的累积差异值为："+DataResult.sum_contary[k]);
				Tools.saveRecord("第 "+(i+1)+" 次，算法 "+(k+1)+" 的累积差异值为："+DataResult.sum_contary[k]);
				Tools.saveRecord("第 "+(i+1)+" 次，算法 "+(k+1)+" 的累积DR用户差异值为："+DataResult.dr_contary[k]);
				Tools.saveRecord("第 "+(i+1)+" 次，算法 "+(k+1)+" 的累积DS用户差异值为："+DataResult.ds_contary[k]);
			}
			//System.out.println("==================================================================================\n\n\n");
			Tools.saveRecord("==================================================================================\n\n\n");
			
			//数据计算完成，清空数据
			DataResult.list_dr_original_common.clear(); 
			DataResult.list_ds_original_common.clear(); 
			
			DataResult.list_dr_original_semisort.clear(); 
			DataResult.list_ds_original_semisort.clear(); 
			
			DataResult.list_dr_original_allsort.clear(); 
			DataResult.list_ds_original_allsort.clear(); 
			
			DataResult.list_dr_original_smp.clear(); 
			DataResult.list_ds_original_smp.clear(); 
			
			DataResult.list_dr_original_rm.clear(); 
			DataResult.list_ds_original_rm.clear(); 
			
			
			DataResult.list_dr_original_tem.clear(); 
			DataResult.list_ds_original_tem.clear(); 
			
			DataResult.list_dr_original_mtm.clear(); 
			DataResult.list_ds_original_mtm.clear(); 
		}
		
		//算法结束后，计算每种算法的失败率和差异值
		System.out.println("\n\n\n====================================最终结果======================================");
		Tools.saveRecord("\n\n\n====================================最终结果======================================");
		for(int k=0;k<7;k++){
			
			System.out.println("算法 "+(k+1)+" 的最终失败率为："+(DataResult.fail_rate[k]/(float)DataResult.CycleTimes));
			Tools.saveRecord("算法 "+(k+1)+" 的最终失败率为："+(DataResult.fail_rate[k]/(float)DataResult.CycleTimes));
			System.out.println("算法 "+(k+1)+" 的最终数据差值为："+(DataResult.sum_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("算法 "+(k+1)+" 的最终数据差值为："+(DataResult.sum_contary[k]/(double)DataResult.CycleTimes));
			System.out.println("算法 "+(k+1)+" 的DR用户额外花费为："+(DataResult.dr_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("算法 "+(k+1)+" 的DR用户额外花费为："+(DataResult.dr_contary[k]/(double)DataResult.CycleTimes));
			System.out.println("算法 "+(k+1)+" 的DS用户无用数据为："+(DataResult.ds_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("算法 "+(k+1)+" 的DS用户无用数据为："+(DataResult.ds_contary[k]/(double)DataResult.CycleTimes));
			System.out.println("算法 "+(k+1)+" 的DR用户额外花费为："+(DataResult.dr_costs[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("算法 "+(k+1)+" 的DR用户额外花费为："+(DataResult.dr_costs[k]/(double)DataResult.CycleTimes));
			System.out.println("算法 "+(k+1)+" 的DS用户收益为："+(DataResult.ds_gains[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("算法 "+(k+1)+" 的DS用户收益为："+(DataResult.ds_gains[k]/(double)DataResult.CycleTimes));
		}
		System.out.println("==================================================================================\n");
		Tools.saveRecord("==================================================================================\n\n\n");
		
		System.out.println("\n====================================数字显示======================================");
		Tools.saveRecord("\n\n\n====================================数字显示======================================");
		for(int k=0;k<7;k++){
			
			System.out.println((DataResult.fail_rate[k]/(float)DataResult.CycleTimes));
			Tools.saveRecord(""+(DataResult.fail_rate[k]/(float)DataResult.CycleTimes));
			System.out.println((DataResult.sum_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord(""+(DataResult.sum_contary[k]/(double)DataResult.CycleTimes));
			System.out.println((DataResult.dr_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord(""+(DataResult.dr_contary[k]/(double)DataResult.CycleTimes));
			System.out.println((DataResult.ds_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord(""+(DataResult.ds_contary[k]/(double)DataResult.CycleTimes));
			System.out.println((DataResult.dr_costs[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord(""+(DataResult.dr_costs[k]/(double)DataResult.CycleTimes));
			System.out.println((DataResult.ds_gains[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord(""+(DataResult.ds_gains[k]/(double)DataResult.CycleTimes));
		}
		System.out.println("==================================================================================\n\n\n");
		Tools.saveRecord("==================================================================================\n\n\n");
	}
}

}
