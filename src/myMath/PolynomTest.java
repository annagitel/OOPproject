package myMath;

import java.util.Iterator;

public class PolynomTest {
	public static void main(String[] args) {
		System.out.println("*************************************************************TEST NUMBER 1*******************************************************************");
		test0();
		System.out.println("*************************************************************TEST NUMBER 2*******************************************************************");
		test1();
		System.out.println("*************************************************************TEST NUMBER 3*******************************************************************");
		test2();
		System.out.println("*************************************************************TEST NUMBER 4*******************************************************************");
		test3();
	}
	public static void test0(){
		String poly = "-2x+3+8x^4-x";
		Polynom newPoly = new Polynom(poly);
		Iterator<Monom> polynomIterator = newPoly.iteretor();
		Monom m = polynomIterator.next();
		while(polynomIterator.hasNext()) {
			System.out.println(m.toString());
			m = polynomIterator.next();
		}
		System.out.println("tadaaaaa: "+newPoly.toString());
		System.out.println(newPoly.size());
	}
	public static void test1() {
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			System.out.println("the string is "+monoms[i]+" the monom that was added is "+m.toString());
			p1.add(m);

		}
		System.out.println("the polynim has "+p1.size()+" monoms and looks like this: "+p1.toString());
		double aa = p1.area(0, 1, 0.0001);
		System.out.println(aa);
		p1.substract(p1);

	}
	public static void test2() {
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1.toString());
		System.out.println("p2: "+p2.toString());
		p1.add(p2);
		System.out.println("p1+p2: "+p1.toString());
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1.toString());

	}
	public static void test3() {
		String [] polys = {"-4.7x^2-x+6", "-1.7x^3-1.5x^2+0.7x+8", "1.7x^2+1.7x+2", "-3x^2+0.7x+8", "-5.1x^4-3x^3+8.7x^2+15x+16"};
		int count = 0;
		for (int i = 0; i < polys.length; i++) {
			Polynom poly = new Polynom(polys[i]);
			double root = -1;
			try {
				root = poly.root(-100, 100, Monom.EPSILON);
			}
			catch (RuntimeException e) {
				System.out.println("	no root value for this polynom");
			}
			finally {
			double area = poly.area(0, 10, Monom.EPSILON); // change epsilon if you dont want your computer to crush.
			System.out.println(count + ") AREA: " + area + " ROOT: " + root);
			count++;
			}
		}

	}


}


