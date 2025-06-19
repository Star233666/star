package org.example;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import MybatisText.MyBatisUtil;
import com.mapper.BookMapper;
import org.apache.ibatis.session.SqlSession;
//import MybatisText.MyBatisUtil;
public class BookXx extends JFrame {

	private JPanel contentPane;
	JTextField textField_1;
	JTextField textField_2;
	JTextField textField_3;
	JTextField textField_4;
	JTextArea textArea;
	private JButton btnNewButton;
	private JButton button;
	// 替换原来的 JDBC 连接工具为 MyBatis 工具，这里不需要再用 JbdcObdcConnection
	// JbdcObdcConnection db = new JbdcObdcConnection();
	private JButton button_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookXx frame = new BookXx();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("书籍信息界面");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookXx() {
		setDefaultCloseOperation(BookXx.this.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 865, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u4E66\u540D\uFF1A");
		label.setBounds(275, 221, 121, 34);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u4F5C\u8005\u540D\uFF1A");
		label_1.setBounds(275, 268, 121, 34);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("\u51FA\u7248\u65F6\u95F4\uFF1A");
		label_2.setBounds(275, 315, 121, 34);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("\u72B6\u6001\uFF1A");
		label_3.setBounds(275, 362, 121, 34);
		contentPane.add(label_3);

		textField_1 = new JTextField();
		textField_1.setBounds(344, 226, 206, 24);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(344, 273, 206, 24);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(344, 320, 206, 24);
		contentPane.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(344, 367, 206, 24);
		contentPane.add(textField_4);

		textArea = new JTextArea();
		textArea.setBounds(163, 26, 522, 184);
		contentPane.add(textArea);

		btnNewButton = new JButton("\u501F\u9605");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookname = textField_1.getText().trim();
				if (bookname.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入书名！");
					return;
				}

				try (SqlSession session = MyBatisUtil.getSession()) {
					BookMapper mapper = session.getMapper(BookMapper.class);

					// 1. 检查图书状态
					String status = mapper.checkBookStatus(bookname);
					if (status == null) {
						JOptionPane.showMessageDialog(null, "该图书不存在!");
						return;
					}
					if ("已借".equals(status)) {
						JOptionPane.showMessageDialog(null, "该书已借出!");
						return;
					}

					// 2. 获取图书详细信息
					Map<String, Object> bookInfo = mapper.getBookInfo(bookname);
					if (bookInfo == null || bookInfo.isEmpty()) {
						JOptionPane.showMessageDialog(null, "获取图书信息失败!");
						return;
					}
					String authorname = (String) bookInfo.get("AuthorName");
					String publishtime = (String) bookInfo.get("PublishTime");

					// 3. 更新图书状态为“已借”
					int updateCount = mapper.updateBookStatus(bookname, "已借");
					if (updateCount == 0) {
						JOptionPane.showMessageDialog(null, "更新图书状态失败!");
						return;
					}

					// 4. 处理副本记录
					int copyExists = mapper.checkBookCopyExists(bookname);
					if (copyExists == 0) {
						// 插入副本
						int insertCount = mapper.insertBookCopy(bookname, authorname, publishtime, "未还");
						if (insertCount == 0) {
							JOptionPane.showMessageDialog(null, "插入副本失败!");
							return;
						}
					} else {
						// 更新副本状态
						int updateCopyCount = mapper.updateBookCopyStatus(bookname, "未还");
						if (updateCopyCount == 0) {
							JOptionPane.showMessageDialog(null, "更新副本状态失败!");
							return;
						}
					}

					JOptionPane.showMessageDialog(null, "借阅成功!");
					JieYue frame = new JieYue();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					BookXx.this.dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "借阅异常: " + ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(598, 256, 113, 27);
		contentPane.add(btnNewButton);

		button = new JButton("\u5F52\u8FD8");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookname = textField_1.getText();
				if (bookname.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入书名！");
					return;
				}

				try (SqlSession session = MyBatisUtil.getSession()) {
					BookMapper mapper = session.getMapper(BookMapper.class);

					// 1. 检查图书状态
					String status = mapper.checkBookStatus(bookname);
					if (status == null) {
						JOptionPane.showMessageDialog(null, "该图书不存在!");
						return;
					}
					if ("未借".equals(status)) {
						JOptionPane.showMessageDialog(null, "该书还未借出!");
						return;
					}

					// 2. 更新图书状态为“未借”
					int updateCount = mapper.updateBookStatus(bookname, "未借");
					if (updateCount == 0) {
						JOptionPane.showMessageDialog(null, "更新图书状态失败!");
						return;
					}

					// 3. 处理副本记录（更新为“已还”）
					int updateCopyCount = mapper.updateBookCopyStatus(bookname, "已还");
					if (updateCopyCount == 0) {
						JOptionPane.showMessageDialog(null, "更新副本状态失败!");
						return;
					}

					JOptionPane.showMessageDialog(null, "归还成功!");
					JieYue frame = new JieYue();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					BookXx.this.dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "归还异常: " + ex.getMessage());
				}
			}
		});
		button.setBounds(598, 296, 113, 27);
		contentPane.add(button);

		button_2 = new JButton("返回");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookXx.this.dispose();
				Book frame = new Book();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setTitle("图书馆界面");
			}
		});
		button_2.setBounds(598, 336, 113, 27);
		contentPane.add(button_2);
	}
}