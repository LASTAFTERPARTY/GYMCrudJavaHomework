import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.*;


public class GYM {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtFee;
    private JTextField txtPhone;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtId;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GYM");
        frame.setContentPane(new GYM().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
public void connect()
{
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/906090fitness","root", "");
        System.out.println("success");

    }
    catch (ClassNotFoundException ex)
    {
ex.printStackTrace();
    }
    catch (SQLException ex)
    {
ex.printStackTrace();
    }






}
void table_load()
{
    try
    {
        pst = con.prepareStatement("select * from member");
        ResultSet rs = pst.executeQuery();
        table1.setModel(DbUtils.resultSetToTableModel(rs));
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }



}











    public GYM() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String name,fee,phone;
             name =txtName.getText();
             fee = txtFee.getText();
             phone = txtPhone.getText();

                try {
                    pst = con.prepareStatement("insert into member(MemberName,Fee,PhoneNumber)values(?,?,?)");
                    pst.setString(1,  name);
                    pst.setString(2, fee);
                    pst.setString(3,  phone);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,   "Added!");
                    //   table_load();
                    txtName.setText("");
                    txtFee.setText("");
                    txtPhone.setText("");
                    txtName.requestFocus();

                }

                catch (SQLException e1) {
                    e1.printStackTrace();
                }






            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    String memberid = txtId.getText();

                    pst = con.prepareStatement("select MemberName,Fee,PhoneNumber from member where İd = ?");
                    pst.setString(1, memberid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {

                        String MemberName = rs.getString(1);
                        String Fee = rs.getString(2);
                        String PhoneNumber= rs.getString(3);

                        txtName.setText(MemberName );
                        txtFee.setText(Fee);
                        txtPhone.setText(PhoneNumber);
                        JOptionPane.showMessageDialog(null,"Member Found!");
                    }
                    else
                    {
                        txtName.setText("");
                        txtFee.setText("");
                        txtPhone.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Member");

                    }


                }

                catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }







        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,fee,phone,memberid;
                name = txtName.getText();
                fee = txtFee.getText();
                phone = txtPhone.getText();
                memberid = txtId.getText();

                try {
                    pst = con.prepareStatement("update member set MemberName = ?,Fee = ?,PhoneNumber = ? where İd = ?");
                    pst.setString(1, name);
                    pst.setString(2, fee);
                    pst.setString(3, phone);
                    pst.setString(4, memberid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Updated!");
                    table_load();
                    txtName.setText("");
                    txtFee  .setText("");
                    txtPhone.setText("");
                    txtId.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }










        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberid;
                memberid = txtId.getText();

                try {
                    pst = con.prepareStatement("delete from member  where İd = ?");

                    pst.setString(1, memberid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted!");
                    table_load();
                    txtName.setText("");
                    txtFee.setText("");
                    txtPhone.setText("");
                    txtId.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }






        });
    }
}
