package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Input;
import java.util.Arrays;
import playGame.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation

//        ObjectNode node1 = objectMapper.createObjectNode();
//
//        node1.put("Elev1", "plm1");
//        node1.put("Elev2", "plm2");
//        node1.put("Elev3", "plm3");
//
//        ObjectNode node2 = objectMapper.createObjectNode();
//
//        node2.put("Elev1", "plm1");
//        node2.put("Elev2", "plm2");
//        node2.put("Elev3", "plm3");
//
//        output.addAll(Arrays.asList(node1));
//
//        output.addAll(Arrays.asList(node2));

//        test01_game_start.json
//        test02_place_card.json
//        test03_place_card_invalid.json
//        test04_use_environment_card.json
//        test05_use_environment_card_invalid.json
//        test06_attack_card.json
//        test07_attack_card_invalid.json
//        test08_use_card_ability.json
//        test09_use_card_ability_invalid.json
//        test10_attack_hero.json
//        test11_attack_hero_invalid.json
//        test12_use_hero_ability_1.json
//        test13_use_hero_ability_1_invalid.json
//        test14_use_hero_ability_2.json
//        test15_use_hero_ability_2_invalid.json
//        test16_multiple_games_valid.json
//        test17_multiple_games_invalid.json
//        test18_big_game.json


//        if (filePath1.compareTo("test01_game_start.json") == 0 ||
//                filePath1.compareTo("test02_place_card.json") == 0 ||
//                filePath1.compareTo("test03_place_card_invalid.json") == 0 ||
//                filePath1.compareTo("test04_use_environment_card.json") == 0 ||
//                filePath1.compareTo("test05_use_environment_card_invalid.json") == 0 ||
//                filePath1.compareTo("test06_attack_card.json") == 0 ||
//                filePath1.compareTo("test07_attack_card_invalid.json") == 0 ||
//                filePath1.compareTo("test08_use_card_ability.json") == 0 ||
//                filePath1.compareTo("test09_use_card_ability_invalid.json") == 0 ||
//                filePath1.compareTo("test10_attack_hero.json") == 0 ||
//                filePath1.compareTo("test11_attack_hero_invalid.json") == 0 ||
//                filePath1.compareTo("test12_use_hero_ability_1.json") == 0  ||
//                filePath1.compareTo("test13_use_hero_ability_1_invalid.json") == 0 ||
//                filePath1.compareTo("test14_use_hero_ability_2.json") == 0 ||
//                filePath1.compareTo("test15_use_hero_ability_2_invalid.json") == 0)
//        {

            PlayGame playGame = new PlayGame(inputData, objectMapper, output);
            playGame.play();
//        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);

  //////////      System.out.println(filePath2);

     //   ArrayNode nodulete = objectMapper.createArrayNode();

    //    nodulete.addAll(Arrays.asList(node1, node2));

    //    System.out.println(nodulete);
//
//        objectWriter.writeValue(new File("result/out_test01_game_start.json"), nodulete);
    }
}
