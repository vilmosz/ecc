package uk.ac.london.ecc;

import java.awt.Point;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ecc {

    private Long a;
    private Long b;
    private Long k;
    private Long order;
    private Point g;

    public Ecc(Long a, Long b, Long k) {
        super();
        this.a = a;
        this.b = b;
        this.k = k;
    }

    public Ecc(Long a, Long b, Long k, Point g) {
        this(a, b, k);
        this.g = g;
    }

    @Override
    public String toString() {
        return "Ecc [a=" + a + ", b=" + b + ", k=" + k + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((a == null) ? 0 : a.hashCode());
        result = prime * result + ((b == null) ? 0 : b.hashCode());
        result = prime * result + ((k == null) ? 0 : k.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ecc other = (Ecc) obj;
        if (a == null) {
            if (other.a != null)
                return false;
        } else if (!a.equals(other.a))
            return false;
        if (b == null) {
            if (other.b != null)
                return false;
        } else if (!b.equals(other.b))
            return false;
        if (k == null) {
            if (other.k != null)
                return false;
        } else if (!k.equals(other.k))
            return false;
        return true;
    }

    public Long getA() {
        return a;
    }

    public void setA(Long a) {
        this.a = a;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }

    public Long getK() {
        return k;
    }

    public void setK(Long k) {
        this.k = k;
    }

    public Long getOrder() {
    	if (order == null) {
    		order = Integer.valueOf(getPoints().size()).longValue();
    	}
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Point getG() {
        return g;
    }

    public void setG(Point g) {
        this.g = g;
    }

    @JsonIgnore
    public Set<Point> getPoints() {
        Set<Point> points = new HashSet<>();
        points.add(null);
        for (int x = 0; x < this.k; x += 1) {
            for (int y = 0; y < this.k; y += 1) {
                if ((y * y - x * x * x - this.a * x - this.b) % this.k == 0) {
                    Point p = new Point(x, y);
                    if (points.contains(p))
                        return points;
                    points.add(p);
                }
            }
        }
        return points;
    }

    /**
     * Returns the result of adding point p to point q, according to the
     * group law for elliptic curves. The point at infinity is represented
     * as null. The base of the prime field is k.
     *
     * @param p
     * @param q
     * @param k
     * @return
     */
    public static Point addPoint(final Point p, final Point q, final Ecc curve) {
        if (p == null)
            return q;
        if (q == null)
            return p;

        Number m;

        if (p.x != q.x) {
            // Two distinct points.
            m = (p.y - q.y) * inverseOf(p.x - q.x, curve.getK());
        } else {
            if (p.y == 0 && q.y == 0) {
                // This may only happen if p = q is a root of the elliptic
                // curve, hence the line is vertical.
                return null;
            } else if (p.y == q.y) {
                // The points are the same, but the line is not vertical.
                m = (3 * p.x * p.x + curve.getA()) * inverseOf(2 * p.y, curve.getK());
            } else {
                // The points are not the same and the line is vertical.
                return null;
            }
        }

        Number rx = (m.longValue() * m.longValue() - p.x - q.x) % curve.getK();
        Number ry = (m.longValue() * (p.x - rx.longValue()) - p.y) % curve.getK();

        if (rx.intValue() < 0) {
            rx = rx.intValue() + curve.getK();
        }
        if (ry.intValue() < 0) {
            ry = ry.intValue() + curve.getK();
        }

        return new Point(rx.intValue(), ry.intValue());
    }

    /**
     * Multiply efficiently point p by n over the elliptic curve ecc. Algorithm:
     *
     *    N ← P
     *    Q ← 0
     *
     *    for i from 0 to m do
     *    	if di = 1 then
     *    		Q ← point_add(Q, N)
     *    	N ← point_double(N)
     *    return Q
     *
     * @param p
     * @param n
     * @param curve
     * @return
     */
    public static Point multiplyPoint(final Point p, final Number n, final Ecc curve) {
        int no = n.intValue();
        Point N = p;
        Point Q = null;
        while(no != 0){
            int d = no % 2;
            if (d == 1) {
                Q = addPoint(Q, N, curve);
            }
            N = addPoint(N, N, curve);
            no = no/2;
        }
        return Q;
    }

    /**
     * Modular inverse of n relative to k.
     *
     * @param n
     * @param k
     * @return
     */
    public static Integer inverseOf(Number n, Number k) {
        BigInteger biN = new BigInteger(n.toString());
        BigInteger biK = new BigInteger(k.toString());
        return biN.modInverse(biK).intValue();
    }

}
