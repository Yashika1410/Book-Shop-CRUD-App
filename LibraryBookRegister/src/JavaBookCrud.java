import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.*;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaBookCrud {

	private JFrame frame;
	private JTextField txtBook;
	private JTextField txtAuthor;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTextField txtSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaBookCrud window = new JavaBookCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaBookCrud() {
		initialize();
		connect();
		table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TableDatabase","MySQLID","MySQLPassword");
		}
		catch(ClassNotFoundException ex) {
			
		}
		catch(SQLException ex) {
			
		}
	}
	public void getData(String id) {
		try {
                pst = con.prepareStatement("select BookName,AuthorName,edition,price from book where id = ?");
                pst.setString(1, id);
                ResultSet rs = pst.executeQuery();

            if(rs.next()==true)
            {
              
                String name = rs.getString(1);
                String author = rs.getString(2);
                String edition = rs.getString(3);
                String price = rs.getString(4);
                txtBook.setText(name);
				txtAuthor.setText(author);
				txtEdition.setText(edition);
				txtPrice.setText(price);
				txtSearch.setText(id);

                
            }   
            else
            {
            	txtBook.setText("");
				txtAuthor.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
                 
            }
            


        } 
	
	 catch (SQLException ex) {
           
        }
	
	}
	public void table_load()
    {
    	try 
    	{
	    pst = con.prepareStatement("select * from book");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	} 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
	  } 
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 925, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(318, 10, 251, 78);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(40, 95, 391, 298);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(34, 50, 91, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Author Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(34, 108, 106, 25);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Edition");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(34, 162, 91, 25);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Price (Rs.)");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1.setBounds(34, 216, 91, 25);
		panel.add(lblNewLabel_1_1_1_1);
		
		txtBook = new JTextField();
		txtBook.setBounds(157, 49, 209, 32);
		panel.add(txtBook);
		txtBook.setColumns(10);
		
		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(157, 101, 209, 32);
		panel.add(txtAuthor);
		
		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(157, 155, 209, 32);
		panel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(157, 209, 209, 32);
		panel.add(txtPrice);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookname,author,edition,price;
				bookname=txtBook.getText();
				author=txtAuthor.getText();
				edition=txtEdition.getText();
				price=txtPrice.getText();
				try {
					pst = con.prepareStatement("insert into book(BookName,AuthorName,edition,price)values(?,?,?,?)");
					pst.setString(1, bookname);
					pst.setString(2, author);
					pst.setString(3, edition);
					pst.setString(4, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added");
					table_load();
						           
					txtBook.setText("");
					txtAuthor.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBook.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSave.setBounds(40, 426, 123, 59);
		frame.getContentPane().add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExit.setBounds(173, 426, 123, 59);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBook.setText("");
				txtAuthor.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtSearch.setText("");
				txtBook.requestFocus();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClear.setBounds(301, 426, 123, 59);
		frame.getContentPane().add(btnClear);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookname,author,edition,price,bid;
				bid=txtSearch.getText();
				bookname=txtBook.getText();
				author=txtAuthor.getText();
				edition=txtEdition.getText();
				price=txtPrice.getText();
				try {
					pst = con.prepareStatement("update book set BookName=?,AuthorName=?,edition=?,price=? where id=?");
					pst.setString(1, bookname);
					pst.setString(2, author);
					pst.setString(3, edition);
					pst.setString(4, price);
					pst.setString(5, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated");
					table_load();
						           
					txtBook.setText("");
					txtAuthor.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtSearch.setText("");
					txtBook.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdate.setBounds(540, 516, 123, 59);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		         String bid;
					bid  = txtSearch.getText();
					
					 try {
							pst = con.prepareStatement("delete from book where id =?");
					
				            pst.setString(1, bid);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Record Deleted");
				            table_load();

				            txtBook.setText("");
							txtAuthor.setText("");
							txtEdition.setText("");
							txtPrice.setText("");
							txtSearch.setText("");
							txtBook.requestFocus();
						}
		 
			            catch (SQLException e1) {
							
							e1.printStackTrace();
						}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDelete.setBounds(703, 516, 123, 59);
		frame.getContentPane().add(btnDelete);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(47, 516, 438, 111);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				 String id = txtSearch.getText();
			getData(id);
		}
		});
		txtSearch.setColumns(10);
		txtSearch.setBounds(99, 43, 256, 32);
		panel_1.add(txtSearch);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1.setBounds(22, 50, 79, 25);
		panel_1.add(lblNewLabel_1_1_1_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(487, 98, 391, 376);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 int row = table.getSelectedRow();
					   getData(table.getValueAt(row,0).toString());
				   
				
			}
		});
		scrollPane.setViewportView(table);
		
	}
}
