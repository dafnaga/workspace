package hopappcore;

import hopappcore.Player.RoundScore.RoundNotSetException;

public class Player {	
	class RoundScore {
		
		class RoundNotSetException extends Exception{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public RoundNotSetException(String message){
				super(message);
			}
		}
		
		private Integer score;
		private Boolean set;
		private Integer round;		
		
		public RoundScore(Integer round){
			set = false;
			this.round = round;
		}
		
		public void setScore(Integer score){
			set = true;
			this.score = score;
		}
		
		public boolean isSet(){
			return set;
		}

		public Integer getScore() throws RoundNotSetException {
			if (!set){
				throw new RoundNotSetException("Points for round " + round +" hasn't been set.");
			}
			return score;
		}
	}
	
	private User user;
	RoundScore[] scores;
	
	public Player(User user){
		this.user = user;
		scores = new RoundScore[HopConsts.NUM_ROUNDS];
		for (int i = 0; i < scores.length; i++){
			scores[i] = new RoundScore(i);
		}
	}
	
	public void setRoundScore(Integer round, Integer score){
		scores[round].setScore(score);		
	}

	public void setRoundWinner(Integer round){
		scores[round].setScore((round+1)*10 + 30);
	}
	
	public Integer getTotalScore(){
		return getTotalScore(scores.length);
	}
	
	public Integer getTotalScore(Integer round){
		Integer totalScore = 0;
		for (int i = 0; i < round; i++){
			if (!scores[i].isSet()){
				break;
			}
			try {
				totalScore += scores[i].getScore();
			} catch (RoundNotSetException e) {				
				e.printStackTrace();
			}
		}
		
		return totalScore;
	}
	
	public User getUser(){
		return user;
	}
}
