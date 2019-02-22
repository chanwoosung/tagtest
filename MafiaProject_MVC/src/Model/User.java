package Model;

public class User {
public String name;
public int vote;
public int rand;

public int getVote() {
	return vote;
}
public void setVote(int vote) {
	this.vote = vote;
}
public int getRand() {
	return rand;
}
public void setRand(int rand) {
	this.rand = rand;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public User(String name, int vote, int rand) {
	super();
	this.name = name;
	this.vote = vote;
	this.rand = rand;
}


}
