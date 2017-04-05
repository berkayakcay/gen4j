/**
 *
 * @author Berkay
 */
package gen4j;

import gen4j.util.Population;

public class main {

    public static String target;
    public static int popmax;
    public static float mutatinRate;
    public static Population population;

    public static void main(String[] args) {

        target = "to be or not to be";
        popmax = 1000;
        mutatinRate = (float) 0.01;

        population = new Population(target, mutatinRate, popmax);

        while (!population.finished()) {
            // Generate matingpool
            population.naturalSelection();
            
            // Create next generation
            population.generate();  
            
            // Calculate fitness
            population.calcFitness();

            displayInfo();

            if (population.finished()) {
                System.out.println("Finished!");
                System.out.println(population.getBest());
                break;
            }
        }
    }

    public static void displayInfo() {
        String answer = population.getBest();
        System.out.println(answer);

        System.out.println("All phrases : \n" + population.allPhrases());
        System.out.println("Generations :" + population.getGenerations());
        System.out.println("Average fitness :" + population.getAverageFitness());
        System.out.println("total population :" + popmax);
        System.out.println("Mutation rate :" + mutatinRate);

    }

}
