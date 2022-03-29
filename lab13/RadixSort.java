/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int max = Integer.MIN_VALUE;
        for (String s : asciis) {
            if (s.length() > max) {
                max = s.length();
            }
        }
        String[] output = asciis.clone();
        for (int i = max - 1; i >= 0; i--) {
            sortHelperLSD(output, i);
        }
        return output;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        /**String[] output = new String[asciis.length];
        int end = 0;
        for (String s : asciis) {
            if (s.length() <= index) {
                output[end] = s;
                end++;
            }
        }
        for (int i = 0; i < 256; i++) {
            for (String s : asciis) {
                if (s.length() <= index) {
                    continue;
                }
                if (((int) s.charAt(index)) == i) {
                    output[end] = s;
                    end++;
                }
            }
        }
        System.arraycopy(output, 0, asciis, 0, asciis.length);**/
        String[] output = new String[asciis.length];
        int[] radix = new int[256];
        for (String s : asciis) {
            if (s.length() <= index) {
                radix[0]++;
                continue;
            }
            radix[s.charAt(index)]++;
        }
        for (int i = 1; i < radix.length; i++) {
            radix[i] += radix[i - 1];
        }
        for (int i = asciis.length - 1; i >= 0; i--) {
            if (asciis[i].length() <= index) {
                radix[0]--;
                output[radix[0]] = asciis[i];
                continue;
            }
            radix[asciis[i].charAt(index)]--;
            output[radix[asciis[i].charAt(index)]] = asciis[i];
        }
        System.arraycopy(output, 0, asciis, 0, asciis.length);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
