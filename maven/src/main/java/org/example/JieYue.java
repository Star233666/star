package org.example;

import MybatisText.MyBatisUtil;
import com.mapper.userbookmapper;
import org.apache.ibatis.session.SqlSession;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JieYue extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JieYue frame = new JieYue();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("借阅信息查询");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JieYue() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 748, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(95, 57, 523, 189);
		contentPane.add(scrollPane);

		// 初始化表格模型
		tableModel = new DefaultTableModel(
				new Object[][]{},
				new String[]{"Id", "BookName", "AuthorName", "PublishTime", "Condition"}
		);

		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int n = table.getSelectedRow();
				if (n >= 0) {
					String bookname = (String) table.getValueAt(n, 1);
					String authorname = (String) table.getValueAt(n, 2);
					String publishtime = (String) table.getValueAt(n, 3);
					String condition = (String) table.getValueAt(n, 4);

					BookXx frame = new BookXx();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);

					// 设置书籍详情
					if ("三国演义".equals(bookname)) {
						String s = "《三国演义》是元末明初小说家罗贯中创作的长篇章回体历史演义小说...";
						frame.textArea.setText(s);
					} else if ("红楼梦".equals(bookname)) {
						String s = "《红楼梦》是清代作家曹雪芹创作的长篇章回体小说...";
						frame.textArea.setText(s);
					} else if ("水浒传".equals(bookname)) {
						String s = "《水浒传》是元末明初施耐庵创作的长篇章回体小说...";
						frame.textArea.setText(s);
					} else if ("西游记".equals(bookname)) {
						String s = "《西游记》是明代作家吴承恩创作的章回体长篇神魔小说...";
						frame.textArea.setText(s);
					}

					frame.textField_1.setText(bookname);
					frame.textField_2.setText(authorname);
					frame.textField_3.setText(publishtime);
					frame.textField_4.setText(condition);
				}
			}
		});

		table.getColumnModel().getColumn(2).setPreferredWidth(112);
		table.getColumnModel().getColumn(3).setPreferredWidth(151);
		table.getColumnModel().getColumn(4).setPreferredWidth(127);
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("查询全部");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 清空表格
				tableModel.setRowCount(0);

				try (SqlSession session = MyBatisUtil.getSession()) {
					userbookmapper mapper = session.getMapper(userbookmapper.class);
					List<Map<String, Object>> bookCopies = mapper.getAllBookCopies();

					for (Map<String, Object> bookCopy : bookCopies) {
						Vector<Object> row = new Vector<>();
						row.add(bookCopy.get("Id"));
						row.add(bookCopy.get("BookName"));
						row.add(bookCopy.get("AuthorName"));
						row.add(bookCopy.get("PublishTime"));
						row.add(bookCopy.get("Condition"));
						tableModel.addRow(row);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "查询异常: " + ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(159, 283, 113, 27);
		contentPane.add(btnNewButton);

		JButton button = new JButton("返回");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book frame = new Book();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();
			}
		});
		button.setBounds(392, 283, 113, 27);
		contentPane.add(button);
	}
}