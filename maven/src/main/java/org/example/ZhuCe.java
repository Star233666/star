package org.example;

import java.awt.EventQueue;
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

public class ZhuCe extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField; // 用户名
	private JPasswordField passwordField_1; // 密码
	private JPasswordField passwordField_2; // 确认用户名
	private JPasswordField passwordField_3; // 确认密码

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZhuCe frame = new ZhuCe();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("注册界面");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ZhuCe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("账号：");
		lblNewLabel.setBounds(75, 44, 125, 27);
		contentPane.add(lblNewLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(158, 44, 209, 26);
		contentPane.add(passwordField);

		JLabel label = new JLabel("密码：");
		label.setBounds(75, 99, 125, 27);
		contentPane.add(label);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(158, 99, 209, 26);
		contentPane.add(passwordField_1);

		JLabel label_1 = new JLabel("再次输入相同账号：");
		label_1.setBounds(14, 157, 135, 27);
		contentPane.add(label_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(158, 157, 209, 26);
		contentPane.add(passwordField_2);

		JLabel label_2 = new JLabel("再次输入相同密码：");
		label_2.setBounds(14, 223, 135, 27);
		contentPane.add(label_2);

		passwordField_3 = new JPasswordField();
		passwordField_3.setBounds(158, 223, 209, 26);
		contentPane.add(passwordField_3);

		JButton btnNewButton = new JButton("注册");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获取用户输入
				String username1 = new String(passwordField.getPassword());
				String username2 = new String(passwordField_2.getPassword());
				String password1 = new String(passwordField_1.getPassword());
				String password2 = new String(passwordField_3.getPassword());

				// 输入验证
				if (username1.isEmpty() || password1.isEmpty()) {
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
					return;
				}
				if (!username1.equals(username2)) {
					JOptionPane.showMessageDialog(null, "两次输入的用户名不一致！");
					return;
				}
				if (!password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, "两次输入的密码不一致！");
					return;
				}

				SqlSession session = MyBatisUtil.getSession();
				try {
					UserMapper userMapper = session.getMapper(UserMapper.class);

					int count = userMapper.checkUserNameExists(username1);
					if (count > 0) {
						JOptionPane.showMessageDialog(null, "该用户名已被注册！");
						return;
					}

					// 创建用户对象
					User user = new User();
					user.setUserName(username1);
					user.setPassWord(password1);

					// 执行插入
					int rows = userMapper.insertUser(user);
					session.commit(); // 提交事务

					if (rows > 0) {
						JOptionPane.showMessageDialog(null, "注册成功！");
						Denglu frame = new Denglu();
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						ZhuCe.this.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "注册失败！");
					}
				} catch (Exception ex) {
					session.rollback(); // 回滚事务
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "注册异常：" + ex.getMessage());
				} finally {
					session.close();
				}
			}
		});
		btnNewButton.setBounds(75, 288, 113, 27);
		contentPane.add(btnNewButton);

		JButton button = new JButton("返回");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Denglu frame = new Denglu();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				ZhuCe.this.dispose();
			}
		});
		button.setBounds(240, 288, 113, 27);
		contentPane.add(button);
	}
}