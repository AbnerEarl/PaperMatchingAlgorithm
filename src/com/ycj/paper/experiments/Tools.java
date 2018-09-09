package com.ycj.paper.experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//��������㷨ģ��DS�û�
	public static ArrayList<HashMap<String,String>> generateDSUsers(int number){
 
        Random random=new Random();
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        for (int i=1;i<number;i++){
            HashMap<String,String> map=new HashMap<>();
            map.put("tag",""+i);
            float v=random.nextFloat()+1;
            DecimalFormat fnum  =   new  DecimalFormat("##0.00");
            String dv=fnum.format(v);
            map.put("speed",dv);
            int f=random.nextInt(100)+5;
            map.put("flow",""+f);
            DataResult.list_ds_original_common.add(map); 
            DataResult.list_ds_original_semisort.add(map);
            DataResult.list_ds_original_allsort.add(map);
            DataResult.list_ds_original_smp.add(map); 
            DataResult.list_ds_original_rm.add(map); 
            DataResult.list_ds_original_tem.add(map); 
            DataResult.list_ds_original_mtm.add(map); 
            list.add(map);
        }
        return list;
    }
	
	
	//��������㷨ģ��DR�û�
	 public static ArrayList<HashMap<String,String>> generateDRUsers(int number){

	        Random random=new Random();
	        Random randomT=new Random();
	        ArrayList<HashMap<String,String>> list=new ArrayList<>();
	        for (int i=1;i<number;i++){
	            HashMap<String,String> map=new HashMap<>();
	            map.put("tag",""+i);
	            int flow=random.nextInt(100)+5;
	            map.put("flow",""+flow);
	            int tole=randomT.nextInt(100)+5;
	            map.put("tole",""+tole);
	          //���Ƶ���ͬ�㷨�ļ��ϣ��������㣬��������ظ�ʹ��
				DataResult.list_dr_original_common.add(map); 
				DataResult.list_dr_original_semisort.add(map); 
				DataResult.list_dr_original_allsort.add(map); 
				DataResult.list_dr_original_smp.add(map); 
				DataResult.list_dr_original_rm.add(map); 
				DataResult.list_dr_original_tem.add(map); 
				DataResult.list_dr_original_mtm.add(map); 
	            list.add(map);
	        }
	        return list;
	    }

	 //���û��������ݴ�С��������
	 public static ArrayList<HashMap<String,String>> sortUsers (ArrayList<HashMap<String,String>>  list){
	        ArrayList<HashMap<String,String>> temList=new ArrayList<>();
	        for (int i=0;i<list.size();){
	            HashMap<String ,String> ms=list.get(i);
	            int fs=Integer.parseInt(ms.get("flow"));
	            int tag=i;
	            for (int j=0;j<list.size();j++){
	                HashMap<String ,String> mm=list.get(j);
	                int fm=Integer.parseInt(mm.get("flow"));
	                if (fm<fs){
	                    fs=fm;
	                    tag=j;
	                }
	            }
	            temList.add(list.get(tag));
	            list.remove(tag);
	        }

	        return temList;
	    }
	 
	 
	 //��ӡDS�û�����Ϣ
	  public static void printDSUsers(ArrayList<HashMap<String,String>> list){

	        for (int i=0;i<list.size();i++){
	            HashMap<String,String> map=list.get(i);
	            //System.out.println("DS�û���"+map.get("tag")+"        �����ٶȣ�"+map.get("speed")+"          ����������"+map.get("flow"));
	            Tools.saveRecord("DS�û���"+map.get("tag")+"        �����ٶȣ�"+map.get("speed")+"          ����������"+map.get("flow"));
	        }

	    }


	  //��ӡDR�û�����Ϣ
	    public static void printDRUsers(ArrayList<HashMap<String,String>> list){

	        for (int i=0;i<list.size();i++){
	            HashMap<String,String> map=list.get(i);
	            //System.out.println("DR�û���"+map.get("tag")+"          ����������"+map.get("flow")+"          ����ʱ�䣺"+map.get("tole"));
	            Tools.saveRecord("DR�û���"+map.get("tag")+"          ����������"+map.get("flow")+"          ����ʱ�䣺"+map.get("tole"));
	        }

	    }
	    
	    
	   //�����Ƿ���������ʱ��
	    public static boolean isNotToleranceTime(HashMap<String,String> dr,HashMap<String,String> ds){
	    	HashMap<String,String> dr_local=dr;
	    	HashMap<String,String> ds_local=ds;
	    	
	    	if(Float.parseFloat(ds_local.get("speed"))>=Float.parseFloat(dr_local.get("flow"))/Float.parseFloat(dr_local.get("tole"))){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    
	    //����DS�û���DR�û�֮��Ĳ���ֵ
	    public static float computeDifferenceValue(HashMap<String,String> dr,HashMap<String,String> ds){
	    	HashMap<String,String> dr_local=dr;
	    	HashMap<String,String> ds_local=ds;
	    	return Math.abs(Float.parseFloat(ds_local.get("flow"))-Float.parseFloat(dr_local.get("flow")));
	    }

	    
	  //����DR�û������Ĳ���ֵ
	    public static float computeDRExpectValue(HashMap<String,String> dr,HashMap<String,String> ds){
	    	HashMap<String,String> dr_local=dr;
	    	HashMap<String,String> ds_local=ds;
	    	float dr_flow=Float.parseFloat(dr_local.get("flow"));
	    	float ds_flow=Float.parseFloat(ds_local.get("flow"));
	    	if(ds_flow>=dr_flow){
	    		return 0;
	    	}else{
	    		return dr_flow-ds_flow;
	    	}
	    	
	    }
	    
	  //����DS�û������Ĳ���ֵ
	    public static float computeDSExpectValue(HashMap<String,String> dr,HashMap<String,String> ds){
	    	HashMap<String,String> dr_local=dr;
	    	HashMap<String,String> ds_local=ds;
	    	float dr_flow=Float.parseFloat(dr_local.get("flow"));
	    	float ds_flow=Float.parseFloat(ds_local.get("flow"));
	    	if(dr_flow>=ds_flow){
	    		return 0;
	    	}else{
	    		return ds_flow-dr_flow;
	    	}
	    	
	    }
	    
	    
	    //DS�û���DR�û�ƫ�ó̶ȵ�����
	    public static float loveDegreeToDR(HashMap<String,String> list_ds,HashMap<String,String> list_dr){
	    	float v=Float.parseFloat(list_ds.get("speed"));
	    	int fs= Integer.parseInt(list_ds.get("flow"));
	    	int fr=Integer.parseInt(list_dr.get("flow"));
	    	int ft=Integer.parseInt(list_dr.get("tole"));
	    	
	    	float result=0;
	        float tole_speed=fr/(float)ft;
	        if (v>=tole_speed) {
	            if (fr >= fs) {
	                result = 1;
	            } else {
	                result = fr / (float) fs;
	            }
	        }
	        return result;
	    }
	    
	    //DR�û���DS�û�ƫ�ó̶ȵ�����
	    public static float loveDegreeToDS(HashMap<String,String> list_ds,HashMap<String,String> list_dr){
	    	float v=Float.parseFloat(list_ds.get("speed"));
	    	int fs= Integer.parseInt(list_ds.get("flow"));
	    	int fr=Integer.parseInt(list_dr.get("flow"));
	    	int ft=Integer.parseInt(list_dr.get("tole"));
	    	
	    	float result=0;
	        float tole_speed=fr/(float)ft;
	        if (v>=tole_speed) {
	            if (fs >= fr) {
	                result = 1;
	            } else {
	                result = fs / (float) fr;
	            }
	        }
	        return result;
	    }
	    
	    
	    public static void saveRecord(String content) {
	    	FileWriter fw = null;
	    	try {
	    	//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
	    	File f=new File("D:\\PaperData.txt");
	    	fw = new FileWriter(f, true);
	    	} catch (IOException e) {
	    	e.printStackTrace();
	    	}
	    	PrintWriter pw = new PrintWriter(fw);
	    	pw.println(content);
	    	pw.flush();
	    	try {
	    	fw.flush();
	    	pw.close();
	    	fw.close();
	    	} catch (IOException e) {
	    	e.printStackTrace();
	    	}
	    	}
	    
}
