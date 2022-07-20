package studentinformation;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame {
    // Variable Declarations
    JMenuBar menubar; JMenu menu; JMenuItem exit;
    JLabel heading, fname, lname, gender, dateofbirth, course, rn;
    JTextField firstName, lastName, rollNumber;
    JRadioButton m, f, bca, bcom, bsc;
    JButton submit, retrieve, reset;
    ButtonGroup sex, cour;
    
    JComboBox day, month, year; 
    String D[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                  "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                  "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    String M[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}; 
    String Y[] = {"1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000",
                  "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010"};
    
    MainFrame() {
        menubar = new JMenuBar();
        menu = new JMenu("File");
        exit = new JMenuItem("Exit");
        menu.add(exit);
        menubar.add(menu);
        setJMenuBar(menubar);
        
        // Heading Label
        heading = new JLabel("Enter Student's Information", SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.BLACK));
        heading.setFont(new Font("Calibri", Font.BOLD, 14));
        heading.setBounds(100, 20, 200, 20);
        add(heading);
        
        // Creating First Name Input
        fname = new JLabel("First Name :");
        fname.setBounds(40, 60, 100, 20);
        add(fname);
        firstName = new JTextField();
        firstName.setBounds(160, 60, 150, 20);
        add(firstName);
        
        // Creating Last Name Input
        lname = new JLabel("Last Name :");
        lname.setBounds(40, 100, 100, 20);
        add(lname);
        lastName = new JTextField();
        lastName.setBounds(160, 100, 150, 20);
        add(lastName);
        
        // Radio Buttons for Gender
        gender = new JLabel("Gender : ");
        gender.setBounds(40, 140, 100, 20);
        add(gender);
        m = new JRadioButton("Male");
        m.setBounds(160, 140, 100, 20);
        f = new JRadioButton("Female");
        f.setBounds(260, 140, 100, 20);
        sex = new ButtonGroup();
        sex.add(m);
        sex.add(f);
        add(m);
        add(f);
        
        //ComboBoxes for Date of Birth
        dateofbirth = new JLabel("Date of Birth : ");
        dateofbirth.setBounds(40, 180, 100, 20);
        add(dateofbirth);
        day = new JComboBox(D);
        day.setBounds(160, 180, 50, 20);
        add(day);
        month = new JComboBox(M);
        month.setBounds(220, 180, 50, 20);
        add(month);
        year = new JComboBox(Y);
        year.setBounds(280, 180, 60, 20);
        add(year);
        
        // Courses
        course = new JLabel("Courses : ");
        course.setBounds(40, 220, 100, 20);
        add(course);
        
        bca = new JRadioButton("BCA");
        bca.setBounds(160, 220, 50, 20);
        
        bcom = new JRadioButton("BCom");
        bcom.setBounds(220, 220, 60, 20);
     
        bsc = new JRadioButton("BSc");
        bsc.setBounds(280, 220, 70, 20);
        
        cour = new ButtonGroup();
        cour.add(bca);
        cour.add(bcom);
        cour.add(bsc);
        add(bca);
        add(bcom);
        add(bsc);
        
        // Roll Number
        rn = new JLabel("Roll No : ");
        rn.setBounds(40, 260, 100, 20);
        add(rn);
        rollNumber = new JTextField();
        rollNumber.setBounds(160, 260, 150, 20);
        add(rollNumber);
        
        // Buttons - Submit, Reset and Retrieve
        submit = new JButton("Submit");
        submit.setBounds(20, 300, 100, 20);
        add(submit);
        reset = new JButton("Reset");
        reset.setBounds(140, 300, 100, 20);
        add(reset);
        retrieve = new JButton("Retrieve");
        retrieve.setBounds(260, 300, 100, 20);
        add(retrieve);

        
        // MainFrame Properties
        setTitle("Student Information Manager");
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
                
        //Exit Menu Event Handling
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        // Submit Button Event Handling
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int RollNo = Integer.parseInt(rollNumber.getText());
                String FName = firstName.getText();
                String LName = lastName.getText();
                String Gender = null;
                if(m.isSelected())
                    Gender = m.getText();
                if(f.isSelected())
                    Gender = f.getText();
                String DD = (String) day.getItemAt(day.getSelectedIndex()); 
                String MM = (String) month.getItemAt(month.getSelectedIndex());
                String YY = (String) year.getItemAt(year.getSelectedIndex());
                
                String courses = null;
                if(bca.isSelected())
                    courses = bca.getText();
                else if(bcom.isSelected())
                    courses = bcom.getText();
                else if(bsc.isSelected())
                    courses = bsc.getText();
                
                
                try {
                    
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentinfo",
                                                                 "root","12345");
                    
                    // the mysql insert statement
                    String query = "insert into student values (?, ?, ?, ?, ?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt (1, RollNo);
                    preparedStmt.setString (2, FName);
                    preparedStmt.setString (3, LName);
                    preparedStmt.setString (4, Gender);
                    preparedStmt.setString (5, DD);
                    preparedStmt.setString (6, MM);
                    preparedStmt.setString (7, YY);
                    preparedStmt.setString (8, courses);

                    // execute the preparedstatement
                    preparedStmt.execute();
                    
                    con.close();
                }
                catch(ClassNotFoundException | SQLException ex){
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Submitted Successfully", "Submission Dialog",
                                                                        JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        
        // Reset Button Event Handling
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // Resetting Text Fields
                firstName.setText(null);
                lastName.setText(null);
                rollNumber.setText(null);
                // Resetting Radio Buttons
                m.setSelected(false);
                f.setSelected(false);
                // Resetting Checkbox
                bca.setSelected(false);
                bcom.setSelected(false);
                bsc.setSelected(false);
                // Resetting Combobox
                day.setSelectedIndex(0);
                month.setSelectedIndex(0);
                year.setSelectedIndex(0);
            }
        });
        
        // Retrieve Button Event Handling
        retrieve.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String str = JOptionPane.showInputDialog("Enter Roll Number...!!!");
                int rn = Integer.parseInt(str);
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentinfo",
                                                                 "root", "12345");

                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("select * from student where rollno=" + rn);
                    while(rs.next())
                            JOptionPane.showMessageDialog(null,
                                    "RollNo : " + rs.getInt(1)
                                    + "\nFirst Name : " + rs.getString(2)
                                    + "\nLast Name : " + rs.getString(3)
                                    + "\nGender : " + rs.getString(4)
                                    + "\nDate of Birth : " + rs.getString(5)
                                    + "-" + rs.getString(6) + "-" + rs.getString(7) 
                                    + "\nCourse : " + rs.getString(8), "Data", JOptionPane.PLAIN_MESSAGE);

                    con.close();
                }
                catch(  HeadlessException | ClassNotFoundException | SQLException ex){
                    System.out.println(ex);
                }
            }
        });
    }
    
}
