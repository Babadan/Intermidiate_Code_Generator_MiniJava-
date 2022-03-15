all: compile


compile: 
	java -jar ../jtb132di.jar Minijava.jj
	java -jar ../javacc5.jar Minijava-jtb.jj
	javac ./intermidiate_code_generator/symbol_table/*.java
	javac ./intermidiate_code_generator/visitor/*.java
	javac ./intermidiate_code_generator/V_Table/*.java
	javac ./Intermidiate_code_generator.java


clean: 
	rm -f *.class
	rm -f ./intermidiate_code_generator/symbol_table/*.class
	rm -f ./intermidiate_code_generator/visitor/*.class
	rm -f ./intermidiate_code_generator/V_Table/*.class
	rm -f ./Intermidiate_code_generator.class
	rm -f ./JavaCharStream.java
	rm -f ./Minijava-jtb.jj
	rm -f ./MiniJavaParser.java
	rm -f ./MiniJavaParserConstants.java
	rm -f ./MiniJavaParserTokenManager.java
	rm -f ./ParseException.java
	rm -f ./Token.java
	rm -f ./TokenMgrError.java
	rm -rf visitor
	rm -rf syntaxtree