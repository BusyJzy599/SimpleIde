package project;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

public class TopTools {
	//�������
	private JTabbedPane tab;
	JToolBar tool1;
	JToolBar tool2;
	JToolBar tool3;
	JButton b1=new JButton("�½�");
	JButton b2=new JButton("��");
	JButton b3=new JButton("����");
	JButton b4=new JButton("�˳�");
	JButton b5=new JButton("����");
	JButton b6=new JButton("����");
	JButton b7=new JButton("��Դ����");
	JButton b8=new JButton("����");

	ChooseFile ch=new ChooseFile();

	
	public TopTools() {
		tab=new JTabbedPane();
		
		tool3=new JToolBar();
		tool3.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		tool2=new JToolBar();
		tool2.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		tool1=new JToolBar();
		tool1.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		
		tab.addTab("�ļ�", tool1);
		tool1.add(b1);
		tool1.add(b2);
		tool1.add(b3);
		tool1.add(b4);
		
		b6.setName("stopping");
		tab.addTab("����", tool2);
		tool2.add(b5);
		tool2.add(b6);
		
		tab.addTab("����", tool3);
		tool3.add(b7);
		tool3.add(b8);
	}
	
	public JTabbedPane getTools() {
		return this.tab;
	}
	
	

}
