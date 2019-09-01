import javafx.scene.paint.Color;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.util.Scanner;

public class CaseMenu {
    private final Scanner scanner;
    private ArrayList<Block> blocks;
    private KeyPair keyPair;

    public CaseMenu(Scanner scanner) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        this.scanner = scanner;
        this.blocks = new ArrayList<>();
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        byte[] seed = new byte[]{12, 18, 34, 16, 96, 79};
        keyGen.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom(seed));
        keyPair = keyGen.generateKeyPair();
    }

    private void printMenu() {
        System.out.println("Введите 1. Добавить новый транзакт;");
        System.out.println("Введите 2. Вывести список всех транзактов;");
        System.out.println("Введите 3. Вывести транзакт по наиенованию лекарства;");
        System.out.println("Введите 4. Выход из приложения;");
    }

    public static void main(String[] args) throws Exception {
        CaseMenu caseMenu = new CaseMenu(new Scanner(System.in));
        caseMenu.start();
    }

    public void start() throws Exception {
        if (this.scanner != null) {
            int key;
            do {
                printMenu();
                System.out.print("Введите номер меню: ");
                key = this.scanner.nextInt();
                this.scanner.nextLine();
                switch (key) {
                    case 1:
                        System.out.println("Input drug name: ");
                        String drugName = scanner.nextLine();
                        System.out.println("Input pharmacy: ");
                        String pharmacyPublicKey = scanner.nextLine();
                        System.out.println("Input state: ");
                        String stateUpdate = scanner.nextLine();
                        addTransaction(createSignedTransaction(drugName,pharmacyPublicKey,stateUpdate));
//                        try(FileWriter writer = new FileWriter("notes3.txt", false)) { String text = drugName+" "+manufacturerPublicKey+" "+pharmacyPublicKey+" "+stateUpdate;
//                            writer.close();
//                        }
                        break;
                    case 2:
                        for (int i = 0; i < blocks.size(); i++) {
                            for (int j = 0; j < blocks.get(i).howMuchTransactionInBlock(); j++) {
                                System.out.println(blocks.get(i).getTransaction(j).getHashCode() + "\t" + blocks.get(i).getTransaction(j).isValid());
                            }
                        }


//                        try(FileReader fr = new FileReader("notes3.txt")) {
//                            fr.close();
//                            System.out.println(fr);
//                        }
                        break;
                    case 3:
                        System.out.print("Введите название лекарства: ");
                        String inName = this.scanner.nextLine();
                        for (int i = 0; i < blocks.size(); i++) {
                            for (int j = 0; j < blocks.get(i).howMuchTransactionInBlock(); j++) {
                                if (inName.equals(blocks.get(i).getTransaction(j).getDrugName())) {
                                    System.out.println(blocks.get(i).getTransaction(j).getHashCode());
                                    System.out.print(blocks.get(i).getTransaction(j).getStateUpdate()+ "\t" + blocks.get(i).getTransaction(j).isValid());
                                }

                            }
                        }


                        break;
                    case 4:
                        System.out.println("Завершение программы...");
                        break;
                    default:
                        System.out.println("Вы ввели неверное значение меню...\n");
                }
            } while (key != 4);
        }

    }


    DrugDataTransaction createSignedTransaction(String drugName, String pharmacyPublicKey, String stateUpdate) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature ecdsa = Signature.getInstance("SHA256withECDSA");
        ecdsa.initSign(keyPair.getPrivate());
        ecdsa.update((drugName+pharmacyPublicKey+stateUpdate).getBytes(StandardCharsets.UTF_8));
        byte[] signature = ecdsa.sign();
        return new DrugDataTransaction(drugName,keyPair.getPublic(),pharmacyPublicKey,stateUpdate,signature);
    }


    void addTransaction(DrugDataTransaction transact) {
        if (blocks.size()<1)
            blocks.add(new Block());
            Block lastBlock = blocks.get(blocks.size() - 1);
        if (lastBlock.isItCompleted()) {
            lastBlock = new Block();
            blocks.add(lastBlock);

        }
        lastBlock.addTransaction(transact);
    }
}