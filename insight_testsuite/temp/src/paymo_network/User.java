package paymo_network;

import java.util.Collections;
import java.util.TreeSet;

/**
 * This class represents a user, and it stores their list of friends.
 * Originally this class also stored friends-of-friends, but it appears the larger
 * list is too costly and inefficient.
 * 
 * @author Kevin
 *
 */
public class User implements Comparable<User> {
	
	private int id;
	private TreeSet<User> friends = new TreeSet<User>();//Users at distance 1 from current user
	//private TreeSet<User> friendsOfFriends = new TreeSet<User>();//Users at distance 2 from current user
	
	
	public User(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	
	protected TreeSet<User> getFriends(){
		return friends;
	}
	
	//protected TreeSet<User> getFriendsOfFriends(){
	//	return friendsOfFriends;
	//}
	
	/**
	 * Adds a user to the friends list of the current user, and updates the friends-of-friends
	 * list accordingly. Returns false if the user is already a friend or is the same as the
	 * the current user.
	 * @param u A user
	 * @return True if the friend was not already a friend or the current user.
	 */
	public boolean addFriend(User u){
		if(u == this){
			return false;
		}
		if(!friends.add(u)){
			return false;
		}
		//if(friendsOfFriends.remove(u)){
		//	for(User v : u.friends){
		//		if(!friends.contains(v)){
		//			friendsOfFriends.add(v);
		//		}
		//	}
		//	
		//} else {
		//	friendsOfFriends.addAll(u.friends);
		//}
		//friendsOfFriends.remove(this);
		return true;
	}
	
	public boolean within1(User u){
		return friends.contains(u);
	}
	
	public boolean within2(User u){
		//return within1(u) || friendsOfFriends.contains(u);
		return within1(u) || !Collections.disjoint(friends, u.friends);
	}
	
	//public boolean within3(User u){
	//	return within2(u) || !Collections.disjoint(friendsOfFriends, u.friends);
	//}
	
	//public boolean within4(User u){
	//	return within3(u) || !Collections.disjoint(friendsOfFriends, u.friendsOfFriends);
	//}
	
	public int getDist(User u){
		if(u == this){
			return 0;
		}
		if(friends.contains(u)){
			return 1;
		}
		//if(friendsOfFriends.contains(u)){
		//	return 2;
		//}
		if(!Collections.disjoint(friends, u.friends)){
			return 2;
		}
		//if(!Collections.disjoint(friendsOfFriends, u.friends)){
		//	return 3;
		//}
		TreeSet<User> thisFOF = getFriendsOfFriends();
		if(!Collections.disjoint(thisFOF, u.friends)){
			return 3;
		}
		//if(!Collections.disjoint(friendsOfFriends, u.friendsOfFriends)){
		//	return 4;
		//}
		TreeSet<User> thatFOF = u.getFriendsOfFriends();
		if(!Collections.disjoint(thisFOF, thatFOF)){
			return 4;
		}
		return 5;
	}
	
	private TreeSet<User> getFriendsOfFriends(){
		TreeSet<User> fof = new TreeSet<User>();
		for(User u : friends){
			fof.addAll(u.friends);
		}
		fof.removeAll(friends);		
		return fof;
	}
	
	
	

	@Override
	public int compareTo(User v) {
		return v.getID() - id;
	}
	
	//public static boolean disjoint
	
	
	
	
	
	
	
}
