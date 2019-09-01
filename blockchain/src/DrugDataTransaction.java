import com.google.common.hash.HashCode;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.Time;

    public class DrugDataTransaction implements Transaction {
        private String drugName;
        private PublicKey manufacturerPublicKey;
        private String pharmacyPublicKey;
        private String stateUpdate;
        private HashCode hashCode;
        private byte[] signature;

        public DrugDataTransaction(String drugName, PublicKey manufacturePublicKey, String pharmacyPublicKey, String stateUpdate, byte[] signature) {
            this.drugName = drugName;
            this.manufacturerPublicKey = manufacturePublicKey;
            this.pharmacyPublicKey = pharmacyPublicKey;
            this.stateUpdate = stateUpdate;
            this.hashCode = Hasher.hash(drugName+manufacturePublicKey+pharmacyPublicKey+stateUpdate);
            this.signature = signature;

        }

        public String getDrugName() {
            return drugName;
        }

        public HashCode getHashCode() {
            return hashCode;
        }

        public String getStateUpdate() {
            return stateUpdate;
        }


        boolean isValid() throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
            Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
            ecdsaVerify.initVerify(manufacturerPublicKey);
            ecdsaVerify.update((drugName+pharmacyPublicKey+stateUpdate).getBytes(StandardCharsets.UTF_8));
            return ecdsaVerify.verify(signature);
        }
    }

