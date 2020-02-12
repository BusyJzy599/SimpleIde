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
 * ���ڵ����ļ�
 * @author 14110
 *
 */
public class InFiles extends JPanel{

		private static final long serialVersionUID = -1877527354792619586L;
	
		
		static JTree tree;
		static DefaultTreeModel newModel;
		//���ڵ�
		static DefaultMutableTreeNode root=new DefaultMutableTreeNode("Default");
		static DefaultMutableTreeNode temp;
		
		private String selectName="";
		//��Ҫ������Ŀ¼
		//static String path="D:\\MySoftware\\Java";
		
		public InFiles() {
			JLabel top=new JLabel("   <----------��Դ������---------->   ");
			top.setHorizontalTextPosition(JLabel.CENTER);
			newModel=new DefaultTreeModel(root);
			tree=new JTree(newModel);
			// ����ֻ��ѡ��һ��TreePath
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			
			this.setLayout(new BorderLayout());
			this.add(top,BorderLayout.NORTH);
			this.add(new JScrollPane(tree),BorderLayout.CENTER);
			//this.setVisible(true);
		}
		
		//��ȡѡ���ļ���
		public String getSelect() {
			return this.selectName;
		}
		//����ѡ���ļ���
		public void setSelect(String name) {
			this.selectName=name;
		}
		//��ȡ�ӽڵ���
		public int getCount() {
			return root.getChildCount();
		}
		//����ӽڵ�
		public void addChild(String path) {
			DefaultMutableTreeNode nc = new DefaultMutableTreeNode(new File(path).getName());
			newModel.insertNodeInto(nc, root, root.getChildCount());
			//�Զ�չ�����ڵ�
			TreeNode[] nodes =  newModel.getPathToRoot(nc);
 			TreePath p= new TreePath(nodes);
 			tree.scrollPathToVisible(p);
 			tree.updateUI();
			//return nc;
		}
		//ɾ���ӽڵ�
		public boolean delete(String n) {
			for(int i=0;i<root.getChildCount();i++) {
				String name=root.getChildAt(i).toString();
				if(name.indexOf(n)>=0){
					
					DefaultMutableTreeNode selectedNode	= 
			 				(DefaultMutableTreeNode) root.getChildAt(i);
					if (selectedNode != null && selectedNode.getParent() != null)
		 			{
		 				// ɾ��ָ���ڵ�
						newModel.removeNodeFromParent(selectedNode);
		 			}
					//tree.updateUI();
					return true;
				}	
			}
			return false;
		}

		//���ļ�
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
