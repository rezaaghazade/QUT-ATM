package view;
import controller.Controller;
import controller.Customer;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/27/12
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Personal {
    public JFrame personalFrame;
    public JFrame giveMoneyFrame;
    public JFrame exchMoneyFrame;
    public JFrame cellPhoneChargeFrame;
    public JFrame reportFrame;
    public JTable table;
    public JPanel panel;
    public JComboBox operationComboBox;
    public JTextField valueOfMoneyTextFieldForGive;
    public JLabel informationLable;
    public JButton exitButton;
    public JButton exitBtnGiveMoney;
    public JButton exitBtnExchMoney;
    public JButton giveMoney;
    public Customer customerDetail;
    public Controller controllerObj;
    public JLabel currentValue;
    public JTextField valueOfMoneyTextFieldForExch;
    public JTextField destinationCardNum;
    public JButton exchangeBtn;

    public Personal(Customer customerDetail)
    {
        controllerObj=new Controller();
        informationLable=new JLabel();
        personalFrame=new JFrame("Personal Page");

        personalFrame.setBounds(490,150,250,150);
        personalFrame.getContentPane().setLayout(new RiverLayout());
        personalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        personalFrame.setVisible(true);
        this.customerDetail=customerDetail;
    }
    public void show()
    {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        personalFrame.getContentPane().add("p",new JLabel("Name : "));
        personalFrame.getContentPane().add(" ",new JLabel(customerDetail.getName()));
        personalFrame.getContentPane().add("p",new JLabel("Family : "));
        personalFrame.getContentPane().add(" ",new JLabel(customerDetail.getFamily()));
        currentValue=new JLabel(customerDetail.getCurrentValue());
        personalFrame.getContentPane().add("p",new JLabel("Current Value : "));
        personalFrame.getContentPane().add("",currentValue);
        operationComboBox=new JComboBox();
        operationComboBox.addItem("");
        operationComboBox.addItem("Give Money");
        operationComboBox.addItem("Exchange Money");
        operationComboBox.addItem("Cell Phone Charge");
        operationComboBox.addItem("Current Value");
        operationComboBox.addItem("Last Detail");
        personalFrame.getContentPane().add(operationComboBox);
        exitButton=new JButton("  Exit  ");
        personalFrame.getContentPane().add("p p p p end right",exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                personalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //clickO on this & close all pages
                personalFrame.dispose();

            }
        });
        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(operationComboBox.getSelectedIndex()==1)
                {
                    giveMoney();
                }
                if(operationComboBox.getSelectedIndex()==2)
                {
                    exchangeMoney();

                }
                if(operationComboBox.getSelectedIndex()==3)
                {
                    cellPhoneCharge();

                }
                if(operationComboBox.getSelectedIndex()==5)
                {
                    showReports();
                }
            }
        });
        personalFrame.pack();
    }
    public void giveMoney()
    {
        giveMoneyFrame=new JFrame("Give Money");
        giveMoneyFrame.setBounds(500,170,210,180);
        giveMoneyFrame.getContentPane().setLayout(new RiverLayout());
        giveMoneyFrame.setVisible(true);
        giveMoneyFrame.getContentPane().add("center", new JLabel("Enter Amount of Money"));
        valueOfMoneyTextFieldForGive=new JTextField(10);
        giveMoneyFrame.getContentPane().add("p center",valueOfMoneyTextFieldForGive);
        JButton clearBtn=new JButton("Clear");
        giveMoneyFrame.getContentPane().add("tab",clearBtn);
        exitBtnGiveMoney=new JButton("Exit");
        giveMoneyFrame.getContentPane().add("p p ",exitBtnGiveMoney);
        exitBtnGiveMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentValue.setText(customerDetail.getCurrentValue());
                giveMoneyFrame.dispose();    //clickO on this & close all pages
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueOfMoneyTextFieldForGive.setText("");
                informationLable.setText("");
            }
        });
        giveMoney=new JButton("Give");
        giveMoneyFrame.getContentPane().add("p p p center",giveMoney);
        giveMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Double x=Double.parseDouble(valueOfMoneyTextFieldForGive.getText());
                    if(x<Double.parseDouble(customerDetail.getCurrentValue()) && x <400000)
                    {
                        Double newValue=new Double((Double.parseDouble(customerDetail.getCurrentValue())-x)-1);
                        customerDetail.setCurrentValue(String.valueOf(newValue));
                        controllerObj.giveMoney(customerDetail.getCardNum(),newValue,x);
                    }
                    else
                    {
                        informationLable.setText("Out Of Current Value Range");
                        giveMoneyFrame.getContentPane().add("p center",informationLable);
                    }
                }catch (Exception ex)
                {

                }
            }
        });
    }
    public void exchangeMoney()
    {
        final JLabel informationLableForEx=new JLabel("");
        exchMoneyFrame=new JFrame("Exchange Money");
        exchMoneyFrame.setBounds(500,170,280,270);
        exchMoneyFrame.getContentPane().setLayout(new RiverLayout());
        exchMoneyFrame.setVisible(true);
        exchMoneyFrame.getContentPane().add("p",new JLabel("Exchange Money Value"));
        valueOfMoneyTextFieldForExch=new JTextField(16);
        exchMoneyFrame.getContentPane().add("p",valueOfMoneyTextFieldForExch);
        final JButton valueTextFiledclearBtn=new JButton("Clear");
        valueTextFiledclearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueOfMoneyTextFieldForExch.setText("");
                informationLableForEx.setText("");
            }
        });
        exchMoneyFrame.getContentPane().add(" ",valueTextFiledclearBtn);
        JButton destCardNumFieldCleanBtn=new JButton("Clear");
        destCardNumFieldCleanBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destinationCardNum.setText("");
                informationLableForEx.setText("");
            }
        });
        exchMoneyFrame.getContentPane().add("p",new JLabel("Destination CardNum"));
        destinationCardNum=new JTextField(16);
        exchMoneyFrame.getContentPane().add("p",destinationCardNum);
        exchMoneyFrame.getContentPane().add(" ",destCardNumFieldCleanBtn);
        exitBtnExchMoney=new JButton("Exit");
        exitBtnExchMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exchMoneyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //clickO on this & close all pages
                currentValue.setText(customerDetail.getCurrentValue());


            }
        });
        exchangeBtn=new JButton("Exchange");
        exchMoneyFrame.getContentPane().add("p center",exitBtnExchMoney);
        exchMoneyFrame.getContentPane().add("p center",exchangeBtn);
        exchangeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(check(valueOfMoneyTextFieldForExch.getText(),destinationCardNum.getText()))
                {
                    if((Double.parseDouble(valueOfMoneyTextFieldForExch.getText())-1)<Double.parseDouble(customerDetail.getCurrentValue()))
                    {
                        //final Controller controllerObj=new Controller();
                        Object object;
                        object=controllerObj.getDestAccDetail(destinationCardNum.getText());
                        try{
                            exchMoneyFrame.setBounds(500,170,280,430);
                           ArrayList detail=new ArrayList();
                           detail= (ArrayList) object;
                           ResultSet detailResult= (ResultSet) detail.get(0);
                           detailResult.next();
                           exchMoneyFrame.getContentPane().add("p",new Label("Name : "+detailResult.getString("NAME")));
                           exchMoneyFrame.getContentPane().add("p",new Label("Family : "+detailResult.getString("FAMILY")));
                           exchMoneyFrame.getContentPane().add("p",new Label("Exchange Value : "+valueOfMoneyTextFieldForExch.getText()));
                           final JButton commitBtn=new JButton("Commit");
                           final JButton cancelBtn=new JButton("Cancel");
                           exchMoneyFrame.getContentPane().add("p center",commitBtn);
                           exchMoneyFrame.getContentPane().add("p center",cancelBtn);
                            cancelBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    exchMoneyFrame.setBounds(500,170,280,240);
                                    valueOfMoneyTextFieldForExch.setText("");
                                    destinationCardNum.setText("");
                                    commitBtn.setVisible(false);
                                    cancelBtn.setVisible(false);
                                }
                            });
                           commitBtn.addActionListener(new ActionListener() {
                               @Override
                               public void actionPerformed(ActionEvent e) {
                                   System.out.println("You Touch commit");
                                   Double newValue=(Double.parseDouble(customerDetail.getCurrentValue())-Double.parseDouble(valueOfMoneyTextFieldForExch.getText())-1);
                                   customerDetail.setCurrentValue(String.valueOf(newValue));
                                   controllerObj.exchangeMoney(customerDetail.getCardNum(),destinationCardNum.getText(),valueOfMoneyTextFieldForExch.getText(),customerDetail.getCurrentValue());
                               }
                           });
                        }catch (Exception elx)
                        {
                            System.out.println(elx.getMessage());
                        }
                    }
                    else
                    {
                        informationLableForEx.setText("Out Of Current Value");
                        exchMoneyFrame.getContentPane().add("p center",informationLableForEx);

                    }
                }
                else {
                    informationLableForEx.setText("Input Are Not Right Style");
                    exchMoneyFrame.getContentPane().add("p center",informationLableForEx);
                }
            }
        });
        exitBtnExchMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exchMoneyFrame.dispose();
            }
        });
    }
    public boolean check(String value,String cardNum)
    {
        try
        {
            Double x=Double.parseDouble(value);
            Double y=Double.parseDouble(cardNum);
            return true;

        }catch (Exception e)
        {
            return false;
        }
    }
    public void showReports()
    {
        reportFrame=new JFrame("Reports");
        panel=new JPanel();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();
        int y= (int) scrnsize.getHeight();
        int x= (int) scrnsize.getWidth();
        reportFrame.setBounds(0,0,x,y);
        reportFrame.setVisible(true);
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        model.addColumn("#");
        model.addColumn("SourceCardNum");
        model.addColumn("DestCardNum");
        model.addColumn("Exchange Value");
        model.addColumn("Operation Time");
        model.addColumn("Type");
        model.addColumn("Current Value");
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(35);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(175);
        table.getColumnModel().getColumn(4).setPreferredWidth(240);
        table.getColumnModel().getColumn(5).setPreferredWidth(250);
        table.getColumnModel().getColumn(6).setPreferredWidth(160);

        //Controller controllerObj=new Controller();
        ResultSet result= (ResultSet) controllerObj.getReport(customerDetail.getCardNum()).get(0);
        Integer i=1;
        try{
            while (result.next())
            {
                String temp=result.getString(5);
                String kind="";
                if(temp.equals("0"))
                {
                    kind="Give Money";
                }
                if(temp.equals("1"))
                {
                    kind="Exchange Money";
                }

                if(temp.equals("10"))
                {
                    kind="IRANCELL,2000 CHARGE";
                }
                if(temp.equals("11"))
                {
                    kind="IRANCELL,5000 CHARGE";
                }
                if(temp.equals("12"))
                {
                    kind="IRANCELL,10000 CHARGE";
                }


                if(temp.equals("13"))
                {
                    kind="HAMRAHE 1,2000 CHARGE";
                }
                if(temp.equals("14"))
                {
                    kind="HAMRAHE 1,5000 CHARGE";
                }
                if(temp.equals("15"))
                {
                    kind="HAMRAHE 1,10000 CHARGE";
                }

                if(temp.equals("16"))
                {
                    kind="TALYA,2000 CHARGE";
                }
                if(temp.equals("17"))
                {
                    kind="TALYA,5000 CHARGE";
                }
                if(temp.equals("18"))
                {
                    kind="TALYA,10000 CHARGE";
                }
                if(temp.equals("3"))
                {
                    kind="Get Report";
                }
                model.addRow(new Object[]{i,result.getString(1)
                        ,result.getString(2)
                        ,result.getString(3)
                        ,result.getString(4)
                        ,kind,result.getString(6)});
                i++;
               // System.out.println(result.getString(1));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        JScrollPane pane = new JScrollPane(table);
        panel.add(pane);
        reportFrame.add(pane);

        //Print The Last Detail
        /*try {
            boolean complete = table.print();
            if (complete) {
                *//* show a success message  *//*

            } else {
                *//*show a message indicating that printing was cancelled *//*

            }
        } catch (PrinterException pe) {
            *//* Printing failed, report to the user *//*
            *

        }*/
    }
    public void cellPhoneCharge()
    {
        cellPhoneChargeFrame=new JFrame("Cell Phone Charge");
        final JLabel informationLable=new JLabel("");
        final JComboBox cellPhoneChargeKind=new JComboBox();
        final JComboBox cellPhoneChargeValue=new JComboBox();
        cellPhoneChargeFrame.setBounds(500,170,290,245);
        cellPhoneChargeFrame.getContentPane().setLayout(new RiverLayout());
        cellPhoneChargeFrame.setVisible(true);
        cellPhoneChargeFrame.getContentPane().add("p left",new JLabel("Charge Kind : "));
        cellPhoneChargeFrame.getContentPane().add("tab tab tab",new JLabel("Charge Value : "));
        cellPhoneChargeKind.addItem("Irancell");
        cellPhoneChargeKind.addItem("Hamrahe 1");
        cellPhoneChargeKind.addItem("Talya");
        cellPhoneChargeFrame.getContentPane().add("p left",cellPhoneChargeKind);
        cellPhoneChargeValue.addItem("2000");
        cellPhoneChargeValue.addItem("5000");
        cellPhoneChargeValue.addItem("10000");
        cellPhoneChargeFrame.getContentPane().add("tab tab tab ",cellPhoneChargeValue);
        JButton buyBtn=new JButton("Buy it.");
        cellPhoneChargeFrame.getContentPane().add("p center",buyBtn);
        buyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long chargeCode;
                String chargeCodeStr="";
                Double currentVal=Double.parseDouble(customerDetail.getCurrentValue());
                if(cellPhoneChargeKind.getSelectedIndex()==0 && cellPhoneChargeValue.getSelectedIndex()==0 )
                {
                    if(currentVal-2001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    chargeCodeStr=String.valueOf(chargeCode);
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCodeStr));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"2",customerDetail.getCurrentValue(),"2000","10");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-2001));
                    }
                    else {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
                if(cellPhoneChargeKind.getSelectedIndex()==0 && cellPhoneChargeValue.getSelectedIndex()==1)
                {
                    if(currentVal-5001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"2",customerDetail.getCurrentValue(),"5000","11");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-5001));
                }else {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }

                if(cellPhoneChargeKind.getSelectedIndex()==0 && cellPhoneChargeValue.getSelectedIndex()==2)
                {
                    if(currentVal-10001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"2",customerDetail.getCurrentValue(),"10000","12");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-10001));
                } else{
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
                if(cellPhoneChargeKind.getSelectedIndex()==1 && cellPhoneChargeValue.getSelectedIndex()==0)
                {
                    if(currentVal-2001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"3",customerDetail.getCurrentValue(),"2000","13");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-2001));

                }else {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
                    if(cellPhoneChargeKind.getSelectedIndex()==1 && cellPhoneChargeValue.getSelectedIndex()==1)
                {
                    if(currentVal-5001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"3",customerDetail.getCurrentValue(),"5000","14");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-5001));


                }else {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
                if(cellPhoneChargeKind.getSelectedIndex()==1 && cellPhoneChargeValue.getSelectedIndex()==2)
                {
                    if(currentVal-10001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"3",customerDetail.getCurrentValue(),"10000","15");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-10001));

                }   else {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
                    if(cellPhoneChargeKind.getSelectedIndex()==2 && cellPhoneChargeValue.getSelectedIndex()==0)
                {
                    if(currentVal-2001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"4",customerDetail.getCurrentValue(),"2000","16");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-2001));

                }else
                    {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
                    if(cellPhoneChargeKind.getSelectedIndex()==2 && cellPhoneChargeValue.getSelectedIndex()==1)
                {   if(currentVal-5001>0)
                {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"4",customerDetail.getCurrentValue(),"5000","17");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-5001));

                }else {
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                }
                }if(cellPhoneChargeKind.getSelectedIndex()==2 && cellPhoneChargeValue.getSelectedIndex()==2)
                {
                    if(currentVal-10001>0)
                    {
                    Date dateObj=new Date();
                    chargeCode=dateObj.getTime();
                    cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Charge Code is :"+chargeCode));
                    //Controller controllerObj=new Controller();
                    controllerObj.buyCharge(customerDetail.getCardNum(),"4",customerDetail.getCurrentValue(),"5000","18");
                    customerDetail.setCurrentValue(String.valueOf(Double.parseDouble(customerDetail.getCurrentValue())-10001));
                } else {
                        cellPhoneChargeFrame.getContentPane().add("p",new JLabel("Not enough Value"));
                    }
                }
            }
        });
        JButton exitBtn=new JButton("Exit");
        cellPhoneChargeFrame.getContentPane().add("p center ",exitBtn);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellPhoneChargeFrame.dispose();
                currentValue.setText(customerDetail.getCurrentValue());
            }
        });


    }
}
