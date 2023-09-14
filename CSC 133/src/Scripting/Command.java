package Scripting;
import java.util.*;

public class Command{
	private String cmd;
	private String[] parms;
	
	public Command(String raw) {
		StringTokenizer st = new StringTokenizer(raw, ":");
		if(st.countTokens() < 2) return;
		
		cmd = st.nextToken().trim();
		String rawParms = st.nextToken().trim();
		StringTokenizer st2 = new StringTokenizer(rawParms, ",");
		parms = new String[st2.countTokens()];
		for(int i = 0; i < parms.length; i++) {
			parms[i] = st2.nextToken().trim();
		}
	}
	
	public String getCommand() {
		return cmd;
	}
	public boolean isCommand(String input) {
		if(cmd == null) return false;
		return cmd.toLowerCase().equals(input.toLowerCase());
	}
	
	public int getNumParms() {
		if(parms == null) return 0;
		return parms.length;
	}
	
	public String getParmByIndex(int index) {
		if(parms == null) return null;
		if(index>=parms.length) return null;
		return parms[index];
	}
}