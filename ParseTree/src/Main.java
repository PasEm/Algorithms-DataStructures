public class Main {

    public static void main(String[] args) {
	    ParseTree parseTree = new ParseTree("-(5+2)*7+(3+4)+(4*7)/7-2*1");
		System.out.println(parseTree.getValueOfTree());
    }
}
