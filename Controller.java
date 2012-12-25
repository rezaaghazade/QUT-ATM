package controller;
import model.bl.BlMan;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/6/12
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Controller {
    public String md5;
    public ResultSet recordInfoResultSet;
    public ArrayList recordInfoArrayList;
    public Object introCheck(String cardNum,String passWd)
    {
        MD5 md5Obj=new MD5();
        md5=md5Obj.md5(passWd);
        Customer customerObj=new Customer();
        Object object=new Object();
        BlMan blMan=new BlMan();
        recordInfoArrayList= blMan.checkAccMan(cardNum);
        if(recordInfoArrayList.size()!=0)
        {
            recordInfoResultSet= (ResultSet) recordInfoArrayList.get(0);
        }
        else
        {
        }
        try{
            recordInfoResultSet.next();
            customerObj.setCardNum(recordInfoResultSet.getString("CARDNUM"));
            customerObj.setAccNum(recordInfoResultSet.getString("ACCNUM"));
            customerObj.setCurrentValue(recordInfoResultSet.getString("CURRENTVALUE"));
            customerObj.setName(recordInfoResultSet.getString("NAME"));
            customerObj.setFamily(recordInfoResultSet.getString("FAMILY"));
            customerObj.setPassWd(recordInfoResultSet.getString("PASSWD"));
            customerObj.setLockCounter(recordInfoResultSet.getString("LOCKCOUNTER"));
            //return customerObj;
        }catch (Exception e)
        {
            return 0;
            //System.out.println(e.getMessage());
        }
        //customerObj= (Customer) object;
        if(!(customerObj.getPassWd().equals(md5)))
        {
            Integer lock=Integer.parseInt(customerObj.getLockCounter());
            lock++;
            customerObj.setLockCounter(""+lock);
            BlMan blMan1Obj=new BlMan();
            blMan1Obj.IncLockCounterMan(cardNum,String.valueOf(lock));
            //DatabaseAccess databaseAccessObj=new DatabaseAccess();
            //Boolean result=databaseAccessObj.IncLockCounter(customerObj.getCardNum(),String.valueOf(lock));
            return 1;
        }
        if((customerObj.getPassWd().equals(md5) && (Integer.parseInt(customerObj.getLockCounter())>=3)))
        {
            return 2;
        }
        return customerObj;
    }
    public void giveMoney(String accNum,Double amountOfValue,Double exchValue)
    {
        BlMan blManObj=new BlMan();
        blManObj.giveMoneyMan(accNum,String.valueOf(amountOfValue));
        blManObj.setReportMan(accNum,accNum,String.valueOf(exchValue),String.valueOf(amountOfValue),"0");
    }
    public Object getDestAccDetail(String cardNum)
    {
        BlMan blManObj=new BlMan();
        recordInfoArrayList=new ArrayList();
        recordInfoArrayList=blManObj.getDestinationAccDetailMan(cardNum);
        recordInfoResultSet= (ResultSet) recordInfoArrayList.get(0);
        try{
            return recordInfoArrayList;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public void exchangeMoney(String sourceCardNum,String destCardNum,String exchangeValue,String currentValue)
    {
        //Double newValue=(Double.parseDouble(currentValue)-Double.parseDouble(exchangeValue)-1);
        BlMan blManObj=new BlMan();
        blManObj.exchangeMoneyMan(sourceCardNum,destCardNum,exchangeValue,currentValue);

    }
    public ArrayList getReport(String cardNum)
    {
        BlMan blManObj=new BlMan();
        return blManObj.getReportMan(cardNum);
    }
    public void buyCharge(String sourceCardNum,String destCardNum,String currentValue,String exchValue,String type)
    {
        BlMan blManObj=new BlMan();
        blManObj.buyChargeMan(sourceCardNum,destCardNum,currentValue,exchValue,type);

    }


  /*  public static void main(String[] args) {
        Controller obj=new Controller();
        obj.getDestAccDetail("4444");

    }*/
}
