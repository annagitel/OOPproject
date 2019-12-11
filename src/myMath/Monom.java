package myMath;
import org.junit.Test;
import java.net.StandardSocketOptions;
import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 * @author anna
 */
public class Monom implements function{
	/***************final objects***********************/
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}

	/****************** Private Methods and Data *****************/
	private Boolean isMonom(String s){
		int dot = 0;
		int pow = 0;
		int x = 0;
		int minus = 0;
		for (int i=0;i<s.length();i++){
			if (s.charAt(i) == '.') {dot++;}
			if (s.charAt(i) == 'x') {x++;}
			if (s.charAt(i) == '^') {pow++;}
			if (s.charAt(i) == '-') {minus++;}
		}
		if ((minus>1)||(dot>1)||(pow>1)||(x>1)) //not more than one x^-.
			return false;
		if (minus==1 && (s.indexOf('-')!=0 || s.indexOf('-')==s.length()-1)) // - must be first and not last
			return false;
		if ((pow==1) && (x!=1)) // cant be ^ without x
			return false;
		if(s.indexOf('^')==s.length()-1) // ^ cant be last
			return false;
		if (x==0 && pow==0){ //number only case
			return check_coef(s);
		}
		if (x==1 && pow==0){  //no power case
			if (!check_coef(s.substring(0,s.indexOf('x'))) || s.indexOf('x')!=s.length()-1)
				return false;
		}
		if (x==1 && pow==1){   //full monom case
			if ((s.indexOf('^') - s.indexOf('x')) != 1)
				return false;
			if (!check_coef(s.substring(0,s.indexOf('x'))) || !check_pow(s.substring(s.indexOf('^')+1,s.length())))
				return false;
		}
		return true;
	}
	private boolean check_coef(String s){
		if (s.length()==0)
			return true;
		if (s.length()<2){
			if (s.charAt(0)=='-' || ((s.charAt(0)>='0' && s.charAt(0)<='9')) ){
				return true;
			}
		}
		for (int i=0;i<s.length();i++){
			char c = s.charAt(i);
			if (c=='-' || c=='.' ||(c>='0' && c<='9')){
				if(c=='.'){
					if (s.charAt(i-1)<'0' || s.charAt(i-1)>'9'|| s.charAt(i+1)<'0' || s.charAt(i+1)>'9')
						return false;
				}
			}
			if ((s.charAt(0)=='-' && s.charAt(1)=='0' && s.charAt(2)!='.') || (s.charAt(0)=='0' && s.charAt(1)!='.'))
				return false;
		}
		return true;
	}
	private boolean check_pow(String s){
		if(s.charAt(0)=='0')
			return false;
		for(int i=0;i<s.length();i++){
			if (s.charAt(i)<'0'|| s.charAt(i)>'9')
				return false;
		}
		return true;
	}
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient;
	private int _power;

	/*****************************constractors***************************************/
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	public Monom(String s) throws RuntimeException{
		if (!isMonom(s))
			throw new RuntimeException("The string is not a valid Monom format");
		else {
			int pow = 0;
			int x = 0;
			for (int i=0;i<s.length();i++){
				if (s.charAt(i) == 'x') {x++;}
				if (s.charAt(i) == '^') {pow++;}
			}
			if (x==0)
				set_coefficient(Double.parseDouble(s));
			else if (x==1) {
				if (s.indexOf('x') == 0)
					set_coefficient(1);
				else if (s.indexOf('-') == 0 && s.indexOf('x') == 1)
					set_coefficient(-1);
				else
					set_coefficient(Double.parseDouble(s.substring(0, s.indexOf('x'))));
			}
			if (pow==0) {
				if (x==1)
					set_power(1);
				else
					set_power(0);
			}
			else if (pow==1)
				set_power(Integer.parseInt(s.substring(s.indexOf('^')+1,s.length())));
		}
	}

	/************* getters *************/
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}


	/*********** public methods **********/
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}

	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	}

	public boolean isZero() {
		return this.get_coefficient() == 0;
	}

	public Monom negative(){
		Monom negM = new Monom((0-this.get_coefficient()), this.get_power());
		return negM;
	}
	
	public void add(Monom m) throws RuntimeException {
		if (get_power() == m.get_power())
			set_coefficient(get_coefficient()+m.get_coefficient());
		else
			throw new RuntimeException("ERR the power of the monoms should be the same. you have got:"+m.get_power()+" "+get_power());
	}
	
	public void multipy(Monom d) {
		if (d.isZero()){
			set_coefficient(0);
			set_power(0);
		}
		else if (d.get_power()== 0)
			set_coefficient(get_coefficient()*d.get_coefficient());
		else{
			set_power(get_power()+d.get_power());
			set_coefficient(get_coefficient()*d.get_coefficient());
		}
	}
	
	public String toString() {
		Double coef = get_coefficient();
		int pow = get_power();
		String coefs = "";
		String pows = "";

		if(coef == 0)
			return "0";
		if (pow==0)
			return Double.toString(coef);

		if (pow == 1)
			pows = pows + "x";
		else
			pows = "x^" +Integer.toString(pow);

		if (coef == -1)
			coefs = "-";
		else if (coef == 1)
			coefs = "";
		else
			coefs = Double.toString(coef);

		return (coefs+pows);
	}

	public function initFromString(String s) {
		Monom newM = new Monom(s);
		return newM;
	}

	public function copy() {
		Monom newM = new Monom(this.get_coefficient(), this.get_power());
		return newM;
	}

	@Override
	public boolean equals(Object a) {
		if (!(a instanceof Monom))
			return false;
		Monom mon = new Monom((Monom) a);
		if (mon.get_coefficient()==0 && this.get_coefficient()==0)
			return true;
		if (Math.abs(mon.get_power()-this.get_power())<EPSILON && Math.abs(mon.get_coefficient()-this.get_coefficient())<EPSILON)
			return true;
		return false;
	}

}
