package unix.knn;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import suanfa.kmeans;

public class KNN {
	
	public static Vector<String> indata = new Vector<>();  //�洢���ļ��ж�ȡ��ԭʼ����  
    public static Vector<double[]> data = new Vector<>();  //�洢����������ÿһ�������ĸ�������ֵ����������
	
	public static void main(String[] args) {
		// һ������������֪��
        List<People> dataList = creatDataSet();
        
//        for(int i=0;i<dataList.size();i++)
//        {
//        	People people=dataList.get(i);
//        	System.out.println(people.getOne());
//        	System.out.println(people.getTwo());
//        }
     // ��������δ֪��
        People x = new People(150,0.38,0.08,0.5,0.5,0.82);
        // ��������������֪�㵽δ֪���ŷʽ���룬�����ݾ����������֪������
        CompareClass compare = new CompareClass();
        Set<Distance> distanceSet = new TreeSet<Distance>(compare);
        for (People people : dataList) {
            distanceSet.add(new Distance(people.getId(), x.getId(), oudistance(people,
                    x)));
           
        }
        // �ġ�ѡȡ�����k����
        double k = 5;
        
        /**
         * �塢����k�������ڷ�����ֵ�Ƶ��
         */
        // 1������ÿ�������������ĵ�ĸ���
        List<Distance> distanceList= new ArrayList<Distance>(distanceSet);
        Map<String, Integer> map = getNumberOfType(distanceList, dataList, k);
//        
        // 2������Ƶ��
        Map<String, Double> p = computeP(map, k);
        
        //3�����Ƶ��
        System.out.println("1.0��Ƶ��"+p.get("1.0"));
        System.out.println("2.0��Ƶ��"+p.get("2.0"));
//        
        x.setType(maxP(p));
        System.out.println("δ֪�������Ϊ��"+x.getType());
	}

	private static List<People> creatDataSet() {
		ArrayList<People> dataList = new ArrayList<People>();
		KNN.loadData("G:/housework/suanfa/dataone.txt");
		KNN.pretreatment(indata);
		for(int i=0;i<data.size();i++)
		{
			People people=new People();
			
				people.setOne(data.get(i)[0]);
				people.setTwo(data.get(i)[1]);
				people.setThree(data.get(i)[2]);
				people.setFour(data.get(i)[3]);
				people.setFive(data.get(i)[4]);
				people.setType(data.get(i)[5]+"");
				people.setId(i);
				//System.out.println(data.get(i)[j]);
			
			dataList.add(people);
		}
		
		return dataList;
	}
	
	public static void pretreatment(Vector<String> indata) {   //����Ԥ������ԭʼ�����е�ÿһ������ֵ��ȡ������ŵ�Vector<double[]>  data��  
        int i = 0;  
        String t;  
       /*Z-score��׼������  
        * double[] sum ={0,0,0,0};
       */
        //min-max��׼��
        double [] a=new double[50];
        double [] b=new double[50];
        double [] c=new double[50];
        double [] d=new double[50];
        double [] e=new double[50];
        double [] max=new double[5];
        double [] min=new double[5];
        
        int count=0;
        
        while (i < indata.size()) {//ȡ��indata�е�ÿһ��ֵ  
        	
            double[] tem = new double[6];  
            t = indata.get(i);  
            String[] sourceStrArray = t.split(",", 6);//ʹ���ַ����ָ����ȡ��������ֵ  
  
            for (int j = 0; j < 5; j++) {  
                tem[j] = Double.parseDouble(sourceStrArray[j]);//��ÿһ���������ĸ�����ֵ����ת�������δ��뵽double[]������
               /*Z-score��׼������  
                * sum[j]+=tem[j];
                */
    
            } 
          //min-max��׼��
            //��ȡ����ֵ
            a[i]=tem[0];
            b[i]=tem[1];
            c[i]=tem[2];
            d[i]=tem[3];
            e[i]=tem[4];
            if(i<25)
            {
            	tem[5] = 1;//tem�ĵ����ֵ��ʾ�������1.0��ʾ��һ�࣬2.0��ʾ�ڶ��࣬3.0��ʾ�����࣬��ʼ��Ϊ0�������κ��� 
            	
            }
            else
            {
            	tem[5] = 2;
            	
            }
            System.out.println(tem[5]);
            data.add(tem);//��ÿһ���������뵽data��  
            i++;
           
        }
	        //min-max��׼������ȡ���ֵ
	         Arrays.sort(a);
	         Arrays.sort(b);
	         Arrays.sort(c);
	         Arrays.sort(d);
	         Arrays.sort(e);
	         
        	 max[0]=a[a.length-1];
        	 min[0]=a[0];
        	 max[1]=b[b.length-1];
        	 min[1]=b[0];
        	 max[2]=c[c.length-1];
        	 min[2]=c[0];
        	 max[3]=d[d.length-1];
        	 min[3]=d[0];
        	 max[4]=e[e.length-1];
        	 min[4]=e[0];
        	//max-min��׼������
        	 NormalizationMax_Min(max,min);
        	 System.out.println("aaaaa"+count);
        
        //Z-score��׼������
//        System.out.println("��һ�������ܺ�:"+sum[0]);
//        System.out.println("��2�������ܺ�:"+sum[1]);
//        System.out.println("��3�������ܺ�:"+sum[2]);
//        System.out.println("��4�������ܺ�:"+sum[3]);
//        Normalization(sum);
    } 
	
