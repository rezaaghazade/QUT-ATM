package view;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import controller.*;
/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/27/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Intro {
    public JFrame introFrame;
    public JTextField cardNumTextField;
    public JPasswordField passwordTextField;
    public JButton cardNumClearButton;
    public JButton passwordClearButton;
    public JButton enterButton;
    public String cardNumStr="";
    public String passWdStr="";
    public JLabel informationLable;
    public Customer customerObj;
    public Controller ControllerObj;
    public Intro()throws Exception
    {
        introFrame=new JFrame("ATM");
        introFrame.setBounds(450,150,180,50);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
    }
    public void ShowIntroPage()
    {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        cardNumTextField=new JTextField(16);
        cardNumTextField.setToolTipText("Card Number is 16 digits");
        passwordTextField=new JPasswordField(16);
        passwordTextField.setToolTipText("Password is 10 digits");
        cardNumClearButton=new JButton("Clear");
        cardNumClearButton.setToolTipText("Clear Card Number Field");
        passwordClearButton=new JButton("Clear");
        passwordClearButton.setToolTipText("Clear Password Field");
        enterButton=new JButton("Enter");
        enterButton.setToolTipText("Enter Account Page");
        informationLable=new JLabel();

        introFrame.getContentPane().add("p", new JLabel("Card Number :"));
        introFrame.getContentPane().add("center",cardNumTextField);
        introFrame.getContentPane().add("       ",cardNumClearButton);
        introFrame.getContentPane().add("p", new JLabel("Password :     "));
        introFrame.getContentPane().add("center ",passwordTextField);
        introFrame.getContentPane().add("       ",passwordClearButton);
        introFrame.getContentPane().add("p center",enterButton);
        introFrame.getContentPane().add("br center",informationLable);
        JLabel tempLable=new JLabel();
        introFrame.getContentPane().add("p center",tempLable);
        introFrame.pack();
        cardNumTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (cardNumStr.length() < 16) {
                    cardNumStr = cardNumStr + e.getKeyChar();
                } else {
                    cardNumTextField.setText(cardNumStr);
                }
            }
            public void keyPressed(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        passwordTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(passWdStr.length()<10){
                    passWdStr=passWdStr+e.getKeyChar();
                }
                else
                {
                    passwordTextField.setText(passWdStr);
                }
            }
            public void keyPressed(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        cardNumClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardNumStr = "";
                cardNumTextField.setText("");
                informationLable.setText("");
            }
        });
        passwordClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passWdStr="";
                passwordTextField.setText("");
                informationLable.setText("");
            }
        });

        customerObj=new Customer();
        ControllerObj=new Controller();

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///*//******************Enter Button
                if(checkInput(cardNumStr,passWdStr))
                {
                    Object tempObject=ControllerObj.introCheck(cardNumStr,passWdStr);
                    int i = 0;
                    try
                    {
                        i=(Integer)tempObject;
                      /*  if(i==1)
                        {
                            informationLable.setText("Cant Get Query From DB,Try Again");
                        }*/
                        if(i==1)
                        {
                            informationLable.setText("Incorrect Password");
                        }
                        else if(i==2)
                        {
                            informationLable.setText("You Enter 3 wrong Password,Your Card is Locked");
                        }
                        else if(i==0)
                        {
                            informationLable.setText("Your Account Is not In DataBase");
                        }
                    }
                    catch (Exception elx)
                    {
                        customerObj= (Customer) tempObject;
                        //ResultSet tableInfo=(ResultSet)tempObject;
                        try {

                        } catch (Exception ee) {
                            System.out.println("Error in Changing Type Object to Result SEt "+ee.getMessage());
                        }
                            introFrame.dispose();
                            Personal personalPageObj=new Personal(customerObj);
                            personalPageObj.show();
                    }
                }
            }
        });
    }
    public boolean checkInput(String cardNum,String passWd)
    {
        try{
            Double tempNum1=new Double(Double.parseDouble(cardNum));
            Double tempNum2=new Double(Double.parseDouble(passWd));
            return true;
        }
        catch (Exception e)
        {
            informationLable.setText("CardNumber & Password is Not in right Style");
            return false;
        }
    }
    public static void main(String[] args) {
        try{
        Intro introPage=new Intro();
        introPage.ShowIntroPage();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
