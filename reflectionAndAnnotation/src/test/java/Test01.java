import com.yc.refection.Showable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Test01 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入类路径");
            String path = sc.nextLine();
            System.out.println("待加载的类为:" + path);

            Class c = Class.forName(path);
            String name = c.getName();
            System.out.println(name);

            //Field[] fs=c.getFields();
            Field[] fs = c.getDeclaredFields();//Declared 自己声明的
            if (fs != null && fs.length > 0) {
                for (Field f : fs) {
                    //private
                    String modifier = "";
                    switch (f.getModifiers()) {
                        case 1:
                            modifier = "public";
                            break;
                        case 2:
                            modifier = "private";
                            break;
                        default:
                            System.out.println(modifier + "\t" + f.getName());
                            //算法：位图算法

                    }
                }
                Method[] ms = c.getDeclaredMethods();
                // Method[] ms=c.getMethods();
                if (ms != null && ms.length > 0) {
                    for (Method m : ms) {
                        System.out.println(m.getModifiers() + "\t" + m.getReturnType().toString() + "\t" + m.getName());
                    }
                }
                Constructor[] cs = c.getConstructors();
                if (cs != null && cs.length > 0) {
                    for (Constructor m : cs) {
                        System.out.println(m.getModifiers() + "\t" + m.getName());
                    }
                }
                //利用反射完成实例化操作
                Object o = c.newInstance();  //五参数构造方法
                if (o instanceof Showable) {
                    Showable p = (Showable) o;
                    p.show();
                }
                //利用反射调某个方法  适合j2EE中的规范化方法调用场景：setXXXX  getXXXX
                System.out.println("========");
                if (ms != null && ms.length > 0) {
                    for (Method m : ms) {
                        if (m.getName().startsWith("sh")) {
                            //调用此方法 show()  它有两个参数：第一个是实例，第二个是是参数组
                            m.invoke(o);
                        }
                    }
                }
                Map<String, String> pMap = new HashMap<String, String>();
                pMap.put("name", "张三");
                pMap.put("age", 30 + "");
                Object oo = setValues(pMap, c);
                System.out.println(oo.toString());
            }
        }

    }
    /*
        反射功能模块:将Map中保存的属性值存到cls对应的对象中，注意：已知cls满j2ee的javabean规范（setXXX getXXX）
        * */
    public  static  Object setValues(Map<String,String> map,Class cls)throws Exception{
      Object o=null;  //o:"Person{name='张三'}";
        o=cls.newInstance();
        Method[] ms=cls.getDeclaredMethods();//ms:Method[6]@615 cls:"class com.yc.Person"
        if(ms !=null && ms.length>0){
            for(Method m:ms){ // m:"public void com.yc.Person.setAge(int)" ms:Method[6]@615
                //只有setXXX才激活
                if(m.getName().startsWith("set")){ //setName()->name
                    String mName=m.getName();//mName:"setAge"
                    String fName=mName.substring(3).toLowerCase();//fName:"age" mName:"setAge"
                    String value=map.get(fName);//value:"30" map:size=2 fName:"age"
                    if("Integer".equalsIgnoreCase(m.getParameterTypes()[0].getTypeName())||"Int".equalsIgnoreCase(m.getParameterTypes()[0].getTypeName())){
                    }else{
                        m.invoke(o,value);//调用set方法，设置值
                    }
                }

            }
        }

        return  null;
    }



}
