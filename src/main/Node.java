package main;

public class Node {
	
	public String value;
	public Node left;
	public Node right;
	int position;
	
	public Node(String data)
    {
        this.value = data;
        this.left = this.right = null;
    }
	
	public Node(Integer data) {
		this.value = String.valueOf(data);
        this.left = this.right = null;
	}
	
	public Node(char data) {
		this.value = String.valueOf(data);
        this.left = this.right = null;
	}

	
	public Node(char data, Node left, Node right) {
		this.value = String.valueOf(data);
        this.left = left;
        this.right = right;
	}
 
    public Node(String data, Node left, Node right)
    {
        this.value = data;
        this.left = left;
        this.right = right;
    }


}
