package model.bl;
import model.da.DatabaseAccess;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/24/12
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlMan {
    ArrayList recordInfoArrayList;
    public ArrayList checkAccMan(String cardNum)
    {
       //Customer customerObj=new Customer();
       try{
           recordInfoArrayList=new ArrayList();
           DatabaseAccess databaseAccessObj=new DatabaseAccess();
           recordInfoArrayList=databaseAccessObj.CheckAcc(cardNum);

       }catch (Exception es)
       {
           System.out.println(es.getMessage());
       }
       return recordInfoArrayList;
   }
    public void IncLockCounterMan(String cardNum,String lockCount)
    {
        DatabaseAccess databaseAccessObj=new DatabaseAccess();
        databaseAccessObj.IncLockCounter(cardNum,lockCount);

    }
    public boolean giveMoneyMan(String accNum,String amountValue)
    {
        try{
            DatabaseAccess databaseAccessObj=new DatabaseAccess();
            databaseAccessObj.giveMoney(accNum,amountValue);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public void setReportMan(String sourceCardNum,String destCardNum,String exchValue,String currentValue,String kind)
    {
        DatabaseAccess databaseAccessObj=new DatabaseAccess();
        databaseAccessObj.setReport(sourceCardNum,destCardNum,exchValue,currentValue,kind);

    }
    public ArrayList getDestinationAccDetailMan(String cardNum)
    {
        DatabaseAccess databaseAccessObj=new DatabaseAccess();
        ResultSet result;
        result=databaseAccessObj.getDestinationAccDetail(cardNum);
        recordInfoArrayList=new ArrayList();
        recordInfoArrayList.add(result);
        return  recordInfoArrayList;
    }
    public void exchangeMoneyMan(String sourceCardNum,String destCardNum,String exchangeValue,String newValue)
    {
      DatabaseAccess databaseAccessObj=new DatabaseAccess();
      databaseAccessObj.exchangeMoney(sourceCardNum,destCardNum,exchangeValue,newValue);
    }
    public ArrayList getReportMan(String cardNum)
    {
        DatabaseAccess databaseAccessObj=new DatabaseAccess();
        ResultSet result;
        result=databaseAccessObj.getReport(cardNum);
        recordInfoArrayList=new ArrayList();
        recordInfoArrayList.add(result);
        return  recordInfoArrayList;
    }
    public void buyChargeMan(String sourceCardNum,String destCardNum,String currentValue,String exchValue,String type)
    {
        DatabaseAccess databaseAccessObj=new DatabaseAccess();
        Double newVal=Double.parseDouble(currentValue)-Double.parseDouble(exchValue)-1;
        databaseAccessObj.buyCharge(sourceCardNum,destCardNum,String.valueOf(newVal),exchValue,type);

    }
  /*  public static void main(String[] args) {
        BlMan obj=new BlMan();

        Object o=obj.getDestinationAccDetailMan("3333");
    }*/
    /*try{
        recordInfoResultSet.next();
        customerObj.setCardNum(recordInfoResultSet.getString("CARDNUM"));
        customerObj.setAccNum(recordInfoResultSet.getString("ACCNUM"));
        customerObj.setCurrentValue(recordInfoResultSet.getString("CURRENTVALUE"));
        customerObj.setName(recordInfoResultSet.getString("NAME"));
        customerObj.setFamily(recordInfoResultSet.getString("FAMILY"));
        customerObj.setPassWd(recordInfoResultSet.getString("PASSWD"));
        customerObj.setLockCounter(recordInfoResultSet.getString("LOCKCOUNTER"));
        return customerObj;
    }catch (Exception e)
    {
        System.out.println(e.getMessage());
    }
    */





}
