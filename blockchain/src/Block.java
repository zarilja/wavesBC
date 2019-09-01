public class Block {

    public static final int TRANSACTIONS_PER_BLOCK = 4;

    private Transaction[] transactions;
    private int nextEmpty;

    public Block() {
        transactions = new Transaction[TRANSACTIONS_PER_BLOCK];
        nextEmpty = 0;
    }

    public boolean isItCompleted() {
        return nextEmpty >= TRANSACTIONS_PER_BLOCK;
    }

    public DrugDataTransaction getTransaction(int ID) {
        return (DrugDataTransaction) transactions[ID];
    }

    void addTransaction(Transaction transaction) {
        transactions[nextEmpty++] = transaction;
    }

    public int howMuchTransactionInBlock()

    {
        int s=0;
        for(int i=0;i<TRANSACTIONS_PER_BLOCK;i++)
            if (transactions[i]!= null)
                s++;
            else
                break;
        return (s);
    }
}
