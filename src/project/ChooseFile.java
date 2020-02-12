/**
 * 
 */
package project;

import java.awt.Component;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class ChooseFile {

    /**
	 * 
	 */
    private JFileChooser jfc=new  JFileChooser(new File("."));
    //用于保存打开文件的内容
    private String content="";
  

    public  ChooseFile(){
    }
    
    //获取文件字符串
    public String getInfor() {
    	return this.content;
    }
    

    public  String[] openfile(Component p) {
    	String []infor= {"","",""};
    	 //打开文件选择器对话框
        int status=jfc.showOpenDialog(p);
        //没有选打开按钮结果提示
           if(status==JFileChooser.APPROVE_OPTION){
        	 //被选中的文件保存为文件对象
               File file=jfc.getSelectedFile();
               try {
                   @SuppressWarnings("resource")
					Scanner scanner=new Scanner(file);
                   while(scanner.hasNextLine())
                   {
                       String str=scanner.nextLine();
                       content+=str+"\r\n";
                   }
     
              } catch (FileNotFoundException e1) {
                  System.out.println("系统没有找到此文件");
                  e1.printStackTrace();
              }
               if(file!=null) {
                infor[0]=file.getName();		//获取文件名
                infor[1]=file.getAbsolutePath();//文件路径
                infor[2]=content;				//文件内容
               }
           }
           content="";
    	 return infor;
    }

    //保存新建的文件并返回文件名
    public String[] newSetfile(Component p,String input) {
    	String[]infor=  {"",""};
    	int re=jfc.showSaveDialog(p);
    	 File f = jfc.getSelectedFile();
    	 if(re==JFileChooser.APPROVE_OPTION) {
	            f=jfc.getSelectedFile();
	            try {
	               FileOutputStream fsp=new FileOutputStream(f);
	               BufferedOutputStream out=new BufferedOutputStream(fsp);
	               //将文本内容转换为字节存到字节数组
	               byte[] b=(input).getBytes();
	               //将数组b中的全部内容写到out流对应的文件
	               out.write(b, 0, b.length);
	               out.close();
	
	           } catch (FileNotFoundException e1) {
	               System.out.println("系统没有找到此文件"); 
	           } catch (IOException e1) {
	               System.out.println("IOException");
	           }
	            infor[0]=f.getName()==null?"unseted":f.getName();
    	 }else
    		 {infor[0]= "unseted";}
    	 infor[1]=f==null?"unseted":f.getAbsolutePath();
    	 return infor;
    }

    //保存当前已存在的文件内容
    public boolean setFile(String path,String content) {
    	File p=new File(path);
		try (FileOutputStream fsp=new FileOutputStream(p);
				BufferedOutputStream out=new BufferedOutputStream(fsp);
				){
		//将文本内容转换为字节存到字节数组
        byte[] b=(content).getBytes();
        //将数组b中的全部内容写到out流对应的文件
            out.write(b, 0, b.length);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}catch (IOException e1) {
            System.out.println("IOException");
            return false;
        }
       return true;
    }
    
}
