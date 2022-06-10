package utilities;

/**@author rotem ghidale
 * This class represents a general counter*/
public class Counter {
    private int value;

    /**Constructor 1.*/
    public Counter() {
        this.value = 0;
    }

    /**Constructor 2- with option to set a start value.
     * @param value start value to the counter*/
    public Counter(int value) {
        this.value = value;
    }

    /**This function add number to current count.
     * @param number add this number to the counter*/
    public void increase(int number) {
        this.value += number;
    }

    /**This function subtract number from current count.
     * @param number subtract this number from the counter*/
    public void decrease(int number) {
        this.value -= number;
    }

    /**This is getter.
     * @return the current count.*/
    public int getValue() {
        return value;
    }
}