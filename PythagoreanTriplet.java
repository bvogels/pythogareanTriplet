import java.util.*;

/* Some explanations on this code, on which I worked intermittenly for a couple of days.
The major challenge is not the formula to calculate the result. Is is the understanding that
assertEquals method in in the test cases tests for equality of reference, not for equality of values.

The class PythagoreanTriplet consists of for members. The integers must have an initial value, since they would
return wrong results if they were initialized with 0. The fourth member is a static array list that holds
the PythagoreanTriplet objects. This can be quite confusing: The class PythagoreanTriplet provides a list that
holds objects of its own type. Since there shall be only on list, it has to be static.
 */

public class PythagoreanTriplet {
    int a = 1;
    int b = 1;
    int c;
    static List<PythagoreanTriplet> triplets = new ArrayList<>();

    /* This is the first constructor. It is also used by the test class to create an object to which the
    expected object will be compared.
     */

    public PythagoreanTriplet(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /* This is the second constructor. This constructor creates an PythagoreanTriplet object without any
    parameters. It will hold the list of PythagoreanTriplets, which items are created by the first
    constructor.
     */
    public PythagoreanTriplet() {
    }

    /* This to methods override the equals method to make the test cases compare the right properties */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PythagoreanTriplet p = (PythagoreanTriplet) o;
        return a == p.a && b == p.b && c == p.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }


    /* This static method calls the second constructor and creates the object that will hold the list of
    PythagoreanTriplet objects. Note that the first command clears the ArrayList of all former values.
    Otherwise the testcases will invariable fail. This is because of the static nature of the ArrayList, which
    will otherwise hold all values throughout the tests.
     */

    public static PythagoreanTriplet makeTripletsList() {
        triplets.clear();
        return new PythagoreanTriplet();
    }


    /*
    Here, some math is done. a is supposed to be the smallest value, so it runs only till factMax/2.
    It consists of two more if statements, which increase b on every iteration until it becomes factMax.
    The, a is increased by 1 and b reset to a+1. Since there are only if statements, the runtime
    should be O(n).
     */
    public PythagoreanTriplet withFactorsLessThanOrEqualTo(int factMax) {
        if (a < factMax / 2) {
            if (a <= b) {
                b++;
            }
            if (b == factMax) {
                a++;
                b = a + 1;
            }
        }
        return this;
    }

    /* This method calculates the PythagorealTriplet. The while loop checks if the sum of the two squares of
    a and b are equal to the square of sumMax (c) - a - b. If not, withFactorsLessThanOrEqual is called again.
    Since this loop will end eventually, it is further checked that a does not lead to numbers bigger than
    sumMax when square and added to the square of b. Otherwise there would be an infinite loop.
    The runtime of the method alone is O(n), but since withFactorsLessThanOrEqualTo(sumMax) is called most
    of the time, the runtime is more to O(n^2).

     */
    public PythagoreanTriplet thatSumTo(int sumMax) {
        while (a < sumMax/2 && (Math.pow(a, 2) + Math.pow(b, 2)) != Math.pow((sumMax - a - b), 2)) {
            withFactorsLessThanOrEqualTo(sumMax);
        }
        if (a < sumMax / 2) {
            c = (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        }
        return this;
    }

    /* Finally, the PythagoreanTriple is inserted into the list. Before that, max stores the sum of a, b, and
    c, because factMax and sumMax is not available to this method.
    As long as a is smaller than max/2, there is a chance of more PythagoreanTriplets. The while loop
    takes care of that and calls withFactorsLessThanOrEqualTo and thatSumTo to check for more matches.
     */

    public List<PythagoreanTriplet> build() {
        int max = a+b+c;
        while (a < max/2) {
            triplets.add(new PythagoreanTriplet(a, b, c));
            withFactorsLessThanOrEqualTo(max).thatSumTo(max);
        }
        return triplets;
    }
}