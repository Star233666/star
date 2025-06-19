package org.example;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class client extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private static final int SERVER_PORT = 8866;
    private static final String SERVER_HOST = "localhost";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    client frame = new client();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public client() {
        setTitle("客户端");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 486);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 保持原有界面布局
        textField = new JTextField();
        textField.setBounds(79, 39, 414, 38);
        contentPane.add(textField);
        textField.setColumns(10);

        textArea = new JTextArea();
        textArea.setEditable(false); // 设置为不可编辑
        textArea.setBounds(51, 104, 462, 216);
        contentPane.add(textArea);

        JButton btnNewButton = new JButton("发送");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        btnNewButton.setBounds(532, 45, 113, 27);
        contentPane.add(btnNewButton);

        // 添加窗口关闭监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeResources();
            }
        });

        // 在单独线程中连接服务器，并添加重试机制
        new Thread(() -> {
            try {
                // 先等待2秒，确保服务器已启动
                Thread.sleep(2000);
                connectToServer();
            } catch (InterruptedException e) {
                appendMessage("连接线程被中断");
                e.printStackTrace();
            }
        }).start();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            appendMessage("图书管理员： 请问我有什么可以帮到您的？");

            // 获取输入输出流
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());

            // 启动线程监听服务器消息
            new Thread(this::listenForMessages).start();

        } catch (IOException ex) {
            appendMessage("连接服务器失败: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            while (socket != null && socket.isConnected()) {
                String message = dis.readUTF();
                appendMessage("图书管理员: " + message);
            }
        } catch (IOException ex) {
            if (socket != null && !socket.isClosed()) {
                appendMessage("与服务器的连接已断开");
            }
        }
    }

    private void sendMessage() {
        String message = textField.getText().trim();
        if (!message.isEmpty() && dos != null) {
            try {
                dos.writeUTF(message);
                appendMessage("用户: " + message);
                textField.setText("");
            } catch (IOException ex) {
                appendMessage("发送消息失败: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void appendMessage(String message) {
        EventQueue.invokeLater(() -> {
            textArea.append(message + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    private void closeResources() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}