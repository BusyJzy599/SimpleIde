package project;

import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import project.TextPane;
/**
 * �±�����
 * @author 14110
 *
 */
public class MainFrame extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4697982052727623928L;
	
	private JFrame f=this;

	//�������н���
	Process now;
	Runtime run=Runtime.getRuntime();
	BufferedReader br;
	BufferedWriter wr;
	
	//�������������
	Inner i=new Inner(this);
	Outer o=new Outer(this);

	//�������
	static JTabbedPane top;   				//����ѡ��
	static JClosableTabbedPane mainText;	//�༭��			
	static ControllArea bottom;				//�ײ�
	static InFiles files_panel;				//����ļ����
	static ChooseFile chooseFile;			//�ļ�ѡ����
	static JPanel left_panel;		
	static JPanel main_panel;
	
	//��ʼ��
	static TopTools toptools=new TopTools();
	
	
	//��ǰ������������
	private String content="";
	//��ǰ·��
	private String path="";
	//��������
	//private CharBuffer enter=CharBuffer.allocate(64);
	private ArrayList<Character>enter=new ArrayList<>();
	private String input="";
	
	//����
	private HashMap<Component, TextPane> textmap = mainText.gettextmap();
	
	
	static {
		//��ʼ��
		bottom=new ControllArea();
		chooseFile=new ChooseFile();
		files_panel=new InFiles();
		mainText=new JClosableTabbedPane();
		left_panel=new JPanel();
		main_panel=new JPanel();
		top=toptools.getTools();
	}
	
	//��ȡ��ǰ�ļ���·��
	public String curPath() {
		this.path=textmap.get(mainText.getPane()).getPath();
		return this.path;
	}
	
	//��ȡ��ǰ����
	public TextPane getNowPane() {
		return textmap.get(mainText.getPane());
	}
	
	//��ȡ����
	synchronized public String getInput() {
		return this.input;
	}
	//��������
	synchronized public void setInput(String n) {
		this.input=n;
	}
	
	public void init() {
		//�����߳�
		i.start();
		o.start();
		//������
		left_panel.setLayout(new BoxLayout(left_panel,BoxLayout.Y_AXIS));
		left_panel.add(files_panel);
		//�����
		main_panel.setLayout(new BoxLayout(main_panel,BoxLayout.Y_AXIS));
		main_panel.add(mainText);
		main_panel.add(bottom);
		mainText.setPreferredSize(new Dimension(500,320));

		//���ò��ֹ�����
		f.setLayout(new BorderLayout());
		//������
		f.add(top,BorderLayout.NORTH);
		f.add(main_panel,BorderLayout.CENTER);
		f.add(left_panel,BorderLayout.WEST);
		
		
		//��Ӽ���������
		addListeners();
		
		//Ĭ������
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds(inset, inset, screenSize.width - inset*2
			, screenSize.height - inset * 2);
		f.setVisible(true);
		
	}

	

	/**
	 * ��Ӽ����¼�
	 */
	public void addListeners() {
		toptools.b1.addActionListener(new ActionListener() {
			//�½�
			@Override
			public void actionPerformed(ActionEvent e) {
				mainText.addNewPane();
			}
			
		});
		toptools.b2.addActionListener(new ActionListener() {
			//��
			@Override
			public void actionPerformed(ActionEvent e) {
				String []infor=chooseFile.openfile(f);
				if(infor[0]!="") {
					if(files_panel.open(infor[1])) {
						mainText.addFilePane(infor);
					}else
						JOptionPane.showMessageDialog(f, "ѡ���ظ�");
				}
			}
			
		});
		toptools.b3.addActionListener(new ActionListener() {
			//����
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainText.getPane()!=null) {
					if(mainText.getSelectedIndex()!=-1&&
							mainText.getSelectedComponent().getName()=="unseted") {
						//��ȡ
						content=getNowPane().getText();
						String []name=chooseFile.newSetfile(f, content);
						if(!name[0].equals("unseted")) {
							mainText.setTitleAt(mainText.getSelectedIndex(), name[0]);
							mainText.getPane().setName(name[0]);
							getNowPane().setName(name[0]);
							getNowPane().setPath(name[1]);
							files_panel.addChild(name[1]);
							mainText.setNewPane(mainText.getNewPane()-1);
						}
					}else {
						content=getNowPane().getText();
						if(chooseFile.setFile(curPath(), content)) {
							JOptionPane.showMessageDialog(f, "����ɹ�");
							getNowPane().setRes(false);
						}else {
							JOptionPane.showMessageDialog(f, "����ʧ��");
						}
					}
				}
			
			}
			
		});
		toptools.b4.addActionListener(new ActionListener() {
			//�˳�
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
			
		toptools.b5.addActionListener(new ActionListener() {
			//����
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getNowPane()==null||getNowPane().getName().equals("unseted")) {
					return;
				}else {
					if(!toptools.b6.getText().equals("�ж�")) {
						String result="";
						String p=getNowPane().getPath();
						try {
							now=run.exec("javac "+p);
							br=new BufferedReader(new InputStreamReader(now.getErrorStream()));
							String read="";
							while((read=br.readLine())!=null) {
								result+=read+"\r\n";
							}
							//System.out.print(result);
							if(result==""){
									JOptionPane.showMessageDialog(f, "����ɹ�");
									getNowPane().setRes(true);
								}
							else {
								JOptionPane.showMessageDialog(f, "����ʧ��");
								bottom.setSelectedIndex(1);
								getNowPane().setRes(false);
							}
						} catch (IOException e1) {
							result=e1.getMessage();
							
						}finally{
							bottom.setErr(result);
						}
					}
				}
				
			}
			
		});
		

		
		toptools.b6.addActionListener(new ActionListener() {
			//����-�ж�
			@Override
			public void actionPerformed(ActionEvent e) {
				bottom.setSelectedIndex(0);
				if(toptools.b6.getText().equals("����")) {
					if(getNowPane()!=null) {
						if(getNowPane().getRes()) {
							bottom.getCon().setText("");
							toptools.b6.setText("�ж�");
						}
						else
							JOptionPane.showMessageDialog(f, "Error:δ�����������");	
					}
				}else {
					now.destroy();
					toptools.b6.setText("����");
					enter.clear();
				}
			}
			
		});
		
	
		
		toptools.b7.addActionListener(new ActionListener() {
			//��Դ����
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(left_panel.isVisible())
					left_panel.setVisible(false);
				else
					left_panel.setVisible(true);
				
			}
			
		});
		toptools.b8.addActionListener(new ActionListener() {
			//����
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(f,"���ܼ��:\n"
			+"1.��ʱ���ܴ��ļ�����Ŀ\n"
			+ "2.��ʱ���ܽ��е���\n"
			+"3.��ʱ���ܶԴ��ļ�λ�ü����ֽ����޸�\n"
			+"4.���ܽ��޵����ļ��ı��뼰����\n"
			+ "���๦�ܾ����ڴ�!");	
			}
			
		});
		//ɾ�����ʱ���¼�
		mainText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(textmap.size()-mainText.getNewPane()<files_panel.getCount()){
					files_panel.delete(mainText.getDel());	
					mainText.setDel("");
				}
			}

		});
		
		// ��Ӽ������ڵ�ѡ���¼��ļ�����
		InFiles.tree.addTreeSelectionListener(e -> {
			if(textmap.size()>=files_panel.getCount()&&
					e.getNewLeadSelectionPath()!=null) {
				files_panel.setSelect(e.getNewLeadSelectionPath().toString());
			    String name="";
				for(int i=0;i<textmap.size();i++) {
					name=mainText.getTitleAt(i);
					if(files_panel.getSelect().indexOf(name)>=0) {
						mainText.setSelectedIndex(i);
					}
				}
			}
		    
		});
		
		

		//������������
		bottom.getCon().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if(now!=null) {
					if(toptools.b6.getText().equals("�ж�")) {
						String I="";
						//��ȡ�����ÿ���ַ���
						if(e.getKeyCode()!=10) {
							if(enter.size()>0&&e.getKeyCode()==8)
								enter.remove(enter.size()-1);
							else
								enter.add(e.getKeyChar());
						}else {
							for(int i=0;i<enter.size();i++) {
								I+=enter.get(i);
							}
							setInput(I);
							enter.clear();//�����������
						}
	
					}
				}
				
			}
		});
		
	
	
	}
	
	
	//-----------------------------------------------------------------
	public static void main(String[] args)
		{
			MainFrame f=new MainFrame();

			f.init();

		}
}


