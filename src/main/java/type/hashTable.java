package type;

public class hashTable{
	private String[] slots;
	private String[] info;

	private int size = 500;
	
	public hashTable() {
		slots = new String[size];
		info = new String[size];
	}
	
	public hashTable(int size) {
		slots = new String[size];
		info = new String[size];
	}
	
	public String getSlots(int pos) {
		if(pos < slots.length) {
			return slots[pos];
		}
		
		return null;
	}
	
	public void setString(int pos, String infoString) {
		if(info[pos]!=null) {
			info[pos] += "///" + infoString;
		}
		else
		{
			info[pos] = infoString;
		}
	}
	
	public int getHash(String str) {
		int value=0;
		char c;
		
		for(int i=0;i<str.length();i++) {
			c = str.charAt(i);
			value += (int) c;
		}
		value = value % slots.length;

		while (getSlots(value)!=null && getSlots(value).compareTo(str)!=0) {
			value = (value + 1) % slots.length;
		}
		
		return value;
	}
	
	public void setHash(String keyString, String infoString) {
		int hash = getHash(keyString);
		setString(hash, infoString);
	}
	
	public String getInfo(String keyString) {
		int hash = getHash(keyString);
		return info[hash];
	}
}
