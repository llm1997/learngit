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
	
	public static Vector<String> indata = new Vector<>();  //存储从文件中读取的原始数据  
    public static Vector<double[]> data = new Vector<>();  //存储经过处理后的每一个样本的各个属性值和所属分类
	
	public static void main(String[] args) {
		// 一、输入所有已知点
        List<People> dataList = creatDataSet();
        
//        for(int i=0;i<dataList.size();i++)
//        {
//        	People people=dataList.get(i);
//        	System.out.println(people.getOne());
//        	System.out.println(people.getTwo());
//        }
     // 二、输入未知点
        People x = new People(150,0.38,0.08,0.5,0.5,0.82);
        // 三、计算所有已知点到未知点的欧式距离，并根据距离对所有已知点排序
        CompareClass compare = new CompareClass();
        Set<Distance> distanceSet = new TreeSet<Distance>(compare);
        for (People people : dataList) {
            distanceSet.add(new Distance(people.getId(), x.getId(), oudistance(people,
                    x)));
           
        }
        // 四、选取最近的k个点
        double k = 5;
        
        /**
         * 五、计算k个点所在分类出现的频率
         */
        // 1、计算每个分类所包含的点的个数
        List<Distance> distanceList= new ArrayList<Distance>(distanceSet);
        Map<String, Integer> map = getNumberOfType(distanceList, dataList, k);
//        
        // 2、计算频率
        Map<String, Double> p = computeP(map, k);
        
        //3、输出频率
        System.out.println("1.0类频率"+p.get("1.0"));
        System.out.println("2.0类频率"+p.get("2.0"));
//        
        x.setType(maxP(p));
        System.out.println("未知点的类型为："+x.getType());
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
	
	public static void pretreatment(Vector<String> indata) {   //数据预处理，将原始数据中的每一个属性值提取出来存放到Vector<double[]>  data中  
        int i = 0;  
        String t;  
       /*Z-score标准化方法  
        * double[] sum ={0,0,0,0};
       */
        //min-max标准化
        double [] a=new double[50];
        double [] b=new double[50];
        double [] c=new double[50];
        double [] d=new double[50];
        double [] e=new double[50];
        double [] max=new double[5];
        double [] min=new double[5];
        
        int count=0;
        
        while (i < indata.size()) {//取出indata中的每一行值  
        	
            double[] tem = new double[6];  
            t = indata.get(i);  
            String[] sourceStrArray = t.split(",", 6);//使用字符串分割函数提取出各属性值  
  
            for (int j = 0; j < 5; j++) {  
                tem[j] = Double.parseDouble(sourceStrArray[j]);//将每一个的样本的各属性值类型转换后依次存入到double[]数组中
               /*Z-score标准化方法  
                * sum[j]+=tem[j];
                */
    
            } 
          //min-max标准化
            //获取单独值
            a[i]=tem[0];
            b[i]=tem[1];
            c[i]=tem[2];
            d[i]=tem[3];
            e[i]=tem[4];
            if(i<25)
            {
            	tem[5] = 1;//tem的第五个值表示所属类别，1.0表示第一类，2.0表示第二类，3.0表示第三类，初始化为0不属于任何类 
            	
            }
            else
            {
            	tem[5] = 2;
            	
            }
            System.out.println(tem[5]);
            data.add(tem);//将每一个样本加入到data中  
            i++;
           
        }
	        //min-max标准化，获取最大值
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
        	//max-min标准化方法
        	 NormalizationMax_Min(max,min);
        	 System.out.println("aaaaa"+count);
        
        //Z-score标准化方法
//        System.out.println("第一类数据总和:"+sum[0]);
//        System.out.println("第2类数据总和:"+sum[1]);
//        System.out.println("第3类数据总和:"+sum[2]);
//        System.out.println("第4类数据总和:"+sum[3]);
//        Normalization(sum);
    } 
	
	//数据预处理之归一化 max-min
	  public static void NormalizationMax_Min(double[] max,double[] min) {
		  	for(int i=0;i<data.size();i++)
		  	{
		  		
		  		for(int j=0;j<5;j++)
		  		{	
		  			data.get(i)[j]=(data.get(i)[j]-min[j])/(max[j]-min[j]);
		  		}
		  		
		  	
		  	}
	  }
	  
	//加载测试的数据文件
	  public static boolean loadData(String url) {  
	        try {  
	            Scanner in = new Scanner(new File(url));//读入文件  
	            while (in.hasNextLine()) {  
	                String str = in.nextLine();//将文件的每一行存到str的临时变量中  
	                indata.add(str);//将每一个样本点的数据追加到Vector 中  
	            }  
	            return true;  
	        } catch (Exception e) { //如果出错返回false  
	            return false;  
	        }  
	    }
	  
	  // 欧式距离计算
	    public static double oudistance(People people, People x) {
	        double temp = Math.pow(people.getOne() - x.getOne(), 2)
	                + Math.pow(people.getTwo() - x.getTwo(), 2)+Math.pow(people.getThree() - x.getThree(), 2)
	                +Math.pow(people.getFour() - x.getFour(), 2)+Math.pow(people.getFive() - x.getFive(), 2);

	        return Math.sqrt(temp);
	    }
	    
	 // 计算每个分类包含的点的个数
	    public static Map<String, Integer> getNumberOfType(
	            List<Distance> listDistance, List<People> listPeople, double k) {
	        Map<String, Integer> map = new HashMap<String, Integer>();
	        int i = 0;
	        System.out.println("选取的k个点，由近及远依次为：");
	        for (Distance distance : listDistance) {
	            System.out.println("id为" + distance.getId() + ",距离为："
	                    + distance.getDisatance());
	            long id = distance.getId();
	            // 通过id找到所属类型,并存储到HashMap中
	            for (People point : listPeople) {
	                if (point.getId() == id) {
	                	//在存放类型的map中，如果不为空，则加1
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
	    
	 // 计算频率
	    public static Map<String, Double> computeP(Map<String, Integer> map,
	            double k) {
	        Map<String, Double> p = new HashMap<String, Double>();
	        for (Map.Entry<String, Integer> entry : map.entrySet()) {
	            p.put(entry.getKey(), entry.getValue() / k);
	        }
	        return p;
	    }
	    // 找出最大频率
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
