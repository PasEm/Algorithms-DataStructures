import org.junit.Assert;
import org.junit.Test;

public class ParseTreeTester {

    @Test
    public void test1(){
        ParseTree parseTree = new ParseTree("-(5+2)*7+(3+4)+(4*7)/7-2*1");
        Assert.assertEquals(-40, parseTree.getValueOfTree());
    }

    @Test
    public void test2(){
        ParseTree parseTree = new ParseTree("-1");
        Assert.assertEquals(-1, parseTree.getValueOfTree());
    }

    @Test
    public void test3(){
        ParseTree parseTree = new ParseTree("((5))");
        Assert.assertEquals(5, parseTree.getValueOfTree());
    }

    @Test
    public void test4(){
        ParseTree parseTree = new ParseTree("(-(5+2)*7+(3+4)+(4*7)/7-2*1)");
        Assert.assertEquals(-40, parseTree.getValueOfTree());
    }

    @Test
    public void test5(){
        ParseTree parseTree = new ParseTree("-(-(5+2)*7+(3+4)+(4*7)/7-2*1)");
        Assert.assertEquals(40, parseTree.getValueOfTree());
    }

    @Test
    public void test6(){
        ParseTree parseTree = new ParseTree("48*5*6-(12-(45*9))*9+36");
        Assert.assertEquals(5013, parseTree.getValueOfTree());
    }

    @Test
    public void test7(){
        ParseTree parseTree = new ParseTree("(48*5*6-(12-(45*9))*9+36)");
        Assert.assertEquals(5013, parseTree.getValueOfTree());
    }

    @Test
    public void test8(){
        ParseTree parseTree = new ParseTree("-(48*5*6-(12-(45*9))*9+36)");
        Assert.assertEquals(-5013, parseTree.getValueOfTree());
    }

    @Test
    public void test9(){
        ParseTree parseTree = new ParseTree("(5+2)*7+(3+4)");
        Assert.assertEquals(56, parseTree.getValueOfTree());
    }

    @Test
    public void test10(){
        ParseTree parseTree = new ParseTree("7*7-325+686/2+325-7*7-686/2");
        Assert.assertEquals(0, parseTree.getValueOfTree());
    }

    @Test
    public void test11(){
        ParseTree parseTree = new ParseTree("0-0+0+0-0+0*0");
        Assert.assertEquals(0, parseTree.getValueOfTree());
    }

    @Test
    public void test12(){
        ParseTree parseTree = new ParseTree("((5+2)*7-84/7)+(7*7)+((3+4)/7-12+77-0)");
        Assert.assertEquals(152, parseTree.getValueOfTree());
    }

    @Test
    public void test13(){
        ParseTree parseTree = new ParseTree("((((5+2)*7)))+((((3+4))))");
        Assert.assertEquals(56, parseTree.getValueOfTree());
    }

    @Test
    public void test14(){
        ParseTree parseTree = new ParseTree("((((5+2)*7)))-((((3+4))))");
        Assert.assertEquals(42, parseTree.getValueOfTree());
    }

    @Test
    public void test15(){
        ParseTree parseTree = new ParseTree("(((((5+2)*7+(3+4)))))");
        Assert.assertEquals(56, parseTree.getValueOfTree());
    }

    @Test
    public void test16(){
        ParseTree parseTree = new ParseTree("((((-(-5+2)*7+(3+4)))))");
        Assert.assertEquals(28, parseTree.getValueOfTree());
    }

    @Test
    public void test17(){
        ParseTree parseTree = new ParseTree("(-(((-(-5+2)*7+(3+4)))))");
        Assert.assertEquals(-28, parseTree.getValueOfTree());
    }
}