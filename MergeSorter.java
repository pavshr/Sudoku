// Classic mergesort algorithm reworked for sorting the empty cells 
// by the amount of possible solutions for them.

public class MergeSorter {
	public static void sort(Rute[] array) {
		if (array.length <= 1) return;
		Rute[] left = new Rute[array.length/2];
		Rute[] right = new Rute[array.length - left.length];

		for (int i = 0; i < left.length; i++) {
				left[i] = array[i];
			}

		for (int i = 0; i < right.length; i++) {
			right[i] = array[i + left.length];
		}
		sort(left);
		sort(right);
		merge(left, right, array);
	}

	public static void merge(Rute[] left, Rute[] right, Rute[] array) {
		int i = 0;
		int j = 0;
		int x = 0;

		while (i < left.length && j < right.length) {
			if (left[i].finnAlleMuligeTall().length < right[j].finnAlleMuligeTall().length) {
				array[x] = left[i];
				x++;
				i++;
			}else {
				array[x] = right[j];
				x++;
				j++;
			}
		}
		while (i < left.length) {
			array[x] = left[i];
			x++;
			i++;
		}
		while (j < right.length) {
			array[x] = right[j];
			x++;
			j++;
		}
	}	
}