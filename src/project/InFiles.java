package project;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * 用于导入文件
 * @author 14110
 *
 */
public class InFiles extends JPanel{

		private static final long serialVersionUID = -1877527354792619586L;
	
		
		static JTree tree;
		static DefaultTreeModel newModel;
		//根节点
		static DefaultMutableTreeNode root=new DefaultMutableTreeNode("Default");
		static DefaultMutableTreeNode temp;
		
		private String selectName="";
		//需要遍历的目录
		//static String path="D:\\MySoftware\\Java";
		
		public InFiles() {
			JLabel top=new JLabel("   <----------资源管理器---------->   ");
			top.setHorizontalTextPosition(JLabel.CENTER);
			newModel=new DefaultTreeModel(root);
			tree=new JTree(newModel);
			// 设置只能选择一个TreePath
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			
			this.setLayout(new BorderLayout());
			this.add(top,BorderLayout.NORTH);
			this.add(new JScrollPane(tree),BorderLayout.CENTER);
			//this.setVisible(true);
		}
		
		//获取选择文件名
		public String getSelect() {
			return this.selectName;
		}
		//设置选择文件名
		public void setSelect(String name) {
			this.selectName=name;
		}
		//获取子节点数
		public int getCount() {
			return root.getChildCount();
		}
		//添加子节点
		public void addChild(String path) {
			DefaultMutableTreeNode nc = new DefaultMutableTreeNode(new File(path).getName());
			newModel.insertNodeInto(nc, root, root.getChildCount());
			//自动展开父节点
			TreeNode[] nodes =  newModel.getPathToRoot(nc);
 			TreePath p= new TreePath(nodes);
 			tree.scrollPathToVisible(p);
 			tree.updateUI();
			//return nc;
		}
		//删除子节点
		public boolean delete(String n) {
			for(int i=0;i<root.getChildCount();i++) {
				String name=root.getChildAt(i).toString();
				if(name.indexOf(n)>=0){
					
					DefaultMutableTreeNode selectedNode	= 
			 				(DefaultMutableTreeNode) root.getChildAt(i);
					if (selectedNode != null && selectedNode.getParent() != null)
		 			{
		 				// 删除指定节点
						newModel.removeNodeFromParent(selectedNode);
		 			}
					//tree.updateUI();
					return true;
				}	
			}
			return false;
		}

		//打开文件
		public boolean open(String path) {
			for(int i=0;i<root.getChildCount();i++) {
				String name=root.getChildAt(i).toString();
				String n=new File(path).getName();
				if(name.indexOf(n)>=0){
					return false;
				}	
			}
			addChild(path);	
			return true;
		}
		
		
		

		
}
