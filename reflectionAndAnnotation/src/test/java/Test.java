import com.yc.annotation.DBConnection;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;


@DBConnection(url="jdbc:mysql://localhost:3306/test",driverClass = "com.mysql.Driver")
public class Test {
    public static void main(String[] args) throws  Exception{
        Class c=Test.class;
       // Annotation[] ans=c.getDeclaredAnnotations();
        DBConnection dbc= (DBConnection) c.getDeclaredAnnotation(DBConnection.class);

        String driverClass=dbc.driverClass();
        String url=dbc.url();
        String user=dbc.user();
        String password=dbc.password();

        Class.forName(driverClass);
        Connection con= DriverManager.getConnection(url,user,password);
        System.out.println(con);
    }
}
