import java.util.ArrayList;

public class ParseTree {
    private class Node {
        Long value;
        Operator operator;
        Node parent;
        Node childLeft;
        Node childRight;

        Node(Node parent, String element){
            this.parent = parent;
            if (isOperator(element)) {
                this.operator = new Operator(element.charAt(0));
                this.value = null;
            }
            else {
                this.operator = null;
                this.value = Long.parseLong(element);
            }
        }

       Node(Node parent){
            this.operator = null;
            this.parent = parent;
        }
    }
    private class Operator{
        int priority;
        static final int CLOSE = 0;
        static final int PLUS = 1;
        static final int RESULTANT = 2;
        static final int MULTIPLICATION = 3;
        static final int DIVISION = 4;
        static final int OPEN = 5;
        static final int NUMBER = 10;
        Operator(char operator){
            switch (operator){
                case (')'): {
                    this.priority = CLOSE;
                    break;
                }
                case ('+'): {
                    this.priority = PLUS;
                    break;
                }
                case ('-'): {
                    this.priority = RESULTANT;
                    break;
                }
                case ('*'): {
                    this.priority = MULTIPLICATION;
                    break;
                }
                case ('/'): {
                    this.priority = DIVISION;
                    break;
                }
                case ('('): {
                    this.priority = OPEN;
                    break;
                }
            }
        }

        Operator(){
            this.priority = NUMBER;
        }
    }
    private ArrayList<String> elements;
    private Node root;

    public ParseTree(String parse) {
        this.elements = getArrayOfElements(parse);
        this.root = makeTree(null, null,0, elements.size());
    }

    private ArrayList<String> getArrayOfElements(String parse){
        int indexOfNumber = 0;
        ArrayList<String> elements = new ArrayList<>();
        for (int i = 0; i < parse.length() || indexOfNumber != 0; i++) {
            if (i < parse.length() && Character.isDigit(parse.charAt(i))) {
                ++indexOfNumber;
            } else {
                if (indexOfNumber != 0) {
                    elements.add(parse.substring(i - indexOfNumber, i));
                    indexOfNumber = 0;
                }
                if (i < parse.length())
                    elements.add(String.valueOf(parse.charAt(i)));
            }
        }
        return elements;
    }

    private static int[] getIntervalOfParse(ArrayList<String> elements, int begin, int end){
        int[] interval = {begin, end};
        if (elements.get(begin).charAt(0) != '(' && elements.get(end - 1).charAt(0) != ')')
            return interval;
        int countOpenBracket = 0, countCloseBracket = 0, countOpenClose = 0, indexOfDelete = 0, checkNeedBracket = 0;
        boolean checkOpen = true;
        for (int i = end - 1; i >= begin && elements.get(i).charAt(0) == ')'; i--)
            countCloseBracket++;
        for (int i = begin; i < end; i++) {
            if (elements.get(i).charAt(0) == '(') {
                countOpenBracket = (checkOpen) ? countOpenBracket + 1 : countOpenBracket;
                if (checkNeedBracket == 1 && (countOpenClose < countOpenBracket)){
                    countOpenBracket = countOpenClose;
                    checkNeedBracket = 2;
                }
                countOpenClose++;
            } else checkOpen = false;
            if (i < end - countCloseBracket && countOpenClose == 0)
                return interval;
            if (elements.get(i).charAt(0) == ')') {
                if (i != end - countCloseBracket) {
                    countOpenClose--;
                    checkNeedBracket = (checkNeedBracket == 0) ? 1 : checkNeedBracket;
                }
                if (i == end - countCloseBracket) {
                    if (countOpenClose == 0) {
                        return interval;
                    } else {
                        indexOfDelete = Math.min(countOpenBracket, Math.min(countCloseBracket, countOpenClose));
                        break;
                    }
                }
            }
        }
        interval[0] = (countOpenClose - countCloseBracket) > 0 ? begin + indexOfDelete + (countOpenClose - countCloseBracket) : begin + indexOfDelete;
        interval[1] = (countOpenClose - countCloseBracket) < 0 ? end - indexOfDelete - (countOpenClose - countCloseBracket) : end - indexOfDelete;
        return interval;
    }

    private int getSeparatorOperator(int begin, int end) {
        Operator operator = new Operator();
        operator.priority = Integer.MAX_VALUE;
        int indexOperator = -1, countBracket = 0;
        for (int i = begin; i < end; i++) {
            Operator current = isOperator(elements.get(i)) ? new Operator(elements.get(i).charAt(0)) : new Operator();
            countBracket = (current.priority == Operator.OPEN) ? countBracket + 1 : (current.priority == Operator.CLOSE) ? countBracket - 1 : countBracket;
            if (operator.priority >= current.priority && current.priority != Operator.CLOSE && current.priority != Operator.OPEN && countBracket == 0) {
                indexOperator = i;
                operator = current;
            }
        }
        return indexOperator;
    }

    private Node makeTree(Node parent, Node root, int begin, int end){
        if (begin != end) {
            int[] interval = getIntervalOfParse(elements, begin, end);
            begin = interval[0];
            end = interval[1];
            int index = getSeparatorOperator(begin, end);
            if (root == null) {
                root = (index == -1) ? null : new Node (parent, elements.get(index));
            }
            if (root != null) {
                root.childLeft = makeTree(root, root.childLeft, begin, index);
                root.childRight = makeTree(root, root.childRight, index + 1, end);
            }
        }
        return root;
    }

    public long getValueOfTree(){
        getValueOfTree(this.root);
        return this.root.value;
    }

    private long getValueOfTree(Node root){
        if (root != null && root.operator != null) {
            root.value = getValueOfTree(root.childLeft);
            long rightValue = getValueOfTree(root.childRight);
            if (root.value != null) {
                switch (root.operator.priority) {
                    case (4): {
                        root.value = division(root.value, rightValue);
                        break;
                    }
                    case (3): {
                        root.value = multiplication(root.value, rightValue);
                        break;
                    }
                    case (2): {
                        root.value = resultant(root.value, rightValue);
                        break;
                    }
                    case (1): {
                        root.value = sum(root.value, rightValue);
                        break;
                    }
                }
            }
        }
        return (root == null || root.value == null) ? 0 : root.value;
    }

    private static long sum(long elem1, long elem2){
        return elem1 + elem2;
    }

    private static long division(long elem1, long elem2){
        return elem1 / elem2;
    }

    private static long multiplication(long elem1, long elem2){
        return elem1 * elem2;
    }

    private static long resultant(long elem1, long elem2){
        return elem1 - elem2;
    }

    private static boolean isOperator(String element){
        return element.length() == 1 && (element.charAt(0) == '-' || element.charAt(0) == '+' || element.charAt(0) == '*' || element.charAt(0) == '/' || element.charAt(0) == '(' || element.charAt(0) == ')');
    }
}