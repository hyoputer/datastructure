import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

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
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
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
					Swap(value, j, j + 1);
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
			
			for(int j = i; j >= 0; j--) // i 번째 까지는 소팅됨
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
				Swap(value, j, j + 1);
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		if(value.length == 1)
		{
			return value;
		}
		
		int[] front = DoMergeSort(Arrays.copyOfRange(value, 0, value.length / 2));
		int[] back = DoMergeSort(Arrays.copyOfRange(value, value.length / 2, value.length));
		int pf = 0, pb = 0, pa = 0; // pointer of 2 subarrays and result array
		
		while(true)
		{
			if(front[pf] >= back[pb])
			{
				value[pa++] = back[pb++];
				
				if(pb >= back.length)
				{
					while(pf < front.length)
					{
						value[pa++] = front[pf++];
					}
					break;
				}
			}
			else
			{
				value[pa++] = front[pf++];
				
				if(pf >= front.length)
				{
					while(pb < back.length)
					{
						value[pa++] = back[pb++];
					}
					break;
				}
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		if(value.length == 1)
			return value;
		
		int pivot = value[0];
		int count = 0;
		
		for(int i = 1; i < value.length; i++)
		{
			if(value[i] < pivot)
			{
				count++;
				if(i > count)
				{
					Swap(value, i, count);
				}
			}
		}
		
		Swap(value, 0, count);
		
		if(count > 0)
		{
			int[] front = DoQuickSort(Arrays.copyOfRange(value, 0, count));
			for(int i = 0; i < count; i++)
			{
				value[i] = front[i]; 
			}
		}
		if(count + 1 < value.length)
		{
			int[] back = DoQuickSort(Arrays.copyOfRange(value, count + 1, value.length));
			for(int i = count + 1; i < value.length; i++)
			{
				value[i] = back[i - count - 1];
			}
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		return (value);
	}
	
	static void Swap(int[] array, int index1, int index2)
	{
		int temp;
		temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}
