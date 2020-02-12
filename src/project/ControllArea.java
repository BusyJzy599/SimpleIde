package project;


import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ControllArea extends JTabbedPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea err_infor=new JTextArea();
	private Console con_infor = Console.getInstance();

	
	
	public ControllArea() {
		//设置滚动条
		JScrollPane scro2=new JScrollPane(err_infor);
		scro2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add("控制台",con_infor);
		this.add("问题",scro2);
		this.setPreferredSize(new Dimension(300,50));
	}


	
	public JTextPane getCon() {
		return this.con_infor.getPane();
	}
	
	public void setErr(String infor) {
		this.err_infor.setText(infor);
	}

	

}


class Console extends JScrollPane
{

    private static final long serialVersionUID = 1L;

    private JTextPane textPane = new JTextPane();

    private static Console console = null;

    public JTextPane getPane() {
    	return this.textPane;
    }
    
    
    public static synchronized Console getInstance()
    {
        if (console == null)
        {
            console = new Console();
        }
        return console;
    }

    private Console()
    {
        setViewportView(textPane);

        // Set up System.out
        PrintStream mySystemOut = new MyPrintStream(System.out, Color.black);
        System.setOut(mySystemOut);
        
        // Set up System.err
        PrintStream mySystemErr = new MyPrintStream(System.err, Color.red);
        System.setErr(mySystemErr);
        
      
        textPane.setEditable(true);
        setPreferredSize(new Dimension(440, 120));
    }

    //获取行数
    private final int getLineCount()
    {
        return textPane.getDocument().getDefaultRootElement().getElementCount();
    }

    
    //获取行开头
    private int getLineStartOffset(int line)
    {
    	Element lineElement = textPane.getDocument().getDefaultRootElement()
                .getElement(line);
        if (lineElement == null)
            return -1;
        else
            return lineElement.getStartOffset();
    }

    /**
        * 清除超过行数时前面多出行的字符
     */
    public void replaceRange(String str, int start, int end)
    {
        if (end < start)
        {
            throw new IllegalArgumentException("end before start");
        }
        javax.swing.text.Document doc = textPane.getDocument();
        if (doc != null)
        {
            try
            {
                if (doc instanceof AbstractDocument)
                {
                    ((AbstractDocument) doc).replace(start, end - start, str,
                            null);
                }
                else
                {
                    doc.remove(start, end - start);
                    doc.insertString(start, str, null);
                }
            }
            catch (BadLocationException e)
            {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    class MyPrintStream extends PrintStream
    {

        private Color foreground; // 输出时所用字体颜色

        /**
         * 构造自己的 PrintStream
         * 
         * @param out
         *            可传入 System.out 或 System.err, 实际不起作用
         * @param foreground
         *            显示字体颜色
         */
        MyPrintStream(OutputStream out, Color foreground)
        {
            super(out, true); // 使用自动刷新
            this.foreground = foreground;
        }
        
   
        /**
         * 在这里重截,所有的打印方法都要调用最底一层的方法
         */
        public void write(byte[] buf, int off, int len)
        {
            final String message = new String(buf, off, len);

            //SWING非界面线程访问组件的方式 
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        StyledDocument doc = (StyledDocument) textPane.getDocument();

                        // Create a style object and then set the style
                        // attributes
                        Style style = doc.addStyle("StyleName", null);

                        // Foreground color
                        StyleConstants.setForeground(style, foreground);

                        doc.insertString(doc.getLength(), message, style);

                    }
                    catch (BadLocationException e)
                    {
                        // e.printStackTrace();
                    }

                    // Make sure the last line is always visible
                    textPane.setCaretPosition(textPane.getDocument()
                            .getLength());

                    // Keep the text area down to a certain line count
                    int idealLine =150;
                    int maxExcess = 50;

                    int excess = getLineCount() - idealLine;
                    if (excess >= maxExcess)
                    {
                        replaceRange("", 0, getLineStartOffset(excess));
                    }
                }
            });
        }
    }
}