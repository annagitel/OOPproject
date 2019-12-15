package Ex1;

public class ComplexFunction implements complex_function {
    /************ private objects*****************************/
    private Operation op = Operation.None;
    private function left ;
    private function right = null;
    public ComplexFunction(){};

    /************ getters*************************************/
    public function left() {
        return this.left;
    }

    public function right() {
        return this.right;
    }

    public Operation getOp() {
        return this.op;
    }
    /************constractors**********************************/

    public ComplexFunction(Operation o, function l){ //init from single function object
        op = Operation.None;
        left = l;
        right = null;
    }
    public ComplexFunction(function l){ //init from single function object
        op = Operation.None;
        left = l;
        right = null;
    }
    public ComplexFunction(String o, function l){ //init from single function object
        op = Operation.None;
        left = l;
        right = null;
    }
    public ComplexFunction(function l, function r){ //init from single function object
        op = Operation.None;
        left = l;
        right = r;
    }
    public ComplexFunction(ComplexFunction comp){ //copy constractor
        this.op = comp.getOp();
        this.left = comp.left();
        this.right = comp.right();
    }
    public ComplexFunction(String  o, function l, function r){ //init with given objects
        this.op =checkO(o);
        this.left = l;
        this.right = r;
    }
    public ComplexFunction(Operation  o, function l, function r){ //init with given objects
        this.op =o;
        this.left = l;
        this.right = r;
    }
    public ComplexFunction(String s){
        initFromString(s);
    }

    /******************public functions****************************/
    public void plus(function f1) {
        ComplexFunction t= (ComplexFunction) this.copy();
        this.left = t;
        this.right = f1;
        this.op = Operation.Plus;
    }

    public void mul(function f1) {
        ComplexFunction t= (ComplexFunction) this.copy();
        this.left = t;
        this.right = f1;
        this.op = Operation.Times;
    }

    public void div(function f1) {
        ComplexFunction t= (ComplexFunction) this.copy();
        this.left = t;
        this.right = f1;
        this.op = Operation.Divid;
    }

    public void max(function f1) {
        ComplexFunction t= (ComplexFunction) this.copy();
        this.left = t;
        this.right = f1;
        this.op = Operation.Max;
    }

    public void min(function f1) {
        ComplexFunction t= (ComplexFunction) this.copy();
        this.left = t;
        this.right = f1;
        this.op = Operation.Min;
    }

    public void comp(function f1) {
        ComplexFunction t= (ComplexFunction) this.copy();
        this.left = t;
        this.right = f1;
        this.op = Operation.Comp;
    }

    public double f(double x) {
        switch (this.getOp()){
            case Plus:
                return (this.left().f(x)+this.right().f(x));
            case Divid:
                return (this.left().f(x)/this.right().f(x));
            case Times:
                return (this.left().f(x)*this.right().f(x));
            case Min:
                return Math.min(this.left().f(x),this.right().f(x));
            case Max:
                return Math.max(this.left().f(x),this.right().f(x));
            case Comp:
                return this.left.f(x);
            default:
                return -1;
        }
    }

    public function copy() {
        ComplexFunction newC = new ComplexFunction(this.getOp(), this.left(), this.right());
        return newC;
    }

    @Override
    public String toString(){
        if(op == Operation.None)
            return left.toString();
        return this.op.toString()+"("+this.left+","+this.right+")";
    }

    public function initFromString(String s) {
        s=s.replaceAll(" ", "");
        try{
           if (isPolynom(s))
               return new Polynom(s);
        }
        catch (Exception e) {}
        try{
            if (isMonom(s))
                return new Monom(s);
        }
        catch (Exception e) {}
        ComplexFunction f= new ComplexFunction();
        String operation= stringOp(s);
        String left=stringLeft(s);
        String right=stringRight(s);
        f.op=checkO(operation);
        if (isPolynom(right)){
            f.right = new Polynom(right);
        }
        else {
            f.right = new ComplexFunction();
            f.right=initFromString(right);
        }
        if (isPolynom(left)){
            f.left = new Polynom(left);
        }
        else {
            f.left = new ComplexFunction();
            f.left=initFromString(left);
        }
        return f;
    }
    private String stringOp (String s) {
        String t="";
        int i=0;
        while(i<s.length()&&s.charAt(i)!='(') {
            t+=s.charAt(i);
            i++;
        }
        return t;
    }
    private String stringLeft(String s) {
        int counter=0;
        int start=0;
        int end=0;
        for(int i=0;(i<s.length());i++){
            if(s.charAt(i)=='('){
                counter++;
                if(start==0){
                    start=i+1;
                }
            }
            if(s.charAt(i)==','){
                counter--;
                if(counter==0){
                    end=i;
                    break;
                }
            }
        }
        return s.substring(start,end);
    }
    private String stringRight(String s) {
        int counter=0;
        int start=0;
        int end=0;
        int length=s.length();
        for(int i=0;(i<s.length());i++){
            if(s.charAt(i)=='('){
                counter++;
                if(start==0){
                    start=i+1;
                }
            }
            if(s.charAt(i)==','){
                counter--;
                if(counter==0){
                    end=i;
                    break;
                }
            }
        }
        return s.substring(end+1,length-1);
    }

    private boolean isPolynom(String s) {
        try{ Polynom p = new Polynom(s); }
        catch (Exception e){ return false; }
        return true;
    }
    private boolean isMonom(String s) {
        try{ Monom m = new Monom(s); }
        catch (Exception e){ return false;}
        return true;
    }

    private Operation checkO(String str){
        str=str.toLowerCase();
        switch(str){
            case ("max"):
                return Operation.Max;
            case ("min"):
                return Operation.Min;
            case ("comp"):
                return Operation.Comp;
            case ("plus"):
                return Operation.Plus;
            case ("mul"):
                return Operation.Times;
            case ("div"):
                return  Operation.Divid;
            case (""):
                return Operation.None;
            default:
                return Operation.Error;
        }
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof ComplexFunction))
            return false;
        ComplexFunction comp = new ComplexFunction((ComplexFunction) obj);
        for (int i=-50; i<50; i++){
            double num = Math.random();
            double thisN = this.f(num);
            double otherN = comp.f(num);
            if (Math.abs(thisN-otherN)>Monom.EPSILON)
                return false;
        }
        return true;
    }
}
