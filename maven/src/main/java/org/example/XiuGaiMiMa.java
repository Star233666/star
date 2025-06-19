package org.example;

import MybatisText.MyBatisUtil;
import com.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.awt.BorderLayout;
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

public class XiuGaiMiMa extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XiuGaiMiMa frame = new XiuGaiMiMa();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("修改密码界面");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public XiuGaiMiMa() {
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

		JLabel label = new JLabel("原密码：");
		label.setBounds(69, 89, 72, 18);
		contentPane.add(label);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(135, 86, 165, 21);
		contentPane.add(passwordField_1);

		JLabel label_1 = new JLabel("新密码：");
		label_1.setBounds(69, 136, 72, 18);
		contentPane.add(label_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(135, 133, 165, 21);
		contentPane.add(passwordField_2);

		JButton btnNewButton = new JButton("修改");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = passwordField.getText().trim();
				String oldPassword = passwordField_1.getText().trim();
				String newPassword = passwordField_2.getText().trim();

				// 输入验证
				if (username.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
					JOptionPane.showMessageDialog(null, "账号和密码不能为空!");
					return;
				}

				if (!newPassword.equals(passwordField_2.getText())) {
					JOptionPane.showMessageDialog(null, "两次输入的新密码不一致!");
					return;
				}

				try (SqlSession session = MyBatisUtil.getSession()) {
					UserMapper mapper = session.getMapper(UserMapper.class);
					int affectedRows = mapper.updatePasswordByUsername(username, newPassword);

					if (affectedRows > 0) {
						JOptionPane.showMessageDialog(null, "修改成功!");
						Denglu frame = new Denglu();
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "修改失败! 请检查账号是否存在");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "修改过程中出现异常: " + ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(40, 181, 113, 27);
		contentPane.add(btnNewButton);

		JButton button = new JButton("重置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.setText("");
				passwordField_1.setText("");
				passwordField_2.setText("");
			}
		});
		button.setBounds(167, 181, 113, 27);
		contentPane.add(button);

		JButton btnFanhui = new JButton("返回");
		btnFanhui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book frame = new Book();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnFanhui.setBounds(294, 181, 113, 27);
		contentPane.add(btnFanhui);
	}
}