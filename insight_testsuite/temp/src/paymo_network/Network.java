package paymo_network;

import java.util.ArrayList;

public class Network {
	ArrayList<User> users = new ArrayList<User>();
	
	protected Network(){
		
	}
	
	/**
	 * Finds the user with the given ID. If the user does not exist, the network expands
	 * and creates users with ids up to the user.
	 * 
	 * This method is protected to prevent abuse of the network expanding method. Use the
	 * findUser method if public access is needed.
	 * @param id An integer
	 * @return A User
	 */
	protected User getUser(int id){
		if(users.size() <= id){
			expand(id);
		}
		return users.get(id);
	}
	
	/**
	 * Expands the network up to the given index.
	 * @param index An integer
	 */
	private void expand(int index){
		for(int i = users.size(); i <= index; i++){
			users.add(new User(i));
		}
	}
	
	/**
	 * Finds the user in the network. Returns null if the user does not exist.
	 * This method is public and does not expand the network to create the user
	 * if they do not already exist.
	 * 
	 * Use the getUser method to expand the network.
	 * @param id
	 * @return
	 */
	public User findUser(int id){
		if(users.size() <= id){
			return null;
		} else {
			return users.get(id);
		}
	}

}
