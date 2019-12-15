/**
 * 

 */
package Ex1Testing;
import static org.junit.jupiter.api.Assertions.*;

import Ex1.*;
import org.junit.jupiter.api.Test;

public class ComplexFunctionTest {

	@Test
	void plus(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x)");
		String poly[]={"3x","7x+2"};
		for (int i = 0; i < poly.length; i++) {
			Polynom p = new Polynom(poly[i]);
			f.plus(p);
		}
		assertEquals("Plus(Plus(Plus(2.0x,3.0x),3.0x),7.0x+2.0)", f.toString());
	}

	@Test
	void mul(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		String poly[]={"3x","7x+2"};
		for (int i = 0; i < poly.length; i++) {
			Polynom p = new Polynom(poly[i]);
			f.mul(p);
		}
		assertEquals("(Mul(Plus(2.0x,3.0x+1.0),3.0x),7.0x+2.0)", f.toString());
	}

	@Test
	void div(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		String poly[]={"3x","7x+2"};
		for (int i = 0; i < poly.length; i++) {
			Polynom p = new Polynom(poly[i]);
			f.div(p);
		}
		assertEquals("Divid(Divid(Plus(2.0x,3.0x+1.0),3.0x),7.0x+2.0)", f.toString());
	}
	@Test
	void max(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		String poly[]={"3x","7x+2"};
		for (int i = 0; i < poly.length; i++) {
			Polynom p = new Polynom(poly[i]);
			f.max(p);
		}
		assertEquals("Max(Max(Plus(2.0x,3.0x+1.0),3.0x),7.0x+2.0)", f.toString());
	}
	@Test
	void min(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		String poly[]={"3x","7x+2"};
		for (int i = 0; i < poly.length; i++) {
			Polynom p = new Polynom(poly[i]);
			f.min(p);
		}
		assertEquals("Min(Min(Plus(2.0x,3.0x+1.0),3.0x),7.0x+2.0)", f.toString());
	}
	@Test
	void comp(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		String poly[]={"3x","7x+2"};
		for (int i = 0; i < poly.length; i++) {
			Polynom p = new Polynom(poly[i]);
			f.comp(p);
		}
		assertEquals("Comp(Comp(Plus(2.0x,3.0x+1.0),3.0x),7.0x+2.0)", f.toString());
	}
	@Test
	void copy(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");

		ComplexFunction g= (ComplexFunction) f.copy();


		assertEquals("Plus(2.0x,3.0x+1.0)", g.toString());
	}
	@Test
	void initFromString(){
		String p= "Plus(Plus(Plus(2x,3x),3x),7x+2)";
		function g = (new ComplexFunction()).initFromString("Plus(Plus(Plus(2x,3x),3x),7x+2)");
		assertEquals("Plus(Plus(Plus(2.0x,3.0x),3.0x),7.0x+2.0)", g.toString());

	}
	@Test
	void toStringt(){
		ComplexFunction f= new ComplexFunction("Plus(Plus(Plus(2x,3x),3x),7x+2)");
		assertEquals("Plus(Plus(Plus(2.0x,3.0x),3.0x),7.0x+2.0)",f.toString());

	}

	@Test
	void f(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		f.f(2);
		assertEquals(11,f.f(2));
	}
	@Test
	void left(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		f.left();
		assertEquals("2.0x",f.left().toString());

		f = new ComplexFunction("x^2");
		f.left();
		assertEquals("x^2",f.left().toString());
	}
	@Test
	void right(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");
		f.right();
		assertEquals("3.0x+1.0",f.right().toString());

		f = new ComplexFunction("x^2");
		f.right();
		assertNull(f.right());
	}
	@Test
	void getOp(){
		ComplexFunction f= new ComplexFunction("plus(2x,3x+1)");

		assertEquals(Operation.Plus, f.getOp());
	}
	@Test
	void equals(){
		ComplexFunction f= new ComplexFunction("mul(x,x)");
		ComplexFunction g= new ComplexFunction("x^2");
		assertEquals(true,f.equals(g));
	}

	@Test
	void ComplexFunction(){
		ComplexFunction f= new ComplexFunction();
		assertEquals(Operation.None, f.getOp());
		assertEquals(null, f.left());

		ComplexFunction g =new ComplexFunction("plus(3x,2x)");
		assertEquals(Operation.Plus,g.getOp());
		assertEquals("3.0x",g.left().toString());
		assertEquals("2.0x", g.right().toString());

		ComplexFunction p=new ComplexFunction(g.getOp().toString(),g.left(),g.right());
		assertEquals(Operation.Plus,p.getOp());
		assertEquals("3.0x",p.left().toString());
		assertEquals("2.0x", p.right().toString());

		ComplexFunction y=new ComplexFunction(g.getOp(),g.left(),g.right());
		assertEquals(Operation.Plus,y.getOp());
		assertEquals("3.0x",y.left().toString());
		assertEquals("2.0x", y.right().toString());

		ComplexFunction l= new ComplexFunction(g.left());
		assertEquals(l.left(), g.left());

		ComplexFunction lr=new ComplexFunction(g.left(),g.right());
		assertEquals(Operation.None,lr.getOp());
		assertEquals("3.0x",lr.left().toString());
		assertEquals("2.0x", lr.right().toString());

		ComplexFunction ol=new ComplexFunction(g.getOp(),g.left());
		assertEquals(Operation.Plus,ol.getOp());
		assertEquals("3.0x",ol.left().toString());
		assertEquals(null, ol.right());

		ComplexFunction sol=new ComplexFunction(g.getOp().toString(),g.left());
		assertEquals(Operation.Plus,sol.getOp());
		assertEquals("3.0x",sol.left().toString());
		assertEquals(null, sol.right());

	}



}

