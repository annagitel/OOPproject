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
        Operation o;
        function l;
        function r;

        int bracketsBalance = 0;
        int flag = 0;
        String opS = "";
        String leftS ="";
        String rightS = "";

        for (int i =0; i<s.length(); i++){
            if (s.charAt(i)=='('){
                opS = s.substring(0,flag);
                bracketsBalance++;
                flag = i;
            }

            if (s.charAt(i)==','){
                if (bracketsBalance == 1){
                    leftS = s.substring(flag+1,i);
                    rightS = s.substring(i+1,s.length());
                }
            }
        }

        o = checkO(opS);

        if (isMonom(leftS))
            l = new Monom(leftS);
        else if (isPolynom(leftS))
            l = new Polynom(leftS);
        else
            l = new ComplexFunction(leftS);

        if (isMonom(rightS))
            r = new Monom(rightS);
        else if (isPolynom(rightS))
            r = new Polynom(rightS);
        else
            r = new ComplexFunction(rightS);


        return new ComplexFunction(o,l,r);
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
