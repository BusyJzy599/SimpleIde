package project;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

public class TopTools {
	//定义组件
	private JTabbedPane tab;
	JToolBar tool1;
	JToolBar tool2;
	JToolBar tool3;
	JButton b1=new JButton("新建");
	JButton b2=new JButton("打开");
	JButton b3=new JButton("保存");
	JButton b4=new JButton("退出");
	JButton b5=new JButton("编译");
	JButton b6=new JButton("运行");
	JButton b7=new JButton("资源管理");
	JButton b8=new JButton("关于");

	ChooseFile ch=new ChooseFile();

	
	public TopTools() {
		tab=new JTabbedPane();
		
		tool3=new JToolBar();
		tool3.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		tool2=new JToolBar();
		tool2.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		tool1=new JToolBar();
		tool1.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		
		tab.addTab("文件", tool1);
		tool1.add(b1);
		tool1.add(b2);
		tool1.add(b3);
		tool1.add(b4);
		
		b6.setName("stopping");
		tab.addTab("运行", tool2);
		tool2.add(b5);
		tool2.add(b6);
		
		tab.addTab("设置", tool3);
		tool3.add(b7);
		tool3.add(b8);
	}
	
	public JTabbedPane getTools() {
		return this.tab;
	}
	
	

}
