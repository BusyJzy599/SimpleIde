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
 * 仿编译器
 * @author 14110
 *
 */
public class MainFrame extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4697982052727623928L;
	
	private JFrame f=this;

	//定义运行进程
	Process now;
	Runtime run=Runtime.getRuntime();
	BufferedReader br;
	BufferedWriter wr;
	
	//输入流与输出流
	Inner i=new Inner(this);
	Outer o=new Outer(this);

	//定义组件
	static JTabbedPane top;   				//顶部选项
	static JClosableTabbedPane mainText;	//编辑框			
	static ControllArea bottom;				//底部
	static InFiles files_panel;				//左侧文件面板
	static ChooseFile chooseFile;			//文件选择器
	static JPanel left_panel;		
	static JPanel main_panel;
	
	//初始化
	static TopTools toptools=new TopTools();
	
	
	//当前界面输入内容
	private String content="";
	//当前路径
	private String path="";
	//键盘输入
	//private CharBuffer enter=CharBuffer.allocate(64);
	private ArrayList<Character>enter=new ArrayList<>();
	private String input="";
	
	//缓存
	private HashMap<Component, TextPane> textmap = mainText.gettextmap();
	
	
	static {
		//初始化
		bottom=new ControllArea();
		chooseFile=new ChooseFile();
		files_panel=new InFiles();
		mainText=new JClosableTabbedPane();
		left_panel=new JPanel();
		main_panel=new JPanel();
		top=toptools.getTools();
	}
	
	//获取当前文件的路径
	public String curPath() {
		this.path=textmap.get(mainText.getPane()).getPath();
		return this.path;
	}
	
	//获取当前界面
	public TextPane getNowPane() {
		return textmap.get(mainText.getPane());
	}
	
	//获取输入
	synchronized public String getInput() {
		return this.input;
	}
	//设置输入
	synchronized public void setInput(String n) {
		this.input=n;
	}
	
	public void init() {
		//开启线程
		i.start();
		o.start();
		//左侧面板
		left_panel.setLayout(new BoxLayout(left_panel,BoxLayout.Y_AXIS));
		left_panel.add(files_panel);
		//主面板
		main_panel.setLayout(new BoxLayout(main_panel,BoxLayout.Y_AXIS));
		main_panel.add(mainText);
		main_panel.add(bottom);
		mainText.setPreferredSize(new Dimension(500,320));

		//设置布局管理器
		f.setLayout(new BorderLayout());
		//添加组件
		f.add(top,BorderLayout.NORTH);
		f.add(main_panel,BorderLayout.CENTER);
		f.add(left_panel,BorderLayout.WEST);
		
		
		//添加监听器功能
		addListeners();
		
		//默认设置
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds(inset, inset, screenSize.width - inset*2
			, screenSize.height - inset * 2);
		f.setVisible(true);
		
	}

	

	/**
	 * 添加监听事件
	 */
	public void addListeners() {
		toptools.b1.addActionListener(new ActionListener() {
			//新建
			@Override
			public void actionPerformed(ActionEvent e) {
				mainText.addNewPane();
			}
			
		});
		toptools.b2.addActionListener(new ActionListener() {
			//打开
			@Override
			public void actionPerformed(ActionEvent e) {
				String []infor=chooseFile.openfile(f);
				if(infor[0]!="") {
					if(files_panel.open(infor[1])) {
						mainText.addFilePane(infor);
					}else
						JOptionPane.showMessageDialog(f, "选择重复");
				}
			}
			
		});
		toptools.b3.addActionListener(new ActionListener() {
			//保存
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainText.getPane()!=null) {
					if(mainText.getSelectedIndex()!=-1&&
							mainText.getSelectedComponent().getName()=="unseted") {
						//获取
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
							JOptionPane.showMessageDialog(f, "保存成功");
							getNowPane().setRes(false);
						}else {
							JOptionPane.showMessageDialog(f, "保存失败");
						}
					}
				}
			
			}
			
		});
		toptools.b4.addActionListener(new ActionListener() {
			//退出
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
			
		toptools.b5.addActionListener(new ActionListener() {
			//编译
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getNowPane()==null||getNowPane().getName().equals("unseted")) {
					return;
				}else {
					if(!toptools.b6.getText().equals("中断")) {
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
									JOptionPane.showMessageDialog(f, "编译成功");
									getNowPane().setRes(true);
								}
							else {
								JOptionPane.showMessageDialog(f, "编译失败");
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
			//运行-中断
			@Override
			public void actionPerformed(ActionEvent e) {
				bottom.setSelectedIndex(0);
				if(toptools.b6.getText().equals("运行")) {
					if(getNowPane()!=null) {
						if(getNowPane().getRes()) {
							bottom.getCon().setText("");
							toptools.b6.setText("中断");
						}
						else
							JOptionPane.showMessageDialog(f, "Error:未编译或编译错误");	
					}
				}else {
					now.destroy();
					toptools.b6.setText("运行");
					enter.clear();
				}
			}
			
		});
		
	
		
		toptools.b7.addActionListener(new ActionListener() {
			//资源管理
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(left_panel.isVisible())
					left_panel.setVisible(false);
				else
					left_panel.setVisible(true);
				
			}
			
		});
		toptools.b8.addActionListener(new ActionListener() {
			//关于
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(f,"功能简介:\n"
			+"1.暂时不能打开文件夹项目\n"
			+ "2.暂时不能进行调试\n"
			+"3.暂时不能对打开文件位置及名字进行修改\n"
			+"4.功能仅限单个文件的编译及运行\n"
			+ "更多功能敬请期待!");	
			}
			
		});
		//删除面板时的事件
		mainText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(textmap.size()-mainText.getNewPane()<files_panel.getCount()){
					files_panel.delete(mainText.getDel());	
					mainText.setDel("");
				}
			}

		});
		
		// 添加监听树节点选择事件的监听器
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
		
		

		//输入流监听器
		bottom.getCon().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if(now!=null) {
					if(toptools.b6.getText().equals("中断")) {
						String I="";
						//获取输入的每行字符串
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
							enter.clear();//清空输入内容
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


//输入流线程
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
			if(MainFrame.toptools.b6.getText().equals("中断")) {
				if(myf.now!=null) {
					myf.wr=new BufferedWriter(new OutputStreamWriter(myf.now.getOutputStream()));
					if(myf.getInput()!="") {
						try{
							myf.wr.write(myf.getInput());
							myf.wr.write("\r\n"); 
							myf.wr.flush();
							myf.setInput("");
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} 
					}
				}
			}
			
		}
	}
	
}


//输出流线程
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
			if(MainFrame.toptools.b6.getText().equals("中断")) {
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
							MainFrame.toptools.b6.setText("运行");
						}
	
					} catch (IOException e1) {
						e1.printStackTrace();
						
					}
				}
		}
	}
	
}


