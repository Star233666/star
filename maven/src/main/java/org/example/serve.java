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
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class serve extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private ServerSocket server;
    private Socket clientSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private static final int SERVER_PORT = 8866;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    serve frame = new serve();
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
    public serve() {
        setTitle("服务器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 695, 510);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 保持原有界面布局
        textField = new JTextField();
        textField.setBounds(84, 57, 407, 32);
        contentPane.add(textField);
        textField.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(60, 124, 447, 202);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        textArea.setEditable(false); // 设置为不可编辑
        scrollPane.setViewportView(textArea);

        JButton btnNewButton = new JButton("发送");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        btnNewButton.setBounds(533, 60, 113, 27);
        contentPane.add(btnNewButton);

        // 添加窗口关闭监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeResources();
            }
        });

        // 在单独线程中启动服务器
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            server = new ServerSocket(SERVER_PORT);
            //appendMessage("服务器已启动，等待客户端连接... 端口: " + SERVER_PORT);

            // 接受客户端连接
            clientSocket = server.accept();
            //appendMessage("客户端已连接: " + clientSocket.getInetAddress());

            // 获取输入输出流
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dis = new DataInputStream(clientSocket.getInputStream());

            // 启动线程监听客户端消息
            new Thread(this::listenForMessages).start();

        } catch (IOException ex) {
            appendMessage("服务器错误: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            while (clientSocket != null && clientSocket.isConnected()) {
                String message = dis.readUTF();
                appendMessage("用户: " + message);
            }
        } catch (IOException ex) {
            if (clientSocket != null && !clientSocket.isClosed()) {
                appendMessage("与用户的连接已断开");
            }
        }
    }

    private void sendMessage() {
        String message = textField.getText().trim();
        if (!message.isEmpty() && dos != null) {
            try {
                dos.writeUTF(message);
                appendMessage("图书管理员: " + message);
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
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (server != null && !server.isClosed()) server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}