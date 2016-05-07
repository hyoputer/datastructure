import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// �Է¹��� �迭�� �����ΰ� �ƴѰ�?
			int[] value;	// �Է� ���� ���ڵ��� �迭
			String nums = br.readLine();	// ù ���� �Է� ����
			if (nums.charAt(0) == 'r')
			{
				// ������ ���
				isRandom = true;	// �������� ǥ��

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// �� ����
				int rminimum = Integer.parseInt(nums_arg[2]);	// �ּҰ�
				int rmaximum = Integer.parseInt(nums_arg[3]);	// �ִ밪

				Random rand = new Random();	// ���� �ν��Ͻ��� �����Ѵ�.

				value = new int[numsize];	// �迭�� �����Ѵ�.
				for (int i = 0; i < value.length; i++)	// ������ �迭�� ������ �����Ͽ� ����
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// ������ �ƴ� ���
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// �迭�� �����Ѵ�.
				for (int i = 0; i < value.length; i++)	// ���پ� �Է¹޾� �迭���ҷ� ����
					value[i] = Integer.parseInt(br.readLine());
			}

			// ���� �Է��� �� �޾����Ƿ� ���� ����� �޾� �׿� �´� ������ �����Ѵ�.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// ���� ���� ��ȣ�� ���� ���纻�� �����Ѵ�.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// ���α׷��� �����Ѵ�.
					default:
						throw new IOException("�߸��� ���� ����� �Է��߽��ϴ�.");
				}
				if (isRandom)
				{
					// ������ ��� ����ð��� ����Ѵ�.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// ������ �ƴ� ��� ���ĵ� ������� ����Ѵ�.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("�Է��� �߸��Ǿ����ϴ�. ���� : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		for(int i = value.length - 1; i >= 1; i--)
		{
			for(int j = 0; j < i; j++)
			{
				if(value[j] > value[j + 1])
				{
					int temp;
					temp = value[j];
					value[j] = value[j + 1];
					value[j + 1] = temp;
				}
			}
		}
			
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		boolean f = false;
		int mark = 0;
		
		for(int i = 0; i < value.length - 1; i++)
		{
			f = false;
			
			for(int j = i; j >= 0; j--) // i ��° ������ ���õ�
			{
				if(value[j] < value[i + 1])
				{
					mark = j + 1;;
					f = true;
					break;
				}
			}
			
			if(f == false)
				mark = 0;
			
			for(int j = i; j >= mark; j--)
			{
				int temp;
				temp = value[j];
				value[j] = value[j + 1];
				value[j + 1] = temp;
			}
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort �� �����϶�.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort �� �����϶�.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort �� �����϶�.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort �� �����϶�.
		return (value);
	}
}
