package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class DataResult {
	
	//普通算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_common;
	 public static ArrayList<HashMap<String,String>> list_dr_original_common;
	 
	//半排序算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_semisort;
	 public static ArrayList<HashMap<String,String>> list_dr_original_semisort;
	 
	//全排序算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_allsort;
	 public static ArrayList<HashMap<String,String>> list_dr_original_allsort;
	 
	//SMP算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_smp;
	 public static ArrayList<HashMap<String,String>> list_dr_original_smp;
	 
	//随机算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_rm;
	 public static ArrayList<HashMap<String,String>> list_dr_original_rm;
	 
	//两端匹配算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_tem;
	 public static ArrayList<HashMap<String,String>> list_dr_original_tem;
	 
	//MTM算法模拟的DS用户和DR用户
	 public static ArrayList<HashMap<String,String>> list_ds_original_mtm;
	 public static ArrayList<HashMap<String,String>> list_dr_original_mtm;

	 //算法匹配失败率
	 public static float []fail_rate=new float[7];
	 //算法匹配差异值
	 public static double []sum_contary=new double[7];
	 //DR用户差异值
	 public static double []dr_contary=new double[7];
	 //DS用户差异值
	 public static double []ds_contary=new double[7];
	 //DR用户额外的平均花费率
	 public static double []dr_costs=new double[7];
	 //DS用户的平均收益率
	 public static double []ds_gains=new double[7];
	 //循环次数
	 public static int CycleTimes=1;
	 //DR用户数量
	 public static int DRNumber=0;
	 //DS用户数量
	 public static int DSNumber=0;
	 //记录文件名称
	 public static String FileName="";
}
