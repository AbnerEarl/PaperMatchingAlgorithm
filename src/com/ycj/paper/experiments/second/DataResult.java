package com.ycj.paper.experiments.second;

import java.util.ArrayList;
import java.util.HashMap;

public class DataResult {
	
	//��ͨ�㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_common;
	 public static ArrayList<HashMap<String,String>> list_dr_original_common;
	 
	//�������㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_semisort;
	 public static ArrayList<HashMap<String,String>> list_dr_original_semisort;
	 
	//ȫ�����㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_allsort;
	 public static ArrayList<HashMap<String,String>> list_dr_original_allsort;
	 
	//SMP�㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_smp;
	 public static ArrayList<HashMap<String,String>> list_dr_original_smp;
	 
	//����㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_rm;
	 public static ArrayList<HashMap<String,String>> list_dr_original_rm;
	 
	//����ƥ���㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_tem;
	 public static ArrayList<HashMap<String,String>> list_dr_original_tem;
	 
	//MTM�㷨ģ���DS�û���DR�û�
	 public static ArrayList<HashMap<String,String>> list_ds_original_mtm;
	 public static ArrayList<HashMap<String,String>> list_dr_original_mtm;

	 //�㷨ƥ��ʧ����
	 public static float []fail_rate=new float[7];
	 //�㷨ƥ�����ֵ
	 public static double []sum_contary=new double[7];
	 //DR�û�����ֵ
	 public static double []dr_contary=new double[7];
	 //DS�û�����ֵ
	 public static double []ds_contary=new double[7];
	 //DR�û������ƽ��������
	 public static double []dr_costs=new double[7];
	 //DS�û���ƽ��������
	 public static double []ds_gains=new double[7];
	 //ѭ������
	 public static int CycleTimes=1;
	 //DR�û�����
	 public static int DRNumber=0;
	 //DS�û�����
	 public static int DSNumber=0;
	 //��¼�ļ�����
	 public static String FileName="";
}
