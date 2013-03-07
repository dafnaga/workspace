package hopappcore;


public class HopGame {		
	
	public class HopGameOverException extends Exception{
		public HopGameOverException(){
			super();
		}
	}
	
	private Player[] players;
	private Integer currentRound; 
	
	private Integer numPlayers(){
		return players.length;
	}
	
	public class IncorrectNumebrOfPlayersException extends Exception{
		public IncorrectNumebrOfPlayersException(String msg){
			super(msg);
		}
	}
	
	public HopGame(User[] playerUsers) throws IncorrectNumebrOfPlayersException{
		// validate list is legal (not empty, not too big)
		if (playerUsers.length != HopConsts.MAX_NUM_PLAYERS){
			throw new IncorrectNumebrOfPlayersException("Expected at most" + HopConsts.MAX_NUM_PLAYERS + "players, got " + playerUsers.length + "players");
		}
		
		// Initialize player list
		players = new Player[playerUsers.length];
		for (int i = 0; i < playerUsers.length; i++){
			players[i] = new Player(playerUsers[i]);
		}
		
		// Initialize current round
		currentRound = 0;
	}
	
	public void setPlayerRoundScore(Integer PlayerNum , Integer round, Integer score){
		players[PlayerNum].setRoundScore(round, score);
	}
	
	public Integer[] getRoundScore(Integer round){		
		Integer currentScore[] = new Integer[numPlayers()];
		
		if (round == 0){
			return currentScore;
		}
		
		for (int i = 0; i < numPlayers(); i++){			
			currentScore[i] = players[i].getTotalScore(round - 1);
		}
		return currentScore;
	}
	
	public Integer[] getLastRoundScore(Integer round){
		// TODO if first round not over yet, return current scores
		return getRoundScore(currentRound);
	} 
	
	public void SetRoundWinner(Integer round, Player player){
		// TODO update score of winner to the negative reward for the current round
	}
	
	public Integer GetCurrentRound() throws HopGameOverException{
		assertGameActive();
		
		return currentRound;
	}
	
	public Integer nextPickerPlayer() throws HopGameOverException{
		assertGameActive();
		
		return ((currentRound - 2) % numPlayers());
	}
	
	public Integer nextDealerPlayer() throws HopGameOverException{
		assertGameActive();
		
		return ((currentRound - 1) % numPlayers());
	}

	public Integer nextStarterPlayer() throws HopGameOverException{
		assertGameActive();
		
		return (currentRound % numPlayers());
	}

	public Boolean isGameOver(){
		return (currentRound >= HopConsts.NUM_ROUNDS);
	}
	
	private void assertGameActive() throws HopGameOverException {
		if (isGameOver()){
			throw new HopGameOverException();
		}
	}
		
}
