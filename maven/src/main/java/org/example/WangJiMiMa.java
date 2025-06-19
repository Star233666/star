package org.example;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import org.apache.ibatis.session.SqlSession;
import com.mapper.UserMapper;
import MybatisText.MyBatisUtil;

public class WangJiMiMa extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WangJiMiMa frame = new WangJiMiMa();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("忘记密码界面");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WangJiMiMa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("账号：");
		lblNewLabel.setBounds(69, 44, 72, 18);
		contentPane.add(lblNewLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(135, 41, 165, 21);
		contentPane.add(passwordField);

		JLabel label = new JLabel("密码：");
		label.setBounds(69, 89, 72, 18);
		contentPane.add(label);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(135, 86, 165, 21);
		contentPane.add(passwordField_1);

		JLabel label_1 = new JLabel("确认密码：");
		label_1.setBounds(53, 136, 88, 18);
		contentPane.add(label_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(135, 133, 165, 21);
		contentPane.add(passwordField_2);

		JButton btnNewButton = new JButton("修改");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = passwordField.getText();
				String password1 = new String(passwordField_1.getPassword());
				String password2 = new String(passwordField_2.getPassword());

				// 基本校验
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(null, "账号不能为空!");
					return;
				}
				if (!password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, "两次输入密码不一致!");
					return;
				}

				// 通过 MyBatis 执行修改密码操作
				try (SqlSession session = MyBatisUtil.getSession()) {
					UserMapper userMapper = session.getMapper(UserMapper.class);
					int affectedRows = userMapper.updatePassword(username, password1);
					if (affectedRows > 0) {
						JOptionPane.showMessageDialog(null, "修改成功!");
						Denglu frame = new Denglu();
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						WangJiMiMa.this.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "修改失败，可能账号不存在!");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "修改过程出现异常!");
				}
			}
		});
		btnNewButton.setBounds(38, 181, 113, 27);
		contentPane.add(btnNewButton);

		JButton button = new JButton("重置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.setText("");
				passwordField_1.setText("");
				passwordField_2.setText("");
			}
		});
		button.setBounds(165, 181, 113, 27);
		contentPane.add(button);

		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Denglu frame = new Denglu();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				WangJiMiMa.this.dispose();
			}
		});
		button_1.setBounds(288, 181, 113, 27);
		contentPane.add(button_1);
	}
}