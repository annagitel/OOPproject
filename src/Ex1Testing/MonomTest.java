package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import Ex1.Monom;

class MonomTest {

    @Test
    void testF() {
        String[] monoms = { "10x", "2x", "15.27x" };
        double y = 0;
        double x = 2;

        for (int i = 0; i < monoms.length; i++) {
            Monom m = new Monom(monoms[i]);
            y += m.f(x);

        }
        assertEquals(10 * x + 2 * x + 15.27 * x, y);
    }

    @Test
    void testDerivativeSum() {
        String[] Monom = { "2x", "4x", "7x", "15x" };
        Monom sum = new Monom("0");

        for (int i = 0; i < Monom.length; i++) {
            Monom m = new Monom(Monom[i]);
            Monom d = m.derivative();
            sum.add(d);
        }
        assertEquals("28.0", sum.toString());
    }

    @Test
    void testIsZero() {
        String[] monoms = { "0", "00", "0x" };
        Monom sum = new Monom("0");
        for (int i = 0; i < monoms.length; i++) {
            Monom m = new Monom(monoms[i]);
            sum.add(m);
        }
        assertTrue(sum.isZero());
    }

    @Test
    void testAdd() {
        Monom m1 = new Monom("2x");
        String[] monoms = { "2x", "4x", "-x", "17x" };

        for (int i = 0; i < monoms.length; i++) {
            Monom m2 = new Monom(monoms[i]);
            m1.add(m2);
        }

        assertEquals("24.0x", m1.toString());
    }

    @Test
    void testMultiply() {
        Monom m1 = new Monom("7x");
        String[] monoms = { "2x", "x" };

        for (int i = 0; i < monoms.length; i++) {
            Monom m2 = new Monom(monoms[i]);
            m1.multipy(m2);
        }

        assertEquals("14.0x^3", m1.toString());
    }

    @Test
    void testEquals() {
        Monom p = new Monom("7x^6");
        Monom s = new Monom("7x^6");
        assertTrue(p.equals(s));
    }

    @Test
    void testInitTest() {
        String s = "2x^6";
        Monom p = new Monom(s);
        assertEquals("2.0x^6", p.toString());
    }

}