package ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class RegisterFrame extends JFrame {
	private JTextField textField1;// 账号框
	private JTextField textField2;// 密码框
	private JButton buttonConfirm;// 确认按钮
	JFrame frame;
	ModifyPasswordFrame cps;

	public RegisterFrame() {
		frame = new JFrame("Register");
		frame.setLayout(null);

		JLabel label = new JLabel("User Register");
		frame.add(label);
		label.setBounds(190, 30, 200, 50);
		label.setFont(new Font("微软雅黑", 1, 25));

		// 账号密码区
		JLabel label1 = new JLabel("User ID");
		JLabel label2 = new JLabel("Password");

		textField1 = new JTextField();
		textField2 = new JTextField();

		frame.add(label1);
		label1.setBounds(110, 150, 100, 50);
		label1.setFont(new Font("宋体", 1, 15));
		frame.add(textField1);
		textField1.setFont(new Font("微软雅黑", 1, 15));
		textField1.setBounds(240, 150, 170, 40);

		frame.add(label2);
		label2.setBounds(110, 200, 100, 50);
		label2.setFont(new Font("宋体", 1, 15));
		frame.add(textField2);
		textField2.setFont(new Font("微软雅黑", 1, 15));
		textField2.setBounds(240, 200, 170, 40);

		// 确认按钮
		buttonConfirm = new JButton("confirm");
		frame.add(buttonConfirm);
		buttonConfirm.setBounds(200, 300, 100, 50);
		buttonConfirm.addActionListener(new ButtonActionListener());

		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(720, 320, 500, 500);
		frame.setVisible(true);
	}

	class ButtonActionListener implements ActionListener {
		// 确认
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = textField1.getText();
			String password = textField2.getText();
			boolean b = true;
			boolean c = true;
			try {
				// 生成一个用户目录。
				c = RemoteHelper.getInstance().getIOService().makeDir(username);
				b = RemoteHelper.getInstance().getUserService().register(username, password);

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if (b && c) {
				JOptionPane.showMessageDialog(null, "Sign up successly! Now log in.");
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "This account exists!");

			}
		}
	}
}