	//����Ԥ����֮��һ�� max-min
	  public static void NormalizationMax_Min(double[] max,double[] min) {
		  	for(int i=0;i<data.size();i++)
		  	{
		  		
		  		for(int j=0;j<5;j++)
		  		{	
		  			data.get(i)[j]=(data.get(i)[j]-min[j])/(max[j]-min[j]);
		  		}
		  		
		  	
		  	}
	  }
	  
	//���ز��Ե������ļ�
	  public static boolean loadData(String url) {  
	        try {  
	            Scanner in = new Scanner(new File(url));//�����ļ�  
	            while (in.hasNextLine()) {  
	                String str = in.nextLine();//���ļ���ÿһ�д浽str����ʱ������  
	                indata.add(str);//��ÿһ�������������׷�ӵ�Vector ��  
	            }  
	            return true;  
	        } catch (Exception e) { //���������false  
	            return false;  
	        }  
	    }
	  
	  // ŷʽ�������
	    public static double oudistance(People people, People x) {
	        double temp = Math.pow(people.getOne() - x.getOne(), 2)
	                + Math.pow(people.getTwo() - x.getTwo(), 2)+Math.pow(people.getThree() - x.getThree(), 2)
	                +Math.pow(people.getFour() - x.getFour(), 2)+Math.pow(people.getFive() - x.getFive(), 2);

	        return Math.sqrt(temp);
	    }
	    
	 // ����ÿ����������ĵ�ĸ���
	    public static Map<String, Integer> getNumberOfType(
	            List<Distance> listDistance, List<People> listPeople, double k) {
	        Map<String, Integer> map = new HashMap<String, Integer>();
	        int i = 0;
	        System.out.println("ѡȡ��k���㣬�ɽ���Զ����Ϊ��");
	        for (Distance distance : listDistance) {
	            System.out.println("idΪ" + distance.getId() + ",����Ϊ��"
	                    + distance.getDisatance());
	            long id = distance.getId();
	            // ͨ��id�ҵ���������,���洢��HashMap��
	            for (People point : listPeople) {
	                if (point.getId() == id) {
	                	//�ڴ�����͵�map�У������Ϊ�գ����1
	                    if (map.get(point.getType()) != null)
	                        map.put(point.getType(), map.get(point.getType()) + 1);
	                    else {
	                        map.put(point.getType(), 1);
	                    }
	                }
	            }
	            i++;
	            if (i >= k)
	                break;
	        }
	        return map;
	    
	    }
	    
	 // ����Ƶ��
	    public static Map<String, Double> computeP(Map<String, Integer> map,
	            double k) {
	        Map<String, Double> p = new HashMap<String, Double>();
	        for (Map.Entry<String, Integer> entry : map.entrySet()) {
	            p.put(entry.getKey(), entry.getValue() / k);
	        }
	        return p;
	    }
	    // �ҳ����Ƶ��
	    public static String maxP(Map<String, Double> map) {
	        String key = null;
	        double value = 0.0;
	        for (Map.Entry<String, Double> entry : map.entrySet()) {
	            if (entry.getValue() > value) {
	                key = entry.getKey();
	                value = entry.getValue();
	            }
	        }
	        return key;
	    }
}
