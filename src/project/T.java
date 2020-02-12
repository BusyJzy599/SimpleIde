package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * 
 * @author 14110
 *
 */

public class T{
	public static void main(String[] args)
	{


		
	
	}
	
}




class Login1 extends JFrame implements ActionListener{

	JPanel panel;
	JLabel label,label2;
	JButton loginButton,exitButton;
	JTextField jTextField;
	JPasswordField passwordField;
	
	public Login1() {
		
		
        
        
		this.setTitle("用户登录界面");
		this.setSize(500,220);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout());//设置为流式布局
		label = new JLabel("用户名");
		label2 = new JLabel("密码");
		loginButton = new JButton("登录");
		loginButton.addActionListener(this);//监听事件
		exitButton = new JButton("退出");
		exitButton.addActionListener(this);//监听事件
		jTextField = new JTextField(16);//设置文本框的长度
		passwordField = new JPasswordField(16);//设置密码框
		
		panel.add(label);//把组件添加到面板panel
		panel.add(jTextField);
		panel.add(label2);
		panel.add(passwordField);
		panel.add(loginButton);
		panel.add(exitButton);
		
		this.add(panel);//实现面板panel
		
		this.setVisible(true);//设置可见
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e){//处理事件
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url="jdbc:mysql://localhost:3306/goods?characterEncoding=utf-8";
        String username="root";
        String password="root";
 
        Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//      3.    获得语句执行平台
//      通过连接对象获取对SQL语句的执行者对象
    
		Statement sta = null;
		try {
			sta = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
      String sql="select * from sort";

//      5.    处理结果 (仅用于查询)
      ResultSet rs = null;
	try {
		rs = ((java.sql.Statement) sta).executeQuery(sql);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      try {
		while(rs.next()){              
			// TODO Auto-generated method stub
			if (e.getSource()==loginButton) {
				
				if (jTextField.getText().contains(rs.getString("name")) && passwordField.getText().contains(rs.getString("password"))) {
				
					JOptionPane.showMessageDialog(null,"登录成功！" ); 
				}else {
					JOptionPane.showMessageDialog(null, "用户名或密码错误！");
				}
				if (e.getSource()==exitButton) {
					System.exit(0);
				}
			}
			  
		  }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	}
}


 
 class SelectJTree
{
	JFrame jf = new JFrame("监听树的选择事件");
	JTree tree;
	// 定义几个初始节点
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
	DefaultMutableTreeNode guangdong = new DefaultMutableTreeNode("广东");
	DefaultMutableTreeNode guangxi = new DefaultMutableTreeNode("广西");
	DefaultMutableTreeNode foshan = new DefaultMutableTreeNode("佛山");
	DefaultMutableTreeNode shantou = new DefaultMutableTreeNode("汕头");
	DefaultMutableTreeNode guilin = new DefaultMutableTreeNode("桂林");
	DefaultMutableTreeNode nanning = new DefaultMutableTreeNode("南宁");
	JTextArea eventTxt = new JTextArea(5 , 20);
	public void init()
	{
		// 通过add()方法建立树节点之间的父子关系
		guangdong.add(foshan);
		guangdong.add(shantou);
		guangxi.add(guilin);
		guangxi.add(nanning);
		root.add(guangdong);
		root.add(guangxi);
		// 以根节点创建树
		tree = new JTree(root);
		// 设置只能选择一个TreePath
		tree.getSelectionModel().setSelectionMode(
			TreeSelectionModel.SINGLE_TREE_SELECTION);
		// 添加监听树节点选择事件的监听器
		// 当JTree中被选择节点发生改变时，将触发该方法
		tree.addTreeSelectionListener(e -> {
			if (e.getOldLeadSelectionPath() != null)
				eventTxt.append("原选中的节点路径："
				+ e.getOldLeadSelectionPath().toString() + "\n");
			eventTxt.append("新选中的节点路径："
				+ e.getNewLeadSelectionPath().toString() + "\n");
		});
		//设置是否显示根节点的“展开/折叠”图标,默认是false
		tree.setShowsRootHandles(true);
		//设置根节点是否可见,默认是true
		tree.setRootVisible(true);
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JScrollPane(tree));
		box.add(new JScrollPane(eventTxt));
		jf.add(box);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
}

/*JTree测试*/
 /**
  * Description:
  * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
  * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
  * <br/>This program is protected by copyright laws.
  * <br/>Program Name:
  * <br/>Date:
  * @author Yeeku.H.Lee kongyeeku@163.com
  * @version 1.0
  */
class EditJTree
 {
 	JFrame jf;
 	JTree tree;
 	// 上面JTree对象对应的model
 	DefaultTreeModel model;
 	// 定义几个初始节点
 	DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
 	DefaultMutableTreeNode guangdong = new DefaultMutableTreeNode("广东");
 	DefaultMutableTreeNode guangxi = new DefaultMutableTreeNode("广西");
 	DefaultMutableTreeNode foshan = new DefaultMutableTreeNode("佛山");
 	DefaultMutableTreeNode shantou = new DefaultMutableTreeNode("汕头");
 	DefaultMutableTreeNode guilin = new DefaultMutableTreeNode("桂林");
 	DefaultMutableTreeNode nanning = new DefaultMutableTreeNode("南宁");
 	// 定义需要被拖动的TreePath
 	TreePath movePath;
 	JButton addSiblingButton = new JButton("添加兄弟节点");
 	JButton addChildButton = new JButton("添加子节点");
 	JButton deleteButton = new JButton("删除节点");
 	JButton editButton = new JButton("编辑当前节点");
 	public void init()
 	{
 		guangdong.add(foshan);
 		guangdong.add(shantou);
 		guangxi.add(guilin);
 		guangxi.add(nanning);
 		root.add(guangdong);
 		root.add(guangxi);
 		jf = new JFrame("可编辑节点的树");
 		tree = new JTree(root);
 		// 获取JTree对应的TreeModel对象
 		model = (DefaultTreeModel)tree.getModel();
 		// 设置JTree可编辑
 		tree.setEditable(true);
 		MouseListener ml = new MouseAdapter()
 		{
 			// 按下鼠标时获得被拖动的节点
 			public void mousePressed(MouseEvent e)
 			{
 				// 如果需要唯一确定某个节点，必须通过TreePath来获取。
 			TreePath tp = tree.getPathForLocation(
 					e.getX() , e.getY());
 				if (tp != null)
 				{
 					movePath = tp;
 				}
 			}
 			// 鼠标松开时获得需要拖到哪个父节点
 			public void mouseReleased(MouseEvent e)
 			{
 				// 根据鼠标松开时的TreePath来获取TreePath
 				TreePath tp = tree.getPathForLocation(
 					e.getX(), e.getY());
 				if (tp != null && movePath != null)
 				{
 					// 阻止向子节点拖动
 					if (movePath.isDescendant(tp) && movePath != tp)
 					{
 						JOptionPane.showMessageDialog(jf,
 							"目标节点是被移动节点的子节点，无法移动！",
 							"非法操作", JOptionPane.ERROR_MESSAGE );
 						return;
 					}
 					// 既不是向子节点移动，而且鼠标按下、松开的不是同一个节点
 					else if (movePath != tp)
 					{
 						// add方法先将该节点从原父节下删除，再添加到新父节点下
 						((DefaultMutableTreeNode)tp.getLastPathComponent())
 							.add((DefaultMutableTreeNode)movePath
 							.getLastPathComponent());
 						movePath = null;
 						tree.updateUI();
 					}
 				}
 			}
 		};
 		// 为JTree添加鼠标监听器
 		tree.addMouseListener(ml);
 		JPanel panel = new JPanel();
 		// 实现添加兄弟节点的监听器
 		addSiblingButton.addActionListener(event -> {
 			// 获取选中节点
 			DefaultMutableTreeNode selectedNode	= (DefaultMutableTreeNode)
 				tree.getLastSelectedPathComponent();
 			// 如果节点为空，直接返回
 			if (selectedNode == null) return;
 			// 获取该选中节点的父节点
 			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
 				selectedNode.getParent();
 			// 如果父节点为空，直接返回
 			if (parent == null) return;
 			// 创建一个新节点
 			DefaultMutableTreeNode newNode = new
 				DefaultMutableTreeNode("新节点");
 			// 获取选中节点的选中索引
 			int selectedIndex = parent.getIndex(selectedNode);
 			// 在选中位置插入新节点
 			model.insertNodeInto(newNode, parent, selectedIndex + 1);
 			// --------下面代码实现显示新节点（自动展开父节点）-------
 			// 获取从根节点到新节点的所有节点
 			TreeNode[] nodes = model.getPathToRoot(newNode);
 			// 使用指定的节点数组来创建TreePath
 			TreePath path = new TreePath(nodes);
 			// 显示指定TreePath
 			tree.scrollPathToVisible(path);
 		});
 		panel.add(addSiblingButton);
 		// 实现添加子节点的监听器
 		addChildButton.addActionListener(event -> {
 			// 获取选中节点
 			DefaultMutableTreeNode selectedNode	= (DefaultMutableTreeNode)
 				tree.getLastSelectedPathComponent();
 			// 如果节点为空，直接返回
 			if (selectedNode == null) return;
 			// 创建一个新节点
 			DefaultMutableTreeNode newNode = new
 				DefaultMutableTreeNode("新节点");
 			// 通过model来添加新节点，则无须通过调用JTree的updateUI方法
 			// model.insertNodeInto(newNode, selectedNode
 			// 	, selectedNode.getChildCount());
 			// 通过节点添加新节点，则需要调用tree的updateUI方法
 			selectedNode.add(newNode);
 			// --------下面代码实现显示新节点（自动展开父节点）-------
 			TreeNode[] nodes = model.getPathToRoot(newNode);
 			TreePath path = new TreePath(nodes);
 			tree.scrollPathToVisible(path);
 			tree.updateUI();
 		});
 		panel.add(addChildButton);
 		// 实现删除节点的监听器
 		deleteButton.addActionListener(event ->	{
 			DefaultMutableTreeNode selectedNode	= (DefaultMutableTreeNode)
 				tree.getLastSelectedPathComponent();
 			if (selectedNode != null && selectedNode.getParent() != null)
 			{
 				// 删除指定节点
 				model.removeNodeFromParent(selectedNode);
 			}
 		});
 		panel.add(deleteButton);
 		// 实现编辑节点的监听器
 		editButton.addActionListener(event -> {
 			TreePath selectedPath = tree.getSelectionPath();
 			if (selectedPath != null)
 			{
 				// 编辑选中节点
 				tree.startEditingAtPath(selectedPath);
 			}
 		});
 		panel.add(editButton);
 		jf.add(new JScrollPane(tree));
 		jf.add(panel , BorderLayout.SOUTH);
 		jf.pack();
 		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		jf.setVisible(true);
 	}
 	
 }




//
class ChangeAllCellRender
{
	JFrame jf = new JFrame("改变所有节点的外观");
	JTree tree;
	// 定义几个初始节点
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
	DefaultMutableTreeNode guangdong = new DefaultMutableTreeNode("广东");
	DefaultMutableTreeNode guangxi = new DefaultMutableTreeNode("广西");
	DefaultMutableTreeNode foshan = new DefaultMutableTreeNode("佛山");
	DefaultMutableTreeNode shantou = new DefaultMutableTreeNode("汕头");
	DefaultMutableTreeNode guilin = new DefaultMutableTreeNode("桂林");
	DefaultMutableTreeNode nanning = new DefaultMutableTreeNode("南宁");
	public void init()
	{
		// 通过add()方法建立树节点之间的父子关系
		guangdong.add(foshan);
		guangdong.add(shantou);
		guangxi.add(guilin);
		guangxi.add(nanning);
		root.add(guangdong);
		root.add(guangxi);
		// 以根节点创建树
		tree = new JTree(root);
		// 创建一个DefaultTreeCellRender对象
		DefaultTreeCellRenderer cellRender = new DefaultTreeCellRenderer();
		// 设置非选定节点的背景色。
		cellRender.setBackgroundNonSelectionColor(new
			Color(220 , 220 , 220));
		// 设置节点在选中状态下的背景颜色。
		cellRender.setBackgroundSelectionColor(new Color(140 , 140, 140));
		// 设置选中状态下节点的边框颜色。
		cellRender.setBorderSelectionColor(Color.BLACK);
		// 设置处于折叠状态下非叶子节点的图标。
		cellRender.setClosedIcon(new ImageIcon("icon/close.gif"));
		// 设置节点文本的字体。
		cellRender.setFont(new Font("SansSerif" , Font.BOLD , 16));
		// 设置叶子节点的图标。
		cellRender.setLeafIcon(new ImageIcon("icon/leaf.png"));
		// 设置处于展开状态下非叶子节点的图标。
		cellRender.setOpenIcon(new ImageIcon("icon/open.gif"));
		// 设置绘制非选中状态下节点文本的颜色。
		cellRender.setTextNonSelectionColor(new Color(255 , 0 , 0));
		// 设置绘制选中状态下节点文本的颜色。
		cellRender.setTextSelectionColor(new Color(0 , 0 , 255));
		tree.setCellRenderer(cellRender);
		// 设置是否显示根节点的“展开/折叠”图标,默认是false
		tree.setShowsRootHandles(true);
		// 设置节点是否可见,默认是true
		tree.setRootVisible(true);
		jf.add(new JScrollPane(tree));
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
}




///


class CustomTreeNode
{
	JFrame jf = new JFrame("定制树的节点");
	JTree tree;
	// 定义几个初始节点
	DefaultMutableTreeNode friends = new DefaultMutableTreeNode("我的好友");
	DefaultMutableTreeNode qingzhao = new DefaultMutableTreeNode("李清照");
	DefaultMutableTreeNode suge = new DefaultMutableTreeNode("苏格拉底");
	DefaultMutableTreeNode libai = new DefaultMutableTreeNode("李白");
	DefaultMutableTreeNode nongyu = new DefaultMutableTreeNode("弄玉");
	DefaultMutableTreeNode hutou = new DefaultMutableTreeNode("虎头");
	public void init()
	{
		// 通过add()方法建立树节点之间的父子关系
		friends.add(qingzhao);
		friends.add(suge);
		friends.add(libai);
		friends.add(nongyu);
		friends.add(hutou);
		// 以根节点创建树
		tree = new JTree(friends);
		// 设置是否显示根节点的“展开/折叠”图标,默认是false
		tree.setShowsRootHandles(true);
		// 设置节点是否可见,默认是true
		tree.setRootVisible(true);
		// 设置使用定制的节点绘制器
		tree.setCellRenderer(new ImageCellRenderer());
		jf.add(new JScrollPane(tree));
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}

}
// 实现自己的节点绘制器
@SuppressWarnings("serial")
class ImageCellRenderer extends JPanel implements TreeCellRenderer
{
	private ImageIcon icon;
	private String name;
	// 定义绘制单元格时的背景色
	private Color background;
	// 定义绘制单元格时的前景色
	private Color foreground;
	public Component getTreeCellRendererComponent(JTree tree
		, Object value , boolean sel , boolean expanded
		, boolean leaf , int row , boolean hasFocus)
	{
		icon = new ImageIcon("icon/" + value + ".gif");
		name = value.toString();
		background = hasFocus ? new Color(140 , 200 ,235)
			: new Color(255 , 255 , 255);
		foreground = hasFocus ? new Color(255 , 255 ,3)
			: new Color(0 , 0 , 0);
		// 返回该JPanel对象作为单元格绘制器
		return this;
	}
	// 重写paintComponent方法，改变JPanel的外观
	public void paintComponent(Graphics g)
	{
		int imageWidth = icon.getImage().getWidth(null);
		int imageHeight = icon.getImage().getHeight(null);
		g.setColor(background);
		g.fillRect(0 , 0 , getWidth() , getHeight());
		g.setColor(foreground);
		// 绘制好友图标
		g.drawImage(icon.getImage() , getWidth() / 2
			- imageWidth / 2 , 10 , null);
		g.setFont(new Font("SansSerif" , Font.BOLD , 18));
		// 绘制好友用户名
		g.drawString(name, getWidth() / 2
			- name.length() * 10 , imageHeight + 30 );
	}
	// 通过该方法来设置该ImageCellRenderer的最佳大小
	public Dimension getPreferredSize()
	{
		return new Dimension(80, 80);
	}
}

