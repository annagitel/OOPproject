package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import Ex1.Monom;
import Ex1.Polynom;

class PolynomTest {

    @Test
    void testAdd() {
        Polynom p1 = new Polynom("2x+4");
        String[] monoms = { "2x", "4x", "-x", "17x" };

        for (int i = 0; i < monoms.length; i++) {
            Monom m = new Monom(monoms[i]);
            p1.add(m);
        }

        assertEquals("24.0x+4.0", p1.toString());
    }

    @Test
    void testSub() {
        Polynom p1 = new Polynom("7x+4");
        String[] poly = { "2x+1", "4x+1" };

        for (int i = 0; i < poly.length; i++) {
            Polynom p2 = new Polynom(poly[i]);
            p1.substract(p2);
        }

        assertEquals("x+2.0", p1.toString());
    }

    @Test
    void testMultiply() {
        Polynom p1 = new Polynom("7x+4");
        String[] poly = { "2x+1", "x+1" };

        for (int i = 0; i < poly.length; i++) {
            Polynom p2 = new Polynom(poly[i]);
            p1.multiply(p2);
        }

        assertEquals("14.0x^3+29.0x^2+19.0x+4.0", p1.toString());
    }

    @Test
    void testF() {
        String[] poly = { "10x^2+4", "2x+1", "15.27x" };
        double y = 0;
        double x = 2;

        for (int i = 0; i < poly.length; i++) {
            Polynom p = new Polynom(poly[i]);
            y += p.f(x);

        }
        assertEquals(10 * Math.pow(x, 2) + 4 + 2 * x + 1 + 15.27 * x, y);
    }

    @Test
    void testIsZero() {
        String[] poly = { "0", "00", "0x" };
        Polynom sum = new Polynom();
        for (int i = 0; i < poly.length; i++) {
            Polynom p = new Polynom(poly[i]);
            sum.add(p);
        }
        assertTrue(sum.isZero());
    }

    @Test
    void testCopy() {

        Polynom P = new Polynom("2x^+4x-5");
        Polynom PCopy = (Polynom) P.copy();

        assertEquals(P, PCopy);
    }

    @Test
    void testInitTest() {
        String s = "2x+4x^7-4x^2+2";
        Polynom p = new Polynom(s);
        assertEquals("4.0x^7-4.0x^2+2.0x+2.0", p.toString());
    }

    @Test
    void testEquals() {
        Polynom p = new Polynom("7x^6-2x^4-5x+4");
        Polynom s = new Polynom("-5x+4-2x^4+7x^6");
        assertTrue(p.equals(s));
    }

}