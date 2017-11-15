package package_1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
//Dictionary-2017.txt
public class N_gram_main_new {
	
	public static void main(String[] args) throws IOException {

		ArrayList <String> ultraLista = new ArrayList <String>();
		ArrayList <String> longestChain = new ArrayList <String>();
		ArrayList <String> longestChainHelp = new ArrayList <String>();
		
		FileInputStream fstream;
		BufferedReader br;
		String strLine;
		boolean fileExist;
		Scanner SK = new Scanner(System.in);
		String fileName;
		BigInteger counterBIG = BigInteger.valueOf(1);
		boolean flag = true;
		int counter1 = 0;
		int counter2 = 0;
		
		do {
			fileExist = true;
			fileName = SK.next();
			
			System.out.println(new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime()));
			
			try {
				fstream = new FileInputStream(fileName);
				br = new BufferedReader(new InputStreamReader(fstream));
				
				while ((strLine = br.readLine()) != null)   {			
					ultraLista.add(strLine);				
				}
				br.close();
				
			} catch(Exception e) {
				fileExist = false;
			}
		}while(!fileExist);
		
		SK.close();
		//--------------------------------------Everything is OK
		
		ArrayList<ArrayList<String>> colOfCol = new ArrayList<ArrayList<String>>();
		int max = 0;
		for(int i = 0; i < ultraLista.size(); i++) {
			if(max<ultraLista.get(i).length()) {
				max=ultraLista.get(i).length();
			}
		}
		for(int i = 0; i < max; i++) {
			colOfCol.add(new ArrayList <String>());
		}
		for(int i = 0; i < ultraLista.size(); i++) {
			
			colOfCol.get(ultraLista.get(i).length()-1).add(ultraLista.get(i));
			
		}
		
		for(int i = 0; i < colOfCol.size(); i++) {
			if(colOfCol.get(i).size()!=0) {
				counterBIG = counterBIG.multiply(BigInteger.valueOf(colOfCol.get(i).size()));
			}
		}
		System.out.println("Number of combinations: "+counterBIG);
		//--------------------------------------Everything is OK
		
		//System.out.println("Duzina velike kolone " +colOfCol.size());
				
		//--------------------------------------Everything is OK
		
		ArrayList <Integer> toDelete = new ArrayList <Integer>();
		for(int i = 0; i < max; i++) {
			if(colOfCol.get(i).isEmpty()) {
				toDelete.add(i);
				//System.out.println(i);
			}
		}
		int pozicija = 0;
		for(int i = toDelete.size()-1; i >= 0; i--) {
			//System.out.println(toDelete.get(i));
			pozicija = toDelete.get(i);
			colOfCol.remove(pozicija);
		}
		max = colOfCol.size();
		
		ArrayList <Integer> state = new ArrayList <Integer>();
		for(int i = 0; i < colOfCol.size(); i++) {
			state.add(0);
		}
		//--------------------------------------Everything is OK
		//System.out.println(Arrays.toString(colOfCol.toArray()));
		for(int i = 0; i < colOfCol.size(); i++) {
			state.set(i, colOfCol.get(i).size());
		}
		System.out.println();
		ArrayList <Integer> state2 = new ArrayList <Integer>();
		for(int i = 0; i < state.size(); i++ ) {
			state2.add(state.get(i));
		}
		ArrayList <String> real = new ArrayList <String>();
		
		BigInteger brojac = BigInteger.valueOf(0);
		
		while(1==1){
			for(int i = state.get(state.size()-1); i > 0; i--) {
				state.set(state.size()-1, i);
				//System.out.println(Arrays.toString(stanje.toArray()));
				for(int u = 0; u < state.size(); u++) {
					real.add(colOfCol.get(u).get(state.get(u)-1));
				}
				flag = true;
				counter1 = 0;
				counter2 = 0;
				do {
					flag = true;
					counter1 = 0;
					do {
						if(flag) {
							longestChainHelp = new ArrayList <String>();
							longestChainHelp.add(real.get(counter2));
						}
									
						if(!flag) {
							if(isOK(longestChainHelp.get(longestChainHelp.size()-1), real.get(counter1))) {
								longestChainHelp.add(real.get(counter1));
							}
						}
						flag = false;
						counter1++;
						
					}while(counter1<real.size());
					
					if(longestChain.size() < longestChainHelp.size()) {
						longestChain = longestChainHelp;
					}
					
					counter2++;
				}while(counter2<real.size());
				
				brojac = brojac.add(BigInteger.valueOf(1));
				System.out.println("Currently, combination no.: " + brojac + "----" + counterBIG);
				
				//System.out.println(Arrays.toString(real.toArray()));
				System.out.println("LONGEST CHAIN TILL NOW : "+Arrays.toString(longestChain.toArray()));
				real = new ArrayList <String>();
				
				if(state.get(state.size()-1) == 1) {
					state.set(state.size()-2, state.get(state.size()-2)-1);
				}
				
				for(int k = 2 ;k <state.size()+1; k++ ) {
					
					if(state.get(state.size()-k) == 0) {
						if(state.get(0) != 0) {
							state.set(state.size()-k, state2.get(state2.size()-k));
							state.set(state.size()-(k+1), state.get(state.size()-(k+1))-1);
						}
					}
				}
				
				if(counterBIG.equals(brojac)) {
					break;
				}
			}
			if(!counterBIG.equals(brojac)) {
				state.set(state.size()-1, state2.get(state2.size()-1));
			}else {
				break;
			}
		}
		
		System.out.println();
		System.out.println(new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime()));
		
	}

private static boolean isOK(String newWord, String lastWord) {
		
		boolean flag = true;
		boolean flag2 = true;

		int counter = 0;
		String[] array1 = newWord.split("");
		String[] array2 = lastWord.split("");
		
		ArrayList<String> list1 = new ArrayList<String>();
		for(int i = 0; i < array1.length; i++) {
			list1.add(array1[i]);
		}
		
		ArrayList<String> list2 = new ArrayList<String>();
		for(int i = 0; i < array2.length; i++) {
			list2.add(array2[i]);
		}
	
		if(newWord.length() == lastWord.length()-1) {
			
			while(counter != list1.size()) {
				flag2 = true;
				for(int i = 0; i < list2.size(); i++) {
					if(list1.get(counter).equals(list2.get(i))){

						list1.remove(counter);
						list2.remove(i);
						counter = 0;
						flag2 = false;

						break;
					}	
				}
				if(flag2) {
					counter++;
				}	
			}
			if(list1.size() > 0) {
				flag = false;
			}
		}else {
			flag = false;
		}
		return flag;
	}
}