package myMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import myMath.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 * @author anna
 */
public class Polynom implements Polynom_able{
	/************************************constractors******************************************************************/
	ArrayList<Monom> currentPolynom; //INIT THE POLYNOM AS AN ARRAY LIST. THE CONSTRACTORS USE THIS AL
	Monom_Comperator sortComp = new Monom_Comperator();

	/*** Zero (empty polynom)*/
	public Polynom() {
		currentPolynom = new ArrayList<Monom>(0); // Create an ArrayList object;
	}
	/*** init a Polynom from a String* @param s: is a string represents a Polynom*/
	public Polynom(String s) {
		currentPolynom = new ArrayList<Monom>(); // Create an ArrayList object;
		String currentS = "";
		Monom currentMonom;
		for (int i=0;i<s.length();i++){
			if (i==0 && s.charAt(i)=='-')
				currentS += '-';
			if(s.charAt(i)!='+' && s.charAt(i)!='-')
				currentS = currentS+s.charAt(i);
			else if (!currentS.equals("-")){
				currentMonom = new Monom(currentS);
				currentPolynom.add(currentMonom);
				if (s.charAt(i)=='-')
					currentS = "-";
				else
					currentS = "";
			}
		}
		currentMonom = new Monom(currentS);
		currentPolynom.add(currentMonom);

		currentPolynom.sort(sortComp);
	}

	/*** init a Polynom from an Excisting polynom (deep copy):*/
	public Polynom(Polynom p) {
		currentPolynom = new ArrayList<Monom>();
		Iterator<Monom> monoms = p.iteretor();
		while(monoms.hasNext()) {
			Monom a = new Monom(monoms.next());
			add(a);
		}
	}

	/************************************public methods ******************************************************************/
	@Override
	public double f(double x) {
		double sum = 0;
		Iterator<Monom> polynomIterator = this.iteretor();
		while(polynomIterator.hasNext()){
			Monom currentMonom = polynomIterator.next() ;
			sum = sum + currentMonom.f(x);
		}
		return sum;
	}

	@Override
	public void add(Monom m1) {
		Iterator<Monom> polynomIterator = currentPolynom.iterator();
		while (polynomIterator.hasNext())
		{
			Monom currentMonom=polynomIterator.next();
			if (currentMonom.get_power()==m1.get_power()) {
				currentMonom.add(m1);
				if (currentMonom.isZero()) {
					currentPolynom.remove(currentMonom);
				}
				return;
			}
		}
		currentPolynom.add(m1);
		currentPolynom.sort(sortComp);
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> monomIterator = p1.iteretor();
		while (monomIterator.hasNext())
			this.add(monomIterator.next());
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> monomIterator = p1.iteretor();
		Polynom negPoly = new Polynom();
		while (monomIterator.hasNext()) {
			negPoly.add(monomIterator.next().negative());
		}
		this.add(negPoly);
	}

	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> thisMonomIterator = this.iteretor();
		Iterator<Monom> givenMonomIterator = p1.iteretor();
		Polynom newPolynom =new Polynom();

		while (thisMonomIterator.hasNext()){
			Monom thisMonom = thisMonomIterator.next();
			givenMonomIterator = p1.iteretor();
			while (givenMonomIterator.hasNext()) {
				Monom givenMonom =givenMonomIterator.next();
				Monom newMonom = new Monom(thisMonom);
				newMonom.multipy(givenMonom);
				newPolynom.add(newMonom);
			}
		}

		currentPolynom.clear();
		this.add(newPolynom);
	}

	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom> thisMonomIterator = this.iteretor();
		Iterator<Monom> givenMonomIterator = p1.iteretor();

		while(givenMonomIterator.hasNext() && thisMonomIterator.hasNext()){
			if(!givenMonomIterator.next().equals(thisMonomIterator.next()))
				return false;
		}
		if(givenMonomIterator.hasNext() || thisMonomIterator.hasNext())
			return false;

		return true;
	}

	@Override
	public boolean isZero() {
		ArrayList<Monom> newPoly = new ArrayList<Monom>();
		Iterator<Monom> monomIterator = this.iteretor();
		while (monomIterator.hasNext()){
			Monom current = monomIterator.next();
			if (!current.isZero())
				return false;
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if (x0 *x1 <= 0 || f(x0)*f(x1)<= 0) {
			double middle = (x1 + x0) / 2;
			if (Math.abs(f(middle)) < eps)
				return middle;
			if (f(x0) == 0)
				return x0;
			if (f(x1) == 0)
				return x1;
			if (f(middle) < 0)
				x0 = middle;
			else if (f(middle) > 0)
				x1 = middle;
			return root(x0, x1, eps);
		}
		else {
			throw new RuntimeException("cannot be calculated, Not root value");
		}

	}

	@Override
	public Polynom_able copy(){
		ArrayList<Monom> newPoly = new ArrayList<Monom>();
		Iterator<Monom> monomIterator = this.iteretor();
		while (monomIterator.hasNext()){
			Monom current = monomIterator.next();
			Monom newMonom = new Monom(current.get_coefficient(),current.get_power());
			newPoly.add(newMonom);
		}
		return null;
	}

	@Override
	public Polynom_able derivative() {
			Polynom derivatedPolynom = new Polynom();
			Iterator<Monom> monomIterator = this.iteretor();
			while (monomIterator.hasNext()) {
				Monom current = monomIterator.next();
				Monom derivated = new Monom(current).derivative();
				if (derivated.get_coefficient()!=0)
					derivatedPolynom.add(derivated);
			}
		return derivatedPolynom;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double sum =0;
		double min = Math.min(x0,x1);
		double max = Math.max(x0,x1);

		for(double i=min+eps;i<=x1;i+=eps){
			if (f(i)>0){
				sum +=f(i)*eps;
			}
		}
		return sum;
	}

	public Iterator<Monom> iteretor() {
		return currentPolynom.iterator();
	}
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> monomIterator = this.iteretor();
		while (monomIterator.hasNext()){
			Monom current = monomIterator.next();
			current.multipy(m1);
		}
	}

	public String toString() {
		String s ="";
		Monom m;
		Iterator <Monom> monomIterator=currentPolynom.iterator();
		while(monomIterator.hasNext()) {
			m=monomIterator.next();
			if(s.length()==0)
				s = s+m.toString();
			else{
				if(m.get_coefficient()<0)
					s=s+m.toString();
				else
					s=s+'+'+m.toString();
			}
		}
		return s;
	}

	public int size(){
		int count = 0;
		Iterator <Monom> monomIterator=this.iteretor();
		while(monomIterator.hasNext()){
			count++;
			monomIterator.next();
		}
		return count;
	}

	/************************************private methods ******************************************************************/

	
}
