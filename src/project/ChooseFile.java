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
    //���ڱ�����ļ�������
    private String content="";
  

    public  ChooseFile(){
    }
    
    //��ȡ�ļ��ַ���
    public String getInfor() {
    	return this.content;
    }
    

    public  String[] openfile(Component p) {
    	String []infor= {"","",""};
    	 //���ļ�ѡ�����Ի���
        int status=jfc.showOpenDialog(p);
        //û��ѡ�򿪰�ť�����ʾ
           if(status==JFileChooser.APPROVE_OPTION){
        	 //��ѡ�е��ļ�����Ϊ�ļ�����
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
                  System.out.println("ϵͳû���ҵ����ļ�");
                  e1.printStackTrace();
              }
               if(file!=null) {
                infor[0]=file.getName();		//��ȡ�ļ���
                infor[1]=file.getAbsolutePath();//�ļ�·��
                infor[2]=content;				//�ļ�����
               }
           }
           content="";
    	 return infor;
    }

    //�����½����ļ��������ļ���
    public String[] newSetfile(Component p,String input) {
    	String[]infor=  {"",""};
    	int re=jfc.showSaveDialog(p);
    	 File f = jfc.getSelectedFile();
    	 if(re==JFileChooser.APPROVE_OPTION) {
	            f=jfc.getSelectedFile();
	            try {
	               FileOutputStream fsp=new FileOutputStream(f);
	               BufferedOutputStream out=new BufferedOutputStream(fsp);
	               //���ı�����ת��Ϊ�ֽڴ浽�ֽ�����
	               byte[] b=(input).getBytes();
	               //������b�е�ȫ������д��out����Ӧ���ļ�
	               out.write(b, 0, b.length);
	               out.close();
	
	           } catch (FileNotFoundException e1) {
	               System.out.println("ϵͳû���ҵ����ļ�"); 
	           } catch (IOException e1) {
	               System.out.println("IOException");
	           }
	            infor[0]=f.getName()==null?"unseted":f.getName();
    	 }else
    		 {infor[0]= "unseted";}
    	 infor[1]=f==null?"unseted":f.getAbsolutePath();
    	 return infor;
    }

    //���浱ǰ�Ѵ��ڵ��ļ�����
    public boolean setFile(String path,String content) {
    	File p=new File(path);
		try (FileOutputStream fsp=new FileOutputStream(p);
				BufferedOutputStream out=new BufferedOutputStream(fsp);
				){
		//���ı�����ת��Ϊ�ֽڴ浽�ֽ�����
        byte[] b=(content).getBytes();
        //������b�е�ȫ������д��out����Ӧ���ļ�
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
