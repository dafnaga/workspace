
public class Guest {
	private String name;
	private GuestSet friends;
	private GuestSet family;
	
	private GuestSet enemies;
	private Boolean musicLover;	
	
	public Guest(String name){
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMusicLover() {
		return musicLover;
	}

	public void setMusicLover(Boolean musicLover) {
		this.musicLover = musicLover;
	}

	public GuestSet getEnemies() {
		return enemies;
	}

	public void setEnemies(GuestSet enemies) {
		this.enemies = enemies;
	}

	public GuestSet getFriends() {
		return friends;
	}

	public void setFriends(GuestSet friends) {
		this.friends = friends;
	}

	public GuestSet getFamily() {
		return family;
	}

	public void setFamily(GuestSet family) {
		this.family = family;
	}
	
	
}
