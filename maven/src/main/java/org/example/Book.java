package org.example;

import MybatisText.MyBatisUtil;
import com.mapper.BookMapper;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Book extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextArea textArea_1;
	private JTextArea textArea_2;
	private JTextArea textArea_3;
	private JTextArea textArea_4;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Book frame = new Book();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setTitle("图书馆界面");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Book() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 671);

		// 菜单、文本框、按钮等初始化（保持原有逻辑，省略重复代码）
		initMenu();
		initTextAreas();
		initButtons();
		initTable();
	}

	// ********** 初始化菜单（提取重复代码） **********
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("我的");
		menuBar.add(mnNewMenu);

		// 个人信息
		JMenuItem mntmInfo = new JMenuItem("个人信息");
		mntmInfo.addActionListener(e -> {
			Person frame = new Person();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			dispose();
		});
		mnNewMenu.add(mntmInfo);

		// 修改密码
		JMenuItem mntmPwd = new JMenuItem("修改密码");
		mntmPwd.addActionListener(e -> {
			XiuGaiMiMa frame = new XiuGaiMiMa();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			dispose();
		});
		mnNewMenu.add(mntmPwd);

		// 个人借阅信息
		JMenuItem mntmBorrow = new JMenuItem("个人借阅信息");
		mntmBorrow.addActionListener(e -> {
			JieYue frame = new JieYue();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			dispose();
		});
		mnNewMenu.add(mntmBorrow);

		JMenu mnService = new JMenu("服务");
		menuBar.add(mnService);

		JMenu mnContact = new JMenu("联系我们");
		mnService.add(mnContact);

		JMenuItem mntmPhone = new JMenuItem("联系电话：88888888");
		mntmPhone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int screenWidth = screenSize.width;
				int screenHeight = screenSize.height;

				client frame1 = new client();
				int clientWidth = frame1.getWidth();
				int clientHeight = frame1.getHeight();
				frame1.setBounds(50, (screenHeight - clientHeight) / 2, clientWidth, clientHeight);
				frame1.setVisible(true);

				serve frame = new serve();
				int serveWidth = frame.getWidth();
				int serveHeight = frame.getHeight();
				frame.setBounds(screenWidth - serveWidth - 50, (screenHeight - serveHeight) / 2, serveWidth, serveHeight);
				frame.setVisible(true);
				dispose();
			}
		});
		mnContact.add(mntmPhone);
	}

	// ********** 初始化文本输入框 **********
	private void initTextAreas() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea_1 = new JTextArea();
		textArea_1.setBounds(78, 99, 136, 24);
		contentPane.add(textArea_1);

		textArea_2 = new JTextArea();
		textArea_2.setBounds(78, 151, 134, 24);
		contentPane.add(textArea_2);

		textArea_3 = new JTextArea();
		textArea_3.setBounds(294, 99, 185, 24);
		contentPane.add(textArea_3);

		textArea_4 = new JTextArea();
		textArea_4.setBounds(294, 155, 183, 24);
		contentPane.add(textArea_4);

		// 标签
		JLabel label = new JLabel("书名：");
		label.setBounds(12, 95, 136, 31);
		contentPane.add(label);

		JLabel label_1 = new JLabel("出版时间：");
		label_1.setBounds(12, 147, 136, 31);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("作者名：");
		label_2.setBounds(228, 95, 150, 31);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("状态：");
		label_3.setBounds(226, 151, 113, 31);
		contentPane.add(label_3);
	}

	// ********** 初始化按钮 **********
	private void initButtons() {
		// 更新书库按钮
		JButton button_1 = new JButton("更新书库");
		button_1.addActionListener(e -> {
			String bookname = textArea_1.getText();
			String authorname = textArea_3.getText();
			String publishtime = textArea_2.getText();
			String condition = textArea_4.getText();

			if (bookname.isEmpty() || authorname.isEmpty() || publishtime.isEmpty() || condition.isEmpty()) {
				JOptionPane.showMessageDialog(null, "图书名或作者名或出版时间或状态不能为空!");
				return;
			}

			try (SqlSession session = MyBatisUtil.getSession()) {
				BookMapper bookMapper = session.getMapper(BookMapper.class);
				int affectedRows = bookMapper.insertBook(bookname, authorname, publishtime, condition);
				if (affectedRows > 0) {
					JOptionPane.showMessageDialog(null, "更新书库成功！");
					queryAllBooks(); // 刷新表格
				} else {
					JOptionPane.showMessageDialog(null, "更新书库失败！");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "更新异常: " + ex.getMessage());
			}

			// 清空输入框
			textArea_1.setText("");
			textArea_2.setText("");
			textArea_3.setText("");
			textArea_4.setText("");
		});
		button_1.setBounds(491, 130, 113, 27);
		contentPane.add(button_1);

		// 查询全部按钮
		JButton button_2 = new JButton("查询全部");
		button_2.addActionListener(e -> queryAllBooks());
		button_2.setBounds(491, 190, 113, 27);
		contentPane.add(button_2);
	}

	// ********** 初始化表格（关键修改：强制列类型） **********
	private void initTable() {
		// 表格模型（显式指定列类型）
		tableModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// 第 0 列（ID）强制为 Integer 类型（根据数据库实际类型调整）
				if (columnIndex == 0) return Integer.class;
				return super.getColumnClass(columnIndex);
			}
		};
		// 设置列名
		tableModel.setColumnIdentifiers(new String[]{"ID", "BookName", "AuthorName", "PublishTime", "Condition"});

		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int n = table.getSelectedRow();
				if (n < 0) return;

				String bookname = (String) table.getValueAt(n, 1);
				String authorname = (String) table.getValueAt(n, 2);
				String publishtime = (String) table.getValueAt(n, 3);
				String condition = (String) table.getValueAt(n, 4);

				BookXx frame = new BookXx();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);

				// 设置书籍详情（简化，实际可优化）
				if ("三国演义".equals(bookname)) {
					frame.textArea.setText("《三国演义》是元末明初小说家罗贯中...");
				} else if ("红楼梦".equals(bookname)) {
					frame.textArea.setText("《红楼梦》是清代作家曹雪芹...");
				} else if ("水浒传".equals(bookname)) {
					frame.textArea.setText("《水浒传》是元末明初施耐庵...");
				} else if ("西游记".equals(bookname)) {
					frame.textArea.setText("《西游记》是明代作家吴承恩...");
				}

				frame.textField_1.setText(bookname);
				frame.textField_2.setText(authorname);
				frame.textField_3.setText(publishtime);
				frame.textField_4.setText(condition);
			}
		});

		// 设置列宽
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(111);
		table.getColumnModel().getColumn(4).setPreferredWidth(119);

		// 强制设置 ID 列为数字类型渲染（彻底解决显示问题）
		TableColumn idColumn = table.getColumnModel().getColumn(0);
		idColumn.setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				// 确保 ID 显示为字符串（兼容数字类型）
				setText(value != null ? value.toString() : "");
				return this;
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(43, 230, 591, 289);
		contentPane.add(scrollPane);

		JLabel lblWelcome = new JLabel("欢迎来到我们的图书馆！！！");
		lblWelcome.setBounds(107, 13, 222, 69);
		contentPane.add(lblWelcome);
	}

	// ********** 查询全部图书（暴力重置模型） **********
	private void queryAllBooks() {
		System.out.println("开始查询所有图书...");
		tableModel.setRowCount(0); // 清空表格

		try (SqlSession session = MyBatisUtil.getSession()) {
			BookMapper bookMapper = session.getMapper(BookMapper.class);
			List<Map<String, Object>> books = bookMapper.selectAllBooks();
			System.out.println("查询到 " + books.size() + " 条记录");

			for (Map<String, Object> book : books) {
				Vector<Object> row = new Vector<>();
				// 暴力获取 ID：同时尝试 "ID" 和 "id"（解决大小写问题）
				Object idValue = book.get("ID") != null ? book.get("ID") : book.get("id");
				System.out.println("ID 值：" + idValue);

				row.add(idValue);
				row.add(book.get("BookName"));
				row.add(book.get("AuthorName"));
				row.add(book.get("PublishTime"));
				row.add(book.get("Condition"));

				tableModel.addRow(row);
			}

			// 重置列模型（终极保险）
			table.setModel(tableModel);
			System.out.println("表格刷新完成");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "查询失败: " + ex.getMessage());
		}
	}
}