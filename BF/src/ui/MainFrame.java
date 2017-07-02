package ui;

/**
 * [161250025 伏家兴]
 */
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;

import rmi.RemoteHelper;

public class MainFrame extends JFrame {

	static JTextArea codeArea;// 代码区
	static JTextArea inputArea;// 输入区
	static JTextArea outputArea;// 输出区
	static boolean isLogin = false;// 登陆状态
	boolean fileisLogin = false;// 文件读取状态

	JButton login;// 登陆按钮
	static JMenu fileListMenu;// 文件菜单
	static JMenu versionMenu;// 版本菜单
	JMenu logMenu;// 登入登出菜单
	JMenuBar menuBar;// 创建菜单栏
	static JMenu sign;// 登陆状态显示栏
	MainFrame mainFrame = this;
	OpenFrame openFrame;
	static String userName = "";
	static String fileName = "";
	static String password = "";
	LoginFrame lf;

	String fontStyle = "微软雅黑";
	int fontStyle2 = Font.BOLD;

	ArrayList<String> list = new ArrayList<String>();
	Stack<String> stack = new Stack<String>();
	Stack<String> stackRedo = new Stack<String>();

	public MainFrame() {

		JFrame frame = new JFrame("BF Client");
		frame.setLayout(null);

		menuBar = new JMenuBar();
		stack.push("");
		stackRedo.push("");

		/* 文件菜单 */
		JMenu fileMenu = new JMenu(" File ");
		menuBar.add(fileMenu);
		fileMenu.setFont(new Font(fontStyle, 1, 15));

		// 新建
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		newMenuItem.setFont(new Font(fontStyle, 1, 15));

		// 打开
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		openMenuItem.setFont(new Font(fontStyle, 1, 15));

		// 保存
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		saveMenuItem.setFont(new Font(fontStyle, 1, 15));

		// 删除文件
		JMenuItem deleteFileMenuItem = new JMenuItem("Delete");
		fileMenu.add(deleteFileMenuItem);
		deleteFileMenuItem.setFont(new Font(fontStyle, 1, 15));

		frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		deleteFileMenuItem.addActionListener(new DeleteFileActionListener());

		/* Run菜单 */
		JMenu runMenu = new JMenu("  Run  ");
		menuBar.add(runMenu);
		runMenu.setFont(new Font(fontStyle, 1, 15));

		// 执行编译
		JMenuItem executeMenuItem = new JMenuItem("execute");
		runMenu.add(executeMenuItem);
		executeMenuItem.setFont(new Font(fontStyle, 1, 15));
		executeMenuItem.addActionListener(new CompileActionListener());

		// 重做
		JMenuItem redoMenuItem = new JMenuItem("redo[重做]");
		runMenu.add(redoMenuItem);
		redoMenuItem.setFont(new Font(fontStyle, 1, 15));
		redoMenuItem.addActionListener(new RedoActionListener());

		// 撤销
		JMenuItem undoMenuItem = new JMenuItem("undo[撤销]");
		runMenu.add(undoMenuItem);
		undoMenuItem.setFont(new Font(fontStyle, 1, 15));
		undoMenuItem.addActionListener(new UndoActionListener());

		// 版本菜单
		versionMenu = new JMenu("Version");
		JMenuItem mntmVersion_1 = new JMenuItem("version_1");
		mntmVersion_1.setFont(new Font(fontStyle, 1, 15));
		versionMenu.add(mntmVersion_1);
		JMenuItem mntmVersion_2 = new JMenuItem("version_2");
		mntmVersion_2.setFont(new Font(fontStyle, 1, 15));
		versionMenu.add(mntmVersion_2);
		JMenuItem mntmVersion_3 = new JMenuItem("version_3");
		mntmVersion_3.setFont(new Font(fontStyle, 1, 15));

		versionMenu.add(mntmVersion_1);
		versionMenu.add(mntmVersion_2);
		versionMenu.add(mntmVersion_3);
		menuBar.add(versionMenu);

		versionMenu.setFont(new Font(fontStyle, 1, 15));
		mntmVersion_1.addActionListener(new VersionActionListener());
		mntmVersion_2.addActionListener(new VersionActionListener());
		mntmVersion_3.addActionListener(new VersionActionListener());

		// 登陆菜单
		logMenu = new JMenu("  Log  ");
		logMenu.setFont(new Font(fontStyle, 1, 15));
		// 登陆
		JMenuItem logInMenuItem = new JMenuItem("login");
		// 登出
		JMenuItem logOutMenuItem = new JMenuItem("logout");
		// 删除账号
		JMenuItem deleteMenuItem = new JMenuItem("delete ID");
		// 修改密码
		JMenuItem cpwMenuItem = new JMenuItem("Modify password");

		logInMenuItem.setFont(new Font(fontStyle, 1, 15));
		logOutMenuItem.setFont(new Font(fontStyle, 1, 15));
		deleteMenuItem.setFont(new Font(fontStyle, 1, 15));
		cpwMenuItem.setFont(new Font(fontStyle, 1, 15));

		logMenu.add(logInMenuItem);
		logMenu.add(logOutMenuItem);
		logMenu.add(cpwMenuItem);
		logMenu.add(deleteMenuItem);
		menuBar.add(logMenu);

		// 显示登陆状态
		JPanel panel = new JPanel();
		panel.setSize(30, 10);
		menuBar.add(panel, BorderLayout.EAST);
		// 未登录状态，登陆后显示"ID: username"
		sign = new JMenu("You haven't logged in...");
		sign.setFont(new Font("宋体", Font.ITALIC, 15));
		panel.add(sign);

		logInMenuItem.addActionListener(new LoginActionListener()); // 登陆
		logOutMenuItem.addActionListener(new LogoutActionListener());// 登出
		cpwMenuItem.addActionListener(new ModifyPasswordActionListener());// 修改密码
		deleteMenuItem.addActionListener(new DeleteActionListener());// 删除账号

		// 代码区
		JLabel Code = new JLabel();
		Code.setBounds(10, 0, 50, 20);
		frame.add(Code);

		codeArea = new JTextArea();
		codeArea.setBorder(BorderFactory.createTitledBorder("code"));// 使用标题边框
		codeArea.setBounds(10, 20, 670, 300);
		codeArea.setMargin(new Insets(10, 10, 10, 10));
		codeArea.setFont(new Font("微软雅黑", fontStyle2, 15));
		codeArea.setBackground(Color.LIGHT_GRAY);
		codeArea.setLineWrap(true);
		frame.add(codeArea);

		JScrollPane sp = new JScrollPane(codeArea);
		sp.setBounds(10, 20, 670, 300);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(sp);

		// 输入区
		JLabel input = new JLabel();
		input.setBounds(10, 325, 75, 25);
		frame.add(input);

		inputArea = new JTextArea();
		inputArea.setBorder(BorderFactory.createTitledBorder("input"));
		inputArea.setBounds(10, 350, 325, 175);
		inputArea.setMargin(new Insets(10, 10, 10, 10));
		inputArea.setFont(new Font("微软雅黑", fontStyle2, 15));
		inputArea.setBackground(Color.LIGHT_GRAY);
		inputArea.setLineWrap(true);
		frame.add(inputArea);

		// 输出区
		JLabel output = new JLabel();
		output.setBounds(370, 325, 75, 25);
		frame.add(output);

		outputArea = new JTextArea();
		outputArea.setBorder(BorderFactory.createTitledBorder("output"));
		outputArea.setBounds(355, 350, 320, 175);
		outputArea.setMargin(new Insets(10, 10, 10, 10));
		outputArea.setFont(new Font("微软雅黑", fontStyle2, 15));
		outputArea.setBackground(Color.LIGHT_GRAY);
		outputArea.setLineWrap(true);
		frame.add(outputArea);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600);
		frame.setLocation(300, 100);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * 撤销 建一个栈。用来保存每次run之后的代码，每当undo时， 把栈顶的代码推入另一个用来保存重做的代码的栈中，然后把当前栈顶的代码pop出。
	 * 
	 * @author Administrator
	 *
	 */
	class UndoActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 利用栈来实现undo、redo
			if (!stack.isEmpty()) {
				stackRedo.push(stack.peek());
				codeArea.setText(stack.pop());
			} else {
				JOptionPane.showMessageDialog(null, "Undo ilegally");
			}
		}
	}

	/**
	 * 重做
	 * 
	 * @author Administrator
	 *
	 */
	class RedoActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (!stackRedo.isEmpty()) {
				stack.push(stackRedo.peek());
				codeArea.setText(stackRedo.pop());
			} else {
				JOptionPane.showMessageDialog(null, "Redo ilegally");
			}
		}

	}

	/**
	 * 删除账号
	 * 
	 * @author Administrator
	 *
	 */
	class DeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isLogin) {
				JOptionPane.showMessageDialog(null, "You haven't logged in.");
			} else {
				try {
					RemoteHelper.getInstance().getUserService().deleteUser(userName);
					RemoteHelper.getInstance().getIOService().deleteDir(userName);
					isLogin = false;
					JOptionPane.showMessageDialog(null, "Delete Id successfully!");
					sign.setText("You haven't logged in...");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 修改密码
	 * 
	 * @author Administrator
	 *
	 */
	class ModifyPasswordActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isLogin) {
				JOptionPane.showMessageDialog(null, "You haven't logged in.");
			} else {
				new ModifyPasswordFrame();
				isLogin = false;
				sign.setText("You haven't logged in...");
			}
		}

	}

	/**
	 * 登出操作
	 * 
	 * @author Administrator
	 *
	 */
	class LogoutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isLogin) {
				JOptionPane.showMessageDialog(null, "You haven't logged in.");
			} else {
				try {
					isLogin = false;
					JOptionPane.showMessageDialog(null, "Logout Successfully.");
					sign.setText("You haven't logged in...");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 登陆
	 * 
	 * @author Administrator
	 *
	 */
	class LoginActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isLogin)
				new LoginFrame(mainFrame);
			if (isLogin) {
				JOptionPane.showMessageDialog(null, "You have logged in");
			}
		}
	}

	/**
	 * 编译代码
	 * 
	 * @author Administrator
	 *
	 */
	class CompileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText().trim();
			String param = inputArea.getText().trim();
			if (code.contains("Ook")) {
				code = code + " ";
			}
			String output = "";
			try {
				stack.push(code);
				output = RemoteHelper.getInstance().getExecuteService().execute(code, param);

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			outputArea.setText(output);
		}
	}

	/**
	 * 新建文件
	 * 
	 * @author Administrator
	 *
	 */
	class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (isLogin) {
				codeArea.setText("");
				inputArea.setText("");
				outputArea.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "You haven't logged in");
			}
		}

	}

	/**
	 * 打开文件
	 * 
	 * @author Administrator
	 *
	 */
	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isLogin) {
				new OpenFrame();
			} else {
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
			}
		}
	}

	/**
	 * 打开历史版本文件
	 * 
	 * @author Administrator
	 *
	 */
	class VersionActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			String code = "";
			String End = SaveFrame.End;
			String path = userName + "\\" + fileName + "_version" + "\\" + "version_";

			if (isLogin) {
				if (fileName.contains(".txt")) {
					JOptionPane.showMessageDialog(null, "There isn't version file!");
				} else {
					if (cmd.equals("version_1")) {
						try {
							code = RemoteHelper.getInstance().getIOService().readFileThroughPath(path + 1 + End);
							codeArea.setText(code);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else if (cmd.equals("version_2")) {
						try {
							code = RemoteHelper.getInstance().getIOService().readFileThroughPath(path + 2 + End);
							codeArea.setText(code);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else if (cmd.equals("version_3")) {
						try {
							code = RemoteHelper.getInstance().getIOService().readFileThroughPath(path + 3 + End);
							codeArea.setText(code);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
			}
		}
	}

	/**
	 * 保存文件
	 * 
	 * @author Administrator
	 *
	 */
	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isLogin) {
				new SaveFrame();
			} else
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
		}

	}

	/**
	 * 删除文件
	 * 
	 * @author Administrator
	 *
	 */
	class DeleteFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isLogin) {
				new DeleteFileFrame();
			} else {
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
			}
		}

	}
}
