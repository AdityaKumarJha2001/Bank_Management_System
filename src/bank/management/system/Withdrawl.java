
package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {
    
    JTextField amount;
    JButton withdraw,back;
    String pinnumber;
    
    Withdrawl(String pinnumber){
        
        this.pinnumber = pinnumber;
        
        setLayout(null); 
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 800, 800);
        add(image);
        
        JLabel text = new JLabel("Enter the amount you want to Withdrawl");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD,16));
        text.setBounds(150, 260, 400, 20);
        image.add(text);    
        
        amount = new JTextField();
        amount.setFont(new Font("Raleway", Font.BOLD, 22));
        amount.setBounds(150, 305, 300, 25);
        image.add(amount);
        
        withdraw = new JButton("Withdrawl");
        withdraw.setBounds(318, 430, 140, 28);
        withdraw.addActionListener(this);
        image.add(withdraw);
        
        back = new JButton("Back");
        back.setBounds(318, 460, 140, 28);
        back.addActionListener(this);
        image.add(back);
        
        setSize(800, 800);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == withdraw){
            String number = amount.getText();
            Date date = new Date();
            Conn conn = new Conn();
            if(number.equals("")){
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to Withdrawl");
            }else{
//                try{
//                    Conn conn = new Conn();
//                    String query = "insert into bank values('"+pinnumber+"', '"+date+"', 'Withdrawl', '"+number+"')";
//                    conn.s.executeUpdate(query);
//                    JOptionPane.showMessageDialog(null, "RS " +number+ " Deposited Successfully");
//                    setVisible(false);
//                    new Transactions(pinnumber).setVisible(true);
//                }catch(Exception e){
//                    System.out.println(e);
//                }

                  try{
                      ResultSet rs = conn.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                      int balance = 0;
                      while(rs.next()){
                            if(rs.getString("type").equals("Deposit")){
                                balance += Integer.parseInt(rs.getString("amount"));
                            }else{
                                balance -= Integer.parseInt(rs.getString("amount"));
                            }
                        }
                if(ae.getSource() != back && balance < Integer.parseInt(number)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
               
                String query = "insert into bank values('"+pinnumber+"', '"+date+"', 'Withdrawl', '"+number+"')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "RS " +number+ " Debited Successfully");
                
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
                
            }catch(Exception e){
                System.out.println(e);
            }
            }
            
        }else if(ae.getSource() == back){
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }

   
    public static void main(String args[]) {
        
        new Withdrawl("");
        
    }
}