//�������߳�
class Inner extends Thread{
	private MainFrame myf;
	Inner(MainFrame myf){
		super();
		this.setDaemon(true);
		this.myf=myf;
	}

	@Override
	synchronized public void run() {
		while(myf!=null) {
			Thread.currentThread().interrupt();
			if(MainFrame.toptools.b6.getText().equals("�ж�")) {
				if(myf.now!=null) {
					myf.wr=new BufferedWriter(new OutputStreamWriter(myf.now.getOutputStream()));
					if(myf.getInput()!="") {
						try{
							myf.wr.write(myf.getInput());
							myf.wr.write("\r\n"); 
							myf.wr.flush();
							myf.setInput("");
						} catch (IOException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						} 
					}
				}
			}
			
		}
	}
	
}


//������߳�
class Outer extends Thread{
	private MainFrame myf;
	Outer(MainFrame myf){
		super();
		this.setDaemon(true);
		this.myf=myf;
	}

	@Override
	synchronized public void run() {
		while(myf!=null) {
			Thread.currentThread().interrupt();
			if(MainFrame.toptools.b6.getText().equals("�ж�")) {
					String p=myf.getNowPane().getPath();
					try {
						myf.now=myf.run.exec("java "+p);
						myf.br=new BufferedReader(new InputStreamReader(myf.now.getInputStream()));
						String result=null;
						while((result=myf.br.readLine())!=null) {	
							System.out.println(result);
						}
						int res=-1;
						try {
							res=myf.now.exitValue();
						}catch(IllegalThreadStateException e2) {}
						if(res==0) {
							MainFrame.toptools.b6.setText("����");
						}
	
					} catch (IOException e1) {
						e1.printStackTrace();
						
					}
				}
		}
	}
	
}


