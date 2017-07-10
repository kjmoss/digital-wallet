package paymo_network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	public static void main(String[] args) throws IOException{
		long startTime = System.currentTimeMillis();
		
		String inDirectory = args[0];
		String outDirectory = args[1];
		String batchPath = inDirectory + File.separator + "batch_payment.txt";
		String streamPath = inDirectory + File.separator + "stream_payment.txt";
		
		Network n = new Network();
		
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(batchPath));
		
		
		line = br.readLine();//reading and ignoring the first line
		if(line == null){
			br.close();
			return;
		}
		
		while((line = br.readLine()) != null){
			User[] users = parse(line, n);
			if(users != null){
				User u1 = users[0];
				User u2 = users[1];
				beFriends(u1,u2);
			}
		}
		br.close();
		
		long midTime = System.currentTimeMillis();
		System.out.println("Batch file done. Runtime: "+(midTime-startTime));
		
		String out1 = outDirectory + File.separator + "output1.txt";
		String out2 = outDirectory + File.separator + "output2.txt";
		String out3 = outDirectory + File.separator + "output3.txt";
		
		BufferedReader br2 = new BufferedReader(new FileReader(streamPath));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(out1));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(out2));
		BufferedWriter bw3 = new BufferedWriter(new FileWriter(out3));
		
		line = br2.readLine();//reading and ignoring the first line
		if(line == null){
			br2.close();
			bw1.close();
			bw2.close();
			bw3.close();
			return;
		}
		
		while((line = br2.readLine()) != null){
			User[] users = parse(line, n);
			if(users != null){
				User u1 = users[0];
				User u2 = users[1];
				int dist = u1.getDist(u2);
				switch(dist){
				case 1:
					bw1.write("trusted");
					bw2.write("trusted");
					bw3.write("trusted");
					break;
				case 2:
					bw1.write("unverified");
					bw2.write("trusted");
					bw3.write("trusted");
					break;
				case 3:
				case 4:
					bw1.write("unverified");
					bw2.write("unverified");
					bw3.write("trusted");
					break;
				default:
					bw1.write("unverified");
					bw2.write("unverified");
					bw3.write("unverified");
					break;
				}
				bw1.newLine();
				bw2.newLine();
				bw3.newLine();
				beFriends(u1,u2);
			}
		}
		br2.close();
		bw1.close();
		bw2.close();
		bw3.close();
		long endTime = System.currentTimeMillis();
		System.out.println("Stream file done. Runtime: "+(endTime-midTime));
		
		
	}
	
	
	
	
	
	public static final String regex = "^([^,]+), ([^,]+), ([^,]+), ([^,]+), (.+)$";
	public static final Pattern p = Pattern.compile(regex);
	
	
	public static User[] parse(String line, Network n){
		Matcher m = p.matcher(line);
		if(m.matches()){
			int id1 = Integer.parseInt(m.group(2));
			int id2 = Integer.parseInt(m.group(3));
			User u1 = n.getUser(id1);
			User u2 = n.getUser(id2);
			User[] users = {u1, u2};
			return users;
			
		} else {
			//System.out.println("Not sure how to handle line: "+line);
			return null;
			//throw new IllegalArgumentException("Input \"" + line + "\" not valid.");
			
			//a few lines are not in the proper format, probably due to transaction details
			//having line breaks
		}
		
	}
	
	/*
	private static boolean verifiedPayment(User u1, User u2, int distance){
		
		switch(distance){
		case 1:	return u2.within1(u1);
		case 2:	return u2.within2(u1);
		case 3:	return u2.within3(u1);
		case 4:	return u2.within4(u1);
		default:
			throw new IllegalArgumentException("Distance "+distance+" not valid."
					+ " Should be between 1 and 4 inclusive.");
		}
	}
	*/
	
	
	private static boolean beFriends(User u1, User u2){
		return u1.addFriend(u2) && u2.addFriend(u1);
	}
	
	
			

}
