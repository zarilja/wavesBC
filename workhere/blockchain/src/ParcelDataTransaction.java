import java.sql.Time;

public class DrugDataTransaction implements Transaction {
    String drugTN;
    String manufacturerPublicKey;
    String customerPublicKey;
    String stateUpdate;
    String shelfLife; 
    // Time timestamp;
    float weight;

    public DrugDataTransaction(String drugTN, String manufacturePublicKey, String customerPublicKey, String stateUpdate,String shelfLife, Time timestamp, float weight)
     {
        this.drugTN = drugTN;
        this.manufacturerPublicKey = manufacturerPublicKey;
        this.customerPublicKey = customerPublicKey;
        this.stateUpdate = stateUpdate;
        this.shelfLife = shelfLife;
       // this.timestamp = timestamp;
        this.weight = weight;
     }
}
