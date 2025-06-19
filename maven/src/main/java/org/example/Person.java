package org.example;

import MybatisText.MyBatisUtil;
import com.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Person extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Person frame = new Person();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("个人信息查询");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Person() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("学号：");
		lblNewLabel.setBounds(94, 71, 72, 18);
		contentPane.add(lblNewLabel);

		JLabel label = new JLabel("姓名：");
		label.setBounds(94, 116, 72, 18);
		contentPane.add(label);

		JLabel label_1 = new JLabel("年龄：");
		label_1.setBounds(94, 160, 72, 18);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("性别：");
		label_2.setBounds(94, 204, 72, 18);
		contentPane.add(label_2);

		JButton button = new JButton("查询");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = passwordField.getText().trim();
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入账号！");
					return;
				}

				try (SqlSession session = MyBatisUtil.getSession()) {
					UserMapper mapper = session.getMapper(UserMapper.class);
					Map<String, Object> user = mapper.getUserByUsername(username);

					if (user != null && !user.isEmpty()) {
						textField.setText(user.get("sno") != null ? user.get("sno").toString() : "");
						textField_1.setText(user.get("name") != null ? user.get("name").toString() : "");
						textField_2.setText(user.get("year") != null ? user.get("year").toString() : "");
						textField_3.setText(user.get("sex") != null ? user.get("sex").toString() : "");
					} else {
						JOptionPane.showMessageDialog(null, "未查询到对应的用户信息!");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "查询异常: " + ex.getMessage());
				}
			}
		});
		button.setBounds(68, 257, 113, 27);
		contentPane.add(button);

		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book frame = new Book();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();
			}
		});
		button_1.setBounds(232, 257, 113, 27);
		contentPane.add(button_1);

		JLabel label_3 = new JLabel("账号：");
		label_3.setBounds(94, 28, 72, 18);
		contentPane.add(label_3);

		passwordField = new JPasswordField();
		passwordField.setBounds(160, 25, 152, 21);
		contentPane.add(passwordField);

		textField = new JTextField();
		textField.setBounds(160, 68, 152, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(160, 113, 152, 21);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(160, 157, 152, 21);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(160, 201, 152, 21);
		contentPane.add(textField_3);
	}
}