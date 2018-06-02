package footballsimulation;


import java.util.Iterator;

import collections.ArrayMinHeap;
import collections.ArraySet;
import collections.Dictionary;
import collections.MinHeap;
import collections.Set;
import utils.ComparablePair;

public class AutomatedFootballManager implements FootballManager {

	public Set<Player> decideStartingLineUp(FootballClub ownClub, FootballClub opponent) {
		
		Player[] squadArr = ownClub.getSquad().toArray();
		Set<Player> startingSquad = new ArraySet<Player>();
		
		int GkCount = 0; int DlCount = 0; int DcCount = 0; int DrCount = 0; int MlCount = 0; int McCount = 0; int MrCount = 0; int FcCount = 0;
		for(Player player : squadArr)
		{
			if(GkCount != 1)
			{
				if (player.getPosition() == Position.GK)
				{
					startingSquad.add(player);
					GkCount++;
					continue;
				}
			}
			if(DlCount != 1)
			{
				if (player.getPosition() == Position.DL)
				{
					startingSquad.add(player);
					DlCount++;
					continue;
				}
			}
			if(DcCount != 2)
			{
				if (player.getPosition() == Position.DC)
				{
					startingSquad.add(player);
					DcCount++;
					continue;
				}
			}
			if(DrCount != 1)
			{
				if (player.getPosition() == Position.DR)
				{
					startingSquad.add(player);
					DrCount++;
					continue;
				}
			}
			if(MlCount != 1)
			{
				if (player.getPosition() == Position.ML)
				{
					startingSquad.add(player);
					MlCount++;
					continue;
				}
			}
			if(McCount != 2)
			{
				if (player.getPosition() == Position.MC)
				{
					startingSquad.add(player);
					McCount++;
					continue;
				}
			}
			if(MrCount != 1)
			{
				if (player.getPosition() == Position.MR)
				{
					startingSquad.add(player);
					MrCount++;
					continue;
				}
			}
			if(FcCount != 2)
			{
				if (player.getPosition() == Position.FC)
				{
					startingSquad.add(player);
					FcCount++;
					continue;
				}
			}
		}
		return startingSquad;
	}

	public Set<Player> decideSubstitutePlayers(FootballClub ownClub, FootballClub opponent, Set<Player> startinglineUp) {
		
		Player[] startingArr = startinglineUp.toArray();
		Set<Player> substitutes = new ArraySet<Player>((ArraySet<Player>) ownClub.getSquad());
		
		for (Player player:startingArr)
		{
			substitutes.remove(player);
		}
		while (substitutes.size() != 7)
		{
			substitutes.remove();
		}
		return substitutes;
	}

	public Set<Player> makeSubstitutions(FootballClub ownClub, FootballMatch footballMatch) {

		if (ownClub.equals(footballMatch.getHomeClub()))
		{
			return makeSubstitutions(footballMatch.getHomeAchievements(),footballMatch.getHomeStartingLineUp(),footballMatch.getHomeSubstitutePlayers());
		}
		else 
		{
			return makeSubstitutions(footballMatch.getAwayAchievements(),footballMatch.getAwayStartingLineUp(),footballMatch.getAwaySubstitutePlayers());
		}

	}
	private Set<Player> makeSubstitutions(Dictionary<Integer,Integer> achievements, Set<Player> oldLineup, Set<Player> substitutePlayers)
	{
		Iterator<Integer> KeyIt = achievements.getKeyIterator();
		Iterator<Integer> ValIt = achievements.getValueIterator();
		
		MinHeap<ComparablePair<Integer,Integer>> minHeap = new ArrayMinHeap<ComparablePair<Integer,Integer>>(11);
		while(KeyIt.hasNext())
		{
			ComparablePair<Integer,Integer> pair = new ComparablePair<Integer,Integer>(KeyIt.next(),ValIt.next());
			minHeap.add(pair);
		}
		
		int substitutions = 0;
		Player[] oldLineupArr = oldLineup.toArray();
		Player[] substitutePlayersArr = substitutePlayers.toArray();
		Set<Player> newLineup = new ArraySet<Player>((ArraySet<Player>) oldLineup);
		
		while(substitutions < 3 && !minHeap.isEmpty()) {
			
			ComparablePair<Integer,Integer> worstPlayerPair = minHeap.removeMin();
			Player worstPlayer = null;
			for(Player player:oldLineupArr)
			{
				if(player.hashCode() == worstPlayerPair.getKey())
				{
					worstPlayer = player;
					break;
				}
			}
			
			for(Player player:substitutePlayersArr)
			{
				if(player.getPosition() == worstPlayer.getPosition())
				{
					newLineup.remove(worstPlayer);
					newLineup.add(player);
					substitutePlayers.remove(player);
					substitutions++;
					break;
				}
				
			}
		}
	
	return newLineup;
	}

}