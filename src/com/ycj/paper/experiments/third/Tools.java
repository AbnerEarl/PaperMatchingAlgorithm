package com.ycj.paper.experiments.third;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tools {
	
	//��������㷨ģ��DS�û�
	public static ArrayList<HashMap<String,String>> generateDSUsers(int number){
        Random random=new Random();
        DecimalFormat fnum  =   new  DecimalFormat("##0.00");
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        for (int i=0;i<number;i++){
            HashMap<String,String> map=new HashMap<>();
            map.put("tag",""+(i+1));
            //float v=random.nextFloat()+1;
            //String dv=fnum.format(v);
            map.put("speed",""+DataResult.V);
            //int f=random.nextInt(100)+5;
            map.put("flow",""+DataResult.F);
          //���Ƶ���ͬ�㷨�ļ��ϣ��������㣬��������ظ�ʹ��
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
	        for (int i=0;i<number;i++){
	            HashMap<String,String> map=new HashMap<>();
	            map.put("tag",""+(i+1));
	            //int flow=random.nextInt(100)+5;
	            map.put("flow",""+DataResult.f);
	            //int tole=randomT.nextInt(100)+5;
	            map.put("tole",""+DataResult.t);
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
	    
	  //����DS�û�������������
	    public static float computeDSGainsValue(HashMap<String,String> dr,HashMap<String,String> ds){
	    	HashMap<String,String> dr_local=dr;
	    	HashMap<String,String> ds_local=ds;
	    	float dr_flow=Float.parseFloat(dr_local.get("flow"));
	    	float ds_flow=Float.parseFloat(ds_local.get("flow"));
	    	if(dr_flow>=ds_flow){
	    		return 1;
	    	}else{
	    		return dr_flow/ds_flow;
	    	}
	    	
	    }
	    
	  //����DR�û��Ķ��⻨����
	    public static float computeDRCostsValue(HashMap<String,String> dr,HashMap<String,String> ds){
	    	HashMap<String,String> dr_local=dr;
	    	HashMap<String,String> ds_local=ds;
	    	float dr_flow=Float.parseFloat(dr_local.get("flow"));
	    	float ds_flow=Float.parseFloat(ds_local.get("flow"));
	    	if(ds_flow>=dr_flow){
	    		return 0;
	    	}else{
	    		return (dr_flow-ds_flow)/dr_flow;
	    	}
	    	
	    }
	    
	    //DS�û���DR�û�ƫ�ó̶ȵ�����
	    public static float loveDegreeDSToDR(HashMap<String,String> list_ds,HashMap<String,String> list_dr){
	    	float fv=Float.parseFloat(list_ds.get("speed"));
	    	int fs= Integer.parseInt(list_ds.get("flow"));
	    	int fr=Integer.parseInt(list_dr.get("flow"));
	    	int ft=Integer.parseInt(list_dr.get("tole"));
	    	
	    	float result=0;
	        float tole_speed=fr/(float)ft;
	        if (fv>=tole_speed) {
	            if (fs >= fr) {
	                result = 1;
	            } else {
	                result = fs / (float) fr;
	            }
	        }
	        return result;
	    }
	    
	    //DR�û���DS�û�ƫ�ó̶ȵ�����
	    public static float loveDegreeDRToDS(HashMap<String,String> list_dr,HashMap<String,String> list_ds){
	    	float fv=Float.parseFloat(list_ds.get("speed"));
	    	int fs= Integer.parseInt(list_ds.get("flow"));
	    	int fr=Integer.parseInt(list_dr.get("flow"));
	    	int ft=Integer.parseInt(list_dr.get("tole"));
	    	
	    	float result=0;
	        float tole_speed=fr/(float)ft;
	        if (fv>=tole_speed) {
	            if (fr >= fs) {
	                result = 1;
	            } else {
	                result = fr / (float) fs;
	            }
	        }
	        return result;
	    }
	    
	    
	    //���ݼ��Ϻ�Ԫ�ػ�ȡDS�û���ƫ���б�
	    public static ArrayList<HashMap<String,String>> loveDegreeListForDS(HashMap<String,String> user,ArrayList<HashMap<String,String>> list){
	    	ArrayList<HashMap<String,String>> result=new ArrayList<>();
	    	ArrayList<HashMap<String,String>> origin_list=list;
	    	while(origin_list.size()>0){
	    		float loveDegree=loveDegreeDRToDS(origin_list.get(0), user);
	    		int selectedTag=0;
	    		for(int i=1;i<origin_list.size();i++){
	    			float temLove=loveDegreeDRToDS(origin_list.get(i), user);
	    			if(temLove>loveDegree){
	    				loveDegree=temLove;
	    				selectedTag=i;
	    			}
	    		}
	    		result.add(origin_list.get(selectedTag));
	    		origin_list.remove(selectedTag);
	    	}
	    	return result;
	    }
	    
	  //���ݼ��Ϻ�Ԫ�ػ�ȡDR�û���ƫ���б�
	    public static ArrayList<HashMap<String,String>> loveDegreeListForDR(HashMap<String,String> user,ArrayList<HashMap<String,String>> list){
	    	ArrayList<HashMap<String,String>> result=new ArrayList<>();
	    	ArrayList<HashMap<String,String>> origin_list=list;
	    	while(origin_list.size()>0){
	    		float loveDegree=loveDegreeDSToDR(origin_list.get(0), user);
	    		int selectedTag=0;
	    		for(int i=1;i<origin_list.size();i++){
	    			float temLove=loveDegreeDSToDR(origin_list.get(i), user);
	    			if(temLove>loveDegree){
	    				loveDegree=temLove;
	    				selectedTag=i;
	    			}
	    		}
	    		result.add(origin_list.get(selectedTag));
	    		origin_list.remove(selectedTag);
	    	}
	    	return result;
	    }
	    
	    
	    public static void saveRecord(String content) {
	    	FileWriter fw = null;
	    	try {
	    	//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
	    	File f=new File("D:\\"+DataResult.FileName+".txt");
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