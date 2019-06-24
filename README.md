# Source-Code-Analysis

As an Academic project, this project is aimed at performing the analysis of a Java Source Code and collecting the following data:
1. Cyclomatic Complexity of the source code.
2. Generating a Call-graph.
3. Performing a Def-Use Analysis of the source code.

Cyclomatic Complexity of the source code is calculated by considering various types of statements in the source code. The type of statements considered are:
  1. Selection statements
  2. Loop statements
  3. Operator statements
  4. Exception statements
  
A serialized call-graph is generated using soot (A Java Optimization Framework). This internally identifies the nodes as the methods classifying them as source and target methods thereby generating a call-graph of the entire source code. The result is generated in a .dot output file which later can be imported and viewed in a .dot format file viewer. 

Def-Use Analysis of the source code is done using Java collections and a series of string operations. This part of the code creates a list of variables defined and used through the program, and generates the definition and usage analysis for each one of the variable. This result is stored in a tabular format and is printed on the console.
