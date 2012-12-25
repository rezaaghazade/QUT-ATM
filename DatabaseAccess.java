package model.da;
import java.sql.*;
import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/24/12
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseAccess {
    public PreparedStatement preparedStatement;
    public Connection connection;
    public ResultSet personInfo;
    public ArrayList recordInfoArray;
    public DatabaseAccess()
    {
        try{
            /*Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/home/reza/Desktop/ATMDB.db3");*/
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "1991");
            connection.setAutoCommit(false);
        }
        catch (Exception e)
        {
            System.out.println("Error in Connection "+e.getMessage());
        }
    }
    public ArrayList CheckAcc(String cardNum)
    {
        try
        {
            preparedStatement=connection.prepareStatement("select * from ATMTB where cardnum=?");
            preparedStatement.setString(1,cardNum);
            personInfo=preparedStatement.executeQuery();

            recordInfoArray=new ArrayList();
            recordInfoArray.add(personInfo);
            connection.commit();

        }
        catch (Exception e)
        {
            System.out.println("Error in Statement :"+e.getMessage());
        }
        return recordInfoArray;
    }
    public boolean IncLockCounter(String cardNum,String lockCounter)
    {
        try
        {
            preparedStatement=connection.prepareStatement("update ATMTB set LOCKCOUNTER=? where cardnum=?");
            preparedStatement.setString(1,lockCounter);
            preparedStatement.setString(2,cardNum);
            //personInfo=preparedStatement.executeQuery();
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            connection.commit();

        }
        catch (Exception e)
        {
            System.out.println("Error in Statement :"+e.getMessage());
            return false;
        }
        return true;
    }
    public boolean giveMoney(String cardNum,String amountOfmoney)
    {
        try{
            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=? where cardnum=?");
            preparedStatement.setString(1,amountOfmoney);
            preparedStatement.setString(2,cardNum);
            personInfo=preparedStatement.executeQuery();
            preparedStatement.addBatch();

            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=(CURRENTVALUE+1) where cardnum=1");
            personInfo=preparedStatement.executeQuery();
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            connection.commit();
           }
        catch (Exception e)
        {
            System.out.println("erro in givemoney"+e.getMessage());
        }
        return true;
    }
    public void setReport(String sourceCardNum,String destCardNum,String exchValue,String currentValue,String kind)
    {
        try{
            preparedStatement=connection.prepareStatement("insert into ATMREPORTTB values (?,?,?,?,?,?)");
            preparedStatement.setString(1,sourceCardNum);
            preparedStatement.setString(2,destCardNum);
            preparedStatement.setString(3,exchValue);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            java.util.Date date1 = calendar.getTime();
            preparedStatement.setString(4,String.valueOf(date1));
            preparedStatement.setString(5,kind);
            preparedStatement.setString(6,currentValue);
            personInfo=preparedStatement.executeQuery();
            connection.commit();
        }catch (Exception el)
        {
            System.out.println("error in setReport"+el.getMessage());
        }
    }
    public ResultSet getDestinationAccDetail(String cardNum)
    {
        try
        {
            preparedStatement=connection.prepareStatement("select NAME,FAMILY from ATMTB where cardnum=?");
            preparedStatement.setString(1,cardNum);
            personInfo=preparedStatement.executeQuery();
            connection.commit();
        }
        catch (Exception e)
        {
            System.out.println("Error in Statement :"+e.getMessage());
        }
        return personInfo;
    }
    public boolean exchangeMoney(String sourceCardNum,String destCardNum,String exchangeValue,String newValue)
    {
        try{
            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=? where cardnum=?");
            preparedStatement.setString(1,newValue);
            preparedStatement.setString(2,sourceCardNum);
            //personInfo=preparedStatement.executeQuery();
            preparedStatement.addBatch();

            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=(CURRENTVALUE+?) where cardnum=?");
            preparedStatement.setString(1,exchangeValue);
            preparedStatement.setString(2,destCardNum);
            personInfo=preparedStatement.executeQuery();
            preparedStatement.addBatch();

            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=(CURRENTVALUE+1) where cardnum=1");
            personInfo=preparedStatement.executeQuery();
            setReport(sourceCardNum,destCardNum,exchangeValue,newValue,"1");
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            connection.commit();

        }
        catch (Exception e)
        {
            System.out.println("erro in givemoney"+e.getMessage());
        }
        return true;


    }
    public ResultSet getReport(String cardNum)
    {
        try{
            preparedStatement=connection.prepareStatement("select * from ATMREPORTTB where SOURCECARDNUM=?");
            preparedStatement.setString(1,cardNum);
            personInfo=preparedStatement.executeQuery();
            connection.commit();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return personInfo;
    }
    public void buyCharge(String sourceCardNum,String destCardNum,String newValue,String exchValue,String type)
    {
        try
        {
            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=? where cardnum=?");
            preparedStatement.setString(1,newValue);
            preparedStatement.setString(2,sourceCardNum);
            //preparedStatement.executeQuery();
            preparedStatement.addBatch();

            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=(CURRENTVALUE+?) where cardnum=?");
            preparedStatement.setString(1,exchValue);
            preparedStatement.setString(2,destCardNum);
            //preparedStatement.executeQuery();
            preparedStatement.addBatch();
            preparedStatement=connection.prepareStatement("update ATMTB set CURRENTVALUE=(CURRENTVALUE+1) where cardnum=1");
            //personInfo=preparedStatement.executeQuery();
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            connection.commit();
            setReport(sourceCardNum,destCardNum,exchValue,newValue,type);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws SQLException {
        DatabaseAccess obj=new DatabaseAccess();

        ArrayList r2=obj.CheckAcc("1111");
        ResultSet r= (ResultSet) r2.get(0);


        try{
            while (r.next())
            {
                System.out.println(r.getString("NAME"));
            }

        }   catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }
}
