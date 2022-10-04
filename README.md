# ExpressionTreeApp
Simple Java application to build Expression Tree from Arithmetic Expression, and solve Expression Tree

To run the application:

1. Change directory to the bin folder of the project 
   cd "\ExpressionTreeApp\bin"

2. Run Main.class using java, one String argument will be required as input
   java main.Main "((15/(7-(1+1)))*-3)-(2+(1+1))"
   
   ![image](https://user-images.githubusercontent.com/87886381/193870194-668616dd-10e2-4c55-b96b-5dd4821a58d5.png)
   
Binary tree is presented in the form of an array, where 
 - index 0 represents the root node, 
 - each left node is represented by 2*(parent's index)+1 
 - each right node is represented by 2*(parent's index)+2
 - example: [root,root.left,root.right]
