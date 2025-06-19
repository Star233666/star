package org.example;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import MybatisText.MyBatisUtil;
import com.mapper.UserMapper;
import com.pojo.User;
import org.apache.ibatis.session.SqlSession;

public class Denglu extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Denglu frame = new Denglu();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("登录界面");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Denglu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("请输入您的账号：");
		lblNewLabel.setBounds(76, 40, 142, 24);
		contentPane.add(lblNewLabel);

		JLabel label = new JLabel("请输入您的密码：");
		label.setBounds(76, 80, 142, 24);
		contentPane.add(label);

		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获取用户名和密码
				String username = new String(passwordField.getPassword());
				String password = new String(passwordField_1.getPassword());

				// 输入验证
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(null, "账号不能为空!");
					return;
				} else if (password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "密码不能为空!");
					return;
				}

				SqlSession session = MyBatisUtil.getSession();
				try {
					UserMapper userMapper = session.getMapper(UserMapper.class);
					User user = userMapper.selectByUserName(username);

					if (user == null) {
						JOptionPane.showMessageDialog(null, "该用户不存在，请注册!");
						passwordField.setText("");
						passwordField_1.setText("");
						return;
					}

					if (!password.equals(user.getPassWord())) {
						JOptionPane.showMessageDialog(null, "密码错误!");
						passwordField.setText("");
						passwordField_1.setText("");
						return;
					}

					JOptionPane.showMessageDialog(null, "登录成功!");
					Book frame = new Book();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					Denglu.this.dispose();
				} catch (HeadlessException ex) {
					ex.printStackTrace();
				} finally {
					session.close(); // 关闭会话
				}
			}
		});
		btnNewButton.setBounds(26, 135, 113, 27);
		contentPane.add(btnNewButton);

		JButton button = new JButton("注册");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZhuCe frame = new ZhuCe();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				Denglu.this.dispose();
			}
		});
		button.setBounds(153, 135, 113, 27);
		contentPane.add(button);

		passwordField = new JPasswordField();
		passwordField.setBounds(202, 40, 149, 24);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(202, 77, 149, 24);
		contentPane.add(passwordField_1);

		JButton button_1 = new JButton("忘记密码");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WangJiMiMa frame = new WangJiMiMa();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				Denglu.this.dispose();
			}
		});
		button_1.setBounds(282, 135, 113, 27);
		contentPane.add(button_1);

		JButton button_2 = new JButton("退出");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Denglu.this.dispose();
			}
		});
		button_2.setBounds(153, 175, 113, 27);
		contentPane.add(button_2);
	}
}