import java.util.ArrayList;
import java.util.Date;

public class Block {

	private String hash;
	private String previousHash;
	private String merkleRoot;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	private int nonce;

	//Block Constructor.
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}

	//Calculate new hash based on blocks contents
	public String calculateHash() {
		return StringUtil.applySha256(
				previousHash +
						Long.toString(timeStamp) +
						Integer.toString(nonce) +
						merkleRoot
		);
	}

	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if (transaction == null) return false;
		if((!"0".equals(previousHash))) {
			if (!transaction.processTransaction()) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}

		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public String getHash() {
		return hash;
	}
}
