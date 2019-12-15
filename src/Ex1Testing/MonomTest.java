package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Ex1.Monom;

class MonomTest {
    @Test
    void testF() {
        String[] mon = { "2x", "4x", "7x", "-15x" };
        int[] a = {10,20,35,-75};
        for (int i = 0; i < mon.length; i++) {
            Monom m = new Monom(mon[i]);
            assertEquals(m.f(5),a[i]);
        }
    }

    @Test
    void testDerivative() {
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
        String[] monomsT = { "0", "0x", "0x^5"};
        for (int i = 0; i < monomsT.length; i++) {
            Monom m = new Monom(monomsT[i]);
            assertTrue(m.isZero());
        }
        String[] monomsF = { "-8", "4x", "6x^9"};
        for (int i = 0; i < monomsF.length; i++) {
            Monom m = new Monom(monomsF[i]);
            assertFalse(m.isZero());
        }

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
        Monom orig = new Monom("7x");
        String[] monoms = { "2x^4", "-6x", "x" };

        for (int i = 0; i < monoms.length; i++) {
            Monom m = new Monom(monoms[i]);
            orig.multipy(m);
        }
        assertEquals("-84.0x^7", orig.toString());
    }

    @Test
    void testEquals() {
        Monom a = new Monom("13x^6");
        Monom b = new Monom("13x^6");
        Monom c = new Monom("-8x^9");
        Monom d = new Monom("-8x^9");
        Monom e = new Monom("x^2");
        Monom f = new Monom("x^2");
        assertTrue(a.equals(b));
        assertTrue(c.equals(d));
        assertTrue(e.equals(f));
    }

    @Test
    void testInitTest() {
        String s = "2x^6";
        Monom p = new Monom(s);
        assertEquals("2.0x^6", p.toString());
    }

}