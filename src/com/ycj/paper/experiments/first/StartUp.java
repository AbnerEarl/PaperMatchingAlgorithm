package com.ycj.paper.experiments.first;

import java.util.ArrayList;
import java.util.HashMap;

public class StartUp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for(int n=10;n<101;n=n+10){
			
			//��ָ̬����¼���̵��ļ�����
			DataResult.FileName="mtm"+n;
			DataResult.DRNumber=n+1;
			DataResult.DSNumber=51;
			
		//��ʼ������
		for(int k=0;k<DataResult.fail_rate.length;k++){
			DataResult.fail_rate[k]=0;
			DataResult.sum_contary[k]=0;
			DataResult.dr_contary[k]=0;
			DataResult.ds_contary[k]=0;
			DataResult.dr_costs[k]=0;
			DataResult.ds_gains[k]=0;
		}
		
		
		
		//��ʼִ���㷨
		for(int i=0;i<DataResult.CycleTimes;i++){
			//��ʼ������
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
			
			//�����û�
			ArrayList<HashMap<String,String>> DRUsersList=Tools.generateDRUsers(DataResult.DRNumber);
			ArrayList<HashMap<String,String>> DSUsersList=Tools.generateDSUsers(DataResult.DSNumber);
			//System.out.println("\n\n\n====================================ԭʼ����==========================================");
			Tools.saveRecord("\n\n\n====================================ԭʼ����======================================");
			Tools.printDRUsers(DRUsersList);
			Tools.printDSUsers(DSUsersList);
			//System.out.println("==================================================================================\n\n");
			Tools.saveRecord("==================================================================================\n\n");
			
			//���ò�ͬ���㷨�����м��㣬DS�û�����ԭ��
			CommonAlgorithm.DSPriorty(DataResult.list_ds_original_common, DataResult.list_dr_original_common, 0);
			SemiSortAlgorithm.DSPriorty(DataResult.list_ds_original_semisort, DataResult.list_dr_original_semisort, 1);
			AllSortAlgorithm.DSPriorty(DataResult.list_ds_original_allsort, DataResult.list_dr_original_allsort, 2);
			StableMatchingProglemAlgorithm.DSPriorty(DataResult.list_ds_original_smp, DataResult.list_dr_original_smp, 3);
			RandomMatchingAlgorithm.DSPriorty(DataResult.list_ds_original_rm, DataResult.list_dr_original_rm, 4);
			TwoEndsMatchingAlgorithm.DSPriorty(DataResult.list_ds_original_tem, DataResult.list_dr_original_tem, 5);
			ManyToManyAlgorithm.DSPriorty(DataResult.list_ds_original_mtm, DataResult.list_dr_original_mtm, 6);
			//OneToManyAlgorithm.DSPriorty(DataResult.list_ds_original_mtm, DataResult.list_dr_original_mtm, 6);
			
			//���ò�ͬ���㷨�����м��㣬DR�û�����ԭ��
			/*CommonAlgorithm.DRPriorty(DataResult.list_ds_original_common, DataResult.list_dr_original_common, 0);
			SemiSortAlgorithm.DRPriorty(DataResult.list_ds_original_semisort, DataResult.list_dr_original_semisort, 1);
			AllSortAlgorithm.DRPriorty(DataResult.list_ds_original_allsort, DataResult.list_dr_original_allsort, 2);
			StableMatchingProglemAlgorithm.DRPriorty(DataResult.list_ds_original_smp, DataResult.list_dr_original_smp, 3);
			RandomMatchingAlgorithm.DRPriorty(DataResult.list_ds_original_rm, DataResult.list_dr_original_rm, 4);
			TwoEndsMatchingAlgorithm.DRPriorty(DataResult.list_ds_original_tem, DataResult.list_dr_original_tem, 5);
			ManyToManyAlgorithm.DRPriorty(DataResult.list_ds_original_mtm, DataResult.list_dr_original_mtm, 6);*/
			
			//����һ�Σ������㷨�õ��Ľ��
			//System.out.println("====================================���н��==========================================");
			Tools.saveRecord("====================================���н��======================================");
			for(int k=0;k<7;k++){
				
				//System.out.println("�� "+i+" �Σ��㷨 "+(k+1)+" ���ۻ�ʧ����Ϊ��"+DataResult.fail_rate[k]);
				Tools.saveRecord("�� "+(i+1)+" �Σ��㷨 "+(k+1)+" ���ۻ�ʧ����Ϊ��"+DataResult.fail_rate[k]);
				//System.out.println("�� "+i+" �Σ��㷨 "+(k+1)+" ���ۻ�����ֵΪ��"+DataResult.sum_contary[k]);
				Tools.saveRecord("�� "+(i+1)+" �Σ��㷨 "+(k+1)+" ���ۻ�����ֵΪ��"+DataResult.sum_contary[k]);
				Tools.saveRecord("�� "+(i+1)+" �Σ��㷨 "+(k+1)+" ���ۻ�DR�û�����ֵΪ��"+DataResult.dr_contary[k]);
				Tools.saveRecord("�� "+(i+1)+" �Σ��㷨 "+(k+1)+" ���ۻ�DS�û�����ֵΪ��"+DataResult.ds_contary[k]);
			}
			//System.out.println("==================================================================================\n\n\n");
			Tools.saveRecord("==================================================================================\n\n\n");
			
			//���ݼ�����ɣ��������
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
		
		//�㷨�����󣬼���ÿ���㷨��ʧ���ʺͲ���ֵ
		System.out.println("\n\n\n====================================���ս��======================================");
		Tools.saveRecord("\n\n\n====================================���ս��======================================");
		for(int k=0;k<7;k++){
			
			System.out.println("�㷨 "+(k+1)+" ������ʧ����Ϊ��"+(DataResult.fail_rate[k]/(float)DataResult.CycleTimes));
			Tools.saveRecord("�㷨 "+(k+1)+" ������ʧ����Ϊ��"+(DataResult.fail_rate[k]/(float)DataResult.CycleTimes));
			System.out.println("�㷨 "+(k+1)+" ���������ݲ�ֵΪ��"+(DataResult.sum_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("�㷨 "+(k+1)+" ���������ݲ�ֵΪ��"+(DataResult.sum_contary[k]/(double)DataResult.CycleTimes));
			System.out.println("�㷨 "+(k+1)+" ��DR�û����⻨��Ϊ��"+(DataResult.dr_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("�㷨 "+(k+1)+" ��DR�û����⻨��Ϊ��"+(DataResult.dr_contary[k]/(double)DataResult.CycleTimes));
			System.out.println("�㷨 "+(k+1)+" ��DS�û���������Ϊ��"+(DataResult.ds_contary[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("�㷨 "+(k+1)+" ��DS�û���������Ϊ��"+(DataResult.ds_contary[k]/(double)DataResult.CycleTimes));
			System.out.println("�㷨 "+(k+1)+" ��DR�û����⻨��Ϊ��"+(DataResult.dr_costs[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("�㷨 "+(k+1)+" ��DR�û����⻨��Ϊ��"+(DataResult.dr_costs[k]/(double)DataResult.CycleTimes));
			System.out.println("�㷨 "+(k+1)+" ��DS�û�����Ϊ��"+(DataResult.ds_gains[k]/(double)DataResult.CycleTimes));
			Tools.saveRecord("�㷨 "+(k+1)+" ��DS�û�����Ϊ��"+(DataResult.ds_gains[k]/(double)DataResult.CycleTimes));
		}
		System.out.println("==================================================================================\n");
		Tools.saveRecord("==================================================================================\n\n\n");
		
		System.out.println("\n====================================������ʾ======================================");
		Tools.saveRecord("\n\n\n====================================������ʾ======================================");
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
