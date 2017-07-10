package com.youi.example1;

/**
 * Created by jinliang on 2017/3/1.
 */
public class BinTreeTraverse2 {

    private class TreeNode {
        public String data;
        public TreeNode leftChild;
        public TreeNode rightChild;
        public TreeNode(String data){
            this.data=data;
            this.leftChild=null;
            this.rightChild=null;
        }
        public TreeNode(String data,TreeNode leftChild,TreeNode rightChild){
            this.data=data;
            this.leftChild=leftChild;
            this.rightChild=rightChild;
        }
    }

    //前序遍历
    public void preOrder(TreeNode subTree){
        if(subTree!=null){
            visted(subTree);
            preOrder(subTree.leftChild);
            preOrder(subTree.rightChild);
        }
    }
    //中序遍历
    public void inOrder(TreeNode subTree){
        if(subTree!=null){
            inOrder(subTree.leftChild);
            visted(subTree);
            inOrder(subTree.rightChild);
        }
    }
    //后序遍历
    public void postOrder(TreeNode subTree){
        if(subTree!=null){
            postOrder(subTree.leftChild);
            postOrder(subTree.rightChild);
            visted(subTree);
        }
    }
    private void visted(TreeNode subTree){
        System.out.print(subTree.data+" ");
    }
    public TreeNode createTree(){
        TreeNode a =new TreeNode("a");
        TreeNode b =new TreeNode("b");
        TreeNode c =new TreeNode("c");
        TreeNode d =new TreeNode("d");
        TreeNode e =new TreeNode("e");
        TreeNode f =new TreeNode("f");
        TreeNode g =new TreeNode("g");
        TreeNode h =new TreeNode("h");
        a.leftChild=d;
        a.rightChild=c;
        d.rightChild=b;
        b.leftChild=g;
        c.leftChild=e;
        c.rightChild=f;
        f.leftChild=h;
        return a;
    }

    public static void main(String[] args) {
       BinTreeTraverse2 demo = new BinTreeTraverse2();
       TreeNode root = demo.createTree();
        demo.postOrder(root);
        System.out.println();
        demo.inOrder(root);
        System.out.println();
        demo.postOrder(root);
        System.out.println();
    }
}
